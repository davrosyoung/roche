package au.com.polly.roche.ui;

import au.com.polly.roche.model.*;
import au.com.polly.roche.model.Point;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: May 28, 2009
 * Time: 10:07:56 PM
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
public class GraphicsModel
{
public final static int debug = 0;

List<Blob> list;

    int width;
    int height;
    ModelConstraints constraints;

    double x0;
    double x1;
    double y0;
    double y1;

    double xScale;
    double yScale;


public GraphicsModel( int width, int height, ModelConstraints constraints )
{
    this.width= width;
    this.height = height;

}

public void populate( Model model, ModelConstraints constraints )
{
    Color colour = Color.WHITE;
    int diameter = 10;
    list = new ArrayList<Blob>();

    this.constraints = constraints;

    x0 = constraints.getBottomLeftCorner().getX();
    x1 = constraints.getTopRightCorner().getX();
    y0 = constraints.getTopRightCorner().getY();
    y1 = constraints.getBottomLeftCorner().getY();

    xScale = (double)width / ( x1 - x0 );
    yScale = (double)height / ( y1 - y0 );
    
    for ( Particle p : model.particleList() )
    {
        diameter = (int)Math.ceil( p.getRadius() * 2 * xScale );

        if ( diameter < 3 )
        {
            diameter = 3;
        }



        colour = p.getColour();

        if ( debug > 2 )
        {
            System.out.println( "GraphicsModel::populate() radius=" + p.getRadius() + "m --> diameter=" + diameter + " pixels, colour=" + colour + "." );
        }

/*
        if ( p.getMass() < ModelConstants.EARTH_MASS * 0.11 )
        {
            diameter = 4;
            colour = Color.WHITE;
        }
        if ( p.getLabel().equals( "ra" ) )
        {
            diameter = 30;
            colour = Color.YELLOW;
        }

        if ( p.getLabel().equals( "terra" ) )
        {
            diameter = 12;
            colour = Color.BLUE;
        }
*/
        
        Blob b = new Blob( translate( p.getLocation() ), diameter, colour );

        if ( p instanceof Planet )
        {
            double rocheLimit = ((Planet)p).getRocheLimit();
            if ( rocheLimit > p.getRadius() )
            {
                int radius = (int)Math.ceil( rocheLimit * xScale );
                b.setRocheRingRadius( radius );
            }
        }

        list.add( b );
    }
}

protected Coordinate translate( Point p )
{

    int x = (int)Math.floor( ( p.getX() - x0 ) * xScale );
    int y = (int)Math.floor( ( p.getY() - y0 ) * yScale );

    Coordinate result = new Coordinate( x, y );

    return result;
}

public void paint( Graphics2D g )
{
    if ( ( list != null ) && ( ! list.isEmpty() ) )
    {
        for( Blob b : list )
        {
            b.paint( g );
        }
    }
}

}
