package au.com.polly.roche.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Battery of tests for the particle class. Because the hashCode() and equals() methods have
 * been overriden, particularly stringent tests for those methods have been implemented.
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
public class ParticleTest
{

Particle sun;
Particle earth;
Particle billionSolarMasses;
Point solarLocation;
Point earthLocation;
Vector zeroVector;


@Before
public void setup()
{
    solarLocation = new Point( 0.0, 0.0 );
    earthLocation = new Point( ModelConstants.AU, 0.0 );
    zeroVector = new Vector( 0.0, 0.0 );

    sun = new Particle( "ra", solarLocation, zeroVector, ModelConstants.SOLAR_MASS, 1410 );
    earth = new Particle( "gaia", earthLocation, zeroVector, ModelConstants.EARTH_MASS, 5515 );
    
}

@Test
public void testCreatingJupiter()
{
    Particle jupiter;
    Point jupiterLocation = new Point( ModelConstants.AU * 5.2, 0.0 );

    jupiter = new Particle( "Jupiter", jupiterLocation, zeroVector, ModelConstants.EARTH_MASS * 318, 1326 );
    assertNotNull( jupiter );
    assertNotNull( jupiter.getLabel() );
    assertEquals( "Jupiter", jupiter.getLabel() );
    assertNotNull( jupiter.getLocation() );
    assertEquals( jupiter.getLocation(), jupiterLocation );
    assertEquals( jupiter.getLocation().x, 5.2 * ModelConstants.AU, 0.000000000001 );
    assertEquals( jupiter.getLocation().y, 0.0, 0.000000000001 );
    assertNotNull( jupiter.getVelocity() );
    assertEquals( jupiter.getVelocity(), zeroVector );
    assertEquals( jupiter.getVelocity().x, 0.0, 0.000000000001 );
    assertEquals( jupiter.getVelocity().y, 0.0, 0.000000000001 );

    assertEquals( jupiter.getDensity(), 1326.0, 0.000000000001 );
    assertEquals( jupiter.getMass(), 318 * ModelConstants.EARTH_MASS, 0.000000001 );
}


/**
 * Demonstrate whether or not we can calculate the gravitational force
 * of attraction between two bodies...

@Test
public void testEarthSunGravitationalForce()
{
    // from theory ...
    // F = G . m1 . m1 / d^2
    double F = ( ModelConstants.G * ModelConstants.SOLAR_MASS * ModelConstants.EARTH_MASS ) / ( ModelConstants.AU * ModelConstants.AU );
    Vector g = sun.attractionForce( earth );
    assertNotNull( g );
    assertEquals( g.scalar(), F, 0.00000000000001 );
    assertEquals( F, g.x, 1E14 );
    assertEquals( 0, g.y, 1E14 );

    // the vector of the sun upon the earth should be in the opposite direction!!
    Vector h = earth.attractionForce( sun );
    assertNotNull( h );
    assertEquals( h.scalar(), F, 0.00000000000001 );
    assertEquals( -F, h.x, 1E14 );
    assertEquals( 0, g.y, 1E14 );
    assertEquals( 0, h.y, 1E14 );

    Vector o = h.getOpposite();
    assertEquals( g.x, o.x, 1E14 );
    assertEquals( g.y, o.y, 1E14 );
}
 */

@Test
public void testEquality()
{
    Particle twin;

    twin = (Particle)earth.clone();
    assertFalse( twin == earth );
    assertEquals( earth, twin );

    // modify label
    twin.setLabel( "not-gaia");
    assertFalse( earth.equals( twin ) );

    // modify density
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    twin.setDensity( twin.getDensity() + 0.01 );
    assertFalse( earth.equals( twin ) );

    // modify location
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    twin.getLocation().add( new Vector( 0.1, 0.1 ) );
    assertFalse( earth.equals( twin ) );

    // modify velocity
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    earth.getVelocity().add( new Vector( 0.1, 0.1 ) );
    assertFalse( earth.equals( twin ) );

    // modify mass
    twin =(Particle) earth.clone();
    assertEquals( earth, twin );
    twin.setMass( twin.getMass() + 1E16 ); // if this is too small, no change is recorded!!
    assertFalse( earth.equals( twin ) );

    // modify internal cohesion
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    twin.setInternalCohesion( twin.getInternalCohesion() + 0.01 );
    assertFalse( earth.equals( twin ) );


}

@Test
public void testHashCode()
{
    Particle twin;

    twin = (Particle)earth.clone();
    assertFalse( twin == earth );
    assertEquals( earth.hashCode(), twin.hashCode() );

    // modify label
    twin.setLabel( "not-gaia");
    assertFalse( earth.hashCode() == twin.hashCode() );

    // modify density
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    twin.setDensity( twin.getDensity() + 0.01 );
    assertFalse( earth.hashCode() == twin.hashCode() );

    // modify location
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    twin.getLocation().add( new Vector( 0.1, 0.1 ) );
    assertFalse( earth.hashCode() == twin.hashCode() );

    // modify velocity
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    earth.getVelocity().add( new Vector( 0.1, 0.1 ) );
    assertFalse( earth.hashCode() == twin.hashCode() );

    // modify mass
    twin =(Particle) earth.clone();
    assertEquals( earth, twin );
    twin.setMass( twin.getMass() + 1E16 ); // if this is too small, no change is recorded!!
    assertFalse( earth.hashCode() == twin.hashCode() );

    // modify internal cohesion
    twin = (Particle)earth.clone();
    assertEquals( earth, twin );
    twin.setInternalCohesion( twin.getInternalCohesion() + 0.01 );
    assertFalse( earth.hashCode() == twin.hashCode() );
}

}
