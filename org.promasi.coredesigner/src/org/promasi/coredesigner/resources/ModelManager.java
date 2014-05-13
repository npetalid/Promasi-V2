package org.promasi.coredesigner.resources;

import java.util.List;

import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdProject;
/**
 * 
 * @author antoxron
 *
 */
public class ModelManager {

	
	private static ModelManager _instance = null;
	private SdProject _sdProject;	
	
	public  static ModelManager getInstance( ) {
		
		if ( _instance == null ) {
			_instance = new ModelManager();
		}
		return _instance;
	}
	
	public void setSdProject( SdProject sdProject ) {
		_sdProject = sdProject;
	}
	public SdProject getSdProject( ) {
		return  _sdProject;
	}
	
	public void setSdModels( List<SdModel> sdModels ) {
		if ( _sdProject != null ) {
			_sdProject.setModels( sdModels );
		}
	}
	public List<SdModel> getSdModels( ) {
		List<SdModel> results = null;
		
		if ( _sdProject != null ) {
			results = _sdProject.getModels();
		}
		return results;
	}	
}