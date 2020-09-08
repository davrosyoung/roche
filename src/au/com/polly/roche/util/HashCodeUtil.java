package au.com.polly.roche.util;

import java.lang.reflect.Array;


/**
 * Adapted from website: http://www.javapractices.com/topic/TopicAction.do?Id=28
 *
 * Collected methods which allow easy implementation of <code>hashCode</code>.
*
* Example use case:
* <pre>
*  public int hashCode(){
*    int result = HashCodeUtil.SEED;
*    //collect the contributions of various fields
*    result = HashCodeUtil.hash(result, fPrimitive);
*    result = HashCodeUtil.hash(result, fObject);
*    result = HashCodeUtil.hash(result, fArray);
*    return result;
*  }
* </pre>
 *
 * @author dave
 *         created: May 29, 2009 8:54:07 PM
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
public final class HashCodeUtil
{

  /**
  * An initial value for a <code>hashCode</code>, to which is added contributions
  * from fields. Using a non-zero value decreases collisons of <code>hashCode</code>
  * values.
  */
  public static final int SEED = 47;

  /**
  * booleans.
  */
  public static int hash( int aSeed, boolean aBoolean ) {
    return firstTerm( aSeed ) + ( aBoolean ? 1 : 0 );
  }

  /**
  * chars.
  */
  public static int hash( int aSeed, char aChar ) {
    return firstTerm( aSeed ) + (int)aChar;
  }

  /**
  * ints.
  */
  public static int hash( int aSeed , int aInt ) {
    /*
    * Implementation Note
    * Note that byte and short are handled by this method, through
    * implicit conversion.
    */
    return firstTerm( aSeed ) + aInt;
  }

  /**
  * longs.
  */
  public static int hash( int aSeed , long aLong ) {
    return firstTerm(aSeed)  + (int)( aLong ^ (aLong >>> 32) );
  }

  /**
  * floats.
  */
  public static int hash( int aSeed , float aFloat ) {
    return hash( aSeed, Float.floatToIntBits(aFloat) );
  }

  /**
  * doubles.
  */
  public static int hash( int aSeed , double aDouble ) {
    return hash( aSeed, Double.doubleToLongBits(aDouble) );
  }

  /**
  * <code>aObject</code> is a possibly-null object field, and possibly an array.
  *
  * If <code>aObject</code> is an array, then each element may be a primitive
  * or a possibly-null object.
  */
  public static int hash( int aSeed , Object aObject ) {
    int result = aSeed;
    if ( aObject == null) {
      result = hash(result, 0);
    }
    else if ( ! isArray(aObject) ) {
      result = hash(result, aObject.hashCode());
    }
    else {
      int length = Array.getLength(aObject);
      for ( int idx = 0; idx < length; ++idx ) {
        Object item = Array.get(aObject, idx);
        //recursive call!
        result = hash(result, item);
      }
    }
    return result;
  }


  /// PRIVATE ///
  private static final int fODD_PRIME_NUMBER = 37;

  private static int firstTerm( int aSeed ){
    return fODD_PRIME_NUMBER * aSeed;
  }

  private static boolean isArray(Object aObject){
    return aObject.getClass().isArray();
  }
}