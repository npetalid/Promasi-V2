package org.promasi.game.project;

import java.util.Map;

import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SerializableTaskBridge 
{
	
	/**
	 * 
	 */
	private String _outputTaskName;
	
	/**
	 * 
	 */
	private String _inputTaskName;
	
	/**
	 * 
	 */
	private String _inputSdObjectId;
	
	/**
	 * 
	 */
	private String _outputSdObjectId;
	
	/**
	 * 
	 */
	public SerializableTaskBridge(){
		
	}
	
	/**
	 * 
	 * @param taskBridge
	 * @throws NullArgumentException
	 */
	protected SerializableTaskBridge(TaskBridge taskBridge)throws NullArgumentException{
		if(taskBridge==null){
			throw new NullArgumentException("Wrong argument taskBridge==null");
		}
		
		_outputTaskName=taskBridge._outputTaskName;
		_inputTaskName=taskBridge._inputTaskName;
		_inputSdObjectId=taskBridge._inputSdObjectId;
		_outputSdObjectId=taskBridge._outputSdObjectId;
	}

	/**
	 * 
	 * @param tasks
	 * @return
	 * @throws SerializationException
	 */
	public TaskBridge getTaskBridge(Map<String, ProjectTask> tasks)throws SerializationException{
		if(tasks==null){
			throw new SerializationException("Serialization failed because wrong argument tasks==null");
		}
		
		try {
			if(_outputTaskName==null){
				throw new SerializationException("Serialization failed because invalid _ouputTaskName property");
			}
			
			if(_inputTaskName==null){
				throw new SerializationException("Serialization failed because invalid _inputTaskName property");
			}
			
			if(_outputSdObjectId==null){
				throw new SerializationException("Serialization failed because invalid _outputSdObjectId property");
			}
			
			if(_inputSdObjectId==null){
				throw new SerializationException("Serialization failed because invalid _inputSdObjectId property");
			}
			
			return new TaskBridge(_outputTaskName, _inputTaskName, _outputSdObjectId, _inputSdObjectId, tasks);
		} catch (IllegalArgumentException e) {
			throw new SerializationException("Serialization failed because "+e.getMessage());
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "+e.getMessage());
		}
	}
	
	/**
	 * @param outputTaskName the outputTaskName to set
	 */
	public void setOutputTaskName(String outputTaskName) {
		_outputTaskName = outputTaskName;
	}

	/**
	 * @return the outputTaskName
	 */
	public String getOutputTaskName() {
		return _outputTaskName;
	}

	/**
	 * @param inputTaskName the inputTaskName to set
	 */
	public void setInputTaskName(String inputTaskName) {
		_inputTaskName = inputTaskName;
	}

	/**
	 * @return the inputTaskName
	 */
	public String getInputTaskName() {
		return _inputTaskName;
	}

	/**
	 * @param inputSdObjectId the inputSdObjectId to set
	 */
	public void setInputSdObjectId(String inputSdObjectId) {
		_inputSdObjectId = inputSdObjectId;
	}

	/**
	 * @return the inputSdObjectId
	 */
	public String getInputSdObjectId() {
		return _inputSdObjectId;
	}

	/**
	 * @param outputSdObjectId the outputSdObjectId to set
	 */
	public void setOutputSdObjectId(String outputSdObjectId) {
		_outputSdObjectId = outputSdObjectId;
	}

	/**
	 * @return the outputSdObjectId
	 */
	public String getOutputSdObjectId() {
		return _outputSdObjectId;
	}
	
}
