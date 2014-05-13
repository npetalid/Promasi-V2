package org.promasi.coredesigner.ui.dialogs;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;

import org.promasi.coredesigner.ui.dialogs.model.DependencyTableModel;
import org.promasi.coredesigner.ui.dialogs.model.GeneralDialogSettings;
import org.promasi.coredesigner.ui.dialogs.table.DependenciesTableLabelProvider;
import org.promasi.coredesigner.utilities.NumberUtils;
/**
 * 
 * @author antoxron
 *
 */
public class GeneralObjectDialog extends ApplicationWindow {
	
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text _nameText;
	private Text _initValueText;
	private Label _initValueLabel;
	private TableViewer _tableViewer;
	private Table _table;
	private TableCombo _equationList;
	private boolean _saveSettings = false;

	private GeneralDialogSettings _settings;
	/**
	 * Create the application window.
	 */
	public GeneralObjectDialog(GeneralDialogSettings settings) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_settings = settings;
	}

	public boolean saveSettings() {
		return _saveSettings;
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
		
		SashForm sashForm = new SashForm(scrolledForm.getBody(), SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(sashForm);
		formToolkit.paintBordersFor(sashForm);
		
		Section generalSection = formToolkit.createSection(sashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(generalSection);
		generalSection.setText("General");
		generalSection.setExpanded(true);
		
		Composite topContainer = new Composite(generalSection, SWT.NONE);
		formToolkit.adapt(topContainer);
		formToolkit.paintBordersFor(topContainer);
		generalSection.setClient(topContainer);
		GridLayout gl_topContainer = new GridLayout(2, false);
		gl_topContainer.marginRight = 10;
		gl_topContainer.marginLeft = 10;
		gl_topContainer.marginTop = 5;
		topContainer.setLayout(gl_topContainer);
		
		Label nameLabel = new Label(topContainer, SWT.NONE);
		nameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(nameLabel, true, true);
		nameLabel.setText("Name");
		
		_nameText = new Text(topContainer, SWT.BORDER | SWT.READ_ONLY);
		_nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_nameText, true, true);
		
		_initValueLabel = new Label(topContainer, SWT.NONE);
		formToolkit.adapt(_initValueLabel, true, true);
		_initValueLabel.setText("Initial Value");
		
		_initValueText = new Text(topContainer, SWT.BORDER);
		_initValueText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_initValueText, true, true);

		Label equationLabel = new Label(topContainer, SWT.NONE);
		formToolkit.adapt(equationLabel, true, true);
		equationLabel.setText("Equation");
		
		_equationList = new TableCombo(topContainer, SWT.BORDER | SWT.READ_ONLY);
		_equationList.defineColumns(new String[] { "Name", "Type" }, 
				new int[] { SWT.DEFAULT , SWT.DEFAULT});
		_equationList.setDisplayColumnIndex(0);
		_equationList.setShowTableHeader(true);
		_equationList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_equationList);
		formToolkit.paintBordersFor(_equationList);

		Section dependenciesSection = formToolkit.createSection(sashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(dependenciesSection);
		dependenciesSection.setText("Dependencies");
		dependenciesSection.setExpanded(true);
		
		Composite bottomContainer = formToolkit.createComposite(dependenciesSection, SWT.NONE);
		formToolkit.paintBordersFor(bottomContainer);
		dependenciesSection.setClient(bottomContainer);
		bottomContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(bottomContainer, SWT.NONE);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new TableColumnLayout());
		
	
		 _tableViewer = new TableViewer( composite, SWT.MULTI |SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER); 
	      _table = _tableViewer.getTable(); 
     	_table.setHeaderVisible(true); 
     	_table.setLinesVisible(true); 
     	
   
   	    TableColumn idColumn = new TableColumn(_table , SWT.None);
   	    idColumn.setText("A/A");
	    TableColumn nameColumn = new TableColumn(_table , SWT.None);
	    nameColumn.setText("Name");
	    TableColumn typeColumn = new TableColumn(_table , SWT.None);
	    typeColumn.setText("Type");

	    
	    
	    TableColumnLayout tableLayout = new TableColumnLayout();
	    composite.setLayout(tableLayout);
	    
	    tableLayout.setColumnData(idColumn, new ColumnWeightData( 10 ));
	    tableLayout.setColumnData(nameColumn, new ColumnWeightData( 30 ));
	    tableLayout.setColumnData(typeColumn, new ColumnWeightData( 30 ));


	    _tableViewer.setLabelProvider( new DependenciesTableLabelProvider()); 
	    _tableViewer.setContentProvider( new ArrayContentProvider()); 
		
	    scrolledForm.getToolBarManager().add(new Action("Save") {  
            public void run() {  
            	
            		if (_settings != null) {
            		
            			String selectedEquation = _equationList.getText();
            			if (selectedEquation != null) {
            				if (!selectedEquation.trim().isEmpty()) {
            					_settings.setSelectedEquation(selectedEquation);
            				}
            			}
            			
            			String initialValue = _initValueText.getText();
            			if ( ( initialValue != null) && NumberUtils.isDouble( initialValue ) ) {
 
            				_settings.setInitialValue(initialValue);
            			}
            			_saveSettings  =true;
            			scrolledForm.getShell().close();
            			
            		}
            		
            }  
        });  
	    scrolledForm.getToolBarManager().add(new Action("Cancel") {  
            public void run() {  
            	scrolledForm.getShell().close();
            		
            }  
        });  
	
		scrolledForm.updateToolBar(); 
		
		sashForm.setWeights(new int[] {1, 1});

		
		loadSettings();
		
		return container;
	}
	public void setInitialVisible(boolean state) {
		_initValueLabel.setVisible(state);
		
		_initValueText.setVisible(state);
	}
	public GeneralDialogSettings getSettings() {
		return _settings;
	}
	
	private void loadSettings() {
		
		if (_settings != null) {
				
			String objectName = _settings.getObjectName();
			String initialValue = _settings.getInitialValue();
			String selectedEquation = _settings.getSelectedEquation();
			SortedMap<String , String> equations = _settings.getEquations();
			List<DependencyTableModel> dependencies = _settings.getDependencies();
			
			if (objectName != null) {
				_nameText.setText(objectName);
			}
			if (initialValue != null) {
				_initValueText.setText(initialValue);
			}
			/// adding dependencies
			if (dependencies != null) {
				if (!dependencies.isEmpty()) {
					DependencyTableModel []dependencyArray = new DependencyTableModel[dependencies.size()];
					dependencies.toArray(dependencyArray);
					_tableViewer.setInput(dependencyArray);
				}
			}
			
			//adding the equations
			loadEquations(_equationList.getTable() , equations);
		
			if (equations != null) {
				 Set<Entry<String, String>> s=equations.entrySet();

			        // Using iterator in SortedMap 
			        Iterator<Entry<String, String>> i=s.iterator();
			//        int index = 0;
			        while(i.hasNext()) {
			        	
			            Map.Entry<String , String> m =(Map.Entry<String , String>)i.next();

			            
			            
			            String key = (String) m.getKey();
			            if (key.equals(selectedEquation)) {
			            	_equationList.setText(selectedEquation);
			            }
			       //     index++;
			        }
			}
		}
	}

	private  List<TableItem> loadEquations(Table table , SortedMap<String , String> equations) {
		List<TableItem> rowList = new ArrayList<TableItem>();
		
		if (equations != null) {
			
		}
		
		
		 Set<Entry<String, String>> s=equations.entrySet();

		 Iterator<Entry<String, String>> i=s.iterator();

	        while(i.hasNext()) {
	        	 Map.Entry<String , String> m =(Map.Entry<String , String>)i.next();

	            String key = (String) m.getKey();
	            String value=(String)m.getValue();
	            TableItem ti = new TableItem(table, SWT.NONE);
	            ti.setText(new String[] {key, value });
				rowList.add(ti);
	        }

		return rowList;
	}
	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Properties Dialog");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 450;
	    int nMinHeight = 300;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}

}
