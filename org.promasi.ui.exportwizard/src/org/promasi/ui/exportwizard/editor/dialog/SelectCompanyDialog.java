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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.promasi.ui.exportwizard.resources.Companies;
import org.promasi.ui.exportwizard.resources.CompanyModel;
/**
 * 
 * @author antoxron
 *
 */

public class SelectCompanyDialog extends ApplicationWindow {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private Text _companyNameLabel;
	private Text _descriptionLabel;
	private Text _budgetText;
	private Text _prestigePointsText;
	private Companies _companies;
	private CompanyModel _selectedCompany;
	private ScrolledForm _mainForm;
	
	private GalleryItem _galleryCompanies;
	private DateTime _startTime;
	private DateTime _endTime;
	private Gallery _gallery;
	
	private Gallery _rightGallery;
	private GalleryItem _gallerySelectedCompanies;

	
	private boolean _saveSettings = false;

	public boolean saveSettings() {
		return _saveSettings;
	}
	/**
	 * Create the application window.
	 */
	public SelectCompanyDialog(Companies companies , CompanyModel selectedCompany) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_companies = companies;
		_selectedCompany = selectedCompany;
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
		_mainForm.setText("Company wizard");
		_mainForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(_mainForm.getBody(), SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(sashForm);
		formToolkit.paintBordersFor(sashForm);
		
		Section topSection = formToolkit.createSection(sashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(topSection);
		topSection.setText("Select a company");
		topSection.setExpanded(true);
		
		Composite topComposite = new Composite(topSection, SWT.NONE);
		formToolkit.adapt(topComposite);
		formToolkit.paintBordersFor(topComposite);
		topSection.setClient(topComposite);
		topComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		// Renderers
		DefaultGalleryGroupRenderer leftGalleryGroupRenderer = new DefaultGalleryGroupRenderer();
		leftGalleryGroupRenderer.setMinMargin(2);
		leftGalleryGroupRenderer.setItemHeight(56);
		leftGalleryGroupRenderer.setItemWidth(72);
		leftGalleryGroupRenderer.setAutoMargin(true);

		DefaultGalleryItemRenderer leftItemRenderer = new DefaultGalleryItemRenderer();
		
		SashForm sashForm_1 = new SashForm(topComposite, SWT.NONE);
		formToolkit.adapt(sashForm_1);
		formToolkit.paintBordersFor(sashForm_1);
		
		Composite leftGalleryComposite = new Composite(sashForm_1, SWT.NONE);
		formToolkit.adapt(leftGalleryComposite);
		formToolkit.paintBordersFor(leftGalleryComposite);
		leftGalleryComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		
		_gallery = new Gallery(leftGalleryComposite, SWT.None);
		_gallery.setGroupRenderer(leftGalleryGroupRenderer);
		_gallery.setItemRenderer(leftItemRenderer);
		_galleryCompanies = new GalleryItem(_gallery, SWT.NONE);
		_galleryCompanies.setText("Companies"); //$NON-NLS-1$
		_galleryCompanies.setExpanded(true);
		
		
		_gallery.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
				if (_gallerySelectedCompanies.getItems().length < 1) {
					GalleryItem[] item = _gallery.getSelection();
					if (item != null) {
						if (item.length > 0) {
							String companyName = item[0].getText();
							List<CompanyModel> companies = _companies.getCompanies();
							for (CompanyModel company : companies) {
								if (company.getCompanyName().equals(companyName)) {
									
									_gallery.remove(item[0]);
									
									String imagePath = company.getImagePath();
									Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
									GalleryItem selectedItem = new GalleryItem(_gallerySelectedCompanies, SWT.NONE);
									selectedItem.setImage(image);
									selectedItem.setText(company.getCompanyName());
								
									_rightGallery.redraw();
								}
							}
						}
					}
				}
				
				
				
				
			}
			@Override
			public void mouseDown(MouseEvent e) {

				
				GalleryItem[] item = _gallery.getSelection();
				if (item != null) {
					if (item.length > 0) {
						String companyName = item[0].getText();
						List<CompanyModel> companies = _companies.getCompanies();
						for (CompanyModel company : companies) {
							if (company.getCompanyName().equals(companyName)) {
								
								_companyNameLabel.setText(company.getCompanyName());
								_descriptionLabel.setText(company.getDescrition());
								_budgetText.setText(company.getBudget());
								_prestigePointsText.setText(company.getPrestigePoints());
								
								String[] startTime = company.getStartTime().split(":");
								String[] endTime = company.getEndTime().split(":");
								if (startTime.length == 3) {
									_startTime.setHours(Integer.valueOf(startTime[0]));
									_startTime.setMinutes(Integer.valueOf(startTime[1]));
									_startTime.setSeconds(Integer.valueOf(startTime[2]));
								}
								if (endTime.length == 3) {
									_endTime.setHours(Integer.valueOf(endTime[0]));
									_endTime.setMinutes(Integer.valueOf(endTime[1]));
									_endTime.setSeconds(Integer.valueOf(endTime[2]));
								}
							}
						}
					}
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		Composite rightGalleryComposite = new Composite(sashForm_1, SWT.NONE);
		formToolkit.adapt(rightGalleryComposite);
		formToolkit.paintBordersFor(rightGalleryComposite);
		rightGalleryComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		_rightGallery = new Gallery(rightGalleryComposite, SWT.None);
		

		DefaultGalleryGroupRenderer rightGalleryGroupRenderer = new DefaultGalleryGroupRenderer();
		rightGalleryGroupRenderer.setMinMargin(2);
		rightGalleryGroupRenderer.setItemHeight(56);
		rightGalleryGroupRenderer.setItemWidth(72);
		rightGalleryGroupRenderer.setAutoMargin(true);
		
		
		_rightGallery.setGroupRenderer(rightGalleryGroupRenderer);
		DefaultGalleryItemRenderer rightItemRenderer = new DefaultGalleryItemRenderer();

		_rightGallery.setItemRenderer(rightItemRenderer);
		_gallerySelectedCompanies = new GalleryItem(_rightGallery, SWT.NONE);
		_gallerySelectedCompanies.setText("Selected Company");
		_gallerySelectedCompanies.setExpanded(true);
		
		_rightGallery.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
				if (_gallerySelectedCompanies.getItems().length > 0) {
					
					GalleryItem[] item = _rightGallery.getSelection();
					if (item != null) {
						if (item.length > 0 ) {
							String companyName = item[0].getText();
							List<CompanyModel> companies = _companies.getCompanies();
							for (CompanyModel company : companies) {
								if (company.getCompanyName().equals(companyName)) {
									
									_rightGallery.remove(item[0]);
									
									String imagePath = company.getImagePath();
									Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
									GalleryItem selectedItem = new GalleryItem(_galleryCompanies, SWT.NONE);
									selectedItem.setImage(image);
									selectedItem.setText(company.getCompanyName());
									_gallery.redraw();

								}
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
						String companyName = item[0].getText();
						List<CompanyModel> companies = _companies.getCompanies();
						for (CompanyModel company : companies) {
							if (company.getCompanyName().equals(companyName)) {
								
								_companyNameLabel.setText(company.getCompanyName());
								_descriptionLabel.setText(company.getDescrition());
								_budgetText.setText(company.getBudget());
								_prestigePointsText.setText(company.getPrestigePoints());
								

								String[] startTime = company.getStartTime().split(":");
								String[] endTime = company.getEndTime().split(":");
								if (startTime.length == 3) {
									_startTime.setHours(Integer.valueOf(startTime[0]));
									_startTime.setMinutes(Integer.valueOf(startTime[1]));
									_startTime.setSeconds(Integer.valueOf(startTime[2]));
								}
								if (endTime.length == 3) {
									_endTime.setHours(Integer.valueOf(endTime[0]));
									_endTime.setMinutes(Integer.valueOf(endTime[1]));
									_endTime.setSeconds(Integer.valueOf(endTime[2]));
								}
							}
						}
					}
				}
			}
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
		});
		
		sashForm_1.setWeights(new int[] {1, 1});
		
		
		
		
		Section bottomSection = formToolkit.createSection(sashForm, Section.TITLE_BAR);
		formToolkit.paintBordersFor(bottomSection);
		bottomSection.setText("Complete the follow fields");
		bottomSection.setExpanded(true);
		
		Composite bottomComposite = new Composite(bottomSection, SWT.NONE);
		formToolkit.adapt(bottomComposite);
		formToolkit.paintBordersFor(bottomComposite);
		bottomSection.setClient(bottomComposite);
		bottomComposite.setLayout(new GridLayout(2, false));
		
		Label companyNameLabel = new Label(bottomComposite, SWT.NONE);
		formToolkit.adapt(companyNameLabel, true, true);
		companyNameLabel.setText("Company name");
		
		_companyNameLabel = new Text(bottomComposite, SWT.BORDER);
		_companyNameLabel.setEditable(false);
		_companyNameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_companyNameLabel, true, true);
		
		Label descriptionLabel = new Label(bottomComposite, SWT.NONE);
		formToolkit.adapt(descriptionLabel, true, true);
		descriptionLabel.setText("Description");
		
		_descriptionLabel = new Text(bottomComposite, SWT.BORDER);
		_descriptionLabel.setEditable(false);
		_descriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_descriptionLabel, true, true);
		
