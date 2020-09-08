package au.com.polly.roche.model;

/**
 * We've added a roche limit field, this serves two purposes;
 * - we use it to display the roche limit in the animation canvas
 * - we use it to determine if a satellite has approached too closely and has to be disintegrated.
 * 
 * @author dave
 *         created: May 29, 2009 11:06:47 AM
 *         <p/>
 *         &copy; Copyright Polly Enterprises Pty Ltd 2009
 *         <p/>
 *         free for distribution for non-commercial use, as long as this notice
 *         is displayed in the resultant work. No warranty, implicit or explicit
 *         is provided by the use, in any manner, of this programme code, or applications of
 *         any kind utilizing it.
 */
public class Planet extends Particle
{
private double rocheLimit;

public Planet(String label, Point location, double mass)
{
    super(label, location, mass);
}

public Planet(String label, Point location, Vector velocity, double mass, double density)
{
    super(label, location, velocity, mass, density);
}

public Planet(String label, Point location, Vector velocity, double mass, double density, double rocheLimit )
{
    this(label, location, velocity, mass, density);
    setRocheLimit( rocheLimit );
}

public double getRocheLimit()
{
    return rocheLimit;
}

public void setRocheLimit(double rocheLimit)
{
    this.rocheLimit = rocheLimit;
}



/**
 *
 * @return en exact duplicate (albeit an evil one) of this particle..
 */
public Object clone()
{
    Planet result = new Planet(
                            this.getLabel(),
                            (Point)getLocation().clone(),
                            (Vector)getVelocity().clone(),
                            getMass(),
                            getDensity()
                    );
    result.setInternalCohesion( this.getInternalCohesion() );
    result.setColour( this.getColour() );
    result.setRocheLimit( this.getRocheLimit() );
    return result;
}

}
