package au.com.polly.roche.ui;

import au.com.polly.roche.model.SatelliteType;
import au.com.polly.roche.model.PlanetType;
import au.com.polly.roche.model.ModelConstraints;
import au.com.polly.roche.model.UIDerivedModelConstraints;
import au.com.polly.roche.util.RocheUtil;

/**
 * Settings for the roche limit simulation. Enables us to transition settings between the model and the
 * control panel. Our preset models are instances of this class.
 *
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
public class ModelSettings
{
private final int debug = 0;
SatelliteType   satelliteType;
double          satelliteMass;
double          orbitalRadius;
double          orbitalSpeed;
double          planetMass;
double          planetDensity;
PlanetType      planetType;

/**
 * Model settings using just the type of planet, and type of satellite...
 *
 * @param planetType
 * @param satelliteType
 */
public ModelSettings( PlanetType planetType, SatelliteType satelliteType )
{
    setPlanetType( planetType );
    setPlanetMass( planetType.getDefaultMass() );

    setSatelliteType( satelliteType );
    setSatelliteMass( satelliteType.getDefaultMass() );

    setOrbitalRadius( calculateRocheLimit() );
    setOrbitalSpeed( calculateCircularOrbitalSpeed() );
}


public ModelSettings(
    PlanetType planetType,
    SatelliteType satelliteType,
    double planetMass,
    double satelliteMass
)
{
    this( planetType, satelliteType, planetMass, satelliteMass, 0.0, 0.0 );
    setOrbitalRadius( calculateRocheLimit() );
    setOrbitalSpeed( calculateCircularOrbitalSpeed() );
}

public ModelSettings(
    PlanetType planetType,
    SatelliteType satelliteType,
    double planetMass,
    double satelliteMass,
    double orbitalRadius
)
{
    this( planetType, satelliteType, planetMass, satelliteMass, orbitalRadius, 0.0 );
    setOrbitalSpeed( calculateCircularOrbitalSpeed() );
}


/**
 *
 * @param planetType which type of planet is being orbited?
 * @param satelliteType which type of satellite is being orbited?
 * @param planetMass mass of the planet
 * @param satelliteMass satellite's mass
 * @param orbitalRadius ditsance from the centre of the planet at which the object orbits.
 * @param orbitalSpeed how fast (m/s) that the satellite is going around the primary.
 */
public ModelSettings(
    PlanetType planetType,
    SatelliteType satelliteType,
    double planetMass,
    double satelliteMass,
    double orbitalRadius,
    double orbitalSpeed
)
{
    setPlanetType( planetType );
    setSatelliteType( satelliteType );

    setPlanetMass( planetMass );

    setSatelliteMass( satelliteMass );

    setOrbitalRadius( orbitalRadius );
    setOrbitalSpeed( orbitalSpeed );
}


public SatelliteType getSatelliteType() {
    return satelliteType;
}

public void setSatelliteType(SatelliteType satelliteType) {
    this.satelliteType = satelliteType;
}

public double getSatelliteDensity() {
    return satelliteType.getDensity();
}

public double getSatelliteMass() {
    return satelliteMass;
}

public void setSatelliteMass(double satelliteMass) {
    this.satelliteMass = satelliteMass;
}

public double getOrbitalRadius() {
    return orbitalRadius;
}

public void setOrbitalRadius(double orbitalRadius) {
    this.orbitalRadius = orbitalRadius;
}

public double getOrbitalSpeed() {
    return orbitalSpeed;
}

public void setOrbitalSpeed(double orbitalSpeed) {
    this.orbitalSpeed = orbitalSpeed;
}

public double getPlanetMass() {
    return planetMass;
}

public void setPlanetMass(double planetMass) {
    this.planetMass = planetMass;
}

public double getPlanetDensity() {
    return planetType.getDensity();
}

public PlanetType getPlanetType()
{
    return planetType;
}

public void setPlanetType(PlanetType planetType)
{
    this.planetType = planetType;
}

public double getPlanetRadius()
{
    return RocheUtil.calculateRadiusFromMassAndDensity( getPlanetMass(), getPlanetDensity() );
}

/**
 * does NOT modify the existing model settings in any way.
 *
 * @return for the masses and densities of the bodies in these settings, what is the
 * theoretical roche limit? Returned as orbital radius, in metres from the centre
 * of the planet.
 *
 */
public double calculateRocheLimit()
{
    double radius;
    double result;

    radius = RocheUtil.calculateRadiusFromMassAndDensity( getPlanetMass(), getPlanetDensity() );
    result = RocheUtil.calculateRocheLimit( radius, getPlanetDensity(), getSatelliteDensity() );

    if ( debug > 2 )
    {
        System.out.println( "ModelSettings::calculateRocheLimit(): planet radius=" + ( radius / 1000.0 ) + "km, planet density=" + getPlanetDensity() + "kg/m3, satellite density=" + getSatelliteDensity() + "kg/m3, roche limit=" + ( result/ 1000.0 ) + "km.");
    }
    return result;
}

/**
 * does NOT modify the existing model settings.
 *
 *
 * @return the required orbital speed to maintain a circular orbit above the planet at the
 * current orbital radius.
 */
public double calculateCircularOrbitalSpeed()
{
    double result;

    result = RocheUtil.calculateOrbitalSpeed( getPlanetMass(), getSatelliteMass(), getOrbitalRadius()  );

    return result;
}

/**
 * Determines appropriate model constraints, such as the graphical bounding box, refresh interval
 * and timescaling.
 *
 *
 * @param controlPanel
 * @return
 */
public ModelConstraints getModelConstraints( ControlPanel controlPanel )
{
    ModelConstraints result = new UIDerivedModelConstraints( this, controlPanel ); 
    return result;
}


/**
 *
 * @return duplicate of this model settings object...
 */
public Object clone()
{
    Object result = null;

    result = new ModelSettings( getPlanetType(), getSatelliteType(), getPlanetMass(), getSatelliteMass(), getOrbitalRadius(), getOrbitalSpeed() );
    
    return result;
}


public String toString()
{
    String result = null;
    StringBuffer out = new StringBuffer();

    out.append( "PLANET type:" + getPlanetType() + ", mass=" + getPlanetMass() + "kg, density=" + getPlanetDensity() + ", radius=" + getPlanetRadius() + "\n" );
    out.append( "SATELLETE type:" + getSatelliteType() + ", mass=" + getSatelliteMass() + "kg, density=" + getSatelliteDensity() + "\n" );
    out.append( "orbit radius=" + ( getOrbitalRadius() / 1000.0 ) + "km, orbital speed=" + ( getOrbitalSpeed() / 1000.0 ) + "kms-1" );

    result = out.toString();
    return result;

}

}
