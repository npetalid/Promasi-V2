package org.promasi.model;


/**
 * 
 * Represents the project manager(user).
 * 
 * @author eddiefullmetal
 * 
 */
public class ProjectManager
        extends Person
{

    /**
     * The {@link Company} that the project manager works for.
     */
    private Company _workingCompany;

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

    /**
     * @return The {@link #_workingCompany}.
     */
    public Company getWorkingCompany ( )
    {
        return _workingCompany;
    }

    /**
     * @param company
     *            The {@link #_workingCompany} to set.
     */
    public void setWorkingCompany ( Company company )
    {
        _workingCompany = company;
    }
}
