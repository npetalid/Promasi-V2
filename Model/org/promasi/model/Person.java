package org.promasi.model;

import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;



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
public class Person implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The name of the person.
     */
    private String _name;

    /**
     * The last name of the person.
     */
    private String _lastName;

    public static final String NAME_PROPERTY = "name";
    public static final String LASTNAME_PROPERTY = "lastName";

    /**
     * Initializes the object.
     */
    public Person( )
    {
    	_name=new String(NAME_PROPERTY);
    	_lastName=new String(LASTNAME_PROPERTY);
    }

    /**
     * Initializes the object.
     * 
     * @param name
     *            The {@link #_name} to set.
     * @param lastName
     *            The {@link #_lastName} to set.
     */
    public Person( String name, String lastName )throws NullArgumentException
    {
        this( );
        if(name==null)
        {
        	throw new NullArgumentException("Wrong argument name==null");
        }
        
        if(lastName==null)
        {
        	throw new NullArgumentException("Wrong argument lastName==null");
        }
        
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
        _name = name;
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
        _lastName = lastName;
    }

    @Override
    public String toString ( )
    {
        return _lastName + " " + _name;
    }
}
