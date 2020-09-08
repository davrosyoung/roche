package au.com.polly.roche.model;

/**
 * Just a tag, means that we know that we need to check this object for proximity to any close
 * planets for breakup (or collision!!)
 *
 *
 * @author dave
 *         created: May 29, 2009 11:08:01 AM
 *         <p/>
 *         &copy; Copyright Polly Enterprises Pty Ltd 2009
 *         <p/>
 *         free for distribution for non-commercial use, as long as this notice
 *         is displayed in the resultant work. No warranty, implicit or explicit
 *         is provided by the use, in any manner, of this programme code, or applications of
 *         any kind utilizing it.
 */
public class Satellite extends Particle
{
public Satellite(String label, Point location, double mass) {
    super(label, location, mass);
}

public Satellite(String label, Point location, Vector velocity, double mass, double density) {
    super(label, location, velocity, mass, density);
}



/**
 *
 * @return en exact duplicate (albeit an evil one) of this particle..
 */
public Object clone()
{
    Satellite result = new Satellite(
                            this.getLabel(),
                            (Point)getLocation().clone(),
                            (Vector)getVelocity().clone(),
                            getMass(),
                            getDensity()
                    );
    result.setInternalCohesion( this.getInternalCohesion() );
    result.setColour( this.getColour() );
    return result;
}

}
