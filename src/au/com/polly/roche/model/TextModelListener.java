package au.com.polly.roche.model;

import java.util.Date;

/**
 * Simple model listener which just outputs coordinates to text.
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
public class TextModelListener implements ModelListener
{

public void update(Model model, ModelConstraints constraints, Object auxilliary)
{
    Date now = new java.util.Date();

    System.out.println( "-----------------------< UPDATE " + now + ">-----------------------" );
    System.out.println( " label        |          x           |              y             |");
    for( Particle particle : model.particleList() )
    {
        System.out.println( particle.getLabel() + " | " + particle.getLocation() + ",   v=" + particle.getVelocity() );
    }

}

}
