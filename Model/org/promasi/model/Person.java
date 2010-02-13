package org.promasi.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.promasi.utilities.ICloneable;


/**
 * Represents a person.Holds basic information:
 * <ul>
 * <li>Name</li>
 * <li>Last name</li>
 * </ul>
 * .
 * 
 * @author eddiefullmetal
 * 
 */
public class Person
        implements ICloneable<Person>
{
    /**
     * The name of the person.
     */
    private String _name;

    /**
     * The last name of the person.
     */
    private String _lastName;

    /**
     * The {@link PropertyChangeSupport} for this class.
     */
    private PropertyChangeSupport _changeSupport;

    public static String NAME_PROPERTY = "name";
    public static String LASTNAME_PROPERTY = "lastName";

    /**
     * Initializes the object.
     */
    public Person( )
    {
        _changeSupport = new PropertyChangeSupport( this );
    }

    /**
     * Initializes the object.
     * 
     * @param name
     *            The {@link #_name} to set.
     * @param lastName
     *            The {@link #_lastName} to set.
     */
    public Person( String name, String lastName )
    {
        this( );
        _name = name;
        _lastName = lastName;
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
        String oldValue = _name;
        _name = name;
        _changeSupport.firePropertyChange( NAME_PROPERTY, oldValue, _name );
    }

    /**
     * @return The {@link #_lastName}.
     */
    public String getLastName ( )
    {
        return _lastName;
    }

    /**
     * @param lastName
     *            The {@link #_lastName} to set.
     */
    public void setLastName ( String lastName )
    {
        String oldValue = _lastName;
        _lastName = lastName;
        _changeSupport.firePropertyChange( LASTNAME_PROPERTY, oldValue, _lastName );
    }

    @Override
    public String toString ( )
    {
        return _lastName + " " + _name;
    }

    /**
     * @param listener
     *            The {@link PropertyChangeListener} to add.
     */
    public void addPropertyChangeListener ( PropertyChangeListener listener )
    {
        if ( listener != null )
        {
            _changeSupport.addPropertyChangeListener( listener );
        }
    }

    /**
     * @param listener
     *            The {@link PropertyChangeListener} to remove.
     */
    public void removePropertyChangeListener ( PropertyChangeListener listener )
    {
        if ( listener != null )
        {
            _changeSupport.removePropertyChangeListener( listener );
        }
    }

    /**
     * @return the {@link #_changeSupport}.
     */
    protected PropertyChangeSupport getChangeSupport ( )
    {
        return _changeSupport;
    }

    @Override
    public Person copy ( )
    {
        try
        {
            return (Person) clone( );
        }
        catch ( CloneNotSupportedException e )
        {
            return null;
        }
    }
}
