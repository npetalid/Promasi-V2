package org.promasi.coredesigner.model.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.promasi.coredesigner.model.AbstractSdObject;
import org.promasi.coredesigner.model.Dependency;
import org.promasi.coredesigner.model.SdCalculate;
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
public class VisualModelBuilder implements IModelBuilder {
	
	private XmlProject _xmlProject;
	
	public void setModel(SdProject sdProject) {
		
		if (sdProject != null) {
			List<SdModel> sdModels = sdProject.getModels();
			List<XmlModel> xmlModels = null;
			
			if (sdModels != null) {
				xmlModels = new ArrayList<XmlModel>();
				
				for (SdModel sdModel : sdModels) {
					XmlModel xmlModel = buildXmlModel(sdModel);
					if (xmlModel != null) {
						xmlModels.add(xmlModel);
					}
				}
			}
			if (xmlModels != null) {
				_xmlProject = new XmlProject();
				_xmlProject.setName(  sdProject.getName() );
				_xmlProject.setXmlModels(xmlModels);
			}
			
			
		}
		
		
	}

	private XmlModel buildXmlModel(SdModel sdModel) {
		
		XmlModel results = null;
		if (sdModel != null) {
			
			
			List<SdObject> sdObjects = sdModel.getChildrenArray();
			if (sdObjects != null) {
				List<XmlObject> xmlObjects = new ArrayList<XmlObject>();
				
				for (SdObject sdObject : sdObjects) {
					
					String type = sdObject.getType();
					String name = sdObject.getName();
					int width = sdObject.getLayout().width;
					int height = sdObject.getLayout().height;
					int x = sdObject.getLayout().x;
					int y = sdObject.getLayout().y;
					
					
					XmlObject xmlObject = new XmlObject();
					xmlObject.setType(type);
					xmlObject.setName(name);
					xmlObject.setWidth(width);
					xmlObject.setHeight(height);
					xmlObject.setX(x);
					xmlObject.setY(y);
					
					
					
					//For sdInput
					if (type.equals(AbstractSdObject.INPUT_OBJECT)) {
						SdInput sdInput = (SdInput)sdObject;
						
						List<Dependency> dependencies = sdInput.getDependencies();
						String equation = sdInput.getEquation();
						List<SdModelConnection> connections = sdInput.getConnections();
						if (dependencies != null) {
							xmlObject.setDependencies(dependencies);
						}
						if (equation != null) {
							xmlObject.setEquation(equation);
						}
						if (connections != null) {
							xmlObject.setConnections(connections);
						}
					}
					else if (type.equals(AbstractSdObject.OUTPUT_OBJECT)) {
						SdOutput sdOutput = (SdOutput)sdObject;
						
						List<Dependency> dependencies = sdOutput.getDependencies();
						String equation = sdOutput.getEquation();
						if (dependencies != null) {
							xmlObject.setDependencies(dependencies);
						}
						if (equation != null) {
							xmlObject.setEquation(equation);
						}
					}
					else if (type.equals(AbstractSdObject.VARIABLE_OBJECT)) {
						SdVariable sdVariable = (SdVariable)sdObject;
						
						List<Dependency> dependencies = sdVariable.getDependencies();
						String equation = sdVariable.getEquation();
						if (dependencies != null) {
							xmlObject.setDependencies(dependencies);
						}
						if (equation != null) {
							xmlObject.setEquation(equation);
						}
					}
					else if (type.equals(AbstractSdObject.FLOW_OBJECT)) {
						SdFlow sdFlow = (SdFlow)sdObject;
						
						List<Dependency> dependencies = sdFlow.getDependencies();
						String equation = sdFlow.getEquation();
						if (dependencies != null) {
							xmlObject.setDependencies(dependencies);
						}
						if (equation != null) {
							xmlObject.setEquation(equation);
						}
					}
					else if (type.equals(AbstractSdObject.STOCK_OBJECT)) {
						SdStock sdStock = (SdStock)sdObject;
						
						List<Dependency> dependencies = sdStock.getDependencies();
						String equation = sdStock.getEquation();
						double initialValue = Double.valueOf(  sdStock.getInitialValue() );
						
						xmlObject.setInitialValue(initialValue);
						
						if (dependencies != null) {
							xmlObject.setDependencies(dependencies);
						}
						if (equation != null) {
							xmlObject.setEquation(equation);
						}
						
					}
					else if (type.equals(AbstractSdObject.LOOKUP_OBJECT)) {
						SdLookup sdLookup = (SdLookup)sdObject;
						
						TreeMap<Double,Double> lookupPoints = sdLookup.getLookupPoints();
						
						if (lookupPoints != null) {
							xmlObject.setLookupPoints(lookupPoints);
						}
						
					}
					else if (type.equals(AbstractSdObject.CALCULATE_OBJECT)) {
						SdCalculate sdCalculate = (SdCalculate)sdObject;
						
						String calculateString = sdCalculate.getCalculateString();
						
						if (calculateString != null) {
							xmlObject.setCalculateString(calculateString);
						}
						
					}
					
					
					xmlObjects.add(xmlObject);	
				}
				
				results = new XmlModel();
				results.setName( sdModel.getName());
				results.setChildren(xmlObjects);
				
				
			}
			
		}
		return results;
	}
	
	public XmlProject getModel() {
		return _xmlProject;
	}

}
