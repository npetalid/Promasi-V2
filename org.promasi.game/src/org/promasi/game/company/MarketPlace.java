/**
 * 
 */
package org.promasi.game.company;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.game.GameException;
import org.promasi.utilities.design.Observer;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 * Represent the market place in ProMaSi
 * system. This class contains the list of
 * employees.
 */
public class MarketPlace extends Observer<IMarketPlaceListener>
{
	/**
	 * List of available in market place employees.
	 */
	protected Map<String,Employee> _availabelEmployees;
	
	/**
	 * The lock object in order to synchronize
	 * the employees list.
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 * @param employees
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public MarketPlace(final Map<String, Employee> employees)throws GameException{
		if(employees==null){
			throw new GameException("Wrong argument employees==null");
		}
		
		for(Map.Entry<String, Employee> entry : employees.entrySet()){
			String employeeId=entry.getKey();
			Employee employee=entry.getValue();
			if(employeeId==null || employee==null || !employeeId.equals(employee.getEmployeeId() )){
				throw new GameException("Wrong argument employees");
			}
		}
		
		_lockObject = new ReentrantLock();
		_availabelEmployees=employees;
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
	public boolean hireEmployee( Company company, String employeeId, DateTime time){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(employeeId!=null){
				if( _availabelEmployees.containsKey( employeeId ) ){
					Employee tmpEmployee=_availabelEmployees.get(employeeId);
					_availabelEmployees.remove( employeeId );
					if(!company.hireEmployee( tmpEmployee, time ) ){
						_availabelEmployees.put(employeeId, tmpEmployee);
					}else{
						for( IMarketPlaceListener listener : getListeners()){
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
	 * @param employee
	 * @return
	 */
	public boolean dischargeEmployee(final Employee employee){
		boolean result = false;
		try {
			_lockObject.lock();
			if(employee!=null){
				if( !_availabelEmployees.containsKey( employee.getEmployeeId() ) ){
					employee.removeAllTasks();
					
					_availabelEmployees.put(employee.getEmployeeId(), employee);
					for( IMarketPlaceListener listener : getListeners()){
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
	
	@Override
	public boolean addListener( IMarketPlaceListener listener ){
		boolean result = super.addListener(listener);
		if( result ){
			listener.MarketPlaceChanged(getMemento());
		}
		
		return result;
	}
}
