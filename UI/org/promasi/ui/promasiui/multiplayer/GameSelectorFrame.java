package org.promasi.ui.promasiui.multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

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

import org.promasi.ui.menu.GamesFrame;
import org.promasi.ui.menu.PlayModeSelectorFrame;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.ui.utilities.ScreenUtils;

public class GameSelectorFrame extends JFrame implements Runnable {
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
     * 
     */
    private JButton _createGameButton;
    
    /**
     * 
     */
    private Thread _updateThread;
    
    /**
     * 
     */
    private boolean _stopUpdating;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( PlayModeSelectorFrame.class );

    /**
     * Initializes the object.
     * @throws IOException 
     */
    public GameSelectorFrame()throws IOException
    {
    	LOGGER.info( "Selecting story..." );

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
        
        _createGameButton=new JButton("New game");
        _createGameButton.addActionListener( new ActionListener( )
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
            	createNewGame( );
            }

        } );
        
        _storiesList.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( GamesFrame.class, "storiesList", "borderTitle" ) ) );
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
        add( _createGameButton, new CC() );
        
        _updateThread=new Thread(this);
        _updateThread.start();
    }

    /**
     * Called when the selection of the {@link #_storiesList} changes. It shows
     * information for the selected play mode.
     */
    private void selectionChanged ( )
    {
    	String gameId=(String)_storiesList.getSelectedValue();
    	if(gameId!=null)
    	{
            _playModeNameLabel.setText( gameId );
            try
            {

            }
            catch ( Exception e )
            {
                LOGGER.error( "Could not load url", e );
                _descriptionText.setText( ResourceManager.getString( GamesFrame.class, "descriptionText", "errorMessage" ) );
            }	
    	}
    }

    /**
     * Called when the {@link #_playButton} is clicked.
     */
    private synchronized void play ( )
    {
    	String selectedIndex=(String)_storiesList.getSelectedValue();
    	if(selectedIndex!=null)
    	{
    		try
    		{
        		/*if(_currentPlayMode.play(selectedIndex,_projectManager))
        		{
        			_stopUpdating=true;
                    setVisible( false );
                    dispose( );
        		} 
        		else
        		{
        			JOptionPane.showMessageDialog( this, "Please select your story first" , "Error ", JOptionPane.ERROR_MESSAGE );
        		}*/
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
     */
    private synchronized void createNewGame()
    {
    	_stopUpdating=true;
    }
    
    
	@Override
	public void run() {
		while(!_stopUpdating){
			synchronized(this)
			{
				try
				{
					int gameId=_storiesList.getSelectedIndex();

				}
				catch(IllegalArgumentException e)
				{
					e.printStackTrace();
				}
			}
		    
		    try 
		    {
				Thread.sleep(5000);
			} 
		    catch (InterruptedException e) 
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
