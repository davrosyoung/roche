package au.com.polly.roche.model;

import au.com.polly.roche.ui.Coordinate;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.text.NumberFormat;

/**
 * Distribution of particles around a central point, used when
 * disintegrating a body.
 *
 *
 * @author dave
 *         created: May 29, 2009 1:53:20 PM
 *         <p/>
 *         &copy; Copyright Polly Enterprises Pty Ltd 2009
 *         <p/>
 *         free for distribution for non-commercial use, as long as this notice
 *         is displayed in the resultant work. No warranty, implicit or explicit
 *         is provided by the use, in any manner, of this programme code, or applications of
 *         any kind utilizing it.
 */
public class DisintegrationMatrix
{
    /**
     * array of distances from the centre of the progenitor.
     */
    private double[][] distanceMatrix;
    /**
     * used when building up the order list, this map records which locations get populated in which order.
     */
    private Map<Coordinate,Integer> occupied = null;

    /**
     * Contains the coordinates, in the list that a lattice of debris should be "built up"
     * around the core of a progenitor object.
     */
    private List<Coordinate> orderList;

    static NumberFormat two;
    static NumberFormat three;
    static NumberFormat twoDotFour;



    static {
        two = NumberFormat.getIntegerInstance();
        two.setMinimumIntegerDigits( 2 );
        two.setMaximumIntegerDigits( 2 );

        three = NumberFormat.getIntegerInstance();
        three.setMinimumIntegerDigits( 3 );
        three.setMaximumIntegerDigits( 3 );

        twoDotFour = NumberFormat.getNumberInstance();
        twoDotFour.setMinimumIntegerDigits( 2 );
        twoDotFour.setMaximumIntegerDigits( 2 );
        twoDotFour.setMinimumFractionDigits( 4 );
        twoDotFour.setMaximumFractionDigits( 4 );
    }


public DisintegrationMatrix( int length )
{
    orderList = new ArrayList<Coordinate>();

    double c = (double)( length - 1 ) / 2;
    distanceMatrix = new double[ length ][];
    for( int i = 0; i < length; i++ )
    {

        double dy = (double) i - c;

        distanceMatrix[ i ] = new double[ length ];
        for ( int j = 0; j < length; j++ )
        {
            double dx = (double) j - c;
            double distance = Math.sqrt( ( dx * dx ) + ( dy * dy ) );
            distanceMatrix[ i ][ j ] = distance;
        }
    }

    double current = -1;
    double m;                       // the minimum value yet found on this sweep througy the distance matrix.
    Coordinate cursor = null;
    Coordinate pos = null;
    occupied = new HashMap<Coordinate,Integer>();


    for( int i = 0; i < length * length; i++ )
    {
        m = 999999;
        // find the first unoccupied square which is above the current value,
        // but also the minimum value so...
        // ----------------------------------------------------------------
        for( int row = 0; row < length; row ++ )
        {
            for ( int column = 0; column < length; column++ )
            {
                pos = new Coordinate( row, column );

                if ( ! occupied.containsKey( pos ) )
                {
                    double v = distanceMatrix[ row ][ column ];
                    if ( ( v >= current ) && ( v < m ) )
                    {
                        m = v;
                        cursor = (Coordinate)pos.clone();
                    }
                }
            }
        }

        int offsetX = (int) Math.floor( c );
        int offsetY = (int) Math.floor( c );
        occupied.put( cursor, i );
        Coordinate translated = new Coordinate( cursor.getX() - offsetX, cursor.getY() - offsetY );
        orderList.add( translated );
        current = m;
    }
/*  not for production ... should really be implemented with log4j (todo!!!)
    System.out.println( "Orderlist.size()=" + orderList.size() );
    for( int i = 0; i < orderList.size(); i++ )
    {
        System.out.println( "orderList[ " + i + " ]=" + orderList.get( i ) );
    }


    System.out.println( "Order matrix:" );
    StringBuffer lineSeparatorBuffer = new StringBuffer( "+----------+" );
    for( int i = 0; i < distanceMatrix.length; i++ )
    {
        lineSeparatorBuffer.append( "----------+" );
    }
    String lineSeparator = lineSeparatorBuffer.toString();
    System.out.println( lineSeparator );

    System.out.print( "|          |" );
    for( int i = 0; i < length; i++ )
    {
        System.out.print( "    " + two.format( i ) + "    |" );
    }
    System.out.println();
    System.out.println( lineSeparator );

    for( int row = 0; row < length; row++ )
    {
        System.out.print( "|    " + two.format( row ) + "    |" );
        for( int column = 0; column < length; column++ )
        {
            pos = new Coordinate( row, column );
            System.out.print( "   " + three.format( occupied.get( pos ) ) + "    |" );
        }
        System.out.println();
    }
    System.out.println( lineSeparator );
*/
}

    /**
     *
     * @return a list of the offsets from the centre of a progenitor object that any children objects
     * should be populated.
     */
public List<Coordinate> getCoordinateList()
{
    return orderList;
}

public void output()
{
    Coordinate pos = null;
    int length = distanceMatrix.length;
    System.out.println( "DISTANCE MATRIX" );
    StringBuffer lineSeparatorBuffer = new StringBuffer( "+----------+" );
    for( int i = 0; i < length; i++ )
    {
        lineSeparatorBuffer.append( "----------+" );
    }
    String lineSeparator = lineSeparatorBuffer.toString();
    System.out.println( lineSeparator );

    System.out.print( "|          |" );
    for( int i = 0; i < distanceMatrix.length; i++ )
    {
        System.out.print( "    " + two.format( i ) + "    |" );
    }
    System.out.println();
    System.out.println( lineSeparator );

    for( int i = 0; i < distanceMatrix.length; i++ )
    {
        System.out.print( "|    " + two.format( i ) + "    |" );
        for( int j = 0; j < distanceMatrix.length; j++ )
        {
            System.out.print( "  " + twoDotFour.format( distanceMatrix[ i ][ j ] ) + " |" );
        }
        System.out.println();
        System.out.println( lineSeparator );
    }


    System.out.println();
    System.out.println();


    System.out.println( "Order matrix:" );
    System.out.println( lineSeparator );
    System.out.print( "|          |" );
    for( int i = 0; i < length; i++ )
    {
        System.out.print( "    " + two.format( i ) + "    |" );
    }
    System.out.println();
    System.out.println( lineSeparator );

    for( int row = 0; row < length; row++ )
    {
        System.out.print( "|    " + two.format( row ) + "    |" );
        for( int column = 0; column < length; column++ )
        {
            pos = new Coordinate( row, column );
            System.out.print( "   " + three.format( occupied.get( pos ) ) + "    |" );
        }
        System.out.println();
    }
    System.out.println( lineSeparator );
}

    public static void main( String[] argv )
    {
        long before;
        long after;
        long elapsed;
        DisintegrationMatrix m;
        before = System.currentTimeMillis();
        m = new DisintegrationMatrix( 21 );
        after = System.currentTimeMillis();
        elapsed = after - before;
        System.out.println( "It took " + elapsed + "ms to build the disintegration matrix" );
        System.out.flush();
        m.output();

//        m = new DisintegrationMatrix( 8 );
//        m.output();
        System.exit( 0 );
    }

}
