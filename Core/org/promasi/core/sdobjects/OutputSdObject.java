package org.promasi.core.sdobjects;


import org.promasi.communication.Communicator;
import org.promasi.core.SdObjectType;


/**
 * Implementation of the {@link AbstractSdObject} for the
 * {@link SdObjectType#Output}.
 *
 * @author eddiefullmetal
 *
 */
public class OutputSdObject
        extends AbstractSdObject
{
    /**
     * Initializes the object.
     *
     * @param key
     *            The key of the {@link AbstractSdObject}
     */
    public OutputSdObject( String key )
    {
        super( key, SdObjectType.Output );
    }

    /**
     * Initializes the object.
     */
    public OutputSdObject( )
    {
        setType( SdObjectType.Output );
    }

    /**
     * calculates the value of the object. This method is called in every step
     * of the system. <b>If calculateValue of the {@link AbstractSdObject} fails
     * the value of the object will not get updated!!!</b>. The
     * {@link OutputSdObject} will also notify the {@link Communicator} about
     * its value.
     */
    @Override
    public void calculateValue ( )
    {
        super.calculateValue( );
        synchronized(this)
        {
        	if(_communicator!=null)
        	{
        		_communicator.sendValue( getKey( ), getValue( ) );
        	}
        }
    }

}
