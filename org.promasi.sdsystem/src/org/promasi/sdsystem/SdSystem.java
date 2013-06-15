package org.promasi.sdsystem;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.promasi.sdsystem.model.generated.SdSystemModel;
import org.promasi.sdsystem.sdobject.FlowSdObject;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;
import org.promasi.sdsystem.sdobject.TimeSdObject;

/**
 * 
 * @author m1cRo
 * Represent an simulation system,
 * based on system dynamics concept.
 * This class contains a list of {@link=ISdObject} which
 * represents the system dynamics objects, such as stock,
 * flow, input, output and so on.
 */
public class SdSystem implements ISdSystem
{
	/**
	 * Name of dynamically generated time object.
	 * Needed to count the clock ticks. Time object will be generated
	 * automatically in the class constructor as a part of the
	 * system dynamics model.
	 */
	public static final String CONST_TIME_SDOBJECT_NAME="time";
	
	/**
	 * Regular expression which represent the name of an sdObject. If some of the
	 * given objects does not match with the current regular expression, constructor
	 * will throw an instance of {@link = SdSystemException}.
	 */
	public static final Pattern CONST_ALPHABET_PATTERN = Pattern.compile("[A-Z0-9a-z]+");
	
	/**
	 * List of {@link = ISdObject} instances which represents the current simulation
	 * system.
	 */
	protected Map<String, ISdObject> _sdObjects;
	
	/**
	 * Lock object needed for cross thread data synchronization.
	 */
	private Lock _lockObject;
	
	/**
	 * Constructor will initialize the object.
	 * @param sdObjects List of the available {@link = ISdObject} instances.
	 * @throws SdSystemException In case of initialization error.
	 */
	public SdSystem( final Map<String, ISdObject> sdObjects)throws SdSystemException{
		if(sdObjects==null){
			throw new SdSystemException("Wrong argument sdObjects==null");
		}
		
		if(sdObjects.isEmpty()){
			throw new SdSystemException("Wrong argument sdObjects is empty");
		}
		
		if(sdObjects.containsKey(CONST_TIME_SDOBJECT_NAME)){
			sdObjects.remove(CONST_TIME_SDOBJECT_NAME);
		}
		
		for(Map.Entry<String, ISdObject> entry : sdObjects.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				throw new SdSystemException("Wrong argument sdObjects contains null");
			}
			
			Matcher matcher=CONST_ALPHABET_PATTERN.matcher(entry.getKey());
			if(!matcher.matches()){
				throw new SdSystemException("Wrong argument sdObjects contains invalid objectId");
			}
		}
		
		Map<String, ISdObject> systemObjects=new TreeMap<String, ISdObject>(sdObjects);
		systemObjects.put(CONST_TIME_SDOBJECT_NAME, new TimeSdObject());
		_sdObjects=systemObjects;
		
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			if(!entry.getValue().executeStep(_sdObjects)){
				throw new SdSystemException("Wrong argument sdObjects invalid system step execution failed");
			}
		}
		
		_lockObject = new ReentrantLock();
	}
	
	/**
	 * Will execute a simulation step. On each step time value
	 * will be incremented and all the values of available system dynamics
	 * objects will be calculated.
	 * @return true on succeed, false otherwise.
	 */
	public boolean executeStep(){
		boolean result = true;
		
		try{
			_lockObject.lock();
			for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
				if( entry.getValue() instanceof FlowSdObject){
					result &= entry.getValue().executeStep(_sdObjects);
				}
			}
			
			for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
				if(entry.getValue() instanceof StockSdObject ){
					result &= entry.getValue().executeStep(_sdObjects);
				}
			}
			
			for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
				if(entry.getValue() instanceof OutputSdObject ){
					result &= entry.getValue().executeStep(_sdObjects);
				}
			}

			ISdObject time=_sdObjects.get(CONST_TIME_SDOBJECT_NAME);
			result &= time.executeStep(_sdObjects);
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * Will set the given value to the input sdObject with the given name. 
	 * If no input object with the same name
	 * exists in the objects list the method will return false.
	 * @param inputName SdObject name. 
	 * @param value.
	 * @return true in case of succeed, false otherwise.
	 */
	public boolean setInput(final String inputName, final Double value){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( inputName != null || value != null)
			{
				if(_sdObjects.containsKey(inputName)){
					ISdObject sdObject=_sdObjects.get(inputName);
					if( sdObject instanceof InputSdObject ){
						InputSdObject inputSdObject=(InputSdObject)sdObject;
						result = inputSdObject.setValue(value);
					}
				}
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * Will return the value of the given SdObject.
	 * @param sdObjectName name of SdObject which value we go to get.
	 * @return value.
	 * @throws SdSystemException if case of invalid sdObjectName argument.
	 */
	public Double getValue(final String sdObjectName)throws SdSystemException{
		if(sdObjectName==null){
			throw new SdSystemException("Wrong argument sdObjectName==null");
		}
		
		if(!_sdObjects.containsKey(sdObjectName)){
			throw new SdSystemException("Wrong argument sdObjectName no in sdObjects list");
		}
		
		try{
			_lockObject.lock();
			return _sdObjects.get(sdObjectName).getValue();
		}finally{
			_lockObject.unlock();
		}
	}
	
	/**
	 * Will return the state of the current object.
	 * @return instance of {@link = SdSystemMemento} which
	 * represent the state of the object.
	 */
	public SdSystemModel getMemento() {
		SdSystemModel model = new SdSystemModel();

		model.setSdObjects(new SdSystemModel.SdObjects());
		for( Map.Entry<String, ISdObject> entry : _sdObjects.entrySet() ){
			SdSystemModel.SdObjects.Entry newEntry = new SdSystemModel.SdObjects.Entry();
			newEntry.setKey(entry.getKey());
			newEntry.setValue(entry.getValue().getMemento());
			model.getSdObjects().getEntry().add(newEntry);
		}
		
		return model;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map< String, Double > getSystemValues(){
		Map<String, Double> values=new TreeMap<String, Double>();
		for(Map.Entry<String, ISdObject> entry : _sdObjects.entrySet()){
			values.put(entry.getKey(), entry.getValue().getValue());
		}
		
		return values;
	}

	/**
	 * 
	 * @param inputName
	 * @return
	 * @throws SdSystemException
	 */
	public boolean hasInput(String inputName) throws SdSystemException {
		if(inputName==null){
			throw new SdSystemException("Wrong argument inputName==null");
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
	 * Will check if a current instance of {@link SdSystem}
	 * contains an output object with the given name.
	 * @param outputName the output object's name.
	 * @exception SdSystemException in case of invalid
	 * arguments.
	 */
	public boolean hasOutput(String outputName) throws SdSystemException {
		if(outputName==null){
			throw new SdSystemException("Wrong argument outputName==null");
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
