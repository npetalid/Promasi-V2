package org.promasi.coredesigner.ui.dialogs;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
/**
 * 
 * @author antoxron
 *
 */
public class CreateProjectDialog extends ApplicationWindow implements SelectionListener {
	
	
	
	private Text _projectNameText;
	private Text _modelNameText;
	
	private Button _createButton;
	private Button _cancelButton;

	private String _projectName = null;
	private String _modelName = null;
	
	
	
	/**
	 * Create the application window.
	 */
	public CreateProjectDialog() {
		super(null);
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		container.setLayout(new FormLayout());
		
		Composite topContainer = new Composite(container, SWT.NONE);
		FormData fd_topContainer = new FormData();
		fd_topContainer.bottom = new FormAttachment(0, 109);
		fd_topContainer.right = new FormAttachment(0, 268);
		fd_topContainer.top = new FormAttachment(0, 34);
		fd_topContainer.left = new FormAttachment(0, 33);
		topContainer.setLayoutData(fd_topContainer);
		topContainer.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		topContainer.setLayout(new GridLayout(3, false));

		
		Label projectNameLabel = new Label(topContainer, SWT.NONE);
		projectNameLabel.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		GridData gd_projectNameLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_projectNameLabel.widthHint = 90;
		projectNameLabel.setLayoutData(gd_projectNameLabel);
		projectNameLabel.setText("Project Name");
		
		_projectNameText = new Text(topContainer, SWT.BORDER);
		_projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label modelNameLabel = new Label(topContainer, SWT.NONE);
		modelNameLabel.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		modelNameLabel.setText("Model name");
		
		_modelNameText = new Text(topContainer, SWT.BORDER);
		_modelNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Composite bottomContainer = new Composite(container, SWT.NONE);
		FormData fd_bottomContainer = new FormData();
		fd_bottomContainer.bottom = new FormAttachment(0, 165);
		fd_bottomContainer.top = new FormAttachment(0, 115);
		fd_bottomContainer.left = new FormAttachment(0, 106);
		bottomContainer.setLayoutData(fd_bottomContainer);
		bottomContainer.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		bottomContainer.setLayout(new GridLayout(3, true));

		
		
		_createButton= new Button(bottomContainer, SWT.NONE);
		_createButton.setText("Create");
		_createButton.addSelectionListener(this);
		
		_cancelButton = new Button(bottomContainer, SWT.NONE);
		_cancelButton.setText("Cancel");
		_cancelButton.addSelectionListener(this);
		return container;
	}

	
	public void setProjectName(String projectName) {
		_projectName = projectName;
	}
	public String getProjectName() {
		return _projectName;
	}
	public void setModelName(String modelName) {
		_modelName = modelName;
	}
	public String getModelName() {
		return _modelName;
	}
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			CreateProjectDialog window = new CreateProjectDialog();
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
		newShell.setText("New Project");
		
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 313;
	    int nMinHeight = 220;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
		
		
	    
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		Object source = e.getSource();
		if (source.equals(_createButton)) {
			String projectName = _projectNameText.getText();
			String modelName = _modelNameText.getText();
			if ((projectName != null) && (modelName != null)) {
				
				if ((!projectName.isEmpty()) && (!modelName.isEmpty())) {
					this._projectName = projectName;
					this._modelName = modelName;
					this.close();
				}
				
				
			}
			
		}
		else if (source.equals(_cancelButton)) {
			this.close();
		}
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
