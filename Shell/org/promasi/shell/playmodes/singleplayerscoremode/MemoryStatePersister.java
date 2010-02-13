package org.promasi.shell.playmodes.singleplayerscoremode;


import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.promasi.core.ISdObject;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;


/**
 * 
 * Persists all the values into {@link Map}s.
 * 
 * @author eddiefullmetal
 * 
 */
public class MemoryStatePersister
        implements IStatePersister
{

    /**
     * The {@link SdModel} to persist.
     */
    private SdModel _model;

    /**
     * Object used for locking.
     */
    private Object _lockObject;

    /**
     * Holds all the values. The objectKey - > Time , Value.
     */
    private Map<String, Map<Double, Double>> _values;

    /**
     * Initializes the object.
     * 
     */
    public MemoryStatePersister( )
    {
        _lockObject = new Object( );
        _values = new Hashtable<String, Map<Double, Double>>( );
    }

    @Override
    public void close ( )
    {

    }

    @Override
    public void initialize ( SdModel model )
    {
        _model = model;
        _values.clear( );
    }

    @Override
    public void persistModel ( double time )
    {
        synchronized ( _lockObject )
        {
            if ( _model != null )
            {
                // Copy all objects.
                List<ISdObject> objects = new Vector<ISdObject>( _model.getSdObjects( ) );
                for ( ISdObject sdObject : objects )
                {
                    Map<Double, Double> objectValues = _values.get( sdObject.getKey( ) );
                    if ( objectValues == null )
                    {
                        objectValues = new Hashtable<Double, Double>( );
                        _values.put( sdObject.getKey( ), objectValues );
                    }
                    // If the same entry exists do not replace.
                    if ( !objectValues.containsKey( time ) )
                    {
                        objectValues.put( time, sdObject.getValue( ) );
                    }
                }
            }
        }
    }

    @Override
    public Double getValue ( String sdObjectKey, double time )
    {
        synchronized ( _lockObject )
        {
            Map<Double, Double> objectValues = _values.get( sdObjectKey );
            if ( objectValues != null )
            {
                return objectValues.get( time );
            }
            else
            {
                return null;
            }
        }
    }

    @Override
    public List<Double> getTimeSteps ( String sdObjectKey )
    {
        synchronized ( _lockObject )
        {
            Map<Double, Double> objectTimeSteps = _values.get( sdObjectKey );
            if ( objectTimeSteps != null )
            {
                return new Vector<Double>( objectTimeSteps.keySet( ) );
            }
            else
            {
                return new Vector<Double>( );
            }
        }
    }
}
