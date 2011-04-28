package org.promasi.sdsystem;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.promasi.sdsystem.sdobject.FlowSdObject;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;
import org.promasi.sdsystem.sdobject.TimeSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SdSystem
{
	/**
	 * 
	 */
	public static final String CONST_TIME_SDOBJECT_NAME="time";
	
	/**
	 * 
	 */
	public static final Pattern CONST_ALPHABET_PATTERN = Pattern.compile("[A-Z0-9a-z]+");
	
	/**
	 * 
	 */
	protected Map<String, ISdObject> _sdObjects;
	
	/**
	 * 
	 * @param _sdObjects
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public SdSystem( final Map<String, ISdObject> sdObjects)throws NullArgumentException, IllegalArgumentException{
		if(sdObjects==null){
			throw new NullArgumentException("Wrong argument sdObjects==null");
		}
		
		if(sdObjects.isEmpty()){
			throw new IllegalArgumentException("Wrong argument sdObjects is empty");
		}
		
		if(sdObjects.containsKey(CONST_TIME_SDOBJECT_NAME)){
			throw new IllegalArgumentException("Wrong argument sdObjects contains time");
		}
		
		for(Map.Entry<String, ISdObject> entry : sdObjects.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				throw new IllegalArgumentException("Wrong argument sdObjects contains null");
			}
			
			Matcher matcher=CONST_ALPHABET_PATTERN.matcher(entry.getKey());
			if(!matcher.matches()){
				throw new IllegalArgumentException("Wrong argument sdObjects contains invalid objectId");
			}
		}
		
		Map<String, ISdObject> systemObjects=new TreeMap<String, ISdObject>(sdObjects);
		systemObjects.put(CONST_TIME_SDOBJECT_NAME, new TimeSdObject());
		
		for(Map.Entry<String, ISdObject> entry : systemObjects.entrySet()){
			if(!entry.getValue().executeStep(systemObjects)){
				throw new IllegalArgumentException("Wrong argument sdObjects invalid system");
			}
		}
		
		_sdObjects=systemObjects;
		
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			if(!entry.getValue().executeStep(_sdObjects)){
				throw new IllegalArgumentException("Wrong argument sdObjects invalid system step execution failed");
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized boolean executeStep(){
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			if( entry.getValue() instanceof FlowSdObject){
				if(!entry.getValue().executeStep(_sdObjects)){
					return false;
				}
			}
		}
		
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			if(entry.getValue() instanceof StockSdObject ){
				if(!entry.getValue().executeStep(_sdObjects)){
					return false;
				}
			}
		}
		
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			if(entry.getValue() instanceof OutputSdObject ){
				if(!entry.getValue().executeStep(_sdObjects)){
					return false;
				}
			}
		}
		
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			System.out.print(String.format("%S, %.2f\n", entry.getKey(), entry.getValue().getValue()));
		}
		
		ISdObject time=_sdObjects.get(CONST_TIME_SDOBJECT_NAME);
		time.executeStep(_sdObjects);
		return true;
	}
	
	/**
	 * 
	 * @param inputName
	 * @param value
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean setInput(final String inputName, final Double value)throws NullArgumentException{
		if(inputName==null)
		{
			throw new NullArgumentException("Wrong argument inputName==null");
		}
		
		if(value==null){
			throw new NullArgumentException("Wrong argument value==null");
		}
		
		if(_sdObjects.containsKey(inputName)){
			ISdObject sdObject=_sdObjects.get(inputName);
			if( sdObject instanceof InputSdObject ){
				InputSdObject inputSdObject=(InputSdObject)sdObject;
				inputSdObject.setValue(value);
			}else{
				return false;
			}
		}else{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param sdObjectName
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized Double getValue(final String sdObjectName)throws NullArgumentException, IllegalArgumentException{
		if(sdObjectName==null){
			throw new NullArgumentException("Wrong argument sdObjectName==null");
		}
		
		if(!_sdObjects.containsKey(sdObjectName)){
			throw new IllegalArgumentException("Wrong argument sdObjectName no in sdObjects list");
		}
		
		return _sdObjects.get(sdObjectName).getValue();
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public SerializableSdSystem getSerializableSdSystem()throws SerializationException {
		try {
			return new SerializableSdSystem(this);
		} catch (IllegalArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
	
	/**
	 * 
	 * @param inputId
	 * @return
	 * @throws NullArgumentException
	 */
	protected InputSdObject getInput(String inputId)throws NullArgumentException, IllegalArgumentException{
		if(inputId==null){
			throw new NullArgumentException("Wrong argument inputId==null");
		}
		
		if(!_sdObjects.containsKey(inputId)){
			throw new IllegalArgumentException("Wrong argument inputId");
		}
		
		ISdObject sdObject=_sdObjects.get(inputId);
		if(sdObject instanceof InputSdObject){
			return (InputSdObject)sdObject;
		}else{
			throw new IllegalArgumentException("Wrong argument inputId");
		}
	}

	/**
	 * 
	 * @param outputId
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	protected OutputSdObject getOutputSdObject(String outputId)throws NullArgumentException, IllegalArgumentException{
		if(outputId==null){
			throw new NullArgumentException("Wrong argument outputId==null");
		}
		
		if(!_sdObjects.containsKey(outputId)){
			throw new IllegalArgumentException("Wrong argument outputId");
		}
		
		ISdObject sdObject=_sdObjects.get(outputId);
		if(sdObject instanceof OutputSdObject){
			return (OutputSdObject)sdObject;
		}else{
			throw new IllegalArgumentException("Wrong argument outputId");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getOutputs() {
		List<String> outputs=new Vector<String>();
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			if(entry.getValue() instanceof OutputSdObject){
				outputs.add(entry.getKey());
			}
		}
		
		return outputs;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getInputs() {
		List<String> inputs=new Vector<String>();
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			if(entry.getValue() instanceof InputSdObject){
				inputs.add(entry.getKey());
			}
		}
		
		return inputs;
	}

	/**
	 * 
	 * @param inputName
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean hasInput(String inputName) throws NullArgumentException {
		if(inputName==null){
			throw new NullArgumentException("Wrong argument inputName==null");
		}
		
		if(!_sdObjects.containsKey(inputName)){
			return false;
		}
		
		if(_sdObjects.get(inputName) instanceof InputSdObject){
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 * @param outputName
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean hasOutput(String outputName) throws NullArgumentException {
		if(outputName==null){
			throw new NullArgumentException("Wrong argument outputName==null");
		}
		
		if(!_sdObjects.containsKey(outputName)){
			return false;
		}
		
		if(_sdObjects.get(outputName) instanceof OutputSdObject){
			return true;
		}
		
		return false;
	}
}
