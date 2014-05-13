package org.promasi.coredesigner.model.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.draw2d.geometry.Rectangle;

import org.promasi.coredesigner.model.AbstractSdObject;
import org.promasi.coredesigner.model.Dependency;
import org.promasi.coredesigner.model.NameValidator;
import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdConnection;
import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.SdOutput;
import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.model.SdStock;
import org.promasi.coredesigner.model.SdVariable;
import org.promasi.coredesigner.model.builder.model.XmlModel;
import org.promasi.coredesigner.model.builder.model.XmlObject;
import org.promasi.coredesigner.model.builder.model.XmlProject;
/**
 * 
 * @author antoxron
 *
 */
public class ModelParser {
	
	private SdProject _sdProject = null;
	
	public ModelParser() {
		
	}
	
	
	public void setProject(XmlProject xmlProject) {
		
		NameValidator.getInstance().setValidateNames(false);
		
		
		if (xmlProject != null) {
			
			List<XmlModel> xmlModels = xmlProject.getXmlModels();
			if (xmlModels != null) {
				List<SdModel> sdModels = buildSdModels(xmlModels);
				if (sdModels != null) {
					_sdProject = new SdProject();
					_sdProject.setName(  xmlProject.getName() );
					_sdProject.setModels(sdModels);
				}
				
				Map<String, List<String> > names = new HashMap<String, List<String> >();
				if (sdModels != null) {
					for (SdModel item : sdModels) {
						String modelName = item.getName();
						List<SdObject> children = item.getChildrenArray();
						List<String> objectNames = new ArrayList<String>();
					    if (objectNames != null) {
					    	for (SdObject child : children) {
					    		objectNames.add(child.getName());
					    	}
					    }
					    names.put(modelName, objectNames);
					    
					}
					NameValidator.getInstance().setNames(names);
					
				}
			}
			
			
			
		}
		NameValidator.getInstance().setValidateNames(true);
	}
	
