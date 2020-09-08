package au.com.polly.roche.ui;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: Jun 2, 2009
 * Time: 4:58:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SliderConverter
{
double getMinimumValue();

double getMaximumValue();

int getSliderSize();

int getSliderPosition( double value );

double getValue( int sliderPosition );
}
