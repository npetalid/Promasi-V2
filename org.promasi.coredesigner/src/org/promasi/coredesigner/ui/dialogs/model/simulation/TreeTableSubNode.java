package org.promasi.coredesigner.ui.dialogs.model.simulation;

public class TreeTableSubNode {
	
	
	private String _name;
	private String _equation;
	private TreeTableNode _sdModel;
	private String _value;
	private String _type;
	
	public TreeTableSubNode() {
		
	}
	
	public void setType(String type) {
		_type = type;
	}
	public String getType() {
		return _type;
	}
	
	public void setValue(String value) {
		_value = value;
	}
	public String getValue() {
		return _value;
	}

	public TreeTableSubNode(TreeTableNode sdModel) {
		_sdModel = sdModel;
	}

	public void setSdModel(TreeTableNode sdModel) {
		_sdModel = sdModel;
	}
	public TreeTableNode getSdModel() {
		return _sdModel;
	}
	
	public void setName(String name) {
		_name = name;
	}
	public String getName() {
		return _name;
	}
	public void setEquation(String equation) {
		_equation = equation;
	}
	public String getEquation() {
		return _equation;
	}
	@Override
	public String toString() {
		return _name;
	}

}
