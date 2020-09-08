package au.com.polly.roche.model;

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
public class UIDerivedModel implements Model
{

private List<Particle> particles;

public UIDerivedModel()
{
    particles = new ArrayList<Particle>();
}
    

public void addParticle( Particle p )
{
    particles.add( p );
}

public void initialise()
{
    
}


public synchronized Object clone()
{
    Object result = new UIDerivedModel();
    UIDerivedModel dummy = (UIDerivedModel)result;

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

public String toString()
{
    StringBuffer out = new StringBuffer();
    if ( particles != null )
    {
        out.append( "number bodies: " );
        out.append( particles.size() );
        out.append( "\n" );
        if ( ! particles.isEmpty() )
        {
            for( Particle particle : particles )
            {
                out.append( particle );
                out.append( "\n" );
            }
        }
    } else {
        out.append( "NO particle list." );
    }

    return out.toString();

}

}