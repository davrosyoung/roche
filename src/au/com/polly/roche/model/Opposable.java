package au.com.polly.roche.model;

/**
 * Implemented by any object which can have a matching opposite, or mirror partner.
 *
 *
 * @author dave
 *         created: May 30, 2009 1:08:05 PM
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
public interface Opposable<T>
{
    public T getOpposite();
}
