package au.com.polly.roche.model;

/**
 * Constants used in the roche limit model simulation. These are used both within the model
 * calculations, and for providing some sensible "default" values within the user interface.
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
public interface ModelConstants
{

/**
 * Gravitational constant ... N m2 kg-2
 */
public final static double G = 6.673E-11;

/**
 * Mass and density of sun
 */
public final static double SOLAR_MASS = 1.989E30;
public final static double SOLAR_DENSITY = 1.989E30;


/**
 * Mass and density of little earth
 */
public final static double EARTH_MASS = 5.974E24;
public final static double EARTH_DENSITY = 5515;

/**
 * Earth's mean orbital distance from the sun.
 */
public final static double AU = 1.496E11;

/**
 * Juptier's mass and density
 */
public final static double JUPITER_MASS = 1.899E27;
public final static double JUPITER_DENSITY = 1326;

/**
 *  Lunar mass and density
 */
public final static double LUNAR_MASS = 7.349E22;
public final static double LUNAR_DENSITY = 3344;


/**
 * density of an ice-rock world..
 */
public final static double CALLISTO_DENSITY = 1851;

/**
 * Mass of deimos
 */
public final static double DEIMOS_MASS = 1.8E15;


}
