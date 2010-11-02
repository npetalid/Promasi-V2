/**
 * 
 */
package org.promasi.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;


import org.apache.commons.lang.NullArgumentException;


/**
 * @author m1cRo
 *
 */
public class MarketPlace 
{
	/**
	 * 
	 */
	private Map<String,Employee> _availabelEmployees;
	
	/**
	 * 
	 */
	private Map<String,Employee> _hiredEmployees;
	
	/**
	 * 
	 */
	private IMarketPlaceEventHandler _eventHandler;
	
	/**
	 * 
	 */
	public MarketPlace()
	{
		_availabelEmployees=new TreeMap<String, Employee>();
		_hiredEmployees=new TreeMap<String, Employee>();
	}
	
	/**
	 * 
	 * @param availabelEmployees
	 * @throws NullArgumentException
	 */
	public MarketPlace(List<Employee> availabelEmployees)throws NullArgumentException
	{
		if(availabelEmployees==null)
		{
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		_availabelEmployees=new TreeMap<String,Employee>();
		for(Employee employee : availabelEmployees)
		{
			if(employee==null)
			{
				throw new NullArgumentException("Wrong argument employees");
			}
			
			_availabelEmployees.put(employee.getPersonId(), employee);
		}
		
		
		_hiredEmployees=new TreeMap<String, Employee>();
	}
	
	
	/**
	 * 
	 * @param employee
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean hireEmployee(Employee employee)throws NullArgumentException
	{
		if(employee==null)
		{
			throw new NullArgumentException("Wrong argument employee==null");
		}
		
		if(!_availabelEmployees.containsKey(employee.getPersonId()))
		{
			return false;
		}
		
		_availabelEmployees.remove(employee.getPersonId());
		
		_hiredEmployees.put(employee.getPersonId(), employee);
		
		if(_eventHandler!=null)
		{
			_eventHandler.hireEmployee(employee);
		}
		

		return true;
	}
	
	
	/**
	 * 
	 * @param employeeId
	 * @return
	 */
	public synchronized boolean dischargeEmployee(Employee employee)
	{
		if(employee==null)
		{
			throw new NullArgumentException("Wrong argument employee==null");
		}
		
		if(!_hiredEmployees.containsKey(employee.getPersonId()))
		{
			return false;
		}
		
		_hiredEmployees.remove(employee.getPersonId());
		_availabelEmployees.put(employee.getPersonId(), employee);
		
		if(_eventHandler!=null)
		{
			_eventHandler.dischargeEmployee(employee);
		}
		
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized List<Employee> getAvailableEmployeesList()
	{
		List<Employee> result = new Vector<Employee>();
		for(Map.Entry<String, Employee> entry : _availabelEmployees.entrySet())
		{
			result.add(entry.getValue());
		}
		
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public synchronized List<Employee> getHiredEmployeesList()
	{
		List<Employee> result = new Vector<Employee>();
		for(Map.Entry<String, Employee> entry : _hiredEmployees.entrySet())
		{
			result.add(entry.getValue());
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized List<Employee> getAllEmployees()
	{
		List<Employee> result = new Vector<Employee>();
		for(Map.Entry<String, Employee> entry : _hiredEmployees.entrySet())
		{
			result.add(entry.getValue());
		}
		
		for(Map.Entry<String, Employee> entry : _availabelEmployees.entrySet())
		{
			result.add(entry.getValue());
		}

		return result;
	}
	
	/**
	 * 
	 * @param eventHandler
	 */
	public synchronized void registerEventHandler(IMarketPlaceEventHandler eventHandler)
	{
		_eventHandler=eventHandler;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Map<String, Employee> getAvailableEmployees()
	{
		return _availabelEmployees;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Map<String, Employee> getHiredEmployees()
	{
		return _hiredEmployees;
	}
	
	/**
	 * 
	 * @param availableEmployees
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setAvailableEmployees(Map<String, Employee> availableEmployees)throws NullArgumentException, IllegalArgumentException
	{
		if(availableEmployees==null)
		{
			throw new NullArgumentException("Wrong argumen availableEmployees==null");
		}
		
		for( Map.Entry<String, Employee> entry : availableEmployees.entrySet() )
		{
			if(_hiredEmployees.containsKey( entry.getKey() ) )
			{
				throw new IllegalArgumentException("Wrong argument availableEmployees conflicts with _hiredEmployees");
			}
		}
		
		_availabelEmployees=availableEmployees;
	}
	
	/**
	 * 
	 * @param hiredEmployees
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setHiredEmployees(Map<String, Employee> hiredEmployees)throws NullArgumentException, IllegalArgumentException
	{
		if(hiredEmployees==null)
		{
			throw new NullArgumentException("Wrong argumen hiredEmployees==null");
		}
		
		for( Map.Entry<String, Employee> entry : hiredEmployees.entrySet() )
		{
			if(_availabelEmployees.containsKey( entry.getKey() ) )
			{
				throw new IllegalArgumentException("Wrong argument hiredEmployees conflicts with _availableEmployees");
			}
		}
		
		_hiredEmployees=hiredEmployees;
	}
}
