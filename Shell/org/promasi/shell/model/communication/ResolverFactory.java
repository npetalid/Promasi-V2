package org.promasi.shell.model.communication;


import java.util.List;
import java.util.Vector;

import org.promasi.model.Company;


/**
 * 
 * Returns the appropriate {@link IResolver} depending on the type of data.
 * 
 * @author eddiefullmetal
 * 
 */
public class ResolverFactory
{

    /**
     * All the registered {@link IResolver}s.
     */
    private static List<IResolver> REGISTERED_RESOLVERS;

    static
    {
        REGISTERED_RESOLVERS = new Vector<IResolver>( );
    }

    /**
     * @param data
     *            The data in order to determine which resolver to return.
     * @param context
     *            The {@link Company} to use as a context in order to resolve
     *            values.
     * @return The {@link IResolver} that can handle the type of the data, null
     *         if there is no registered resolver that can handle the data. If
     *         there are 2 registered resolvers that can handle the data the
     *         first {@link IResolver} that is found in the list will be
     *         returned.
     */
    private static IResolver getResolver ( Object data )
    {
        for ( IResolver resolver : REGISTERED_RESOLVERS )
        {
            if ( resolver.canHandleData( data ) )
            {
                return resolver;
            }
        }
        return null;
    }

    /**
     * @param resolver
     *            The {@link IResolver} to add to the
     *            {@link #REGISTERED_RESOLVERS}. If an {@link IResolver} with
     *            the same class as the resolver exists the resolver will not be
     *            added, with this behavior for example only 1
     *            {@link JXPathResolver} can be added to the list.
     */
    public static void registerResolver ( IResolver resolver )
    {
        boolean sameClassExists = false;
        for ( IResolver registeredResolver : REGISTERED_RESOLVERS )
        {
            if ( registeredResolver.getClass( ).getName( ).equals( resolver.getClass( ).getName( ) ) )
            {
                sameClassExists = true;
                break;
            }
        }

        if ( !sameClassExists )
        {
            REGISTERED_RESOLVERS.add( resolver );
        }
    }

    /**
     * 
     * It will get the value of a model object using the appropriate
     * {@link IResolver}.
     * 
     * @param data
     *            The data to get the value for.
     * @return The value.
     */
    public static Double getValue ( Object data, Company context )
    {
        IResolver resolver = getResolver( data );
        return resolver.getValue( data, context );
    }

    /**
     * 
     * It will set the value of a model object using the appropriate
     * {@link IResolver}.
     * 
     * @param data
     *            The data to get the value for.
     * @return The value.
     */
    public static void setValue ( Object data, Company context, Object value )
    {
        IResolver resolver = getResolver( data );
        resolver.setValue( data, context, value );
    }
}
