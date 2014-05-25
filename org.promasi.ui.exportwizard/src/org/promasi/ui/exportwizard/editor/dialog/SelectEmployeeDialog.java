package org.promasi.ui.exportwizard.editor.dialog;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ProgressBar;
import org.promasi.ui.exportwizard.resources.EmployeeModel;
import org.promasi.ui.exportwizard.resources.Employees;
import java.util.ArrayList;
/***
 * 
 * @author antoxron
 *
 */
public class SelectEmployeeDialog extends ApplicationWindow {
	
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private Text _nameText;
	private Text _lastNameText;
	private Text _salaryText;
	private Text _descriptionText;
	private SashForm _mainSashForm;
	
	private Gallery _rightGallery;
	private Gallery _leftGallery;
	private ProgressBar _developerProgressBar;
	private ProgressBar _testerProgressBar;
	private ProgressBar _designerProgressBar;
	private ProgressBar _teamPlayerProgressBar;
	private ProgressBar _systemKnoProgressBar;
	
	private GalleryItem _rightGalleryGroup;
	private GalleryItem _leftGalleryGroup;
	private Employees _employees;
	private List<EmployeeModel> _selectedEmployees;

	
	private ScrolledForm _mainForm;
	private boolean _saveSettings = false;

	public boolean saveSettings() {
		return _saveSettings;
	}
	/**
	 * Create the application window.
	 */
	public SelectEmployeeDialog(Employees employees , List<EmployeeModel> selectedEmployees) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_employees = employees;
		_selectedEmployees = selectedEmployees;
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
		_mainForm.setText("Employees Wizard");
		_mainForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		_mainSashForm = new SashForm(_mainForm.getBody(), SWT.NONE);
		_mainSashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(_mainSashForm);
		formToolkit.paintBordersFor(_mainSashForm);
		
		Section topSection = formToolkit.createSection(_mainSashForm,  Section.TITLE_BAR);
		formToolkit.paintBordersFor(topSection);
		topSection.setText("Select employees");
		topSection.setExpanded(true);
		
		SashForm topSashForm = new SashForm(topSection, SWT.NONE);
		formToolkit.adapt(topSashForm);
		formToolkit.paintBordersFor(topSashForm);
		topSection.setClient(topSashForm);
		
		Section leftSection = formToolkit.createSection(topSashForm,  Section.TITLE_BAR);
		formToolkit.paintBordersFor(leftSection);
		leftSection.setText("Available employees");
		leftSection.setLayout(new FillLayout());
		
		Composite reftSectionComposite = new Composite(leftSection, SWT.NONE);
		formToolkit.adapt(reftSectionComposite);
		formToolkit.paintBordersFor(reftSectionComposite);
		leftSection.setClient(reftSectionComposite);
		reftSectionComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
				
		_leftGallery = new Gallery(reftSectionComposite, SWT.H_SCROLL);

		DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
		gr.setMinMargin(2);
		gr.setItemHeight(56);
		gr.setItemWidth(72);
		gr.setAutoMargin(true);
		_leftGallery.setGroupRenderer(gr);

		DefaultGalleryItemRenderer ir = new DefaultGalleryItemRenderer();
		_leftGallery.setItemRenderer(ir);

