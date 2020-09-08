package au.com.polly.roche.model;

/**
 * Describes a location. All units are in metres.
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
public class Point extends Vector
{
public Point( double x, double y )
{
    super( x, y );
}


/**
 *
 * @param other
 * @return scalar distance between this point and another.
 */
double distance( Vector other )
{
    Vector delta = (Vector) this.clone();
    delta.getDelta( other );
    double result = delta.scalar();
    return result;
}

/**
 *
 * @return have to override clone(), so that we get a Point and
 * not just a vector!!
 */
public Object clone()
{
    Object result = new Point( x, y );
    return result;
}


}
