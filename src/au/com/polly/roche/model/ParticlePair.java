package au.com.polly.roche.model;

import au.com.polly.roche.util.HashCodeUtil;

/**
 *
 *
 *  * We use the particle pair as a way of keeping track of the interactions
 * between pairs of particles which have already been calculated. We deliberately
 * do *not* care about the order of the particles in the pair.

 * @author dave
 *         created: May 29, 2009 9:11:23 PM
 *         <p/>
 *         <p/>
 *         &copy; Copyright Polly Enterprises Pty Ltd 2009
 *         <p/>
 *         <p>
 *         Intended for demonstration purposes only, not intended for
 *         use within a production environment.
 *         Free for distribution for non-commercial use, as long as this notice
 *         is displayed in the resultant work. No warranty, implicit or explicit
 *         is provided by the use, in any manner, of this programme code, or applications of
 *         any kind utilizing it.
 *         </p>
 */
public class ParticlePair
{
private Particle alpha;
private Particle beta;
private ParticlePair gegenteil;     // gegenteil is "opposite" for "against part" or opposite.

ParticlePair( Particle a, Particle b )
{
    alpha = a;
    beta = b;

    if ( ( a == null ) || ( b == null ) )
    {
        throw new NullPointerException( "Both particles must be non null!!" );
    }

    if ( ( a == b ) || ( a.equals( b ) ) )
    {
        throw new IllegalArgumentException( "Arguments must differ" );
    }
}


/*
// we don't care which way around alpha and beta are ... oh yes we do!!
// ----------------------------------------------------------------------
public int hashCode()
{
    return alpha.hashCode() + ( beta.hashCode() * HashCodeUtil.SEED );
}
*/

public int hashCode()
{
    int result;

    result = HashCodeUtil.SEED;
    result = HashCodeUtil.hash( result, alpha.getLabel() );
    result = HashCodeUtil.hash( result, alpha.getMass() );
    result = HashCodeUtil.hash( result, beta.getLabel() );
    result = HashCodeUtil.hash( result, beta.getMass() );

    return result;
}

/**
 *
 * @return the same particle pair, but in reverse order.
 */
ParticlePair getOpposite()
{
    if ( gegenteil == null )
    {
        gegenteil = new ParticlePair( beta, alpha );
    }

    return gegenteil;
}

/**
 * overriding hashCode() means overriding equals!!
 *
 * @param other the object to compare ourselves too...
 * @return
 */
public boolean equals( Object other )
{
    boolean result = false;
    ParticlePair otherPair;

    do {

        // return false if other object is null!!
        // --------------------------------------
        if ( other == null )
        {
            break;
        }

        // for efficiency...
        // -------------------
        if ( this == other )
        {
            result = true;
            break;
        }

        // other object must also be a particle pair
        // ------------------------------------------
        if ( ! ( other instanceof ParticlePair ) )
        {
            break;
        }

        otherPair = (ParticlePair)other;

        if (
                ( alpha == null )
             && ( beta == null )
             && ( otherPair.alpha == null )
             && ( otherPair.beta == null )
        )
        {
            result = true;
            break;
        }

        // do alpha and beta in both pairs match?
        // ---------------------------------------
        result = this.hashCode() == other.hashCode();

    } while( false );

    return result;
}


/**
 *
 * @return the first particle in the ordered pair
 */
public Particle getAlpha()
{
    return alpha;
}

/**
 * 
 * @return the second particle in the ordered pair
 */
public Particle getBeta()
{
    return beta;
}


public String toString()
{
    return( "pair(alpha=" + alpha.getLabel() + ",beta=" + beta.getLabel() + ",objid=" + Integer.toHexString(System.identityHashCode( this )) + ")" );
}


}

