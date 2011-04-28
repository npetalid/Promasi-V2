package org.promasi.game.company;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

public class SerializableMarketPlace extends SerializableObject
{
	/**
	 * 
	 */
	private Map<String, SerializableEmployee> _availableEmployees;

	/**
	 * 
	 */
	public SerializableMarketPlace(){
	}
	
	/**
	 * 
	 * @param marketPlace
	 * @throws NullArgumentException
	 * @throws SerializationException 
	 */
	public SerializableMarketPlace(final MarketPlace marketPlace)throws NullArgumentException, SerializationException
	{
		if(marketPlace==null){
			throw new NullArgumentException("Wrong argumnet marketPlace==null");
		}
		
		_availableEmployees=new TreeMap<String, SerializableEmployee>();
		for(Map.Entry<String, Employee> entry : marketPlace._availabelEmployees.entrySet()){
			_availableEmployees.put(entry.getKey(), entry.getValue().getSerializableEmployee());
		}
	}
	
	public MarketPlace getMarketPlace()throws SerializationException{
		if(_availableEmployees==null){
			throw new SerializationException("Serialization failed because _availableEmployees property is null");
		}
		
		Map<String, Employee> availableEmployees=new TreeMap<String, Employee>();
		for(Map.Entry<String, SerializableEmployee> entry : _availableEmployees.entrySet()){
			availableEmployees.put(entry.getKey(), entry.getValue().getEmployee());
		}
		
		try {
			return new MarketPlace(availableEmployees);
		} catch (IllegalArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
	
	/**
	 * @param availableEmployees the availableEmployees to set
	 */
	public void setAvailableEmployees(Map<String, SerializableEmployee> availableEmployees) {
		_availableEmployees = availableEmployees;
	}

	/**
	 * @return the availableEmployees
	 */
	public Map<String, SerializableEmployee> getAvailableEmployees() {
		return _availableEmployees;
	}
}
