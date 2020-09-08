package au.com.polly.roche.ui;

import au.com.polly.roche.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

/**
 * Top level of the user interface, creates the various swing UI components, the
 * pane on which the simulation is displayed, the model itself, and then sets them
 * off in motion.
 *
 *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 */
public class Top extends JFrame implements ActionListener, ApplicationController
{
private final static int debug = 0;
private AnimationCanvas canvas;
private au.com.polly.roche.ui.CountdownPanel countdownPanel;
private ControlPanel controlPanel;
private boolean fini = false;
private long interval = 200;
Model model;
ModelConstraints constraints;
ModelListener listener;
SingleThreadedCruncher cruncher;
ApplicationState state = ApplicationState.COUNTDOWN;
long countdownPeriod;
long countdownStarted;
long animationStartedStamp;


private final static long MINUTE = 60;
private final static long HOUR = MINUTE * 60;
private final static long DAY = HOUR * 24;
private final static long YEAR = DAY * 365;

private static NumberFormat two;
private static NumberFormat three;

static {
    two = NumberFormat.getIntegerInstance();
    two.setMinimumIntegerDigits( 2 );
    two.setMaximumIntegerDigits( 2 );

    three = NumberFormat.getIntegerInstance();
    three.setMinimumIntegerDigits( 3 );
    three.setMaximumIntegerDigits( 3 );

}


GraphicsConfiguration gc;

Timer timer;


public Top()
{
    super( "Roche Limit Simulation" );

    countdownStarted = System.currentTimeMillis();
    countdownPeriod = 30000L;

    ModelSettings modelSettings = PresetModelSetting.getRandomModelSettings();


    setSize( 700, 600 );

    listener = new TextModelListener();

    timer = new Timer( 1000, this );


    countdownPanel = new CountdownPanel( this, timer );
    countdownPanel.setVisible( true );
    controlPanel = new ControlPanel( this );
    controlPanel.initialiseControls( modelSettings );


    model = controlPanel.getModel();


    cruncher = new SingleThreadedCruncher( model, constraints, interval, this  );
//        cruncher.addListener(  listener, null );

//        cruncher.addListener( listener, null );

    constraints = modelSettings.getModelConstraints( controlPanel );
    interval = constraints.getInterval();
    canvas = new au.com.polly.roche.ui.AnimationCanvas( constraints );
    canvas.update( controlPanel.getModel(), constraints, null );
    cruncher.addListener( canvas, null );

    getContentPane().add( countdownPanel, BorderLayout.NORTH );
    getContentPane().add( canvas, BorderLayout.CENTER );
    getContentPane().add( controlPanel, BorderLayout.SOUTH );


    timer.addActionListener( countdownPanel );
    timer.start();
}




public static void main( String[] argv )
{
    Top top = new Top();

    top.pack();
    top.setVisible( true );
    top.show();
/*
    try {
        top.animate();
    } catch (InterruptedException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
*/

    // we don't want to exit!!
    while( top.getApplicationState() != ApplicationState.TERMINATED )
    {
        try {
            synchronized( top )
            {
                top.wait( 10000L );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}


public void actionPerformed( ActionEvent event )
{
    if ( event.getSource() == timer )
    {
        if ( debug > 3 )
        {
            System.out.println( "actionPerformed(): timer event" );
        }
    }
    if ( debug > 3 )
    {
        System.out.println( "Top::actionPerformed(): method invoked!!" );
    }
    //To change body of implemented methods use File | Settings | File Templates.

}

public void startAnimation()
{

    animationStartedStamp = System.currentTimeMillis();
    state = ApplicationState.RUNNING;
    if ( debug > 0 )
    {
        System.out.println( "Top::animate(): changing application state to " + state + ", animationStartedStamp=" + animationStartedStamp );
    }
    cruncher.setModel( controlPanel.getModel() );
    cruncher.setModelConstraints( controlPanel.getModelConstraints() );
    cruncher.start();
    controlPanel.freezeControlPanel();
}

public void pauseAnimation() {

}

public void resetAnimation()
{
    cruncher.stop();


    state = ApplicationState.COUNTDOWN;
    cruncher.stop();
    controlPanel.enableControlPanel();

    ModelSettings modelSettings = PresetModelSetting.getRandomModelSettings();

    controlPanel.initialiseControls( modelSettings );
    constraints = modelSettings.getModelConstraints( controlPanel );
    cruncher.setModel( controlPanel.getModel() );
    cruncher.setModelConstraints( controlPanel.getModelConstraints() );
    canvas.update( controlPanel.getModel(), constraints, null );

    resetCountdown();
}

/**
 *
 * @return how long this animation has been running in milliseconds
 */
public long getAnimationElapsedTime()
{
    long now = System.currentTimeMillis();
    long elapsed = now - animationStartedStamp;
//  System.out.println( "Top::getAnimationElapsedTime(): elapsed=" + elapsed + "ms." );
    return elapsed;
}

/**
 *
 * @return how long this animation has been running in "model" time, in seconds..
 */
public long getAnimationModelElapsedTime()
{
    long seconds = (long) ( ( getAnimationElapsedTime() * constraints.getTimeScaleFactor() ) / 1000 );
//  System.out.println( "Top::getAnimationModelElapsedTime(): returning with result=" + seconds + " seconds." );
    return seconds;
}

public String getAnimationModelFormattedElapsedTime( String template )
{
    long elapsed = getAnimationModelElapsedTime();
    long years = 0;
    long days = 0;
    long hours = 0;
    long minutes = 0;
    long seconds = 0;
    long remainder = elapsed;

    long totalSeconds;
    long totalMinutes;
    long totalHours;
    long totalDays;
    String result = template;

    if ( elapsed > YEAR )
    {
        years = elapsed / YEAR;
        remainder -= ( years * YEAR );
    }

    totalDays = elapsed / DAY;
    if ( elapsed > DAY )
    {
        days = remainder / DAY;
        remainder -= ( days * DAY );
    }

    totalHours = elapsed / HOUR;
    if ( elapsed > HOUR )
    {
        hours = remainder / HOUR;
        remainder -= ( hours * HOUR );
    }

    totalMinutes = elapsed / MINUTE;
    if ( elapsed > MINUTE )
    {
        minutes = remainder / MINUTE;
        remainder -= ( minutes * MINUTE );
    }

    seconds = remainder;
    totalSeconds = elapsed;

    result = result.replaceAll( "%YYY", three.format( years ) );
    result = result.replaceAll( "%YY", two.format( years ) );
    result = result.replaceAll( "%Y", Long.toString( years ) );

    result = result.replaceAll( "%yyy", three.format( years ) );
    result = result.replaceAll( "%yy", two.format( years ) );
    result = result.replaceAll( "%y", Long.toString( years ) );


    result = result.replaceAll( "%ddd", three.format( days ) );
    result = result.replaceAll( "%dd", two.format( days ) );
    result = result.replaceAll( "%d", Long.toString( days ) );

    result = result.replaceAll( "%DDD", three.format( totalDays ) );
    result = result.replaceAll( "%DD", two.format( totalDays ) );
    result = result.replaceAll( "%D", Long.toString( totalDays ) );


    result = result.replaceAll( "%HHH", three.format( totalHours ) );
    result = result.replaceAll( "%HH", two.format( totalHours ) );
    result = result.replaceAll( "%H", Long.toString( totalHours ) );


    result = result.replaceAll( "%hhh", three.format( hours ) );
    result = result.replaceAll( "%hh", two.format( hours ) );
    result = result.replaceAll( "%h", Long.toString( hours ) );


    result = result.replaceAll( "%mmm", three.format( minutes ) );
    result = result.replaceAll( "%mm", two.format( minutes ) );
    result = result.replaceAll( "%m", Long.toString( minutes ) );

    result = result.replaceAll( "%MMM", three.format( totalMinutes ) );
    result = result.replaceAll( "%MM", two.format( totalMinutes ) );
    result = result.replaceAll( "%M", Long.toString( totalMinutes ) );


    result = result.replaceAll( "%sss", three.format( seconds ) );
    result = result.replaceAll( "%ss", two.format( seconds ) );
    result = result.replaceAll( "%s", Long.toString( seconds ) );

    result = result.replaceAll( "%SSS", three.format( totalSeconds ) );
    result = result.replaceAll( "%SS", two.format( totalSeconds ) );
    result = result.replaceAll( "%S", Long.toString( totalSeconds ) );

//  System.out.println( "Top::getAnimationModelFormattedElapsedTime(): invoked with template=[" + template + "], elapsed=" + elapsed + ", result=[" + result + "]" );


    return result;
}

public long getCountdownRemaining()
{
    long now = System.currentTimeMillis();
    long elapsed = now - countdownStarted;
    long remaining = countdownPeriod < elapsed ? 0 : countdownPeriod - elapsed;

    return remaining;
}

public ApplicationState getApplicationState()
{
    return state;
}

public void refreshAnimation( Model model, ModelConstraints constraints )
{
    if ( canvas != null )
    {
        canvas.update( model, constraints, null );
    }
}

public void resetCountdown()
{
    countdownPeriod = 30000;
    countdownStarted = System.currentTimeMillis();
}

}
