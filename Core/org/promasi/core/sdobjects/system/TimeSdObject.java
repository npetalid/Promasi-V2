package org.promasi.core.sdobjects.system;


import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.promasi.communication.ICommunicator;
import org.promasi.core.ISdListener;
import org.promasi.core.ISdObject;
import org.promasi.core.SdObjectInfo;
import org.promasi.core.SdObjectType;
import org.promasi.core.SdSystem;


/**
 * The TimeSdObject is used to store the systems current step. This
 * {@link ISdObject} is added to the model by the {@link SdSystem}.
 *
 * @author eddiefullmetal
 *
 */
public class TimeSdObject
        implements ISdObject, ISdListener
{

    /**
     * The key of the System object.
     */
    public static final String KEY = "time";

    /**
     * The current step of the system.
     */
    private double _currentStep;

    /**
     * A map containing properties of the object used for external apps.
     */
    private Map<String, Object> _properties;

    /**
     * Initializes the object.
     */
    public TimeSdObject( )
    {
        _currentStep = 0.0;
        _properties = new Hashtable<String, Object>( );
    }

    /**
     * Does nothing. System objects do not support dependencies.
     *
     * @param sdObject
     *            ...
     */
    @Override
    public void addDependency ( ISdObject sdObject )
    {
    }

    @Override
    public void calculateValue ( )
    {
    }

    /**
     * Returns an empty list. System objects do not support dependencies.
     *
     * @return An empty list.
     */
    @Override
    public List<ISdObject> getDependencies ( )
    {
        return new Vector<ISdObject>( );
    }

    @Override
    public String getKey ( )
    {
        return KEY;
    }

    @Override
    public SdObjectType getType ( )
    {
        return SdObjectType.System;
    }

    @Override
    public Double getValue ( )
    {
        return _currentStep;
    }

    @Override
    public void stepFinished ( )
    {
        _currentStep++;
    }

    @Override
    public SdObjectInfo getInfo ( )
    {
        return null;
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

    @Override
    public String toString ( )
    {
        return KEY;
    }

	@Override
	public void registerCommunicator(ICommunicator communicator) {
		// TODO Auto-generated method stub

	}
}
