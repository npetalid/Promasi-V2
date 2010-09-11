package org.promasi.core.sdobjects;


import org.promasi.core.SdObjectType;


/**
 * Implementation of the {@link AbstractSdObject} for the
 * {@link SdObjectType#Variable}.
 * 
 * @author eddiefullmetal
 * 
 */
public class VariableSdObject
        extends AbstractSdObject
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Initializes the object.
     * 
     * @param key
     *            The key of the {@link AbstractSdObject}
     */
    public VariableSdObject( String key )
    {
        super( key, SdObjectType.Variable );
    }

    /**
     * Initializes the object.
     */
    public VariableSdObject( )
    {
        setType( SdObjectType.Variable );
    }
}
