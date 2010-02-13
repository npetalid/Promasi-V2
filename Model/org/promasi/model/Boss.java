package org.promasi.model;


/**
 * Represents the Boss of the company. Responsible for assigning a project.
 * 
 * @author eddiefullmetal
 * 
 */
public class Boss
        extends Person
{

    /**
     * The {@link Company} that the boss owns.
     */
    private Company _workingCompany;

    /**
     * Initializes the object.
     */
    public Boss( )
    {
        super( );
    }

    /**
     * Initializes the object. Calls the {@link Person#Person(String, String)}.
     */
    public Boss( String name, String lastName )
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
