/**
 * 
 */
package org.promasi.game.company;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalTime;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class SerializableCompany extends SerializableObject {
	/**
     * The name of the company.
     */
    private String _name;

    /**
     * Description about the company.
     */
    private String _description;

    /**
     * The time that the company starts working.
     */
    private String _startTime;

    /**
     * The time that the company stops working.
     */
    private String _endTime;

    /**
     * 
     */
    private double _budget;
    
    /**
     * 
     */
    private double _prestigePoints;

	/**
     * 
     */
    private Map<String, SerializableEmployee> _employees;

	/**
     * 
     */
    public SerializableCompany(){
    }
    
    /**
     * 
     * @param company
     * @throws NullArgumentException
     */
    public SerializableCompany(final Company company)throws NullArgumentException, SerializationException{
    	if(company==null){
    		throw new NullArgumentException("Wrong argument company==null");
    	}
    	
    	setStartTime(company._startTime.toString());
    	setEndTime(company._endTime.toString());
    	_budget=company._budget;
    	_description=company._description;
    	_name=company._name;
    	_prestigePoints=company._prestigePoints;
    	
    	_employees=new TreeMap<String, SerializableEmployee>();
    	for(Map.Entry<String, Employee> entry : company._employees.entrySet()){
    		_employees.put(entry.getKey(), entry.getValue().getSerializableEmployee());
    	}
    }
    
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		_description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		_startTime = startTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return _startTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		_endTime = endTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return _endTime;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(double budget) {
		_budget = budget;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return _budget;
	}
	
    /**
	 * @return the prestigePoints
	 */
	public double getPrestigePoints() {
		return _prestigePoints;
	}

	/**
	 * @param prestigePoints the _prestigePoints to set
	 */
	public void setPrestigePoints(double prestigePoints) {
		this._prestigePoints = prestigePoints;
	}
    
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
    public Company getCompany()throws SerializationException{
    	if(_name==null){
    		throw new SerializationException("Serialization failed because _name property is null");
    	}
    	
    	if(_description==null){
    		throw new SerializationException("Serialization failed because _description property is null");
    	}
    	
    	if(_startTime==null){
    		throw new SerializationException("Serialization failed because _startTime property is null");
    	}
    	
    	if(_endTime==null){
    		throw new SerializationException("Serialization failed because _endTime property is null");
    	}
    	
    	try{
    		Company company=new Company(_name, _description, new LocalTime(_startTime), new LocalTime(_endTime), _budget, _prestigePoints);
    		if(_employees!=null){
    			for(Map.Entry<String, SerializableEmployee> entry : _employees.entrySet()){
    				if(entry.getKey()==null || entry.getValue()==null){
    					throw new SerializationException("Serialization failed becuase employee list is invalid");
    				}
    				
    				company.hireEmployee(entry.getValue().getEmployee());
    			}
    		}
    		
    		return company;
    	}catch(IllegalArgumentException e){
    		throw new SerializationException("Serialization failed because "+e.getMessage());
    	}catch(NullArgumentException e){
    		throw new SerializationException("Serialization failed because " + e.getMessage());
    	}
    }

    /**
     * 
     * @return
     */
    public Map<String, SerializableEmployee> getEmployees() {
		return _employees;
	}

    /**
     * 
     * @param employees
     */
	public void setEmployees(Map<String, SerializableEmployee> employees) {
		_employees = employees;
	}
}
