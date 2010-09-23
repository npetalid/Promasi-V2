package org.promasi.model;

import java.io.Serializable;


/**
 * 
 * Represents the administrator of the company.
 * 
 * @author eddiefullmetal
 * 
 */
public class Administrator extends Person implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The {@link Company} that the administrator works for.
     */
    private Company _workingCompany;

    /**
     * Initializes the object.
     */
    public Administrator( )
    {
        super( );
    }

    /**
     * Initializes the object. Calls the {@link Person#Person(String, String)}.
     * 
     * @param name
     * @param lastName
     */
    public Administrator( String name, String lastName )
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
