package au.com.polly.roche.ui;

import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
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
public class Boing extends JComponent
{


/**
 *
 * @param filename image to be bounced around the screen
 */
public Boing( String filename )
{
    Image sprite;
    BufferedImage bufferedSprite;
    MediaTracker tracker;
    long now;
    long then;
    long elapsed;
    JFrame frame;
    Graphics2D g;

    //          g.drawImage()


    // load up the image....
    // -------------------------
    try {
        sprite = getToolkit().getImage( filename );
        tracker = new MediaTracker( this );
        tracker.addImage( sprite, 1  );
        then = System.currentTimeMillis();
        do {
            try {
                Thread.sleep( 50 );
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            now = System.currentTimeMillis();
            elapsed = now - then;
        } while( ( !tracker.checkID( 1 ) ) && elapsed < 2000L );
    } catch (Exception e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
}

}
