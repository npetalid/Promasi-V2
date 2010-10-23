package org.promasi.ui.promasiui.promasidesktop.story;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

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

import org.promasi.model.ProjectManager;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.ui.Story.Story;

import org.promasi.ui.promasiui.promasidesktop.PlayModeSelectorFrame;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;
import java.util.List;
import java.util.Vector;


/**
 *
 * A {@link JFrame} used for selecting a {@link Story}.
 *
 * @author eddiefullmetal
 *
 */
public class StorySelectorFrame extends JFrame implements Runnable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * A list that contains all the stories.
     */
    private JList _storiesList;

    /**
     * A label that displays the name of the selected {@link Story}.
     */
    private JLabel _playModeNameLabel;

    /**
     * A text area that displays the description of the selected {@link Story}.
     */
    private JEditorPane _descriptionText;

    /**
     * The play button that starts the main frame.
     */
    private JButton _playButton;

    /**
     * The user that logged in.
     */
    private ProjectManager _projectManager;
    
    /**
     * 
     */
    private Thread _updateThread;
    
    /**
     * 
     */
    private boolean _stopUpdating;
    
    /**
     * 
     */
    private IPlayMode _currentPlayMode;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( PlayModeSelectorFrame.class );

    /**
     * Initializes the object.
     * @throws IOException 
     */
    public StorySelectorFrame( ProjectManager projectManager, IPlayMode playMode)throws NullArgumentException, IOException
    {
    	if(projectManager==null)
    	{
    		throw new NullArgumentException("Wrong argument projectManager==null");
    	}
    	
    	if(playMode==null){
    		throw new NullArgumentException("Wrong argument playMode==null");
    	}
    	
    	_currentPlayMode=playMode;

    	LOGGER.info( "Selecting story..." );
    	
        _projectManager = projectManager;
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
        
        _storiesList.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( StorySelectorFrame.class, "storiesList", "borderTitle" ) ) );
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
        add( new JScrollPane( _storiesList ), new CC( ).spanY( ).growY( ).minWidth( "150px" ) );
        add( _playModeNameLabel, new CC( ).split( 2 ).flowY( ).alignX( "center" ) );
        add( _descriptionText, new CC( ).grow( ).wrap( ) );
        add( _playButton, new CC( ) );
        
        _updateThread=new Thread(this);
        _updateThread.start();
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
            _playModeNameLabel.setText( _currentPlayMode.getGameDescription(gameId));
            try
            {
            	String gameInfo=_currentPlayMode.getGameInfo(gameId);
            	if( gameInfo!=null && _currentPlayMode.getGameInfo(gameId)!=null )
            	{
            		_descriptionText.setText( _currentPlayMode.getGameInfo( gameId) );
            	}
            }
            catch ( Exception e )
            {
                LOGGER.error( "Could not load url", e );
                _descriptionText.setText( ResourceManager.getString( StorySelectorFrame.class, "descriptionText", "errorMessage" ) );
            }	
    	}
    }

    /**
     * Called when the {@link #_playButton} is clicked.
     */
    private synchronized void play ( )
    {
    	int selectedIndex=_storiesList.getSelectedIndex();
    	if(selectedIndex>=0)
    	{
    		try
    		{
        		if(_currentPlayMode.play(selectedIndex,_projectManager))
        		{
        			_stopUpdating=true;
                    setVisible( false );
                    dispose( );
        		} 
        		else
        		{
        			JOptionPane.showMessageDialog( this, "Please select your story first" , "Error ", JOptionPane.ERROR_MESSAGE );
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

	@Override
	public void run() {
		while(!_stopUpdating){
			synchronized(this)
			{
				try
				{
					int gameId=_storiesList.getSelectedIndex();

					List<String> games = _currentPlayMode.getGamesList();
				    if(games!=null){
				       _storiesList.setListData(new Vector<String>(games));
				    }
				    
					if(gameId>=0)
					{
						_storiesList.setSelectedIndex(gameId);
					}
				}
				catch(IllegalArgumentException e)
				{
					e.printStackTrace();
				}
			}
		    
		    try 
		    {
				Thread.sleep(1000);
			} 
		    catch (InterruptedException e) 
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
