package org.promasi.core.sdobjects;


import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.util.MathUtils;
import org.apache.log4j.Logger;
import org.nfunk.jep.ParseException;
import org.promasi.communication.ICommunicator;
import org.promasi.core.Event;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.core.SdObjectInfo;
import org.promasi.core.SdObjectType;
import org.promasi.core.utilities.Defaults;
import org.promasi.utilities.ErrorBuilder;


/**
 * Abstract implementation of {@link ISdObject} using an {@link IEquation}
 * object to calculate its value. The initial value of all
 * {@link AbstractSdObject}s is 1.The class follows the javabeans specification.
 *
 * @author eddiefullmetal
 *
 */
public abstract class AbstractSdObject
        implements ISdObject, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The {@link SdObjectType} of the {@link ISdObject} object.
     */
    private SdObjectType _type;

    /**
     * The value of the {@link ISdObject}.
     */
    private Double _value;

    /**
     * The key of the {@link ISdObject}.
     */
    private String _key;

	/**
	 *	System communicator.
	 */
	protected ICommunicator _communicator;

    /**
     * The dependencies of the {@link ISdObject}.
     */
    private List<ISdObject> _dependencies;

    /**
     * All the registered {@link Event}s of this {@link ISdObject}.
     */
    private List<Event> _events;

    /**
     * The {@link IEquation} of the {@link AbstractSdObject}.
     */
    private IEquation _equation;

    /**
     * The {@link SdObjectInfo} for this object.
     */
    private SdObjectInfo _info;

    /**
     * A map containing properties of the object used for external apps.
     */
    private Map<String, Object> _properties;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( AbstractSdObject.class );

    /**
     * Initializes the object.
     *
     *
     * @param sdObjectType
     *            The {@link #_type}
     * @param key
     *            The {@link #_key}
     *
     * @throws IllegalArgumentException
     *             In case the key is null or empty.
     */
    public AbstractSdObject( String key, SdObjectType sdObjectType )
            throws IllegalArgumentException
    {
        this( );
        // Check arguments
        if ( key == null || key.isEmpty( ) )
        {
            String message = ErrorBuilder.generateNullOrEmptyArgumentError( "AbstractSdObject", "key" );
            LOGGER.error( message );
            throw new IllegalArgumentException( message );
        }
        _key = key;
        _type = sdObjectType;
        _value = 0.0d;
    }

    /**
     * Initializes the object.
     */
    public AbstractSdObject( )
    {
        _dependencies = new Vector<ISdObject>( );
        _events = new Vector<Event>( );
        _properties = new Hashtable<String, Object>( );
    }

    /**
     * calculates the value of the object. This method is called in every step
     * of the system. <b>If calculateValue of the {@link AbstractSdObject} fails
     * the value of the object will not get updated!!!. This method also raises
     * the events, if an event was raised it is being removed.</b>.
     */
    @Override
    public void calculateValue ( )
    {
        if ( _equation != null )
        {
            if ( LOGGER.isDebugEnabled( ) )
            {
                LOGGER.debug( "Calculating the value of " + _key );
            }

            // In case an error happens in the calculate value the value will
            // not be updated.
            try
            {
                _value = MathUtils.round( _equation.calculateValue( ), Defaults.NUMBER_OF_DECIMALS );
                if ( _value.isInfinite( ) || _value.isNaN( ) )
                {
                    _value = 0.0;
                }
                // Call all the events.
                Vector<Event> clonedEvents = new Vector<Event>( _events );
                for ( Event event : clonedEvents )
                {
                    event.raise( );
                    if ( event.wasRaised( ) )
                    {
                        _events.remove( event );
                    }
                }
            }
            catch ( ParseException e )
            {
                LOGGER.error( "Value could not be calculated", e );
            }
            catch ( MathException e )
            {
                LOGGER.error( "Value could not be calculated", e );
            }
            catch ( NullPointerException e )
            {
                LOGGER.error( "Value could not be calculated", e );
            }

            if ( LOGGER.isDebugEnabled( ) )
            {
                LOGGER.debug( "Calculated value: " + _value );
            }
        }
        else
        {
            // This isn't considered an error for the core cause a zero value
            // will be returned. Just throw a warning cause it's not a common
            // behavior for an AbstractSdObject not to have an IEquation
            // defined.
            LOGGER.warn( "No equation is defined for sdObject:" + _key );
        }
    }

    @Override
    public void addDependency ( ISdObject sdObject )
    {
        // Check arguments
        if ( sdObject == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "addDependency", "sdObject" ) );
            throw new NullArgumentException( "sdObject" );
        }

        if ( LOGGER.isDebugEnabled( ) )
        {
            LOGGER.debug( "Adding dependency " + sdObject.getKey( ) + " to " + _key );
        }

        if ( !_dependencies.contains( sdObject ) )
        {
            _dependencies.add( sdObject );
        }
    }

    /**
     * @param dependencies
     *            The {@link #_dependencies} to set.
     */
    public void setDependencies ( List<ISdObject> dependencies )
    {
        _dependencies = dependencies;
    }

    @Override
    public List<ISdObject> getDependencies ( )
    {
        return _dependencies;
    }

    /**
     * Adds an {@link Event} to the {@link AbstractSdObject}.
     *
     * @param event
     *            The {@link Event} to add.
     */
    public void addEvent ( Event event )
    {
        if ( event == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "addEvent", "event" ) );
            throw new NullArgumentException( "event" );
        }

        if ( !_events.contains( event ) )
        {
            _events.add( event );
        }
    }

    /**
     * @return The {@link #_events}.
     */
    public List<Event> getEvents ( )
    {
        return _events;
    }

    /**
     * @param events
     *            The {@link #_events} to set.
     */
    public void setEvents ( List<Event> events )
    {
        _events = events;
    }

    @Override
    public String getKey ( )
    {
        return _key;
    }

    /**
     * @param key
     *            The {@link #_key} to set.
     */
    public void setKey ( String key )
    {
        _key = key;
    }

    @Override
    public SdObjectType getType ( )
    {
        return _type;
    }

    /**
     * @param type
     *            The {@link #_type} to set.
     */
    public void setType ( SdObjectType type )
    {
        _type = type;
    }

    @Override
    public Double getValue ( )
    {
        return _value;
    }

    /**
     * @param value
     *            The {@link #_value} to set.
     */
    public void setValue ( Double value )
    {
        _value = value;
    }

    /**
     * @param equation
     *            The {@link #_equation} to set.
     */
    public void setEquation ( IEquation equation )
    {
    	synchronized(this)
    	{
    		_equation = equation;
    		if(_equation!=null)
    		{
    			_equation.registerCommunicator(_communicator);
    		}
    	}

    }

    /**
     * @return The {@link #_equation}.
     */
    public IEquation getEquation ( )
    {
        return _equation;
    }

    @Override
    public String toString ( )
    {
        return _key;
    }

    /**
     * @param info
     *            the {@link #_info} to set.
     */
    public void setInfo ( SdObjectInfo info )
    {
        _info = info;
    }

    @Override
    public SdObjectInfo getInfo ( )
    {
        return _info;
    }

    @Override
    public void addProperty ( String key, Object property )
    {
        if ( _properties.containsKey( key ) )
        {
            _properties.remove( key );
        }
        _properties.put( key, property );
    }

    @Override
    public Object getProperty ( String key )
    {
        return _properties.get( key );
    }

    /**
     * @return the {@link #_properties}.
     */
    public Map<String, Object> getProperties ( )
    {
        return _properties;
    }

    /**
     * @param properties
     *            the {@link #_properties} to set.
     */
    public void setProperties ( Map<String, Object> properties )
    {
        _properties = properties;
    }

    public void registerCommunicator(ICommunicator communicator)
    {
    	synchronized(this)
    	{
    		_communicator=communicator;
    		if(_equation!=null)
    		{
    			_equation.registerCommunicator(communicator);
    		}
    	}
    }
}
