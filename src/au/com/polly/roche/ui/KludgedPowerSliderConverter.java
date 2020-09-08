package au.com.polly.roche.ui;

import javax.swing.JSlider;

/**
 * Allows simple conversions between slider position and value it is modelling.
 *
 *
 * Modifies original version by streteching out the second half of the slider (the right half) only...
 *
 */
public class KludgedPowerSliderConverter extends BaseSliderConverter
{
    private double  factor;

    public KludgedPowerSliderConverter( double minValue, double maxValue, JSlider slider )
    {
        super( minValue, maxValue, slider );
        this.factor = Math.pow( ( this.maxValue - this.minValue ), 1.00 / (double) this.sliderSize );
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
            result = (int)Math.round( Math.log( value - minValue ) / Math.log( factor ) );
            break;
        }

    } while( false );

    return result;
}

public double getValue( int sliderPosition )
{
    double result = 0.0;

    result = minValue + Math.pow( this.factor, sliderPosition );

    return result;
}

}