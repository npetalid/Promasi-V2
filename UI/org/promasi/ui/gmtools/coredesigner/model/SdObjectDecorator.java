package org.promasi.ui.gmtools.coredesigner.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.promasi.core.Event;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.core.SdObjectInfo;
import org.promasi.core.SdObjectType;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.StockSdObject;


/**
 * Class that decorates an {@link AbstractSdObject} with a
 * {@link PropertyChangeSupport}.
 * 
 * @author eddiefullmetal
 * 
 */
public class SdObjectDecorator
{

    /**
     * The {@link AbstractSdObject} to decorate.
     */
    private AbstractSdObject _sdObject;

    /**
     * The {@link PropertyChangeSupport}.
     */
    private PropertyChangeSupport _propertyChangeSupport;

    /**
     * Flag determines if the {@link #_sdObject} is a {@link SdObjectType#Stock}
     * type.
     */
    private boolean _isStock;

    /**
     * The name of the key property.
     */
    public static final String KEY_PROPERTY = "key";

    /**
     * The name of the equation property.
     */
    public static final String EQUATION_PROPERTY = "equation";

    /**
     * The name of the initial value property.
     */
    public static final String INITIALVALUE_PROPERTY = "initialvalue";

    /**
     * The name of the dependencies property.
     */
    public static final String DEPENDENCIES_PROPERTY = "dependencies";

    /**
     * The name of the events property.
     */
    public static final String EVENTS_PROPERTY = "events";

    /**
     * The name of the info property.
     */
    public static final String INFO_PROPERTY = "info";

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     */
    public SdObjectDecorator( AbstractSdObject sdObject )
    {
        if ( sdObject.getType( ).equals( SdObjectType.Stock ) )
        {
            _isStock = true;
        }
        else
        {
            _isStock = false;
        }

        _sdObject = sdObject;
        _propertyChangeSupport = new PropertyChangeSupport( this );
    }

    /**
     * Sets the key of the {@link #_sdObject}.
     * 
     * @param key
     *            The key of the {@link #_sdObject}.
     */
    public void setKey ( String key )
    {
        String oldValue = _sdObject.getKey( );
        _sdObject.setKey( key );
        _propertyChangeSupport.firePropertyChange( KEY_PROPERTY, oldValue, key );
    }

    /**
     * Gets the key of the {@link #_sdObject}.
     * 
     * @return The key of the {@link #_sdObject}.
     */
    public String getKey ( )
    {
        return _sdObject.getKey( );
    }

    /**
     * Sets the equation of the {@link #_sdObject}.
     * 
     * @param equation
     *            The {@link IEquation} of the {@link #_sdObject}.
     */
    public void setEquation ( IEquation equation )
    {
        IEquation oldValue = _sdObject.getEquation( );
        _sdObject.setEquation( equation );
        _propertyChangeSupport.firePropertyChange( EQUATION_PROPERTY, oldValue, equation );
    }

    /**
     * Gets the equation of the {@link #_sdObject}.
     * 
     * @return The {@link IEquation} of the {@link #_sdObject}.
     */
    public IEquation getEquation ( )
    {
        return _sdObject.getEquation( );
    }

    /**
     * 
     * Adds a dependency to the {@link #_sdObject}.
     * 
     * @param sdObject
     *            The {@link ISdObject} to add as a dependency.
     */
    public void addDependency ( ISdObject sdObject )
    {
        Object[] oldValue = sdObject.getDependencies( ).toArray( );
        _sdObject.addDependency( sdObject );
        _propertyChangeSupport.firePropertyChange( DEPENDENCIES_PROPERTY, oldValue, _sdObject.getDependencies( ).toArray( ) );
    }

    /**
     * 
     * Removes a dependency from the {@link #_sdObject}.
     * 
     * @param sdObject
     *            The {@link ISdObject} to remove.
     */
    public void removeDependency ( ISdObject sdObject )
    {
        Object[] oldValue = sdObject.getDependencies( ).toArray( );
        _sdObject.getDependencies( ).remove( sdObject );
        _propertyChangeSupport.firePropertyChange( DEPENDENCIES_PROPERTY, oldValue, _sdObject.getDependencies( ).toArray( ) );
    }

