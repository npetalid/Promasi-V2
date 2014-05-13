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
public class RemoveUtils {

	
	
	public List<SdModel> removeSdObject(String objectName ,  String parent , List<SdModel> models) {
		
		List<SdModel> results = new ArrayList<SdModel>();
		
		if (models != null) {
			for (SdModel model : models) {
				if (model.getName() != parent) {
					SdModel updatedModel = updateModels( objectName , model);
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
			if (!(models.size() == results.size())) {
				results = null;
			}
		}
		return results;
	}
	private SdModel updateModels(String objectName , SdModel sdModel) {
		SdModel results = null;
		
		if (sdModel != null) {
			List<SdObject> children = sdModel.getChildrenArray();
			
			if (children != null) {
				for (SdObject child : children) {
					if (child instanceof SdInput) {
						SdInput sdInput = (SdInput)child;
						List<SdModelConnection> connections = sdInput.getConnections();
						if (connections != null) {
							connections = removeConnection(objectName , connections);
							if (connections != null) {
								 sdInput.setConnections(connections);
								 children = updateSdInput(sdInput , children);
							
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
	private List<SdModelConnection> removeConnection(String name  , List<SdModelConnection> connections) {
		List<SdModelConnection> results = null;
		 
		 if ((connections != null) && (name != null)) {
			 
			 
			 results = new ArrayList<SdModelConnection>();
			 
			 
			 for (SdModelConnection connection :  connections) {
				 String objectName = connection.getObjectName();

				 if (!objectName.equals(name)) {
					 results.add(connection);	
		          }   
			 }
	
		 }
		 return results;
	}
	private List<SdObject> updateSdInput(SdInput sdInput , List<SdObject> sdObjects) {
		
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	public List<SdModel> updateModels(String modelName , List<SdModel> models) {
		
		
		java.util.List<SdModel> results = new ArrayList<SdModel>();
		
		if (models != null) {
			for (SdModel model : models) {
				if (model.getName() != modelName) {
					SdModel updatedModel = updateModel( modelName , model);
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
		}
		return results;
	}	
	private SdModel updateModel( String modelName , SdModel sdModel) {
		SdModel results = null;
		
		if (sdModel != null) {
			java.util.List<SdObject> children = sdModel.getChildrenArray();
			
			if (children != null) {
				for (SdObject child : children) {
					if (child instanceof SdInput) {
						SdInput sdInput = (SdInput)child;
		
						java.util.List<SdModelConnection> connections = sdInput.getConnections();
						if (connections != null) {
							connections = removeModelName( modelName , connections);
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
	
	private java.util.List<SdModelConnection> removeModelName( String modelName  , List<SdModelConnection> connections) {
		java.util.List<SdModelConnection> results = null;
		 
		 if ((connections != null) && (modelName != null)) {
			 
			 
			 
			 results = new ArrayList<SdModelConnection>();
			 for (SdModelConnection modelConnection : connections) {
				 
				 String objectName = modelConnection.getObjectName();
				 
		         String newModelName = modelConnection.getModelName();
		            
		            if (!newModelName.equals(modelName)) {
		        		
		            	SdModelConnection connection = new SdModelConnection();
		            	connection.setObjectName(objectName);
		            	connection.setModelName(newModelName);
		            	results.add(connection);
		            }
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
}
