package org.promasi.ui.promasiui.promasidesktop.programs.planner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.promasi.model.Employee;
import org.promasi.model.EmployeeTeamData;
import org.promasi.ui.promasiui.promasidesktop.DesktopMainFrame;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * Editor for adding resources to a {@link GanttTaskSchedule}.
 * 
 * @author eddiefullmetal
 * 
 */
public class AddResourceEditor
        extends JDialog
        implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * All the available {@link Employee}s.
     */
    private List<Employee> _allEmployees;

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
     * The number of hours that this employee will work on the task.
     */
    private JSpinner _hoursToWorkSpinner;

    /**
     * Button that closes the editor and saves the changes.
     */
    private JButton _okButton;

    /**
     * Initializes the object.
     */
    public AddResourceEditor( DesktopMainFrame mainFrame, List<Employee> allEmployees, GanttTaskSchedule taskToEdit )
    {
        super( mainFrame );
        setModal( true );
        setTitle( ResourceManager.getString( AddResourceEditor.class, "title" ) );
        setSize( ScreenUtils.sizeForPercentage( 0.2, 0.2 ) );
        ScreenUtils.centerInScreen( this );
        _taskToEdit = taskToEdit;
        // Copy the vector because this editor modifies the list.
        _allEmployees = new Vector<Employee>( );
        _allEmployees.addAll( allEmployees );
        _allEmployees.remove( taskToEdit );
        _allEmployees.removeAll( _taskToEdit.getPredecessors( ) );
       
        _tasksCombo = new JComboBox( _allEmployees.toArray( ) );
        _okButton = new JButton( ResourceManager.getString( AddResourceEditor.class, "okButton", "text" ), ResourceManager.getIcon( "ok" ) );
        _okButton.addActionListener( this );
        _hoursToWorkSpinner = new JSpinner( new SpinnerNumberModel( 8, 1, 8, 1 ) );

        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _tasksCombo, new CC( ).growX( ).wrap( ) );
        add( _hoursToWorkSpinner, new CC( ).growX( ).wrap( ) );
        add( _okButton, new CC( ) );
    }

    /**
     * @return the {@link #_allEmployees}.
     */
    public List<Employee> getAllEmployees ( )
    {
        return _allEmployees;
    }

    /**
     * @param allEmployees
     *            the {@link #_allEmployees} to set.
     */
    public void setAllEmployees ( List<Employee> allEmployees )
    {
        _allEmployees = allEmployees;
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
                Employee employee = (Employee) _tasksCombo.getSelectedItem( );
                if ( employee != null )
                {
                    EmployeeTeamData teamData = new EmployeeTeamData( employee, (Integer) _hoursToWorkSpinner.getValue( ) );
                    _taskToEdit.addResource( teamData );
                }
            }
            catch ( IllegalArgumentException ex )
            {
                JOptionPane.showMessageDialog( this, ResourceManager.getString( AddResourceEditor.class, "resourceExists", "text" ), ResourceManager
                        .getString( AddResourceEditor.class, "resourceExists", "title" ), JOptionPane.WARNING_MESSAGE );
            }
            setVisible( false );
        }
    }
}
