package au.com.polly.roche.model;

import au.com.polly.roche.ui.ApplicationController;
import au.com.polly.roche.ui.ApplicationState;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * Provides infrastructure and code common to various flavours of crunchers.
 *
 *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 */
public abstract class BaseCruncher implements Cruncher
{
private final static int debug = 0;
protected HashMap<ModelListener,Object> listenerMap;
protected Model model;
protected ModelConstraints constraints;
protected long interval;
protected WorkingData calculations;
protected Thread runner = null;
protected ApplicationController controller;
protected DisintegrationMatrix matrix;


/**
 *
 * @param model the universe to be modelled
 * @param intervalMS how long between updates in milliseconds
 * @param constraints how the model universe translates into the depicted version..
 */
public BaseCruncher( Model model, ModelConstraints constraints, long intervalMS, ApplicationController controller )
{
    this.model = model;
    this.constraints = constraints;
    this.interval = intervalMS;
    this.controller = controller;

    this.listenerMap = new HashMap<ModelListener,Object>();

    this.matrix = new DisintegrationMatrix( 21 );

}

public void setModel( Model model )
{
    this.model = model;
}

public void setModelConstraints( ModelConstraints constraints )
{
    this.constraints = constraints;
}

public void addListener( ModelListener listener, Object auxilliary )
{
    if ( !listenerMap.containsKey( listener ) )
    {
        listenerMap.put( listener, auxilliary );
    } else {
        if ( debug > 0 )
        {
            System.out.println( "BaseCruncher::addListener(): LISTENER ALREADY REGISTRERD");
        }
        throw new IllegalArgumentException( "Listener already registered!!" );
    }
}

public void start()
{
    if ( runner == null )
    {
        if ( debug > 1 )
        {
            System.out.println( "BaseCruncher::start(): start()ing up with following model;" );
            System.out.println( "BaseCruncher::start(): -------------------------------------------------------------------------------" );
            System.out.println( model );
            System.out.println( "BaseCruncher::start(): -------------------------------------------------------------------------------" );
        }

        runner = new Thread( this );
        runner.start();
    }
}

public void stop()
{
    if ( controller.getApplicationState() != ApplicationState.RUNNING )
    {
        synchronized( this )
        {
            this.notify();
        }
        this.runner = null;
    } else {
        if( debug > 0 )
        {
            System.out.println( "BaseCruncher::stop(): Invoked when in state RUNNING!!" );
        }
    }
}

public void run()
{
    long lastUpdate = -1;
    Map<ParticlePair,Vector> calculated;
    Map<ParticlePair,Vector> delta;
    Map<ParticlePair,Integer> previous = new HashMap<ParticlePair,Integer>();
    WorkingData calculations;
    long now;
    long elapsed;
    long gate;
    Vector force;
    Model updated;
    Model copy;
    long gate2;

    model.initialise();

    do {

        // get ready for next iteration of the simulation...
        // ---------------------------------------------------
        calculated = new HashMap<ParticlePair,Vector>();
        delta = new HashMap<ParticlePair,Vector>();
        now = System.currentTimeMillis();

        if ( ( lastUpdate > 0 ) && ( ( elapsed = lastUpdate - now ) < interval ) )
        {
            synchronized( this )
            {
                try {
                    wait( interval - elapsed );
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        gate = System.currentTimeMillis();

        calculations = update( model, constraints, previous );
        previous = calculations.previousCollisions;

        // todo: gate .... cannot proceed until last updates given to listeners have finished....
        // ---------------------------------------------------------------------------

        gate2 = System.currentTimeMillis();

        // make a copy of the model data for the listeners to process, since we may be
        // in the process of updating this before these listeners are all finished...
        // ------------------------------------------------------------------------------
        copy = (Model) model.clone();


        // now update any listeners ... for the moment do this "in-line"
        // todo: perform this in a pool of waiting threads ...but do not
        // call a listener until they have finished processing
        // any previous update...
        // -----------------------------------------------------
        for( ModelListener listener : listenerMap.keySet() )
        {
            Object aux = listenerMap.get( listener );
            assert( copy != null );
            assert( constraints != null );
            assert( listener != null );
            listener.update( copy, constraints, aux );
        }

        lastUpdate = System.currentTimeMillis();

        elapsed = lastUpdate - gate;
        if ( debug > 0 )
        {
            System.out.println( "BaseCruncher::run(): Iteration took " + elapsed + "ms. It took " + ( lastUpdate - gate2 ) + " for listeners to execute." );
        }

        // give up on this animation if we are reduced to only having a single body...
        // -----------------------------------------------------------------------------
        if ( model.particleList().size() < 2 )
        {
            controller.resetAnimation();
        }

    } while( controller.getApplicationState() == ApplicationState.RUNNING );
}


/**
 * Carries out the steps to cause the model to be updated during this iteration...
 *
 *
 * @param data
 * @param constraints
 */
public WorkingData update( Model data, ModelConstraints constraints, Map<ParticlePair,Integer> previousCollisions )
{
    long a,b,c,d,e,f,g,h,i;

    if ( ( previousCollisions != null ) && ( previousCollisions.size() > 0 ) )
    {
//        System.out.println( "Update(): WE HAVE " + previousCollisions.size() + " previousCollisions" );
//        for( ParticlePair pair : previousCollisions.keySet() )
//        {
//            System.out.println( "Update(): previous between " + pair.getAlpha().getLabel() + " & " + pair.getBeta().getLabel() + " ... count=" + previousCollisions.get( pair ) );
//        }
    } else {
//        System.out.println( "Update(): WE HAVE ZERO previousCollisions" );

    }


    WorkingData calculations = new WorkingData( previousCollisions);

    a = System.currentTimeMillis();
    calculateDeltas( data, constraints, calculations );
    b = System.currentTimeMillis();
    calculateDistances( data, constraints, calculations );
    c = System.currentTimeMillis();
    checkForCollisions( data, constraints, calculations );
    d = System.currentTimeMillis();
    calculateGravitationalInteractions( data, constraints, calculations );
    e = System.currentTimeMillis();
    calculateCollisionImpacts( data, constraints, calculations );
    f = System.currentTimeMillis();
    checkTidalDisintegrations( data, constraints, calculations );
    g = System.currentTimeMillis();
    impartForces( data, constraints, calculations );
    h = System.currentTimeMillis();
    updateParticles( data, constraints, calculations );
    i = System.currentTimeMillis();

    b = System.currentTimeMillis();
    if ( debug > 0 )
    {
        if ( debug > 1 )
        {
            System.out.println( "BaseCruncher::update(): took " + ( i - a ) + "ms. a=" + ( b - a ) + "ms, b=" + ( c - b ) + "ms, c=" + ( d - c ) + "ms, d=" + ( e - d ) + "ms, e=" + ( f - e ) + "ms, f=" + ( g - f ) + "ms, g=" + ( h - g ) + "ms, h=" + ( i - h ) + "ms." ); 
        } else {
            System.out.println( "BaseCruncher::update(): took " + ( i - a ) + "ms." );
        }
        System.out.flush();
    }

    return calculations;
}


/**
 * calculates the difference in location between once particle and another...
 *
 * @param data
 * @param constraints
 * @param calculations
 */
abstract public void calculateDeltas( Model data, ModelConstraints constraints, WorkingData calculations );

/**
 * calculates the distance (in metres) between one particle and another ....
 *
 * @param data
 * @param constraints
 * @param calculations
 */
abstract public void calculateDistances( Model data, ModelConstraints constraints, WorkingData calculations );

/**
 * calculates the gravitation force to be imnparted from one particle to another....
 *
 * @param data
 * @param constraints
 * @param calculations
 */
abstract public void calculateGravitationalInteractions(
        Model data, ModelConstraints constraints,
        WorkingData calculations
);

/**
 *
 * @param data
 * @param constraints
 * @param calculations
 */
abstract public void checkForCollisions(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
);

/**
 *
 * @param data
 * @param constraints
 * @param calculations
 */
abstract public void calculateCollisionImpacts(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
);

/**
 *
 * @param data
 * @param contraints
 * @param calculations
 */
abstract public void impartForces(
        Model data,
        ModelConstraints contraints,
        WorkingData calculations
);


abstract public void checkTidalDisintegrations(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
);

abstract public void updateParticles( Model data, ModelConstraints constraints, WorkingData calculations );

}