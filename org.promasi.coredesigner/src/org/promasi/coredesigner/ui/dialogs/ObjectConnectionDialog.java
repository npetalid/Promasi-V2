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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;

import org.eclipse.nebula.widgets.tablecombo.TableCombo;

import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.resources.ImageUtilities;
import org.promasi.coredesigner.ui.dialogs.model.ConnectionDialogSettings;
import org.promasi.coredesigner.ui.dialogs.model.DependencyTableModel;
import org.promasi.coredesigner.ui.dialogs.model.ConnectionTableModel;
import org.promasi.coredesigner.ui.dialogs.table.ConnectionsTableLabelProvider;
import org.promasi.coredesigner.ui.dialogs.table.DependenciesTableLabelProvider;
/**
 * 
 * @author antoxron
 *
 */
public class ObjectConnectionDialog extends ApplicationWindow implements SelectionListener {
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private boolean _saveSettings = false;
	private Text _nameText;
	private Table _connectionsTable;
	private TableViewer _connectionsTableViewer;

	private TableCombo _equationList;
	
	private Button _addConnectionButton;
	private Button _deleteConnectionButton;
	
	private TableViewer _dependencyTableViewer;
	private Table _dependencyTable;
	
	private ConnectionDialogSettings _settings;
	
	List<SdModelConnection>  _connectionObjects;
	
