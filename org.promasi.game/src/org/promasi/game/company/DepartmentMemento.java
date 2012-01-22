/**
 * 
 */
package org.promasi.game.company;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.utilities.serialization.SerializationException;

/**
 * @author alekstheod
 *
 */
public class DepartmentMemento {
	
	/**
	 * 
	 */
	private Map<String, EmployeeMemento> _employees;
	
	/**
	 * 
	 */
	public DepartmentMemento(){
		
	}
	
	/**
	 * 
	 * @param department
	 * @throws SerializationException
	 */
	public DepartmentMemento( Department department ){
    	_employees=new TreeMap<String, EmployeeMemento>();
    	for(Map.Entry<String, Employee> entry : department._employees.entrySet()){
    		_employees.put(entry.getKey(), entry.getValue().getMemento());
    	}
	}

	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public Department getDepartment()throws SerializationException{
		Department result = new Department();
		if(_employees!=null){
			for(Map.Entry<String, EmployeeMemento> entry : _employees.entrySet()){
				if(entry.getKey()==null || entry.getValue()==null){
					throw new SerializationException("Serialization failed becuase employee list is invalid");
				}
				
				result.hireEmployee(result.getDirector(), entry.getValue().getEmployee(), null);
			}
		}
		
		return result;
	}
	
	/**
	 * @return the _employees
	 */
	public Map<String, EmployeeMemento> getEmployees() {
		return _employees;
	}

	/**
	 * @param _employees the _employees to set
	 */
	public void setEmployees(Map<String, EmployeeMemento> _employees) {
		this._employees = _employees;
	}
}
