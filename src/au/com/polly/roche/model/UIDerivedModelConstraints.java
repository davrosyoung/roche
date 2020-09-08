package au.com.polly.roche.model;

import au.com.polly.roche.ui.ModelSettings;
import au.com.polly.roche.ui.ControlPanel;

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
public class UIDerivedModelConstraints implements ModelConstraints
{
private final static int debug = 0;
private Point bottomLeft;
private Point topRight;
private long timeScaleFactor;
ControlPanel controlPanel;

public UIDerivedModelConstraints( ModelSettings settings, ControlPanel controlPanel )
{
    this( settings.calculateCircularOrbitalSpeed(), settings.getOrbitalRadius(), settings.calculateRocheLimit(), settings.getPlanetRadius(),  controlPanel );
}

public UIDerivedModelConstraints( double orbitalSpeed, double orbitalRadius, double rocheLimit, double planetRadius, ControlPanel controlPanel )
{
    // ok ... now determine the period of the orbit at the roche limit....
    // ------------------------------------------------------------------
    double circumference = 2 * Math.PI * rocheLimit;
    long period = (long)Math.round( circumference / orbitalSpeed );

    if ( debug > 4 )
    {
        System.out.println( "UIDerivedModelConstraints::constructor(): orbitalSpeed=" + orbitalSpeed + "ms-1, orbitalRadius=" + ( orbitalRadius / 1000.0 ) + "km, rocheLimit=" + ( rocheLimit / 1000.0 ) + "km, planetRadius=" + ( planetRadius / 1000.0 ) + "km, orbit circumference=" + ( circumference / 1000.0 )  + "km, orbital period=" + ( period / 3600.0 ) + "hours" );
    }

    this.controlPanel = controlPanel;

    // we want a roche limit orbit to take approximately two minutes...
    // -----------------------------------------------------------------
    timeScaleFactor = period / 60;

    // work out the measurement that we wish to scale the animation against ....
    // ... orbital radius
    // ... roche limit
    // ... planetary radius
    // -------------------------------------------------------------------
    double yardStick = planetRadius;
    if ( rocheLimit > yardStick )
    {
        yardStick = rocheLimit;
    }

    if ( orbitalRadius > yardStick )
    {
        yardStick = orbitalRadius;
    }


    bottomLeft = new Point( -yardStick * 2, -yardStick * 2 );
    topRight = new Point( yardStick * 2, yardStick * 2 );

    if ( debug > 4 )
    {
        System.out.println( "UIDerivedModelConstraints::constructor(): timeScaleFactor=" + timeScaleFactor + ", yardStick=" + ( yardStick / 1000.0 ) + "km." );
        System.out.println( "UIDerivedModelConstraints::constructor(): bottomLeft=" + bottomLeft + ", topRight=" + topRight );
    }

}


/**
 * There are 86400 seconds in a day, so speed up time such that a day passes in a second
 *
 * @return
 */
public double getTimeScaleFactor()
{
    return timeScaleFactor;
}

public long getInterval()
{
    return 50;
}

public Point getBottomLeftCorner() {
    return bottomLeft;
}

public Point getTopRightCorner()
{
    return topRight;
}
}