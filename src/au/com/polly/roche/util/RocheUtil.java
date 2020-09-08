package au.com.polly.roche.util;

import au.com.polly.roche.model.ModelConstants;

/**
 *
 * Useful calculations which we did not want to spread amongst the other classes, so placed into this
 * utility class instead.
 *
 * @Author Dave Young
 *
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 *
 */
public class RocheUtil
{
private final static int debug = 0;
private final static double fourPiSquared = 4.0 * Math.PI * Math.PI;


/**
 *
 *
 * @param mass mass (in kg) of the sphere
 * @param density desity (in m / kg3 ) of the sphere
 * @return the radius of the sphere with the supplied mass and density
 */
public static double calculateRadiusFromMassAndDensity( double mass, double density )
{
    double result;
    double volume;
    volume = mass / density;
    result = calculateRadiusFromVolume( volume );
    return result;
}


/**
 *
 * @param volume how much space the sphere occupies (m3).
 * @return radius (in metres) of a sphere with supplied volume
 */
public static double calculateRadiusFromVolume( double volume )
{
    double result;

    result = Math.pow( volume / ( 1.33333333333 * Math.PI), 0.3333333333333333333 );

    return result;
}


/**
 *
 * @param planetRadius
 * @param planetDensity
 * @param satelliteDensity
 * @return how far from the planet's centre the roche limit is to be found.
 */
public static double calculateRocheLimit( double planetRadius, double planetDensity, double satelliteDensity )
{
    double densityRatio;
    double result;

    densityRatio = planetDensity / satelliteDensity;


//    wikipedia!!
//    result = planetRadius * Math.pow( 2 * densityRatio, 0.3333333333333333 );

    // roche limit as per lewis
    result = planetRadius * 2.52 * Math.pow( densityRatio, 0.33333333333333333333 );

    return result;
}


/**
 * Determine the speed that a satellite will require in order to have a circular orbit around a planet. The velocity
 * of the satellite will have to be tangential to the surface of the planet that it orbits.
 *
 *
 * @param planetMass mass of the planet
 * @param satelliteMass mass of the satellite
 * @param orbitalRadius how far above the centre of the planet (not it's surface!!) that the satellite will orbit.
 * @return  speed in ms-1 that the satellite will require in order to have a circular orbit.
 */
public static double calculateOrbitalSpeed( double planetMass, double satelliteMass, double orbitalRadius )
{
    double result;

    double period;
    double circumference;
    double a3;
    double p2;
    double gm;

    a3 = orbitalRadius * orbitalRadius * orbitalRadius;
    gm = ModelConstants.G * ( planetMass + satelliteMass );

    p2 = ( fourPiSquared / ( gm ) ) * a3;
    period = Math.sqrt( p2 );


    circumference = orbitalRadius * 2 * Math.PI;
    result = circumference / period;

    if ( debug > 2 )
    {
        System.out.println( "RocheUtil::calculateOrbitalSpeed(): supplied with planetMass=" + planetMass + ", orbitalRadius=" + orbitalRadius + ", result=" + result + "m/s.");
    }

    return result;
}


}
