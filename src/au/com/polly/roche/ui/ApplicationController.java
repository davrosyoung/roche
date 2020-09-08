package au.com.polly.roche.ui;

import au.com.polly.roche.model.Model;
import au.com.polly.roche.model.ModelConstraints;

/**
 * Provides an interface which compontents can use to obtain the current application state, and
 * also to enable changes to it.
 *
 *
 * @author dave
 *         created: May 31, 2009 12:08:37 AM
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
public interface ApplicationController
{

/**
 * causes the animation sequence to begin.
 */
public void startAnimation();


/**
 * causes the animation sequence to be paused
 */
public void pauseAnimation();

/**
 * causes the animation sequence to be stopped.
 */
public void resetAnimation();

/**
 * redisplays the animation upon the animation canvas.
 *
 * @param model
 * @param constraints
 */
public void refreshAnimation( Model model, ModelConstraints constraints );

/**
 * number of milliseconds since the animation started
 *
 * @return
 */
public long getAnimationElapsedTime();

/**
 * follwing substitutions supported,
 * %yy - years
 * %ddd - days
 * %hh - hours
 * %mm - minutes
 * %ss - seconds
 *
 * @return formatted time since animation started
 */
public String getAnimationModelFormattedElapsedTime( String template );

/**
 * @return seconds since animation started
 */
public long getAnimationModelElapsedTime();

/**
 * If we are in the countdown state, how long until the animation commences??
 * 
 * @return
 */
public long getCountdownRemaining();

/**
 * Causes the countdown to be reset to thirty seconds.
 */
public void resetCountdown();

/**
 * Which state that the application currently finds itself in, currently;
 * RUNNING and COUNTDOWN are the two states used.
 *
 * @return
 */
public ApplicationState getApplicationState();
}
