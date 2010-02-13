package org.promasi.shell.model.communication;


import org.promasi.model.Company;


/**
 * 
 * Used for resolving a data to a value of a model object and setting a value to
 * a model object.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IResolver
{

    /**
     * @param data
     *            The data define from which object and property we will get the
     *            value from. The data can be of any type depending the
     *            implementation of the resolver.
     * @param context
     *            The {@link Company} to use as a context in order to resolve
     *            values.
     * @return The value of a model class.
     */
    Double getValue ( Object data, Company context );

    /**
     * 
     * @param data
     *            The data define in which object and property we will set the
     *            value.The data can be of any type depending the implementation
     *            of the resolver.
     * @param context
     *            The {@link Company} to use as a context in order to resolve
     *            values.
     * @param value
     *            The value to set to a model class.
     */
    void setValue ( Object data, Company context, Object value );

    /**
     * Checks if the {@link IResolver} can handle the data.
     * 
     * @param data
     * @return True if the {@link IResolver} can handle the data, False
     *         otherwise.
     */
    boolean canHandleData ( Object data );
}
