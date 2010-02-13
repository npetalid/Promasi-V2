package org.promasi.ui.promasiui.promasidesktop.programs.planner;


import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.promasi.shell.UiManager;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * Editor that adds a predecessor to a {@link GanttTaskSchedule}.
 * 
 * @author eddiefullmetal
 * 
 */
public class AddPredecessorEditor
        extends JDialog
        implements ActionListener
{

    /**
     * All the available {@link GanttTaskSchedule}s.
     */
    private List<GanttTaskSchedule> _allTasks;

    /**
     * The {@link GanttTaskSchedule} that this editor will edit.
     */
    private GanttTaskSchedule _taskToEdit;

    /**
     * A {@link JComboBox} that contains all available {@link GanttTaskSchedule}
     * .
     */
    private JComboBox _tasksCombo;

    /**
     * Button that closes the editor and saves the changes.
     */
    private JButton _okButton;

    /**
     * Initializes the object.
     */
    public AddPredecessorEditor( List<GanttTaskSchedule> allTasks, GanttTaskSchedule taskToEdit )
    {
        super( (Window) UiManager.getInstance( ).getRegisteredMainFrame( ) );
        setModal( true );
        setTitle( ResourceManager.getString( AddPredecessorEditor.class, "title" ) );
        setSize( ScreenUtils.sizeForPercentage( 0.2, 0.2 ) );
        ScreenUtils.centerInScreen( this );
        _taskToEdit = taskToEdit;
        // Copy the vector because this editor modifies the list.
        _allTasks = new Vector<GanttTaskSchedule>( );
        _allTasks.addAll( allTasks );
        _allTasks.remove( taskToEdit );
        _allTasks.removeAll( _taskToEdit.getPredecessors( ) );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        _tasksCombo = new JComboBox( _allTasks.toArray( ) );
        _okButton = new JButton( ResourceManager.getString( AddPredecessorEditor.class, "okButton", "text" ), ResourceManager.getIcon( "ok" ) );
        _okButton.addActionListener( this );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _tasksCombo, new CC( ).growX( ).wrap( ) );
        add( _okButton, new CC( ) );
    }

    /**
     * @return the {@link #_allTasks}.
     */
    public List<GanttTaskSchedule> getAllTasks ( )
    {
        return _allTasks;
    }

    /**
     * @param allTasks
     *            the {@link #_allTasks} to set.
     */
    public void setAllTasks ( List<GanttTaskSchedule> allTasks )
    {
        _allTasks = allTasks;
    }

    /**
     * @return the {@link #_taskToEdit}.
     */
    public GanttTaskSchedule getTaskToEdit ( )
    {
        return _taskToEdit;
    }

    /**
     * @param taskToEdit
     *            the {@link #_taskToEdit} to set.
     */
    public void setTaskToEdit ( GanttTaskSchedule taskToEdit )
    {
        _taskToEdit = taskToEdit;
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        if ( e.getSource( ).equals( _okButton ) )
        {
            try
            {
                GanttTaskSchedule taskSchedule = (GanttTaskSchedule) _tasksCombo.getSelectedItem( );
                if ( taskSchedule != null )
                {
                    _taskToEdit.addPredecessor( taskSchedule );
                }
            }
            catch ( IllegalArgumentException ex )
            {
                JOptionPane.showMessageDialog( this, ResourceManager.getString( AddPredecessorEditor.class, "relationExists", "text" ),
                        ResourceManager.getString( AddPredecessorEditor.class, "relationExists", "title" ), JOptionPane.WARNING_MESSAGE );
            }
            setVisible( false );
        }
    }
}
