package au.com.polly.roche.ui;

import au.com.polly.roche.model.PlanetType;
import au.com.polly.roche.model.SatelliteType;
import au.com.polly.roche.model.ModelConstants;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Battery of tests for the model settings.
 * 
 */
public class ModelSettingsTest implements ModelConstants
{

@Test
public void testConstructor()
{
    ModelSettings settings = new ModelSettings( PlanetType.JOVIAN, SatelliteType.ICE );

    assertNotNull( settings );
    assertEquals( PlanetType.JOVIAN, settings.getPlanetType() );
    assertEquals( JUPITER_MASS, settings.getPlanetMass(), 1E12 );
    assertEquals( JUPITER_DENSITY, settings.getPlanetDensity(), 0.000001 );

    assertEquals( SatelliteType.ICE, settings.getSatelliteType() );
    assertEquals( 920.0, settings.getSatelliteDensity(), 0.000001 );
    assertEquals( 1E20, settings.getSatelliteMass(), 1E8 );

    assertEquals( 9.9515983918997E7, settings.getOrbitalRadius(), 0.1 );
    assertEquals( 35684.25511687408, settings.getOrbitalSpeed(), 0.0001 );

    assertEquals( 69924753.4558, settings.getPlanetRadius(), 0.1 );


    assertEquals( 99515983.919, settings.calculateRocheLimit(), 0.1 );
    assertEquals( 35684.25511687408, settings.calculateCircularOrbitalSpeed(), 0.0001 );

}


@Test
public void testEarthMoonSettingsAtRocheLimit()
{
    ModelSettings settings = new ModelSettings( PlanetType.TERRESTIAL, SatelliteType.ROCK );
    assertNotNull( settings );
    assertEquals( PlanetType.TERRESTIAL, settings.getPlanetType() );
    assertEquals( PlanetType.TERRESTIAL.getDefaultMass(), settings.getPlanetMass(), 1E8 );
    assertEquals( PlanetType.TERRESTIAL.getDensity(), settings.getPlanetDensity(), 0.1 );
    assertEquals( EARTH_MASS, settings.getPlanetMass(), 1E8 );

    assertEquals( SatelliteType.ROCK, settings.getSatelliteType() );
    assertEquals( SatelliteType.ROCK.getDefaultMass(), settings.getSatelliteMass(), 1E8 );
    assertEquals( SatelliteType.ROCK.getDensity(), settings.getSatelliteDensity(), 0.1 );
    assertEquals( LUNAR_MASS, settings.getSatelliteMass(), 1E8 );


    assertEquals( 6523.1608, settings.getOrbitalSpeed(), 0.1 );
    assertEquals( 9483750.32, settings.getOrbitalRadius(), 0.1 );
    assertEquals( 6371040.4738, settings.getPlanetRadius(), 0.1 );
}

@Test
public void testEarthMoonSettingsCurrent()
{
    ModelSettings settings = new ModelSettings( PlanetType.TERRESTIAL, SatelliteType.ROCK );
    assertNotNull( settings );
    assertEquals( PlanetType.TERRESTIAL, settings.getPlanetType() );
    assertEquals( PlanetType.TERRESTIAL.getDefaultMass(), settings.getPlanetMass(), 1E8 );
    assertEquals( PlanetType.TERRESTIAL.getDensity(), settings.getPlanetDensity(), 0.1 );
    assertEquals( EARTH_MASS, settings.getPlanetMass(), 1E8 );

    settings.setOrbitalRadius( 384400000.0 );
    settings.setOrbitalSpeed( settings.calculateCircularOrbitalSpeed() );

    assertEquals( SatelliteType.ROCK, settings.getSatelliteType() );
    assertEquals( SatelliteType.ROCK.getDefaultMass(), settings.getSatelliteMass(), 1E8 );
    assertEquals( SatelliteType.ROCK.getDensity(), settings.getSatelliteDensity(), 0.1 );
    assertEquals( LUNAR_MASS, settings.getSatelliteMass(), 1E8 );


    assertEquals( 1024.60496, settings.getOrbitalSpeed(), 0.1 );
    assertEquals( 384400000.0, settings.getOrbitalRadius(), 0.1 );
    assertEquals( 6371040.4738, settings.getPlanetRadius(), 0.1 );
}

}
