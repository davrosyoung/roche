package au.com.polly.roche.ui;

import au.com.polly.roche.model.*;
import au.com.polly.roche.model.Point;
import au.com.polly.roche.util.RocheUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Control panel, enabling the settings for the roche limit simulation to be set...
 *
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
public class ControlPanel extends JPanel implements ActionListener, ModelConstants
{

private final static int debug = 0;

ApplicationController controller;
ModelSettings uiSettings;

LayoutManager layout;
JLabel satelliteMassLabel;
JLabel satelliteMassValue;
JSlider satelliteMassSlider;
SliderConverter satelliteMassConverter;

JLabel satelliteTypeLabel;
JPanel satelliteRadioButtonPanel;
ButtonGroup     satelliteTypeRadioButtonGroup;

Map<JRadioButton,SatelliteType> satelliteButtonMap = new HashMap<JRadioButton,SatelliteType>();
Map<SatelliteType,JRadioButton> buttonBySatelliteTypeMap = new HashMap<SatelliteType,JRadioButton>();

JLabel  orbitalRadiusLabel;
JTextField orbitalRadiusValue;
JSlider orbitalRadiusSlider;
SliderConverter orbitalRadiusConverter;

JLabel orbitalSpeedLabel;
JTextField orbitalSpeedValue;
JSlider orbitalSpeedSlider;
SliderConverter orbitalSpeedConverter;

JLabel planetTypeLabel;
JPanel planetRadioButtonPanel;
ButtonGroup     planetTypeRadioButtonGroup;
Map<JRadioButton,PlanetType> planetButtonMap = new HashMap<JRadioButton,PlanetType>();
Map<PlanetType,JRadioButton> buttonByPlanetTypeMap = new HashMap<PlanetType,JRadioButton>();

JLabel planetMassLabel;
JTextField planetMassValue;
JSlider planetMassSlider;
SliderConverter planetMassConverter;

JButton hitMe;

static NumberFormat oneDotTwo = NumberFormat.getNumberInstance();
static NumberFormat oneDotTwelve = NumberFormat.getNumberInstance();
static NumberFormat twoDotOne = NumberFormat.getNumberInstance();
static NumberFormat three = NumberFormat.getIntegerInstance();
static NumberFormat four = NumberFormat.getIntegerInstance();
static NumberFormat fourDotSix = NumberFormat.getNumberInstance();
static NumberFormat eightDotFour = NumberFormat.getNumberInstance();
static NumberFormat dotTwo = NumberFormat.getNumberInstance();
static NumberFormat dotFour = NumberFormat.getNumberInstance();
static NumberFormat dotSix = NumberFormat.getNumberInstance();



static {
    oneDotTwo.setMinimumFractionDigits( 2 );
    oneDotTwo.setMaximumFractionDigits( 2 );

    oneDotTwelve.setMinimumFractionDigits( 12 );
    oneDotTwelve.setMaximumFractionDigits( 12 );
    oneDotTwelve.setGroupingUsed( false );

    twoDotOne.setMinimumIntegerDigits( 2 );
    twoDotOne.setMaximumIntegerDigits( 2 );

    twoDotOne.setMinimumFractionDigits( 1 );
    twoDotOne.setMaximumFractionDigits( 1 );


    three.setMinimumIntegerDigits( 3 );
    three.setMaximumIntegerDigits( 3 );

    four.setMinimumIntegerDigits( 4 );
    four.setMaximumIntegerDigits( 4 );
    four.setGroupingUsed( false );

    fourDotSix.setMinimumIntegerDigits( 4 );
    fourDotSix.setMaximumIntegerDigits( 4 );
    fourDotSix.setMinimumFractionDigits( 6 );
    fourDotSix.setMaximumFractionDigits( 6 );
    fourDotSix.setGroupingUsed( false );

    eightDotFour.setMinimumIntegerDigits( 8 );
    eightDotFour.setMaximumIntegerDigits( 8 );
    eightDotFour.setMinimumFractionDigits( 4 );
    eightDotFour.setMaximumFractionDigits( 4 );
    eightDotFour.setGroupingUsed( false );

    dotSix.setMinimumIntegerDigits( 1 );
    dotSix.setMaximumIntegerDigits( 4 );
    dotSix.setMinimumFractionDigits( 6 );
    dotSix.setMaximumFractionDigits( 6 );
    dotSix.setGroupingUsed( false );

    dotFour.setMinimumIntegerDigits( 1 );
    dotFour.setMaximumIntegerDigits( 8 );
    dotFour.setMinimumFractionDigits( 4 );
    dotFour.setMaximumFractionDigits( 4 );
    dotFour.setGroupingUsed( false );

    dotTwo.setMinimumIntegerDigits( 1 );
    dotTwo.setMaximumIntegerDigits( 8 );
    dotTwo.setMinimumFractionDigits( 2 );
    dotTwo.setMaximumFractionDigits( 2 );
    dotTwo.setGroupingUsed( false );
}

public ControlPanel( ApplicationController controller )
{
    super();

    this.controller = controller;

    GridBagConstraints constraints;
    GridBagConstraints labelConstraints;
    GridBagConstraints valueConstraints;
    GridBagConstraints sliderConstraints;
    layout = new GridBagLayout();

    setLayout( layout );

    satelliteMassLabel = new JLabel( "Satellite Mass" );

    labelConstraints = new GridBagConstraints();
    labelConstraints.gridx = 0;
    labelConstraints.gridy = 0;
    labelConstraints.gridwidth = 3;
    labelConstraints.gridheight = 1;
    labelConstraints.anchor = GridBagConstraints.WEST;
    add( satelliteMassLabel, labelConstraints );

    satelliteMassValue = new JLabel( oneDotTwelve.format( 0.0 ) + "M\u2295" );
    valueConstraints = new GridBagConstraints();
    valueConstraints.gridx = GridBagConstraints.RELATIVE;
    valueConstraints.gridy = 0;
    valueConstraints.gridwidth = 2;
    valueConstraints.gridheight = 1;
    valueConstraints.anchor = GridBagConstraints.WEST;
    add( satelliteMassValue, valueConstraints );

    satelliteMassSlider = new JSlider( 0, 200, 100 );
    satelliteMassSlider.addChangeListener( new SatelliteMassSliderListener() );
    sliderConstraints = new GridBagConstraints();
    sliderConstraints.gridx = GridBagConstraints.RELATIVE;
    sliderConstraints.gridy = 0;
    sliderConstraints.gridwidth = 5;
    sliderConstraints.gridheight = 1;
    sliderConstraints.anchor = GridBagConstraints.WEST;

    add( satelliteMassSlider, sliderConstraints );

    satelliteRadioButtonPanel = new JPanel();

    satelliteTypeLabel = new JLabel( "Satellite Type" );
    labelConstraints.gridy = GridBagConstraints.RELATIVE;
    labelConstraints.gridheight = 3;
    add( satelliteTypeLabel, labelConstraints );

    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 1;
    constraints.gridwidth = 5;
    constraints.gridheight = 3;
    constraints.anchor = GridBagConstraints.WEST;


    satelliteTypeRadioButtonGroup = new ButtonGroup();

    for( SatelliteType moonType: SatelliteType.values() )
    {
        JRadioButton button = new JRadioButton( moonType.getLabel(), false );
        button.addActionListener( this );
        satelliteTypeRadioButtonGroup.add( button );
        satelliteRadioButtonPanel.add( button );
        satelliteButtonMap.put( button, moonType );
        buttonBySatelliteTypeMap.put( moonType, button );
    }

    add(satelliteRadioButtonPanel, constraints );

    orbitalRadiusLabel = new JLabel( "Orbital Radius" );
    labelConstraints.gridheight = 1;
    labelConstraints.gridy=4;
    add( orbitalRadiusLabel, labelConstraints );

    orbitalRadiusValue = new JTextField( "100% RL" );
    orbitalRadiusValue.setColumns( 10 );
    orbitalRadiusValue.setEditable( false );
    valueConstraints.gridy=4;
    add( orbitalRadiusValue, valueConstraints );

    orbitalRadiusSlider = new JSlider( 0, 200, 100 );
    sliderConstraints.gridy=4;
    add( orbitalRadiusSlider, sliderConstraints );
    orbitalRadiusSlider.addChangeListener( new OrbitalRadiusSliderListener() );

    orbitalSpeedLabel = new JLabel( "Orbital Speed" );
    labelConstraints.gridy=5;
    add( orbitalSpeedLabel, labelConstraints );

    orbitalSpeedValue = new JTextField( "0.0kms-1" );
    orbitalSpeedValue.setColumns( 10 );
    valueConstraints.gridy=5;
    orbitalSpeedValue.setEditable( false );
    add( orbitalSpeedValue, valueConstraints );

    orbitalSpeedSlider = new JSlider( 0, 200 ,100 );
    orbitalSpeedSlider.addChangeListener( new OrbitalSpeedMassSliderListener() );
    sliderConstraints.gridy=5;
    add( orbitalSpeedSlider, sliderConstraints );

    planetRadioButtonPanel = new JPanel();

    planetTypeLabel = new JLabel( "Planet Type" );
    labelConstraints.gridy = GridBagConstraints.RELATIVE;
    labelConstraints.gridheight = 3;
    add( planetTypeLabel, labelConstraints );

    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 6;
    constraints.gridwidth = 5;
    constraints.gridheight = 3;
    constraints.anchor = GridBagConstraints.WEST;


    planetTypeRadioButtonGroup = new ButtonGroup();

    for( PlanetType planetType: PlanetType.values() )
    {
        JRadioButton button = new JRadioButton( planetType.getLabel(), false );
        button.addActionListener( this );
        planetTypeRadioButtonGroup.add( button );
        planetRadioButtonPanel.add( button );
        planetButtonMap.put( button, planetType );
        buttonByPlanetTypeMap.put( planetType, button );

    }

    add( planetRadioButtonPanel, constraints );


    planetMassLabel = new JLabel( "Planet Mass" );
    labelConstraints.gridy=9;
    add( planetMassLabel, labelConstraints );

    planetMassValue = new JTextField( dotTwo.format( 500 ) + " M\u2295" );
    planetMassValue.setColumns( 10 );
    valueConstraints.gridy=9;
    planetMassValue.setEditable( false );
    add( planetMassValue, valueConstraints );

    planetMassSlider = new JSlider( 0, 200, 100 );
    planetMassSlider.addChangeListener( new PlanetMassSliderListener() );
    sliderConstraints.gridy=9;
    add( planetMassSlider, sliderConstraints );

    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.gridwidth = 5;
    constraints.gridheight = 1;
    constraints.anchor = GridBagConstraints.SOUTH;

    hitMe = new JButton( "Hit Me!!" );
    hitMe.addActionListener( this );
    add( hitMe, constraints );


}


public double getPlanetDensity()
{
    return uiSettings.getPlanetDensity();
}

public double getPlanetMass()
{
    double result;

    result = planetMassConverter.getValue( planetMassSlider.getValue() );

    return result;
}

public double getSatelliteDensity()
{
    return uiSettings.getSatelliteDensity();
}

public double getSatelliteMass()
{
    return satelliteMassConverter.getValue( satelliteMassSlider.getValue() );
}


/**
 *
 * @return  a model universe, based of particles, based upon the input settings.
 */
public Model getModel()
{
    Model result = new UIDerivedModel();
    Point planetLocation;
    Vector planetVelocity;
    Point satelliteLocation = null;
    Vector satelliteVelocity = null;
    Planet planet = null;
    Satellite satellite= null;

    double rocheLimit;


    planetLocation = new Point( 0.0, 0.0 );
    planetVelocity = new Vector( 0.0, 0.0 );

    satelliteLocation = new Point( 0.0, uiSettings.getOrbitalRadius() );
    // todo: why is teh satellite velocity vector so large???
    satelliteVelocity = new Vector( uiSettings.getOrbitalSpeed(), 0.0 );

    if ( debug > 1 )
    {
        System.out.println( "ControlPanel::getModel(): satellite: location=" + satelliteLocation + ", velocity=" + satelliteVelocity );
    }

    planet = new Planet( "planet", planetLocation, planetVelocity, getPlanetMass(), getPlanetDensity() );
    planet.setInternalCohesion( 1.0 );
    planet.setColour( Color.GREEN );
    planet.setRocheLimit( uiSettings.calculateRocheLimit() );

    satellite = new Satellite( "satellite", satelliteLocation, satelliteVelocity, getSatelliteMass(), getSatelliteDensity() );
    satellite.setInternalCohesion( 1.0 );
    satellite.setColour( Color.ORANGE );

    ((UIDerivedModel)result).addParticle( planet );
    ((UIDerivedModel)result).addParticle( satellite );

    rocheLimit = RocheUtil.calculateRocheLimit( planet.getRadius(), planet.getDensity(), satellite.getDensity() );

    if ( debug > 2 )
    {
        System.out.println( "ControlPanel::getModel(): about to return with following model" );
        System.out.println( "ControlPanel::getModel(): -------------------------------------------------------------------" );
        System.out.println( "ControlPanel::getModel(): " + result );
        System.out.println( "ControlPanel::getModel(): -------------------------------------------------------------------" );
    }

    return result;
}


public ModelConstraints getModelConstraints()
{
    ModelConstraints result;

    result = new UIDerivedModelConstraints( uiSettings, this );

    return result;
}


/**
 * Sets up the user interface panel controls to match those specified in the settings.
 *
 *
 * @param settings what values to setup the user interface with....
 */
public void initialiseControls( ModelSettings settings )
{
    double rocheLimit;
    double circularOrbitalSpeed;

    if ( debug > 2 )
    {
        System.out.println( "ControlPanel::initialiseControls(): invoked with settings;" );
        System.out.println( "ControlPanel::initialiseControls(): -----------------------------------------------------------" );
        System.out.println( settings );
        System.out.println( "ControlPanel::initialiseControls(): -----------------------------------------------------------" );
        
    }

    uiSettings = (ModelSettings) settings.clone();
    satelliteMassConverter = new QuadraticSliderConverter( uiSettings.getSatelliteType().getMinimumMass(), uiSettings.getSatelliteType().getMaximumMass(), satelliteMassSlider );
    planetMassConverter = new QuadraticSliderConverter( uiSettings.getPlanetType().getMinimumMass(), uiSettings.getPlanetType().getMaximumMass(), planetMassSlider );

    rocheLimit = uiSettings.calculateRocheLimit();
    circularOrbitalSpeed = uiSettings.calculateCircularOrbitalSpeed();    
    double planetRadius = RocheUtil.calculateRadiusFromMassAndDensity( getPlanetMass(), getPlanetDensity() );
    double minOrbitalRadius = ( planetRadius * 2 ) > rocheLimit * 0.5 ? rocheLimit * 0.5 : planetRadius * 2;


    orbitalRadiusConverter = new LinearSliderConverter( minOrbitalRadius, rocheLimit * 2, orbitalRadiusSlider );
    orbitalSpeedConverter = new LinearSliderConverter( 0.0, circularOrbitalSpeed * 4, orbitalSpeedSlider );

    // display the orbital characteristics as per the supplied model....
    // --------------------------------------------------------------------
    setOrbitalSpeed( uiSettings.getOrbitalSpeed() );
    setOrbitalRadius( uiSettings.getOrbitalRadius() );


    // update the sliders with the currently selected values...
    // ----------------------------------------------------------
    setSatelliteMass( uiSettings.getSatelliteMass() );
    setPlanetMass( uiSettings.getPlanetMass() );



    // update the radio buttons too..
    // --------------------------------
//    planetTypeRadioButtonGroup.clearSelection();

    for( PlanetType pt : PlanetType.values() )
    {
        JRadioButton button = buttonByPlanetTypeMap.get( pt );
        if ( button != null )
        {
            if ( pt == uiSettings.getPlanetType() )
            {
                button.getModel().setPressed( true );
                button.setSelected( true );

            } else {
                button.getModel().setPressed( false );
                button.setSelected( false );
            }
        }
    }

//    satelliteTypeRadioButtonGroup.clearSelection();

    for( SatelliteType st : SatelliteType.values() )
    {
        JRadioButton  button = buttonBySatelliteTypeMap.get( st );
        if ( button != null )
        {
            if ( st == uiSettings.getSatelliteType() )
            {
                button.getModel().setPressed( true );
                button.setSelected( true );
            } else {
                button.getModel().setPressed( false );
                button.setSelected( false );
            }
        }

    }

}

protected void setSatelliteMass( double value )
{
    uiSettings.setSatelliteMass( value );
    int pos = satelliteMassConverter.getSliderPosition( value );
    double earthFactor = value / EARTH_MASS;
    String textValue = oneDotTwelve.format( earthFactor ) + " M\u2295";

    if ( debug > 2 )
    {
        System.out.println( "ControlPanel::setSatelliteMass(): supplied with value=" + value + ", calculated pos=" + pos + ", text=[" + textValue + "]" );
    }
    satelliteMassSlider.setValue( pos );
    satelliteMassValue.setText( textValue );
}

protected void setPlanetMass( double value )
{
    uiSettings.setPlanetMass( value );
    int pos = planetMassConverter.getSliderPosition( value );
    double earthFactor = value / EARTH_MASS;
    String textValue = dotTwo.format( earthFactor ) + " M\u2295";

    if ( debug > 2 )
    {
        System.out.println( "ControlPanel::setPlanetMass(): supplied with value=" + value + ", calculated pos=" + pos + ", text=[" + textValue + "]" );
    }
    planetMassSlider.setValue( pos );
    planetMassValue.setText( textValue );
}


protected void setOrbitalSpeed( double value )
{
    double circularOrbitalSpeed = uiSettings.calculateCircularOrbitalSpeed();
    uiSettings.setOrbitalSpeed( value );
    int pos = orbitalSpeedConverter.getSliderPosition( value );
    String textValue = dotTwo.format( value / 1000.0 ) + "kms-1";
    orbitalSpeedSlider.setValue( pos );
    if ( value > circularOrbitalSpeed )
    {
        orbitalSpeedValue.setForeground( Color.GREEN );
    } else {
        orbitalSpeedValue.setForeground( Color.RED );
    }
    orbitalSpeedValue.setText( textValue );

    if ( debug > 4 )
    {
        System.out.println( "ControlPanel::setOrbitalSpeed(): invoked with value=" + value + ", pos=" + pos + ", textValue=\"" + textValue + "\"" );
    }


}

protected void setOrbitalRadius( double value )
{
    uiSettings.setOrbitalRadius( value );
    int pos = orbitalRadiusConverter.getSliderPosition( value );
    String textValue = dotTwo.format( value / 1000.0 ) + "km";
    orbitalRadiusSlider.setValue( pos );
    orbitalRadiusValue.setText( textValue );


    if ( debug > 4 )
    {
        System.out.println( "ControlPanel::setOrbitalRadius(): invoked with value=" + value + ", pos=" + pos + ", textValue=\"" + textValue + "\"" );
    }

}


// Reacts to changes in the position of the orbital radius slider...
// 
class OrbitalRadiusSliderListener implements javax.swing.event.ChangeListener
{
    OrbitalRadiusSliderListener()
    {

    }

