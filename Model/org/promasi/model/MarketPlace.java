/**
 * 
 */
package org.promasi.model;

import java.util.Map;
import java.util.TreeMap;


import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class MarketPlace {
	/**
	 * 
	 */
	private Map<Integer,Employee> _employees;
	
	/**
	 * 
	 */
	private IMarketPlaceEventHandler _eventHandler;
	
	/**
	 * 
	 */
	public MarketPlace()
	{
		_employees=new TreeMap<Integer,Employee>();
	}
	
	
	/**
	 * 
	 * @param employees
	 * @throws NullArgumentException
	 */
	public MarketPlace(Map<Integer,Employee> employees)throws NullArgumentException
	{
		if(employees==null)
		{
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		for(Map.Entry<Integer, Employee> entry : employees.entrySet())
		{
			if(entry.getKey()==null || entry.getValue()==null)
			{
				throw new NullArgumentException("Wrong argument employees");
			}
		}
		
		_employees=employees;
	}
	
	
	/**
	 * 
	 * @param employees
	 * @throws NullArgumentException
	 */
	public synchronized void setEmployees(Map<Integer,Employee> employees)throws NullArgumentException
	{
		if(employees==null)
		{
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		for(Map.Entry<Integer, Employee> entry : employees.entrySet())
		{
			if(entry.getKey()==null || entry.getValue()==null)
			{
				throw new NullArgumentException("Wrong argument employees");
			}
		}
		
		_employees=employees;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized Map<Integer,Employee> getEmployees()
	{
		return _employees;
	}
	
	
	/**
	 * 
	 * @param employeeId
	 * @return
	 */
	public synchronized boolean hireEmployee(Integer employeeId)
	{
		if(!_employees.containsKey(employeeId))
		{
			return false;
		}
		
		if(_eventHandler!=null)
		{
			_eventHandler.hireEmployee(employeeId);
		}
		
		_employees.get(employeeId).hireEmployee();
		return true;
	}
	
	
	/**
	 * 
	 * @param employeeId
	 * @return
	 */
	public synchronized boolean dischargeEmployee(Integer employeeId)
	{
		if(!_employees.containsKey(employeeId))
		{
			return false;
		}
		
		if(_eventHandler!=null)
		{
			_eventHandler.dischargeEmployee(employeeId);
		}
		
		_employees.get(employeeId).discharge();
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized Map<Integer, Employee> getAvailableEmployees()
	{
		Map<Integer, Employee> employees=new TreeMap<Integer, Employee>();
		for(Map.Entry<Integer, Employee> entry : _employees.entrySet())
		{
			if(!entry.getValue().isHired())
			{
				employees.put(entry.getKey(), entry.getValue());
			}
		}
		
		return employees;
	}
	
	
	/**
	 * 
	 * @param eventHandler
	 */
	public synchronized void registerEventHandler(IMarketPlaceEventHandler eventHandler)
	{
		_eventHandler=eventHandler;
	}
}
