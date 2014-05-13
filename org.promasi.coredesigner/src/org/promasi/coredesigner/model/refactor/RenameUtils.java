package org.promasi.coredesigner.model.refactor;

import java.util.ArrayList;
import java.util.List;

import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.SdObject;
/**
 * 
 * @author antoxron
 *
 */
public class RenameUtils {
	
	
	public List<SdModel> renameSdObject(String oldName , String currentName ,  String parent , List<SdModel> models) {
		
		List<SdModel> results = null;
		
		
		if (models != null) {
			results = new ArrayList<SdModel>();
			for (SdModel model : models) {
				if (model.getName() != parent) {
					SdModel updatedModel = updateObjects(oldName , currentName , model);
					if (updatedModel != null) {
						results.add(updatedModel);
					}
					else {
						results.add(model);
					}
				}
				else {
					results.add(model);
				}
			}
			if (  !(models.size() == results.size()) ) {
				results = null;
			}
		}
		return results;
	}
	private SdModel updateObjects(String oldName , String currentName , SdModel sdModel) {
		SdModel results = null;
		
		if (sdModel != null) {
			List<SdObject> children = sdModel.getChildrenArray();
			
			if (children != null) {
				for (SdObject child : children) {
					if (child instanceof SdInput) {
						SdInput sdInput = (SdInput)child;
						List<SdModelConnection> connections = sdInput.getConnections();
						if (connections != null) {
							connections = replaceObjectName(oldName , currentName , connections);
							if (connections != null) {
								 sdInput.setConnections(connections);
								 children = replaceSdInput(sdInput , children);
							
							}
						}
						
					}
				}
				if (children != null) {
					sdModel.setChildren(children);
				}
				results = sdModel;
			}
		}
		return results;
	}
	private List<SdObject> replaceSdInput(SdInput sdInput , List<SdObject> sdObjects) {
		
		List<SdObject> results = null;
		
		if ((sdInput != null) && (sdObjects != null)) {
			results = new ArrayList<SdObject>();
			for (SdObject sdObject :sdObjects) {
				
				if (sdObject.getName().equals(sdInput.getName())) {
					results.add(sdInput);
				}
				else {
					results.add(sdObject);
				}
			}
			if (sdObjects.size() != results.size()) {
				results = null;
			}
		}
		return results;
	}
	private List<SdModelConnection> replaceObjectName(String oldName , String currentName  , List<SdModelConnection> connections) {
		List<SdModelConnection> results = null;
		 
		 if ((connections != null) && (currentName != null)) {
			 
			 
			 results = new ArrayList<SdModelConnection>();
			 
			 
			 for (SdModelConnection connection :  connections) {
				 String objectName = connection.getObjectName();
				 String modelName = connection.getModelName();
				 
				 
				 if (objectName.equals(oldName)) {
					 objectName = currentName;
		            	
		            	SdModelConnection modelConnection = new SdModelConnection();
		            	modelConnection.setModelName(modelName);
		            	modelConnection.setObjectName(objectName);
		            	results.add(modelConnection);
		            	
		            }
		            else {
		            	results.add(connection);
		            }
			 }
		 }
		 
		 return results;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	public List<SdModel> renameSdModel(String oldName , String currentName , List<SdModel> models) {
		
		
		List<SdModel> results = new ArrayList<SdModel>();
		
		if (models != null) {
			for (SdModel model : models) {
				if (model.getName() != oldName) {
					SdModel updatedModel = renameModel(oldName , currentName , model);
					if (updatedModel != null) {
						results.add(updatedModel);
					}
					else {
						results.add(model);
					}
				}
				else {
					results.add(model);
				}
			}
			
			
			if (  !(models.size() == results.size())) {
				results = null;
			}
		}
		return results;
	}
	
	private SdModel renameModel(String oldName , String currentName , SdModel sdModel) {
		SdModel results = null;
		
		if (sdModel != null) {
			List<SdObject> children = sdModel.getChildrenArray();
			
			if (children != null) {
				for (SdObject child : children) {
					if (child instanceof SdInput) {
						SdInput sdInput = (SdInput)child;

						List<SdModelConnection> connections = sdInput.getConnections();
						if (connections != null) {
							connections = replaceModelName(oldName , currentName , connections);
							if (connections != null) {
								 sdInput.setConnections(connections);
								 children = replaceSdInput(sdInput , children);
							}
						}
					}
				}
				if (children != null) {
					sdModel.setChildren(children);
				}
				results = sdModel;
			}
		}
		return results;
	}
	
	private List<SdModelConnection> replaceModelName(String oldName , String currentName  , List<SdModelConnection> connections) {
		List<SdModelConnection> results = null;
		 
		
		 if ((connections != null) && (currentName != null)) {
			 


			 results = new ArrayList<SdModelConnection>();
			 
			 for (SdModelConnection connection : connections) {
				 String objectName = connection.getObjectName();
				 String modelName = connection.getModelName();
				 
				 if (modelName.equals(oldName)) {
					 modelName = currentName;
					 
					 SdModelConnection modelConnection = new SdModelConnection();
					 modelConnection.setModelName(modelName);
					 modelConnection.setObjectName(objectName);
		            	results.add(modelConnection);
		            }
		            else {
		            	results.add(connection);
		            }
			 }
			 
			 
		 }
		 
		 return results;
	}
}
