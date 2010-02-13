package org.promasi.shell.playmodes.singleplayerscoremode.corebindings;


import java.util.Hashtable;
import java.util.Map;

import org.promasi.shell.model.actions.IModelAction;


/**
 * 
 * Defines which {@link IModelAction} will be called and all of its parameters.
 * 
 * @see EventBinding
 * @author eddiefullmetal
 * 
 */
public class ActionBinding
{

    /**
     * The full name of an {@link IModelAction}.
     */
    private String _actionClassName;

    /**
     * All the parameters that will be passed in the {@link IModelAction}. The
     * key of the map is the name of the parameter and the value of the map is
     * the value of the parameter.
     */
    private Map<String, String> _parameters;

    /**
     * Initializes the object.
     * 
     */
    public ActionBinding( )
    {
        _parameters = new Hashtable<String, String>( );
    }

    /**
     * @return the {@link #_actionClassName}.
     */
    public String getActionClassName ( )
    {
        return _actionClassName;
    }

    /**
     * @param actionClassName
     *            the {@link #_actionClassName} to set.
     */
    public void setActionClassName ( String actionClassName )
    {
        _actionClassName = actionClassName;
    }

    /**
     * @param name
     *            The key to add to the {@link #_actionClassName}.
     * @param value
     *            The value to add to the {@link #_parameters}.
     */
    public void addParameter ( String name, String value )
    {
        if ( !_parameters.containsKey( name ) )
        {
            _parameters.put( name, value );
        }
    }

    /**
     * @param name
     * @return The parameter with the specified name.
     */
    public String getParameter ( String name )
    {
        if ( _parameters.containsKey( name ) )
        {
            return _parameters.get( name );
        }
        return null;
    }

    /**
     * @return the {@link #_parameters}.
     */
    public Map<String, String> getParameters ( )
    {
        return _parameters;
    }

    /**
     * @param parameters
     *            the {@link #_parameters} to set.
     */
    public void setParameters ( Map<String, String> parameters )
    {
        _parameters = parameters;
    }

}
