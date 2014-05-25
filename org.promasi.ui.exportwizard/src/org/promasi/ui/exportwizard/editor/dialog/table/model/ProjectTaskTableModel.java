package org.promasi.ui.exportwizard.editor.dialog.table.model;

import java.util.List;
/**
 * 
 * @author antoxron
 *
 */
public class ProjectTaskTableModel {
	
	
	private String _taskName;
	private String _equation;
	
	private List<String> _variables;
	
	public ProjectTaskTableModel() {
		_equation = "";
	}
	
	public void setTaskName(String taskName) {
		_taskName = taskName;
	}
	public String getTaskName() {
		return _taskName;
	}
	public void setEquation(String equation) {
		_equation = equation;
	}
	public String getEquation() {
		return _equation;
	}
	
	public void setVariables(List<String> variables) {
		_variables = variables;
	}
	public List<String> getVariables() {
		return _variables;
	}
	

}
