package org.promasi.ui.promasiui.multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.CreateNewGameRequest;
import org.promasi.shell.ui.Story.StoriesPool;
import org.promasi.shell.ui.Story.Story;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.ui.promasiui.promasidesktop.story.StorySelectorFrame;
import org.promasi.utilities.ui.ScreenUtils;


public class MakeGameForm extends JFrame {

	/**
	 * 
	 */
	ProMaSiClient _client;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JButton _createGameButton;
	
    /**
     * A text area that displays the description of the selected {@link Story}.
     */
    private JEditorPane _descriptionText;
	
    /**
     * A label that displays the name of the selected {@link Story}.
     */
    private JLabel _playModeNameLabel;
    
	/**
     * A list that contains all the stories.
     */
    private JList _storiesList;
    
    /**
     * 
     */
    private List<Story> _stories;
    
	/**
	 * 
	 * @param client
	 * @throws NullArgumentException
	 */
	public MakeGameForm(ProMaSiClient client)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		

        setTitle( ResourceManager.getString( StorySelectorFrame.class, "title" ) );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final double sizePercentage = 0.4d;
        setSize( ScreenUtils.sizeForPercentage( sizePercentage, sizePercentage ) );
        ScreenUtils.centerInScreen( this );
        
        _storiesList = new JList( );
        _storiesList.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( )
        {
            @Override
            public void valueChanged ( ListSelectionEvent e )
            {
                selectionChanged( );
            }

        } );
        
        _storiesList.setBorder( BorderFactory.createTitledBorder( "Create new game" ) );
        _createGameButton = new JButton( "Play" );

        _playModeNameLabel = new JLabel( );
        _descriptionText = new JEditorPane( );
        _descriptionText.setContentType( "text/html" );
        _descriptionText.setEditable( false );
        
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( _storiesList ), new CC( ).spanY( ).growY( ).minWidth( "150px" ) );
        add( _playModeNameLabel, new CC( ).split( 2 ).flowY( ).alignX( "center" ) );
        add( _descriptionText, new CC( ).grow( ).wrap( ) );
        add( _createGameButton, new CC( ) );
        
        _stories=StoriesPool.getAllStories();
        _storiesList.setListData(_stories.toArray());
        
        _createGameButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
              	int gameId=_storiesList.getSelectedIndex();
            	if(gameId>=0)
            	{
            		Story currentStory=_stories.get(gameId);
            		CreateNewGameRequest request=new CreateNewGameRequest(currentStory);
            		_client.sendMessage(request.toProtocolString());
            	}
            	else
            	{
            		
            	}
            	
            }

        } );
        
		_client=client;
	}
	
	

    /**
     * Called when the selection of the {@link #_storiesList} changes. It shows
     * information for the selected play mode.
     */
    private void selectionChanged ( )
    {
    	int gameId=_storiesList.getSelectedIndex();
    	if(gameId>=0)
    	{
    		Story currentStory=_stories.get(gameId);
            _playModeNameLabel.setText(currentStory.getName());
            try
            {
            	String gameInfo=currentStory.getInfoString();
            	if( gameInfo!=null)
            	{
            		_descriptionText.setText(gameInfo);
            	}
            }
            catch ( Exception e )
            {

            }	
    	}
    	
    }
}
