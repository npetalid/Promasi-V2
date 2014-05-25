package org.promasi.ui.exportwizard.editor.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.promasi.coredesigner.model.Dependency;
import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.model.SdStock;

import org.promasi.game.GameException;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectTask;
import org.promasi.sdsystem.SdSystem;
import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.sdobject.FlowSdObject;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;
import org.promasi.sdsystem.sdobject.equation.CalculatedEquation;
import org.promasi.ui.exportwizard.editor.dialog.model.ProjectSettings;
import org.promasi.ui.exportwizard.editor.dialog.table.model.ProjectTaskTableModel;
import org.promasi.utilities.exceptions.NullArgumentException;
/**
 * 
 * @author antoxron
 *
 */
public class PromasiModelBuilder  {

	private List<TaskConnection> _taskConnections = new ArrayList<TaskConnection>();
	private Project _project = null;
	

	private ProjectSettings _projectSettings;
	
	public PromasiModelBuilder(ProjectSettings projectSettings) {
		_projectSettings = projectSettings;
	}
	
	
	public void setModel(SdProject sdProject) {
		
		
		if (sdProject != null) {
			Project results = null;
			
			
			if (sdProject != null) {
				
				if (_projectSettings != null) {
					
				
				
				List<SdModel> sdProjectTasks = sdProject.getModels();
		        Map<String, ProjectTask> tasks = null;

		        if (sdProjectTasks != null) {
		        	
		        	tasks = new TreeMap<String, ProjectTask>();
		        	
		        	for (SdModel sdTask : sdProjectTasks) {
		        		ProjectTask projectTask  = buildProjectTask(sdTask);
		        		if (projectTask != null) {
		        			tasks.put(projectTask.getName(), projectTask);
		        		}
		        	}
		        	
		        	 try {
		        		 results = new Project( sdProject.getName() , _projectSettings.getDescription() 
		        				 ,_projectSettings.getProjectDuration() ,tasks,_projectSettings.getProjectPrice() 
		        				 ,_projectSettings.getDifficultyLevel());
						
						if ( !_taskConnections.isEmpty() ) {
								
							for(TaskConnection taskConnection : _taskConnections) {
								String sourceTask = taskConnection.getSourceTask();
								String sourceObjectName = taskConnection.getSourceObjectName();
								String targetTask = taskConnection.getTargetTask();
								String targetObjectName = taskConnection.getTargetObjectName();
								results.makeBridge(sourceTask, sourceObjectName, targetTask, targetObjectName);
							}
							
						}
						
						
						
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NullArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        }	
		        
				}
			}
			_project =  results;
		}
	}

	
	public Object getModel() {
		return _project;
	}
	
	
	private Map<String, ISdObject> buildSdObjects(List<SdObject> sdObjects) {
		
		 Map<String, ISdObject> results = null;
		
		 if (sdObjects != null) {
			 results = new TreeMap<String, ISdObject>();
			 for (SdObject sdObject : sdObjects) {
				 
				
				 String type = sdObject.getType();
				

				 if ( (type != null) && (!type.trim().isEmpty()) ) {
					 
					 ISdObject  object = null;
					 String name = sdObject.getName();
					 CalculatedEquation calcEquation = null;
					 double initialValue = 0.0;
					
					 if (!type.equals(SdObject.INPUT_OBJECT)) {
						 
						 
						 if (type.equals(SdObject.CALCULATE_OBJECT)) {
							 SdCalculate sdCalculate = (SdCalculate)sdObject;
								
							 String calculateString = sdCalculate.getCalculateString();
							 try {
									
									calcEquation = new CalculatedEquation(calculateString);
						
							 }
							 catch (IllegalArgumentException w) {
								 w.printStackTrace();
							 } catch (SdSystemException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 }
						 
						
					 }
					 
					 if (type.equals(SdObject.FLOW_OBJECT)) {
						 if (calcEquation != null) {
							 try {
								
								 object = new FlowSdObject(calcEquation);
							  } 
							 catch (NullArgumentException e) {
								
								e.printStackTrace();
							}
						 }
						 
						 
					 }
					 else if (type.equals(SdObject.STOCK_OBJECT)) {
						 if (calcEquation != null) {
							 try {
								 
								 SdStock sdStock = (SdStock)sdObject;
								 
								 initialValue = Double.valueOf(  sdStock.getInitialValue() );
								 object = new StockSdObject(calcEquation , initialValue);
							  } 
							 catch (NullArgumentException e) {
								
								e.printStackTrace();
							}
						 }
					 }
					 else if (type.equals(SdObject.INPUT_OBJECT)) {
						 try {
							object = new InputSdObject();
							
							List<Dependency> dependencies = sdObject.getDependencies();
							if (dependencies != null) {
								for (Dependency dependency : dependencies) {
									String dependencyName = dependency.getName();
									String dependencyTask = dependency.getModelName();
									String dependencyType = dependency.getType();
									if ((dependencyType.equals(SdObject.OUTPUT_OBJECT)) 
											&& ( !(dependencyTask.equals(sdObject.getParent().getName())))) {


										TaskConnection connection = new TaskConnection(dependencyTask ,  dependencyName ,  sdObject.getParent().getName() ,  sdObject.getName());
										_taskConnections.add(connection);
									}
								}
							}
						} 
						 catch (NullArgumentException e) {
							
							e.printStackTrace();
						}
					 }
					 else if (type.equals(SdObject.OUTPUT_OBJECT)) {
						 if (calcEquation != null) {
							 try {
								
								 object = new OutputSdObject(calcEquation);
							  } 
							 catch (NullArgumentException e) {
								
								e.printStackTrace();
							}
						 }
					 }
					 
					 
					 if (object != null) {
						 results.put(name, object);
					 }
					 
					 
				 }
			 }
		 }
		 
		 return results;
	}

	private ProjectTask buildProjectTask(SdModel sdProjectTask) {
		
		ProjectTask projectTask = null;
		
		if (sdProjectTask != null) {
			String taskName = sdProjectTask.getName();
			List<SdObject> children = sdProjectTask.getChildrenArray();
			
			
			if ( (taskName != null) && (children != null)  ) {
						
				 Map<String, ISdObject> sdObjects = buildSdObjects(children);
				 
				 
			    
				 if (sdObjects != null) {
				        try {
				     
							SdSystem sdSystem = null;
							try {
								sdSystem = new SdSystem(sdObjects);
							} catch (SdSystemException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
							String equationValue = "";
							List<ProjectTaskTableModel> modelTasks = _projectSettings.getTasks();
							if (modelTasks != null) {
								for (ProjectTaskTableModel item : modelTasks) {
									if (item.getTaskName().equals(taskName)) {
										equationValue = item.getEquation();
									}
									
									
								}
							}
							
							try {
								projectTask = new ProjectTask(taskName,taskName,sdSystem, new CalculatedEquation(SdSystem.CONST_TIME_SDOBJECT_NAME+ "*" + equationValue));
							} catch (GameException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SdSystemException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
				 }
			}
		}
		return projectTask;
		
	}
	
	
	
	
	
	
	
	
	private class TaskConnection {
		
		private String _sourceTask;
		private String _sourceObjectName;
		private String _targetTask;
		private String _targetObjectName;
		
		public TaskConnection(String sourceTask , String sourceObjectName , String targetTask , String targetObjectName) {
			_sourceTask = sourceTask;
			_sourceObjectName = sourceObjectName;
			_targetTask = targetTask;
			_targetObjectName = targetObjectName;
		}
		public String getSourceTask() {
			return _sourceTask;
		}
		public String getSourceObjectName() {
			return _sourceObjectName;
		}
		public String getTargetTask() {
			return _targetTask;
		}
		public String getTargetObjectName() {
			return _targetObjectName;
		}
		
	}
}
