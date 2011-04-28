/**
 * 
 */
package org.promasi.game.company;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
		
		_availabelEmployees=employees;
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public synchronized SerializableMarketPlace getSerializableMarketPlace()throws SerializationException{
		try {
			return new SerializableMarketPlace(this);
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException 
	 */
	public synchronized Map<String, SerializableEmployee> getAvailableEmployees() throws SerializationException{
		Map<String, SerializableEmployee> employees=new TreeMap<String, SerializableEmployee>();
		for(Map.Entry<String, Employee> entry : _availabelEmployees.entrySet()){
			employees.put(entry.getKey(), entry.getValue().getSerializableEmployee());
		}
		
		return employees;
	}
	
	/**
	 * 
	 * @param employee
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean isEmployeeAvailable(final SerializableEmployee employee)throws NullArgumentException{
		if(employee==null){
			throw new NullArgumentException("Wrong argument employee==null");
		}
		
		if( _availabelEmployees.containsKey( employee.getEmployeeId() ) ){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param company
	 * @param employeeId
	 * @return
	 * @throws NullArgumentException
	 * @throws SerializationException
	 */
	public synchronized boolean hireEmployee(final Company company, final String employeeId)throws NullArgumentException, SerializationException{
		if(employeeId==null){
			throw new NullArgumentException("Wrong argument employeeId==null");
		}
		
		if( _availabelEmployees.containsKey( employeeId ) ){
			Employee tmpEmployee=_availabelEmployees.get(employeeId);
			_availabelEmployees.remove( employeeId );
			if(!company.hireEmployee( tmpEmployee ) ){
				_availabelEmployees.put(employeeId, tmpEmployee);
				return false;
			}
		}else{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean dischargeEmployee(final Employee employee)throws NullArgumentException{
		try {
			if(employee==null){
				throw new NullArgumentException("Wrong argument employee==null");
			}
			
			if( _availabelEmployees.containsKey( employee.getEmployeeId() ) ){
				return false;
			}
			
			employee.removeAllTasks();
			_availabelEmployees.put(employee.getEmployeeId(), employee);
		} catch (SerializationException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param employees
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void addEmployees(final List<Employee> employees)throws NullArgumentException, IllegalArgumentException{
		if(employees==null){
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		for(Employee employee : employees){
			if( employee==null ){
				throw new IllegalArgumentException("Wrong argument employees contains null");
			}
		}
		
		for(Employee employee : employees){
			if( !_availabelEmployees.containsKey(employee.getEmployeeId() ) ){
				_availabelEmployees.put(employee.getEmployeeId(), employee);
			}
		}
	}
}
