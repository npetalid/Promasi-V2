package org.promasi.shell.ui.playmode;


import org.promasi.model.Project;


/**
 * 
 * A UI component used to display information when a project is finished.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IProjectFinishedUi
{

    /**
     * Shows the UI.
     * 
     * @param finishedProject
     *            The project that has finished.
     */
    void showUi ( Project finishedProject );

}
