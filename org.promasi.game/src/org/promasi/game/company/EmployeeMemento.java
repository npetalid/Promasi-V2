/**
 * 
 */
package org.promasi.game.company;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.game.GameException;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class EmployeeMemento extends SerializableObject 
{
	/**
	 * 
	 */
	private String _firstName;

	/**
	 * 
	 */
	private String _lastName;

	/**
	 * 
	 */
	private String _employeeId;

	/**
     * The salary of the employee.
     */
    private double _salary;

    /**
     * The CV of the employee.
     */
    private String _curriculumVitae;
    
    /**
	 * 
	 */
	private Map<String, EmployeeTaskMemento> _tasks;
    
    /**
     * 
     */
    private Map<String, Double> _employeeSkills;
    
    /**
     * 
     */
    public EmployeeMemento(){
    	
    }
    
    /**
     * 
     * @param employee
     * @throws NullArgumentException
     */
    public EmployeeMemento(Employee employee){
    	_salary=employee._salary;
    	_curriculumVitae=employee._curriculumVitae;
    	_employeeSkills=employee._employeeSkills;
    	_firstName=employee.getFirstName();
    	_lastName=employee.getLastName();
    	_employeeId=employee.getEmployeeId();
    	
    	_tasks = new TreeMap<String, EmployeeTaskMemento>();
    	for(Map.Entry<String, EmployeeTask> entry : employee._employeeTasks.entrySet()){
    		_tasks.put(entry.getKey(), entry.getValue().getMemento());
    	}
    }
    
    public Employee getEmployee()throws SerializationException{
    	if(_firstName==null){
    		throw new SerializationException("Serialization failed because _firstName property is null");
    	}
    	
    	if(_lastName==null){
    		throw new SerializationException("Serialization failed because _lastName property is null");
    	}
    	
    	if(_employeeId==null){
    		throw new SerializationException("Serialization failed because _personId property is null");
    	}
    	
    	if(_curriculumVitae==null){
    		throw new SerializationException("Serialization failed because _curriculumVitae property is null");
    	}
    	
    	if(_employeeSkills==null){
    		throw new SerializationException("Serialization failed because _employeeSkills property is null");
    	}
    	
    	try{
    		Employee employee = new Employee(_firstName, _lastName, _employeeId, _curriculumVitae, _salary, _employeeSkills);
    		return employee;
    	}catch(GameException e){
    		throw new SerializationException("Serialization failed because " + e.getMessage());
    	}
    }

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		_salary = salary;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return _salary;
	}

	/**
	 * @param curriculumVitae the curriculumVitae to set
	 */
	public void setCurriculumVitae(String curriculumVitae) {
		_curriculumVitae = curriculumVitae;
	}

	/**
	 * @return the curriculumVitae
	 */
	public String getCurriculumVitae() {
		return _curriculumVitae;
	}

	/**
	 * @param employeeSkills the employeeSkills to set
	 */
	public void setEmployeeSkills(Map<String, Double> employeeSkills) {
		_employeeSkills = employeeSkills;
	}

	/**
	 * @return the employeeSkills
	 */
	public Map<String, Double> getEmployeeSkills() {
		return _employeeSkills;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEmployeeId() {
		return _employeeId;
	}

	/**
	 * 
	 * @param employeeId
	 */
	public void setEmployeeId(String employeeId) {
		_employeeId = employeeId;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getLastName() {
		return _lastName;
	}

	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		_lastName = lastName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	/**
	 * 
	 * @param tasks
	 */
	public void setTasks( Map<String, EmployeeTaskMemento> tasks){
		_tasks = tasks;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, EmployeeTaskMemento> getTasks(){
		return _tasks;
	}
}