	private List<SdModel> buildSdModels(List<XmlModel> xmlModels) {
		List<SdModel> results = null;
		
		if (xmlModels != null) {
			results = new ArrayList<SdModel>();
			
			
			for (XmlModel xmlModel : xmlModels) {
				SdModel sdModel = buildSdModel(xmlModel);
				if (sdModel != null) {
					results.add(sdModel);
				}
			}
		}
		
		
		return results;
	}
	
	
	private SdModel buildSdModel(XmlModel xmlModel) {
		SdModel results = null;
		
		if (xmlModel != null) {
			results = new SdModel();
			
		
			String name = xmlModel.getName();
			results.setName(name);
			
			List<XmlObject> xmlObjects = xmlModel.getChildren();
			List<SdObject> sdObjects = new ArrayList<SdObject>();
			
			if (xmlObjects != null) {
				
				for (XmlObject xmlObject : xmlObjects) {
					
					SdObject sdObject = null;
					    
					
					
						if (xmlObject.getType().equals(AbstractSdObject.INPUT_OBJECT)) {
							
							sdObject = new SdInput();
							
							List<Dependency> dependencies = xmlObject.getDependencies();
							String equation = xmlObject.getEquation();
							List<SdModelConnection> connections = xmlObject.getConnections();
							if (dependencies != null) {
								sdObject.setDependencies(dependencies);
							}
							if (equation != null) {
								((SdInput) sdObject).setEquation(equation);
							}
							if (connections != null) {
								((SdInput) sdObject).setConnections(connections);
							}
						}
						else if (xmlObject.getType().equals(AbstractSdObject.OUTPUT_OBJECT)) {
							
							sdObject = new SdOutput();
							
							List<Dependency> dependencies = xmlObject.getDependencies();
							String equation = xmlObject.getEquation();
							if (dependencies != null) {
								sdObject.setDependencies(dependencies);
							}
							if (equation != null) {
								((SdOutput) sdObject).setEquation(equation);
							}
						}
						else if (xmlObject.getType().equals(AbstractSdObject.VARIABLE_OBJECT)) {
							sdObject = new SdVariable();
							
							List<Dependency> dependencies = xmlObject.getDependencies();
							
							String equation = xmlObject.getEquation();
							
							if (dependencies != null) {
								sdObject.setDependencies(dependencies);
							}
							if (equation != null) {
								((SdVariable) sdObject).setEquation(equation);
							}
						}
						else if (xmlObject.getType().equals(AbstractSdObject.FLOW_OBJECT)) {
							sdObject = new SdFlow();
							
							List<Dependency> dependencies = xmlObject.getDependencies();
							
							String equation = xmlObject.getEquation();
							
							if (dependencies != null) {
								sdObject.setDependencies(dependencies);
							}
							if (equation != null) {
								((SdFlow) sdObject).setEquation(equation);
							}
						}
						else if (xmlObject.getType().equals(AbstractSdObject.STOCK_OBJECT)) {
							sdObject = new SdStock();

							
							List<Dependency> dependencies = xmlObject.getDependencies();
							String equation = xmlObject.getEquation();
							double initialValue =  xmlObject.getInitialValue() ;
							
							((SdStock) sdObject).setInitialValue(initialValue);
							
							if (dependencies != null) {
								sdObject.setDependencies(dependencies);
							}
							if (equation != null) {
								((SdStock) sdObject).setEquation(equation);
							}
							
						}
						else if (xmlObject.getType().equals(AbstractSdObject.LOOKUP_OBJECT)) {
							sdObject = new SdLookup();

							
							TreeMap<Double,Double> lookupPoints = xmlObject.getLookupPoints();
							
							if (lookupPoints != null) {
								((SdLookup) sdObject).setLookupPoints(lookupPoints);
							}
							
						}
						else if (xmlObject.getType().equals(AbstractSdObject.CALCULATE_OBJECT)) {
							sdObject = new SdCalculate();
							
							String calculateString = xmlObject.getCalculateString();
							
							if (calculateString != null) {
								((SdCalculate) sdObject).setCalculateString(calculateString);
							}
							
						}
						
						if (sdObject != null) {
							 String type = xmlObject.getType();
								name = xmlObject.getName();
								int width = xmlObject.getWidth();
								int height = xmlObject.getHeight();
								int x = xmlObject.getX();
								int y = xmlObject.getY();
								
								sdObject.setType(type);
								sdObject.setName(name);
								sdObject.setLayout(new Rectangle( x , y , width , height));
								sdObject.setParent(results);
								sdObjects.add(sdObject);
						}
				}//end for
				
				
				///create connections between objects in task
				List<SdObject> connectedObjects = new ArrayList<SdObject>();

				for (SdObject item : sdObjects)  {
					
					List<Dependency> dependencies = item.getDependencies();
					
						if (dependencies != null) {
							
							if (dependencies.size() > 0) {

								for (Dependency dependency : dependencies) {
									
									SdObject sdObject  = findObject(dependency.getName() , sdObjects);
									SdConnection connection = new SdConnection(sdObject , item);
									
									if (sdObject != null) {
										sdObject.addConnection(connection);
										item.addConnection(connection);
										
										if (! connectedObjects.contains(sdObject)) {
											connectedObjects.add(sdObject);
										}
										if (! connectedObjects.contains(item)) {
											connectedObjects.add(item);							
										}
									}
										
								}
							}
							else {
								if (! connectedObjects.contains(item)) {
									connectedObjects.add(item);							
								}
							}
						}
						else {
							connectedObjects.add(item);
						}
				}
				results.setChildren( connectedObjects  );
			}//end if
			
		}
	
		
		return results;
	}
	private  SdObject findObject(String name,List<SdObject> sdObjects){
		
		SdObject object = null;
		
		if (name != null) {
			for (SdObject item : sdObjects) {
				if (item != null) {
					
					if (item.getName() != null) {
						if (item.getName().equals(name)) {
							
							object = item;
						}
					}	
				}		
			}
		}
		
		return object;
	}
	public SdProject getProject() {
		return _sdProject;
	}
}
