package org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.ConfigurationException;
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
import org.promasi.model.Company;
import org.promasi.model.ProjectManager;
import org.promasi.shell.Shell;
import org.promasi.shell.playmodes.singleplayerscoremode.SinglePlayerScorePlayMode;
import org.promasi.shell.playmodes.singleplayerscoremode.StoriesPool;
import org.promasi.shell.playmodes.singleplayerscoremode.Story;
import org.promasi.ui.promasiui.promasidesktop.PlayModeSelectorFrame;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * A {@link JFrame} used for selecting a {@link Story}.
 * 
 * @author eddiefullmetal
 * 
 */
public class StorySelectorFrame
        extends JFrame
{

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
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( PlayModeSelectorFrame.class );

    /**
     * Initializes the object.
     */
    public StorySelectorFrame( ProjectManager projectManager )
    {
        LOGGER.info( "Selecting story..." );
        _projectManager = projectManager;
        setTitle( ResourceManager.getString( StorySelectorFrame.class, "title" ) );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final double sizePercentage = 0.4d;
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
        _storiesList = new JList( StoriesPool.getAllStories( ).toArray( ) );
        _storiesList.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( )
        {
            @Override
            public void valueChanged ( ListSelectionEvent e )
            {
                selectionChanged( );
            }

        } );
        _storiesList.setBorder( BorderFactory
                .createTitledBorder( ResourceManager.getString( StorySelectorFrame.class, "storiesList", "borderTitle" ) ) );
        _playButton = new JButton( ResourceManager.getString( StorySelectorFrame.class, "playButton", "text" ) );
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
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( _storiesList ), new CC( ).spanY( ).growY( ).minWidth( "150px" ) );
        add( _playModeNameLabel, new CC( ).split( 2 ).flowY( ).alignX( "center" ) );
        add( _descriptionText, new CC( ).grow( ).wrap( ) );
        add( _playButton, new CC( ) );
    }

    /**
     * Called when the selection of the {@link #_storiesList} changes. It shows
     * information for the selected play mode.
     */
    private void selectionChanged ( )
    {
        Story story = (Story) _storiesList.getSelectedValue( );
        _playModeNameLabel.setText( story.getName( ) );
        try
        {
            _descriptionText.setPage( story.getInfoFile( ).toURI( ).toURL( ) );
        }
        catch ( Exception e )
        {
            LOGGER.error( "Could not load url", e );
            _descriptionText.setText( ResourceManager.getString( StorySelectorFrame.class, "descriptionText", "errorMessage" ) );
        }
    }

    /**
     * Called when the {@link #_playButton} is clicked.
     */
    private void play ( )
    {
        Story story = (Story) _storiesList.getSelectedValue( );
        if ( story != null )
        {
            LOGGER.info( "Selected story:" + story );
            Company company = story.getCompany( );
            company.setProjectManager( _projectManager );
            _projectManager.setWorkingCompany( company );
            Shell.getInstance( ).setCompany( company );
            SinglePlayerScorePlayMode playMode = (SinglePlayerScorePlayMode) Shell.getInstance( ).getCurrentPlayMode( );
            playMode.setCurrentStory( story );
            setVisible( false );
            dispose( );
            try
            {
                Shell.getInstance( ).start( );
            }
            catch ( ConfigurationException e )
            {
                e.printStackTrace( );
            }
        }
        else
        {
            JOptionPane.showMessageDialog( this, ResourceManager.getString( StorySelectorFrame.class, "noSelectedStory", "text" ), ResourceManager
                    .getString( StorySelectorFrame.class, "noSelectedStory", "title" ), JOptionPane.ERROR_MESSAGE );
        }
    }
}
