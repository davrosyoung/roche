package au.com.polly.roche.model;

/**
 * Jupiter and orbiting satellite within roche limit
 *
 * model 1,000,000 km across
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
public class SampleRocheModelConstraints implements ModelConstraints
{
    static Point bottomLeft;
    static Point topRight;

    static {
        bottomLeft = new Point( -300000000.0, -300000000.0 );
        topRight = new Point( 300000000.0, 300000000.0 );
    }

    /**
     * Each second depicts 3 mins
     *
     * @return
     */
    public double getTimeScaleFactor()
    {
        return 180;
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