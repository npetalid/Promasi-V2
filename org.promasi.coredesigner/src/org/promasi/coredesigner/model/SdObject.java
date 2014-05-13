package org.promasi.coredesigner.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
/**
 * Represents the SdObject 
 * 
 * @author antoxron
 *
 */
public class SdObject extends AbstractSdObject {

	/**
	 * list of SdObjects that are connected to this SdObject.
	 */
	private List<Dependency> _dependencies;
	/**
	 * the type of sdObject (Flow , Stock , Variable etc).
	 */
	private String _type;
	/**
	 * the name of SdObject
	 */
	private String _name = null; 
	/**
	 * the coordinates of the visual representation of the SdObject.
	 */
	private Rectangle layout; 
	/**
	 * used by the SdModel to describe the SdObject that contain.
	 */
	private List<SdObject> _children; 
	/**
	 * 
	 */
	private SdObject parent; 
	
	
	public SdObject( ){ 
		_sourceConnections = new ArrayList<SdConnection>();
		_targetConnections = new ArrayList<SdConnection>();
		this.layout = new Rectangle( 10, 10, 100, 100 ); 
		this._children = new ArrayList<SdObject>( ); 
		this.parent = null; 
		_dependencies = new ArrayList<Dependency>( );
		this._listeners = new PropertyChangeSupport( this );
	} 
	/**
	 * 
	 * @param dependencies
	 */
	public void setDependencies( List<Dependency> dependencies ) {
		_dependencies = dependencies;
	}
	/**
	 * 
	 * @return dependencies
	 */
	public List<Dependency> getDependencies( ) {
		
		List<Dependency> targetConnections = getDependenciesFromTargetConnections( );
		if ( ( targetConnections != null ) && ( !targetConnections.isEmpty( ) ) ) {
			_dependencies = targetConnections;
		}
		
		return _dependencies;
	}
	/**
	 * 
	 * @param type , 
	 */
	public void setType( String type ) {
		_type = type;
	}
	/**
	 * 
	 * @return type of sdObject
	 */
	public String getType( ) {
		return _type;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName( String name ) { 
		
		if ( NameValidator.getInstance().validateNames( ) ) {
			
			if ( !name.equals( EMPTY_OBJECT_NAME ) ) {
				boolean isCorrect = false;
				
				if ( ( this instanceof SdModel )  ) {
					isCorrect = NameValidator.getInstance().validateName( _name, name );
				}
				else {
					if ( this.getParent() != null ) {
						String parentName = this.getParent().getName( );
						isCorrect = NameValidator.getInstance().validateName( _name, name, parentName );
					}
				}
				if (isCorrect) {
					String oldName = _name;
					_name = name;
					getListeners().firePropertyChange( PROPERTY_RENAME, oldName, _name );
				}
			}
			else {
				String oldName = _name;
				_name = name;
				getListeners().firePropertyChange( PROPERTY_RENAME, oldName, _name );
			}
			
		}
		else {
			String oldName = _name;
			_name = name;
			getListeners().firePropertyChange( PROPERTY_RENAME, oldName, _name );
		}
	} 
	/**
	 * 
	 * @return name
	 */
	public String getName( ) { 
		return this._name; 
	} 
	/**
	 * 
	 * @param newLayout
	 */
	public void setLayout( Rectangle newLayout ) { 
		Rectangle oldLayout = this.layout; 
		this.layout = newLayout; 
		getListeners().firePropertyChange( PROPERTY_LAYOUT, oldLayout, newLayout );
	} 
	/**
	 * 
	 * @return layout
	 */
	public Rectangle getLayout( ) { 
		return this.layout; 
	} 
	/**
	 * 
	 */
	public void refreshDependencies( ) {
		List<Dependency> dependencies = new ArrayList<Dependency>();
				
		
		int numberOfDependencies = _targetConnections.size( );
		if ( numberOfDependencies > 0 )
		{
			for ( SdConnection connection : _targetConnections ) 
			{
				SdObject sdObject = connection.getSourceObject( );
				if ( sdObject != null ) {
					Dependency dependency = new Dependency( );
					dependency.setName( sdObject.getName() );
					dependency.setType( sdObject.getType() );
					dependency.setModelName( sdObject.getParent().getName( ) );
					dependencies.add( dependency );
				}
				
			}
		}
		_dependencies = dependencies;
	}
	/**
	 * 
	 * @return dependencies
	 */
	public List<Dependency> getDependenciesFromTargetConnections( ) {
		
		List<Dependency> dependencies = new ArrayList<Dependency>();
			
		int numberOfDependencies = _targetConnections.size( );
		if ( numberOfDependencies > 0 ) {
			for ( SdConnection connection : _targetConnections )  {
				SdObject sdObject = connection.getSourceObject( );
				if ( sdObject != null ) {
					Dependency dependency = new Dependency( );
					dependency.setName( sdObject.getName() );
					dependency.setType( sdObject.getType() );
					dependency.setModelName( sdObject.getParent().getName( ) );
					dependencies.add( dependency );
				}
				
			}
		}
		return dependencies;
	}
	/**
	 * 
	 * @param children
	 */
	public void setChildren( List<SdObject> children ) {
		_children = children;
	}
	/**
	 * 
	 * @return
	 */
	public List<SdObject> getChildrenArray( ) {
		return this._children; 
	}
	/**
	 * 
	 * @param child
	 * @return
	 */
	public boolean addChild( SdObject child ) {
		boolean isAdded = _children.add( child );
		
		if ( isAdded ) {
			child.setParent( this );
			getListeners().firePropertyChange( PROPERTY_ADD, null, child );
		}
		return isAdded;
	}
	/**
	 * 
	 * @param child
	 * @return
	 */
	public boolean removeChild( SdObject child ) {
		boolean isRemoved = _children.remove( child );
		
		if ( isRemoved ) {
			
			if ( !( child instanceof SdModel ) ) {
				NameValidator.getInstance().removeName( child.getName( ), child.getParent().getName( ) );
			}
			else {
				NameValidator.getInstance().removeName( child.getName( ) );
			}
			getListeners().firePropertyChange( PROPERTY_REMOVE, child, null );
		}
		return isRemoved;
	}
	/**
	 * 
	 * @param parent
	 */
	public void setParent( SdObject parent ) { 
		this.parent = parent; 
	} 
	/**
	 * 
	 * @return
	 */
	public SdObject getParent( ) { 
		return this.parent; 
	}
	/**
	 * 
	 * @param child
	 * @return
	 */
	public boolean contains( SdObject child ) {
		return _children.contains( child );
	}
	/**
	 * used to make a connection of this SdObject with another SdObject.
	 * 
	 * @param connection
	 * @return
	 */
	public boolean addConnection( SdConnection connection ) {
		
		SdObject sourceObject = connection.getSourceObject( );
		SdObject targetObject = connection.getTargetObject( );
		
		
		boolean isCorrect = false;
		
		if ( ( sourceObject.getParent().getName() ).equals( targetObject.getParent().getName() ) ) {
			
			if (   (! ( ( (sourceObject instanceof SdInput ) && ( targetObject instanceof SdOutput ) ) 
					|| ( (sourceObject instanceof SdOutput ) && ( targetObject instanceof SdInput ) ) ) ) 
					&& (! ( (sourceObject instanceof SdStock ) && ( targetObject instanceof SdStock ) ) ) 
					&& (! ( ( sourceObject instanceof SdFlow ) && ( targetObject instanceof SdFlow ) ) ) ) {
				
				isCorrect = true;
			}
		
		}
		if ( !( sourceObject.getParent().getName() ).equals( targetObject.getParent().getName() ) ) {

			if ( ( sourceObject instanceof SdOutput ) && ( targetObject instanceof SdInput ) ) {
				isCorrect = true;
			}
		}
		
		if ( isCorrect ) {
			if ( connection.getSourceObject() == this ) {
				if ( !_sourceConnections.contains( connection ) ) {
					String sourceObjectName = connection.getSourceObject().getName( );
					String targetObjectName = connection.getTargetObject().getName( );
					if ( ( !sourceObjectName.equals( EMPTY_OBJECT_NAME ) ) && ( !targetObjectName.equals(EMPTY_OBJECT_NAME) ) )  {
						if ( _sourceConnections.add( connection ) ) {
							getListeners().firePropertyChange( SOURCE_CONNECTION, null, connection );
							
							return true;
						}
						return false;
					}
					return false;
				}
			}
			else if ( connection.getTargetObject() == this ) {
				if ( !_targetConnections.contains( connection ) ) {
					
					String sourceObjectName = connection.getSourceObject().getName( );
					String targetObjectName = connection.getTargetObject().getName( );

					if ( ( !sourceObjectName.equals( EMPTY_OBJECT_NAME ) ) && ( !targetObjectName.equals( EMPTY_OBJECT_NAME ) ) ) {
						
							if ( _targetConnections.add( connection ) ){

								getListeners().firePropertyChange( TARGET_CONNECTION, null, connection );
								return true;
							}
							return false;
					}
					return false;
				}
			}
		}
		return false;
	}
	/**
	 * used to remove a connection of this SdObject with another SdObject.
	 * 
	 * @param connection
	 * @return
	 */
	public boolean removeConnection( SdConnection connection ) {	
		if ( connection.getSourceObject() == this ) {
			if ( _sourceConnections.contains( connection ) ) {
				if ( _sourceConnections.remove( connection ) ) {
					getListeners().firePropertyChange( SOURCE_CONNECTION, null, connection );
					return true;
				}
				return false;
			}
		}
		else if ( connection.getTargetObject() == this ) {
			if ( _targetConnections.contains( connection ) ) {
				
				if ( _targetConnections.remove( connection ) ) {
				
					refreshDependencies( );
					getListeners().firePropertyChange( TARGET_CONNECTION, null, connection );
					return true;
				}
				return false;
			}
		}
		return false;
	}	
}