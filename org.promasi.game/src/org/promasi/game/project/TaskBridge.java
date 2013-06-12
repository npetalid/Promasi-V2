/**
 * 
 */
package org.promasi.game.project;

import java.util.Map;

import org.promasi.game.GameException;
import org.promasi.game.model.TaskBridgeModel;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class TaskBridge 
{
	/**
	 * 
	 */
	protected String _outputTaskName;
	
	/**
	 * 
	 */
	protected String _inputTaskName;
	
	/**
	 * 
	 */
	protected String _inputSdObjectId;
	
	/**
	 * 
	 */
	protected String _outputSdObjectId;
	
	/**
	 * 
	 * @param outputTaskName
	 * @param inputTaskName
	 * @param outputSdObjectId
	 * @param inputSdObjectId
	 * @param tasks
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public TaskBridge(String outputTaskName, String inputTaskName, String outputSdObjectId, String inputSdObjectId, Map<String,ProjectTask> tasks)throws GameException{
		if(outputTaskName==null){
			throw new GameException("Wrong argument outputTaskName==null");
		}
		
		if(outputSdObjectId==null){
			throw new GameException("Wrong argument outputSdObjectId==null");
		}
		
		if(inputTaskName==null){
			throw new GameException("Wrong argument inputTaskname==null");
		}
		
		if(inputSdObjectId==null){
			throw new GameException("Wrong argument inputSdObjectId==null");
		}
		
		if(tasks==null){
			throw new GameException("Wrong argument tasks==null");
		}
		
		if(!tasks.containsKey(inputTaskName)){
			throw new GameException("Wrong argument tasks does not contain inputTask");
		}
		
		if(!tasks.containsKey(outputTaskName)){
			throw new GameException("Wrong argument tasks does not contain outputTask");
		}
		
		if(outputTaskName.equals(inputTaskName)){
			throw new GameException("Wrong argument outputTaskName, inputTaskName");
		}
		
		ProjectTask outputTask=tasks.get(outputTaskName);
		ProjectTask inputTask=tasks.get(inputTaskName);
		
		if(!outputTask.makeBridge(outputSdObjectId, inputSdObjectId, inputTask)){
			throw new GameException("Make bridge failed");
		}
		
		_outputTaskName=outputTaskName;
		_inputTaskName=inputTaskName;
		_inputSdObjectId=inputSdObjectId;
		_outputSdObjectId=outputSdObjectId;
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public synchronized TaskBridgeModel getMemento(){
		TaskBridgeModel result = new TaskBridgeModel();
		
		result.setInputSdObjectId(_inputSdObjectId);
		result.setInputTaskName(_inputTaskName);
		result.setOutputSdObjectId(_outputSdObjectId);
		result.setOutputTaskName(_outputTaskName);
		
		return result;
	}
}
