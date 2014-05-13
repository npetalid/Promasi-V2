package org.promasi.coredesigner.ui.dialogs.model;

import java.util.List;
import java.util.SortedMap;
/**
 * 
 * @author antoxron
 *
 */
public class GeneralDialogSettings {
	
	private String _objectName;
	private String _initialValue;
	private String _selectedEquation;
	private SortedMap<String , String> _equations;
	private List<DependencyTableModel> _dependencies;
	
	
	
	public void setObjectName(String objectName) {
		_objectName = objectName;
	}
	public String getObjectName() {
		return _objectName;
	}
	public void setInitialValue(String initialValue) {
		_initialValue = initialValue;
	}
	public String getInitialValue() {
		return _initialValue;
	}
	public void setSelectedEquation(String selectedEquation) {
		_selectedEquation = selectedEquation;
	}
	public String getSelectedEquation() {
		return _selectedEquation;
	}
	public void setEquations(SortedMap<String , String> equations) {
		_equations = equations;
	}
	public SortedMap<String , String> getEquations() {
		return _equations;
	}
	public void setDependencies(List<DependencyTableModel> dependencies) {
		_dependencies = dependencies;
	}
	public List<DependencyTableModel> getDependencies() {
		return _dependencies;
	}
}