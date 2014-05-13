package org.promasi.coredesigner.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;

import org.eclipse.ui.IWorkbenchPart;

import org.promasi.coredesigner.editpart.AbstractEditPart;
import org.promasi.coredesigner.editpart.SdConnectionPart;
import org.promasi.coredesigner.editpart.SdModelEditPart;
import org.promasi.coredesigner.model.AbstractSdObject;
import org.promasi.coredesigner.model.Dependency;
import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.SdOutput;
import org.promasi.coredesigner.model.SdStock;
import org.promasi.coredesigner.model.SdVariable;
import org.promasi.coredesigner.resources.ModelManager;
import org.promasi.coredesigner.ui.dialogs.CalculateEquationDialog;
import org.promasi.coredesigner.ui.dialogs.GeneralObjectDialog;
import org.promasi.coredesigner.ui.dialogs.LookupEquationDialog;
import org.promasi.coredesigner.ui.dialogs.ObjectConnectionDialog;
import org.promasi.coredesigner.ui.dialogs.model.ConnectionDialogSettings;
import org.promasi.coredesigner.ui.dialogs.model.ConnectionTableModel;
import org.promasi.coredesigner.ui.dialogs.model.DependencyTableModel;
import org.promasi.coredesigner.ui.dialogs.model.GeneralDialogSettings;
/**
 * This class is used to display the window object
 * 
 * @author antoxron
 *
 */
public class SdObjectPropertiesAction extends SelectionAction  {

	/**
	 * 
	 */
	public static final String ID = "org.promasi.coredesigner.actions.objectpropertiesaction";
	
	public SdObjectPropertiesAction( IWorkbenchPart part ) {
		super( part );
	}

	@Override
	protected void init() {
		setText( "Properies" );
		setToolTipText( "properties" );
		setId( ID );
	}
	

