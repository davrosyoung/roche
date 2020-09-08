package au.com.polly.roche.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * The delta table is extremely important for recording the difference in location between
 * particles. Once invoked for a given particle pair, it automatically records the "opposite"
 * vector for the same pair indexed in the other order.
 *
 *
 * @author dave
 *         created: May 29, 2009 10:31:58 PM
 *         <p/>
 *         <p/>
 *         &copy; Copyright Polly Enterprises Pty Ltd 2009
 *         <p/>
 *         <p>
 *         Intended for demonstration purposes only, not intended for
 *         use within a production environment.
 *         Free for distribution for non-commercial use, as long as this notice
 *         is displayed in the resultant work. No warranty, implicit or explicit
 *         is provided by the use, in any manner, of this programme code, or applications of
 *         any kind utilizing it.
 *         </p>
 */
public class ParticlePairTableTest implements ModelConstants
{

private final static double ALLOWABLE_ERROR = 1E-12;

ParticlePairTable<Vector> deltaTable;
ParticlePairTable<Double> distanceTable;
Particle sun;
Particle earth;
Particle billionSolarMasses;
Point solarLocation;
Point earthLocation;
Vector zeroVector;
Particle jupiter;

@Before
public void setup()
{
    solarLocation = new Point( 0.0, 0.0 );
    earthLocation = new Point( ModelConstants.AU, 0.0 );
    zeroVector = new Vector( 0.0, 0.0 );
    Point jupiterLocation = new Point( ModelConstants.AU * 5.2, 0.0 );

    jupiter = new Particle( "Jupiter", jupiterLocation, zeroVector, ModelConstants.EARTH_MASS * 318, 1326 );
    sun = new Particle( "ra", solarLocation, zeroVector, ModelConstants.SOLAR_MASS, 1410 );
    earth = new Particle( "gaia", earthLocation, zeroVector, ModelConstants.EARTH_MASS, 5515 );

    deltaTable = new ParticlePairTable<Vector>();
    distanceTable = new ParticlePairTable<Double>();
    Vector v = sun.getLocation().getDelta( earth.getLocation() );
    deltaTable.put( sun, earth, v );
    distanceTable.put( sun, earth, v.scalar() );

    v = sun.getLocation().getDelta( jupiter.getLocation() );
    deltaTable.put( sun, jupiter, v );
    distanceTable.put( sun, jupiter, v.scalar() );

    v = earth.getLocation().getDelta( jupiter.getLocation() );
    deltaTable.put( earth, jupiter, v );
    distanceTable.put( earth, jupiter, v.scalar() );
}


@Test
public void testConstructor()
{
    ParticlePairTable<Integer> table = new ParticlePairTable<Integer>();
    assertNotNull( table );
}

@Test( expected=NullPointerException.class)
public void testGettingEntryWithFirstParticleArgNull()
{
    Vector v;

    v = deltaTable.get( null, earth );
}

@Test( expected=NullPointerException.class)
public void testGettingEntryWithSecondParticleArgNull()
{
    Vector v;

    v = deltaTable.get( earth, null );
}

@Test( expected=NullPointerException.class)
public void testGettingEntryWithBothParticleArgsNull()
{
    Vector v;

    v = deltaTable.get( null, null );
}

@Test( expected=IllegalArgumentException.class)
public void testGettingEntryWithSameParticle()
{
    Vector v;

    v = deltaTable.get( earth, earth );
}

@Test( expected=IllegalArgumentException.class)
public void testGettingEntryWithClonedPartcle()
{
    Vector v;

    v = deltaTable.get( earth, (Particle)earth.clone() );
}

@Test
public void testGettingEntryWithOfPartclesWithZeroSeparationUsingPairs()
{
    Vector v;
    double distance;
    Particle twin = (Particle)earth.clone();
    ParticlePair pair;
    twin.setLabel( "evil twin" );
    twin.setVelocity( new Vector( -2.0, -2.0 ) );
    pair = new ParticlePair( earth, twin );

    Vector earthLocation = (Vector) earth.getLocation().clone();
    Vector twinLocation = (Vector) twin.getLocation().clone();
    Vector delta = earthLocation.getDelta( twinLocation );

    deltaTable.put( pair, delta );
    distanceTable.put( pair, delta.scalar() );

    v = deltaTable.get( pair );

    assertNotNull( v );
    assertEquals( 0, v.x, ALLOWABLE_ERROR );
    assertEquals( 0, v.y, ALLOWABLE_ERROR );
    assertEquals( 0, v.scalar(), ALLOWABLE_ERROR );

    distance = distanceTable.get( pair );
    assertEquals( distance, 0.0, ALLOWABLE_ERROR );
}
@Test
public void testGettingEntryWithOfPartclesWithZeroSeparationUsingParticles()
{
    Vector v;
    double distance;
    Particle twin = (Particle)earth.clone();
    twin.setLabel( "evil twin" );
    twin.setVelocity( new Vector( -2.0, -2.0 ) );


    Vector earthLocation = (Vector) earth.getLocation().clone();
    Vector twinLocation = (Vector) twin.getLocation().clone();

    deltaTable.put( earth, twin, earthLocation.getDelta( twinLocation ) );
    distanceTable.put( earth,twin, earthLocation.getDelta( twinLocation ).scalar() );
    
    v = deltaTable.get( earth, twin );

    assertNotNull( v );
    assertEquals( 0, v.x, ALLOWABLE_ERROR );
    assertEquals( 0, v.y, ALLOWABLE_ERROR );
    assertEquals( 0, v.scalar(), ALLOWABLE_ERROR );

    distance = distanceTable.get( earth, twin );
    assertEquals( distance, 0.0, ALLOWABLE_ERROR );
}

@Test
public void testGettingEntryWithExtantParticle()
{
    Vector v;
    Particle twin = (Particle)earth.clone();
    twin.setLabel( "evil twin" );
    twin.setVelocity( new Vector( -2.0, -2.0 ) );

//    deltaTable.put( earth, twin, earth.getLocation().getDelta( twin.getLocation() ) );

    v = deltaTable.get( earth, twin );

    assertNull( v );

}

@Test
public void testGettingEarthSunDelta()
{
    Vector v = deltaTable.get( earth, sun );
    assertNotNull( v );
    assertEquals( v.x, -AU, 1E12 );
    assertEquals( v.y, 0.0, ALLOWABLE_ERROR );

    double distance = distanceTable.get( earth, sun );
    assertEquals( AU, distance, ALLOWABLE_ERROR );

}

@Test
public void testGettingSunEarthDelta()
{
    Vector v = deltaTable.get( earth, sun );
    assertNotNull( v );
    assertEquals( v.x, AU, 1E12 );
    assertEquals( v.y, 0.0, ALLOWABLE_ERROR );

    double distance = distanceTable.get( sun, earth );
    assertEquals( AU, distance, ALLOWABLE_ERROR );
}

}