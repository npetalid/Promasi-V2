package org.promasi.ui.promasiui.promasidesktop.programs.infogate;


import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.promasi.model.Company;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.ui.SwingCreator;


/**
 * A panel that shows information about the {@link Company}.
 *
 * @author eddiefullmetal
 *
 */
public class CompanyInfoPanel
        extends JPanel
{

    /**
     * A {@link JLabel} that displays the name of the company.
     */
    private JLabel _companyNameLabel;

    /**
     * A {@link JLabel} that displays the number of employees the company
     * currently has.
     */
    private JLabel _numberOfEmployeesLabel;

    /**
     * A {@link JEditorPane} that displays the description of the company.
     */
    private JEditorPane _companyDescription;

    /**
     * The controller for this panel. The controller will initialize the
     * bindings upon construction thats why its not used(keep it for later use).
     */
    @SuppressWarnings( "unused" )
    private CompanyInfoPanelController _controller;

    /**
     * Initializes the object.
     *
     */
    public CompanyInfoPanel(Shell shell )
    {
        _controller = new CompanyInfoPanelController( this,shell );
        initialize( );
    }

    /**
     * Initializes the ui.
     */
    private void initialize ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( getCompanyNameLabel( ), new CC( ).spanX( ).alignX( "leading" ).wrap( ) );
        add( new JLabel( ResourceManager.getString( CompanyInfoPanel.class, "numberOfEmployees" ) ), new CC( ).split( 2 ) );
        add( getNumberOfEmployeesLabel( ), new CC( ).wrap( ) );
        add( SwingCreator.createLabel( ResourceManager.getString( CompanyInfoPanel.class, "descriptionText" ), Font.BOLD ), new CC( ).wrap( ) );
        add( new JScrollPane( getCompanyDescription( ) ), new CC( ).grow( ).spanX( ) );
    }

    /**
     * @return the {@link #_numberOfEmployeesLabel}.
     */
    public JLabel getNumberOfEmployeesLabel ( )
    {
        if ( _numberOfEmployeesLabel == null )
        {
            _numberOfEmployeesLabel = new JLabel( );
        }
        return _numberOfEmployeesLabel;
    }

    /**
     * @return the {@link #_companyNameLabel}.
     */
    public JLabel getCompanyNameLabel ( )
    {
        if ( _companyNameLabel == null )
        {
            _companyNameLabel = SwingCreator.createLabel( StringUtils.EMPTY, 14, Font.BOLD );
        }
        return _companyNameLabel;
    }

    /**
     * @return the {@link #_companyDescription}.
     */
    public JEditorPane getCompanyDescription ( )
    {
        if ( _companyDescription == null )
        {
            _companyDescription = new JEditorPane( );
            _companyDescription.setContentType( "text/html" );
            _companyDescription.setEditable( false );
        }
        return _companyDescription;
    }
}
