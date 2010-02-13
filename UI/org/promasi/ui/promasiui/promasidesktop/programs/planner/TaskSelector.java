package org.promasi.ui.promasiui.promasidesktop.programs.planner;


import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.promasi.model.Project;
import org.promasi.model.Task;
import org.promasi.shell.Shell;
import org.promasi.shell.UiManager;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * {@link JDialog} that helps selecting an available {@link Task} from the
 * current {@link Project}.
 * 
 * @author eddiefullmetal
 * 
 */
public class TaskSelector
        extends JDialog
        implements ActionListener
{

    /**
     * A {@link JList} that contains all available {@link Task}s .
     */
    private JList _tasksList;

    /**
     * Button that closes the editor and saves the changes.
     */
    private JButton _selectButton;

    /**
     * Determines if the user clicked the select button.
     */
    private boolean _isSelected;

    /**
     * Initializes the object.
     */
    public TaskSelector( )
    {
        super( (Window) UiManager.getInstance( ).getRegisteredMainFrame( ) );
        setModal( true );
        setTitle( ResourceManager.getString( TaskSelector.class, "title" ) );
        setSize( ScreenUtils.sizeForPercentage( 0.2, 0.2 ) );
        ScreenUtils.centerInScreen( this );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        _tasksList = new JList( Shell.getInstance( ).getCurrentProject( ).getTasks( ).toArray( ) );
        _selectButton = new JButton( ResourceManager.getString( TaskSelector.class, "selectButton", "text" ), ResourceManager.getIcon( "ok" ) );
        _selectButton.addActionListener( this );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _tasksList, new CC( ).grow( ).wrap( ) );
        add( _selectButton, new CC( ) );
    }

    /**
     * @return the {@link #_isSelected}.
     */
    public boolean isSelected ( )
    {
        return _isSelected;
    }

    /**
     * @return The {@link Task} that the user selected, null if no task is
     *         selected.
     */
    public Task getSelectedTask ( )
    {
        return (Task) _tasksList.getSelectedValue( );
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        if ( e.getSource( ).equals( _selectButton ) )
        {
            _isSelected = true;
            setVisible( false );
        }
    }
}
