package au.com.polly.roche.ui;

import javax.swing.JSlider;

/**
 * Allows simple conversions between slider position and value it is modelling.
 * Uses a simple linear scale.
 *
 *
 */
public class LinearSliderConverter extends BaseSliderConverter
{
private double  scale;

public LinearSliderConverter( double minValue, double maxValue, JSlider slider )
{
    super( minValue, maxValue, slider );
    this.scale = ( this.maxValue - this.minValue ) / (double)this.sliderSize;
}

public int getSliderPosition( double value )
{
    int result = 0;

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

        if ( value >= minValue )
        {
            result = (int)Math.round( ( value - minValue ) / scale );
            break;
        }

    } while( false );

    return result;
}

public double getValue( int sliderPosition )
{
    double result = 0.0;

    result = minValue + ( sliderPosition * scale );

    return result;
}

}