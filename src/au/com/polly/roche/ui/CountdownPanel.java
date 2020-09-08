package au.com.polly.roche.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

/**
 * Provides a countdown to the animation starting, and then displays the elapsed time once
 * the animation commences.
 *
 *
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: May 27, 2009
 * Time: 11:30:38 AM
 * To change this template use File | Settings | File Templates.
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
public class CountdownPanel extends JPanel implements ActionListener
{
private final static int debug = 0;

/**
 * Button used to either stop or abort the animation...
 */
JButton goButton;

/**
 * Value displayed to the left of the countdown planel
 */
JLabel  label;

/**
 * Changing value ... either the countdown, or the elapsed time.
 */
JLabel  value;

/**
 * reference to timer object, we only use this to check to see
 * if an action event is related to the timer..
 */
Timer timer;

/**
 * enables us to control and determine state of the application...
 */
ApplicationController controller;

/**
 * enables consistent formatting of numeric values....
 */
NumberFormat two;

public CountdownPanel(ApplicationController controller, Timer timer )
{
    super();

    this.timer = timer;
    this.controller = controller;
    two = NumberFormat.getIntegerInstance();
    two.setMaximumIntegerDigits( 2 );
    two.setMinimumIntegerDigits( 2 );

    goButton = new JButton( "go!" );
    goButton.addActionListener( this );
    label = new JLabel( "Countdown:" );
    value = new JLabel( "             30" );

    add( label, BorderLayout.WEST );
    add( value, BorderLayout.CENTER );
    add( goButton, BorderLayout.EAST );
}

public void actionPerformed(ActionEvent event)
{
    do {
        if ( event.getSource() == goButton )
        {

            if ( debug > 0 )
            {
                System.out.println( "CountdownPanel::actionPerformed(): go button event. event.getActionCommand()=" + event.getActionCommand() + ", currentState=" + controller.getApplicationState() );
            }

            if ( controller.getApplicationState() == ApplicationState.COUNTDOWN )
            {
                controller.startAnimation();
                goButton.setText( "abort" );
                label.setText( "Elapsed" );
                value.setText( "00:00:00" );
                break;
            }

            if ( controller.getApplicationState() == ApplicationState.RUNNING )
            {
                controller.resetAnimation();
                goButton.setText( "go!" );
                label.setText( "Countdown" );
                value.setText( "        30" );
            }
        }

        if ( event.getSource() == timer )
        {

            if ( debug > 2 )
            {
                System.out.println( "CountdownPanel::actionPerformed(): timer event. state=" + controller.getApplicationState() + ", event.actionCommand=" + event.getActionCommand() );
            }

            if ( controller.getApplicationState() == ApplicationState.COUNTDOWN )
            {
                long seconds = controller.getCountdownRemaining() / 1000;
                value.setText( "             " + two.format( seconds ) );

                if ( seconds < 1 )
                {
                    controller.startAnimation();
                    goButton.setText( "abort" );
                }

                if ( debug > 3 )
                {
                    System.out.println( "CountdownPanel::actionPerformed(): just set value field to " + seconds + ", controller.getCountdownRemaining()=" + controller.getCountdownRemaining() );
                }

                break;
            }

            if ( controller.getApplicationState() == ApplicationState.RUNNING )
            {
                String formatted = controller.getAnimationModelFormattedElapsedTime( "%DDD days %hh:%mm:%ss");
                value.setText( formatted );
                break;
            }
        }

        if ( debug > 2 )
        {
            System.out.println( "CountdownPanel::actionPerformed(): some other event, source=" + event.getSource() );
        }


    } while( false );

}

}
