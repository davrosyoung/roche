package au.com.polly.roche.model;

import au.com.polly.roche.ui.ApplicationState;
import au.com.polly.roche.ui.ApplicationController;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: May 28, 2009
 * Time: 8:40:10 PM
 * To change this template use File | Settings | File Templates.
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
public class EarthSunModelHarness implements ApplicationController
{
    Model model;
    ModelConstraints constraints;
    ModelListener listener;
    SingleThreadedCruncher cruncher;

    public static void main( String[] argv )
    {
        EarthSunModelHarness harness = new EarthSunModelHarness();
        harness.go();
    }

    public EarthSunModelHarness()
    {
        model = new EarthSunModel();
        constraints = new EarthSunModelConstraints();
        listener = new TextModelListener();

        cruncher = new SingleThreadedCruncher( model, constraints, 1000L, this );
        cruncher.addListener(  listener, null );

    }

    public void go()
    {

            cruncher.start();
        
    }



    public void startAnimation() {
        return;
    }

    public void pauseAnimation() {
        return;
    }

    public void resetAnimation() {
        return;
    }

    public long getAnimationElapsedTime() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getAnimationModelFormattedElapsedTime(String template) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getAnimationModelElapsedTime() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getCountdownRemaining() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ApplicationState getApplicationState() {
        return ApplicationState.RUNNING;
    }

public void refreshAnimation(Model model, ModelConstraints constraints)
{
    return;
}

public void resetCountdown()
{
    return;
}
}