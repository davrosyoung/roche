package au.com.polly.roche.model;

/**
 * If you want to be notified when the model is updated,
 * implement this interface!!!
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
public interface ModelListener
{

/**
 *
 * @param model the model which has just been updated.
 * @param auxilliary any additional object which the listener registered
 * when it added itself as a listener...
 */
public void update( Model model, ModelConstraints constraints, Object auxilliary );
    
}
