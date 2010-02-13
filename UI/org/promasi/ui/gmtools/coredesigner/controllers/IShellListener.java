package org.promasi.ui.gmtools.coredesigner.controllers;


import org.promasi.ui.gmtools.coredesigner.model.SdModelInfo;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * Interface that gets notified when the shell does an action.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IShellListener
{

    /**
     * Is thrown when a new model is created.
     * 
     * @param sdModelInfo
     *            The info of the newly created model.
     */
    void modelCreated ( SdModelInfo sdModelInfo );

    /**
     * Is thrown when an {@link SdObjectDecorator} is removed.
     * 
     * @param obj
     *            The {@link SdObjectDecorator} that has been removed.
     * @param modelInfo
     *            The {@link SdModelInfo} that had the obj.
     */
    void objectRemoved ( SdModelInfo modelInfo, SdObjectDecorator obj );
}
