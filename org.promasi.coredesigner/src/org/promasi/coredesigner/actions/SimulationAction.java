package org.promasi.coredesigner.actions;

import java.util.ArrayList;
import java.util.List;


import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.promasi.coredesigner.model.AbstractSdObject;

import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.model.builder.IModelBuilder;
import org.promasi.coredesigner.model.builder.ModelBuilderFactory;
import org.promasi.coredesigner.model.builder.model.XmlModel;
import org.promasi.coredesigner.model.builder.model.XmlObject;
import org.promasi.coredesigner.model.builder.model.XmlProject;
import org.promasi.coredesigner.resources.ModelManager;
import org.promasi.coredesigner.ui.dialogs.SimulationDialog;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableNode;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableSubNode;


public class SimulationAction extends SelectionAction {
	
	public static final String ID = "org.promasi.coredesigner.actions.simulateaction";

	
	
	public SimulationAction(IWorkbenchPart part) {
		super(part);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init() {
		setText( "Simulate" );
		setToolTipText( "Simulate" );
		setId( ID );
		//this.seti
	}

	@Override
	protected boolean calculateEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	private List<TreeTableSubNode> getTableSubNodes(TreeTableNode parent, List<XmlObject> xmlObjects) {
		
		List<TreeTableSubNode> results = null;
		
		if (xmlObjects != null) {
			
			results = new ArrayList<TreeTableSubNode>();
			for (XmlObject xmlObject : xmlObjects) {
				
				String type = xmlObject.getType();
				
				if ((  !type.trim().equals(AbstractSdObject.CALCULATE_OBJECT))  && (!type.trim().equals(AbstractSdObject.LOOKUP_OBJECT)) ) {
					String name = xmlObject.getName();
					
					String equation = findEquationValue(xmlObject.getEquation(), xmlObjects);
					
					TreeTableSubNode subNode = new TreeTableSubNode(parent);
					subNode.setName(name);
					subNode.setType(type);
					subNode.setEquation(equation);
					subNode.setValue("");
					results.add(subNode);
				}
				
				
			}
		}
		
		
		
		return results;
		
	}
	private String findEquationValue(String objectName, List<XmlObject> xmlObjects) {
		String results = null;
		
		if (xmlObjects != null) {
			for (XmlObject xmlObject :xmlObjects) {
				if (xmlObject.getName().equals(objectName)) {
					results = xmlObject.getCalculateString();
				}
			}
		}
		
		return results;
	}
	
	@Override
	public void run() {
		
		
		SdProject sdProject = ModelManager.getInstance().getSdProject();
		IModelBuilder builder = ModelBuilderFactory.getInstance(ModelBuilderFactory.VISUAL_BUILDER);
		builder.setModel(sdProject);
		XmlProject xmlProject = (XmlProject) builder.getModel();
		


		if (xmlProject != null) {
			List<XmlModel> xmlModels = xmlProject.getXmlModels();
			if (xmlModels != null) {
				
				List<TreeTableNode> nodes = new ArrayList<TreeTableNode>();
				for (XmlModel xmlModel : xmlModels) {
					
					String modelName = xmlModel.getName();
					List<XmlObject> xmlObjects = xmlModel.getChildren();
					
					TreeTableNode tableNode = new TreeTableNode();
					tableNode.setName(modelName);
					
					List<TreeTableSubNode> subNodes = getTableSubNodes(tableNode, xmlObjects);
					if (subNodes != null) {
						tableNode.setSdObjects(subNodes);
					}
					nodes.add(tableNode);
				}
				
				SimulationDialog window = new SimulationDialog(nodes);
				window.setBlockOnOpen(true);
				window.open();

			}
		}
	}


}
