package org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.promasi.model.ProjectManager;
import org.promasi.shell.playmodes.singleplayerscoremode.SinglePlayerScorePlayMode;
import org.promasi.shell.ui.playmode.ILoginUi;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * The {@link ILoginUi} for the {@link SinglePlayerScorePlayMode}.
 * 
 * @author eddiefullmetal
 * 
 */
public class SinglePlayerScoreModeLoginUi
        extends JFrame
        implements ILoginUi
{

    /**
     * The name of the project manager.
     */
    private JTextField _nameField;

    /**
     * The last name of the project manager.
     */
    private JTextField _lastNameField;

    /**
     * The OK button.
     */
    private JButton _okButton;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( SinglePlayerScoreModeLoginUi.class );

    /**
     * Initializes the object.
     */
    public SinglePlayerScoreModeLoginUi( )
    {
        setTitle( ResourceManager.getString( SinglePlayerScoreModeLoginUi.class, "title" ) );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final double sizePercentage = 0.15d;
        setSize( ScreenUtils.sizeForPercentage( sizePercentage, sizePercentage ) );
        ScreenUtils.centerInScreen( this );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        _nameField = new JTextField( );
        _lastNameField = new JTextField( );
        _okButton = new JButton( ResourceManager.getString( SinglePlayerScoreModeLoginUi.class, "okButton", "text" ) );
        _okButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                login( );
            }
        } );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fillX( ) ) );
        add( new JLabel( ResourceManager.getString( SinglePlayerScoreModeLoginUi.class, "nameText" ) ), new CC( ) );
        add( _nameField, new CC( ).growX( ).wrap( ) );
        add( new JLabel( ResourceManager.getString( SinglePlayerScoreModeLoginUi.class, "lastNameText" ) ), new CC( ) );
        add( _lastNameField, new CC( ).growX( ).wrap( ) );
        add( _okButton, new CC( ).skip( 1 ).alignX( "right" ) );
    }

    @Override
    public void login ( )
    {
        String name = _nameField.getText( );
        String lastName = _lastNameField.getText( );

        if ( StringUtils.isBlank( name ) || StringUtils.isBlank( lastName ) )
        {
            JOptionPane.showMessageDialog( this, ResourceManager.getString( SinglePlayerScoreModeLoginUi.class, "invalidInput", "text" ),
                    ResourceManager.getString( SinglePlayerScoreModeLoginUi.class, "invalidInput", "title" ), JOptionPane.ERROR_MESSAGE );
        }
        else
        {
            LOGGER.info( "Logging for " + name + " " + lastName );
            // Create the company and assign it.
            ProjectManager projectManager = new ProjectManager( name, lastName );
            // Show the story selector frame.
            setVisible( false );
            dispose( );
            StorySelectorFrame storySelector = new StorySelectorFrame( projectManager );
            storySelector.setVisible( true );
        }
    }

    @Override
    public void showUi ( )
    {
        // Make sure that the setVisible is called inside the event queue.
        EventQueue.invokeLater( new Runnable( )
        {

            @Override
            public void run ( )
            {
                setVisible( true );
            }

        } );
    }

}
