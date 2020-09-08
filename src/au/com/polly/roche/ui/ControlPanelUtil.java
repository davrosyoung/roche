package au.com.polly.roche.ui;

/**
 *
 * Utilities to map between control settings and model values. Isolated into separate utility class
 * to enable simpler unit testing.
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
public class ControlPanelUtil
{

public static int getSatelliteMassSliderPosition( double mass )
{
    return -1;
}


public static double getSatelliteMassInEarthMasses( int position )
{
    return -1.0;
}

public static double getSatelliteMass( int position )
{
    return -1.0;
}

public static int getPlanetMassSliderPosition( double mass )
{
    return -1;
}

public static double getPlanetMassInEarthMasses( int position )
{
    return -1.0;
}

public static double getPlanetMass( int position )
{
    return -1.0;
}


public static int getOrbitalRadiusSliderPosition( double radius )
{
    return -1;
}

public static  double getOrbitalRadius( int position )
{
    return 1.0;
}
    

}
