package org.promasi.coredesigner.model.parser;

import java.io.File;
import java.io.IOException;

import java.util.TreeMap;

import org.apache.commons.digester3.Digester;

import org.promasi.coredesigner.model.Dependency;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.builder.model.XmlModel;
import org.promasi.coredesigner.model.builder.model.XmlObject;
import org.promasi.coredesigner.model.builder.model.XmlProject;

import org.xml.sax.SAXException;
/**
 * 
 * @author antoxron
 *
 */
public class VisualXmlParser {
	
	private String _filename;
	
	public VisualXmlParser(String filename) {
		_filename = filename;
	}
	
	
	public void setFilename(String filename) {
		_filename = filename;
	}
	public String getFilename() {
		return _filename;
	}
	
	
	public XmlProject parse() throws IOException, SAXException {
		
		XmlProject results = null;
		
		
		if (_filename != null) {
			if (!_filename.trim().isEmpty()) {
				
			         Digester digester = new Digester();
			         digester.setValidating( false );

			         digester.addObjectCreate( "Project", XmlProject.class );
			         digester.addBeanPropertySetter( "Project/Name", "name" );
			         
			         digester.addObjectCreate( "Project/Model", XmlModel.class );
			         digester.addBeanPropertySetter( "Project/Model/Name", "name" );
			         
			         digester.addSetNext( "Project/Model", "addModels" );
			         
			         
			         digester.addObjectCreate( "Project/Model/Object", XmlObject.class );
			         digester.addBeanPropertySetter( "Project/Model/Object/Name", "name" );
			         digester.addBeanPropertySetter( "Project/Model/Object/Type", "type" );
			         digester.addBeanPropertySetter( "Project/Model/Object/VisualPoints/x", "x" );
			         digester.addBeanPropertySetter( "Project/Model/Object/VisualPoints/y", "y" );
			         digester.addBeanPropertySetter( "Project/Model/Object/VisualPoints/Height", "height" );
			         digester.addBeanPropertySetter( "Project/Model/Object/VisualPoints/Width", "width" );
			         
			         //equation
			         digester.addBeanPropertySetter( "Project/Model/Object/Equation/Type", "equation" );
			         digester.addBeanPropertySetter( "Project/Model/Object/Equation/InitialValue", "initialValue" );
			         digester.addBeanPropertySetter( "Project/Model/Object/Equation/CalculatedEquationValue", "calculateString" );

			         digester.addObjectCreate( "Project/Model/Object/Equation/LookupPoints", TreeMap.class );
				     digester.addCallMethod("Project/Model/Object/Equation/LookupPoints/Point" , "put", 2, new String[] { "java.lang.Double" , "java.lang.Double" });
			         digester.addCallParam("Project/Model/Object/Equation/LookupPoints/Point/x", 0);        
			         digester.addCallParam("Project/Model/Object/Equation/LookupPoints/Point/y", 1 );
			         digester.addSetNext("Project/Model/Object/Equation/LookupPoints/Point/", "setLookupPoints"); 

			         //dependencies
			         digester.addObjectCreate( "Project/Model/Object/Dependencies/Dependency", Dependency.class );
			         digester.addBeanPropertySetter( "Project/Model/Object/Dependencies/Dependency/Model", "modelName" );
			         digester.addBeanPropertySetter( "Project/Model/Object/Dependencies/Dependency/Name", "name" );
			         digester.addBeanPropertySetter( "Project/Model/Object/Dependencies/Dependency/Type", "type" );
			         digester.addSetNext( "Project/Model/Object/Dependencies/Dependency", "addDependency" );

			         
			         //connections
			         digester.addObjectCreate( "Project/Model/Object/Connections/Connection", SdModelConnection.class );
			         digester.addBeanPropertySetter( "Project/Model/Object/Connections/Connection/Name", "objectName" );
			         digester.addBeanPropertySetter( "Project/Model/Object/Connections/Connection/Model", "modelName" );
			         digester.addSetNext( "Project/Model/Object/Connections/Connection", "addConnection" );
			         digester.addSetNext( "Project/Model/Object", "addObject" );

			         File inputFile = new File( _filename );
			         results = (XmlProject)digester.parse( inputFile );
				
			}
		}
		return results;
	}
	
	

}
