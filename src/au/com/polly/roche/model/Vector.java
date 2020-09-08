package au.com.polly.roche.model;

import au.com.polly.roche.util.HashCodeUtil;

/**
 * Represents a two dimensional floating point vector.
 * It is NOT immutable. Many of the methods on this class will modify
 * the object they are invoked upon.
 *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 * 
 */
public class Vector implements Opposable<Vector>
{
double x;
double y;

private final double ALLOWABLE_ERROR = 1E-12;

/**
 * Creates a new vector
 *
 * @param x x-coordinate in the cartesian plane
 * @param y y-coordinate in the cartesian plane
 */
public Vector( double x, double y )
{
    this.x = x;
    this.y = y;
}

/**
 *
 * @return scalar value of this vector.
 */
public double scalar()
{
    double result = Math.sqrt( ( x * x ) + ( y * y ) );
    return result;
}


/**
 *
 * @return angle (in radians) that this vector points in.
 */
public double getDirection()
{
    double result = Math.atan2( y, x );
    return result;
}

/**
 *
 * @return an identical (yet evil!!) twin of this vector.
 */
public Object clone()
{
    Object result = new Vector( x, y );
    return result;
}

/**
 * GENERATES a new vector object.
 *
 * @param other vector to be removed from this vector
 * @return resultant vector.
 */
public synchronized Vector getDelta( Vector other )
{
    Vector result;
    result = new Vector( this.getX() - other.getX(), this.getY() - other.getY() );
    return result;
}


/**
 * MODIFIES this vector, returns this vector.
 *
 * @param other vector to be added to this vector
 * @return modified vector added with argument.
 */
public synchronized Vector add( Vector other )
{
    this.x += other.x;
    this.y += other.y;
    return this;
}

/**
 * MODIFIES this vector.
 *
 * @param amount
 * @return
 */
public synchronized Vector divide( double amount )
{
    Vector result;

    if ( Math.abs( amount ) < ALLOWABLE_ERROR )
    {
        throw new ArithmeticException( "Division by zero!!" );
    }

    this.x = this.x / amount;
    this.y = this.y / amount;
    return this;
}

/**
 * MODIFIES this vector
 *
 * @param multiplier
 * @return
 */
public synchronized Vector multiply( double multiplier )
{
    this.x *= multiplier;
    this.y *= multiplier;
    return this;
}

/**
 * does NOT modify this vector, returns a new vector.
 *
 * @return a new vector, pointing in the opposite direction to this one.
 */
public Vector getOpposite()
{
    double nx;
    double ny;

    // this prevents rounding errors when vector values are
    // at or near zero...
    // -----------------------------------------------------
    nx = ( Math.abs( x ) < ALLOWABLE_ERROR ) ? 0 : -x;
    ny = ( Math.abs( y ) < ALLOWABLE_ERROR ) ? 0 : -y;
    Vector result = new Vector( nx, ny );
    return result;
}


/**
 *
 * @param other  vector to compare this vector to.
 * @return whether this vector is equal to another vector. To avoid confusion with
 * floating point error, a small margin of error is allowed.
 */
@Override public boolean equals( Object other )
{
    boolean result = false;
    Vector o;

    if ( other instanceof Vector )
    {
        o = (Vector)other;
        result = ( Math.abs( o.x - x ) < ALLOWABLE_ERROR );
        if ( result )
        {
            result = ( Math.abs( o.y - y ) < ALLOWABLE_ERROR );
        }
    }

    return result;
}

/**
 * overriden, such that equality, and hashcode is based upon the coordinates
 * of the vector.
 *
 * @return
 */
@Override public int hashCode()
{
    int result = HashCodeUtil.SEED;
    result = HashCodeUtil.hash( result, x );
    result = HashCodeUtil.hash( result, y );
    return result;
}


public String toString()
{
    String result = "<" + x + ", " + y + ">";
    return result;
}

public double getX()
{
    return this.x;
}

public double getY()
{
    return this.y;
}


}
