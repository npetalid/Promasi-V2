/**
 * 
 */
package org.promasi.sdsystem;

import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;
import org.promasi.sdsystem.sdobject.SerializableInputSdObject;
import org.promasi.sdsystem.sdobject.SerializableOutputSdObject;
import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class SerializableSdSystemBridge
{
	/**
	 * 
	 */
	private ISerializableSdObject _inputSdObject;
	
	/**
	 * 
	 */
	private ISerializableSdObject _outputSdObject;
	
	/**
	 * 
	 */
	public SerializableSdSystemBridge(){
		
	}
	
	/**
	 * 
	 * @param inputSdObject
	 * @param outputSdObject
	 * @throws NullArgumentException
	 */
	public SerializableSdSystemBridge(final ISerializableSdObject inputSdObject, ISerializableSdObject outputSdObject)throws NullArgumentException{
		if(inputSdObject==null){
			throw new NullArgumentException("Wrong argument inputSdObject==null");
		}
		
		if(outputSdObject==null){
			throw new NullArgumentException("Wrong argument outputSdObject==null");
		}
		
		_inputSdObject=inputSdObject;
		_outputSdObject=outputSdObject;
	}

	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public SdSystemBridge getSdSystemBridge()throws SerializationException{
		if(_inputSdObject==null){
			throw new SerializationException("Serialization failed because _inputSdObject property is null");
		}
		
		if(_outputSdObject==null){
			throw new SerializationException("Serialization failed because _outputSdObject property is null");
		}
		
		if(!(_inputSdObject instanceof SerializableInputSdObject)){
			throw new SerializationException("Serialization failed because _outputSdObject property is not an instance of InputSdObject");
		}
		
		if(!(_outputSdObject instanceof SerializableOutputSdObject)){
			throw new SerializationException("Serialization failed because _outputSdObject property is not an instance of OutputSdObject");
		}
		
		try {
			return new SdSystemBridge((InputSdObject)_inputSdObject.getSdObject(), (OutputSdObject)_outputSdObject.getSdObject());
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
	
	/**
	 * @param inputSdObject the inputSdObject to set
	 */
	public void setInputSdObject(ISerializableSdObject inputSdObject) {
		_inputSdObject = inputSdObject;
	}

	/**
	 * @return the inputSdObject
	 */
	public ISerializableSdObject getInputSdObject() {
		return _inputSdObject;
	}

	/**
	 * @param outputSdObject the outputSdObject to set
	 */
	public void setOutputSdObject(ISerializableSdObject outputSdObject) {
		_outputSdObject = outputSdObject;
	}

	/**
	 * @return the outputSdObject
	 */
	public ISerializableSdObject getOutputSdObject() {
		return _outputSdObject;
	}
}
