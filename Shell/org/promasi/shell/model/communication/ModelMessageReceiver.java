package org.promasi.shell.model.communication;


import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.promasi.communication.IMessageReceiver;
import org.promasi.model.Company;
import org.promasi.shell.model.actions.IModelAction;


/**
 * 
 * Singleton class that receives and handles notifications from the core layer.
 * 
 * @author eddiefullmetal
 * 
 */
public final class ModelMessageReceiver
        implements IMessageReceiver
{

    /**
     * The singleton instance.
     */
    private static ModelMessageReceiver INSTANCE;

    /**
     * A {@link Map} that contains for a sdObjectKey what data it will use in
     * order to get the value.
     */
    private Map<String, Object> _valueRequestedData;

    /**
     * A {@link Map} that contains for a sdObjectKey what data it will use in
     * order to set the value.
     */
    private Map<String, Object> _valueSentData;

    /**
     * The actions that will be executed in case an event fires. The key is the
     * event name and the value is a map with the sdObject key as a key and the
     * action to be executed as a value.
     */
    private Map<String, Map<String, IModelAction>> _eventActions;

    /**
     * The {@link Company} to use as a context in order to resolve the values.
     */
    private Company _context;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ModelMessageReceiver.class );

    /**
     * Initializes the object.
     */
    private ModelMessageReceiver( )
    {
        _valueRequestedData = new Hashtable<String, Object>( );
        _valueSentData = new Hashtable<String, Object>( );
        _eventActions = new Hashtable<String, Map<String, IModelAction>>( );
    }

    /**
     * Clears all the data of the {@link ModelMessageReceiver}.
     */
    public void clearAll ( )
    {
        _valueRequestedData.clear( );
        _valueSentData.clear( );
        _eventActions.clear( );
    }

    @Override
    public void eventRaised ( String sdObjectKey, String eventName )
    {
        Map<String, IModelAction> eventActions = _eventActions.get( eventName );
        if ( eventActions != null )
        {
            IModelAction action = eventActions.get( sdObjectKey );
            if ( action != null )
            {
                action.runAction( );
            }
        }
    }

    @Override
    public Double valueRequested ( String sdObjectKey )
    {
        try
        {
            if ( LOGGER.isDebugEnabled( ) )
            {
                LOGGER.debug( "value requested from " + sdObjectKey );
            }

            if ( _valueRequestedData.containsKey( sdObjectKey ) )
            {
                return ResolverFactory.getValue( _valueRequestedData.get( sdObjectKey ), _context );
            }
        }
        catch ( Exception e )
        {
            LOGGER.warn( "Exception on valueRequested.", e );
            // Do nothing an exception will return 0.
        }
        return 0.0d;
    }

    @Override
    public void valueSent ( String sdObjectKey, Double value )
    {
        try
        {
            if ( LOGGER.isDebugEnabled( ) )
            {
                LOGGER.debug( "setting value " + value + " to " + sdObjectKey );
            }

            if ( _valueSentData.containsKey( sdObjectKey ) )
            {
                ResolverFactory.setValue( _valueSentData.get( sdObjectKey ), _context, value );
            }
        }
        catch ( Exception e )
        {
            LOGGER.warn( "Exception on valueSent.", e );
        }
    }

    /**
     * @param sdObjectKey
     *            The key to add to the {@link #_valueRequestedData}.
     * @param data
     *            The value to add to the {@link #_valueRequestedData}.
     */
    public void addValueRequestedData ( String sdObjectKey, Object data )
    {
        if ( !_valueRequestedData.containsKey( sdObjectKey ) )
        {
            _valueRequestedData.put( sdObjectKey, data );
        }
    }

    /**
     * @param sdObjectKey
     *            The key to add to the {@link #_valueSentData}.
     * @param data
     *            The value to add to the {@link #_valueSentData}.
     */
    public void addValueSentData ( String sdObjectKey, Object data )
    {
        if ( !_valueSentData.containsKey( sdObjectKey ) )
        {
            _valueSentData.put( sdObjectKey, data );
        }
    }

    /**
     * 
     * Adds an entry to the {@link #_eventActions}. If an entry for the
     * specified event and object exists the entry is ignored.
     * 
     * @param eventName
     * @param sdObjectName
     * @param action
     */
    public void addEventAction ( String eventName, String sdObjectName, IModelAction action )
    {
        Map<String, IModelAction> eventActions = _eventActions.get( eventName );
        if ( eventActions == null )
        {
            eventActions = new Hashtable<String, IModelAction>( );
            _eventActions.put( eventName, eventActions );
        }
        if ( !eventActions.containsKey( sdObjectName ) )
        {
            eventActions.put( sdObjectName, action );
        }
    }

    /**
     * @return the {@link #_context}.
     */
    public Company getContext ( )
    {
        return _context;
    }

    /**
     * @param context
     *            the {@link #_context} to set.
     */
    public void setContext ( Company context )
    {
        _context = context;
    }

    /**
     * @return the {@link #INSTANCE}.
     */
    public static ModelMessageReceiver getInstance ( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new ModelMessageReceiver( );
        }
        return INSTANCE;
    }

}
