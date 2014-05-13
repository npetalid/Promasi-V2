package org.promasi.coredesigner.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;

/**
 * Represents the connection between SdObjects
 * 
 * @author antoxron
 *
 */
public class SdConnection {
	
	/**
	 * the source object of the connection
	 */
	private SdObject _sourceObject;
	/**
	 * the target object of the connection
	 */
	private SdObject _targetObject;
	/**
	 * 
	 */
	private List<Point> _bendpoints = new ArrayList<Point>( );
	/**
	 * used as an identification request
	 */
	public static final String BEND_POINT = "BendPoint";
	/**
	 * 
	 */
	private PropertyChangeSupport _listeners = new PropertyChangeSupport( this );

	/**
	 * 
	 * @param sourceObject
	 * @param targetObject
	 */
	public SdConnection( SdObject sourceObject, SdObject targetObject ) {
		_sourceObject = sourceObject;
		_targetObject = targetObject;
	}
	/**
	 * 
	 * @return
	 */
	public SdObject getSourceObject( ) {
		return _sourceObject;
	}
	/**
	 * 
	 * @return
	 */
	public SdObject getTargetObject( ) {
		return _targetObject;
	}
	/**
	 * make the connection between SdObjects
	 */
	public void connect( ) {
		_sourceObject.addConnection( this );
		_targetObject.addConnection( this );
	}
	/**
	 * remove the connection between SdObjects
	 * for example if the user chooses to delete the specific connection
	 */
	public void disconnect( ) {
		_sourceObject.removeConnection( this );
		_targetObject.removeConnection( this );
	}
	/**
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener( PropertyChangeListener listener ) {
		_listeners.addPropertyChangeListener( listener );
	}
	/**
	 * 
	 * @param propName
	 * @param oldValue
	 * @param newValue
	 */
	public void firePropertyChange( String propName, Object oldValue, Object newValue ) {

		_listeners.firePropertyChange( propName, oldValue, newValue );
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
	 */
	public Object getEditableValue( ) {
		return this; 
	}
	/**
	 * 
	 */
	public void addBendpoint( int index, Point point ) {
		_bendpoints.add( index, point );
		firePropertyChange( BEND_POINT, null, null );
	}
	/**
	 * 
	 * @return
	 */
	public List<Point> getBendpoints( ) {
		return _bendpoints;
	}
	/**
	 * 
	 * @param index
	 */
	public void removeBendpoint( int index ) {
		_bendpoints.remove( index );
		firePropertyChange( BEND_POINT, null, null );
	}
	/**
	 * 
	 * @param index
	 * @param point
	 */
	public void replaceBendpoint( int index, Point point ) {
		_bendpoints.set( index, point );
		firePropertyChange( BEND_POINT, null, null );
	}
}