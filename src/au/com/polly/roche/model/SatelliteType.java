package au.com.polly.roche.model;

import java.awt.*;

/**
 * Represents the different type of satellite's that we simulate.
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
public enum SatelliteType implements ModelConstants {
ROCK( "Rock", LUNAR_DENSITY, 1E12, EARTH_MASS, LUNAR_MASS, Color.RED, null ),
ICE( "Ice", 920.0, 1E16, EARTH_MASS, 1E20, Color.WHITE, null ),
ICE_ROCK_SLUSHIE( "Ice-Rock Slushie", 1800.0, 1E16, EARTH_MASS, 1E20, Color.LIGHT_GRAY, null ),
RUBBLE_PILE( "Rubble Pile", 3000.0, 1E16, EARTH_MASS, 1E20, Color.DARK_GRAY, null ),
IRON( "Iron", 6000.0, 1E16, EARTH_MASS, 1E20, Color.ORANGE, null );


/**
 * identifies this satellite type in the user interface.
 */
private final String label;

/**
 * the density, in km/m3 of this class of satellite
 */
private final double density;

/**
 * the minimum mass that can be assigned to this type of satellite
 */
private final double minimumMass;

/**
 * the maximum mass that can be assigned to this type of satellite
 */
private final double maximumMass;

/**
 * the default mass to assign ot this type of satellite.
 */
private final double defaultMass;

/**
 * the colour that this type of satellite should appear as.
 */
private final Color colour;

/**
 * the image (if any) used to depict this type of satellite.
 */
private final String imagePath;


SatelliteType( String label, double density, double minimumMass, double maximumMass, double defaultMass, Color colour, String imagePath )
{
    this.label = label;
    this.density = density;
    this.minimumMass = minimumMass;
    this.maximumMass = maximumMass;
    this.defaultMass = defaultMass;
    this.colour = colour;
    this.imagePath = imagePath;
}

public Color getColour()
{
    return colour;
}

public double getDefaultMass()
{
    return defaultMass;
}

public double getDensity()
{
    return density;
}

public String getImagePath()
{
    return imagePath;
}

public String getLabel()
{
    return label;
}

public double getMaximumMass()
{
    return maximumMass;
}

public double getMinimumMass()
{
    return minimumMass;
}

}
