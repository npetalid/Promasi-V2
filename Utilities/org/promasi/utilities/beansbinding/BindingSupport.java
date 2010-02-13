package org.promasi.utilities.beansbinding;


import java.util.List;
import java.util.Vector;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.Property;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;


/**
 * 
 * Provides easy to use methods for creating bindings and holding references to
 * them.
 * 
 * @author eddiefullmetal
 * 
 */
public class BindingSupport
{

    /**
     * The {@link BindingGroup} that holds all bindings.
     */
    private BindingGroup _group;

    /**
     * Initializes the object.
     * 
     */
    public BindingSupport( )
    {
        _group = new BindingGroup( );
    }

    /**
     * Creates a {@link Binding} with the {@link UpdateStrategy#READ}
     * 
     * @param sourceObject
     *            The object to take the sourcePropertyName from.
     * @param sourcePropertyName
     *            The name of the property to read.
     * @param targetObject
     *            The target object
     * @param targetPropertyName
     *            The property name of the targetObject that the sourceProperty
     *            will go to.
     */
    public void createOneWayBinding ( Object sourceObject, String sourcePropertyName, Object targetObject, String targetPropertyName )
    {
        createOneWayBinding( sourceObject, sourcePropertyName, targetObject, targetPropertyName, null );
    }

    /**
     * Creates a {@link Binding} with the {@link UpdateStrategy#READ}
     * 
     * @param sourceObject
     *            The object to take the sourcePropertyName from.
     * @param sourcePropertyName
     *            The name of the property to read.
     * @param targetObject
     *            The target object
     * @param targetPropertyName
     *            The property name of the targetObject that the sourceProperty
     *            will go to.
     * @param converter
     *            The {@link Converter} to use.
     */
    @SuppressWarnings( "unchecked" )
    public void createOneWayBinding ( Object sourceObject, String sourcePropertyName, Object targetObject, String targetPropertyName,
            Converter converter )
    {
        Property sourceProperty = BeanProperty.create( sourcePropertyName );
        Property targetProperty = BeanProperty.create( targetPropertyName );
        Binding binding = Bindings.createAutoBinding( UpdateStrategy.READ, sourceObject, sourceProperty, targetObject, targetProperty );
        binding.setConverter( converter );
        binding.bind( );
        _group.addBinding( binding );
    }

    /**
     * Removes all bindings that this binding support has.
     */
    public void removeAllBindings ( )
    {
        List<Binding> bindings = new Vector<Binding>( _group.getBindings( ) );
        for ( Binding binding : bindings )
        {
            binding.unbind( );
            _group.removeBinding( binding );
        }
    }
}
