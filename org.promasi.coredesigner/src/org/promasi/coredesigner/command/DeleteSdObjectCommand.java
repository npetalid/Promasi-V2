package org.promasi.coredesigner.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdConnection;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.SdOutput;
import org.promasi.coredesigner.model.refactor.RemoveUtils;
import org.promasi.coredesigner.resources.ModelManager;
/**
 * Represents the command to delete objects
 * 
 * @author antoxron
 *
 */
public class DeleteSdObjectCommand extends Command {
	
	/**
	 * the sdObject to be deleted
	 */
	private SdObject _model;
	/**
	 * sdModel that contains the sdObject
	 */
	private SdObject _parentModel;
	/**
	 * 
	 */
	private List<SdConnection> sourceConnections;
	/**
	 * 
	 */
	private List<SdConnection> targetConnections;
	/**
	 * 
	 */
	private HashMap<SdObject,ArrayList<SdConnection>> sourceChildConnections = new HashMap<SdObject,ArrayList<SdConnection>>() ;
	/**
	 * 
	 */
	private HashMap<SdObject,ArrayList<SdConnection>> targetChildConnections = new HashMap<SdObject,ArrayList<SdConnection>>() ;
	
	/**
	 * 
	 * @param model
	 */
	public void setModel( Object model ) {
		
		if ( model instanceof SdObject ) {
			_model = ( SdObject )model;
			sourceConnections = getSourceConnections( _model );
			targetConnections = getTargetConnections( _model );
		}
	}
	@Override
	public void execute( ) {
		if ( ( _model != null ) && ( _parentModel != null ) ) {
			removeConnections( sourceConnections );
			removeConnections( targetConnections );
			removeChildConnections( _model );
			_parentModel.removeChild( _model );
			
			if ( _model instanceof SdOutput ) {

				List<SdModel> sdModels = ModelManager.getInstance().getSdModels();
				RemoveUtils removeUtils = new RemoveUtils();
				sdModels = removeUtils.removeSdObject( _model.getName(), _parentModel.getName(), sdModels );
				if ( sdModels != null ) {
					ModelManager.getInstance().setSdModels( sdModels );
				}
			}
		}
	}
	/**
	 * 
	 * @param model
	 */
	public void setParentModel( Object model ) {
		if ( model instanceof SdObject ) { 
			_parentModel = ( SdObject )model;
		}
	}
	@Override
	public void undo( ) {
		_parentModel.addChild( _model );
		addConnections( sourceConnections );
		addConnections( targetConnections );
		addChildConnections( _model );
	}
	/**
	 * 
	 * @param connections
	 */
	private void addConnections( List<SdConnection> connections ) {
		SdConnection connection;
		for ( Iterator<SdConnection> iter = connections.iterator(); iter.hasNext(); ) {
			connection = iter.next();			
			connection.getSourceObject().addConnection( connection );
			connection.getTargetObject().addConnection( connection );
		}
	}
	/**
	 * 
	 * @param connections
	 */
	private void removeConnections( List<SdConnection> connections ) {
		SdConnection connection;
		for ( Iterator<SdConnection> iter = connections.iterator(); iter.hasNext(); ) {
			connection = iter.next();
			connection.getSourceObject().removeConnection( connection );
			connection.getTargetObject().removeConnection( connection );
		}
	}
	/**
	 * 
	 * @param sdObject
	 */
	private void addChildConnections( SdObject sdObject ) {
		SdObject child;
		ArrayList<SdConnection> sourceConnections;
		ArrayList<SdConnection> targetConnections;
		for ( Iterator<SdObject> iter = sdObject.getChildrenArray().iterator(); iter.hasNext(); ) {
			child = iter.next();
			sourceConnections = sourceChildConnections.get( child );
			targetConnections = targetChildConnections.get( child );
			addConnections( sourceConnections );
			addConnections( targetConnections );
		}
	}
	/**
	 * 
	 * @param sdObject
	 * @return
	 */
	private ArrayList<SdConnection> getSourceConnections ( SdObject sdObject ) {
		return new ArrayList<SdConnection>( sdObject.getSourceConnectionArray() );
	}
	/**
	 * 
	 * @param sdObject
	 * @return
	 */
	private ArrayList<SdConnection> getTargetConnections( SdObject sdObject )  {
		return new ArrayList<SdConnection>( sdObject.getTargetConnectionArray() );
	}
	/**
	 * 
	 * @param sdObject
	 */
	private void removeChildConnections( SdObject sdObject ) {
		SdObject child;
		ArrayList<SdConnection> sourceConnections;
		ArrayList<SdConnection> targetConnections;
		for ( Iterator<SdObject> iter = sdObject.getChildrenArray().iterator(); iter.hasNext(); ) {
			child = iter.next();
			sourceChildConnections.put( child, getSourceConnections( child ) );
			targetChildConnections.put( child, getTargetConnections( child ) );
			sourceConnections = getSourceConnections( child );
			targetConnections = getTargetConnections( child );
			removeConnections( sourceConnections );
			removeConnections( targetConnections );
			removeChildConnections( child );			
		}
	}
}