    public void stateChanged(ChangeEvent event)
    {
        double radius = orbitalRadiusConverter.getValue( orbitalRadiusSlider.getValue() );
        setOrbitalRadius( radius );
        uiSettingsModified();

    /*
        //To change body of implemented methods use File | Settings | File Templates.
        int pos = 100 + ( orbitalRadiusSlider.getValue() - 50 );
        String value = Integer.toString( pos ) + "%RL";
        orbitalRadiusValue.setText( value );
        */
    }
}

class SatelliteMassSliderListener implements ChangeListener
{
    SatelliteMassSliderListener()
    {

    }

    public void stateChanged( ChangeEvent evt )
    {
        double mass = satelliteMassConverter.getValue( satelliteMassSlider.getValue() );


        if ( debug > 2 )
        {
            System.out.println( "SatelliteMassSliderListener::stateChanged(): invoked with value=" + satelliteMassSlider.getValue() + ", about to set satellite mass=" + mass );
        }

        setSatelliteMass( mass );
        uiSettingsModified();
/*        int pos = satelliteMassSlider.getValue();
        double mass = 0.01 + ( (double)pos / 101 );
        String value =  oneDotTwo.format( mass ) + " M\u2295";
        satelliteMassValue.setText( value );
 */
    }
}


class OrbitalSpeedMassSliderListener implements ChangeListener
{
    OrbitalSpeedMassSliderListener()
    {

    }

