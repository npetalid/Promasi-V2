package org.promasi.ui.exportwizard.editor.dialog;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;



import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.promasi.ui.exportwizard.editor.dialog.model.ProjectSettings;
import org.promasi.ui.exportwizard.editor.dialog.table.ProjectTaskLabelProvider;
import org.promasi.ui.exportwizard.editor.dialog.table.model.ProjectTaskTableModel;
import org.promasi.ui.exportwizard.editor.utils.NumberValidator;
/**
 * 
 * @author antoxron
 *
 */
public class ProjectSettingsDialog extends ApplicationWindow {
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private ScrolledForm _mainForm;
	
	private Text _projectNameText;
	private Text _projectDescriptionText;
	private Text _projectDurationText;
	private Text _projectPriceText;
	private Text _projectDiffText;

	
	
	private List<ProjectTaskTableModel> _tableTasks;
	
	private TableViewer _tableViewer;
	private Table _table;
	
	private ProjectSettings _settings;
	
	private boolean _saveSettings = false;

	public boolean saveSettings() {
		return _saveSettings;
	}
	/**
	 * Create the application window.
	 */
	public ProjectSettingsDialog( ProjectSettings settings ) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_settings = settings;
		
		_tableTasks = new ArrayList<ProjectTaskTableModel>();
	}
	public ProjectSettings getProjectSettings() {
		return _settings;
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		_mainForm = formToolkit.createScrolledForm(container);
		formToolkit.paintBordersFor(_mainForm);
		_mainForm.setText("Project settings");
		_mainForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(_mainForm.getBody(), SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(sashForm);
		formToolkit.paintBordersFor(sashForm);
		
		Section _topSection = formToolkit.createSection(sashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(_topSection);
		_topSection.setText("Complete the follow fields");
		
		Composite _topComposite = new Composite(_topSection, SWT.NONE);
		formToolkit.adapt(_topComposite);
		formToolkit.paintBordersFor(_topComposite);
		_topSection.setClient(_topComposite);
		_topComposite.setLayout(new GridLayout(2, false));
		
		Label projectNameLabel = new Label(_topComposite, SWT.NONE);
		formToolkit.adapt(projectNameLabel, true, true);
		projectNameLabel.setText("Name");
		
		_projectNameText = new Text(_topComposite, SWT.BORDER | SWT.READ_ONLY );
		_projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_projectNameText, true, true);
		
		Label projectDescriptionLabel = new Label(_topComposite, SWT.NONE);
		formToolkit.adapt(projectDescriptionLabel, true, true);
		projectDescriptionLabel.setText("Description");
		
		_projectDescriptionText = new Text(_topComposite, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd__projectDescriptionText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd__projectDescriptionText.heightHint = 113;
		_projectDescriptionText.setLayoutData(gd__projectDescriptionText);
		formToolkit.adapt(_projectDescriptionText, true, true);
		
		Label projectDurationLabel = new Label(_topComposite, SWT.NONE);
		formToolkit.adapt(projectDurationLabel, true, true);
		projectDurationLabel.setText("Duration");
		
		_projectDurationText = new Text(_topComposite, SWT.BORDER);
		_projectDurationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_projectDurationText, true, true);
		
		Label projectPriceLabel = new Label(_topComposite, SWT.NONE);
		formToolkit.adapt(projectPriceLabel, true, true);
		projectPriceLabel.setText("Price");
		
		_projectPriceText = new Text(_topComposite, SWT.BORDER);
		_projectPriceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_projectPriceText, true, true);
		
		Label projectDiffLabel = new Label(_topComposite, SWT.NONE);
		formToolkit.adapt(projectDiffLabel, true, true);
		projectDiffLabel.setText("Difficulty Level");
		
		_projectDiffText = new Text(_topComposite, SWT.BORDER);
		_projectDiffText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_projectDiffText, true, true);
		
		Section _bottomSection = formToolkit.createSection(sashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(_bottomSection);
		_bottomSection.setText("Set the equation for each model");
		
		Composite _bottomComposite = new Composite(_bottomSection, SWT.NONE);
		formToolkit.adapt(_bottomComposite);
		formToolkit.paintBordersFor(_bottomComposite);
		_bottomSection.setClient(_bottomComposite);
		
		
		
		 _tableViewer = new TableViewer( _bottomComposite, SWT.MULTI |SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER); 
	      _table = _tableViewer.getTable(); 
    	_table.setHeaderVisible(true); 
    	_table.setLinesVisible(true); 
    	
 
	    TableColumn modelNameColumn = new TableColumn(_table , SWT.None);
	    modelNameColumn.setText("Model");
	    TableColumn equationColumn = new TableColumn(_table , SWT.None);
	    equationColumn.setText("Equation");

	    
	    
	    TableColumnLayout tableLayout = new TableColumnLayout();
	    _bottomComposite.setLayout(tableLayout);

	    tableLayout.setColumnData(modelNameColumn, new ColumnWeightData( 30 ));
	    tableLayout.setColumnData(equationColumn, new ColumnWeightData( 30 ));
	    

	    _tableViewer.setLabelProvider( new ProjectTaskLabelProvider()); 
	    _tableViewer.setContentProvider( new ArrayContentProvider()); 

	    
	    _table.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
					
					ProjectTaskTableModel model = (ProjectTaskTableModel) _table.getSelection()[0].getData();
					if (model != null) {
						
						List<String> variables = model.getVariables();
						String equation = model.getEquation();
						CalculateEquationDialog dialog = new CalculateEquationDialog(variables , equation);
						dialog.setBlockOnOpen(true);
						dialog.open();
						
						if (dialog.saveSettings()) {
							String selectedEquation = dialog.getCalculatedString();
							_tableTasks.remove(model);
							model.setEquation(selectedEquation);
							_tableTasks.add(model);
							_tableViewer.setInput(_tableTasks);
						}
					}
						
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    
	    sashForm.setWeights(new int[] {285, 140});


		
	    _mainForm.getToolBarManager().add(new Action("Save") {  
            public void run() {  
            	
    			String description = _projectDescriptionText.getText();
    			String projectDurationStr = _projectDurationText.getText();
    			String projectPriceStr = _projectPriceText.getText();
    			String diffLevelStr = _projectDiffText.getText();
    			
    			if ( (!description.isEmpty()) && (!projectDurationStr.isEmpty()) 
    					&& (!projectPriceStr.isEmpty()) && (!diffLevelStr.isEmpty()) ) {
    				if ( (NumberValidator.isInteger(projectDurationStr)) && NumberValidator.isDouble(projectPriceStr) 
    						&& (NumberValidator.isDouble(diffLevelStr)) ) {
    					
    					int projectDuration = Integer.valueOf(projectDurationStr);
    					double projectPrice = Double.valueOf(projectPriceStr);
    					double diffLevel = Double.valueOf(diffLevelStr);
    					
    					@SuppressWarnings("unchecked")
						List<ProjectTaskTableModel> modelList = (List<ProjectTaskTableModel>) _tableViewer.getInput();
    					
    					if (modelList != null) {
    						
    						boolean isDataCorrect = true;
    						for (ProjectTaskTableModel model : modelList) { 
  
    							if (model.getEquation().trim().isEmpty()) {
    								isDataCorrect = false;
    							}
    						}
    				
    						if (isDataCorrect) {
    							_settings.setDescription(description);
    							_settings.setDifficultyLevel(diffLevel);
    							_settings.setProjectDuration(projectDuration);
    							_settings.setProjectPrice(projectPrice);
    							_settings.setTasks(modelList);
    							_saveSettings = true;
    							_mainForm.getShell().close();
    						}
    					}
    				}
    			}
    			
            }  
        });  
	    _mainForm.getToolBarManager().add(new Action("Cancel") {  
            public void run() {  
            
            	_mainForm.getShell().close();
            		
            }  
        });  
	
	    _mainForm.updateToolBar();
		loadSettings();
		return container;
	}

	private void loadSettings() {
		
		if (_settings != null) {
			
			String projectName = _settings.getProjectName();
			String description = _settings.getDescription();
			int projectDuration = _settings.getProjectDuration();
			double projectPrice = _settings.getProjectPrice();
			double diffLevel = _settings.getDifficultyLevel();
			_tableTasks = _settings.getTasks();
			
			
			if ( !projectName.trim().isEmpty() ) {
				_projectNameText.setText(projectName);
			}
			if (!description.trim().isEmpty()) {
				_projectDescriptionText.setText(description);
			}
			if (projectDuration >= 0) {
				String projectDurationStr = String.valueOf(projectDuration);
				_projectDurationText.setText(projectDurationStr);
			}
			if ( projectPrice >= 0 ) {
				String projectPriceStr = String.valueOf(projectPrice);
				_projectPriceText.setText(projectPriceStr);
			}
			if ( diffLevel >= 0 ) {
				String diffLevelStr = String.valueOf(diffLevel);
				_projectDiffText.setText(diffLevelStr);
			}
			
			if (_tableTasks != null) {
				if (!_tableTasks.isEmpty()) {
					_tableViewer.setInput(_tableTasks);
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
		newShell.setText("Input Properties Dialog");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 553;
	    int nMinHeight = 447;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}
}