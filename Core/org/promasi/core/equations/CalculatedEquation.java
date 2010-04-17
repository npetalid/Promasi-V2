package org.promasi.core.equations;


import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.SymbolTable;
import org.nfunk.jep.Variable;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.core.utilities.jep.JepInitializer;
import org.promasi.utilities.ErrorBuilder;


/**
 *
 * The calculated equation takes a string that represents the equation for
 * example : <code>x*y</code> where <i>x</i> and <i>y</i> are the keys of the
 * {@link ISdObject} that exist as a dependency in the {@link #_context}.
 * Generally the {@link #_equationString} can take {@link JEP} expressions. The
 * class follows the javabeans specification.
 *
 * @author eddiefullmetal
 *
 */
public class CalculatedEquation
        implements IEquation, Serializable
{
    /**
     * The {@link JEP} expression.
     */
    private String _equationString;

    /**
     * The {@link ISdObject} that is used to resolve the variable names by
     * checking its dependencies it's actually the {@link ISdObject} that uses
     * this {@link IEquation}.
     */
    private ISdObject _context;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( CalculatedEquation.class );

    /**
     * Initializes the object.
     *
     * @param equationString
     *            The {@link #_equationString}.
     *
     * @param context
     *            The {@link #_context}.
     * @throws NullArgumentException
     *             In case the context is null.
     * @throws IllegalArgumentException
     *             In case the equationString is null or empty.
     */
    public CalculatedEquation( ISdObject context, String equationString )
            throws NullArgumentException, IllegalArgumentException
    {
        // Check arguments
        if ( context == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "CalculatedEquation", "context" ) );
            throw new NullArgumentException( "context" );
        }
        if ( equationString == null || equationString.isEmpty( ) )
        {
            String message = ErrorBuilder.generateNullOrEmptyArgumentError( "CalculatedEquation", "equationString" );
            LOGGER.error( message );
            throw new IllegalArgumentException( message );
        }
        _context = context;
        _equationString = equationString;
    }

    /**
     * Initializes the object.
     */
    public CalculatedEquation( )
    {

    }

    /**
     * Calculates the value of the equation.
     *
     * @return The value of the equation.
     * @throws ParseException
     *             In case the {@link JEP} fails to parse the
     *             {@link #_equationString} or a dependency is not found
     */
    @Override
    public Double calculateValue ( )
            throws ParseException
    {
        // initialize the JEP object and parse the expression.
        // TODO we don't need to parse the expression every time the equation is
        // calculated just parse it when the equation string is assigned.
        JEP jep = JepInitializer.getFullJep( );
        Node topNode = jep.parseExpression( _equationString );
        // If the topNode is null it means that JEP failed to parse the
        // expression.
        if ( topNode != null )
        {
            // Get a list with all the undeclared variables. Those variable are
            // one of the dependency objects of the _context.
            SymbolTable symTable = jep.getSymbolTable( );
            Collection coll = symTable.values( );
            // Get the value of each variable. The Computational sequence
            // ensures that the value will be up to date.
            for ( Object object : coll )
            {
                Variable var = (Variable) object;
                // Make sure that the var is not a JEP constant.
                if ( !var.isConstant( ) )
                {
                    ISdObject sdObject = getSdObjectFromContext( var.getName( ) );
                    if ( sdObject != null )
                    {
                        Double value = sdObject.getValue( );
                        if ( value == null )
                        {
                            value = 0.0;
                        }
                        jep.setVarValue( var.getName( ), value );
                    }
                    else
                    {
                        // A dependency is not found so the equationString
                        // cannot be
                        // calculated.
                        String message = "Unsatisfied dependency: " + var.getName( );
                        LOGGER.error( message );
                        throw new ParseException( message );
                    }
                }
            }
            return jep.getValue( );
        }
        else
        {
            String message = "JEP failed to parse equationString: " + _equationString;
            LOGGER.error( message );
            throw new ParseException( message );
        }
    }

    @Override
    public EquationType getType ( )
    {
        return EquationType.Calculated;
    }

    /**
     *
     * Gets the {@link ISdObject} from the {@link ISdObject#getDependencies()}
     * of the {@link #_context} and returns the {@link ISdObject} that has the
     * same key.
     *
     * @param key
     *            The key to perform the search.
     * @return The {@link ISdObject} from the
     *         {@link ISdObject#getDependencies()} of the {@link #_context} that
     *         corresponds to the key or null if the object is not found.
     * @throws IllegalArgumentException
     *             In case the key is null or empty.
     */
    private ISdObject getSdObjectFromContext ( String key )
            throws IllegalArgumentException
    {
        // Check arguments
        if ( key == null || key.isEmpty( ) )
        {
            String message = ErrorBuilder.generateNullOrEmptyArgumentError( "getSdObjectFromContext", "key" );
            LOGGER.error( message );
            throw new IllegalArgumentException( message );
        }
        // Search for the ISdObject that corresponds to the key.
        for ( ISdObject dependency : _context.getDependencies( ) )
        {
            if ( dependency.getKey( ).equals( key ) )
            {
                return dependency;
            }
        }
        if ( key.equals( _context.getKey( ) ) )
        {
            return _context;
        }

        return null;
    }

    /**
     * @return The {@link #_context}
     */
    public ISdObject getContext ( )
    {
        return _context;
    }

    /**
     * @return The {@link #_equationString} to set.
     */
    public String getEquationString ( )
    {
        return _equationString;
    }

    /**
     * @param context
     *            The {@link #_context} to set.
     */
    public void setContext ( ISdObject context )
    {
        _context = context;
    }

    /**
     * @param equationString
     *            The {@link #_equationString} to set.
     */
    public void setEquationString ( String equationString )
    {
        _equationString = equationString;
    }

	@Override
	public void registerCommunicator(ICommunicator communicator) {

	}
}
