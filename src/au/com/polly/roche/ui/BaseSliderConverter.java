package au.com.polly.roche.ui;

import javax.swing.JSlider;

/**
 * Allows simple conversions between slider position and value it is modelling.
 *
 *
 */
public abstract class BaseSliderConverter implements SliderConverter
{
private final static int debug = 1;
protected double  minValue;
protected double  maxValue;
protected int     sliderSize;

public BaseSliderConverter( double minValue, double maxValue, JSlider slider )
{
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.sliderSize = slider.getModel().getMaximum() - slider.getModel().getMinimum();

    if ( debug > 2 )
    {
        System.out.println( "BaseSliderConverter::constructor(minValue=" + minValue + ",maxValue=" + maxValue + ",JSlider=" + slider + ") set minValue=" + this.minValue + ", maxValue=" + this.maxValue + ", sliderSize=" + this.sliderSize );
    }
}

public double getMinimumValue()
{
    return this.minValue;
}

public double getMaximumValue()
{
    return this.maxValue;
}

public int getSliderSize()
{
    return this.sliderSize;
}


{

}

}
