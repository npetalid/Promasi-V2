package org.promasi.coredesigner.ui.dialogs;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.window.ApplicationWindow;

import org.eclipse.nebula.widgets.tablecombo.TableCombo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

import org.promasi.coredesigner.model.SdModelConnection;
import org.promasi.coredesigner.resources.ImageUtilities;
/**
 * 
 * @author antoxron
 *
 */
public class CreateConnectionDialog extends ApplicationWindow implements SelectionListener{

	
	
	private int _selectionIndex;
	private List<SdModelConnection> _objects;
	private TableCombo _objectList;
	private Button _createButton;
	private Button _cancelButton;
	/**
	 * Create the application window.
	 */
	public CreateConnectionDialog(List<SdModelConnection> objects) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_objects = objects;
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite mainContainer = new Composite(parent, SWT.NONE);
		mainContainer.setBackground(ImageUtilities.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		mainContainer.setLayout(null);
		
		Label selectObjectLabel = new Label(mainContainer, SWT.NONE);
		selectObjectLabel.setBackground(ImageUtilities.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		selectObjectLabel.setBounds(10, 38, 96, 14);
		selectObjectLabel.setText("Select object");
		
		
		
		_objectList = new TableCombo(mainContainer, SWT.BORDER | SWT.READ_ONLY);
		_objectList.defineColumns(new String[] { "Name", "Model" }, 
				new int[] { SWT.DEFAULT , SWT.DEFAULT});
		_objectList.setDisplayColumnIndex(0);
		_objectList.setShowTableHeader(true);

		_objectList.setBounds(112, 34, 176, 22);
		
		_createButton = new Button(mainContainer, SWT.NONE);
		_createButton.setBounds(82, 95, 94, 22);
		_createButton.setText("Create");
		_createButton.addSelectionListener(this);
		
		_cancelButton = new Button(mainContainer, SWT.NONE);
		_cancelButton.setBounds(182, 95, 94, 22);
		_cancelButton.setText("Cancel");
		_cancelButton.addSelectionListener(this);
		
		loadData();
		
		return mainContainer;
	}
	
	private void loadData() {
		loadObjects(_objectList.getTable() , _objects);
	}

	
	private  List<TableItem> loadObjects(Table table , List<SdModelConnection> connections) {
		List<TableItem> rowList = new ArrayList<TableItem>();
		
		
		for (SdModelConnection connection : connections) {
			
			String modelName = connection.getModelName();
            String objectName = connection.getObjectName();
            TableItem ti = new TableItem(table, SWT.NONE);
            ti.setText(new String[] { objectName , modelName });
			rowList.add(ti);
		}
		
		return rowList;
	}
	
	
	
	public void setSelectionIndex(int selectionIndex) {
		_selectionIndex = selectionIndex;
	}
	public int getSelectionIndex() {
		return _selectionIndex;
	}
	public void setObjects(List<SdModelConnection> objects) {
		_objects = objects;
	}
	public List<SdModelConnection> getObjects() {
		return _objects;
	}
	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Create connection");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 341;
	    int nMinHeight = 174;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		Object source = e.getSource();
		
		if (source.equals(_createButton)) {
			_selectionIndex = _objectList.getSelectionIndex();
			this.close();
		
		}
		else if (source.equals(_cancelButton)) {
			_selectionIndex = -1;
			this.close();
		}
		
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
		
	}
}
