package au.com.polly.roche.model;

import au.com.polly.roche.ui.ApplicationController;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Initial, single threaded simulation processor.
 * Updates the model universe and calls any listeners...
 * Intended to be superceded by a multihreaded version. Uses simplistic modelling,
 * intended to be updated with something more sophisticated.
 *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 */
public class SingleThreadedCruncher extends BaseCruncher implements Cruncher, ModelConstants
{

private boolean davesCollisionMethod = false;
private final static int debug = 1;


/**
 *
 * @param model the universe to be modelled
 * @param intervalMS how long between updates in milliseconds
 * @param constraints how the model universe translates into the depicted version..
 */
public SingleThreadedCruncher( Model model, ModelConstraints constraints, long intervalMS, ApplicationController controller )
{
    super( model, constraints, intervalMS, controller );
}


/**
 * Invoked to calculate the vector difference in location between one body and another,
 * we also use this opportunity to calculate the distance too, rather than performing
 * it in a separate step
 *
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void calculateDeltas(
    Model data,
    ModelConstraints constraints,
    WorkingData calculations
)
{
    int i;
    int j;
    Particle p;
    Particle q;
    ParticlePair pair;
    Vector delta;
    double distance;

    for( i = 0; i < data.particleList().size(); i++ )
    {
        p = data.particleList().get( i );

        for( j = i + 1; j < data.particleList().size(); j++ )
        {
            q = data.particleList().get( j );
            pair = new ParticlePair( p, q );
            delta = q.getLocation().getDelta( p.getLocation() );
            calculations.deltaTable.put( pair, delta );

            distance = delta.scalar();
            calculations.distanceTable.put( pair, distance );
        }
    }


}

public void calculateDistances(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
)

{
    // we've already done this ;-)
    return;
}


/**
 * Calculate the gravitational force between each of the bodies. Note that
 * this is the force, not the acceleration, which requires division by the
 * mass of the body.
 *
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void calculateGravitationalInteractions(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
)
{
    int i;
    int j;
    Particle p;
    Particle q;
    ParticlePair pair;
    Vector delta;
    double distance;
    Vector gForce = null;
    double m1;
    double m2;
    double f;
    double theta;

    for( i = 0; i < data.particleList().size(); i++ )
    {
        p = data.particleList().get( i );

        for( j = i + 1; j < data.particleList().size(); j++ )
        {
            q = data.particleList().get( j );
            pair = new ParticlePair( p, q );
            delta = calculations.deltaTable.get( pair );
            distance = calculations.distanceTable.get( pair );
            m1 = p.getMass();
            m2 = q.getMass();

            if ( distance < p.getRadius() + q.getRadius() )
            {
                if ( debug > 2 )
                {
                    System.out.println();
                    System.out.println();
                    System.out.println( "--------------------------------------------------------------------------------------------------------------" );
                    System.out.println( "Using combined radii distance of " + ( p.getRadius() + q.getRadius() ) + ", rather than distance between centres (" + distance + ") for gravity calc" );
                    System.out.println( "--------------------------------------------------------------------------------------------------------------" );
                    System.out.println();
                    System.out.println();
                }
                distance = p.getRadius() + q.getRadius();
            }

            theta = delta.getDirection();
            if ( debug > 5 )
            {
                System.out.println( "SingleThreadedCruncher::calculateGravitationalInteractions(): delta=" + delta + ", distance=" + distance + ", theta=" + 360 * ( theta / ( 2 * Math.PI ))  + ", cos(theta)=" + Math.cos( theta ) + ",. sin(theta)=" + Math.sin( theta ) );
            }


            // now work out the magnitude of the force...
            // ------------------------------------------
            f = ( ModelConstants.G * m1 * m2 ) / ( distance * distance );

            // now apply to vector...
            // -----------------------
            gForce = new Vector( f * Math.cos( theta ), f * Math.sin( theta ) );

            calculations.gForceTable.put( pair, gForce );
        }
    }

}

/**
 * Check each body against each other body for collisions..
 *
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void checkForCollisions(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
)
{
    int i;
    int j;
    Particle p;
    Particle q;
    ParticlePair pair;
    ParticlePair gegenPair;
    double distance;
    double r1;
    double r2;

    for( i = 0; i < data.particleList().size(); i++ )
    {
        p = data.particleList().get( i );

        for( j = i + 1; j < data.particleList().size(); j++ )
        {
            q = data.particleList().get( j );
            pair = new ParticlePair( p, q );
            gegenPair = pair.getOpposite();

            // if two bodies have just collided, then allow the new (rebound) velocities to
            // move them apart before we try to check for collision between them again....
            // ---------------------------------------------------------------------------------
            if ( calculations.previousCollisions != null )
            {
                synchronized( calculations.previousCollisions )
                {
                    if ( ! calculations.previousCollisions.isEmpty() )
                    {
                        int count = 0;
                        if ( calculations.previousCollisions.containsKey( pair ) )
                        {
                            count = calculations.previousCollisions.get( pair );
                        } else {
                            if ( calculations.previousCollisions.containsKey( gegenPair ) )
                            {
                                count = calculations.previousCollisions.get( gegenPair );
                            }
                        }
                        if ( count > 0 )
                        {
                            if ( debug >  3)
                            {
                                System.out.println( "SingleThreadedCruncher::checkForCollisions(): SKIPPING previous collision between " + p.getLabel() + " & " + q.getLabel() + " ... count=" + count );
                            }

                            // skip this particle pair for collision detection...
                            // ---------------------------------------------------
                            continue;
                        }
                    }
                }
            }

            // determine if we need to modify the kinetics of this particle pair due to a new collision...
            // --------------------------------------------------------------------------------------------
            distance = calculations.distanceTable.get( pair );

            r1 = p.getRadius();
            r2 = q.getRadius();

            if ( ( r1 + r2 ) >= distance )
            {
                calculations.collisions.add( pair );
            }
        }
    }

}

public void calculateCollisionImpacts(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
)
{
    Particle p;
    Particle q;
    Vector pMoment;
    Vector qMoment;
    Vector sumMoments;
    Vector averageMoment;
    Vector d1Moment;
    Vector d2Moment;
    double mp;            // mass of p
    double mq;            // mass of q;


    // update the countdown for previous collision .... in
    // this way we prevent object pairs, which have just collided
    // from being able to collide again for a couple of iterations..
    // ------------------------------------------------------------
    if ( calculations.previousCollisions != null )
    {
        synchronized( calculations.previousCollisions )
        {

            if ( ! calculations.previousCollisions.isEmpty() )
            {
                // make a copy of the set of particle pairs to iterate through....
                // -----------------------------------------------------------------
                for( ParticlePair key : calculations.previousCollisions.keySet() )
                {
//            System.out.println( "calculateCollisionImpacts(): key=" + key + ", calculations.previousCalculations.containsKey()=" + calculations.previousCollisions.containsKey( key ) );
//            System.out.println( "calculateCollisionImpacts(): calculations.previousCalculations.get( key=" + key + " )=" + calculations.previousCollisions.get( key ) );
//            System.out.flush();
                    int count = calculations.previousCollisions.get( key );
                    if ( count > 0 )
                    {
                        calculations.previousCollisions.put( key, count - 1);
                    }
                }
            }
        }
    }

    for( ParticlePair pair : calculations.collisions )
    {
        p = pair.getAlpha();
        q = pair.getBeta();
        mp = p.getMass();            // mass of p
        mq = q.getMass();            // mass of q;


        // if q crashes into something much more massive than it ... just remove it
        if ( ( mp / mq ) > 1000 )
        {
            // just take out mq...
            calculations.executionList.add( q );
            continue;
        }

         // if p crashes into something much more massive than it ... just remove it
        if ( ( mq / mp ) > 1000 )
        {
            // just take out mp....
            calculations.executionList.add( p );
            continue;
        }


        if ( davesCollisionMethod )
        {
            pMoment = ((Vector)p.getVelocity().clone()).multiply( p.getMass() );
            qMoment = ((Vector)q.getVelocity().clone()).multiply( q.getMass() );
            sumMoments = (Vector)pMoment.clone();
            sumMoments.add( qMoment );


            averageMoment = ((Vector)sumMoments.clone()).divide( 2 );
            d1Moment = averageMoment.getDelta( pMoment );
            d2Moment = averageMoment.getDelta( qMoment );

            calculations.collisionForces.put( new ParticlePair( p, q ), d2Moment.getOpposite().multiply( 2 ) );
            calculations.collisionForces.put( new ParticlePair( q, p ), d1Moment.getOpposite().multiply( 2 ) );
        } else {

            // simple collision calculation, just maintains the kinetic energy of the system, without
            // concerning itself about the angle of impact. simply performs two one dimensional collision
            // determinations...
            // --------------------------------------------------------------------------------------------

            // with help from web page; http://www.director-online.com/buildArticle.php?id=460
            double vpxi = p.getVelocity().x;    // initial x velocity of p
            double vpyi = p.getVelocity().y;    // initial y velocity of p
            double vqxi = q.getVelocity().x;    // initial x velocity of q
            double vqyi = q.getVelocity().y;    // initial y velocity of q
            mp = p.getMass();            // mass of p
            mq = q.getMass();            // mass of q;

            double vpxf; // final x velocity of p
            double vpyf; // final y velocity of p
            double vqxf; // final x velocity of q
            double vqyf; // final y velocity of q

            vpxf = ( ( mp - mq ) / ( mp + mq ) ) * vpxi + ( ( 2 * mq ) / ( mp + mq ) ) * vqxi;
            vpyf = ( ( mp - mq ) / ( mp + mq ) ) * vpyi + ( ( 2 * mq ) / ( mp + mq ) ) * vqyi;

            vqxf = ( ( mq - mp ) / ( mp + mq ) ) * vqxi + ( ( 2 * mp ) / ( mp + mq ) ) * vpxi;
            vqyf = ( ( mq - mp ) / ( mp + mq ) ) * vqyi + ( ( 2 * mp ) / ( mp + mq ) ) * vpyi;

            p.setVelocity( new Vector( vpxf, vpyf ) );
            q.setVelocity( new Vector( vqxf, vqyf ) );
        }

        synchronized( calculations.previousCollisions )
        {
            calculations.previousCollisions.put( pair, 3 );
        }


//        System.out.println( "COLLISION--> between " + p.getLabel() + "-" + p.getLocation() + "  &  " + q.getLabel() + "-" + q.getLocation() );
    }
    //To change body of implemented methods use File | Settings | File Templates.
}

/**
 * adds up all of the forces currently acting upon the particle
 *
 * @param data
 * @param contraints
 * @param calculations
 */
public void impartForces(
        Model data,
        ModelConstraints contraints,
        WorkingData calculations
)
{
    double mass;
    Vector sum;

    for( Particle p : data.particleList() )
    {
        mass = p.getMass();
        sum = new Vector( 0.0, 0.0 );
        for( Particle q : data.particleList() )
        {
            if ( p == q ) { continue; }

            p.impartForce( calculations.gForceTable.get( p, q ) );
        }
    }

    if ( davesCollisionMethod )
    {
        for( ParticlePair pair : calculations.collisions )
        {
            Vector collisionForce = calculations.collisionForces.get( pair );
            if ( collisionForce != null )
            {
                pair.getAlpha().impartZeroTimeForce( collisionForce );
            }
    
            ParticlePair gegenteil = pair.getOpposite();
            collisionForce = calculations.collisionForces.get( gegenteil );
            if ( collisionForce != null )
            {
                gegenteil.getAlpha().impartZeroTimeForce( collisionForce );
            }
        }
    }

    //To change body of implemented methods use File | Settings | File Templates.
}


/**
 *
 * Check for bodies which may have passed inside the roche limit...
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void checkTidalDisintegrations(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
)
{
    int i;
    int j;
    Particle p;
    Particle q;
    Satellite satellite;
    Planet  planet;
    double distance;

    for( i = 0; i < data.particleList().size(); i++ )
    {
        p = data.particleList().get( i );

        for ( j = i + 1; j < data.particleList().size(); j++ )
        {
            q = data.particleList().get( j );

            satellite = null;
            planet = null;

            if ( (  p instanceof Planet ) && ( q instanceof Satellite ) )
            {
                planet = (Planet)p;
                satellite = (Satellite)q;
            }

            if ( ( p instanceof Satellite ) && ( q instanceof Planet ) )
            {
                satellite = (Satellite)p;
                planet = (Planet)q;
            }

            if ( ( satellite != null ) && ( planet != null ) )
            {
                distance = calculations.distanceTable.get( planet, satellite );
                if ( distance <= planet.getRocheLimit() )
                {
                    // mark the moon for removal...
                    calculations.executionList.add( satellite );

                    // now add all the children of the moon...
                    // ----------------------------------------
                    List<Particle> children = satellite.disintegrate( 100, matrix );
                    for( Particle moonlet : children )
                    {
                        calculations.birthList.add( moonlet );
                    }
                }
            }


        }
    }
    //To change body of implemented methods use File | Settings | File Templates.
}

/**
 * Now actually cause each particle's location and velocity to be modified....
 *
 * @param data
 * @param constraints
 */
public void updateParticles( Model data, ModelConstraints constraints, WorkingData calculations )
{
    // take out those objects for the chop.....
    // -----------------------------------------
    for( Particle p : calculations.executionList )
    {
        data.particleList().remove( p );
    }

    // add those new bodies which have just been created.
    // ---------------------------------------------------
    for( Particle p : calculations.birthList )
    {
        data.particleList().add( p );
    }

    // update the position of all the bodies..
    // ----------------------------------------
    for( Particle p : data.particleList() )
    {
        p.update( constraints );
    }
}

}
