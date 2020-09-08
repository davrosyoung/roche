package au.com.polly.roche.model;

import au.com.polly.roche.ui.Coordinate;
import au.com.polly.roche.util.HashCodeUtil;

import java.util.List;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.awt.*;

/**
 *  Represents a body in our model. It can be subjected to collisions, gravtiational interaction and
 * disintegration. This sort of treatment is normally dished out by a cruncher. A collection of these
 * bodies is collected together in a model.
 *
 * @author Dave Young
 *
 * &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 * free for distribution for non-commercial use, no warranty, implicit or explicit
 * is provided by the use, in any manner, of this programme code, or applications of
 * any kind utilizing it.
 */
public class Particle {
    private final static int debug = 0;

    static NumberFormat three;

    private String  label;
    private double  mass;
    private Point   location;
    private Vector velocity;
    private double  density;
    private Color   colour;
    transient private Vector  force;
    transient private Vector  zForce;
    transient private long    lastUpdate = -1L;
    transient private boolean radiusStale = true;
    transient private double  radius;

    /** how strongly this body is held together. a value of zero, means not at all. we simply "add" internal
     * gravitational strength to the internal body as this value increases.
     */
    private double  internalCohesion;


    static {
        three = NumberFormat.getIntegerInstance();
        three.setMinimumIntegerDigits( 3 );
        three.setMaximumIntegerDigits( 3 );
    }


    /**
     *
     * @param label used for diagnosis
     * @param location where this particle is located
     * @param mass mass of this body in kg
     */
    public Particle( String label, Point location, double mass )
    {
        this( label, location, new Vector( 0.0, 0.0 ), mass, 1000.0 );
    }

    /**
     *
     * @param label used for diagnosis
     * @param location where this particle is located
     * @param velocity it's current velocity
     * @param mass mass of this body in kg
     * @param density density of this body in kg per cubic metre. water=1000
     */
    public Particle( String label, Point location, Vector velocity, double mass, double density )
    {
        this( label, location, velocity, mass, density, 1.0, Color.WHITE );
    }

    /**
     *
     * @param label used for diagnosis
     * @param location where this particle is located
     * @param velocity it's current velocity
     * @param mass mass of this body in kg
     * @param density density of this body in kg per cubic metre. water=1000
     */
    public Particle( String label, Point location, Vector velocity, double mass, double density, double internalCohesion, Color colour )
    {
        this.label = label;
        this.location = location;
        this.velocity = velocity;
        setMass( mass );
        setDensity( density );
        this.internalCohesion = internalCohesion;
        this.colour = colour;
    }
    
    /**
     *
     * @param newForce to be affected against this body, will be distributed across
     * the mass of the body, and result in acceleration of the body (i.e.: a change
     * in velocity).
     */
    public void impartForce( Vector newForce )
    {
        // if no force have yet been applied to this body, then
        // use the supplied force.
        // -----------------------------------------------------
        if ( this.force == null )
        {
            this.force = (Vector) newForce.clone();
        } else {
            // otherwise add the supplied force to the existing
            // force.
            // -----------------------------------------------
            if ( newForce != null )
            {
                this.force = this.force.add( newForce );
            }
        }
        return;
    }

    /**
     *
     * @param newForce to be affected against this body, will be distributed across
     * the mass of the body, and result in acceleration of the body (i.e.: a change
     * in velocity). This will be instantaneous, and not scaled across the time since
     * the last update.
     */
    public void impartZeroTimeForce( Vector newForce )
    {
        // if no force have yet been applied to this body, then
        // use the supplied force.
        // -----------------------------------------------------
        if ( this.zForce == null )
        {
            this.zForce = (Vector) newForce.clone();
        } else {
            // otherwise add the supplied force to the existing
            // force.
            // -----------------------------------------------
            this.zForce = this.zForce.add( newForce );
        }
        return;
    }

    public void update( ModelConstraints constraints )
    {
        Vector movement;
        // only update our position if this is not the initial
        // iteration of the model...
        // ---------------------------------------------------
        if ( lastUpdate > 0 )
        {
            // produce a movement vector, which is a product of the
            // current velocity multiplied by the fraction of a second
            // since the previous update...
            // ---------------------------------------------------------
            double elapsed = ( (double)( System.currentTimeMillis() - lastUpdate ) / 1000.0 ) * constraints.getTimeScaleFactor();

            if ( debug > 2 )
            {
                System.out.println( "Particle::update(): elapsed=" + elapsed + ", constraints.getTimeScaleFactor()=" + constraints.getTimeScaleFactor() );
            }

            if ( force != null )
            {
                // now the force must be multiplied by the number of seconds...
                // -------------------------------------------------------------
                velocity.x += ( force.x * elapsed ) / mass;
                velocity.y += ( force.y * elapsed ) / mass;

            }

            // now add any zero time (instantaneous) forces to be applied.
            // ----------------------------------------------------------
            if ( zForce != null )
            {
                velocity.x += zForce.x / mass;
                velocity.y += zForce.y / mass;
            }

            // now update the position of this object....
            // ---------------------------------------------
            movement = (Vector) getVelocity().clone();
            movement.multiply( elapsed );

            if ( debug > 3 )
            {
                System.out.println( "Particle::update(): label=\"" + label + "\" movement vector=" + movement );
            }
            getLocation().add( movement );

        }
        lastUpdate = System.currentTimeMillis();
        this.force = null;
        this.zForce = null;
    }