    public void stateChanged( ChangeEvent evt )
    {
        double speed = orbitalSpeedConverter.getValue( orbitalSpeedSlider.getValue() );
        if ( debug > 3 )
        {
            System.out.println( "ControlPanel::OrbitalSpeedMassSliderListener::stateChanged(): sliderPos=" + orbitalSpeedSlider.getValue() + ", speed=" + ( speed / 1000.0 ) + "km/s" );
        }
        setOrbitalSpeed( speed );
        uiSettingsModified();
/*        int pos = orbitalSpeedSlider.getValue();
        double speed = 0.0 + ( (double)pos / 2 );
        String value =  twoDotOne.format( speed ) + " kms-1";
        orbitalSpeedValue.setText( value );
        */
    }
}


class PlanetMassSliderListener implements ChangeListener
{
    PlanetMassSliderListener()
    {

    }

    public void stateChanged( ChangeEvent evt )
    {
        double mass = planetMassConverter.getValue( planetMassSlider.getValue() );

        if ( debug > 2 )
        {
            System.out.println( "ControlPanel::PlaneteMassSliderListener::stateChanged(): invoked with value=" + planetMassSlider.getValue() + ", about to set planet mass=" + mass );
        }

        setPlanetMass( mass );
        uiSettingsModified();

/*
        int pos = planetMassSlider.getValue();
        double mass = 1 + ( ( (double)pos / 2 ) * ( (double)pos / 2 ) );
        String value =  four.format( mass ) + " M\u2295";
        planetMassValue.setText( value );
 */
    }
}

/**
 * for processing radio button events...
 * 
 * @param actionEvent
 */
public void actionPerformed(ActionEvent actionEvent)
{
    if( actionEvent.getSource() instanceof JRadioButton )
    {
        JRadioButton button = (JRadioButton)actionEvent.getSource();

        if ( debug > 1 )
        {
            System.out.println( "ControlPanel::actionPerformed(): ---> BUTTON( \"" + button.getLabel() + "\"" );
        }

        // one of the planetary radio buttons was pressed .... set planet related settings to
        // the default values for this new planet type...
        // ---------------------------------------------------------------------------------------
        if ( planetButtonMap.containsKey( button ) )
        {
            uiSettings.setPlanetType( planetButtonMap.get( button ) );
            planetMassConverter = new PowerSliderConverter( uiSettings.getPlanetType().getMinimumMass(), uiSettings.getPlanetType().getMaximumMass(), planetMassSlider );
            setPlanetMass( uiSettings.getPlanetType().getDefaultMass() );
        }


        // one of the planetary radio buttons was pressed .... set planet related settings to
        // the default values for this new planet type...
        // ---------------------------------------------------------------------------------------
        if ( satelliteButtonMap.containsKey( button ) )
        {
            uiSettings.setSatelliteType( satelliteButtonMap.get( button ) );
            satelliteMassConverter = new PowerSliderConverter( uiSettings.getSatelliteType().getMinimumMass(), uiSettings.getSatelliteType().getMaximumMass(), satelliteMassSlider );
            setSatelliteMass( uiSettings.getSatelliteType().getDefaultMass() );
        }


        uiSettingsModified();
    }

    if ( actionEvent.getSource() == hitMe )
    {
        initialiseControls( PresetModelSetting.getRandomModelSettings() );
        uiSettingsModified();
    }
    //To change body of implemented methods use File | Settings | File Templates.
}

public void freezeControlPanel()
{
    planetMassSlider.setEnabled( false );
    satelliteMassSlider.setEnabled( false );
    orbitalRadiusSlider.setEnabled( false );
    orbitalSpeedSlider.setEnabled( false );
    for( JRadioButton button : planetButtonMap.keySet() )
    {
        button.setEnabled( false );
    }

    for( JRadioButton button : satelliteButtonMap.keySet() )
    {
        button.setEnabled( false );
    }

    hitMe.setEnabled( false );
}

public void enableControlPanel()
{
    planetMassSlider.setEnabled( true );
    satelliteMassSlider.setEnabled( true );
    orbitalRadiusSlider.setEnabled( true );
    orbitalSpeedSlider.setEnabled( true );
    for( JRadioButton button : planetButtonMap.keySet() )
    {
        button.setEnabled( true );
    }

    for( JRadioButton button : satelliteButtonMap.keySet() )
    {
        button.setEnabled( true );
    }

    hitMe.setEnabled( true );
}


/**
 * invoked whenever a setting in the UI is modified....
 */
public void uiSettingsModified()
{
    double circularOrbitalSpeed = uiSettings.calculateCircularOrbitalSpeed();
    if ( uiSettings.getOrbitalSpeed() >= circularOrbitalSpeed )
    {
        orbitalSpeedValue.setForeground( Color.GREEN );
    } else {
        orbitalSpeedValue.setForeground( Color.RED );
    }


    controller.resetCountdown();
    controller.refreshAnimation( getModel(), getModelConstraints() );

}

}
