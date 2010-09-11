package org.promasi.ui.promasiui.promasidesktop.programs.infogate;


import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.promasi.model.Project;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * Panel that shows information about a {@link Project}.
 * 
 * @author eddiefullmetal
 * 
 */
public class ProjectInfoPanel
        extends JPanel
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * A {@link JLabel} that shows the name of the {@link #_project}.
     */
    private JLabel _projectNameLabel;

    /**
     * A {@link JTextArea} that shows the description of the {@link #_project}.
     */
    private JEditorPane _projectDescriptionArea;

    /**
     * A {@link JLabel} that shows the startDate of the {@link #_project}.
     */
    private JLabel _projectStartDateLabel;

    /**
     * A {@link JLabel} that shows the endDate of the {@link #_project}.
     */
    private JLabel _projectEndDateLabel;

    /**
     * A {@link JLabel} that shows the budget of the {@link #_project}.
     */
    private JLabel _projectBudgetLabel;

    /**
     * The controller for this panel.
     */
    private ProjectInfoPanelController _controller;

    /**
     * Initializes the object.
     * 
     * @param project
     *            The {@link #_project}.
     */
    public ProjectInfoPanel( )
    {
        _controller = new ProjectInfoPanelController( this );
        initialize( );
    }

    /**
     * Initializes the ui.
     */
    private void initialize ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ), new AC( ), new AC( ).index( 4 ).grow( 1 ) ) );
        add( getProjectNameLabel( ), new CC( ).growX( ).wrap( ) );
        add( new JSeparator( JSeparator.HORIZONTAL ), new CC( ).growX( ).spanX( ).wrap( ) );
        add( new JScrollPane( getProjectDescriptionArea( ) ), new CC( ).spanY( ).grow( ) );
        add( new JLabel( ResourceManager.getString( ProjectInfoPanel.class, "StartDateName" ) ), new CC( ).alignX( "trailing" ) );
        add( getProjectStartDateLabel( ), new CC( ).wrap( ) );
        add( new JLabel( ResourceManager.getString( ProjectInfoPanel.class, "EndDateName" ) ), new CC( ).alignX( "trailing" ) );
        add( getProjectEndDateLabel( ), new CC( ).wrap( ) );
        add( new JLabel( ResourceManager.getString( ProjectInfoPanel.class, "BudgetName" ) ), new CC( ).alignX( "trailing" ).alignY( "top" ) );
        add( getProjectBudgetLabel( ), new CC( ).alignY( "top" ) );
    }

    /**
     * @param project
     *            the {@link #_project} to set.
     */
    public void setProject ( Project project )
    {
        _controller.setModel( project );
    }

    /**
     * @return the {@link #_projectNameLabel}.
     */
    public JLabel getProjectNameLabel ( )
    {
        if ( _projectNameLabel == null )
        {
            _projectNameLabel = new JLabel( );
        }
        return _projectNameLabel;
    }

    /**
     * @return the {@link #_projectDescriptionArea}.
     */
    public JEditorPane getProjectDescriptionArea ( )
    {
        if ( _projectDescriptionArea == null )
        {
            _projectDescriptionArea = new JEditorPane( );
            _projectDescriptionArea.setContentType( "text/html" );
            _projectDescriptionArea.setEditable( false );
        }
        return _projectDescriptionArea;
    }

    /**
     * @return the {@link #_projectBudgetLabel}.
     */
    public JLabel getProjectBudgetLabel ( )
    {
        if ( _projectBudgetLabel == null )
        {
            _projectBudgetLabel = new JLabel( );
        }
        return _projectBudgetLabel;
    }

    /**
     * @return the {@link #_projectEndDateLabel}.
     */
    public JLabel getProjectEndDateLabel ( )
    {
        if ( _projectEndDateLabel == null )
        {
            _projectEndDateLabel = new JLabel( );
        }
        return _projectEndDateLabel;
    }

    /**
     * @return the {@link #_projectStartDateLabel}.
     */
    public JLabel getProjectStartDateLabel ( )
    {
        if ( _projectStartDateLabel == null )
        {
            _projectStartDateLabel = new JLabel( );
        }
        return _projectStartDateLabel;
    }
}
