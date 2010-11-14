package org.promasi.ui.promasiui.multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import org.promasi.ui.menu.GamesFrame;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.ui.utilities.ScreenUtils;


public class ChooseStoryFrame extends JFrame {

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
    private Map<String,String> _availableGames;
    
	/**
	 * 
	 * @param client
	 * @throws NullArgumentException
	 */
	public ChooseStoryFrame(ProMaSiClient client, Map<String, String> availableGames)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		if(availableGames==null)
		{
			throw new NullArgumentException("Wrong argument stories==null");
		}
		
		for(Map.Entry<String, String> entry : availableGames.entrySet())
		{
			if(entry.getKey()==null || entry.getValue()==null)
			{
				throw new IllegalArgumentException("Wrong argument stories contains null");
			}
		}

		_availableGames=availableGames;
		
        setTitle( ResourceManager.getString( GamesFrame.class, "title" ) );
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
        _createGameButton = new JButton( "Create" );

        _playModeNameLabel = new JLabel( );
        _descriptionText = new JEditorPane( );
        _descriptionText.setContentType( "text/html" );
        _descriptionText.setEditable( false );
        
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( _storiesList ), new CC( ).spanY( ).growY( ).minWidth( "150px" ) );
        add( _playModeNameLabel, new CC( ).split( 2 ).flowY( ).alignX( "center" ) );
        add( _descriptionText, new CC( ).grow( ).wrap( ) );
        add( _createGameButton, new CC( ) );
        
        List<String> stories=new Vector<String>();
        for(Map.Entry<String, String> entry : availableGames.entrySet())
        {
        	stories.add(entry.getKey());
        }
        
        _storiesList.setListData(stories.toArray());
        _createGameButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
              	int gameId=_storiesList.getSelectedIndex();
            	if(gameId>=0)
            	{
            		CreateGameFrame createGameForm=new CreateGameFrame(_client, (String)_storiesList.getSelectedValue());
            		createGameForm.setVisible(true);
            		setVisible(false);
            		/*CreateNewGameRequest request=new CreateNewGameRequest(currentStory);
            		_client.sendMessage(request.toProtocolString());
            		setVisible(false);*/
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
    	if(_storiesList.getSelectedIndex()>=0)
    	{
    		String storyDescription=_availableGames.get(_storiesList.getSelectedValue());
            _playModeNameLabel.setText((String)_storiesList.getSelectedValue());
            _descriptionText.setText(storyDescription);
    	}
    	
    }
}