	@Override
	protected boolean calculateEnabled( ) {
		boolean enabled = false;
			
		if ( getSelectedObjects().size() > 0) {
			Object object = getSelectedObjects().get( 0 );
			
			if ( !( object instanceof SdConnectionPart ) ) {
				AbstractEditPart editPart = ( AbstractEditPart )getSelectedObjects().get( 0 );
				if ( !( editPart instanceof SdModelEditPart ) ) {

					enabled = true;
				}
			}
		}
		/*
		Object object = getSelectedObjects().get( 0 );
		
		if ( !( object instanceof SdConnectionPart ) ) {
			AbstractEditPart editPart = ( AbstractEditPart )getSelectedObjects().get( 0 );
			if ( !( editPart instanceof SdModelEditPart ) ) {

				enabled = true;
			}
		}*/
		
		
		return enabled;
	}
    
	
	@SuppressWarnings("unchecked")
	public Command createObjectPropertiesCommand( SdObject sdObject ) {
		Request propertiesRequest = new Request( ID );
		
		
		HashMap<String , SdObject> reqData = new HashMap<String , SdObject>();
		reqData.put( ID, sdObject );
		propertiesRequest.setExtendedData( reqData );
			
		List<SelectionAction> selectedObjects = getSelectedObjects();
		
		
		Command cmd = null;
		if ( ( selectedObjects != null ) && ( !selectedObjects.isEmpty() ) ) {
			EditPart object = ( EditPart ) getSelectedObjects().get( 0 );
			cmd = object.getCommand( propertiesRequest );
		}
		return cmd;
	}
	@Override
	public void run() {
		
		
		SdObject sdObject = getSelectedObject();
		
		if ( sdObject instanceof SdCalculate ) {
			SdCalculate sdCalculate = (SdCalculate) sdObject;
			SdModel sdModel = (SdModel)sdObject.getParent();
			if (sdModel!= null) {
				List<String> variables = new ArrayList<String>();
				
				List<SdObject> children = sdModel.getChildrenArray();
				if (children != null) {
					
					for (SdObject child : children) {
						
						if ( (!(child instanceof SdCalculate)) && (!(child instanceof SdLookup))   ) {
							
							String objectName = child.getName();
							variables.add(objectName);
						}
					}
					CalculateEquationDialog dialog = new CalculateEquationDialog(variables, sdCalculate.getCalculateString());
					dialog.setBlockOnOpen(true);
					dialog.open();
					if (dialog.saveSettings()) {
						String calculateString  = dialog.getCalculatedString();
						if (calculateString != null) {
							sdCalculate.setCalculateString(calculateString);
							execute(createObjectPropertiesCommand(sdCalculate));
						}
					}
				}
			}
			
		}
		else if (sdObject instanceof SdLookup) {
			SdLookup sdLookup = (SdLookup) sdObject;
			if (sdLookup != null) {
				LookupEquationDialog dialog = new LookupEquationDialog(sdLookup.getLookupPoints());
				dialog.setBlockOnOpen(true);
				dialog.open();
				if (dialog.saveSettings()) {
					TreeMap<Double,Double> xyPoints = dialog.getXYPoints();
					if (xyPoints != null) {
						sdLookup.setLookupPoints(xyPoints);
						execute(createObjectPropertiesCommand(sdLookup));
					}
				}
			}
		}
		else if ( (sdObject instanceof SdFlow) || (sdObject instanceof SdStock) 
				|| (sdObject instanceof SdVariable)   || (sdObject instanceof SdOutput)) {
			
			
			String objectName = sdObject.getName();
			double initialValue = 0.0;
			String selectedEquation = null;
			
			
			if (sdObject.getType().equals(AbstractSdObject.STOCK_OBJECT)) {
				SdStock sdStock = (SdStock)sdObject;
				initialValue = sdStock.getInitialValue();
				selectedEquation = sdStock.getEquation();
			}
			else if (sdObject.getType().equals(AbstractSdObject.OUTPUT_OBJECT)) {
				SdOutput sdOutput = (SdOutput)sdObject;
				
				selectedEquation = sdOutput.getEquation();
			}
			else {
				if (sdObject.getType().equals(AbstractSdObject.VARIABLE_OBJECT)) {
					SdVariable sdVariable = (SdVariable)sdObject;
					selectedEquation = sdVariable.getEquation();
				}
				else {
					SdFlow sdFlow = (SdFlow)sdObject;
					selectedEquation = sdFlow.getEquation();
				}
			}
			List<DependencyTableModel> dependecyTableModel = new ArrayList<DependencyTableModel>();
			
			List<Dependency> depedencies = sdObject.getDependencies();
			if (depedencies != null) {
				
				for (int index = 0; index <  depedencies.size(); index++) {
					DependencyTableModel tableModel = new DependencyTableModel();
					tableModel.setId(index + 1);
					tableModel.setName(  depedencies.get(index).getName() );
					tableModel.setType(  depedencies.get(index).getType()  );
					dependecyTableModel.add(tableModel);
				}
			}
			SortedMap<String , String> equations = new TreeMap<String, String>();
			List<SdObject> children = sdObject.getParent().getChildrenArray();
			
			if (children != null) {
				for (SdObject child : children) {
					if ( (child instanceof SdCalculate) || (child instanceof SdLookup)) {
						equations.put(child.getName(), child.getType());
					}
				}
			}
			GeneralDialogSettings settings = new GeneralDialogSettings();
			settings.setDependencies(dependecyTableModel);
			settings.setEquations(equations);
			settings.setInitialValue(    String.valueOf(initialValue));
			settings.setObjectName(objectName);
			if (selectedEquation != null) {
				settings.setSelectedEquation(selectedEquation);
			}
			
			GeneralObjectDialog dialog = new GeneralObjectDialog(settings);
			dialog.setBlockOnOpen(true);
			dialog.open();
			if (dialog.saveSettings()) {
				settings = dialog.getSettings();
				
				selectedEquation = settings.getSelectedEquation();
				
				
				if (sdObject.getType().equals(AbstractSdObject.FLOW_OBJECT)) {
					SdFlow sdFlow = (SdFlow)sdObject;
					sdFlow.setEquation(selectedEquation);
					execute(createObjectPropertiesCommand(sdFlow));
				}
				if (sdObject.getType().equals(AbstractSdObject.VARIABLE_OBJECT)) {
					SdVariable sdVariable = (SdVariable)sdObject;
					sdVariable.setEquation(selectedEquation);
					execute(createObjectPropertiesCommand(sdVariable));
				}
				if (sdObject.getType().equals(AbstractSdObject.OUTPUT_OBJECT)) {
					SdOutput sdOutput = (SdOutput)sdObject;
					sdOutput.setEquation(selectedEquation);
					execute(createObjectPropertiesCommand(sdOutput));
				}
				if (sdObject.getType().equals(AbstractSdObject.STOCK_OBJECT)) {
					SdStock sdStock = (SdStock)sdObject;
					sdStock.setEquation(selectedEquation);
					
					initialValue = Double.valueOf(settings.getInitialValue());	
					sdStock.setInitialValue(initialValue);
			
					execute(createObjectPropertiesCommand(sdStock));
				}
			}
			
			
		}
		else if ( sdObject instanceof SdInput ) {
			
			SdInput sdInput = (SdInput) sdObject;
		
			String objectName = sdInput.getName();
		
			String  equation = sdInput.getEquation();
		
			List<DependencyTableModel> dependecyTableModel = new ArrayList<DependencyTableModel>();
			List<Dependency> depedencies = sdObject.getDependencies();
			if (depedencies != null) {
				
				for (int index = 0; index <  depedencies.size(); index++) {
					DependencyTableModel tableModel = new DependencyTableModel();
					tableModel.setId(index + 1);
					tableModel.setName(  depedencies.get(index).getName() );
					tableModel.setType(  depedencies.get(index).getType()  );
					dependecyTableModel.add(tableModel);
				}
			}
			
			
			List<ConnectionTableModel> connections = new ArrayList<ConnectionTableModel>();
			List<SdModelConnection> inputConnections = sdInput.getConnections();
			
			int index = 1;
			for (SdModelConnection sdModelConnection : inputConnections) {
				
				String modelName = sdModelConnection.getModelName();
				String modelObjectName = sdModelConnection.getObjectName();
				
				 ConnectionTableModel tableModel = new ConnectionTableModel();
		            tableModel.setId(index);
		            tableModel.setName(modelObjectName);
		            tableModel.setTask(modelName);
		            connections.add(tableModel);
		            index++;
			}
		
	        List<SdModelConnection> connectedObjects = new ArrayList<SdModelConnection>();  
			List<SdModel> sdModels = ModelManager.getInstance().getSdModels();
			if (sdModels != null) {
				for (SdModel sdModel : sdModels) {
					if (!sdModel.getName().equals(sdInput.getParent().getName())) {
						List<SdObject> sdObjects = sdModel.getChildrenArray();
						if (sdObjects != null) {

							for (SdObject item : sdObjects) {
								if (item.getType().equals(AbstractSdObject.OUTPUT_OBJECT)) {
									String objecName = item.getName();
									String task = sdModel.getName();
									SdModelConnection connection = new SdModelConnection();
									connection.setModelName(task);
									connection.setObjectName(objecName);
									
									connectedObjects.add(connection);
									
							
								}
							}
							
						}
					}
				}
			}
			SortedMap<String , String> equations = new TreeMap<String, String>();
			List<SdObject> children = sdObject.getParent().getChildrenArray();
			
			if (children != null) {
				for (SdObject child : children) {
					if ( (child instanceof SdCalculate) || (child instanceof SdLookup)) {
						equations.put(child.getName(), child.getType());
					}
				}
			}
			ConnectionDialogSettings settings = new ConnectionDialogSettings();
			settings.setConnectionObjects(connectedObjects);
			settings.setConnections(connections);
			settings.setDependencies(dependecyTableModel);
			settings.setEquations(equations);
			settings.setObjectName(objectName);
			settings.setSelectedEquation(equation);
			
			ObjectConnectionDialog dialog = new ObjectConnectionDialog(settings);
			dialog.setBlockOnOpen(true);
			
		
			dialog.open();

			if (dialog.saveSettings()) {
				settings = dialog.getSettings();
				String selectedEquation = settings.getSelectedEquation();
				sdInput.setEquation(selectedEquation);
				connections = settings.getConnections();
				
				inputConnections = new ArrayList<SdModelConnection>();
				if (connections != null) {
					for (ConnectionTableModel tableModel : connections) {
						
						SdModelConnection modelConnection = new SdModelConnection();
						modelConnection.setModelName(tableModel.getTask());
						modelConnection.setObjectName(tableModel.getName());
						inputConnections.add(modelConnection);
						
					}
					sdInput.setConnections(inputConnections);
				}
				execute(createObjectPropertiesCommand(sdInput));
			}
			
			
		}
	}

	@SuppressWarnings({  "unchecked" })
	private SdObject getSelectedObject() {
		List<SdObject> objects = getSelectedObjects();
		if (objects.isEmpty()) {
			return null;
		}
		if (!(objects.get(0) instanceof EditPart)) {
			return null;
		}
		EditPart part = (EditPart) objects.get(0);
		return (SdObject)part.getModel();
	}
}