    /**
     * Break this particle up into a number of smaller bodies, all travelling along the same
     * velocity vector.
     *
     * @param numberBodies
     * @return
     */
    public List<Particle> disintegrate( int numberBodies, DisintegrationMatrix matrix )
    {
        List<Particle> children = new ArrayList<Particle>();
        List<Coordinate> orderList = null;
        double childMass = getMass() / numberBodies;
        double childVolume = childMass / density;
        double childRadius = Math.pow( childVolume / ( 1.33333333333 * Math.PI ), 0.333333333333333333 );
        double latticeSize = childRadius * 2.2;
        int counter=1;
        Particle child;
        // todo: define colours array properly...

        Color availableColours[] = { Color.WHITE, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                                        Color.ORANGE, Color.GRAY, Color.MAGENTA };
        Color childColour;
        String label;
        Vector v;
        double xOffset;
        double yOffset;

        orderList = matrix.getCoordinateList();

        for( counter = 0; counter < numberBodies; counter++ )
        {
            Coordinate where = orderList.get( counter );
            xOffset = where.getX() * latticeSize;
            yOffset = where.getY() * latticeSize;

            Point location = new Point( getLocation().x + xOffset, getLocation().y + yOffset );



            // ok, let's give them a slight velocity away from the centre..
            // -----------------------------------------------------------
            v = (Vector)velocity.clone();
 //           v.add( new Vector( xOffset / 5000.0 , yOffset / 5000.0 ) );

            label = getLabel() + "-child-" + three.format( counter );
            childColour = availableColours[ counter % 8 ];
            child = new Particle( label, location, v, childMass, density, internalCohesion, childColour );
            children.add( child );
        }


        return children;
    }

    /**
     * Determines the radius of this particle, based upon it's mass and density.
     *
     *
     * @return the radius of this particle, in metres
     */
    public double getRadius()
    {
        if ( radiusStale )
        {
            double volume = mass / density;
            radius = Math.pow( volume / ( 1.3333333333333333333 * Math.PI ), 0.333333333333333333333 );
            radiusStale = false;
        }

        return radius;
    }
    

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getMass() {
        return mass;
    }

    public void setMass( double mass ) {
        this.mass = mass;
        this.radiusStale = true;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
        this.radiusStale = true;
    }


    public double getInternalCohesion() {
        return internalCohesion;
    }

    public void setInternalCohesion(double internalCohesion) {
        this.internalCohesion = internalCohesion;
    }

public Color getColour() {
    return colour;
}

public void setColour(Color colour) {
    this.colour = colour;
}

/**
     *
     * @return en exact duplicate (albeit an evil one) of this particle..
     */
    public Object clone()
    {
        Particle result = new Particle(
                                this.label,
                                (Point)this.location.clone(),
                                (Vector)this.velocity.clone(),
                                this.mass,
                                this.density,
                                this.internalCohesion,
                                this.colour
                        );
        return result;
    }


/**
 *
 * @param other  vector to compare this vector to.
 * @return whether this vector is equal to another vector. To avoid confusion with
 * floating point error, a small margin of error is allowed.
 */
@Override public boolean equals( Object other )
{
    boolean result = false;
    Particle o;

    do {

        if ( other == null )
        {
            break;
        }

        if ( this == other )
        {
            result = true;
            break;
        }

        if ( ! ( other instanceof Particle ) )
        {
            break;
        }

        o = (Particle) other;
        result = hasEqualState( o );

    } while( false );

    return result;
}

/**
* Ensure all non-transient state is equal between the two particles...
*/
private boolean hasEqualState( Particle other )
{
    boolean result =
    ( this.label == null ? other.label==null : this.label.equals( other.label ) ) &&
    ( this.mass == other.mass ) &&
    ( this.density == other.density ) &&
    ( this.internalCohesion == other.internalCohesion ) &&
    ( this.location == null ? other.location == null : this.location.equals( other.location ) ) &&
    ( this.velocity == null ? other.velocity == null : this.velocity.equals( other.velocity ) );

    return result;
}


/**
 * overriden, such that equality, and hashcode is based upon the non
 * transient fields of the particle.
 *
 * @return
 */
@Override public int hashCode()
{
    int result = HashCodeUtil.SEED;
    result = HashCodeUtil.hash( result, label );
    result = HashCodeUtil.hash( result, mass );
    result = HashCodeUtil.hash( result, internalCohesion );
    result = ( result * HashCodeUtil.SEED ) + location.hashCode();
    result = ( result * HashCodeUtil.SEED ) + velocity.hashCode();
    result = HashCodeUtil.hash( result, density );


    return result;
}


public String toString()
{
    StringBuffer out = new StringBuffer();

    out.append( getLabel() + "> location:" + getLocation() + ", velocity=" + getVelocity() + ", mass=" + getMass() + "kg, radius=" + getRadius() + ", colour=" + getColour() );

    return out.toString();
}


}
