package au.com.polly.roche.ui;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import au.com.polly.roche.model.ModelConstants;
import static org.junit.Assert.*;

/**
 * Battery of tests for linear and power slider converters
 */
public class SliderConverterTest implements ModelConstants
{
JSlider hunjey;
JSlider twoHunjey;
JSlider fiddy;


@Before
public void setup()
{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    hunjey = new JSlider( 0, 100, 50 );
    twoHunjey = new JSlider( 0, 200, 100 );
    fiddy = new JSlider( 0, 50, 25 );

    panel.add( hunjey );
    panel.add( twoHunjey );
    panel.add( fiddy );
    panel.setVisible( true );

    frame.add( panel );
    frame.pack();
    frame.setVisible( true );
}

@Test
public void testLinearSlider()
{
    double range = JUPITER_MASS - EARTH_MASS;

    assertNotNull( hunjey );
    assertNotNull( twoHunjey );
    assertNotNull( fiddy );

    SliderConverter conv = new LinearSliderConverter( EARTH_MASS, JUPITER_MASS, hunjey );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );
    assertEquals( EARTH_MASS + ( range / 100.0 ), conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + 2 * ( range / 100.0 ), conv.getValue( 2 ), 1E16 );
    assertEquals( ( EARTH_MASS + JUPITER_MASS ) / 2, conv.getValue( 50 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 100 ), 1E16 );
    assertEquals( EARTH_MASS + 99 * ( range / 100.0 ), conv.getValue( 99 ), 1E16 );

    assertEquals( 0, conv.getSliderPosition( EARTH_MASS ) );
    assertEquals( 01, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.01 )) );
    assertEquals( 25, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.25 )) );
    assertEquals( 50, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.50 )) );
    assertEquals( 75, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.75 )) );
    assertEquals( 99, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.99 )) );
    assertEquals( 100, conv.getSliderPosition( JUPITER_MASS ) );

    conv = new LinearSliderConverter( EARTH_MASS, JUPITER_MASS, fiddy );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );
    assertEquals( EARTH_MASS + ( range / 50.0 ), conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + 2 * ( range / 50.0 ), conv.getValue( 2 ), 1E16 );
    assertEquals( ( EARTH_MASS + JUPITER_MASS ) / 2, conv.getValue( 25 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 50 ), 1E16 );
    assertEquals( EARTH_MASS + 49 * ( range / 50.0 ), conv.getValue( 49 ), 1E16 );

    assertEquals( 0, conv.getSliderPosition( EARTH_MASS ) );
    assertEquals( 01, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.01 )) );
    assertEquals( 20, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.40 )) );
    assertEquals( 25, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.50 )) );
    assertEquals( 30, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.60 )) );
    assertEquals( 49, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.98 )) );
    assertEquals( 50, conv.getSliderPosition( JUPITER_MASS ) );

    conv = new LinearSliderConverter( EARTH_MASS, JUPITER_MASS, twoHunjey );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );
    assertEquals( EARTH_MASS + ( range / 200.0 ), conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + 2 * ( range / 200.0 ), conv.getValue( 2 ), 1E16 );
    assertEquals( ( EARTH_MASS + JUPITER_MASS ) / 2, conv.getValue( 100 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 200 ), 1E16 );
    assertEquals( EARTH_MASS + 199 * ( range / 200.0 ), conv.getValue( 199 ), 1E16 );


    assertEquals( 0, conv.getSliderPosition( EARTH_MASS ) );
    assertEquals( 1, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.005 )) );
    assertEquals( 2, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.01 )) );
    assertEquals( 50, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.25 )) );
    assertEquals( 99, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.495 )) );
    assertEquals( 100, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.50 )) );
    assertEquals( 101, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.505 )) );
    assertEquals( 150, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.75 )) );
    assertEquals( 199, conv.getSliderPosition( EARTH_MASS + ( ( JUPITER_MASS - EARTH_MASS ) * 0.995 )) );
    assertEquals( 200, conv.getSliderPosition( JUPITER_MASS ) );

}


