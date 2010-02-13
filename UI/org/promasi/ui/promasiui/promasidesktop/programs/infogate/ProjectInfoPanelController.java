package org.promasi.ui.promasiui.promasidesktop.programs.infogate;


import org.jdesktop.beansbinding.Converter;
import org.joda.time.DateTime;
import org.promasi.model.Project;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.beansbinding.BindingSupport;


/**
 * @author eddiefullmetal
 * 
 */
public class ProjectInfoPanelController
{

    /**
     * The UI class.
     */
    private ProjectInfoPanel _ui;

    /**
     * The model class.
     */
    private Project _model;

    /**
     * The {@link BindingSupport}.
     */
    private BindingSupport _bindingSupport;

    /**
     * The converter to use.
     */
    private DateTimeConverter _dateTimeConverter;

    /**
     * Initializes the object.
     * 
     */
    public ProjectInfoPanelController( ProjectInfoPanel ui )
    {
        _dateTimeConverter = new DateTimeConverter( );
        _bindingSupport = new BindingSupport( );
        _ui = ui;
    }

    /**
     * @param model
     *            the {@link #_model} to set.
     */
    public void setModel ( Project model )
    {
        if ( model != null )
        {
            _model = model;
        }
        else
        {
            _model = new Project( );
        }
        initializeBindings( );
    }

    /**
     * Initializes the bindings between the UI and the model.
     */
    private void initializeBindings ( )
    {
        // Remove previous bindings if any.
        _bindingSupport.removeAllBindings( );
        // Initialize all bindings.
        _bindingSupport.createOneWayBinding( _model, Project.NAME_PROPERTY, _ui.getProjectNameLabel( ), "text" );
        _bindingSupport.createOneWayBinding( _model, Project.DESCRIPTION_PROPERTY, _ui.getProjectDescriptionArea( ), "text" );
        _bindingSupport.createOneWayBinding( _model, Project.BUDGET_PROPERTY, _ui.getProjectBudgetLabel( ), "text" );
        _bindingSupport.createOneWayBinding( _model, Project.END_DATE_PROPERTY, _ui.getProjectEndDateLabel( ), "text", _dateTimeConverter );
        _bindingSupport.createOneWayBinding( _model, Project.START_DATE_PROPERTY, _ui.getProjectStartDateLabel( ), "text", _dateTimeConverter );
    }

    /**
     * 
     * Converts a {@link DateTime} to a string using the {@link ResourceManager}
     * .
     * 
     * @author eddiefullmetal
     * 
     */
    private class DateTimeConverter
            extends Converter<DateTime, String>
    {

        @Override
        public DateTime convertReverse ( String value )
        {
            return new DateTime( value );
        }

        @Override
        public String convertForward ( DateTime value )
        {
            return ResourceManager.formatDateOnly( value );
        }

    }
}
