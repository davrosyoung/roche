package au.com.polly.roche.ui;

/**
 *x,y coordinates in the graphical world. Happy for these coordinates to be between 0 & 65535.
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
 */
public class Coordinate
{
    int x;
    int y;

    public Coordinate( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    public Object clone()
    {
        Coordinate result = new Coordinate( x, y );
        return result;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    /**
     *
     * hashcode is based entired upon the coordindates. two coordinates of the same location have
     * the same hash code.
     *
     * @return
     */
    public int hashCode()
    {
        int code = ( y * 65536 ) + x;
        return code;
    }

    public boolean equals( Object other )
    {
        boolean result = false;

        do {

            if ( other == null )
            {
                break;
            }

            if ( ! ( other instanceof Coordinate ) )
            {
                break;
            }

            Coordinate c = (Coordinate)other;

            result = ( x == c.x ) && ( y == c.y );

        } while( false );

        return result;
    }
}
