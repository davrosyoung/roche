package au.com.polly.roche.model;

/**
 * @author dave
 *         created: May 30, 2009 5:33:07 PM
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
public class EarthSunModelConstraints implements ModelConstraints
{
    static Point bottomLeft;
    static Point topRight;

    static {
        bottomLeft = new Point( -( ModelConstants.AU * 1.10 ), -( ModelConstants.AU * 1.10 ) );
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
        return 86400 * 10;
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
