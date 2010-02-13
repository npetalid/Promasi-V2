package org.promasi.ui.promasiui.promasidesktop.programs;


import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.promasi.ui.promasiui.promasidesktop.DesktopToolbar;


/**
 * 
 * Abstract class represents a program.
 * 
 * @author eddiefullmetal
 * 
 */
public abstract class AbstractProgram
        extends JPanel
        implements InternalFrameListener
{

    /**
     * The name of the program.
     */
    private String _name;

    /**
     * An extended name for the program.
     */
    private String _extendedName;

    /**
     * The internal frame of this program.
     */
    private JInternalFrame _frame;

    /**
     * Initializes the object.
     * 
     * @param name
     *            The name of the program.
     * @param extendedName
     *            The extended name of the program. It can be the name followed
     *            by a description.
     */
    public AbstractProgram( String name, String extendedName )
    {
        _name = name;
        _extendedName = extendedName;
    }

    /**
     * @return the {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }

    /**
     * @return the {@link #_extendedName}.
     */
    public String getExtendedName ( )
    {
        return _extendedName;
    }

    /**
     * @return The {@link Icon} for the program.
     */
    public abstract Icon getIcon ( );

    /**
     * @return The button that launches this application. This will be used by
     *         the {@link DesktopToolbar}. No need to add an action listener.
     */
    public JButton getButton ( )
    {
        JButton button = new JButton( getIcon( ) );
        button.setToolTipText( getExtendedName( ) );
        return button;
    }

    /**
     * @return A new {@link JInternalFrame}.
     */
    public JInternalFrame getInternalFrame ( )
    {
        if ( _frame == null )
        {
            _frame = new JInternalFrame( getExtendedName( ) );
            _frame.setFrameIcon( getIcon( ) );
            _frame.setContentPane( this );
            _frame.setSize( getPreferredSize( ) );
            _frame.setResizable( true );
            _frame.setMaximizable( true );
            _frame.setClosable( true );
            _frame.addInternalFrameListener( this );
        }

        return _frame;
    }

    @Override
    public Dimension getPreferredSize ( )
    {
        return new Dimension( 800, 600 );
    }

    /**
     * Called when the program is opened.
     */
    public abstract void opened ( );

    /**
     * Called when the program is closed.
     */
    public abstract void closed ( );

    @Override
    public void internalFrameDeactivated ( InternalFrameEvent e )
    {
    }

    @Override
    public void internalFrameActivated ( InternalFrameEvent e )
    {
    }

    @Override
    public void internalFrameOpened ( InternalFrameEvent e )
    {
    }

    @Override
    public void internalFrameClosed ( InternalFrameEvent e )
    {
    }

    @Override
    public void internalFrameDeiconified ( InternalFrameEvent e )
    {
    }

    @Override
    public void internalFrameIconified ( InternalFrameEvent e )
    {
    }

    @Override
    public void internalFrameClosing ( InternalFrameEvent e )
    {
        closed( );
    }
}
