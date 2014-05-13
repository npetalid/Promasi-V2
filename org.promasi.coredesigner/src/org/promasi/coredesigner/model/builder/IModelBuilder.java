package org.promasi.coredesigner.model.builder;

import org.promasi.coredesigner.model.SdProject;

/**
 * 
 * @author antoxron
 *
 */
public interface IModelBuilder {
	
	public void setModel( SdProject sdProject );
	public Object getModel();
	
}
