package org.promasi.coredesigner.ui.dialogs.model.simulation;

import java.util.List;

public class TreeTableNode {
	
	private String _name;
	private List<TreeTableSubNode> _sdObjects;
	
	public TreeTableNode() {
		
	}
	
	public void setName(String name) {
		_name = name;
	}
	public String getName() {
		return _name;
	}
	public void setSdObjects(List<TreeTableSubNode> sdObjects) {
		_sdObjects = sdObjects;
	}
	public List<TreeTableSubNode> getSdObjects() {
		return _sdObjects;
	}
	@Override
	public String toString() {
		return _name;
	}
}
