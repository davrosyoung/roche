package au.com.polly.roche.ui;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Ensure that the coordinate class behaves as expected.
 *
 * @author dave
 *         created: May 29, 2009 4:10:03 PM
 *         <p/>
 *         &copy; Copyright Polly Enterprises Pty Ltd 2009
 *         <p/>
 *         free for distribution for non-commercial use, as long as this notice
 *         is displayed in the resultant work. No warranty, implicit or explicit
 *         is provided by the use, in any manner, of this programme code, or applications of
 *         any kind utilizing it.
 */
public class CoordinateTest
{

    Coordinate a;
    Coordinate b;
    Coordinate c;

    Coordinate m00;
    Coordinate m01;
    Coordinate m10;
    Coordinate m11;
    Coordinate ma0;

@Before
public void setup()
{
    a = new Coordinate( -65, 73 );
    b = new Coordinate( 65, 73 );
    c = new Coordinate( 65, 73 );

    m00 = new Coordinate( 0, 0 );
    m01 = new Coordinate( 0, 1 );
    m10 = new Coordinate( 1, 0 );
    m11 = new Coordinate( 1, 1 );
    ma0 = new Coordinate( 0, 65536);

}

@Test
public void testHashCode()
{
    assertFalse( a.hashCode() == c.hashCode() );
    assertEquals( c.hashCode(), b.hashCode() );
    assertEquals( m00.hashCode(), ma0.hashCode() );

    assertFalse( m00.hashCode() == m01.hashCode() );
    assertFalse( m00.hashCode() == m10.hashCode() );
    assertFalse( m00.hashCode() == m11.hashCode() );

    assertFalse( m00.hashCode() == m01.hashCode() );
    assertFalse( m00.hashCode() == m10.hashCode() );
    assertFalse( m00.hashCode() == m11.hashCode() );
    assertEquals( m00.hashCode(), ma0.hashCode() );

    assertFalse( m01.hashCode() == m00.hashCode() );
    assertFalse( m01.hashCode() == m10.hashCode() );
    assertFalse( m01.hashCode() == m11.hashCode() );
    assertFalse( m01.hashCode() == ma0.hashCode() );

    assertFalse( m10.hashCode() == m01.hashCode() );
    assertFalse( m10.hashCode() == m00.hashCode() );
    assertFalse( m10.hashCode() == m11.hashCode() );
    assertFalse( m10.hashCode() == ma0.hashCode() );

    assertFalse( m11.hashCode() == m00.hashCode() );
    assertFalse( m11.hashCode() == m01.hashCode() );
    assertFalse( m11.hashCode() == m10.hashCode() );
    assertFalse( m11.hashCode() == ma0.hashCode() );


}

public void testEquals()
{
    assertTrue( b.equals( c ) );
    assertFalse( a.equals( b ) );
    assertFalse( a.equals( null ) );
    assertFalse( a.equals( new java.util.Date() ) );

    assertFalse( m00.equals( m01 ) );
    assertFalse( m00.equals( m10 ) );
    assertFalse( m00.equals( m11 ) );
    assertTrue( m00.equals( ma0 ) );

    assertFalse( m01.equals( m00 ) );
    assertFalse( m01.equals( m10 ) );
    assertFalse( m01.equals( m11 ) );
    assertFalse( m01.equals( ma0 ) );

    assertFalse( m10.equals( m01 ) );
    assertFalse( m10.equals( m00 ) );
    assertFalse( m10.equals( m11 ) );
    assertFalse( m10.equals( ma0 ) );

    assertFalse( m11.equals( m01 ) );
    assertFalse( m11.equals( m10 ) );
    assertFalse( m11.equals( m00 ) );
    assertFalse( m11.equals( ma0 ) );
}

}
