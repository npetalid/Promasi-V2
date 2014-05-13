package org.promasi.coredesigner.ui.dialogs;

import org.eclipse.jface.window.ApplicationWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
/**
 * 
 * @author antoxron
 *
 */
public class CreateModelDialog extends ApplicationWindow implements SelectionListener {
	
	
	private Text _modelNameText;
	private Button _createModelButton;
	private Button _cancelButton;
	private String _modelName;

	
	
	
	public CreateModelDialog() {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		

		container.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));

		Label modelNameLabel = new Label(container, SWT.NONE);
		modelNameLabel.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		modelNameLabel.setBounds(22, 29, 69, 13);
		modelNameLabel.setText("Model Name");
		
		_modelNameText = new Text(container, SWT.BORDER);
		_modelNameText.setBounds(99, 26, 119, 19);
		
		_createModelButton = new Button(container, SWT.NONE);
		_createModelButton.setBounds(62, 81, 68, 23);
		_createModelButton.setText("Create");
		_createModelButton.addSelectionListener(this);
		_cancelButton = new Button(container, SWT.NONE);
		_cancelButton.setBounds(136, 81, 68, 23);
		_cancelButton.setText("Cancel");
		_cancelButton.addSelectionListener(this);
		return container;
	}


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			CreateModelDialog window = new CreateModelDialog();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
	
		super.configureShell(newShell);
		newShell.setText("Type Model Name");
		
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 275;
	    int nMinHeight = 165;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}

	public void setModelName(String modelName) {
		_modelName = modelName;
	}
	public String getModelName() {
		return _modelName;
	}
	@Override
	protected Point getInitialSize() {
		return new Point(275, 165);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		Object source = e.getSource();
		
		if (source.equals(_cancelButton)) {
			this.close();
		}
		else if (source.equals(_createModelButton)) {
			String modelName = _modelNameText.getText();
			if ( (modelName != null) && (!modelName.isEmpty())  ) {
				_modelName = modelName;
				this.close();
			}
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
