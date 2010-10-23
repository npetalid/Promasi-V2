package org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode.projectFinishedUi;


import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.promasi.core.ISdObject;
import org.promasi.core.SdSystem;
import org.promasi.model.Project;
import org.promasi.shell.Shell;
import org.promasi.shell.playmodes.singleplayerscoremode.SinglePlayerScorePlayMode;
import org.promasi.ui.promasiui.promasidesktop.DesktopMainFrame;
import org.promasi.ui.promasiui.promasidesktop.programs.infogate.ProjectInfoPanel;
import org.promasi.ui.promasiui.promasidesktop.programs.planner.PlannerProgram;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.SwingCreator;


/**
 *
 * Implementation of the {@link IProjectFinishedUi} for the
 * {@link SinglePlayerScorePlayMode}.
 *
 * @author eddiefullmetal
 *
 */
public class SinglePlayerScoreModeProjectFinishedUi
        extends JPanel
        implements Runnable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The {@link JInternalFrame}.
     */
    private JInternalFrame _internalFrame;

    /**
     *A {@link JProgressBar} that displays the percentageCompleted of the
     * {@link Project}.
     */
    private JProgressBar _projectProgressBar;

    /**
     *A {@link JProgressBar} that displays the quality of the {@link Project}.
     */
    private JProgressBar _projectQualityBar;

    /**
     * A {@link JTextField} that displays the prestige points gained.
     */
    private JTextField _prestigePointsTextField;

    /**
     * A {@link JLabel} that displays the name of the project.
     */
    private JLabel _projectNameLabel;

    /**
     * A {@link JTabbedPane} that contains various info for the finished
     * project.
     */
    private JTabbedPane _tabbedPane;

    /**
     * A {@link ProjectInfoPanel} that displays information about the finished
     * project.
     */
    private ProjectInfoPanel _projectInfoPanel;

    /**
     * A {@link PlannerProgram} that displays information about the gantt that
     * was used in the finished project.
     */
    private PlannerProgram _ganttInfoPanel;

    /**
     * A {@link SdModelInfoPanel} that displays information about certain
     * {@link ISdObject}s of the {@link SdSystem} that was used in the finished
     * project.
     */
    private SdModelInfoPanel _systemInfoPanel;

    private DesktopMainFrame _mainFrame;
    
    private Shell _shell;

    /**
     * The finished {@link Project}.
     */
    private Project _project;

    /**
     * Initializes the object.
     *
     */
    public SinglePlayerScoreModeProjectFinishedUi(DesktopMainFrame mainFrame,Shell shell)throws NullArgumentException
    {
    	if(mainFrame==null)
    	{
    		throw new NullArgumentException("Wrong argument mainFrame==null");
    	}
    	
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
    	
    	_mainFrame=mainFrame;
    	_shell=shell;
       
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( getProjectNameLabel( ), new CC( ) );
        add( SwingCreator.createLabel( ResourceManager.getString( SinglePlayerScoreModeProjectFinishedUi.class, "prestigePoints" ), Font.BOLD ),
                new CC( ).alignX( "trailing" ).split( 2 ) );
        add( getPrestigePointsTextField( ), new CC( ).wrap( ) );
        add( getProjectProgressBar( ), new CC( ).growX( ).spanX( ).wrap( ) );
        add( getProjectQualityBar( ), new CC( ).growX( ).spanX( ).wrap( ) );
        add( getTabbedPane( ), new CC( ).grow( ).spanX( ) );
    }

    /**
     * @return the {@link #_internalFrame}.
     */
    public JInternalFrame getInternalFrame ( )
    {
        if ( _internalFrame == null )
        {
            _internalFrame = new JInternalFrame( ResourceManager.getString( SinglePlayerScoreModeProjectFinishedUi.class, "title" ) );
            _internalFrame.setFrameIcon( ResourceManager.getIcon( "projectFinished" ) );
            _internalFrame.setContentPane( this );
            _internalFrame.setSize( 800, 600 );
            _internalFrame.setResizable( true );
            _internalFrame.setMaximizable( true );
            _internalFrame.setClosable( true );
        }
        return _internalFrame;
    }

    /**
     * @return the {@link #_projectProgressBar}.
     */
    public JProgressBar getProjectProgressBar ( )
    {
        if ( _projectProgressBar == null )
        {
            _projectProgressBar = new JProgressBar( );
            _projectProgressBar.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( SinglePlayerScoreModeProjectFinishedUi.class,
                    "projectProgress" ) ) );
        }
        return _projectProgressBar;
    }

    /**
     * @return the {@link #_projectQualityBar}.
     */
    public JProgressBar getProjectQualityBar ( )
    {
        if ( _projectQualityBar == null )
        {
            _projectQualityBar = new JProgressBar( );
            _projectQualityBar.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( SinglePlayerScoreModeProjectFinishedUi.class,
                    "projectQuality" ) ) );
        }
        return _projectQualityBar;
    }

    /**
     * @return the {@link #_prestigePointsTextField}.
     */
    public JTextField getPrestigePointsTextField ( )
    {
        if ( _prestigePointsTextField == null )
        {
            _prestigePointsTextField = new JTextField( );
            _prestigePointsTextField.setEditable( false );
        }
        return _prestigePointsTextField;
    }

    /**
     * @return the {@link #_projectNameLabel}.
     */
    public JLabel getProjectNameLabel ( )
    {
        if ( _projectNameLabel == null )
        {
            _projectNameLabel = SwingCreator.createLabel( StringUtils.EMPTY, 14, Font.BOLD );
        }
        return _projectNameLabel;
    }

    /**
     * @return the {@link #_tabbedPane}.
     */
    public JTabbedPane getTabbedPane ( )
    {
        if ( _tabbedPane == null )
        {
            _tabbedPane = new JTabbedPane( );
            _tabbedPane.addTab( ResourceManager.getString( SinglePlayerScoreModeProjectFinishedUi.class, "projectInfo" ), getProjectInfoPanel( ) );
            _tabbedPane.addTab( ResourceManager.getString( SinglePlayerScoreModeProjectFinishedUi.class, "ganttInfo" ), getGanttInfoPanel( ) );
            _tabbedPane.addTab( ResourceManager.getString( SinglePlayerScoreModeProjectFinishedUi.class, "systemInfo" ), getSystemInfoPanel( ) );
        }
        return _tabbedPane;
    }

    /**
     * @return the {@link #_projectInfoPanel}.
     */
    public ProjectInfoPanel getProjectInfoPanel ( )
    {
        if ( _projectInfoPanel == null )
        {
            _projectInfoPanel = new ProjectInfoPanel( );
        }
        return _projectInfoPanel;
    }

    /**
     * @return the {@link #_ganttInfoPanel}.
     */
    public PlannerProgram getGanttInfoPanel ( )
    {
        if ( _ganttInfoPanel == null )
        {
            _ganttInfoPanel = new PlannerProgram(_mainFrame,_shell );
        }
        return _ganttInfoPanel;
    }

    /**
     * @return the {@link #_systemInfoPanel}.
     */
    public SdModelInfoPanel getSystemInfoPanel ( )
    {
        if ( _systemInfoPanel == null )
        {
            _systemInfoPanel = new SdModelInfoPanel(_shell );
        }
        return _systemInfoPanel;
    }

    public void showUi ( Project finishedProject )
    {
        _project = finishedProject;
        EventQueue.invokeLater( this );
    }

    @Override
    public void run ( )
    {
        getGanttInfoPanel( ).loadFromProject( _project );
        getGanttInfoPanel( ).readOnly( );
        getProjectProgressBar( ).setValue( (int) ( _project.getPercentageCompleted( ) * 100 ) );
        getProjectQualityBar( ).setValue( (int) _project.getQuality( ) );
        getPrestigePointsTextField( ).setText( String.valueOf( _project.getPrestigePoints( ) ) );
        getProjectNameLabel( ).setText( _project.getName( ) );
        getProjectInfoPanel( ).setProject( _project );
        getSystemInfoPanel( ).setProject( _project );
        _mainFrame.showWindow( getInternalFrame( ) );
    }

}
