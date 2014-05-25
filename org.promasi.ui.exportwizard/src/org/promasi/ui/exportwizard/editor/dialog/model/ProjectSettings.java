package org.promasi.ui.exportwizard.editor.dialog.model;

import java.util.List;

import org.promasi.ui.exportwizard.editor.dialog.table.model.ProjectTaskTableModel;
/**
 * 
 * @author antoxron
 *
 */

public class ProjectSettings {
	
	
	private List<ProjectTaskTableModel> _tasks;
	private String _projectName;
	private String _description;
	private int _projectDuration;
	private double _projectPrice;
	private double _difficultyLevel;
	
	
	public ProjectSettings() {
		_projectName = "";
		_description = "";
		_projectDuration = 0;
		_projectPrice = 0.0;
		_difficultyLevel = 0.0;
	}

	public void setTasks(List<ProjectTaskTableModel> tasks) {
		_tasks = tasks;
	}
	public List<ProjectTaskTableModel> getTasks() {
		return _tasks;
	}
	public void setProjectName(String projectName) {
		_projectName = projectName;
	}
	public String getProjectName() {
		return _projectName;
	}
	public void setDescription(String description) {
		_description = description;
	}
	public String getDescription() {
		return _description;
	}
	public void setProjectDuration(int projectDuration) {
		_projectDuration = projectDuration;
	}
	public int getProjectDuration() {
		return _projectDuration;
	}
	public void setProjectPrice(double projectPrice) {
		_projectPrice = projectPrice;
	}
	public double getProjectPrice() {
		return _projectPrice;
	}
	public void setDifficultyLevel(double difficultyLevel) {
		_difficultyLevel = difficultyLevel;
	}
	public double getDifficultyLevel() {
		return _difficultyLevel;
	}
}