		Label startTimeLabel = new Label(bottomComposite, SWT.NONE);
		formToolkit.adapt(startTimeLabel, true, true);
		startTimeLabel.setText("Start time");
		
		_startTime = new DateTime(bottomComposite, SWT.BORDER | SWT.TIME | SWT.MEDIUM);
		formToolkit.adapt(_startTime);
		formToolkit.paintBordersFor(_startTime);
		
		Label endTimeLabel = new Label(bottomComposite, SWT.NONE);
		formToolkit.adapt(endTimeLabel, true, true);
		endTimeLabel.setText("End time");
		
		_endTime = new DateTime(bottomComposite, SWT.BORDER | SWT.TIME | SWT.MEDIUM);
		formToolkit.adapt(_endTime);
		formToolkit.paintBordersFor(_endTime);
		
		Label budgetLabel = new Label(bottomComposite, SWT.NONE);
		formToolkit.adapt(budgetLabel, true, true);
		budgetLabel.setText("Budget");
		
		_budgetText = new Text(bottomComposite, SWT.BORDER);
		_budgetText.setEditable(false);
		_budgetText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_budgetText, true, true);
		
		Label prestigePointsLabel = new Label(bottomComposite, SWT.NONE);
		formToolkit.adapt(prestigePointsLabel, true, true);
		prestigePointsLabel.setText("Prestige points");
		
		_prestigePointsText = new Text(bottomComposite, SWT.BORDER);
		_prestigePointsText.setEditable(false);
		_prestigePointsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(_prestigePointsText, true, true);
		sashForm.setWeights(new int[] {171, 189});

		
		
		 _mainForm.getToolBarManager().add(new Action("Save") {  
	            public void run() {  
	            	
	            	if (_rightGallery.getItemCount() > 0) {
	            		_rightGallery.selectAll();
	            		GalleryItem[] item = _rightGallery.getSelection();
	            		
	            		if (item != null) {

	    							String companyName = item[0].getText();
	    							List<CompanyModel> companies = _companies.getCompanies();
	    							boolean isSettingsCorrect = false;
	    							for (CompanyModel company : companies) {
	    								if (company.getCompanyName().equals(companyName)) {
	    									
	    									_selectedCompany = company;
	    									
	    									int hours = _startTime.getHours();
	    									int minutes = _startTime.getMinutes();
	    									int seconds = _startTime.getSeconds();
	    									
	    									String startTime = String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
	    									
	    									
	    									hours = _endTime.getHours();
	    									minutes = _endTime.getMinutes();
	    									seconds = _endTime.getSeconds();
	    									
	    									String endTime = String.valueOf(hours) + ":" + String.valueOf(minutes) +  ":" + String.valueOf(seconds);
	    						
	    									_selectedCompany.setStartTime(startTime);
	    									_selectedCompany.setEndTime(endTime);
	    									isSettingsCorrect = true;	
	    								}
	    							}
	    							if (isSettingsCorrect) {
	    								_saveSettings = true;
	    								_mainForm.getShell().close();
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
		    
		    loadCompanies();
		return container;
	}
	
	
	private void loadCompanies() {
		if (_companies != null) {
			List<CompanyModel> companies = _companies.getCompanies();
			if (companies != null) {
				
				if (_selectedCompany != null) {
					
					CompanyModel company = null;
					for (CompanyModel companyItem : companies) {
						if (companyItem.getCompanyName().equals(_selectedCompany.getCompanyName())) {
							company = companyItem;
						}
					}
					if (company != null) {
						companies.remove(company);
					}
					String imagePath = _selectedCompany.getImagePath();
					Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
					GalleryItem item = new GalleryItem(_gallerySelectedCompanies, SWT.NONE);
					item.setImage(image);
					item.setText(_selectedCompany.getCompanyName());
				}
				
				for (CompanyModel company : companies) {
					String imagePath = company.getImagePath();
					Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.ui.exportwizard", imagePath ).createImage();
					GalleryItem item = new GalleryItem(_galleryCompanies, SWT.NONE);
					item.setImage(image);
					item.setText(company.getCompanyName());
				}
				if (_selectedCompany != null) {
					companies.add(_selectedCompany);
				}
				
				
			}
		}
	}
	
	public CompanyModel getSelectedCompany() {
		return _selectedCompany;
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
	    int nMinHeight = 432;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}
}
