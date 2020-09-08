package au.com.polly.roche.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data used by crunchers when calculating how to update the simulation model.
 *
 * @author dave
 *         created: May 30, 2009 4:47:34 PM
 *         <p/>
 *         <p/>
 *         &copy; Copyright Polly Enterprises Pty Ltd 2009
 *         <p/>
 *         <p>
 *         Intended for demonstration purposes only, not intended for
 *         use within a production environment.
 *         Free for distribution for non-commercial use, as long as this notice
 *         is displayed in the resultant work. No warranty, implicit or explicit
 *         is provided by the use, in any manner, of this programme code, or applications of
 *         any kind utilizing it.
 *         </p>
 */
public class WorkingData
{
    protected ParticlePairTable<Vector> deltaTable;
    protected ParticlePairTable<Double> distanceTable;
    protected ParticlePairTable<Vector> gForceTable;
    protected List<ParticlePair> collisions;
    protected Map<ParticlePair,Integer> previousCollisions;
    protected Map<ParticlePair,Vector> collisionForces;
    protected List<Particle>            executionList; // these particles are for the chop..
    protected List<Particle>            birthList;      // these particles are to be added to the model...


public WorkingData( Map<ParticlePair,Integer> previousCollisions)
{
    deltaTable = new ParticlePairTable<Vector>();
    distanceTable = new ParticlePairTable<Double>();
    gForceTable = new ParticlePairTable<Vector>();
    collisions = new ArrayList<ParticlePair>();
    if ( previousCollisions != null )
    {
        this.previousCollisions = previousCollisions;
    } else {
        this.previousCollisions = new HashMap<ParticlePair,Integer>();        
    }
    collisionForces = new HashMap<ParticlePair,Vector>();
    executionList = new ArrayList<Particle>();
    birthList = new ArrayList<Particle>();
}
}
