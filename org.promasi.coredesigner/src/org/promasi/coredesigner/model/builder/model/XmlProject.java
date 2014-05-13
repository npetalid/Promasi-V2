package org.promasi.coredesigner.model.builder.model;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author antoxron
 *
 */
public class XmlProject {

	private String name;
	private List<XmlModel> xmlModels;
	
	public XmlProject() {
		xmlModels = new ArrayList<XmlModel>();
	}

	public void addModels(XmlModel xmlModel) {
		xmlModels.add(xmlModel);
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<XmlModel> getXmlModels() {
		return xmlModels;
	}


	public void setXmlModels(List<XmlModel> xmlModels) {
		this.xmlModels = xmlModels;
	}



	
}
