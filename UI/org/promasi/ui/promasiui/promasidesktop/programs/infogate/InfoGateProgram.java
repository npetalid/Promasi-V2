package org.promasi.ui.promasiui.promasidesktop.programs.infogate;


import javax.swing.Icon;
import javax.swing.JTabbedPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.game.IGame;
import org.promasi.model.Company;
import org.promasi.model.Project;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.programs.AbstractProgram;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 *
 * Program for showing info about various parts of the company(current
 * project,company info etc).
 *
 * @author eddiefullmetal
 *
 */
public class InfoGateProgram extends AbstractProgram
{

    /**
     * The {@link ProjectInfoPanel} that shows information about the current
     * {@link Project}.
     */
    private ProjectInfoPanel _projectInfoPanel;

    /**
     * The {@link CompanyInfoPanel} that shows information about the
     * {@link Company}.
     */
    private CompanyInfoPanel _companyInfoPanel;

    /**
     * The {@link EmployeesInfoPanel} that shows information about the hired
     * employees.
     */
    private EmployeesInfoPanel _employeesInfoPanel;

    /**
     * The tabbed pane used to seperate the panels.
     */
    private JTabbedPane _tabbedPane;

    /**
     * 
     */
    private IGame _game;

    /**
     * Initializes the object.
     */
    public InfoGateProgram(IGame game )throws NullArgumentException
    {
        super( "infoGate", "InfoGate, view information" );
        if(game==null)
        {
        	throw new NullArgumentException("Wrong argument shell==null");
        }
        _game=game;
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the layout.
     */
    private void initializeComponents ( )
    {
        _projectInfoPanel = new ProjectInfoPanel( );
        _companyInfoPanel = new CompanyInfoPanel(_game );
        //_employeesInfoPanel = new EmployeesInfoPanel(_game );

        _tabbedPane = new JTabbedPane( );
        _tabbedPane.addTab( ResourceManager.getString( InfoGateProgram.class, "companyInfo" ), _companyInfoPanel );
        _tabbedPane.addTab( ResourceManager.getString( InfoGateProgram.class, "projectInfo" ), _projectInfoPanel );
        _tabbedPane.addTab( ResourceManager.getString( InfoGateProgram.class, "employeeInfo" ), _employeesInfoPanel );
    }

    /**
     * Initializes the components.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _tabbedPane, new CC( ).grow( ) );

    }

    @Override
    public void closed ( )
    {

    }

    @Override
    public Icon getIcon ( )
    {
        return ResourceManager.getIcon( getName( ) );
    }

    @Override
    public void opened ( )
    {
       /*Project currentProject = _shell.getCurrentProject( );
        if ( currentProject != null )
        {
            _tabbedPane.setEnabledAt( 1, true );
        }
        else
        {
            _tabbedPane.setEnabledAt( 1, false );
            _tabbedPane.setSelectedIndex( 0 );
        }

        _projectInfoPanel.setProject( currentProject );*/
    }
}
