package org.promasi.model;


/**
 * 
 * Listener for the {@link Notifier}.
 * 
 * @author eddiefullmetal
 * 
 */
public interface INotifierListener
{
    /**
     * Called when a new {@link Project} is assigned to the company.
     * 
     * @param project
     *            The newly assigned {@link Project}.
     */
    void projectAssigned ( Project project );

    /**
     * Called when a new {@link Employee} is hired.
     * 
     * @param employee
     */
    void employeeHired ( Employee employee );
}
