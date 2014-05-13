package org.promasi.coredesigner.ui.dialogs;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.window.ApplicationWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphContainer;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;

import org.promasi.coredesigner.model.AbstractSdObject;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.model.SdObject;
/**
 * 
 * @author antoxron
 *
 */
public class ConnectionsDesigner extends ApplicationWindow {

	
	private List<SdModel> _sdModels;
	private Graph _graph = null;
	
	public ConnectionsDesigner(List<SdModel> sdModels) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE | SWT.CLOSE |  SWT.RESIZE) ;
		_sdModels = sdModels;
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
		_graph.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		_graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		
		
		buildModel(_graph , _sdModels);
		return container;
	}
	
	
	private GraphNode findGraphNode(HashMap<GraphContainer , List<GraphNode>> models , String modelName , String objectName) {
		GraphNode results = null;
		
		
		if ((models != null) && (modelName != null) && (objectName != null)) {
			
			 Set<Entry<GraphContainer, List<GraphNode>>> s = models.entrySet();

			 Iterator<Entry<GraphContainer, List<GraphNode> > > i = s.iterator();

		        while(i.hasNext()) {
		        	 Map.Entry<GraphContainer , List<GraphNode>> m =(Map.Entry<GraphContainer , List<GraphNode>>)i.next();

		        	 GraphContainer graphContainer = (GraphContainer) m.getKey();
		            
		        	 List<GraphNode> graphNodes =(List<GraphNode>)m.getValue();
		        	 
		        	 if ((graphContainer != null) && (graphNodes != null)) {
		        		 if (graphContainer.getText().equals(modelName)) {
		        			 for (GraphNode graphNode : graphNodes) {
		        				 if (graphNode.getText().equals(objectName)) {
		        					 results = graphNode;
		        				 }
		        			 }
		        		 }
		        	 }
		           
		        }
		}
		return results;
	}
	
	private void buildModel(Graph graph ,List<SdModel> sdModels ) {
		
		
		HashMap<GraphContainer , List<GraphNode>> models = new HashMap<GraphContainer , List<GraphNode>>();
		
	
		
		if ((graph != null) && (sdModels != null)) {
		
			
			for (SdModel sdModel : sdModels) {
				String modelName = sdModel.getName();
				GraphContainer graphModel = new GraphContainer(graph , SWT.None);
				graphModel.setText(modelName);
				graphModel.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
				
				
				
				List<SdObject> sdObjects = sdModel.getChildrenArray();
				List<GraphNode> graphNodes = new ArrayList<GraphNode>();
				if (sdObjects != null) {
					int x = 10;
					int y = 10;
					for (SdObject sdObject : sdObjects) {
						if (   sdObject.getType().equals(AbstractSdObject.INPUT_OBJECT)  ||  sdObject.getType().equals(AbstractSdObject.OUTPUT_OBJECT)  ) {
							GraphNode graphNode = new GraphNode(graphModel, SWT.NONE, sdObject.getName(), null);
							graphNode.setLocation(x, y);
							graphNodes.add(graphNode);
							
							x+=30;
							y+=20;
						}
						
					}
				}
				
				
				models.put(graphModel, graphNodes);
			}
			
			
			if (!models.isEmpty()) {
				for (SdModel sdModel : sdModels) {
					String modelName = sdModel.getName();
					
					List<SdObject> sdObjects = sdModel.getChildrenArray();
					if (sdObjects != null) {
						for (SdObject sdObject : sdObjects) {
							if (   sdObject.getType().equals(AbstractSdObject.INPUT_OBJECT)   ) {
								
								SdInput sdInput = (SdInput)sdObject;
								
							
								List<SdModelConnection> connections = sdInput.getConnections();
								if (connections != null) {
									GraphNode inputGraphNode = findGraphNode(models , modelName , sdInput.getName());
									List<GraphNode> connectionObjects = new ArrayList<GraphNode>();
									
									for (SdModelConnection modelConnection : connections) {
										String key = modelConnection.getObjectName();
							            String value=modelConnection.getModelName();
							            
							            GraphNode graphNode = findGraphNode(models  , value , key);
							            if (graphNode != null) {
							            	
							            	connectionObjects.add(graphNode);
							            }
									}
											        
								        
								     // end while
								        if (connectionObjects != null) {
								        	for (GraphNode connectionObject :connectionObjects) {
								        		new GraphConnection(graph, SWT.NONE, connectionObject, inputGraphNode);
								        	}
								        }
								        
								        
								}
							}
						}
					}
				}
			}
		
			
			//open all models
			Set<GraphContainer> s=models.keySet();

			 Iterator<GraphContainer> i=s.iterator();
		        while(i.hasNext()) {
		        	
		        	GraphContainer graphContainer = i.next();
		        	if (graphContainer != null) {
		        		graphContainer.open(true);
		        	}
		     }
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Model Connections");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 800;
	    int nMinHeight = 600;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}
}
