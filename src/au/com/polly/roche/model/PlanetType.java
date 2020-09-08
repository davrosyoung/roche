package au.com.polly.roche.model;

import java.awt.*;

/**
 * Represents the different type of planet's (or other heavy bodies) which can
 * be found at the centre of the simulation...
 *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 */
public enum PlanetType implements ModelConstants {
//    SUN( "Sun", 1300.0, 0.4 * SOLAR_MASS, 8.0 * SOLAR_MASS, SOLAR_MASS, null, Color.YELLOW, 0.0 ),
//    BLACK_HOLE( "Black Hole (Massive)", 1E12, 1000 * SOLAR_MASS, 10000000 * SOLAR_MASS, SOLAR_MASS, null, Color.DARK_GRAY, 0.0 ),
//    WHITE_DWARF( "White Dwarf", 1E6, 0.4 * SOLAR_MASS, 8.0 * SOLAR_MASS, SOLAR_MASS, null, Color.WHITE, 1.0 ),
    JOVIAN( "Jovian Planet", JUPITER_DENSITY, 40 * EARTH_MASS, 10 * JUPITER_MASS, JUPITER_MASS, "images/jupiter.png", Color.ORANGE, 0.0 ),
    ICE_GIANT( "Ice Giant", 1800, 8 * EARTH_MASS, 50 * EARTH_MASS, 20 * EARTH_MASS, "images/neptune.png", Color.BLUE, 0.0 ),
    TERRESTIAL( "Terrestial Planet", EARTH_DENSITY, 0.1 * EARTH_MASS, 10 * EARTH_MASS, EARTH_MASS, "images/earth.png", Color.BLUE, 0.5 );

private final String label;
private final double density;
private final double minimumMass;
private final double maximumMass;
private final double defaultMass;
private final double internalCohesion;
private final String imagePath;
private final Color colour;

PlanetType( String label, double density, double minMass, double maxMass, double defaultMass, String imagePath, Color colour, double internalCohesion )
{
    this.label = label;
    this.density = density;
    this.minimumMass = minMass;
    this.maximumMass = maxMass;
    this.defaultMass = defaultMass;
    this.internalCohesion = internalCohesion;
    this.imagePath = imagePath;
    this.colour = colour;
}

public String getLabel()
{
    return this.label;
}

/**
 *
 * @return density of this type of satellite. units are g per cubic cc (or kg per cubic metre )
 */
public double getDensity()
{
    return this.density;
}

public double getMinimumMass()
{
    return this.minimumMass;
}

public Color getColour()
{
    return colour;
}

public double getDefaultMass()
{
    return defaultMass;
}

public String getImagePath()
{
    return imagePath;
}

public double getInternalCohesion()
{
    return internalCohesion;
}

public double getMaximumMass()
{
    return maximumMass;
}
}