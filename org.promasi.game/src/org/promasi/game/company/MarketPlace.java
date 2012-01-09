/**
 * 
 */
package org.promasi.game.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;


/**
 * @author m1cRo
 *
 */
public class MarketPlace 
{
	/**
	 * 
	 */
	protected Map<String,Employee> _availabelEmployees;
	
	/**
	 * 
	 */
	private List<IMarketPlaceListener> _listeners;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 * @param employees
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public MarketPlace(final Map<String, Employee> employees)throws NullArgumentException, IllegalArgumentException{
		if(employees==null){
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		for(Map.Entry<String, Employee> entry : employees.entrySet()){
			String employeeId=entry.getKey();
			Employee employee=entry.getValue();
			if(employeeId==null || employee==null || !employeeId.equals(employee.getEmployeeId() )){
				throw new IllegalArgumentException("Wrong argument employees");
			}
		}
		
		_lockObject = new ReentrantLock();
		_availabelEmployees=employees;
		_listeners = new LinkedList<IMarketPlaceListener>();
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean addListener( IMarketPlaceListener listener ){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( !_listeners.contains(listener)){
				result = _listeners.add(listener);
				listener.MarketPlaceChanged(getMemento());
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeListener( IMarketPlaceListener listener ){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( _listeners.contains(listener)){
				result = _listeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 */
	public void removeAllListeners(){
		try{
			_lockObject.lock();
			_listeners.clear();
		}finally{
			_lockObject.unlock();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public MarketPlaceMemento getMemento(){
		MarketPlaceMemento result = null;
		try {
			_lockObject.lock();
			result = new MarketPlaceMemento(this);
		} finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException 
	 */
	public Map<String, EmployeeMemento> getAvailableEmployees(){
		Map<String, EmployeeMemento> employees = new TreeMap<String, EmployeeMemento>();

		try{
			_lockObject.lock();		
			for(Map.Entry<String, Employee> entry : _availabelEmployees.entrySet()){
			employees.put(entry.getKey(), entry.getValue().getMemento());
		}
		}finally{
			_lockObject.unlock();
		}
		
		return employees;
	}
	
	/**
	 * 
	 * @param employee
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean isEmployeeAvailable(final EmployeeMemento employee){
		boolean result = false;
		
		try{
			_lockObject.lock();		
			if( _availabelEmployees.containsKey( employee.getEmployeeId() ) ){
				result = true;
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @param company
	 * @param employeeId
	 * @return
	 */
	public boolean hireEmployee( Company company, String employeeId){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(employeeId!=null){
				if( _availabelEmployees.containsKey( employeeId ) ){
					Employee tmpEmployee=_availabelEmployees.get(employeeId);
					_availabelEmployees.remove( employeeId );
					if(!company.hireEmployee( tmpEmployee ) ){
						_availabelEmployees.put(employeeId, tmpEmployee);
					}else{
						for( IMarketPlaceListener listener : _listeners){
							listener.MarketPlaceChanged(getMemento());
						}
						
						result = true;
					}
				}
			}
			
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean dischargeEmployee(final Employee employee){
		boolean result = false;
		try {
			_lockObject.lock();
			if(employee!=null){
				if( !_availabelEmployees.containsKey( employee.getEmployeeId() ) ){
					employee.removeAllTasks();
					
					_availabelEmployees.put(employee.getEmployeeId(), employee);
					for( IMarketPlaceListener listener : _listeners){
						listener.MarketPlaceChanged(getMemento());
					}
					
					result = true;
				}		
			}
		} finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param employees
	 * @return
	 */
	public boolean addEmployees(final List<Employee> employees){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( employees != null ){
				
				for(Employee employee : employees){
					if( employee==null ){
						throw new IllegalArgumentException("Wrong argument employees contains null");
					}
				}
				
				boolean employeeAdded = false;
				for(Employee employee : employees){
					if( !_availabelEmployees.containsKey(employee.getEmployeeId() ) ){
						_availabelEmployees.put(employee.getEmployeeId(), employee);
						employeeAdded = true;
					}
				}
				
				if( employeeAdded ){
					for( IMarketPlaceListener listener : _listeners){
						listener.MarketPlaceChanged(getMemento());
					}
				}
			}
			
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
}
