package au.com.polly.roche.model;

/**
 * Constraints providing a means of translating the model universe
 * in metric coordinates to the displayed version of the model.. implemented
 * as an interface to enable dummy/test values to be deployed in the early development
 * phase, to be replaced by more sophisticated implementations later on.
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
public interface ModelConstraints
{

/**
 *
 * @return how many seconds in the model elapse for each single second
 * of elapsed "real" time?? A value less than one "slow down" time, whereas
 * a value larger than one (a value of 3600 is envisaged) causes a large amount
 * of elapsed time to be sped up into a shorter amount of displayed time.
 */
public double getTimeScaleFactor();


/**
 * How many milliseconds between simulation updates?
 *
 * @return
 */
public long getInterval();

/**
 *
 * @return the point representing the bottom left corner of the
 * visible portion of the model universe.
 */
public Point getBottomLeftCorner();


/**
 *
 * @return the point representing the top right corner of the
 * visible portion of the model universe.
 */
public Point getTopRightCorner();
}
