package org.promasi.model;

import org.apache.commons.lang.NullArgumentException;


/**
 * 
 * Represents the project manager(user).
 * 
 * @author eddiefullmetal
 * 
 */
public class ProjectManager extends Person
{

	public static final String CONST_PROJECT_MANAGER_PERSON_ID="PROJECT_MANAGER";
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Initializes the object.
     */
    public ProjectManager( )
    {
        super( );
    }

    /**
     * Initializes the object. Calls the {@link Person#Person(String, String)}.
     */
    public ProjectManager( String firstName, String lastName )throws NullArgumentException
    {
        super( firstName, lastName, CONST_PROJECT_MANAGER_PERSON_ID);
    }
}
