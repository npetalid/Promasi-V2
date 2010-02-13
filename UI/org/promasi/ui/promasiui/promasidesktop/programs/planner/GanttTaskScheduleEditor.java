package org.promasi.ui.promasiui.promasidesktop.programs.planner;


import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.promasi.model.EmployeeTeamData;
import org.promasi.shell.Shell;
import org.promasi.shell.UiManager;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * Edits a {@link GanttTaskSchedule}.
 * 
 * @author eddiefullmetal
 * 
 */
public class GanttTaskScheduleEditor
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
     * Categorizes the properties of the {@link GanttTaskSchedule}.
     */
    private JTabbedPane _tabPane;

    /**
     * The panel that contains the general properties of the
     * {@link GanttTaskSchedule}.
     */
    private JPanel _generalPropertiesPanel;

    /**
     * The panel that contains the predecessors properties of the
     * {@link GanttTaskSchedule}.
     */
    private JPanel _predecessorsPropertiesPanel;

    /**
     * The panel that contains the resource properties of the
     * {@link GanttTaskSchedule}.
     */
    private JPanel _resourcesPropertiesPanel;

    /**
     * The text field that contains the name of the {@link GanttTaskSchedule}.
     */
    private JTextField _nameField;

    /**
     * The {@link JSpinner} that contains the number of days for the task.
     */
    private JSpinner _workSpinner;

    /**
     * {@link JList} that contains all the predecessors of the
     * {@link #_taskToEdit}.
     */
    private JList _predecessorsList;

    /**
     * {@link JTable} that contains all the resources of the
     * {@link #_taskToEdit} .
     */
    private JTable _resourcesTable;

    /**
     * Button that closes the editor and saves the changes.
     */
    private JButton _okButton;

    /**
     * Button that adds a predecessor to the {@link #_taskToEdit}.
     */
    private JButton _addPredecessorButton;

    /**
     * Button that removes a predecessor from the {@link #_taskToEdit}.
     */
    private JButton _removePredecessorButton;

    /**
     * Button that adds a resource to the {@link #_taskToEdit}.
     */
    private JButton _addResourceButton;

    /**
     * Button that removes a resource from the {@link #_taskToEdit}.
     */
    private JButton _removeResourceButton;

    /**
     * Initializes the object.
     * 
     * @param allTasks
     *            The {@link #_allTasks}.
     * @param taskToEdit
     *            The {@link #_taskToEdit}.
     */
    public GanttTaskScheduleEditor( List<GanttTaskSchedule> allTasks, GanttTaskSchedule taskToEdit )
    {
        super( (Window) UiManager.getInstance( ).getRegisteredMainFrame( ) );
        setModal( true );
        setTitle( ResourceManager.getString( GanttTaskScheduleEditor.class, "title" ) );
        setSize( ScreenUtils.sizeForPercentage( 0.4, 0.2 ) );
        ScreenUtils.centerInScreen( this );
        _allTasks = allTasks;
        _taskToEdit = taskToEdit;
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        // Setup the general properties tab and all of its components.
        _nameField = new JTextField( _taskToEdit.getName( ) );
        _nameField.setEditable( false );
        _workSpinner = new JSpinner( new SpinnerNumberModel( _taskToEdit.getWork( ), 1, Integer.MAX_VALUE, 1 ) );
        _generalPropertiesPanel = new JPanel( );
        _generalPropertiesPanel.setLayout( new MigLayout( new LC( ).fillX( ) ) );
        _generalPropertiesPanel.add( new JLabel( ResourceManager.getString( GanttTaskScheduleEditor.class, "generalProperties", "nameText" ) ),
                new CC( ) );
        _generalPropertiesPanel.add( _nameField, new CC( ).growX( ).wrap( ) );
        _generalPropertiesPanel.add( new JLabel( ResourceManager.getString( GanttTaskScheduleEditor.class, "generalProperties", "workText" ) ),
                new CC( ) );
        _generalPropertiesPanel.add( _workSpinner, new CC( ).growX( ).wrap( ) );
        // Setup the predecessors properties tab and all of its components.
        _addPredecessorButton = new JButton( ResourceManager.getString( GanttTaskScheduleEditor.class, "addPredecessorButton", "text" ),
                ResourceManager.getIcon( "addPredecessor" ) );
        _addPredecessorButton.addActionListener( this );
        _removePredecessorButton = new JButton( ResourceManager.getString( GanttTaskScheduleEditor.class, "removePredecessorButton", "text" ),
                ResourceManager.getIcon( "removePredecessor" ) );
        _removePredecessorButton.addActionListener( this );
        _predecessorsList = new JList( _taskToEdit.getPredecessors( ).toArray( ) );
        _predecessorsPropertiesPanel = new JPanel( );
        _predecessorsPropertiesPanel.setLayout( new MigLayout( new LC( ).fill( ) ) );
        _predecessorsPropertiesPanel.add( new JScrollPane( _predecessorsList ), new CC( ).grow( ).spanX( ).wrap( ) );
        _predecessorsPropertiesPanel.add( _addPredecessorButton, new CC( ) );
        _predecessorsPropertiesPanel.add( _removePredecessorButton, new CC( ) );
        // Setup the resource properties tab and all of its components.
        _addResourceButton = new JButton( ResourceManager.getString( GanttTaskScheduleEditor.class, "addResourceButton", "text" ), ResourceManager
                .getIcon( "addPredecessor" ) );
        _addResourceButton.addActionListener( this );
        _removeResourceButton = new JButton( ResourceManager.getString( GanttTaskScheduleEditor.class, "removeResourceButton", "text" ),
                ResourceManager.getIcon( "removePredecessor" ) );
        _removeResourceButton.addActionListener( this );
        _resourcesTable = new JTable( new EmployeeTeamDataTableModel( _taskToEdit.getResources( ) ) );
        _resourcesPropertiesPanel = new JPanel( );
        _resourcesPropertiesPanel.setLayout( new MigLayout( new LC( ).fill( ) ) );
        _resourcesPropertiesPanel.add( new JScrollPane( _resourcesTable ), new CC( ).grow( ).spanX( ).wrap( ) );
        _resourcesPropertiesPanel.add( _addResourceButton, new CC( ) );
        _resourcesPropertiesPanel.add( _removeResourceButton, new CC( ) );
        // Setup the tabPane
        _tabPane = new JTabbedPane( );
        _tabPane.addTab( ResourceManager.getString( GanttTaskScheduleEditor.class, "generalTabName" ), _generalPropertiesPanel );
        _tabPane.addTab( ResourceManager.getString( GanttTaskScheduleEditor.class, "predecessorsTabName" ), _predecessorsPropertiesPanel );
        _tabPane.addTab( ResourceManager.getString( GanttTaskScheduleEditor.class, "resourcesTabName" ), _resourcesPropertiesPanel );
        // ----------------
        _okButton = new JButton( ResourceManager.getString( GanttTaskScheduleEditor.class, "okButton", "text" ), ResourceManager.getIcon( "ok" ) );
        _okButton.setToolTipText( ResourceManager.getString( GanttTaskScheduleEditor.class, "okButton", "tooltip" ) );
        _okButton.addActionListener( this );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _tabPane, new CC( ).grow( ).wrap( ) );
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
            _taskToEdit.setWork( (Integer) _workSpinner.getValue( ) );
            setVisible( false );
        }
        else if ( e.getSource( ).equals( _addPredecessorButton ) )
        {
            AddPredecessorEditor editor = new AddPredecessorEditor( _allTasks, _taskToEdit );
            editor.setVisible( true );
            _predecessorsList.setListData( _taskToEdit.getPredecessors( ).toArray( ) );
            repaint( );
        }
        else if ( e.getSource( ).equals( _removePredecessorButton ) )
        {
            GanttTaskSchedule taskSchedule = (GanttTaskSchedule) _predecessorsList.getSelectedValue( );
            if ( taskSchedule != null )
            {
                _taskToEdit.removePredecessor( taskSchedule );
                _predecessorsList.setListData( _taskToEdit.getPredecessors( ).toArray( ) );
            }
        }
        else if ( e.getSource( ).equals( _addResourceButton ) )
        {
            AddResourceEditor editor = new AddResourceEditor( Shell.getInstance( ).getHiredEmployees( ), _taskToEdit );
            editor.setVisible( true );
            _resourcesTable.setModel( new EmployeeTeamDataTableModel( _taskToEdit.getResources( ) ) );
            repaint( );
        }
        else if ( e.getSource( ).equals( _removeResourceButton ) )
        {
            EmployeeTeamData resource = ( (EmployeeTeamDataTableModel) _resourcesTable.getModel( ) ).getTaskEmployeeTeamDataAt( _resourcesTable
                    .getSelectedRow( ) );
            if ( resource != null )
            {
                _taskToEdit.removeResource( resource );
                _resourcesTable.setModel( new EmployeeTeamDataTableModel( _taskToEdit.getResources( ) ) );
            }
        }
    }

}
