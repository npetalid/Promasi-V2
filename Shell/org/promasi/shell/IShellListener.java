package org.promasi.shell;


import org.promasi.model.Employee;
import org.promasi.model.Project;


/**
 * 
 * Listens to {@link Shell} events.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IShellListener
{

    /**
     * Indicates that the project start date time has come.
     * 
     * @param project
     */
    void projectStarted ( Project project );

    /**
     * Indicates that the project end date time has come.
     * 
     * @param project
     */
    void projectFinished ( Project project );

    /**
     * Indicates that a project has been assigned to the company.
     * 
     * @param project
     */
    void projectAssigned ( Project project );

    /**
     * Indicates that an employee is hired.
     * 
     * @param employee
     */
    void employeeHired ( Employee employee );
}
