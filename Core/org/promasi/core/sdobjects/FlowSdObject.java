package org.promasi.core.sdobjects;


import org.promasi.core.SdObjectType;


/**
 * Implementation of the {@link AbstractSdObject} for the
 * {@link SdObjectType#Flow}.
 * 
 * @author eddiefullmetal
 * 
 */
public class FlowSdObject
        extends AbstractSdObject
{

    /**
     * Initializes the object.
     * 
     * @param key
     *            The key of the {@link AbstractSdObject}
     */
    public FlowSdObject( String key )
    {
        super( key, SdObjectType.Flow );
    }

    /**
     * Initializes the object.
     */
    public FlowSdObject( )
    {
        setType( SdObjectType.Flow );
    }

}
