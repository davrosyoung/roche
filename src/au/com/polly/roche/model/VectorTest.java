package au.com.polly.roche.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Battery of tests to instill confidence in the
 * Vector class.
 *
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
public class VectorTest
{

Vector unitVector;
Vector zeroVector;
Vector a;
Vector b;
Vector c;
Vector up;
Vector northEast;
Vector southEast;
Vector west;
Vector down;
Vector east;
private final double ALLOWABLE_ERROR = 1E-12;

@Before
public void setup()
{
    unitVector = new Vector( 1.0, 1.0 );
    a = new Vector( -5.0, 4.0 );
    b = new Vector( 4.27E17, 2.98E13 );
    c = new Vector( 2, -2 );
    up = new Vector( 0.0, 2.0 );
    northEast = new Vector( 4.0, 4.0 );
    southEast = new Vector( 4.0, -4.0 );
    west = new Vector( -2.0, 0.0 );
    east = new Vector( 2.0, 0.0 );
    down = new Vector( -2.0, 0.0 );
    zeroVector = new Vector( 0.0, 0.0 );

}


@Test
public void testConstructingVector()
{
    Vector v;

    v = new Vector( 0, 0 );
    assertNotNull( v );

    assertEquals( 0.0, v.x, ALLOWABLE_ERROR );
    assertEquals( 0.0, v.y, ALLOWABLE_ERROR );

    v = new Vector( -5, 4 );
    assertNotNull( v );

    assertEquals( -5.0, v.x, ALLOWABLE_ERROR );
    assertEquals( 4.0, v.y, ALLOWABLE_ERROR );

    assertEquals( v, a );
}

// demonstrate that adding two vectors together yields expected results.
@Test
public void testAddingVectors()
{
    Vector v = a.add( c );
    assertEquals( v, a );
    assertTrue( v == a );
    assertNotNull( v );
    assertEquals( -3.0, v.x, ALLOWABLE_ERROR );
    assertEquals( 2.0, v.y, ALLOWABLE_ERROR );

    v = (Vector) west.clone();
    assertFalse( v == west );
    assertEquals( v, west );

    v = v.add( east );
    assertNotNull( v );
    assertEquals( 0.0, v.x, ALLOWABLE_ERROR );
    assertEquals( 0.0, v.y, ALLOWABLE_ERROR );
}

@Test
public void testSubtractingVectors()
{
    Vector v = a.getDelta( c );
    assertFalse( v == a );
    assertFalse( v.equals( a ) );
    assertNotNull( v );
    assertEquals( -7.0, v.x, ALLOWABLE_ERROR );
    assertEquals( 6.0, v.y, ALLOWABLE_ERROR );
}

@Test
public void testScalarDividingVectors()
{
    Vector v = a.divide( 2.0 );
    assertNotNull( v );
    assertTrue( a == v );
    assertEquals( v, a );
    assertEquals( -2.5, v.x, ALLOWABLE_ERROR );
    assertEquals( 2.0, v.y, ALLOWABLE_ERROR );

}

@Test( expected = java.lang.ArithmeticException.class )
public void testScalarDividingVectorByZero()
{
    Vector v = a.divide( 0.0 );
    // wtf??
    System.out.println( "v.x=" + v.x + ", v.y=" + v.y );
}

public void testScalarMultiplyingVectors()
{
    Vector v = a.multiply( 2.0 );
    assertNotNull( v );
    assertTrue( a == v );
    assertEquals( v, a );
    assertEquals( -10.0, v.x, ALLOWABLE_ERROR );
    assertEquals( 8.0, v.y, ALLOWABLE_ERROR );

}


public void testScalarDirection()
{
    assertEquals( Math.PI / 2, up.getDirection(), ALLOWABLE_ERROR );
    assertEquals( Math.PI, west.getDirection(), ALLOWABLE_ERROR );
    assertEquals( Math.PI / 4, northEast.getDirection(), ALLOWABLE_ERROR );
    assertEquals( -Math.PI / 4, southEast.getDirection(), ALLOWABLE_ERROR );
    assertEquals( -Math.PI / 2, down.getDirection(), ALLOWABLE_ERROR );
}



}
