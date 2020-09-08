package au.com.polly.roche.ui;

import au.com.polly.roche.model.*;
import au.com.polly.roche.util.RocheUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Kind of kludgy ... we've hardwired some different model settings, such that when the
 * simulation is started up, it will cycle through some different, hopefully interesting
 * scenarios .... hardcoded programming like this is not clever or witty children ....
 * ... but we were in a bit of a hurry..
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
public class PresetModelSetting implements ModelConstants
{

static List<ModelSettings> presetList = new ArrayList<ModelSettings>();

static Random random = new Random( System.currentTimeMillis() );
private static int count = 0;
private static Object classLock = new java.util.Date();

static {
    double rocheLimit;
    double circularOrbitalSpeed;
    double planetRadius;
    double orbitalRadius;
    ModelSettings eins = new ModelSettings( PlanetType.JOVIAN, SatelliteType.RUBBLE_PILE, JUPITER_MASS, LUNAR_MASS * 0.02, 200000000.0, 24000.0 );
    planetRadius = eins.getPlanetRadius();
    rocheLimit = eins.calculateRocheLimit();
    eins.setOrbitalRadius( rocheLimit * 1.01 );
    circularOrbitalSpeed = eins.calculateCircularOrbitalSpeed();
    eins.setOrbitalSpeed( circularOrbitalSpeed * 0.97 );

    ModelSettings zwei = new ModelSettings( PlanetType.TERRESTIAL, SatelliteType.RUBBLE_PILE );
    zwei.setSatelliteMass( ModelConstants.LUNAR_MASS * 0.004 );
    rocheLimit = zwei.calculateRocheLimit();
    zwei.setOrbitalRadius( rocheLimit * 1.00005 );
    circularOrbitalSpeed = zwei.calculateCircularOrbitalSpeed();
    zwei.setOrbitalSpeed( circularOrbitalSpeed * 0.99 );

    ModelSettings drei = new ModelSettings( PlanetType.JOVIAN, SatelliteType.ICE_ROCK_SLUSHIE );
    drei.setSatelliteMass( ModelConstants.LUNAR_MASS * 0.02  );
    rocheLimit = drei.calculateRocheLimit();
    drei.setOrbitalRadius( rocheLimit * 0.8 );
    circularOrbitalSpeed = drei.calculateCircularOrbitalSpeed();
    drei.setOrbitalSpeed( circularOrbitalSpeed * 1.12 );

    ModelSettings vier = new ModelSettings( PlanetType.TERRESTIAL, SatelliteType.ICE );
    vier.setSatelliteMass( LUNAR_MASS * 0.02 );
    rocheLimit = vier.calculateRocheLimit();
    vier.setOrbitalRadius( rocheLimit * 1.5 );
    circularOrbitalSpeed = vier.calculateCircularOrbitalSpeed();
    vier.setOrbitalSpeed( circularOrbitalSpeed * 0.5 );

    ModelSettings fuenf = new ModelSettings( PlanetType.JOVIAN, SatelliteType.ICE );
    fuenf.setPlanetMass( JUPITER_MASS * 0.5 );
    fuenf.setSatelliteMass( EARTH_MASS * 0.2 );
    rocheLimit = fuenf.calculateRocheLimit();
    fuenf.setOrbitalRadius( rocheLimit * 1.001 );
    circularOrbitalSpeed = fuenf.calculateCircularOrbitalSpeed();
    fuenf.setOrbitalSpeed( circularOrbitalSpeed * 0.985 );
/*
    ModelSettings sechs = new ModelSettings( PlanetType.ICE_GIANT, SatelliteType.ICE );
    fuenf.setPlanetMass( JUPITER_MASS * 0.5 );
    fuenf.setSatelliteMass( LUNAR_MASS * 2 );
    rocheLimit = fuenf.calculateRocheLimit();
    fuenf.setOrbitalRadius( rocheLimit * 1.001 );
    circularOrbitalSpeed = fuenf.calculateCircularOrbitalSpeed();
    fuenf.setOrbitalSpeed( circularOrbitalSpeed * 0.985 );
 */

//    ModelSettings vier = new ModelSettings( PlanetType.ICE_GIANT, SatelliteType.ICE  );
 //   rocheLimit =

        presetList.add( eins );
        presetList.add( zwei );
        presetList.add( drei );
        presetList.add( vier );
        presetList.add( fuenf );

}

/**
 *
 * @return one of the preset model settings
 */
public static ModelSettings getRandomModelSettings()
{
    ModelSettings result;

    synchronized( classLock )
    {
        count++;
    }
    int i = count % presetList.size();
    result = presetList.get( i );

    return result;
}


/**
 *
 * @return a list of all of the preset models
 */
public static List<ModelSettings> getPresetModelSettings()
{
    return null;
}


}