public void testPowerSlider()
{
    double range = JUPITER_MASS - EARTH_MASS;
    double multiplier = Math.pow( range, 0.01 );
    double midValue;

    assertNotNull( hunjey );
    assertNotNull( twoHunjey );
    assertNotNull( fiddy );

    SliderConverter conv = new PowerSliderConverter( EARTH_MASS, JUPITER_MASS, hunjey );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );

    midValue = EARTH_MASS + Math.pow( multiplier, 50.0 );

    assertEquals( EARTH_MASS + ( range * multiplier ), conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + ( range * multiplier * multiplier ), conv.getValue( 2 ), 1E16 );
    assertEquals( midValue, conv.getValue( 50 ), 1E16 );
    assertEquals( JUPITER_MASS / multiplier, conv.getValue( 99 ), 1E16 );
    assertEquals( JUPITER_MASS / ( multiplier * multiplier ), conv.getValue( 98 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 100 ), 1E16 );


    conv = new PowerSliderConverter( EARTH_MASS, JUPITER_MASS, fiddy );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );

    multiplier = Math.pow( range, 0.02 );
    midValue = EARTH_MASS + Math.pow( multiplier, 25.0 );

    assertEquals( EARTH_MASS + ( range * multiplier ), conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + ( range * multiplier * multiplier ), conv.getValue( 2 ), 1E16 );
    assertEquals( midValue, conv.getValue( 50 ), 1E16 );
    assertEquals( JUPITER_MASS / multiplier, conv.getValue( 49 ), 1E16 );
    assertEquals( JUPITER_MASS / ( multiplier * multiplier ), conv.getValue( 48 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 100 ), 1E16 );

    conv = new PowerSliderConverter( EARTH_MASS, JUPITER_MASS, twoHunjey );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );

    multiplier = Math.pow( range, 0.005 );
    midValue = EARTH_MASS + Math.pow( multiplier, 100.0 );

    assertEquals( EARTH_MASS + ( range * multiplier ), conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + ( range * multiplier * multiplier ), conv.getValue( 2 ), 1E16 );
    assertEquals( midValue, conv.getValue( 100 ), 1E16 );
    assertEquals( JUPITER_MASS / multiplier, conv.getValue( 199 ), 1E16 );
    assertEquals( JUPITER_MASS / ( multiplier * multiplier ), conv.getValue( 198 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 100 ), 1E16 );


}

@Test
public void testQuadraticSlider()
{
    double range = JUPITER_MASS - EARTH_MASS;
    double midValue;
    double tenThousandth = range / 10000.0;
    double twoThousandFiveHundreth = range / 2500.0;

    assertNotNull( hunjey );
    assertNotNull( twoHunjey );
    assertNotNull( fiddy );

    SliderConverter conv = new QuadraticSliderConverter( EARTH_MASS, JUPITER_MASS, hunjey );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );

    midValue = ( 50.0 * 50.0 ) * tenThousandth;


    assertEquals( EARTH_MASS + tenThousandth, conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + ( tenThousandth * 4 ), conv.getValue( 2 ), 1E16 );
    assertEquals( EARTH_MASS + midValue, conv.getValue( 50 ), 1E16 );
    assertEquals( EARTH_MASS + ( tenThousandth * 98 * 98 ), conv.getValue( 98 ), 1E16 );
    assertEquals( EARTH_MASS + ( tenThousandth * 99 * 99 ), conv.getValue( 99 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 100 ), 1E16 );

    assertEquals( 50, conv.getSliderPosition( EARTH_MASS + midValue ) );
    assertEquals( 0, conv.getSliderPosition( EARTH_MASS ) );
    assertEquals( 100, conv.getSliderPosition( JUPITER_MASS ) );
    assertEquals( 1, conv.getSliderPosition( EARTH_MASS + tenThousandth ) );
    assertEquals( 2, conv.getSliderPosition( EARTH_MASS + ( 4 * tenThousandth ) ) );
    assertEquals( 3, conv.getSliderPosition( EARTH_MASS + ( 9 * tenThousandth ) ) ); 
    assertEquals( 4, conv.getSliderPosition( EARTH_MASS + ( 16 * tenThousandth ) ) );

    conv = new QuadraticSliderConverter( EARTH_MASS, JUPITER_MASS, fiddy );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );
    assertEquals( EARTH_MASS + twoThousandFiveHundreth, conv.getValue( 1 ), 1E16 );
    assertEquals( EARTH_MASS + ( twoThousandFiveHundreth * 4 ), conv.getValue( 2 ), 1E16 );
    assertEquals( EARTH_MASS + midValue, conv.getValue( 25 ), 1E16 );

    assertEquals( EARTH_MASS + ( twoThousandFiveHundreth * 48 * 48 ), conv.getValue( 48 ), 1E16 );
    assertEquals( EARTH_MASS + ( twoThousandFiveHundreth * 49 * 49 ), conv.getValue( 49 ), 1E16 );
    assertEquals( JUPITER_MASS, conv.getValue( 50 ), 1E16 );

    assertEquals( 25, conv.getSliderPosition( EARTH_MASS + midValue ) );
    assertEquals( 0, conv.getSliderPosition( EARTH_MASS ) );
    assertEquals( 50, conv.getSliderPosition( JUPITER_MASS ) );
    assertEquals( 1, conv.getSliderPosition( EARTH_MASS + twoThousandFiveHundreth ) );
    assertEquals( 2, conv.getSliderPosition( EARTH_MASS + ( 4 * twoThousandFiveHundreth ) ) );
    assertEquals( 3, conv.getSliderPosition( EARTH_MASS + ( 9 * twoThousandFiveHundreth ) ) );
    assertEquals( 4, conv.getSliderPosition( EARTH_MASS + ( 16 * twoThousandFiveHundreth ) ) );



    conv = new QuadraticSliderConverter( EARTH_MASS, JUPITER_MASS, twoHunjey );
    assertEquals( EARTH_MASS, conv.getValue( 0 ), 1E16 );
}


}
