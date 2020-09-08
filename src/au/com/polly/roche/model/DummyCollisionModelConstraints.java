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
public class DummyCollisionModelConstraints implements ModelConstraints
{
    static Point bottomLeft;
    static Point topRight;

    static {
        bottomLeft = new Point( -200, -200 );
        Vector v = bottomLeft.getOpposite();
        topRight = new Point( v.x, v.y );
    }

    /**
     * There are 86400 seconds in a day, so speed up time such that a day passes in a second
     *
     * @return
     */
    public double getTimeScaleFactor()
    {
        return 1;
    }

    public long getInterval()
    {
        return 200;
    }

    public Point getBottomLeftCorner() {
        return bottomLeft;
    }

    public Point getTopRightCorner()
    {
        return topRight;
    }
}