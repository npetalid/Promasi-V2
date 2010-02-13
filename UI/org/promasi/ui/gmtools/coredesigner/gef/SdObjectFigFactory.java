package org.promasi.ui.gmtools.coredesigner.gef;


import org.promasi.core.ISdObject;
import org.promasi.core.SdObjectType;
import org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs.FlowSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs.OutputSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs.StockSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs.VariableSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * 
 * Factory for creating {@link AbstractSdObjectFig} depending on the
 * {@link ISdObject} type.
 * 
 * @author eddiefullmetal
 * 
 */
public final class SdObjectFigFactory
{

    /**
     * Initializes the object.
     */
    private SdObjectFigFactory( )
    {

    }

    /**
     * Gets the appropriate {@link AbstractSdObjectFig} for the sdObject.
     * 
     * @param sdObject
     *            The {@link ISdObject} to get the {@link AbstractSdObjectFig}
     *            for.
     * @return The {@link AbstractSdObjectFig} corresponding to the
     *         {@link SdObjectType} of the sdObject or null if an implementation
     *         of the {@link AbstractSdObjectFig} is not known for the
     *         {@link SdObjectType}.
     */
    public static AbstractSdObjectFig getSdObjectPanel ( SdObjectDecorator sdObject )
    {
        switch ( sdObject.getType( ) )
        {
            case Flow :
                return new FlowSdObjectFig( sdObject );
            case Output :
                return new OutputSdObjectFig( sdObject );
            case Stock :
                return new StockSdObjectFig( sdObject );
            case Variable :
                return new VariableSdObjectFig( sdObject );
            case System :
            default :
                return null;
        }
    }

}
