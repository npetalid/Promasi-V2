package org.promasi.model;

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
public class Person
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The name of the person.
     */
    private String _firstName;

    /**
     * The last name of the person.
     */
    private String _lastName;

    /**
     * 
     */
    private String _personId;
    
    public static final String NAME_PROPERTY = "firstName";
    public static final String LASTNAME_PROPERTY = "lastName";

    /**
     * Initializes the object.
     */
    public Person( )
    {
    	_firstName=new String(NAME_PROPERTY);
    	_lastName=new String(LASTNAME_PROPERTY);
    	_personId=new String();
    }

    /**
     * Initializes the object.
     * 
     * @param firstName
     *            The {@link #_firstName} to set.
     * @param lastName
     *            The {@link #_lastName} to set.
     */
    public Person( String firstName, String lastName, String personId )throws NullArgumentException
    {
        this( );
        if(firstName==null)
        {
        	throw new NullArgumentException("Wrong argument name==null");
        }
        
        if(lastName==null)
        {
        	throw new NullArgumentException("Wrong argument lastName==null");
        }
        
        if(personId==null)
        {
        	throw new NullArgumentException("Wrong argument personId==null");
        }
        
        _firstName = firstName;
        _lastName = lastName;
        _personId=personId;
    }

    /**
     * @return The {@link #_firstName}.
     */
    public String getFirstName ( )
    {
        return _firstName;
    }

    /**
     * @param name
     *            The {@link #_firstName} to set.
     */
    public void setFirstName ( String firstName )throws NullArgumentException
    {
    	if(firstName==null)
    	{
    		throw new NullArgumentException("Wrong argument firstName==null");
    	}
        _firstName = firstName;
    }

    /**
     * 
     * @param personId
     * @throws NullArgumentException
     */
    public void setPersonId(String personId)throws NullArgumentException
    {
    	if(personId==null)
    	{
    		throw new NullArgumentException("Wrong argument personId==null");
    	}
    	
    	_personId=personId;
    }
    
    /**
     * 
     * @return
     */
    public String getPersonId()
    {
    	return _personId;
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
    public void setLastName ( String lastName )throws NullArgumentException
    {
    	if(lastName==null)
    	{
    		throw new NullArgumentException("Wrong argument lastName==null");
    	}
    	
        _lastName = lastName;
    }

    @Override
    public String toString ( )
    {
        return _lastName + " " + _firstName;
    }
}
