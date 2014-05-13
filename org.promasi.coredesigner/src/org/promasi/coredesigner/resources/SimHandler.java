package org.promasi.coredesigner.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.promasi.coredesigner.model.AbstractSdObject;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.builder.model.XmlBridge;
import org.promasi.coredesigner.model.builder.model.XmlModel;
import org.promasi.coredesigner.model.builder.model.XmlObject;
import org.promasi.coredesigner.model.builder.model.XmlProject;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableNode;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableSubNode;
import org.promasi.sdsystem.ISdSystem;
import org.promasi.sdsystem.SdSystem;
import org.promasi.sdsystem.SdSystemBridge;
import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.sdobject.FlowSdObject;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;
import org.promasi.utilities.equation.CalculatedEquation;
import org.promasi.utilities.equation.CalculationExeption;
import org.promasi.utilities.equation.IEquation;

public class SimHandler {
	
	
	private List<XmlBridge> _xmlBridgeList = new ArrayList<XmlBridge>();
	private Map<String, ISdSystem> _sdSystems;
	
	
	public void simulate(XmlProject xmlProject, int steps) {
		
		List<SdSystemBridge> bridges = buildBridges(xmlProject);
		if ((bridges != null) && (_sdSystems != null)) {
			
			
			
			for (int i = 0;i < steps; i++) {
				for (SdSystemBridge bridge : bridges) {
					bridge.executeStep();
					
				}
				
				
				for (Entry<String, ISdSystem> entry : _sdSystems.entrySet()) {
				   // String key = entry.getKey();
				    ISdSystem value = entry.getValue();
				    if (value != null) {
				    	value.executeStep();
				    }
				   
				    
				}
			}
			
			
		}
	}
	

	private String findObjectValue(String objectName, ISdSystem sdSystem) {
		String results = "";
		
		if ((objectName != null) && (sdSystem != null)) {
			
			Map<String,Double> values = sdSystem.getSystemValues();
			
			
			for (Entry<String, Double> objectEntry : values.entrySet()) {
			    String key = objectEntry.getKey();
			    Double value = objectEntry.getValue();

			    if (key.equals(objectName)) {
			    	results = String.valueOf(value);
			    }
			    
			}
			
		}
		return results;
	}
	
	private String findModelValues(String modelName, String objectName) {
		String results = "";
		
		if ((modelName != null) && (objectName != null)) {
			
			for (Entry<String, ISdSystem> entry : _sdSystems.entrySet()) {
			    String key = entry.getKey();
			    ISdSystem sdSystem = entry.getValue();
			    
			    if (key.equals(modelName)) {
			    	
			    	if (sdSystem != null) {
			    		
			    		results = findObjectValue( objectName, sdSystem);
			    		
			    	}
			    
			    	
			    }
			    
			}
			
		}
		
		
		
		return results;
	}
	
	
	public List<TreeTableNode> populateValues(List<TreeTableNode> nodes) {
		
		
		
		if (nodes != null) {
			
			
		
			for (TreeTableNode treeNode : nodes) {
				String nodeName = treeNode.getName();
				List<TreeTableSubNode> subNodes = treeNode.getSdObjects();
				
				if (subNodes != null) {
					for (TreeTableSubNode subNode : subNodes) {
						String subNodeName = subNode.getName();
						String value = findModelValues(nodeName, subNodeName);
						subNode.setValue(value);
					}
				}
				
			}
		
		}
		
		
		
		return nodes;
		
	}

