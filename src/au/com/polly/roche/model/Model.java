package au.com.polly.roche.model;

import java.util.List;

/**
 * Interface that any model of particles complies to. This can then be
 * fed to the cruncher, which will process the elements within the model. This
 * enables us to use different types of models, such as test rigs in the development phase,
 * or fully blown models generated by the UI.
 *
 *
 *  *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 *
 */
public interface Model
{


/**
 * used to set the particles to their initial state..
 */
public void initialise();

/**
 *
 * @return list of the particles in the current model
 */
public List<Particle> particleList();


/**
 *
 * @return whether this model has now finished.
 */
public boolean finished();

/**
 *
 * @return produce an exact replica of this model. This enables
 * this model to be worked on, whilst a listener can output a
 * static "replica"..
 */
public Object clone();

}
