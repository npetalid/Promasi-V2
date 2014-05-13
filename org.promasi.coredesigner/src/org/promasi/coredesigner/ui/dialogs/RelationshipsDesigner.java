package org.promasi.coredesigner.ui.dialogs;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;

import org.eclipse.jface.window.ApplicationWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.Filter;
import org.eclipse.zest.layouts.LayoutItem;
import org.eclipse.zest.layouts.LayoutStyles;

import org.promasi.coredesigner.model.AbstractSdObject;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.SdProject;
/**
 * 
 * @author antoxron
 *
 */
public class RelationshipsDesigner extends ApplicationWindow {

	
	private SdProject _sdProject;
	private Graph _graph = null;
	
	public RelationshipsDesigner(SdProject sdProject) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE | SWT.CLOSE |  SWT.RESIZE) ;
		_sdProject = sdProject;
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		
		_graph = new Graph(container , SWT.None);
		TreeLayoutAlgorithm treeLayoutAlgorithm = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		Filter filter = new Filter() {
			public boolean isObjectFiltered(LayoutItem item) {

				Object object = item.getGraphData();
				if  (object instanceof GraphConnection ) {
					GraphConnection connection = (GraphConnection) object;
					if ( connection.getData() != null && connection.getData() instanceof Boolean ) {
						
						return ((Boolean)connection.getData()).booleanValue();
					}
					return true;
				}
				return false;
			}			
		};
		treeLayoutAlgorithm.setFilter(filter);
		_graph.setLayoutAlgorithm(treeLayoutAlgorithm, true);
		
		buildModel(_graph , _sdProject);
		return container;
	}	
	private void buildModel(Graph graph , SdProject sdProject) {

		if ((graph != null) && (sdProject != null)) {
			GraphNode root = new GraphNode(graph, SWT.NONE, sdProject.getName());
			List<GraphNode> models = new ArrayList<GraphNode>();
			
			List<SdModel> sdModels = sdProject.getModels();
			if (sdModels != null) {
				for (SdModel sdModel : sdModels) {
					GraphNode graphModel = new GraphNode(graph, SWT.NONE, sdModel.getName());
					models.add(graphModel);
				}
				
			}
			for (GraphNode model : models) {
				GraphConnection connection = new GraphConnection(graph, SWT.NONE, root, model);
				connection.setData(Boolean.FALSE);
			}
			if (sdModels != null) {
				for (SdModel sdModel : sdModels) {
					
					List<String> connections = findConnections(sdModels , sdModel.getName());
					if (connections != null) {
						for (String connection : connections) {
							GraphNode sourceConnection = findGraphNode(models , connection);
							GraphNode targetConnection = findGraphNode(models , sdModel.getName());
							GraphConnection conn = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, targetConnection, sourceConnection);
							conn.setLineColor(ColorConstants.red);
							conn.setLineWidth(3);
						}
					}	
				}	
			}
		}
	}
	private GraphNode findGraphNode(List<GraphNode> nodes , String nodeName) {
		GraphNode graphNode = null;
		
		if ((nodes != null) && (nodeName != null)) {
			for (GraphNode node : nodes) {
				if (node.getText().equals(nodeName)) {
					graphNode = node;
				}
			}
		}
		return graphNode;
	}
	private List<String> findConnections(List<SdModel> sdModels , String modelName) {
		List<String> results = null;
		
		
		if ((sdModels != null) && (modelName != null)) {
			
			results = new ArrayList<String>();
			
			for (SdModel sdModel : sdModels) {
				if (sdModel.getName() != modelName) {
					List<SdObject> children = sdModel.getChildrenArray();
					if (children != null) {
						for (SdObject child : children) {
							if (child.getType().equals(AbstractSdObject.INPUT_OBJECT)) {
								SdInput sdInput = (SdInput)child;
								List<SdModelConnection> connections = sdInput.getConnections();
								if (connections != null) {
									
									
									for (SdModelConnection modelConnection : connections) {
										
										String value=(String)modelConnection.getModelName();
							            if (value != null) {
							            	if (value.equals(modelName)) {
							            		results.add(sdModel.getName());
							            	}
							            }
									}
								
								}
							}
						}
					}
				}
			}
		}
		return results;
	}
	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Project Relationships");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 800;
	    int nMinHeight = 600;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}
}