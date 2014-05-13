package org.promasi.coredesigner.model.builder;

import java.io.FileWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import org.promasi.coredesigner.model.Dependency;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.builder.model.XmlModel;
import org.promasi.coredesigner.model.builder.model.XmlObject;
import org.promasi.coredesigner.model.builder.model.XmlProject;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
/**
 * 
 * @author antoxron
 *
 */
@SuppressWarnings("deprecation")
public class VisualXmlBuilder {
	
	private XmlProject _xmlProject;
	
	public VisualXmlBuilder(XmlProject xmlProject) {
		_xmlProject = xmlProject;
	}
	
	public void build(String filename) throws IOException {
		
		if ( (filename != null ) && (_xmlProject != null)) {
			if ( !filename.trim().isEmpty() ) {
				
				
				Document document = new DocumentImpl();
				Element project = getModelsElement(document  , _xmlProject);
				document.appendChild(project);
				
				OutputFormat format  = new OutputFormat(document, "UTF-8", true);
				FileWriter file;
		
				file = new FileWriter(filename);
				XMLSerializer serial = new XMLSerializer(file, format);
				serial.asDOMSerializer();

				serial.serialize(document.getDocumentElement());
				file.close();

				
			}
		}
		
		
	}
	public void setProject(XmlProject xmlProject) {
		_xmlProject = xmlProject;
	}
	public XmlProject getProject() {
		return _xmlProject;
	}
	
	 private Element getDependencyElement(Document document , Dependency  dependency) {
			Element results = null;

			if (dependency != null) {
				results = document.createElement("Dependency");
				 
				 Element name = document.createElement("Name");
				 Element type = document.createElement("Type");
				 Element model = document.createElement("Model");

				 Text nameText = document.createTextNode( dependency.getName());
				 Text typeText = document.createTextNode( dependency.getType());
				 Text modelText = document.createTextNode( dependency.getModelName());
				 
				 name.appendChild(nameText);
				 type.appendChild(typeText);
				 model.appendChild(modelText);
				 
				 results.appendChild(model);
				 results.appendChild(name);
				 results.appendChild(type);
			}
			return results;
	  }
	 