	private Composite _mainContainer;
	/**
	 * Create the application window.
	 */
	public ObjectConnectionDialog(ConnectionDialogSettings settings) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_settings = settings;
	}

	public ConnectionDialogSettings getSettings() {
		return _settings;
	}
	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		_mainContainer = new Composite(parent, SWT.None);
		_mainContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final ScrolledForm mainScrolledForm = formToolkit.createScrolledForm(_mainContainer);
		formToolkit.paintBordersFor(mainScrolledForm);
		mainScrolledForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		CTabFolder mainTabFolder = new CTabFolder(mainScrolledForm.getBody(), SWT.BORDER);
		mainTabFolder.setTabPosition(SWT.BOTTOM);
		formToolkit.adapt(mainTabFolder);
		formToolkit.paintBordersFor(mainTabFolder);
		mainTabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		mainTabFolder.setFocus();
		CTabItem generalTabItem = new CTabItem(mainTabFolder, SWT.NONE);
		generalTabItem.setText("General");
		
		Composite generalContainer = new Composite(mainTabFolder, SWT.NONE);
		generalContainer.setBackground(ImageUtilities.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		generalTabItem.setControl(generalContainer);
		formToolkit.paintBordersFor(generalContainer);
		generalContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm generalSashForm = new SashForm(generalContainer, SWT.NONE);
		generalSashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(generalSashForm);
		formToolkit.paintBordersFor(generalSashForm);
		
		Composite generalTopContainer = new Composite(generalSashForm, SWT.NONE);
		formToolkit.adapt(generalTopContainer);
		formToolkit.paintBordersFor(generalTopContainer);
		GridLayout gl_generalTopContainer = new GridLayout(2, false);
		gl_generalTopContainer.marginTop = 20;
		gl_generalTopContainer.marginLeft = 10;
		generalTopContainer.setLayout(gl_generalTopContainer);
		
		Label nameLabel = new Label(generalTopContainer, SWT.READ_ONLY);
		// new
		nameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		//
		formToolkit.adapt(nameLabel, true, true);
		nameLabel.setText("Name");
		
		_nameText = new Text(generalTopContainer, SWT.BORDER);
		_nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_nameText, true, true);
		
		Label equationsLabel = new Label(generalTopContainer, SWT.NONE);
		formToolkit.adapt(equationsLabel, true, true);
		equationsLabel.setText("Equation");
		
		_equationList = new TableCombo(generalTopContainer, SWT.BORDER | SWT.READ_ONLY);
		_equationList.defineColumns(new String[] { "Name", "Type" }, 
				new int[] { SWT.DEFAULT , SWT.DEFAULT });
		_equationList.setDisplayColumnIndex(0);
		_equationList.setShowTableHeader(true);
		//_equationsList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		_equationList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_equationList);
		formToolkit.paintBordersFor(_equationList);
		
		Composite generalBottomContainer = new Composite(generalSashForm, SWT.NONE);
		formToolkit.adapt(generalBottomContainer);
		formToolkit.paintBordersFor(generalBottomContainer);
		generalBottomContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//////////////////////////////////////////////////
		_dependencyTableViewer = new TableViewer( generalBottomContainer, SWT.MULTI |SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER); 
		_dependencyTable = _dependencyTableViewer.getTable(); 
		_dependencyTable.setHeaderVisible(true); 
		_dependencyTable.setLinesVisible(true); 
    	
	
  	    TableColumn idColumn = new TableColumn(_dependencyTable , SWT.None);
  	    idColumn.setText("A/A");
	    TableColumn nameColumn = new TableColumn(_dependencyTable , SWT.None);
	    nameColumn.setText("Name");
	    TableColumn typeColumn = new TableColumn(_dependencyTable , SWT.None);
	    typeColumn.setText("Type");

	    
	    
	    TableColumnLayout tableLayout = new TableColumnLayout();
	    generalBottomContainer.setLayout(tableLayout);
	    
	    tableLayout.setColumnData(idColumn, new ColumnWeightData( 10 ));
	    tableLayout.setColumnData(nameColumn, new ColumnWeightData( 30 ));
	    tableLayout.setColumnData(typeColumn, new ColumnWeightData( 30 ));


	    _dependencyTableViewer.setLabelProvider( new DependenciesTableLabelProvider()); 
	    _dependencyTableViewer.setContentProvider( new ArrayContentProvider()); 
		
		/////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		
		
		
		
		
		generalSashForm.setWeights(new int[] {82, 123});
		
		CTabItem connectionsTabItem = new CTabItem(mainTabFolder, SWT.NONE);
		connectionsTabItem.setText("Connections");
		
		Composite connectionsContainer = new Composite(mainTabFolder, SWT.NONE);
		connectionsContainer.setBackground(ImageUtilities.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		connectionsTabItem.setControl(connectionsContainer);
		formToolkit.paintBordersFor(connectionsContainer);
		connectionsContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm connectionsSashForm = new SashForm(connectionsContainer, SWT.NONE);
		connectionsSashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(connectionsSashForm);
		formToolkit.paintBordersFor(connectionsSashForm);
		
		Composite connectionsTopContainer = new Composite(connectionsSashForm, SWT.NONE);
		formToolkit.adapt(connectionsTopContainer);
		formToolkit.paintBordersFor(connectionsTopContainer);
		connectionsTopContainer.setLayout(new GridLayout(2, false));
		
		
		_addConnectionButton = new Button(connectionsTopContainer, SWT.NONE);
		GridData gd__addConnectionButton = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd__addConnectionButton.heightHint = 20;
		gd__addConnectionButton.widthHint = 54;
		_addConnectionButton.setLayoutData(gd__addConnectionButton);
		formToolkit.adapt(_addConnectionButton, true, true);
		_addConnectionButton.setText("Add");
		_addConnectionButton.addSelectionListener(this);
		
		_deleteConnectionButton = new Button(connectionsTopContainer, SWT.NONE);
		GridData gd__deleteConnectionButton = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd__deleteConnectionButton.heightHint = 22;
		gd__deleteConnectionButton.widthHint = 51;
		_deleteConnectionButton.setLayoutData(gd__deleteConnectionButton);
		formToolkit.adapt(_deleteConnectionButton, true, true);
		_deleteConnectionButton.setText("Delete");
		_deleteConnectionButton.addSelectionListener(this);
		
		Composite connectionsBottomContainer = new Composite(connectionsSashForm, SWT.NONE);
		formToolkit.adapt(connectionsBottomContainer);
		formToolkit.paintBordersFor(connectionsBottomContainer);
		connectionsBottomContainer.setLayout(new FillLayout(SWT.HORIZONTAL));

	
		_connectionsTableViewer = new TableViewer( connectionsBottomContainer, SWT.MULTI |SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER); 
		_connectionsTable = _connectionsTableViewer.getTable(); 
		_connectionsTable.setHeaderVisible(true); 
		_connectionsTable.setLinesVisible(true); 
    	
  
  	    idColumn = new TableColumn(_connectionsTable , SWT.None);
  	    idColumn.setText("A/A");
	    nameColumn = new TableColumn(_connectionsTable , SWT.None);
	    nameColumn.setText("Name");
	    TableColumn taskColumn = new TableColumn(_connectionsTable , SWT.None);
	    taskColumn.setText("Task");
	    
	    
	    tableLayout = new TableColumnLayout();
	    connectionsBottomContainer.setLayout(tableLayout);
	    
	    tableLayout.setColumnData(idColumn, new ColumnWeightData( 10 ));
	    tableLayout.setColumnData(nameColumn, new ColumnWeightData( 30 ));
	    tableLayout.setColumnData(taskColumn, new ColumnWeightData( 30 ));


	    _connectionsTableViewer.setLabelProvider( new ConnectionsTableLabelProvider()); 
	    _connectionsTableViewer.setContentProvider( new ArrayContentProvider()); 
		
		/////////////////////////////////////////////////////////////////////////////////	
		
		
	    mainScrolledForm.getToolBarManager().add(new Action("Save") {  
            public void run() {  
            	
            		if (_settings != null) {
            			
            			
            			String selectedEquation = _equationList.getText();
            			if (selectedEquation != null) {
            				if (!selectedEquation.trim().isEmpty()) {
            					_settings.setSelectedEquation(selectedEquation);
            				}
            			}
            			
            			_saveSettings  =true;
            			mainScrolledForm.getShell().close();
            		}
            }  
        });  
	    
	    mainScrolledForm.getToolBarManager().add(new Action("Cancel") {  
            public void run() {  
            	mainScrolledForm.getShell().close();
            		
            }  
        });  
		
	    mainScrolledForm.updateToolBar(); 
		
		
		
		
		
		
		formToolkit.paintBordersFor(_connectionsTable);
		
		
		
		
		
		
		
		connectionsSashForm.setWeights(new int[] {35, 170});

		loadSettings();
		return _mainContainer;
	}
	public boolean saveSettings() {
		return _saveSettings;
	}
	private void loadSettings() {
		
		
		String objectName = _settings.getObjectName();
		String selectedEquation = _settings.getSelectedEquation();
		SortedMap<String , String> equations = _settings.getEquations();
		List<DependencyTableModel> dependencies = _settings.getDependencies();
		List<ConnectionTableModel> connections = _settings.getConnections();
		_connectionObjects = _settings.getConnectionObjects();
		
		if (objectName != null) {
			_nameText.setText(objectName);
		}
		/// adding dependencies
		if (dependencies != null) {
			if (!dependencies.isEmpty()) {
				DependencyTableModel []dependencyArray = new DependencyTableModel[dependencies.size()];
				dependencies.toArray(dependencyArray);
				_dependencyTableViewer.setInput(dependencyArray);
			}
		}
		
		
		//adding the equations
		loadEquations(_equationList.getTable() , equations);
	
		
		
		if (equations != null) {
			 Set<Entry<String, String>> s=equations.entrySet();

		        // Using iterator in SortedMap 
		        Iterator<Entry<String, String>> i=s.iterator();
		    //    int index = 0;
		        while(i.hasNext()) {
		        	
		            Map.Entry<String , String> m =(Map.Entry<String , String>)i.next();

		            
		            
		            String key = (String) m.getKey();
		            if (key.equals(selectedEquation)) {
		            	_equationList.setText(selectedEquation);
		            }
		        //    index++;
		        }
		}
		
		/// adding connections
		if (connections != null) {
			if (!connections.isEmpty()) {
				ConnectionTableModel []connectionArray = new ConnectionTableModel[connections.size()];
				connections.toArray(connectionArray);
				_connectionsTableViewer.setInput(connectionArray);
				
			
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
		newShell.setText("Input Properties Dialog");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 450;
	    int nMinHeight = 300;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	    
	   
	}

	
	private boolean findRecord(String column1 , String column2) {
		boolean results = false;
		
		for (int i = 0;i < _connectionsTableViewer.getTable().getItemCount(); i++) {
			
			ConnectionTableModel row = (ConnectionTableModel)_connectionsTableViewer.getTable().getItem(i).getData();
			if (row != null) {
				if ( (row.getName().equals(column1)) && (row.getTask().equals(column2))  ) {
					results = true;
				}
			}
			
		}
		
		

		
		
		return results;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		Object source = e.getSource();
		
		if (source.equals(_addConnectionButton)) {
			
			if (_settings != null) {
				CreateConnectionDialog dialog = new CreateConnectionDialog(_connectionObjects);
				dialog.setBlockOnOpen(true);
				int state = dialog.open();
				if (state == Dialog.OK) {
					int selectionIndex = dialog.getSelectionIndex();
					if (selectionIndex != -1) {
						
						if (selectionIndex <= _connectionObjects.size()) {
							
						
							SdModelConnection modelConnection = _connectionObjects.get(selectionIndex);
							if (modelConnection != null) {
								String objectTask = modelConnection.getModelName();
								String objectName = modelConnection.getObjectName();

								List<ConnectionTableModel> connections = _settings.getConnections();

								if (!findRecord(objectName , objectTask)) {
									ConnectionTableModel connection = new ConnectionTableModel();
									connection.setId(connections.size() + 1);
									connection.setName(objectName);
									connection.setTask(objectTask);
									connections.add(connection);
									_settings.setConnections(connections);
									ConnectionTableModel []connectionArray = new ConnectionTableModel[connections.size()];
									connections.toArray(connectionArray);
									_connectionsTableViewer.setInput(connectionArray);
									
									
									
								}
								
							}
							
						}
						
					}
				}
				
				
				
			}
			
			
			
		}
		else if (source.equals(_deleteConnectionButton)) {
			
			int index = _connectionsTable.getSelectionIndex();
		    if (index != -1) {
		    	ConnectionTableModel connection = (ConnectionTableModel)_connectionsTable.getItem(index).getData();
		    	if (connection != null) {
		    		List<ConnectionTableModel> connections = _settings.getConnections();
		    		if (connections != null) {
		    			connections.remove(connection);
		    			_settings.setConnections(connections);
		    			ConnectionTableModel []connectionArray = new ConnectionTableModel[connections.size()];
						connections.toArray(connectionArray);
						_connectionsTableViewer.setInput(connectionArray);
		    		}
		    	}
		    	
		    	
		    	
		    }
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
		
	}
}
