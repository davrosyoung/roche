package au.com.polly.roche.ui;

import javax.swing.JSlider;

/**
 * Allows simple conversions between slider position and value it is modelling.
 * Uses a formula .... y = ax + c
 * where x is the position on the slider, and y varies between our minimum and maximum values.
 *
 *
 *
 *
 */
public class QuadraticSliderConverter extends BaseSliderConverter
{
private double  factor;
private double c;
private double a;


public QuadraticSliderConverter( double minValue, double maxValue, JSlider slider )
{
    super( minValue, maxValue, slider );
    this.c = minValue;
    double range = maxValue - minValue;
    this.a = range / ( this.sliderSize * this.sliderSize );
}


public int getSliderPosition( double value )
{
    int result = 0;
    double y;
    double x;

    do {

        if ( value < minValue )
        {
            result = 0;
            break;
        }

        if ( value > maxValue )
        {
            result = sliderSize;
            break;
        }

        // our line is of a form; y = a x^2 + c
        // .... c is minValue
        // .... so for formula y = a x^2, y is value above "c"
        // .... and x (slider pos ) = sqrt( y / a ).
        // -----------------------------------------------------
        if ( value >= minValue )
        {
            y = ( value - minValue ) / a;
            x = Math.sqrt( y );
            result = (int)Math.round( x );
            break;
        }

    } while( false );

    return result;
}

public double getValue( int sliderPosition )
{
    double result = 0.0;

    result = minValue + ( a * sliderPosition * sliderPosition );

    return result;
}

}