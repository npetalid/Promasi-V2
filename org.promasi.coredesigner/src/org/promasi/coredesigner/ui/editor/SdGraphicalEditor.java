package org.promasi.coredesigner.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.IOException;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import org.promasi.coredesigner.actions.SdObjectPropertiesAction;
import org.promasi.coredesigner.actions.SimulationAction;
import org.promasi.coredesigner.dnd.SdTemplateTransferDropTargetListener;
import org.promasi.coredesigner.editpart.SdEditPartFactory;
import org.promasi.coredesigner.model.NameValidator;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.model.builder.IModelBuilder;
import org.promasi.coredesigner.model.builder.ModelBuilderFactory;
import org.promasi.coredesigner.model.builder.VisualXmlBuilder;
import org.promasi.coredesigner.model.builder.model.XmlProject;
import org.promasi.coredesigner.model.refactor.RemoveUtils;
import org.promasi.coredesigner.palette.GraphicalEditorPalette;
import org.promasi.coredesigner.resources.ImageUtilities;
import org.promasi.coredesigner.resources.ModelManager;
import org.promasi.coredesigner.ui.dialogs.ConnectionsDesigner;
import org.promasi.coredesigner.ui.dialogs.CreateModelDialog;
import org.promasi.coredesigner.ui.dialogs.EditModelDialog;
import org.promasi.coredesigner.ui.dialogs.RelationshipsDesigner;
/**
 * 
 * @author antoxron
 *
 */
public class SdGraphicalEditor extends GraphicalEditorWithFlyoutPalette {
	
	
	public static final String ID = "org.promasi.coredesigner.ui.editor.maineditor";
	
	
	private SdModel _currentModel;
	private java.util.List<SdModel> _models;
	
	
	public SdGraphicalEditor() {
		setEditDomain(new DefaultEditDomain(this));
		_models = new ArrayList<SdModel>();
		
		createModel();
	}

	public SdModel createModel() {
		
		SdProject sdProject = ModelManager.getInstance().getSdProject();
		_models = sdProject.getModels();
		_currentModel = _models.get( 0 );
		return _currentModel;
		
	}
	
	protected void configureGraphicalViewer() { 
		super.configureGraphicalViewer(); 
		GraphicalViewer viewer = getGraphicalViewer(); 
		viewer.setEditPartFactory(new SdEditPartFactory()); 
		
		
		double[] zoomLevels; 
		ArrayList<String> zoomContributions; 

		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart); 
		ZoomManager manager = rootEditPart.getZoomManager(); 
		getActionRegistry().registerAction(new ZoomInAction(manager)); 
		getActionRegistry().registerAction(new ZoomOutAction(manager)); 
		
