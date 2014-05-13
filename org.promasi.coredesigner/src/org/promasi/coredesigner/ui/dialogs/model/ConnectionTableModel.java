package org.promasi.coredesigner.ui.dialogs.model;
/**
 * 
 * @author antoxron
 *
 */
public class ConnectionTableModel {

	private int _id;
	private String _name;
	private String _task;
	
	public ConnectionTableModel() {
		
	}
	public void setId(int id) {
		_id = id;
	}
	public int getId() {
		return _id;
	}
	public void setName(String name) {
		_name = name;
	}
	public String getName() {
		return _name;
	}
	public void setTask(String task) {
		_task = task;
	}
	public String getTask() {
		return _task;
	}	
}