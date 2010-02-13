package org.promasi.core;


import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.util.MathUtils;
import org.apache.log4j.Logger;
import org.nfunk.jep.ParseException;
import org.promasi.communication.Communicator;
import org.promasi.utilities.ErrorBuilder;


/**
 * Represents an event of the core. Events are thrown when a specified condition
 * is true. The event notifies the {@link Communicator} when that condition is
 * true. In order for the {@link IEquation} of the event to be correctly
 * calculated the dependencies of the {@link ISdObject} must be set correctly.
 * The class follows the javabeans specification.
 * 
 * @author eddiefullmetal
 * 
 */
public class Event
{

    /**
     * The name of the event.
     */
    private String _name;

    /**
     * The equation that the event will check. If that equation returns true(>=1
     * rounded by 2 digits) the event will notify the {@link Communicator}.
     */
    private IEquation _equation;

    /**
     * The {@link ISdObject} that raised the event.
     */
    private ISdObject _context;

    /**
     * Flag indicates if the event was raised.
     */
    private boolean _wasRaised;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Event.class );

    /**
     * Initializes the object.
     */
    public Event( )
    {
        _wasRaised = false;
    }

    /**
     * Initializes the object.
     * 
     * @param name
     *            The {@link #_name}.
     * @param equation
     *            The {@link #_equation}.
     * @param context
     *            The {@link #_context}.
     * @throws NullArgumentException
     *             In case the equation is null.
     * @throws IllegalArgumentException
     *             In case the name is null or empty.
     */
    public Event( String name, IEquation equation, ISdObject context )
            throws IllegalArgumentException, NullArgumentException
    {
        // Check arguments
        if ( name == null || name.isEmpty( ) )
        {
            String message = ErrorBuilder.generateNullOrEmptyArgumentError( "Event", "name" );
            LOGGER.error( message );
            throw new IllegalArgumentException( message );
        }
        if ( equation == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "Event", "equation" ) );
            throw new NullArgumentException( "equation" );
        }
        if ( context == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "Event", "context" ) );
            throw new NullArgumentException( "context" );
        }

        _name = name;
        _equation = equation;
        _context = context;
    }

    /**
     * Notifies the {@link Communicator} if the {@link #_equation} returns >=1
     * rounded by 2 digits.
     */
    public void raise ( )
    {
        if ( !_wasRaised && _equation != null && _context != null && _name != null && !_name.isEmpty( ) )
        {
            try
            {
                Double value = _equation.calculateValue( );
                value = MathUtils.round( value, 2 );
                if ( value >= 1.0d )
                {
                    Communicator.getInstance( ).raiseEvent( _context.getKey( ), _name );
                    _wasRaised = true;
                }
            }
            catch ( ParseException e )
            {
                // Do nothing in case the equation fails the event will not be
                // fired.
                LOGGER.warn( "Event failed.", e );
            }
            catch ( MathException e )
            {
                LOGGER.warn( "Event failed.", e );
            }
            catch ( NullPointerException e )
            {
                LOGGER.warn( "Event failed.", e );
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

    /**
     * @param equation
     *            The {@link #_equation} to set.
     */
    public void setEquation ( IEquation equation )
    {
        _equation = equation;
    }

    /**
     * @return The {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }

    /**
     * @param name
     *            The {@link #_name} to set.
     */
    public void setName ( String name )
    {
        _name = name;
    }

    /**
     * @param context
     *            The {@link #_context} to set.
     */
    public void setContext ( ISdObject context )
    {
        _context = context;
    }

    /**
     * @return The {@link #_context}.
     */
    public ISdObject getContext ( )
    {
        return _context;
    }

    /**
     * @return The {@link #_wasRaised}
     */
    public boolean wasRaised ( )
    {
        return _wasRaised;
    }

    @Override
    public String toString ( )
    {
        return _name;
    }
}
