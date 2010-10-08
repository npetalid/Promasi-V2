package org.promasi.ui.promasiui.promasidesktop;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;

import com.jidesoft.dialog.JideOptionPane;


/**
 * 
 * A {@link JButton} that exits the app when clicked.
 * 
 * @author eddiefullmetal
 * 
 */
public class ExitButton
        extends JButton
        implements ActionListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Initializes the object.
     * 
     */
    public ExitButton( )
    {
        addActionListener( this );
        setIcon( ResourceManager.getIcon( "exit" ) );
        setFocusable( false );
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        int result = JideOptionPane.showConfirmDialog( this, ResourceManager.getString( ExitButton.class, "exit", "text" ), ResourceManager
                .getString( ExitButton.class, "exit", "title" ), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE );
        if ( result == JideOptionPane.OK_OPTION )
        {
            System.exit( 0 );
        }
    }

}