	private  List<SdSystemBridge> buildBridges(XmlProject xmlProject) {
	
	_sdSystems = getSdSystems(xmlProject);
	List<SdSystemBridge> connections = new ArrayList<SdSystemBridge>();
	
	
		for (XmlBridge item : _xmlBridgeList) {
		
			String inputObject = item.getInputObjectName();
			String inputModel = item.getInputModelName();
		
			String outputObject = item.getOutputObjectName();
			String outputModel = item.getOutputModelName();
		
			SdSystem inputSystem = (SdSystem) findSdSystem(inputModel, _sdSystems);
			SdSystem outputSystem = (SdSystem) findSdSystem(outputModel, _sdSystems);
		
			try {
				SdSystemBridge systemBridge = new SdSystemBridge(outputObject, outputSystem, inputObject, inputSystem);
				connections.add(systemBridge);
			} catch (SdSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		}
		return connections;
	}



	private Map<String, ISdSystem> getSdSystems(XmlProject xmlProject) {
		Map<String, ISdSystem> results = null;
	
		if (xmlProject != null) {
			List<XmlModel> xmlModels = xmlProject.getXmlModels();
			if (xmlModels != null) {
				results = new TreeMap<String, ISdSystem>();
				for (XmlModel xmlModel : xmlModels) {
					String modelName = xmlModel.getName();
					ISdSystem sdSystem = buildSdSystem(xmlModel);
					if (sdSystem != null) {
						results.put(modelName, sdSystem);
					}
				}
			}
		}
			
		return results;
	}


	private ISdSystem buildSdSystem(XmlModel xmlModel) {
	
	
		ISdSystem sdSystem = null;
	
		if (xmlModel != null) {
		
			List<XmlObject> xmlObjects = xmlModel.getChildren();
			if (xmlObjects != null) {
			
				Map< String, ISdObject > sdObjects = new TreeMap<String, ISdObject>();
				for (XmlObject xmlObject : xmlObjects) {
				
					String type = xmlObject.getType();
					String name = xmlObject.getName();
					String equation = xmlObject.getEquation();
				
					ISdObject sdObject = null;
				
					if (type.trim().equals(AbstractSdObject.INPUT_OBJECT)) {
						sdObject = new InputSdObject();
					
						List<SdModelConnection> connections = xmlObject.getConnections();
						if (connections != null) {
							for (SdModelConnection connection : connections) {
							
								XmlBridge xmlBridge = new XmlBridge();
								xmlBridge.setInputModelName(xmlModel.getName());
								xmlBridge.setInputObjectName(name);
								xmlBridge.setOutputModelName(connection.getModelName());
								xmlBridge.setOutputObjectName(connection.getObjectName());
								_xmlBridgeList.add(xmlBridge);
							}
						}
					
					
					
					}
					if (type.trim().equals(AbstractSdObject.OUTPUT_OBJECT)) {
						String equationValue  = findEquationValue(equation, xmlObjects);
						
						IEquation calcEquation;
						try {
							calcEquation = new CalculatedEquation(equationValue);
							sdObject = new OutputSdObject(calcEquation);
						} catch (CalculationExeption e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (SdSystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (type.trim().equals(AbstractSdObject.FLOW_OBJECT)) {
						String equationValue  = findEquationValue(equation, xmlObjects);
						IEquation calcEquation;
						try {
							calcEquation = new CalculatedEquation(equationValue);
							sdObject = new FlowSdObject(calcEquation);
						} catch (CalculationExeption e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (SdSystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (type.trim().equals(AbstractSdObject.STOCK_OBJECT)) {
						String equationValue  = findEquationValue(equation, xmlObjects);
						IEquation calcEquation;
						try {
							calcEquation = new CalculatedEquation(equationValue);
							double initialValue = xmlObject.getInitialValue();
							sdObject = new StockSdObject(calcEquation, initialValue);
						} catch (CalculationExeption e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (SdSystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if ((  !type.trim().equals(AbstractSdObject.CALCULATE_OBJECT))  && (!type.trim().equals(AbstractSdObject.LOOKUP_OBJECT)) ) {
						sdObjects.put(name, sdObject);
					}
				
				
				}
				try {
					sdSystem = new SdSystem(sdObjects);
				} catch (SdSystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		
		}
		return sdSystem;
	}

	private ISdSystem findSdSystem(String name, Map<String, ISdSystem> sdSystems) {
		
		
		ISdSystem results = null;
		
		if ( (name != null) && (sdSystems != null)) {
			
			for (Entry<String, ISdSystem> entry : sdSystems.entrySet()) {
			    String key = entry.getKey();
			    ISdSystem value = entry.getValue();
			    
			    if (key.equals(name)) {
			    	results = value;
			    }
			    
			}
		}
		
		return results;
	}
	
	
	
	private String findEquationValue(String objectName, List<XmlObject> xmlObjects) {
		String results = null;
		
		if (xmlObjects != null) {
			for (XmlObject xmlObject :xmlObjects) {
				if (xmlObject.getName().equals(objectName)) {
					results = xmlObject.getCalculateString();
				}
			}
		}
		
		return results;
	}

}
