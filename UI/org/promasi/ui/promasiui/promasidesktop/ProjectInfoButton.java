package org.promasi.ui.promasiui.promasidesktop;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.joda.time.DurationFieldType;
import org.promasi.model.Clock;
import org.promasi.model.Employee;
import org.promasi.model.IClockListener;
import org.promasi.model.Project;
import org.promasi.shell.IShellListener;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 *
 * Button that shows which is the current project. How many time is left before
 * the project starts. It also provides an option to immediately start the
 * project(moves the date to the project start date) which is available if the
 * project has not started yet.
 *
 *
 * @author eddiefullmetal
 *
 */
public class ProjectInfoButton
        extends JButton
        implements IShellListener, MouseListener, IClockListener
{

    /**
     * The current project.
     */
    private Project _currentProject;

    /**
     * Label that displays how much time is left before the project will start
     * or end.
     */
    private JLabel _remainingTimeLabel;

    /**
     * A {@link JButton} that when clicked it moves the current day to the
     * project start day.
     */
    private JButton _jumpToProject;

    /**
     * The popup that shows the info.
     */
    private JPopupMenu _popUpInfo;

    /**
     * Flag that indicates if the project has started.
     */
    private volatile boolean _projectHasStarted;

    /**
     * Object used for locking.
     */
    private Object _lockObject;

    /**
     * Initializes the object.
     *
     */
    public ProjectInfoButton(Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
        _lockObject = new Object( );
        _projectHasStarted = false;
        addMouseListener( this );
        shell.addListener( this );
        Clock.getInstance( ).addListener( this );
        setIcon( ResourceManager.getIcon( "projectInfo" ) );
        setFocusable( false );
        initializeComponents(shell );
    }

    /**
     * Initializes the {@link #_popUpInfo}.
     */
    private void initializePopup ( )
    {
        _popUpInfo = new JPopupMenu( );
        _popUpInfo.setBorder( BorderFactory.createTitledBorder( _currentProject.getName( ) ) );
        _popUpInfo.setLayout( new MigLayout( new LC( ).fill( ) ) );
        _popUpInfo.add( _remainingTimeLabel, new CC( ).growX( ).spanX( ).wrap( ) );
        _popUpInfo.add( _jumpToProject, new CC( ).skip( 1 ) );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents (final Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
        _remainingTimeLabel = new JLabel( );
        _jumpToProject = new JButton( ResourceManager.getIcon( "jumpToProject" ) );
        _jumpToProject.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                shell.jumpToProjectDate( );
            }

        } );
    }

    @Override
    public void projectStarted ( Project project )
    {
        synchronized ( _lockObject )
        {
            _projectHasStarted = true;
            EventQueue.invokeLater( new Runnable( )
            {

                @Override
                public void run ( )
                {
                    _jumpToProject.setEnabled( false );
                }

            } );
        }
    }

    @Override
    public void projectFinished ( Project project )
    {
        synchronized ( _lockObject )
        {
            _currentProject = null;
            _projectHasStarted = false;
            EventQueue.invokeLater( new Runnable( )
            {

                @Override
                public void run ( )
                {
                    _jumpToProject.setEnabled( false );
                }

            } );
        }
    }

    @Override
    public void employeeHired ( Employee employee )
    {
    }

    @Override
    public void projectAssigned ( Project project )
    {
        synchronized ( _lockObject )
        {
            _currentProject = project;
            EventQueue.invokeLater( new Runnable( )
            {

                @Override
                public void run ( )
                {
                    initializePopup( );
                    _jumpToProject.setEnabled( true );
                }

            } );
        }
    }

    @Override
    public void mouseClicked ( MouseEvent e )
    {
        if ( _popUpInfo != null && _currentProject != null )
        {
            if ( !_popUpInfo.isVisible( ) )
            {
                _popUpInfo.show( this, getWidth( ) - _popUpInfo.getPreferredSize( ).width, getHeight( ) );
            }
            else
            {
                _popUpInfo.setVisible( false );
            }
        }
    }

    @Override
    public void mouseEntered ( MouseEvent e )
    {
    }

    @Override
    public void mouseExited ( MouseEvent e )
    {
    }

    @Override
    public void mousePressed ( MouseEvent e )
    {
    }

    @Override
    public void mouseReleased ( MouseEvent e )
    {
    }

    @Override
    public void ticked ( List<DurationFieldType> changedTypes )
    {
        synchronized ( _lockObject )
        {
            if ( _currentProject != null )
            {
                EventQueue.invokeLater( new Runnable( )
                {

                    @Override
                    public void run ( )
                    {
                        if ( !_projectHasStarted )
                        {
                            int days = _currentProject.getStartDate( ).getDayOfYear( ) - Clock.getInstance( ).getCurrentDateTime( ).getDayOfYear( );
                            _remainingTimeLabel.setText( String.valueOf( days ) + " "
                                    + ResourceManager.getString( ProjectInfoButton.class, "remainingTimeToStart" ) );
                        }
                        else
                        {
                            int days = _currentProject.getEndDate( ).getDayOfYear( ) - Clock.getInstance( ).getCurrentDateTime( ).getDayOfYear( );
                            _remainingTimeLabel.setText( String.valueOf( days ) + " "
                                    + ResourceManager.getString( ProjectInfoButton.class, "remainingTimeToEnd" ) );
                        }
                    }

                } );
            }
        }
    }
}
