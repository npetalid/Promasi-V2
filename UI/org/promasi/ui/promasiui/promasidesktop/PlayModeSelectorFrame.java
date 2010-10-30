package org.promasi.ui.promasiui.promasidesktop;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.PlayModePool;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 *
 * A {@link JFrame} that allows the user to select a {@link IPlayMode}.
 *
 * @author eddiefullmetal
 *
 */
public class PlayModeSelectorFrame extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * A list that contains all the play modes.
     */
    private JList _playModesList;

    /**
     * A label that displays the name of the selected play mode.
     */
    private JLabel _playModeNameLabel;

    /**
     * A text area that displays the description of the selected play mode.
     */
    private JEditorPane _descriptionText;

    /**
     * The play button that starts the game.
     */
    private JButton _playButton;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( PlayModeSelectorFrame.class );

    /**
     * Initializes the object.
     */
    public PlayModeSelectorFrame()
    {
        LOGGER.info( "Selecting play mode" );
        setTitle( ResourceManager.getString( PlayModeSelectorFrame.class, "title" ) );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( ScreenUtils.sizeForPercentage( 0.5d, 0.5d ) );
        ScreenUtils.centerInScreen( this );
        
        _playModesList = new JList( new PlayModePool( ).getPlayModes().toArray() );
        _playModesList.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( )
        {
            @Override
            public void valueChanged ( ListSelectionEvent e )
            {
                selectionChanged( );
            }

        } );
        
        _playModesList.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( PlayModeSelectorFrame.class, "playModesList","borderTitle" ) ) );
        _playButton = new JButton( ResourceManager.getString( PlayModeSelectorFrame.class, "playButton", "text" ) );
        _playButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                play( );
            }

        } );
        
        _playModeNameLabel = new JLabel( );
        _descriptionText = new JEditorPane( );
        _descriptionText.setContentType( "text/html" );
        _descriptionText.setEditable( false );
        
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( _playModesList ), new CC( ).spanY( ).growY( ) );
        add( _playModeNameLabel, new CC( ).split( 2 ).flowY( ).alignX( "center" ) );
        add( _descriptionText, new CC( ).grow( ).wrap( ) );
        add( _playButton, new CC( ) );
    }

    /**
     * Called when the selection of the {@link #_playModesList} changes. It
     * shows information for the selected play mode.
     */
    private void selectionChanged ( )
    {
        IPlayMode playMode = (IPlayMode) _playModesList.getSelectedValue( );
        _playModeNameLabel.setText( playMode.getName( ) );
        _descriptionText.setText( playMode.getDescription( ) );
    }

    /**
     * Called when the {@link #_playButton} is clicked.
     */
    private void play ( )
    {
        IPlayMode playMode = (IPlayMode) _playModesList.getSelectedValue( );
        if ( playMode != null )
        {
            LOGGER.info( "Selected " + playMode.toString( ) );
            setVisible( false );
            
            LoginUi loginUi=new LoginUi(playMode);
            loginUi.showUi();
        }
        else
        {
            JOptionPane.showMessageDialog( this, ResourceManager.getString( PlayModeSelectorFrame.class, "noSelectedPlayMode", "text" ),
                    ResourceManager.getString( PlayModeSelectorFrame.class, "noSelectedPlayMode", "title" ), JOptionPane.ERROR_MESSAGE );
        }
    }
}
