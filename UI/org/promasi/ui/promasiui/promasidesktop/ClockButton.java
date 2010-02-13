package org.promasi.ui.promasiui.promasidesktop;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.promasi.model.Clock;
import org.promasi.model.IClockListener;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * A {@link JButton} that is used as a clock.
 * 
 * @author eddiefullmetal
 * 
 */
public class ClockButton
        extends JButton
        implements IClockListener, Runnable, ActionListener
{

    /**
     * Initializes the object.
     */
    public ClockButton( )
    {
        setIcon( ResourceManager.getIcon( "clock" ) );
        addActionListener( this );
        Clock.getInstance( ).addListener( this );
        setFocusPainted( false );
    }

    /**
     * Invokes this runnable on the event queue. This method is not called from
     * the UI.
     */
    @Override
    public void ticked ( List<DurationFieldType> changedTypes )
    {
        EventQueue.invokeLater( this );
    }

    /**
     * Updates the text of the button to match the date of the {@link Clock}.
     * The date is parsed to string using the EEEE MMMM dd, HH:mm format.
     */
    @Override
    public void run ( )
    {
        DateTime currentDate = Clock.getInstance( ).getCurrentDateTime( );
        setText( ResourceManager.formatDetailedDateAndTime( currentDate ) );
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        JPopupMenu menu = new JPopupMenu( );
        menu.add( new ClockPanel( ) );
        menu.show( this, getWidth( ) - menu.getPreferredSize( ).width, getHeight( ) );
    }
}
