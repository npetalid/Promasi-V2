package org.promasi.ui.promasiui.promasidesktop;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXMonthView;
import org.joda.time.DurationFieldType;
import org.promasi.model.Clock;
import org.promasi.model.IClockListener;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * Panel that configures the clock speed and shows a calendar.
 * 
 * @author eddiefullmetal
 * 
 */
public class ClockPanel
        extends JPanel
        implements ActionListener, IClockListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXMonthView _calendar;
    private JButton _slowButton;
    private JButton _normalButton;
    private JButton _fastButton;

    /**
     * Initializes the object.
     */
    public ClockPanel( )
    {
        Clock.getInstance( ).addListener( this );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        Date currentDate = Clock.getInstance( ).getCurrentDateTime( ).toDate( );
        _calendar = new JXMonthView( );
        _calendar.setFirstDisplayedDay( currentDate );
        _calendar.setTraversable( true );
        _calendar.setTodayBackground( Color.white );
        _calendar.setFlaggedDates( currentDate );
        _calendar.setFlaggedDayForeground( Color.RED );

        _slowButton = new JButton( ResourceManager.getString( ClockPanel.class, "slowButtonText" ) );
        _slowButton.addActionListener( this );
        _normalButton = new JButton( ResourceManager.getString( ClockPanel.class, "normalButtonText" ) );
        _normalButton.addActionListener( this );
        _fastButton = new JButton( ResourceManager.getString( ClockPanel.class, "fastButtonText" ) );
        _fastButton.addActionListener( this );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _calendar, new CC( ).grow( ).spanX( ).wrap( ) );
        add( new JLabel( ResourceManager.getString( ClockPanel.class, "speedText" ) ), new CC( ) );
        add( _slowButton, new CC( ) );
        add( _normalButton, new CC( ) );
        add( _fastButton, new CC( ).wrap( ) );
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        if ( e.getSource( ).equals( _slowButton ) )
        {
            Clock.getInstance( ).setSpeed( 3000 );
        }
        else if ( e.getSource( ).equals( _normalButton ) )
        {
            Clock.getInstance( ).setSpeed( 1000 );
        }
        else if ( e.getSource( ).equals( _fastButton ) )
        {
            Clock.getInstance( ).setSpeed( 100 );
        }
    }

    @Override
    public void ticked ( List<DurationFieldType> changedTypes )
    {
        if ( changedTypes.contains( DurationFieldType.days( ) ) )
        {
            _calendar.setFlaggedDates( Clock.getInstance( ).getCurrentDateTime( ).toDate( ) );
        }
    }

    @Override
    public void removeNotify ( )
    {
        super.removeNotify( );
        Clock.getInstance( ).removeListener( this );
    }
}