		_leftGalleryGroup = new GalleryItem(_leftGallery, SWT.NONE);
		_leftGalleryGroup.setText("Employees"); 
		_leftGalleryGroup.setExpanded(true);
		
		
		Section rightSection = formToolkit.createSection(topSashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(rightSection);
		rightSection.setText("Hired employees");
		rightSection.setLayout(new FillLayout());
		
		Composite rightSectionComposite = new Composite(rightSection, SWT.NONE);
		formToolkit.adapt(rightSectionComposite);
		formToolkit.paintBordersFor(rightSectionComposite);
		rightSection.setClient(rightSectionComposite);
		rightSectionComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		_rightGallery = new Gallery(rightSectionComposite, SWT.H_SCROLL);
		_rightGallery.setGroupRenderer(gr);
		_rightGallery.setItemRenderer(ir);
		
		
		_rightGalleryGroup = new GalleryItem(_rightGallery, SWT.NONE);
		_rightGalleryGroup.setText("Employees"); 
		_rightGalleryGroup.setExpanded(true);
		
		
		topSashForm.setWeights(new int[] {1, 1});
		
		
		
		Section bottomSection = formToolkit.createSection(_mainSashForm,  Section.TITLE_BAR);
		formToolkit.paintBordersFor(bottomSection);
		bottomSection.setText("Employee details");
		
		Composite composite = new Composite(bottomSection, SWT.NONE);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		bottomSection.setClient(composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm bottomSashForm = new SashForm(composite, SWT.NONE);
		bottomSashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(bottomSashForm);
		formToolkit.paintBordersFor(bottomSashForm);
		
		Composite topEmployeeComposite = new Composite(bottomSashForm, SWT.NONE);
		formToolkit.adapt(topEmployeeComposite);
		formToolkit.paintBordersFor(topEmployeeComposite);
		topEmployeeComposite.setLayout(new GridLayout(2, true));
		
		Composite employeeDetailsComposite = new Composite(topEmployeeComposite, SWT.NONE);
		GridLayout gl_employeeDetailsComposite = new GridLayout(2, false);
		gl_employeeDetailsComposite.marginTop = 5;
		employeeDetailsComposite.setLayout(gl_employeeDetailsComposite);
		GridData gd_employeeDetailsComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_employeeDetailsComposite.heightHint = 102;
		gd_employeeDetailsComposite.widthHint = 263;
		employeeDetailsComposite.setLayoutData(gd_employeeDetailsComposite);
		formToolkit.adapt(employeeDetailsComposite);
		formToolkit.paintBordersFor(employeeDetailsComposite);
		
		Label nameLabel = new Label(employeeDetailsComposite, SWT.NONE);
		formToolkit.adapt(nameLabel, true, true);
		nameLabel.setText("Name");
		
		_nameText = new Text(employeeDetailsComposite, SWT.BORDER);
		_nameText.setEditable(false);
		_nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_nameText, true, true);
		
		Label lastNameLabel = new Label(employeeDetailsComposite, SWT.NONE);
		formToolkit.adapt(lastNameLabel, true, true);
		lastNameLabel.setText("Last name");
		
		_lastNameText = new Text(employeeDetailsComposite, SWT.BORDER);
		_lastNameText.setEditable(false);
		_lastNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_lastNameText, true, true);
		
		Label salaryLabel = new Label(employeeDetailsComposite, SWT.NONE);
		formToolkit.adapt(salaryLabel, true, true);
		salaryLabel.setText("Salary");
		
