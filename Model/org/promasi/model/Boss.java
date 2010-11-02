package org.promasi.model;

import org.apache.commons.lang.NullArgumentException;


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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The {@link Company} that the boss owns.
     */
    private Company _workingCompany;

    public static final String CONST_BOSS_PERSON_ID="BOSS";
    
    /**
     * Initializes the object.
     */
    public Boss( )
    {
        super("","",CONST_BOSS_PERSON_ID );
    }

    /**
     * Initializes the object. Calls the {@link Person#Person(String, String)}.
     */
    public Boss( String name, String lastName )throws NullArgumentException
    {
        super( name, lastName, CONST_BOSS_PERSON_ID);
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
