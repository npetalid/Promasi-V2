package org.promasi.coredesigner.ui.dialogs.model;

import java.util.List;
import java.util.SortedMap;

import org.promasi.coredesigner.model.SdModelConnection;
/**
 * 
 * @author antoxron
 *
 */
public class ConnectionDialogSettings {
	
	
	private String _objectName;
	private String _selectedEquation;
	private SortedMap<String , String> _equations;
	private List<DependencyTableModel> _dependencies;
	private List<ConnectionTableModel> _connections;
	private List<SdModelConnection> _connectionObjects;
	
	
	public void setConnectionObjects(List<SdModelConnection> connectionObjects) {
		_connectionObjects = connectionObjects;
		
	}
	public List<SdModelConnection> getConnectionObjects() {
		return _connectionObjects;
	}
	
	public ConnectionDialogSettings() {
		
	}
	public void setObjectName(String objectName) {
		_objectName = objectName;
	}
	public String getObjectName() {
		return _objectName;
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
	public void setConnections(List<ConnectionTableModel> connections) {
		_connections = connections;
	}
	public List<ConnectionTableModel> getConnections() {
		return _connections;
	}

}
