package au.com.polly.roche.ui;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents an on-screen sprite, or body. Simply used to store on-screen coordinates and colour. No
 * actual modelling is performed with this entity.
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
public class Blob
{
private final static int debug = 1;

private Coordinate pos;
private int diameter;
private int radius;
private Color colour;
private int rocheRingRadius = -1;
private final static float[] dashPattern = { 2.0F, 2.0F, 2.0F, 2.0F };
private final static Stroke dashedStroke = new BasicStroke( 1.0F, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 20.0F, dashPattern, 0.0F );


public Blob( int x, int y, int diameter, Color colour )
{
    this.pos = new Coordinate( x, y );
    this.diameter = diameter;
    this.colour = colour;
    radius = diameter / 2;
}

public Blob( Coordinate pos, int diameter, Color colour )
{
    this.pos = pos;
    this.diameter = diameter;
    this.colour = colour;
    radius = diameter / 2;
}

public void paint( Graphics2D g )
{
    Ellipse2D.Double circle;


    // draw a roche limit circle around this body if appropriate..
    // ----------------------------------------------------------------
    if ( rocheRingRadius > radius )
    {
        Stroke existingStroke = g.getStroke();
        g.setStroke( dashedStroke );
        circle = new Ellipse2D.Double( pos.x - rocheRingRadius, pos.y - rocheRingRadius, rocheRingRadius * 2, rocheRingRadius * 2 );
        g.setColor( Color.ORANGE );
        g.draw( circle );
        g.setStroke( new BasicStroke() );
    }

    circle = new Ellipse2D.Double( pos.x - radius, pos.y - radius, diameter, diameter );
    g.setColor( colour );
    g.fill( circle );

    if ( debug > 3 )
    {
        System.out.println( "Blob::paint(): painting " + colour + " circle, diameter=" + diameter + " at x=" + ( pos.x - radius ) + ", y=" + ( pos.y - radius ) );
    }


}


public int getRocheRingRadius()
{
    return rocheRingRadius;
}

public void setRocheRingRadius(int rocheRingRadius)
{
    this.rocheRingRadius = rocheRingRadius;
}
}
