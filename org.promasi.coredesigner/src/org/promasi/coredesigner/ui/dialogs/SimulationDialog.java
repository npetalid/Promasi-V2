package org.promasi.coredesigner.ui.dialogs;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.model.builder.IModelBuilder;
import org.promasi.coredesigner.model.builder.ModelBuilderFactory;
import org.promasi.coredesigner.model.builder.model.XmlProject;
import org.promasi.coredesigner.resources.ModelManager;
import org.promasi.coredesigner.resources.SimHandler;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableNode;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableSubNode;
import org.promasi.coredesigner.ui.dialogs.table.simulation.TableLabelProvider;
import org.promasi.coredesigner.ui.dialogs.table.simulation.TreeContentProvider;


public class SimulationDialog extends ApplicationWindow implements SelectionListener {
	
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Spinner _stepsValue;
	private Button _resetButton;
	private Button _startButton;
	
	
	private TreeViewer _treeViewer;
	private List<TreeTableNode> _tableModel;
	/**
	 * Create the application window.
	 */
	public SimulationDialog(List<TreeTableNode> tableModel) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_tableModel = tableModel;
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final ScrolledForm scrolledForm = formToolkit.createScrolledForm(container);
		formToolkit.paintBordersFor(scrolledForm);
		scrolledForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(scrolledForm.getBody(), SWT.VERTICAL);
		formToolkit.adapt(sashForm);
		formToolkit.paintBordersFor(sashForm);
		
		Section topSection = formToolkit.createSection(sashForm,  Section.TITLE_BAR);
		formToolkit.paintBordersFor(topSection);
		topSection.setText("Simulation setup");
		
		Composite topComposite = new Composite(topSection, SWT.NONE);
		formToolkit.adapt(topComposite);
		formToolkit.paintBordersFor(topComposite);
		topSection.setClient(topComposite);
		topComposite.setLayout(null);
		
		Label stepsLabel = new Label(topComposite, SWT.NONE);
		stepsLabel.setBounds(10, 15, 75, 14);
		formToolkit.adapt(stepsLabel, true, true);
		stepsLabel.setText("Set steps");
		
		_stepsValue = new Spinner(topComposite, SWT.BORDER);
		_stepsValue.setMaximum(10000);
		_stepsValue.setMinimum(1);
		_stepsValue.setIncrement(100);
		_stepsValue.setBounds(91, 10, 143, 22);
		formToolkit.adapt(_stepsValue);
		formToolkit.paintBordersFor(_stepsValue);
		
		_resetButton = new Button(topComposite, SWT.NONE);
		_resetButton.setBounds(340, 10, 94, 28);
		formToolkit.adapt(_resetButton, true, true);
		_resetButton.setText("Reset");
		_resetButton.addSelectionListener(this);
		
		_startButton = new Button(topComposite, SWT.NONE);
		_startButton.setBounds(240, 10, 94, 28);
		formToolkit.adapt(_startButton, true, true);
		_startButton.setText("Simulate");
		_startButton.addSelectionListener(this);
		
		Section bottomSection = formToolkit.createSection(sashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(bottomSection);
		bottomSection.setText("Simulation results");
		
		Composite bottomComposite = formToolkit.createComposite(bottomSection, SWT.NONE);
		formToolkit.paintBordersFor(bottomComposite);
		bottomSection.setClient(bottomComposite);
		bottomComposite.setLayout(new FillLayout());
		
		
		buildTreeViewer(bottomComposite, _treeViewer);
		
		 scrolledForm.getToolBarManager().add(new Action("Close") {  
	            public void run() {  
	            	scrolledForm.getShell().close();
	            		
	            }  
	        });  
		
			scrolledForm.updateToolBar(); 
		
		sashForm.setWeights(new int[] {84, 285});

		return container;
	}

	private void buildTreeViewer(Composite parent, TreeViewer treeViewer) {
		
		Tree statisticsTree = new Tree(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		statisticsTree.setHeaderVisible(true);
		_treeViewer = new TreeViewer(statisticsTree);
 
		TreeColumn column1 = new TreeColumn(statisticsTree, SWT.LEFT);
		statisticsTree.setLinesVisible(true);
		column1.setAlignment(SWT.LEFT);
		column1.setText("Object");
		column1.setWidth(160);
      
		TreeColumn column2 = new TreeColumn(statisticsTree, SWT.RIGHT);
		column2.setAlignment(SWT.CENTER);
		column2.setText("Type");
		column2.setWidth(80);
      
		TreeColumn column3 = new TreeColumn(statisticsTree, SWT.RIGHT);
		column3.setAlignment(SWT.CENTER);
		column3.setText("Equation");
		column3.setWidth(200);
      
		TreeColumn column4 = new TreeColumn(statisticsTree, SWT.RIGHT);
		column4.setAlignment(SWT.CENTER);
		column4.setText("Value");
		column4.setWidth(80);
 
      
		_treeViewer.setContentProvider(new TreeContentProvider());
		_treeViewer.setLabelProvider(new TableLabelProvider());
      
      
		if (_tableModel != null) {
			_treeViewer.setInput( _tableModel );
		}
		
		_treeViewer.expandAll();
		
	}
	

	
	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {		
		super.configureShell(newShell);
		newShell.setText("Simulation Dialog");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 660;
	    int nMinHeight = 500;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		if (e.getSource().equals(_startButton)) {
			
			final int value = _stepsValue.getSelection();
			
			final SimHandler simHandler = new SimHandler();
			
			
			SdProject sdProject = ModelManager.getInstance().getSdProject();
			IModelBuilder builder = ModelBuilderFactory.getInstance(ModelBuilderFactory.VISUAL_BUILDER);
			builder.setModel(sdProject);
			final XmlProject xmlProject = (XmlProject) builder.getModel();
			
			
			
			
			
			
			
			ProgressMonitorDialog resourcesProgress = new ProgressMonitorDialog(null);
			resourcesProgress.setCancelable(true);
			try {
				resourcesProgress.run(true, true, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException {
						try {
							try {
								monitor.beginTask("Simulating...", IProgressMonitor.UNKNOWN);
								simHandler.simulate(xmlProject, value);
							}
							finally {
								monitor.done();
							}
						} 
						catch (Exception e) {
							throw new InvocationTargetException(e);
						}
					}
				});
			} 
			catch (InvocationTargetException ea) {
				ea.printStackTrace();
			} 
			catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
			simHandler.populateValues(_tableModel);
			
			
			_treeViewer.refresh();
			
		}
		else if (e.getSource().equals(_resetButton)) {
			
			clearModelData(_tableModel);
			_treeViewer.refresh();
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	private void clearModelData(List<TreeTableNode> tableModel) {
		
		if (tableModel != null) {
			for (TreeTableNode node : tableModel) {
				List<TreeTableSubNode> subNodes = node.getSdObjects();
				for (TreeTableSubNode subNode : subNodes) {
					subNode.setValue("");
				}
				
			}
		}
	}

	
}
