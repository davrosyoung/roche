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
public class EarthSunModel implements Model
{

private List<Particle> particles;

public EarthSunModel()
{
    particles = new ArrayList<Particle>();
}

public synchronized void initialise()
{
    Point sunLocation;
    Point earthLocation;
    Vector sunVelocity;
    Vector earthVelocity;
    Particle sun;
    Particle earth;


    sunLocation = new Point( 0.0, 0.0 );
    earthLocation = new Point( ModelConstants.AU, 0.0 );
    sunVelocity = new Vector( 0.0, 0.0 );
    earthVelocity = new Vector( 0.0, 29790 );

    sun = new Particle( "ra", sunLocation, sunVelocity, ModelConstants.SOLAR_MASS, 1326 );
    earth = new Particle( "terra", earthLocation, earthVelocity, ModelConstants.EARTH_MASS, 5515 );
//    children = earth.disintegrate( 100, matrix );
    particles.add( sun );

/*    for( Particle p : children )
    {
        particles.add( p );
    }
*/

    particles.add( earth );

}

public synchronized Object clone()
{
    Object result = new EarthSunModel();
    EarthSunModel dummy = (EarthSunModel)result;

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