	 private  Element getDependenciesElement(Document document ,List<Dependency> dependencies) {
			Element results = null;
			
			if ( (dependencies != null) && (!dependencies.isEmpty())) {
				Element dependencyList = document.createElement("Dependencies");
				for (Dependency dependency : dependencies) {
					Element dependencyElement = getDependencyElement(document , dependency);
					if (dependencyElement != null) {
						dependencyList.appendChild(dependencyElement);
					}
				}
				results = dependencyList;
				
			}
			
			return results;
	  }
	 private  Element getConnectionElement(Document document ,SdModelConnection connection) {
			Element results = null;

			if ( connection != null) {
				results = document.createElement("Connection");
				
				 Element name = document.createElement("Name");
				 Element model = document.createElement("Model");
				 
				 Text nameText = document.createTextNode(connection.getObjectName());
				 Text modelText = document.createTextNode(connection.getModelName());
				 name.appendChild(nameText);
				 model.appendChild(modelText);
				 results.appendChild(name);
				 results.appendChild(model);
			}
			
			
			
			return results;

	  }
	 private  Element getConnectionsElement(Document document ,List<SdModelConnection> connections) {

			Element results = null;

		  if ( (connections != null) && (!connections.isEmpty())) {
			  
			  results = document.createElement("Connections");
			  Comment comments = document.createComment("Model connections , the output objects");
			  results.appendChild(comments);
			  
			  
			  
			  for (SdModelConnection connection : connections) {
				  Element connectionElement = getConnectionElement(document , connection);
				  results.appendChild(connectionElement);
			  }
		  }
		  
		  
			return results;
	  }
	 private  Element getTreeMapElement(Document document ,TreeMap<Double, Double> treeMap) {
			Element results = null;
			
			if (treeMap != null) {
				Element lookUpPoints = document.createElement("LookupPoints");
				Comment comments = document.createComment("Lookup Equation (x,y) points");
				lookUpPoints.appendChild(comments);
				
				
				  Set<Double> keys = treeMap.keySet();
				  Iterator<Double> it = keys.iterator();

				  while (it.hasNext())  {
					Element point = document.createElement("Point");
					Element xPoint = document.createElement("x");
					Element yPoint = document.createElement("y");

					
					 double x = it.next();
					 double y = treeMap.get(x);

					 Text xPointText = document.createTextNode( String.valueOf(x) );
					 Text yPointText = document.createTextNode( String.valueOf(y) );
					 xPoint.appendChild(xPointText);
					 yPoint.appendChild(yPointText);
					 point.appendChild(xPoint);
					 point.appendChild(yPoint);
					 
					 
					 lookUpPoints.appendChild(point);
				 }
				  results = lookUpPoints; 
			}
				
			return results;
	 }
	 private Element getObjectElement(Document document ,XmlObject xmlObject) {
			Element results = null;
			
			
			if (xmlObject != null) {
				
				//object values
				String xmlObjectName = xmlObject.getName();
				String xmlObjectType = xmlObject.getType();
				//visual values
				String xValue = String.valueOf(  xmlObject.getX() );
				String yValue = String.valueOf( xmlObject.getY() );
				String heightValue = String.valueOf( xmlObject.getHeight());
				String widthValue = String.valueOf( xmlObject.getWidth());
				
				
				//equations
				String initialValue = String.valueOf( xmlObject.getInitialValue() );
				String equation = xmlObject.getEquation();
				String calculateString = xmlObject.getCalculateString();
				
				Element lookupEquation = getTreeMapElement( document ,xmlObject.getLookupPoints() );
				Element dependencies = getDependenciesElement( document , xmlObject.getDependencies());
				Element connections = getConnectionsElement( document , xmlObject.getConnections());
				
				
				
					
				
				
				results = document.createElement("Object");
				
				Element name = document.createElement("Name");
				Element type = document.createElement("Type");
				Text nameText = document.createTextNode(xmlObjectName);
				Text typeText = document.createTextNode(xmlObjectType);
				name.appendChild(nameText);
				type.appendChild(typeText);
				
				results.appendChild(name);
				results.appendChild(type);
				
				
				
				Comment comments = document.createComment("Coordinates and size of visual object");
				results.appendChild(comments);
				
				Element visualPositions = document.createElement("VisualPoints");
				Element x = document.createElement("x");
				Element y = document.createElement("y");
				Element height = document.createElement("Height");
				Element width = document.createElement("Width");
				
				Text xText = document.createTextNode(xValue);
				Text yText = document.createTextNode(yValue);
				Text heightText = document.createTextNode(heightValue);
				Text widthText = document.createTextNode(widthValue);
				x.appendChild(xText);
				y.appendChild(yText);
				height.appendChild(heightText);
				width.appendChild(widthText);
				
				visualPositions.appendChild(x);
				visualPositions.appendChild(y);
				visualPositions.appendChild(height);
				visualPositions.appendChild(width);
				results.appendChild(visualPositions);
				
				
				
				Element equationElement = document.createElement("Equation");
				
				Element equationType = document.createElement("Type");
				Element initialValueElement = document.createElement("InitialValue");
				Element calcStringElement = document.createElement("CalculatedEquationValue");

				Text equationText = document.createTextNode(equation);
				Text calcStringText = document.createTextNode(calculateString);
				Text initialValueText = document.createTextNode(initialValue);
				
				equationType.appendChild(equationText);
				initialValueElement.appendChild(initialValueText);
				calcStringElement.appendChild(calcStringText);
				
				equationElement.appendChild(equationType);
				equationElement.appendChild(initialValueElement);
				equationElement.appendChild(calcStringElement);
				equationElement.appendChild(lookupEquation);
				
				
				if (equationElement != null) {
					results.appendChild(equationElement);
				}
				if (dependencies != null) {
					results.appendChild(dependencies);
				}
				if (connections != null) {
					results.appendChild(connections);
				}
				
			}
			return results;
	 }
	 private Element getModelElement(Document document ,XmlModel xmlModel) {
			Element results = null;

			
			if (xmlModel != null) {
				
				String modelName = xmlModel.getName();
				List<XmlObject> xmlObjects = xmlModel.getChildren();
				String objectNumber = String.valueOf( xmlObjects.size()  );
				
				
				Element model = document.createElement("Model");
				
				Element modelNameElement = document.createElement("Name");
				
				
				Element objectNumberElement = document.createElement("Objects");
				Text modelNameText = document.createTextNode(modelName);
				
				modelNameElement.appendChild(modelNameText);
				
				
				
				model.appendChild(modelNameElement);
				
				Text objectNumberText = document.createTextNode(objectNumber);
				objectNumberElement.appendChild(objectNumberText);
				
				model.appendChild(objectNumberElement);
				
				
				if (xmlObjects != null) {
					for (XmlObject xmlObject : xmlObjects) {
						Element objectElement = getObjectElement( document , xmlObject );
						model.appendChild(objectElement);
					}
				}
				
				
				results = model;
			}
			
			
			return results;
	 }
	 private Element getModelsElement(Document document , XmlProject xmlProject) {
			Element results = null;
			
			if (xmlProject != null) {
				
				String name = xmlProject.getName();
				List<XmlModel> xmlModels = xmlProject.getXmlModels();
				String modelNumberStr = String.valueOf( xmlModels.size() );
				
				Element project = document.createElement("Project");
				Element projectName = document.createElement("Name");
				Element modelNumberElement = document.createElement("Models");
				
				Text projectNameText = document.createTextNode(name);
				Text modelNumberText = document.createTextNode(modelNumberStr);
				projectName.appendChild(projectNameText);
				modelNumberElement.appendChild(modelNumberText);
				
				project.appendChild(projectName);
				project.appendChild(modelNumberElement);

				if (xmlModels != null) {
					for (XmlModel xmlModel : xmlModels) {
						Element modelElement = getModelElement(document , xmlModel);
						project.appendChild(modelElement);
					}
				}
				results = project;
			}
			
			return results;
		}

}
