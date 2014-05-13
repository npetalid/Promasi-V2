package org.promasi.coredesigner.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * Represents the SdObject.
 * 
 * @author antoxron
 *
 */
public abstract class AbstractSdObject {
	
	/**
	 * used for move or resize object
	 */
	protected PropertyChangeSupport _listeners;
	/**
	 * used as an identification request
	 */
	public static final String PROPERTY_LAYOUT = "SdObjectLayout";
	/**
	 * used as an identification request
	 */
	public static final String PROPERTY_ADD = "SdObjectAddChild";
	/**
	 * used as an identification request
	 */
	public static final String PROPERTY_REMOVE = "SdObjectRemoveChild";
	/**
	 * used as an identification request
	 */
	public static final String PROPERTY_RENAME = "SdObjectRename";
	/**
	 * 
	 */
	protected  List<SdConnection> _sourceConnections;
	/**
	 * 
	 */
	protected  List<SdConnection> _targetConnections;
	/**
	 * used as an identification request
	 */
	public static final String SOURCE_CONNECTION ="SourceConnectionAdded";
	/**
	 * used as an identification request
	 */
	public static final String TARGET_CONNECTION = "TargetConnectionAdded";
	/**
	 * 
	 */
	public static final String EMPTY_OBJECT_NAME = "Unknown";
	/**
	 * 
	 */
	public static  final String STOCK_OBJECT = "Stock";
	/**
	 * 
	 */
	public static  final String FLOW_OBJECT = "Flow";
	/**
	 * 
	 */
	public static  final String INPUT_OBJECT = "Input";
	/**
	 * 
	 */
	public static  final String OUTPUT_OBJECT = "Output";
	/**
	 * 
	 */
	public static  final String CALCULATE_OBJECT = "Calculate";
	/**
	 * 
	 */
	public static  final String LOOKUP_OBJECT = "Lookup";
	/**
	 * 
	 */
	public static  final String VARIABLE_OBJECT = "Variable";

	/**
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener( PropertyChangeListener listener ) {
		_listeners.addPropertyChangeListener( listener );
	}
	/**
	 * 
	 * @return
	 */
	public PropertyChangeSupport getListeners( ) {
		return _listeners;
	}
	/**
	 * 
	 * @param listener
	 */
	public void removePropertyChangeListener( PropertyChangeListener listener ) {
		_listeners.removePropertyChangeListener( listener );
	}
	/**
	 * 
	 * @return the source connections
	 */
	public List<SdConnection> getSourceConnectionArray( ) {
		return _sourceConnections;
	}
	/**
	 * 
	 * @return the target connections
	 */
	public List<SdConnection> getTargetConnectionArray( ) {
		return _targetConnections;
	}
	/**
	 * 
	 * @param sourceConnections
	 */
	public void setSourceConnectionArray( List<SdConnection> sourceConnections ) {
		_sourceConnections = sourceConnections;
	}
	/**
	 * 
	 * @param targetConnections
	 */
	public void setTargetConnectionArray( List<SdConnection> targetConnections ) {
		_targetConnections = targetConnections;
	}	
}