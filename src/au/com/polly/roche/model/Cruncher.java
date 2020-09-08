package au.com.polly.roche.model;

import java.util.List;
import java.util.Map;

/**
 * This interface is implemented by a class, which is capable of processing and updating the
 * elements in a model, to deliver the required simulation. This interface enables us to start
 * with a simplified single threaded processor, but to subsitute either more complex multihreaded
 * processors, or even dummy processors, to test or illustrate a specific point.
 *
 *
 * @author dave
 *
 *  created: May 29, 2009 7:54:16 PM
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * <p>
 * For demonstration purposes only, not intended as production quality code.
 * Free for distribution for non-commercial use, as long as this notice
 * is displayed in the resultant work. No warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 * </p>
 */
public interface Cruncher extends Runnable
{
/**
 * once invoked, the cruncher is expected to carry out the simulation
 *
 * @throws InterruptedException
 */
public void start();

/**
 *
 * @param model model for the cruncher to use...
 */
public void setModel( Model model );

/**
 *
 * @param constraints model constraints to be used.
 */
public void setModelConstraints( ModelConstraints constraints );

/**
 * conducts a single iteration of the simulation.
 *
 *
 * @param model
 * @param constraints
 * @param previousCollisions - particle pairs which collided last iteration, do not catch collisions between these
 * pairs of particles this iteration.
 * @return
 */
public WorkingData update( Model model, ModelConstraints constraints, Map<ParticlePair,Integer> previousCollisions  );

/**
 * Enables a listener to be added to the processor, which will be invoked
 * to represent any updates which may have been carried out upon the model.
 *
 * @param listener
 * @param auxilliary
 */
public void addListener( ModelListener listener, Object auxilliary );


/**
 * calculates the difference in location between once particle and another...
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void calculateDeltas( Model data, ModelConstraints constraints, WorkingData calculations );

/**
 * calculates the distance (in metres) between one particle and another ....
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void calculateDistances( Model data, ModelConstraints constraints, WorkingData calculations );

/**
 * calculates the gravitation force to be imnparted from one particle to another....
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void calculateGravitationalInteractions(
        Model data, ModelConstraints constraints,
        WorkingData calculations
);

/**
 *
 * @param data
 * @param constraints
 * @param calculations
 */
public void checkForCollisions(
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
public void calculateCollisionImpacts(
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
public void impartForces(
        Model data,
        ModelConstraints contraints,
        WorkingData calculations
);


public void checkTidalDisintegrations(
        Model data,
        ModelConstraints constraints,
        WorkingData calculations
);

public void updateParticles( Model data, ModelConstraints constraints, WorkingData calculations );


}
