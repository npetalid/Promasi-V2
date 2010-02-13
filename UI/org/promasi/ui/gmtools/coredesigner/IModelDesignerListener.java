package org.promasi.ui.gmtools.coredesigner;

import org.promasi.ui.gmtools.coredesigner.gef.AbstractSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.gef.ModelDesignerPanel;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;




/**
 * Listens to events of the {@link ModelDesignerPanel}.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IModelDesignerListener
{

    /**
     * This is thrown when a {@link AbstractSdObjectFig} is selected from the
     * {@link ModelDesignerPanel}.
     * 
     * @param sdObject
     *            The {@link SdObjectDecorator} that was selected. Null
     *            if no {@link SdObjectDecorator} is selected.
     */
    void sdObjectSelected ( SdObjectDecorator sdObject );

}
