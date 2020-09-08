package au.com.polly.roche.model;

/**
 * Constraints for the dummy model with sun & earth..
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
public class DisintegrationModelConstraints implements ModelConstraints
{
    static Point bottomLeft;
    static Point topRight;

    static {
        bottomLeft = new Point( -60000000.0, -60000000.0 );
        topRight = new Point( 60000000.0, 60000000.0 );
    }

    /**
     * Each second depicts one hour
     *
     * @return
     */
    public double getTimeScaleFactor()
    {
        return 30;
    }

    public long getInterval()
    {
        return 400;
    }

    public Point getBottomLeftCorner() {
        return bottomLeft;
    }

    public Point getTopRightCorner()
    {
        return topRight;
    }
}