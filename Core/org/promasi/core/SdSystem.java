package org.promasi.core;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.communication.ICommunicator;
import org.promasi.core.computationalsequences.DefaultComputationalSequence;
import org.promasi.core.sdobjects.system.TimeSdObject;
import org.promasi.utilities.ErrorBuilder;


/**
 *
 * The SdSystem contains one {@link SdModel}. An implementation of an
 * {@link IComputationalSequence} and provides methods for handling the system.
 *
 * @author eddiefullmetal
 *
 */
public class SdSystem
{

    /**
     * The {@link SdModel} of the system.
     */
    private SdModel _model;

    /**
     * The {@link IComputationalSequence} of the system.
     */
    private IComputationalSequence _computationalSequence;

    /**
     * All the registered {@link ISdListener}s of the system.
     */
    private List<ISdListener> _listeners;

    /**
     * The {@link IStatePersister} of the system.
     */
    private IStatePersister _persister;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( SdSystem.class );

    /**
     * Initializes the object.
     */
    public SdSystem( )
    {
        _model = new SdModel( );
        _listeners = new Vector<ISdListener>( );
    }

    /**
     * Initializes the {@link SdSystem}.
     *
     * @param sdObjects
     *            The {@link ISdObject}s to initialize the {@link SdModel} with.
     *
     * @param computationalSequence
     *            The {@link IComputationalSequence} to use.
     */
    public void initialize ( List<ISdObject> sdObjects, IComputationalSequence computationalSequence )
    {
        LOGGER.info( "Initializinig SdSystem" );
        normalizeSdObjects( sdObjects );
        _model.setSdObjects( sdObjects );
        _model.initialize( );
        _computationalSequence = computationalSequence;
        _computationalSequence.initialize( _model );
    }

    /**
     * Initializes the {@link SdSystem} with a
     * {@link DefaultComputationalSequence}.
     *
     * @param sdObjects
     *            The {@link ISdObject}s to initialize the {@link SdModel} with.
     */
    public void initialize ( List<ISdObject> sdObjects )
    {
        initialize( sdObjects, new DefaultComputationalSequence( ) );
    }

    /**
     * Calculates the values of the {@link ISdObject}s with the
     * {@link #_computationalSequence}.
     */
    public void executeStep ( )
    {
        LOGGER.info( "Executing step..." );
        _computationalSequence.computeValues( );
        // Persist the model for the current step.
        if ( _persister != null )
        {
            _persister.persistModel( getCurrentStep( ) );
        }
        else
        {
            LOGGER.warn( "No registered state persisters" );
        }
        // Call the listeners.
        for ( ISdListener listener : _listeners )
        {
            listener.stepFinished( );
        }
    }

    /**
     * Adds an {@link ISdListener}.
     *
     * @param listener
     *            The {@link ISdListener} to add.
     *
     * @throws NullArgumentException
     *             In case the listener is null.
     */
    public void addListener ( ISdListener listener )
            throws NullArgumentException
    {
        // Check arguments
        if ( listener == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "addListener", "listener" ) );
            throw new NullArgumentException( "listener" );
        }

        if ( !_listeners.contains( listener ) )
        {
            _listeners.add( listener );
        }
    }

    /**
     * Removes the {@link ISdListener} from the system.
     *
     * @param listener
     *            The {@link ISdListener} to remove.
     * @throws NullArgumentException
     *             In case the listener is null.
     */
    public void removeListener ( ISdListener listener )
            throws NullArgumentException
    {
        // Check arguments
        if ( listener == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "removeListener", "listener" ) );
            throw new NullArgumentException( "listener" );
        }

        if ( _listeners.contains( listener ) )
        {
            _listeners.remove( listener );
        }
    }

    /**
     * Register the {@link IStatePersister} and initialize it with the
     * {@link #_model}.
     *
     * @param persister
     *            The {@link IStatePersister} to use.
     */
    public void registerStatePersister ( IStatePersister persister )
    {
        _persister = persister;
        _persister.initialize( _model );
    }

    /**
     * Takes a list and checks if it contains any invalid sdObjects( sdObjects
     * that their key matches the key of a system sdObject) and removes them.
     * Adds all the needed {@link SdObjectType#System} objects :
     * <ol>
     * <li> {@link TimeSdObject}</li>
     * </ol>
     *
     * @param sdObjects
     *            The {@link ISdObject}s to normalize.
     */
    private void normalizeSdObjects ( List<ISdObject> sdObjects )
    {
        LOGGER.info( "Normalizing sdObjects" );
        // Copy the sdObjects list cause it will be modified inside an iterator
        // and this will throw a ConcurrentModificationException.
        List<ISdObject> copiedSdObjects = new Vector<ISdObject>( sdObjects );
        Collections.copy( copiedSdObjects, sdObjects );

        // Add the TimeSdObject and register it as a listener.
        LOGGER.info( "Registering system sdObjects" );
        TimeSdObject time = new TimeSdObject( );
        sdObjects.add( time );
        addListener( time );

        // Check for invalid keys. And add the system variables as dependencies.
        for ( ISdObject sdObject : copiedSdObjects )
        {
            sdObject.addDependency( time );
            if ( sdObject.getKey( ).equals( TimeSdObject.KEY ) )
            {
                LOGGER.warn( "Found invalid sdObject :" + sdObject.getKey( ) );
                sdObjects.remove( sdObject );
            }
        }
    }

    /**
     *
     * Gets the current step of the system using the {@link TimeSdObject} from
     * the current {@link SdModel}.
     *
     * @return The current step.
     */
    public double getCurrentStep ( )
    {
        ISdObject time = _model.getSdObject( TimeSdObject.KEY );
        return time.getValue( );
    }

    /**
     *
     * @return The outputs values for current SdSystem
     */
    public HashMap<String,Double> getOutputs()
    {
    	if(_model==null)
    	{
    		return new HashMap<String,Double>();
    	}
    	return _model.getOutputs();
    }

    public void registerCommunicator(ICommunicator communicator)
    {
    	synchronized(this)
    	{
    		if(_model!=null)
    		{
    			_model.registerCommunicator(communicator);
    		}
    	}
    }
}
