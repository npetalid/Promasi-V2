package org.promasi.ui.promasiui.promasidesktop.programs.infogate;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.promasi.model.Company;
import org.promasi.shell.Shell;
import org.promasi.utilities.beansbinding.BindingSupport;


/**
 * 
 * Controller for the {@link CompanyInfoPanel}.
 * 
 * @author eddiefullmetal
 * 
 */
public class CompanyInfoPanelController
        implements PropertyChangeListener
{

    /**
     * The model class.
     */
    private Company _model;

    /**
     * The UI class.
     */
    private CompanyInfoPanel _ui;

    /**
     * The {@link BindingSupport}.
     */
    private BindingSupport _bindingSupport;

    /**
     * Initializes the object.
     * 
     */
    public CompanyInfoPanelController( CompanyInfoPanel ui )
    {
        _bindingSupport = new BindingSupport( );
        _ui = ui;
        _model = Shell.getInstance( ).getCompany( );
        _model.addPropertyChangeListener( this );
        initializeBindings( );
    }

    /**
     * Initializes the bindings between the UI and the model.
     */
    private void initializeBindings ( )
    {
        _bindingSupport.createOneWayBinding( _model, Company.NAME_PROPERTY, _ui.getCompanyNameLabel( ), "text" );
        _bindingSupport.createOneWayBinding( _model, Company.DESCRIPTION_PROPERTY, _ui.getCompanyDescription( ), "text" );
        _ui.getNumberOfEmployeesLabel( ).setText( String.valueOf( _model.getEmployees( ).size( ) ) );

    }

    @Override
    public void propertyChange ( PropertyChangeEvent evt )
    {
        if ( evt.getPropertyName( ).equals( Company.EMPLOYEES_PROPERTY ) )
        {
            _ui.getNumberOfEmployeesLabel( ).setText( String.valueOf( _model.getEmployees( ).size( ) ) );
        }
    }
}
