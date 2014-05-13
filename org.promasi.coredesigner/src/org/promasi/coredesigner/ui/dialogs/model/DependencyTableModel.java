package org.promasi.coredesigner.ui.dialogs.model;
/**
 * 
 * @author antoxron
 *
 */
public class DependencyTableModel {

	private int _id;
	private String _name;
	private String _type;
	
	
	public DependencyTableModel() {
		
	}
	public DependencyTableModel(int id , String name , String type , String equation) {
		this._id = id;
		this._name = name;
		this._type = type;
	}
	
	public void setId(int id) {
		this._id = id;
	}
	public int getId() {
		return _id;
	}
	
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		this._type = type;
	}
}
