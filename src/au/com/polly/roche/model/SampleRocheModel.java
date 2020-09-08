package au.com.polly.roche.model;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * Dummy model demonstrating a solid body travelling at a decent orbit, breaking up
 * inside the roche limit....
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
public class SampleRocheModel implements Model, ModelConstants
{

private List<Particle> particles;

public SampleRocheModel()
{
    particles = new ArrayList<Particle>();
}

public synchronized void initialise()
{
    Particle planet;
    Particle moon;
    DisintegrationMatrix matrix = new DisintegrationMatrix( 30 );

    Point planetLocation = new Point( 0.0, 0.0 );
    Vector planetVelocity = new Vector( -100.0, 0.0 );

    Point satelliteLocation = new Point( 0.0, 200000000.0 );  // 200,000km
    Vector satelliteVelocity = new Vector( 24500.0, 0.0 ); // 24 kms-1

    planet = new Particle( "Jupiter", planetLocation, planetVelocity, JUPITER_MASS, JUPITER_DENSITY, 1.0, Color.ORANGE );
    moon = new Particle( "Moon", satelliteLocation, satelliteVelocity, EARTH_MASS * 2, 10000, 1.0, Color.GRAY );

    particles.add( planet );

    List<Particle> children = moon.disintegrate( 20, matrix );
    for( Particle p : children )
    {
        particles.add( p );
    }

    /*
    children = earth.disintegrate( 100, matrix );

    for( Particle p : children )
    {
        particles.add( p );
    }
    */

//    particles.add( earth );

//    particles.add( new Particle( "rock1", new Point( ModelConstants.AU * 0.8, ModelConstants.AU * 0.1 ), new Vector( 0.0, 15000 ), ModelConstants.EARTH_MASS * 0.1, 5000  ) );
//    particles.add( new Particle( "rock2", new Point( ModelConstants.AU * 0.7, ModelConstants.AU * 0.2 ), new Vector( 0.0, 15000 ), ModelConstants.EARTH_MASS * 0.1, 5000  ) );
//    particles.add( new Particle( "rock3", new Point( -ModelConstants.AU , 0.0 ), new Vector( 0.0, -10000 ), ModelConstants.EARTH_MASS * 0.1, 5000  ) );
//    particles.add( new Particle( "rock4", new Point( 0, ModelConstants.AU ), new Vector( -20000, 0 ), ModelConstants.EARTH_MASS * 0.1, 5000  ) );
//    particles.add( new Particle( "rock5", venusLocation, venusVelocity, ModelConstants.EARTH_MASS * 0.1, 5000  ) );
//    particles.add( new Particle( "rock6", new Point( ModelConstants.AU * 0.5, ModelConstants.AU * 0.1 ), new Vector( 0.0, 24000 ), ModelConstants.EARTH_MASS * 0.1, 5000  ) );
//    particles.add( new Particle( "rock7", new Point( ModelConstants.AU * 0.6, ModelConstants.AU * 0.1 ), new Vector( 0.0, 23000 ), ModelConstants.EARTH_MASS * 0.1, 5000  ) );
//    particles.add( new Particle( "rock8", new Point( ModelConstants.AU * 0.7, ModelConstants.AU * 0.1 ), new Vector( 0.0, 22000 ), ModelConstants.EARTH_MASS * 0.1, 5000  ) );

//    for( int i = 0; i < 360; i+= 1 )
//    {
//        double theta = ( (double)i / 1440.0 ) * 2.0 * Math.PI;
//        double epsilon = theta + ( Math.PI / 2.0 );
//        Point location = new Point( Math.cos( theta ) * ModelConstants.AU * 0.5, Math.sin( theta ) * ModelConstants.AU * 0.5 );
//        Vector velocity = new Vector( Math.cos( epsilon ) * 43000.0, Math.sin( epsilon ) * 43000.0 );
//        String label = "debris-" + i;
//        particles.add( new Particle( label, location, velocity, ModelConstants.EARTH_MASS * 0.01, 5000 ) );
//    }
}

public synchronized Object clone()
{
    Object result = new SampleRocheModel();
    SampleRocheModel dummy = (SampleRocheModel)result;

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