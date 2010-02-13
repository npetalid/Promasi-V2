package org.promasi.shell;


import java.util.List;

import javax.naming.ConfigurationException;

import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Employee;
import org.promasi.model.Project;


/**
 * 
 * Interface that defines a play mode.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IPlayMode
{

    /**
     * @return The name of the play mode.
     */
    String getName ( );

    /**
     * @return The description of the play mode. The description can be in HTML
     *         format.
     * 
     */
    String getDescription ( );

    /**
     * Starts the play mode.
     * 
     * @throws ConfigurationException
     *             In case the play mode is not configured properly.
     */
    void start ( )
            throws ConfigurationException;

    /**
     * @return All the available employees in the system(Hired and Free).
     */
    List<Employee> getAllEmployees ( );

    /**
     * This is used from the {@link Shell} in order to know which
     * {@link SdModel} will initialize.
     * 
     * @return The current {@link SdModel}.
     */
    SdModel getCurrentModel ( );

    /**
     * @param project
     * @return The {@link SdModel} that is associated with the specified
     *         {@link Project}.
     */
    SdModel getModelForProject ( Project project );

    /**
     * @param project
     * @return The {@link IStatePersister} for the specified project.
     */
    IStatePersister getPersisterForProject ( Project project );
}
