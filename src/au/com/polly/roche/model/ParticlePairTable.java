package au.com.polly.roche.model;

import java.util.Map;
import java.util.HashMap;

/**
 * @author dave
 * created: May 29, 2009 8:17:13 PM
 *
 *  &copy; Copyright Polly Enterprises Pty Ltd 2009
 *
 *  free for distribution for non-commercial use, as long as this notice
 *  is displayed in the resultant work. No warranty, implicit or explicit
 *  is provided by the use, in any manner, of this programme code, or applications of
 *  any kind utilizing it.
 */
public class ParticlePairTable<T>
{
Map<ParticlePair,T> map;


public ParticlePairTable()
{
    map = new HashMap<ParticlePair,T>();
}

/**
 *
 * @param pair the particle pair to query for.
 * @return whether or not an entry exists for this particle pair yet or not.
 */
public boolean containsEntry( ParticlePair pair )
{
    boolean result = false;

    synchronized( map )
    {
        result = map.containsKey( pair );
    }

    return result;
}


/**
 *
 * @param alpha - particle alpha
 * @param beta - particle beta
 * @return  the data element for particle pair alpha and beta.
 */
public T get( Particle alpha, Particle beta )
{
    T result = null;
    ParticlePair pear = new ParticlePair( alpha, beta );

    return get( pear );
}


/**
 *
 * @param pear identifies the pair of particles that the data is to be extracted for.
 * @return the data element for the supplied particle pair.
 */
public T get( ParticlePair pear )
{
    T result = null;
    synchronized( map )
    {
        if ( containsEntry( pear ) )
        {
            result = map.get( pear );
        }
    }

    return result;
}

/**
 *
 *
 * @param alpha
 * @param beta
 * @param data
 */
public void put( Particle alpha, Particle beta, T data )
{
    ParticlePair pair = new ParticlePair( alpha, beta );
    put( pair, data );

}

public void put( ParticlePair pair, T data )
{
    ParticlePair gegenteil;

    gegenteil = pair.getOpposite();

    map.put( pair, data );

    // if the object we're depositing has a mirror image, or opposite, then
    // record that value for the reverse order particle pair ...........
    // -----------------------------------------------------------------
    if ( data instanceof Opposable)
    {
        data = (T) ((Opposable)data).getOpposite();
    }

    synchronized( map )
    {
        map.put( gegenteil, data );
    }
}

}