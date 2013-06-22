/**
 * 
 */
package org.promasi.sdsystem;

import org.promasi.sdsystem.model.generated.SdSystemBridgeModel;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;


/**
 * @author m1cRo
 * Represent the bridge between different
 * instances of {@link SdSystem}. Needed in order
 * to pass information between Sd systems.
 */
public class SdSystemBridge 
{
	/**
	 * 
	 */
	protected OutputSdObject _outputSdObject;
	
	/**
	 * 
	 */
	protected InputSdObject _inputSdObject;
	
	/**
	 * 
	 * @param outputSdObjectId
	 * @param outputSdSystem
	 * @param inputSdObjectId
	 * @param inputSdSystem
	 */
	public SdSystemBridge(String outputSdObjectId, SdSystem outputSdSystem, String inputSdObjectId, SdSystem inputSdSystem)throws SdSystemException{
		if(outputSdObjectId==null){
			throw new SdSystemException("Wrong argument outputSdObjectId==null");
		}
		
		if(inputSdObjectId==null){
			throw new SdSystemException("Wrong argument inputSdObjectId==null");
		}
		
		if(outputSdSystem==null){
			throw new SdSystemException("Wrong argument outputSdSystem==null");
		}
		
		if(inputSdSystem==null){
			throw new SdSystemException("Wrong argument inputSdSystem==null");
		}
		
		if( inputSdSystem == outputSdSystem ){
			throw new SdSystemException("Wrong arguments inputSdSystem and outputSdSystem are the same");
		}
		
		if(!inputSdSystem._sdObjects.containsKey(inputSdObjectId) || !(inputSdSystem._sdObjects.get(inputSdObjectId) instanceof InputSdObject) ){
			throw new SdSystemException("Wrong arguments input named '"+inputSdObjectId+"' does not exist in inputSdSystem");
		}
		
		if(!outputSdSystem._sdObjects.containsKey(outputSdObjectId) || !(outputSdSystem._sdObjects.get(outputSdObjectId) instanceof OutputSdObject) ){
			throw new SdSystemException("Wrong arguments input named '" + inputSdObjectId+ "' does not exist in outputSdSystem");
		}
		
		_inputSdObject=(InputSdObject) inputSdSystem._sdObjects.get(inputSdObjectId);
		_outputSdObject=(OutputSdObject) outputSdSystem._sdObjects.get(outputSdObjectId);
	}
	
	/**
	 * 
	 * @param inputSdObject
	 * @param outputSdObject
	 */
	protected SdSystemBridge(InputSdObject inputSdObject, OutputSdObject outputSdObject)throws SdSystemException{
		if(inputSdObject==null){
			throw new SdSystemException("Wrong argument inputSdObject==null");
		}
		
		if(outputSdObject==null){
			throw new SdSystemException("Wrong argument outputSdObject==null");
		}
		
		_inputSdObject=inputSdObject;
		_outputSdObject=outputSdObject;
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public SdSystemBridgeModel getSerializableSdSystemBridge(){
		SdSystemBridgeModel model = new SdSystemBridgeModel();
		model.setInputSdObject(_inputSdObject.getMemento());
		model.setOutputSdObject(_outputSdObject.getMemento());
		return model;
	}
	
	/**
	 * 
	 */
	public boolean executeStep(){
		return _inputSdObject.setValue( _outputSdObject.getValue() );
	}
}
