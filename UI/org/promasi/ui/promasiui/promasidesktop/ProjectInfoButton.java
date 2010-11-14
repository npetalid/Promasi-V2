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
import org.promasi.game.IGame;
import org.promasi.game.project.SerializableProject;
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
public class ProjectInfoButton extends JButton implements MouseListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The current project.
     */
    private SerializableProject _currentProject;

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
    public ProjectInfoButton(IGame game )throws NullArgumentException
    {
    	if(game==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
        _lockObject = new Object( );
        _projectHasStarted = false;
        addMouseListener( this );

        setIcon( ResourceManager.getIcon( "projectInfo" ) );
        setFocusable( false );
        initializeComponents(game );
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
    private void initializeComponents (final IGame game )throws NullArgumentException
    {
    	if(game==null)
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
            	//game.jumpToProjectDate( );
            }

        } );
    }

    public void projectStarted ( SerializableProject project )
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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
