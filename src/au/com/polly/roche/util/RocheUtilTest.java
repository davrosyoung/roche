package au.com.polly.roche.util;

import org.junit.Test;
import static org.junit.Assert.*;
import au.com.polly.roche.model.ModelConstants;

/**
 * Battery of tests for the RocheUtil utility class
 * 
 */
public class RocheUtilTest implements ModelConstants
{


@Test
public void testCalculateGettingRadiusFromMassAndDensity()
{
    assertEquals( 6371040.4736, RocheUtil.calculateRadiusFromMassAndDensity( EARTH_MASS, EARTH_DENSITY ), 0.1 );
}

@Test
public void testCalculateGettingRadiuFromVolume()
{
    assertEquals( 6371040.4736, RocheUtil.calculateRadiusFromVolume( 1.08322756E21 ), 0.1 );
}


/**
 * Demonstrate that we can correctly calculate the roche limit for the moon from the
 * earth's centre.
 */
@Test
public void testCalculatingEarthMoonRocheLimit()
{
    assertEquals( 9483750.32, RocheUtil.calculateRocheLimit( 6371040.4736, EARTH_DENSITY, LUNAR_DENSITY ), 0.1 );
}

@Test
public void testCalculatingJupiterMoonRocheLimit()
{
    assertEquals( 9.00742756990845E7, RocheUtil.calculateRocheLimit( 7.1492E7, JUPITER_DENSITY, JUPITER_DENSITY ), 1E3 );
    assertEquals( 1.017464684639154E8, RocheUtil.calculateRocheLimit( 7.1492E7, JUPITER_DENSITY, 920.0 ), 0.1 );
    assertEquals( 6.6174885269E7, RocheUtil.calculateRocheLimit( 7.1492E7, JUPITER_DENSITY, LUNAR_DENSITY ), 1E3 );   // within jupiter's radius!!!
}


/**
 * Demonstrate that we can caculate orbital speed of one body around another, assuming a circular
 * orbit, and given the orbital radius.
 *
 */

@Test
public void testCalcuatingOrbitalSpeed()
{
    assertEquals( 1024.6, RocheUtil.calculateOrbitalSpeed( EARTH_MASS, LUNAR_MASS, 384400000.0 ), 0.1 );
    assertEquals( 29786.0, RocheUtil.calculateOrbitalSpeed( SOLAR_MASS, EARTH_MASS, AU ), 0.1 );
}

}
