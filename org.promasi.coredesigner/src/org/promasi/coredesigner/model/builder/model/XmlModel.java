package org.promasi.coredesigner.model.builder.model;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author antoxron
 *
 */
public class XmlModel {
	
	private String name;
	private List<XmlObject> children;
	
	public XmlModel() {
		children = new ArrayList<XmlObject>();
	}

	
	public void addObject(XmlObject object) {
		children.add(object);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<XmlObject> getChildren() {
		return children;
	}

	public void setChildren(List<XmlObject> children) {
		this.children = children;
	}
}