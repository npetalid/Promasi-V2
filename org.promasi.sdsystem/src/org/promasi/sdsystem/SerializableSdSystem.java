package org.promasi.sdsystem;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SerializableSdSystem extends SerializableObject{
	
	/**
	 * 
	 */
	private Map<String, ISerializableSdObject> _sdObjects;
	
	/**
	 * 
	 */
	public SerializableSdSystem(){
		_sdObjects=new TreeMap<String, ISerializableSdObject>();
	}
	
	/**
	 * 
	 * @param sdSystem
	 * @throws NullArgumentException
	 * @throws SerializationException 
	 */
	public SerializableSdSystem(final SdSystem sdSystem)throws NullArgumentException, IllegalArgumentException, SerializationException{
		if(sdSystem==null){
			throw new NullArgumentException("Wrong argument sdSystem==null");
		}
		
		_sdObjects=new TreeMap<String, ISerializableSdObject>();
		for(Map.Entry<String, ISdObject> entry : sdSystem._sdObjects.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				throw new IllegalArgumentException("Wrong argument sdSystem contains invalid sdObject entries");
			}

			_sdObjects.put(entry.getKey(), entry.getValue().getSerializableSdObject());
		}
	}
	
	/**
	 * 
	 * @param sdObjects
	 */
	public void setSdObjects(Map<String, ISerializableSdObject> sdObjects){
		_sdObjects=sdObjects;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, ISerializableSdObject> getSdObjects(){
		return _sdObjects;
	}

	
	public SdSystem getSdSystem() throws SerializationException {
		if(_sdObjects==null){
			throw new SerializationException("Serialization failed because _sdObject property is null");
		}
		
		Map<String, ISdObject> sdObjects=new TreeMap<String, ISdObject>();
		for(Map.Entry<String, ISerializableSdObject> entry : _sdObjects.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				throw new SerializationException("Serialization failed because _sdObjects property is invalid");
			}
			
			if( !entry.getKey().equals(SdSystem.CONST_TIME_SDOBJECT_NAME) ){
				sdObjects.put(entry.getKey(), entry.getValue().getSdObject());
			}
		}
		
		try {
			return new SdSystem(sdObjects);
		} catch (IllegalArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
}