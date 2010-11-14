package org.promasi.ui.menu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

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
import org.promasi.multiplayer.MultiPlayerPlayMode;
import org.promasi.playmode.IPlayMode;
import org.promasi.playmode.singleplayer.SinglePlayerPlayMode;
import org.promasi.ui.utilities.ScreenUtils;



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
     * 
     */
    private IPlayMode _singlePlayerPlayMode;
    
    /**
     * 
     */
    private IPlayMode _multiPlayerPlayMode;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( PlayModeSelectorFrame.class );

    /**
     * Initializes the object.
     */
    public PlayModeSelectorFrame()
    {
        LOGGER.info( "Selecting PlayMode" );
        setTitle( "Select PlayMode" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( ScreenUtils.sizeForPercentage( 0.5d, 0.5d ) );
        ScreenUtils.centerInScreen( this );
        
        List<String> playModes=new LinkedList<String>();
        _singlePlayerPlayMode=new SinglePlayerPlayMode();
        _multiPlayerPlayMode=new MultiPlayerPlayMode();
        playModes.add(_singlePlayerPlayMode.getName());
        playModes.add(_multiPlayerPlayMode.getName());
        
        _playModesList = new JList( playModes.toArray() );
        _playModesList.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( )
        {
            @Override
            public void valueChanged ( ListSelectionEvent e )
            {
                selectionChanged( );
            }

        } );
        
        _playModesList.setBorder( BorderFactory.createTitledBorder( "PlayMode" ) );
        _playButton = new JButton( "Play" );
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
        String playModeName =  _playModesList.getSelectedValue( ).toString();
        if(playModeName!=null){
            _playModeNameLabel.setText( playModeName );
            
            if( playModeName.equals(_singlePlayerPlayMode.getName()) ){
            	_descriptionText.setText( _singlePlayerPlayMode.getDescription( ) );
            }else if( playModeName.equals( _multiPlayerPlayMode.getName() ) ){
            	_descriptionText.setText( _multiPlayerPlayMode.getDescription( ) );
            }
        }
    }

    /**
     * Called when the {@link #_playButton} is clicked.
     */
    private void play ( )
    {
    	
        String playModeName = _playModesList.getSelectedValue( ).toString();
        if ( playModeName != null )
        {
            _playModeNameLabel.setText( playModeName );
            
            if( playModeName.equals(_singlePlayerPlayMode.getName()) ){
            	setVisible(false);
            	dispose();
            	_singlePlayerPlayMode.play();
            }else if( playModeName.equals( _multiPlayerPlayMode.getName() ) ){
            	setVisible(false);
            	dispose();
            	_multiPlayerPlayMode.play();
            }
            LOGGER.info( "Selected " + playModeName );
            setVisible( false );
        }
        else
        {
            JOptionPane.showMessageDialog( this, "PlayMode not selected","Please Select your PlayMode first", JOptionPane.ERROR_MESSAGE );
        }
    }
}
