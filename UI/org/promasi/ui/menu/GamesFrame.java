package org.promasi.ui.menu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;

import org.promasi.game.IGame;
import org.promasi.ui.utilities.ScreenUtils;


/**
 *
 * A {@link JFrame} used for selecting a {@link Story}.
 *
 * @author eddiefullmetal
 *
 */
public class GamesFrame extends JFrame
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * A list that contains all the stories.
     */
    private JList _gamesList;

    /**
     * A label that displays the name of the selected {@link Story}.
     */
    private JLabel _playModeNameLabel;

    /**
     * A text area that displays the description of the selected {@link Story}.
     */
    private JEditorPane _descriptionPane;

    /**
     * The play button that starts the main frame.
     */
    private JButton _playButton;

    /**
     * 
     */
    private Map<String, IGame> _games;
    
    /**
     * 
     */
    private List<IGamesFrameEventHandler> _eventHandlers;
    
    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( PlayModeSelectorFrame.class );

    /**
     * Initializes the object.
     * @throws IOException 
     */
    public GamesFrame()
    {
    	LOGGER.info( "Selecting story..." );
    	_eventHandlers=new LinkedList<IGamesFrameEventHandler>();
        setTitle( "Available games" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final double sizePercentage = 0.4d;
        setSize( ScreenUtils.sizeForPercentage( sizePercentage, sizePercentage ) );
        ScreenUtils.centerInScreen( this );
        
        _gamesList = new JList( );
        _gamesList.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( )
        {
            @Override
            public void valueChanged ( ListSelectionEvent e )
            {
                selectionChanged( );
            }

        } );
        
        _gamesList.setBorder( BorderFactory.createTitledBorder( "Games" ) );
        _playButton = new JButton( "Play" );
        _playButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                play();
            }

        } );
        
        _playModeNameLabel = new JLabel( );
        _descriptionPane = new JEditorPane( );
        _descriptionPane.setContentType( "text/html" );
        _descriptionPane.setEditable( false );
        
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( _gamesList ), new CC( ).spanY( ).growY( ).minWidth( "150px" ) );
        add( _playModeNameLabel, new CC( ).split( 2 ).flowY( ).alignX( "center" ) );
        add( _descriptionPane, new CC( ).grow( ).wrap( ) );
        add( _playButton, new CC( ) );
        
        _games=new TreeMap<String, IGame>();
        
    }

    /**
     * Called when the selection of the {@link #_gamesList} changes. It shows
     * information for the selected play mode.
     */
    private void selectionChanged ( )
    {
    	String gameName=_gamesList.getSelectedValue().toString();
    	if(gameName!=null && _games.containsKey(gameName))
    	{
            _playModeNameLabel.setText( gameName );
            _descriptionPane.setText(_games.get(gameName).getGameDescription());
    	}
    }

    /**
     * Called when the {@link #_playButton} is clicked.
     */
    private synchronized void play ( )
    {
    	String gameName=(String)_gamesList.getSelectedValue();
    	if(gameName!=null)
    	{
    		try
    		{
    			if(_games.containsKey(gameName)){
        			for(IGamesFrameEventHandler eventHandler : _eventHandlers){
        				eventHandler.gameSelected(this, _games.get(gameName));
        			}
    			}
    		}
    		catch(NullArgumentException e)
    		{
    			e.printStackTrace();
    		}
    		catch(IllegalArgumentException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	else
    	{
    		JOptionPane.showMessageDialog( this, "Please select your story first" , "Error ", JOptionPane.ERROR_MESSAGE );
    	}
    }
    
    /**
     * 
     * @param games
     */
    public synchronized void updateGameList(final Map<String, IGame> games)throws NullArgumentException, IllegalArgumentException{
    	if(games==null){
    		throw new NullArgumentException("Wrong argument games==null");
    	}
    	
    	List<String> gamesList=new LinkedList<String>();
    	_games.clear();
    	for(Map.Entry<String, IGame> entry : games.entrySet()){
    		if(entry.getKey()==null || entry.getValue()==null){
    			throw new IllegalArgumentException("Wrong argument games contains null");
    		}
    		
    		gamesList.add(entry.getKey());
    		_games.put(entry.getKey(), entry.getValue());
    	}
    	
    	_gamesList.setListData(gamesList.toArray());
    }
    
    /**
     * 
     * @param eventHandler
     * @return
     * @throws NullArgumentException
     */
    public boolean registerGamesFrameEventHandler(final IGamesFrameEventHandler eventHandler)throws NullArgumentException{
    	if(eventHandler==null){
    		throw new NullArgumentException("Wrong argument eventHandler==null");
    	}
    	
    	if(_eventHandlers.contains(eventHandler)){
    		return false;
    	}
    	
    	_eventHandlers.add(eventHandler);
    	return true;
    }
}
