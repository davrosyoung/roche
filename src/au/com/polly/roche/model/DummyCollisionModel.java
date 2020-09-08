package au.com.polly.roche.model;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy model to mimic the earth going around the sun... see how it fairs, with some lunar sized debris
 * throw in for good measure. Implemented to see how the simulation works. Not intended for use with
 * the final simulation.
 *
 *  *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 */
public class DummyCollisionModel implements Model
{


private List<Particle> particles;

public DummyCollisionModel()
{
    particles = new ArrayList<Particle>();
}

public synchronized void initialise()
{
    Point aLocation;
    Point bLocation;
    Vector aVelocity;
    Vector bVelocity;

    Particle a;
    Particle b;

    aLocation = new Point( 0.0, 0.0 );
    bLocation = new Point( 50.0, 50.0 );
    aVelocity = new Vector( 10.0, 10.0 );
    bVelocity = new Vector( -10.0, -10.0 );

    a = new Particle( "alpha", aLocation, aVelocity, 100, 1 );
    b = new Particle( "beta", bLocation, bVelocity, 500, 1 );

    System.out.println( "alpha.radius=" + a.getRadius() );
    System.out.println( "beta.radius=" + b.getRadius() );
    particles.add( a );
    particles.add( b );

}

public synchronized Object clone()
{
    Object result = new DummyCollisionModel();
    DummyCollisionModel dummy = (DummyCollisionModel)result;

    for( Particle particle : particles )
    {
        dummy.particles.add( (Particle)particle.clone() );
    }

    return result;
}


public List<Particle> particleList()
{
    return particles;
}

public boolean finished()
{
    return false;  //To change body of implemented methods use File | Settings | File Templates.
}

}