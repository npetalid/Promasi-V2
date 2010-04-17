package org.promasi.core.equations;


import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.utilities.ErrorBuilder;


/**
 *
 * The external equation takes its value from the upper layer using the
 * {@link Communicator}. The class follows the javabeans specification.
 *
 * @author eddiefullmetal
 *
 */
public class ExternalEquation
        implements IEquation
{

    /**
     * The {@link ISdObject} that the value will be requested for.
     */
    private ISdObject _context;

    /**
     * System communicator
     */
    private ICommunicator _communicator;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ExternalEquation.class );

    /**
     *
     * Initializes the object.
     *
     * @param context
     *            The {@link #_context}
     * @throws NullArgumentException
     *             In case the context is null.
     */
    public ExternalEquation( ISdObject context )
            throws NullArgumentException
    {
        // Check arguments
        if ( context == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "ExternalEquation", "context" ) );
            throw new NullArgumentException( "context" );
        }

        _context = context;
    }

    /**
     * Initializes the object.
     */
    public ExternalEquation( )
    {

    }

    @Override
    public Double calculateValue ( )
            throws NullPointerException
    {
        if ( _context == null )
        {
            LOGGER.error( "Context is not defined" );
            throw new NullPointerException( "Context is not defined" );
        }

        synchronized(this)
        {
        	if(_communicator!=null)
        	{
        		return _communicator.requestValue( _context.getKey( ));
        	}
        }
        return 0.0;
    }

    @Override
    public EquationType getType ( )
    {
        return EquationType.External;
    }

    /**
     * @return The {@link #_context}.
     */
    public ISdObject getContext ( )
    {
        return _context;
    }

    /**
     * @param context
     *            The {@link #_context} to set.
     */
    public void setContext ( ISdObject context )
    {
        _context = context;
    }

	@Override
	public void registerCommunicator(ICommunicator communicator) {
    	synchronized(this)
    	{
    		_communicator=communicator;
    	}
	}
}