    /**
     * 
     * Adds a dependency to the {@link #_sdObject}.
     * 
     * @param event
     *            The {@link Event} to add.
     */
    public void addEvent ( Event event )
    {
        Object[] oldValue = _sdObject.getEvents( ).toArray( );
        _sdObject.addEvent( event );
        _propertyChangeSupport.firePropertyChange( EVENTS_PROPERTY, oldValue, _sdObject.getEvents( ).toArray( ) );
    }

    /**
     * 
     * Removes a dependency from the {@link #_sdObject}.
     * 
     * @param event
     *            The {@link Event} to remove.
     */
    public void removeEvent ( Event event )
    {
        Object[] oldValue = _sdObject.getEvents( ).toArray( );
        _sdObject.getEvents( ).remove( event );
        _propertyChangeSupport.firePropertyChange( EVENTS_PROPERTY, oldValue, _sdObject.getEvents( ).toArray( ) );
    }

    /**
     * Gets the dependencies of the {@link #_sdObject}.
     * 
     * @return The dependencies of the {@link #_sdObject}.
     */
    public List<ISdObject> getDependencies ( )
    {
        return _sdObject.getDependencies( );
    }

    /**
     * Gets the events of the {@link #_sdObject}.
     * 
     * @return The events of the {@link #_sdObject}.
     */
    public List<Event> getEvents ( )
    {
        return _sdObject.getEvents( );
    }

    /**
     * Gets the {@link SdObjectType} of the {@link #_sdObject}.
     * 
     * @return The {@link SdObjectType} of the {@link #_sdObject}.
     */
    public SdObjectType getType ( )
    {
        return _sdObject.getType( );
    }

    /**
     * If the {@link #_isStock} is true it will set the initial value of the
     * stock object.
     * 
     * @param value
     *            The value to set.
     */
    public void setInitialValue ( Double value )
    {
        if ( _isStock )
        {
            StockSdObject sdObject = (StockSdObject) _sdObject;
            Double oldValue = sdObject.getValue( );
            sdObject.setValue( value );
            _propertyChangeSupport.firePropertyChange( INITIALVALUE_PROPERTY, oldValue, value );
        }
    }

    /**
     * Gets the value of the {@link #_sdObject}.
     * 
     * @return The value of the {@link #_sdObject}.
     */
    public Double getValue ( )
    {
        return _sdObject.getValue( );
    }

    /**
     * Sets the info of the {@link #_sdObject}.
     * 
     * @param info
     *            The {@link SdObjectInfo} to set.
     */
    public void setInfo ( SdObjectInfo info )
    {
        _sdObject.setInfo( info );
    }

    /**
     * Gets the info of the {@link #_sdObject}.
     * 
     * @return The {@link SdObjectInfo} of the {@link #_sdObject}.
     */
    public SdObjectInfo getInfo ( )
    {
        return _sdObject.getInfo( );
    }

    /**
     * Adds a {@link PropertyChangeListener} to the
     * {@link #_propertyChangeSupport}.
     * 
     * @param listener
     *            The {@link PropertyChangeListener} to add.
     */
    public void addPropertyChangeListener ( PropertyChangeListener listener )
    {
        _propertyChangeSupport.addPropertyChangeListener( listener );
    }

    /**
     * Removes a {@link PropertyChangeListener} from the
     * {@link #_propertyChangeSupport}.
     * 
     * @param listener
     *            The {@link PropertyChangeListener} to remove.
     */
    public void removePropertyChangeListener ( PropertyChangeListener listener )
    {
        _propertyChangeSupport.removePropertyChangeListener( listener );
    }

    /**
     * Gets the {@link #_sdObject}.
     * 
     * @return The {@link #_sdObject}.
     */
    public AbstractSdObject getActualSdObject ( )
    {
        return _sdObject;
    }

    @Override
    public String toString ( )
    {
        return _sdObject.getKey( );
    }
}
