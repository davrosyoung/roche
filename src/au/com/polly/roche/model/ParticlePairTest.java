package au.com.polly.roche.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Battery of tests for the particle pair class. Because this class plays such an important
 * role, and is used for hashing into tables of calculations, particular care is taken to
 * exercise the equals() and hashCode() methods.
 *
 *
 * @author dave
 *         created: May 29, 2009 10:13:59 PM
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
public class ParticlePairTest {


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

}


@Test( expected=IllegalArgumentException.class )
public void testCreatingPairWithSameParticle()
{
    ParticlePair pricklyPear;

    pricklyPear = new ParticlePair( earth, earth );
}


@Test( expected=IllegalArgumentException.class )
public void testCreatingPairWithParticleAndClonedCopy()
{
    ParticlePair pricklyPear;

    pricklyPear = new ParticlePair( earth, (Particle)earth.clone() );
}

@Test( expected=NullPointerException.class )
public void testCreatingPairWithFirstArgNull()
{
    ParticlePair pricklyPair;

    pricklyPair = new ParticlePair( null, earth );
}

@Test( expected=NullPointerException.class )
public void testCreatingPairWithSecondArgNull()
{
    ParticlePair pricklyPair;

    pricklyPair = new ParticlePair( earth, null );
}

@Test( expected=NullPointerException.class )
public void testCreatingPairWithBothArgsNull()
{
    ParticlePair pricklyPair;

    pricklyPair = new ParticlePair( null, null );    

}


@Test
public void testEqualsPairs()
{
    ParticlePair alpha;
    ParticlePair beta;

    alpha = new ParticlePair( earth, sun );
    assertNotNull( alpha );

    beta = new ParticlePair( earth, sun );
    assertNotNull( beta );
}

@Test
public void testPairsWithClonedCopiesOfParticles()
{
    ParticlePair alpha;
    ParticlePair beta;

    alpha = new ParticlePair( earth, sun );
    assertNotNull( alpha );

    beta = new ParticlePair( (Particle)earth, (Particle)sun.clone() );
    assertNotNull( beta );



    assertFalse( alpha == beta );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
}

@Test
public void testPairsWithDifferentParticles()
{
    ParticlePair alpha;
    ParticlePair beta;

    alpha = new ParticlePair( earth, sun );
    assertNotNull( alpha );

    beta = new ParticlePair( earth, jupiter );
    assertNotNull( beta );

    assertFalse( alpha == beta );
    assertFalse( alpha.equals( beta ) );
    assertFalse( alpha.hashCode() == beta.hashCode() );

}


}
