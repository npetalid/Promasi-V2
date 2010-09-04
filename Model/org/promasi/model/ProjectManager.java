package org.promasi.model;


/**
 * 
 * Represents the project manager(user).
 * 
 * @author eddiefullmetal
 * 
 */
public class ProjectManager extends Person
{


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
    public ProjectManager( String name, String lastName )
    {
        super( name, lastName );
    }
}
