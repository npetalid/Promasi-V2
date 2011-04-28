/**
 * 
 */
package org.promasi.game.project;

import java.util.Map;

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
	public TaskBridge(String outputTaskName, String inputTaskName, String outputSdObjectId, String inputSdObjectId, Map<String,ProjectTask> tasks)throws NullArgumentException, IllegalArgumentException{
		if(outputTaskName==null){
			throw new NullArgumentException("Wrong argument outputTaskName==null");
		}
		
		if(outputSdObjectId==null){
			throw new NullArgumentException("Wrong argument outputSdObjectId==null");
		}
		
		if(inputTaskName==null){
			throw new NullArgumentException("Wrong argument inputTaskname==null");
		}
		
		if(inputSdObjectId==null){
			throw new NullArgumentException("Wrong argument inputSdObjectId==null");
		}
		
		if(tasks==null){
			throw new NullArgumentException("Wrong argument tasks==null");
		}
		
		if(!tasks.containsKey(inputTaskName)){
			throw new IllegalArgumentException("Wrong argument tasks does not contain inputTask");
		}
		
		if(!tasks.containsKey(outputTaskName)){
			throw new IllegalArgumentException("Wrong argument tasks does not contain outputTask");
		}
		
		if(outputTaskName.equals(inputTaskName)){
			throw new IllegalArgumentException("Wrong argument outputTaskName, inputTaskName");
		}
		
		ProjectTask outputTask=tasks.get(outputTaskName);
		ProjectTask inputTask=tasks.get(inputTaskName);
		
		if(!outputTask.makeBridge(outputSdObjectId, inputSdObjectId, inputTask)){
			throw new IllegalArgumentException("Make bridge failed");
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
	public synchronized SerializableTaskBridge getSerializableTaskBridge()throws SerializationException{
		try {
			return new SerializableTaskBridge(this);
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "+e.getMessage());
		}
	}
}
