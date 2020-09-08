package au.com.polly.roche.ui;

import au.com.polly.roche.model.ModelListener;
import au.com.polly.roche.model.Model;
import au.com.polly.roche.model.ModelConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * component upon which we draw our little universe...
 *
 *
 * 
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 */
public class AnimationCanvas extends JPanel implements ModelListener
{
private final static int debug = 1;
static long created = System.currentTimeMillis();
BufferedImage buffer;  // The image we use for double-buffering
Graphics2D osg;        // Graphics2D object for drawing into the buffer
GraphicsModel graphicsModel;
private final static int DEFAULT_WIDTH = 600;
private final static int DEFAULT_HEIGHT = 600;
private final static float[] dashPattern = { 8.0F, 8.0F, 8.0F, 8.0F };
private final static Stroke dashedStroke = new BasicStroke( 2.0F, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 20.0F, dashPattern, 0.0F );


public AnimationCanvas( ModelConstraints constraints )
{
    super();

    //
    setPreferredSize( new Dimension( DEFAULT_WIDTH, DEFAULT_HEIGHT ) );
    graphicsModel = new GraphicsModel( DEFAULT_WIDTH, DEFAULT_HEIGHT, constraints );
}


/**
 * Swing calls this method to ask the component to redraw itself.
 * This method uses double-buffering to make the animation smoother.
 * Swing does double-buffering automatically, so this may not actually
 * make much difference, but it is important to understand the technique.
 **/
public void paintComponent(Graphics g)
{
    super.paintComponent( g );

    Graphics2D g2 = (Graphics2D)g;

    g2.setBackground( Color.BLACK );
    g2.clearRect( 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT );

    Stroke existingStroke = g2.getStroke();

    g2.setStroke( dashedStroke );
    g2.setColor( Color.ORANGE );
    g2.drawRect( 0, 0, DEFAULT_WIDTH - 1, DEFAULT_HEIGHT - 1 );
    graphicsModel.paint( g2 );

    g2.setStroke( existingStroke );

    /*
    Ellipse2D.Double circle;

    long elapsed = System.currentTimeMillis() - created;
    double r = ( elapsed % 10000L ) / 40;
    System.out.println( "Top::paintComponent(): r=" + r );

    circle = new Ellipse2D.Double( 200 - ( r / 2 ), 200 - ( r / 2 ), r, r );


    g2.setBackground( Color.YELLOW );
    g2.setColor( Color.BLUE );
    g2.clearRect( 0, 0, 400, 400 );
    g2.draw( circle );
    */
}


/**
 * updates the animation canvas (on-screen) with the supplied version of the model to be displayed.
 *
 * @param model the model which has just been updated.
 * @param constraints
 * @param auxilliary any additional object which the listener registered
 */
public void update(Model model, ModelConstraints constraints, Object auxilliary)
{
    if ( debug > 2 )
    {
        System.out.println( "AnimationCanvas::update() method invoked." );
    }
    // just repaint everything for now......
    graphicsModel.populate( model, constraints );
    repaint();
    return;
}
}