		zoomLevels = new double[] {0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0, 20.0}; 
		manager.setZoomLevels(zoomLevels); 
		zoomContributions = new ArrayList<String>(); 
		zoomContributions.add(ZoomManager.FIT_ALL); 
		zoomContributions.add(ZoomManager.FIT_HEIGHT); 
		zoomContributions.add(ZoomManager.FIT_WIDTH); 
		manager.setZoomLevelContributions(zoomContributions);
		
		
		KeyHandler keyHandler = new KeyHandler();
		keyHandler.put( KeyStroke.getPressed('+', SWT.KEYPAD_ADD, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));
		
		
		keyHandler.put( KeyStroke.getPressed('-', SWT.KEYPAD_SUBTRACT, 0), 
				getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));
	
		viewer.setProperty( MouseWheelHandler.KeyGenerator.getKey(SWT.NONE),
				MouseWheelZoomHandler.SINGLETON); 
		viewer.setKeyHandler(keyHandler);
	
	
		ContextMenuProvider provider = new SdContextMenuProvider(viewer, getActionRegistry()); 
		viewer.setContextMenu(provider);
		
		getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, true);
	}
	
	
	
	@Override
	protected void initializeGraphicalViewer() {
		
		GraphicalViewer viewer = getGraphicalViewer(); 
		_currentModel = createModel();
		viewer.setContents(_currentModel);
		loadModel();
		viewer.addDropTargetListener(new SdTemplateTransferDropTargetListener(viewer));
		
		

	}
	private void loadModel() {
		GraphicalViewer viewer = getGraphicalViewer(); 
		viewer.setContents(_currentModel); 
	}
	

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			@Override
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
			
		};
		
		
	}
	
	protected  class OutlinePage extends ContentOutlinePage implements SelectionListener ,PropertyChangeListener {

		public OutlinePage(EditPartViewer viewer) {
			super(viewer);
			
			viewer.addPropertyChangeListener(this);
		}
		private Composite _container;
		private ScrollableThumbnail thumbnail; 
		private DisposeListener disposeListener;
		private Button _newModelButton;
		private Button _editModelButton;
		private Button _deleteModelButton;
		private  List _modelList;

		
		@Override
		public void createControl(Composite parent) {

			
			_container = new Composite(parent, SWT.NONE);
			_container.setLayout(new FillLayout(SWT.HORIZONTAL));
			
			ExpandBar expandBar = new ExpandBar(_container, SWT.NONE);
			
			ExpandItem menuBarExpandItem = new ExpandItem(expandBar, SWT.NONE);
			menuBarExpandItem.setExpanded(true);
			menuBarExpandItem.setText("General");
			
			Composite menuBarComposite = new Composite(expandBar, SWT.NONE);
			menuBarExpandItem.setControl(menuBarComposite);
			menuBarComposite.setLayout(new FillLayout());
			
			final ToolBar toolBar = new ToolBar(menuBarComposite, SWT.BORDER);
				
			ToolItem fileDropDownItem = new ToolItem(toolBar, SWT.DROP_DOWN );
			fileDropDownItem.setText("File");
				
			ToolItem viewDropDownItem = new ToolItem(toolBar, SWT.DROP_DOWN);
			viewDropDownItem.setText("View");
			menuBarExpandItem.setHeight(menuBarExpandItem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 10);
			
			ExpandItem modelExpandItem = new ExpandItem(expandBar, SWT.NONE);
			modelExpandItem.setExpanded(true);
			modelExpandItem.setText("Model");
			
			Composite modelComposite = new Composite(expandBar, SWT.NONE);
			modelExpandItem.setControl(modelComposite);
			modelComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
			
			SashForm sashForm = new SashForm(modelComposite, SWT.VERTICAL);
			
			Composite buttonsComposite = new Composite(sashForm, SWT.NONE);
			buttonsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
			
			_newModelButton = new Button(buttonsComposite, SWT.NONE);
			_newModelButton.setText("New");
			_newModelButton.addSelectionListener(this);
			_editModelButton = new Button(buttonsComposite, SWT.NONE);
			_editModelButton.setText("Edit");
			_editModelButton.addSelectionListener(this);
			_deleteModelButton = new Button(buttonsComposite, SWT.NONE);
			_deleteModelButton.setText("Delete");
			_deleteModelButton.addSelectionListener(this);
			Composite listComposite = new Composite(sashForm, SWT.NONE);
			listComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
			
			_modelList = new List(listComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			sashForm.setWeights(new int[] {24, 72});
			modelExpandItem.setHeight(modelExpandItem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			
			ExpandItem miniViewExpandItem = new ExpandItem(expandBar, SWT.NONE);
			miniViewExpandItem.setExpanded(true);
			miniViewExpandItem.setText("View");
			
			Composite miniViewComposite = new Composite(expandBar, SWT.NONE);
			miniViewExpandItem.setControl(miniViewComposite);
			miniViewComposite.setLayout(new FillLayout(SWT.VERTICAL | SWT.HORIZONTAL));
			miniViewExpandItem.setHeight(150);

			Canvas canvas = new Canvas(miniViewComposite, SWT.None); 
			canvas.setBackground(ImageUtilities.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
			LightweightSystem lws = new LightweightSystem(canvas);
			thumbnail = new ScrollableThumbnail( (Viewport) ((ScalableRootEditPart) getGraphicalViewer() .getRootEditPart()).getFigure());
			thumbnail.setSource(((ScalableRootEditPart) getGraphicalViewer() .getRootEditPart()) .getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			
			disposeListener = new DisposeListener() {
				
				@Override 
				public void widgetDisposed(DisposeEvent e) { 
					if (thumbnail != null) { 
						thumbnail.deactivate(); thumbnail = null; 
					} 
				} }; 
				getGraphicalViewer().getControl().addDisposeListener(disposeListener);
			  
				fileDropDownItem.addSelectionListener(new SelectionAdapter() {

		        Menu dropMenu = null;

		        @Override
		        public void widgetSelected(SelectionEvent e) {
		            if(dropMenu == null) {
		                dropMenu = new Menu(toolBar.getShell(), SWT.POP_UP);
		                toolBar.setMenu(dropMenu);
		                
		                
		               
		                MenuItem exportItem = new MenuItem(dropMenu , SWT.CASCADE);
		                exportItem.setText("Export");
		                
		                
		                final Menu submenu = new Menu(toolBar.getShell(),  SWT.DROP_DOWN);
		                exportItem.setMenu(submenu);
		                MenuItem imageItem = new MenuItem(submenu, SWT.PUSH);
		                imageItem.setText("To image");
		                imageItem.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								
								FileDialog saveDialog = new FileDialog(getSite().getShell(), SWT.SAVE );
								
								String[] filterExtensions = new String[] {"*.jpeg" , "*.png" , "*.bmp", "*.ico" };
								saveDialog.setFilterExtensions(filterExtensions);		
								
								int format = SWT.IMAGE_JPEG;

								String filePath =  saveDialog.open();
								
								if( filePath != null )  {
									
									if( filePath.endsWith(".jpeg") ) {
										format = SWT.IMAGE_JPEG;
									}
									else if( filePath.endsWith(".png") ) {
										format = SWT.IMAGE_PNG;
									}
									else if( filePath.endsWith(".bmp") ) {
										format = SWT.IMAGE_BMP;
									}
									else if( filePath.endsWith(".ico") ) {
										format = SWT.IMAGE_ICO;
									}
									ImageUtilities.exportImageFromEditorContents(getGraphicalViewer(), filePath, format);
									MessageDialog.openInformation(getSite().getShell() , "Information","Image file successfully created.");
								}	
							}
							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								
							}	
		                });
		               

		                MenuItem saveItem = new MenuItem(dropMenu , SWT.PUSH);
		                saveItem.setText("Save");
		                saveItem.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								
								
								SdProject sdProject = ModelManager.getInstance().getSdProject();
								
								FileDialog saveDialog = new FileDialog(getSite().getShell(), SWT.SAVE );
								
								String[] filterExtensions = new String[] { "*.pms" };
								saveDialog.setFilterExtensions(filterExtensions);		
								saveDialog.setFileName( sdProject.getName());
								String filePath =  saveDialog.open();
								if ( filePath != null) {
									IModelBuilder builder = ModelBuilderFactory.getInstance(ModelBuilderFactory.VISUAL_BUILDER);
									builder.setModel(sdProject);
									XmlProject xmlProject = (XmlProject) builder.getModel();
									if ( xmlProject != null ) {
										VisualXmlBuilder xmlBuilder = new VisualXmlBuilder(xmlProject);
										try {
											xmlBuilder.build(filePath);
											MessageDialog.openInformation(getSite().getShell() , "Information",xmlProject.getName() + " is successfully saved");

										} catch (IOException e1) {
											e1.printStackTrace();
										}  
									}									
								}
							}
							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
							
							}
		                	
		                });
		                
		                
		                
		                //////////////////////////////
		                MenuItem closeItem = new MenuItem(dropMenu , SWT.PUSH);
		                closeItem.setText("Close");
		                closeItem.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
																
								getSite().getWorkbenchWindow().getActivePage().closeAllEditors(false);
								
							}
							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
							
							}
		                	
		                });
		            }
		            
		            if (e.detail == SWT.ARROW) {
		              
		                final ToolItem toolItem = (ToolItem) e.widget;
		                final ToolBar  toolBar = toolItem.getParent();

		                Point point = toolBar.toDisplay(new Point(e.x, e.y));
		                dropMenu.setLocation(point.x, point.y);
		                dropMenu.setVisible(true);
		            } 

		        }

		    });
			
			
				viewDropDownItem.addSelectionListener(new SelectionAdapter() {

		        Menu dropMenu = null;

		        @Override
		        public void widgetSelected(SelectionEvent e) {
		            if(dropMenu == null) {
		                dropMenu = new Menu(toolBar.getShell(), SWT.POP_UP);
		                toolBar.setMenu(dropMenu);
		                
		                
		                
		                
		                final MenuItem gridItem = new MenuItem(dropMenu, SWT.CHECK);
		                gridItem.setText("Grid");
		                gridItem.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								boolean isChecked = gridItem.getSelection();
								if (isChecked) {
									getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, true);
								}
								else {
									getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, false);
								}
								
								
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								
							}
		                	
		                	
		                });
		                ///////////////////////////////
		                MenuItem relationshipsItem = new MenuItem(dropMenu, SWT.PUSH);
		                relationshipsItem.setText("Relationships");
		                relationshipsItem.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
							
						
								RelationshipsDesigner designer = new RelationshipsDesigner(ModelManager.getInstance().getSdProject());
								designer.setBlockOnOpen(true);
								designer.open();
								
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								
							}
		                	
		                	
		                });
		                //////////////////////////////
		                MenuItem connectionsItem = new MenuItem(dropMenu, SWT.PUSH);
		                connectionsItem.setText("Connections");
		                connectionsItem.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								
								
								java.util.List<SdModel> sdModels = ModelManager.getInstance().getSdModels();
								if (sdModels != null) {
									ConnectionsDesigner designer = new ConnectionsDesigner(sdModels);
									designer.setBlockOnOpen(true);
									designer.open();
								}
								
								
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								
							}
		                	
		                	
		                });
		               
		                
		                
		            }
		            
		            if (e.detail == SWT.ARROW) {
		                
		                final ToolItem toolItem = (ToolItem) e.widget;
		                final ToolBar  toolBar = toolItem.getParent();

		                Point point = toolBar.toDisplay(new Point(e.x, e.y));
		                dropMenu.setLocation(point.x, point.y);
		                dropMenu.setVisible(true);
		            } 

		        }

		    });
			
				updateModelList();
			
			
			_modelList.addListener(SWT.Selection, new Listener() {
			      public void handleEvent(Event e) {
			    	  
			    	  
			    	  int index = _modelList.getSelectionIndex();
			    	  if (index != -1) {
			    		  String selection = _modelList.getItem(index);
			    		  
			    		  if (selection != null) {
			    			  for (SdModel sdModel : _models) {
			    				  if (sdModel != null) {
			    					  if (sdModel.getName().equals(selection)) {
			    						  _currentModel = sdModel;
			    						  loadModel();
			    						  _modelList.setSelection(index);
			    					  }
			    				  }
			    			  }
			    		  }  
			    	  }
			       
			      }
			});
			
			
			
			

		}
		private void updateModelList() {
			java.util.List<String> models = new ArrayList<String>();
			
			if (_models != null) {
				for (SdModel sdModel : _models) {
					String modelName = sdModel.getName();
					if (modelName != null) {
						models.add(modelName);
					}
				}
			}
			String[] modelNamesArray = new String[models.size()];
			models.toArray(modelNamesArray);
			_modelList.setItems(modelNamesArray);
		}
		@Override
		public Control getControl() { 
			return _container; 
		}
		@Override
		public void dispose() {
		
			getSelectionSynchronizer().removeViewer(getViewer()); 
			if (getGraphicalViewer().getControl() != null && !getGraphicalViewer().getControl().isDisposed()) 
				getGraphicalViewer().getControl().removeDisposeListener(disposeListener); 
	
			super.dispose();
		}
		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			
			if (source.equals(_newModelButton)) {
				CreateModelDialog dialog = new CreateModelDialog();
				dialog.setBlockOnOpen(true);
				dialog.open();
				
				String modelName = dialog.getModelName();
				
				
				
				
				if (modelName != null) {
					
					
					boolean isNameExist = false;
					
					for (SdModel model : _models) {
						if (model.getName().equals(modelName)) {
							isNameExist = true;
						}
					}
					
					if (!isNameExist   && NameValidator.getInstance().validateString(modelName)) {
						SdModel sdModel = new SdModel();
						sdModel.setName(modelName);
						_models.add(sdModel);
						
						
						updateModelList();
						ModelManager.getInstance().setSdModels(_models);
						getViewer().setContents(sdModel);
					}
					
					
				}
			}
			else if (source.equals(_editModelButton)) {
				String currentModelName = _currentModel.getName();
				if (currentModelName != null) {
					EditModelDialog dialog = new EditModelDialog(currentModelName);
					dialog.setBlockOnOpen(true);
					dialog.open();
					String modelName = dialog.getModelName();
					if (modelName != null) {
						
						
						
						_currentModel.setName(modelName);
						
						updateModelList();
					    
						 loadModel();
						 ModelManager.getInstance().setSdModels(_models);
					}
				}
			}
			else if (source.equals(_deleteModelButton)) {
				
				if (_models.size() > 1) {
					_models.remove(_currentModel);
				
					NameValidator.getInstance().removeName(_currentModel.getName());
					
					
					RemoveUtils removeUtils = new RemoveUtils();
					java.util.List<SdModel> sdModels = removeUtils.updateModels(_currentModel.getName(), _models);
					
					
					
					
					if (sdModels != null) {
						_models = sdModels;
						_currentModel = _models.get( _models.size() - 1);
						ModelManager.getInstance().setSdModels(sdModels);
						
	
						updateModelList();
						 loadModel();
					}
					 
				}
			}
			
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			
			if (evt.getPropertyName().equals(SdObject.PROPERTY_RENAME)) {
				_models = ModelManager.getInstance().getSdModels();

				updateModelList();
			}
		}
	} // end of outline
	
	@SuppressWarnings("unchecked")
	@Override
	public void createActions() {
		super.createActions();
		
		ActionRegistry registry = getActionRegistry();
		
		
		IAction action = new AlignmentAction((IWorkbenchPart) this,PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new DirectEditAction((IWorkbenchPart) this);
	    registry.registerAction(action);
	    getSelectionActions().add(action.getId());
	    
	     action = new SdObjectPropertiesAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		 action = new SimulationAction(this);
		registry.registerAction(action);
		
		
		getSelectionActions().add(action.getId());
	}
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) { 
		
		if (type == ZoomManager.class) 
			return ((ScalableRootEditPart) 
					getGraphicalViewer().getRootEditPart()).getZoomManager(); 
		if (type == IContentOutlinePage.class) { 
			return new OutlinePage(this.getGraphicalViewer()); 
		}
		
		else 
			return super.getAdapter(type); 
	}


	@Override
	protected PaletteRoot getPaletteRoot() {
		
		return GraphicalEditorPalette.getPaletteRoot();		
	}
	@Override
	public boolean isDirty() {
		return false;
	}
}
