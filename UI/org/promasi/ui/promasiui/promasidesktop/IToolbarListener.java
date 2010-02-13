package org.promasi.ui.promasiui.promasidesktop;


import org.promasi.ui.promasiui.promasidesktop.programs.AbstractProgram;


/**
 * 
 * Listener that gets notified when a quick launch button is clicked.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IToolbarListener
{

    /**
     * Called when a quick launch button is clicked.
     * 
     * @param program
     *            The {@link AbstractProgram} of the button that was clicked.
     */
    void quickLauchButtonClicked ( AbstractProgram program );

}