		_salaryText = new Text(employeeDetailsComposite, SWT.BORDER);
		_salaryText.setEditable(false);
		_salaryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_salaryText, true, true);
		
		Composite progressBarComposite = new Composite(topEmployeeComposite, SWT.NONE);
		progressBarComposite.setLayout(new GridLayout(2, false));
		GridData gd_progressBarComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_progressBarComposite.heightHint = 115;
		gd_progressBarComposite.widthHint = 256;
		progressBarComposite.setLayoutData(gd_progressBarComposite);
		formToolkit.adapt(progressBarComposite);
		formToolkit.paintBordersFor(progressBarComposite);
		
		Label developerLabel = new Label(progressBarComposite, SWT.NONE);
		formToolkit.adapt(developerLabel, true, true);
		developerLabel.setText("Developer");
		
		_developerProgressBar = new ProgressBar(progressBarComposite, SWT.NONE);
		_developerProgressBar.setMaximum(10);
		GridData gd__developerProgressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd__developerProgressBar.widthHint = 151;
		_developerProgressBar.setLayoutData(gd__developerProgressBar);
		formToolkit.adapt(_developerProgressBar, true, true);
		
		Label testerLabel = new Label(progressBarComposite, SWT.NONE);
		formToolkit.adapt(testerLabel, true, true);
		testerLabel.setText("Tester");
		
		_testerProgressBar = new ProgressBar(progressBarComposite, SWT.NONE);
		_testerProgressBar.setMaximum(10);
		GridData gd__testerProgressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd__testerProgressBar.widthHint = 152;
		_testerProgressBar.setLayoutData(gd__testerProgressBar);
		formToolkit.adapt(_testerProgressBar, true, true);
		
		Label teamPlayerLabel = new Label(progressBarComposite, SWT.NONE);
		formToolkit.adapt(teamPlayerLabel, true, true);
		teamPlayerLabel.setText("Designer");
		
		_designerProgressBar = new ProgressBar(progressBarComposite, SWT.NONE);
		_designerProgressBar.setMaximum(10);
		GridData gd__designerProgressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd__designerProgressBar.widthHint = 153;
		_designerProgressBar.setLayoutData(gd__designerProgressBar);
		formToolkit.adapt(_designerProgressBar, true, true);
		
		Label systemKnowledgeLabel = new Label(progressBarComposite, SWT.NONE);
		formToolkit.adapt(systemKnowledgeLabel, true, true);
		systemKnowledgeLabel.setText("Team player");
		
		_teamPlayerProgressBar = new ProgressBar(progressBarComposite, SWT.NONE);
		_teamPlayerProgressBar.setMaximum(10);
		GridData gd__teamPlayerProgressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd__teamPlayerProgressBar.widthHint = 153;
		_teamPlayerProgressBar.setLayoutData(gd__teamPlayerProgressBar);
		formToolkit.adapt(_teamPlayerProgressBar, true, true);
		
		Label designerLabel = new Label(progressBarComposite, SWT.NONE);
		formToolkit.adapt(designerLabel, true, true);
		designerLabel.setText("System knowledge");
		
		_systemKnoProgressBar = new ProgressBar(progressBarComposite, SWT.NONE);
		_systemKnoProgressBar.setMaximum(10);
		GridData gd__systemKnoProgressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd__systemKnoProgressBar.widthHint = 152;
		_systemKnoProgressBar.setLayoutData(gd__systemKnoProgressBar);
		formToolkit.adapt(_systemKnoProgressBar, true, true);
		
		Composite bottomEmployeeComposite = new Composite(bottomSashForm, SWT.NONE);
		formToolkit.adapt(bottomEmployeeComposite);
		formToolkit.paintBordersFor(bottomEmployeeComposite);
		bottomEmployeeComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite employeeDescriptionComposite = new Composite(bottomEmployeeComposite, SWT.NONE);
		employeeDescriptionComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		formToolkit.adapt(employeeDescriptionComposite);
		formToolkit.paintBordersFor(employeeDescriptionComposite);
		
		_descriptionText = new Text(employeeDescriptionComposite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		formToolkit.adapt(_descriptionText, true, true);
		
		bottomSashForm.setWeights(new int[] {137, 115});
		_mainSashForm.setWeights(new int[] {152, 271});

		
		 _mainForm.getToolBarManager().add(new Action("Save") {  
	            public void run() {  

	            	if (_rightGallery.getItemCount() > 0) {

	            		GalleryItem[] item = _rightGalleryGroup.getItems();
     		
	            		if (item != null) {
	            			_selectedEmployees = new ArrayList<EmployeeModel>();

	            					for (int i = 0;i < item.length; i++) {
	            						String lastName = item[i].getText();
	            						List<EmployeeModel> employees = _employees.getEmployees();
	            						
	            						for (EmployeeModel employeeItem : employees) {
	            							if (employeeItem.getLastName().equals(lastName)) {
	            								_selectedEmployees.add(employeeItem);
	            							}
	            						}
	            						
	            					}
	            					_saveSettings = true;
    								_mainForm.getShell().close();
	    					}
	            	}
	    			
	            }  
	        });  
		    _mainForm.getToolBarManager().add(new Action("Cancel") {  
	            public void run() {  
	            	
	            	_mainSashForm.getShell().close();
	            		
	            }  
	        });  
		
		    _mainForm.updateToolBar();
		
		    
		    _leftGallery.addMouseListener(new MouseListener() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					
					
					GalleryItem[] item = _leftGallery.getSelection();
					if (item != null) {
						if (item.length > 0) {
							String lastName = item[0].getText();
							List<EmployeeModel> employees = _employees.getEmployees();
							
							for ( EmployeeModel employee : employees ) {
								if (employee.getLastName().equals(lastName)) {
									
									
									_leftGallery.remove(item[0]);
									String imagePath = employee.getImagePath();
									Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
									GalleryItem selectedItem = new GalleryItem(_rightGalleryGroup, SWT.NONE);
									selectedItem.setImage(image);
									selectedItem.setText(employee.getLastName());

									_rightGallery.redraw();
								}
							}
						}
					}	
					
				}
				@Override
				public void mouseDown(MouseEvent e) {

					GalleryItem[] item = _leftGallery.getSelection();
					if (item != null) {
						if (item.length > 0) {
							String lastName = item[0].getText();
							List<EmployeeModel> employees = _employees.getEmployees();
							
							for ( EmployeeModel employee : employees ) {
								if (employee.getLastName().equals(lastName)) {
									
									
									_lastNameText.setText(employee.getLastName());
									_nameText.setText(employee.getName());
									_salaryText.setText(employee.getSalary());
									_descriptionText.setText(employee.getCurriculumVitae());

									int developerSkill = Integer.valueOf(employee.getDeveloperSkill());
									int designSkill = Integer.valueOf(employee.getDesignerSkill());
									int testerSkill = Integer.valueOf(employee.getTesterSkill());
									int teamSkill = Integer.valueOf(employee.getTeamPlayerSkill());
									int systemKnowSkill = Integer.valueOf(employee.getSystemKnowledgeSkill());
									
									
									_developerProgressBar.setSelection(developerSkill);
									_testerProgressBar.setSelection(testerSkill);
									_designerProgressBar.setSelection(designSkill);
									_teamPlayerProgressBar.setSelection(teamSkill);
									_systemKnoProgressBar.setSelection(systemKnowSkill);
								}
							}
						}
					}
				}

				@Override
				public void mouseUp(MouseEvent e) {
					
				}
				
			});
		    
		    _rightGallery.addMouseListener(new MouseListener() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					
					GalleryItem[] item = _rightGallery.getSelection();
					if (item != null) {
						if (item.length > 0) {
							String lastName = item[0].getText();
							List<EmployeeModel> employees = _employees.getEmployees();
							
							for ( EmployeeModel employee : employees ) {
								if (employee.getLastName().equals(lastName)) {
									
									
									_rightGallery.remove(item[0]);
									
									
									String imagePath = employee.getImagePath();
									Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
									GalleryItem selectedItem = new GalleryItem(_leftGalleryGroup, SWT.NONE);
									selectedItem.setImage(image);
									selectedItem.setText(employee.getLastName());

									_leftGallery.redraw();
								}
							}
						}
					}
					
				}
				@Override
				public void mouseDown(MouseEvent e) {

					GalleryItem[] item = _rightGallery.getSelection();
					if (item != null) {
						if (item.length > 0) {
							String lastName = item[0].getText();
							List<EmployeeModel> employees = _employees.getEmployees();
							
							for ( EmployeeModel employee : employees ) {
								if (employee.getLastName().equals(lastName)) {
									
									
									_lastNameText.setText(employee.getLastName());
									_nameText.setText(employee.getName());
									_salaryText.setText(employee.getSalary());
									_descriptionText.setText(employee.getCurriculumVitae());

									int developerSkill = Integer.valueOf(employee.getDeveloperSkill());
									int designSkill = Integer.valueOf(employee.getDesignerSkill());
									int testerSkill = Integer.valueOf(employee.getTesterSkill());
									int teamSkill = Integer.valueOf(employee.getTeamPlayerSkill());
									int systemKnowSkill = Integer.valueOf(employee.getSystemKnowledgeSkill());
									
									
									_developerProgressBar.setSelection(developerSkill);
									_testerProgressBar.setSelection(testerSkill);
									_designerProgressBar.setSelection(designSkill);
									_teamPlayerProgressBar.setSelection(teamSkill);
									_systemKnoProgressBar.setSelection(systemKnowSkill);
								}
							}
						}
					}
				}

				@Override
				public void mouseUp(MouseEvent e) {
					
				}
				
			});
		    
		    loadEmployees();
		
		return container;
	}
	public List<EmployeeModel> getSelectedEmployees() {
		return _selectedEmployees;
	}
	
	private EmployeeModel findEmployee(List<EmployeeModel> employees , EmployeeModel employee) {
		
		EmployeeModel results = null;
		
		if ((employees != null) && (employee != null)) {
			for (EmployeeModel employeeItem : employees) {
				if ( employeeItem.getid().equals(employee.getid())  ) {
					results = employeeItem;
				}
			}
		}
		
		return results;
		
	}
	
	
	private void loadEmployees() {
		
		if (_employees != null) {
			
			List<EmployeeModel> employees = _employees.getEmployees();
			
			
			if (employees != null) {
				if (_selectedEmployees != null) {
					
					for (EmployeeModel selectedEmployee : _selectedEmployees) {
							EmployeeModel systemEmployee = findEmployee(employees, selectedEmployee);
							if (systemEmployee != null) {
								employees.remove(systemEmployee);
							}	
					}
					for (EmployeeModel employee : _selectedEmployees) {
						String imagePath = employee.getImagePath();
						Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
						GalleryItem item = new GalleryItem(_rightGalleryGroup, SWT.NONE);
						item.setImage(image);
						item.setText(employee.getLastName());
					}
					
				}
				for (EmployeeModel employee : employees) {
					String imagePath = employee.getImagePath();
					Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
					GalleryItem item = new GalleryItem(_leftGalleryGroup, SWT.NONE);
					item.setImage(image);
					item.setText(employee.getLastName());
				}
				if (_selectedEmployees != null) {
					for (EmployeeModel selectedEmployee : _selectedEmployees) {
						employees.add(selectedEmployee);	
					}
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
		newShell.setText("Export dialog");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 559;
	    int nMinHeight = 447;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}

}
