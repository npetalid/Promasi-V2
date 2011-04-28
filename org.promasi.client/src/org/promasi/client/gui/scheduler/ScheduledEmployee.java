package org.promasi.client.gui.scheduler;

import org.promasi.game.company.SerializableEmployee;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * 
 * @author m1cRo
 *
 */
public class ScheduledEmployee 
{
	/**
	 * 
	 */
	private String _employeeId;
	
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
	private String _curriculumVitae;
	
	/**
	 * 
	 */
	private int _workingDuration;
	
	/**
	 * 
	 * @param employeeId
	 * @param firstName
	 * @param lastName
	 * @throws NullArgumentException
	 */
	public ScheduledEmployee(String employeeId, String firstName, String lastName, String curriculumVitae, int workingDuration)throws NullArgumentException{
		if(employeeId==null){
			throw new NullArgumentException("Wrong argument employeeId==null");
		}
		
		if(firstName==null){
			throw new NullArgumentException("Wrong argument firstName==null");
		}
		
		if(lastName==null){
			throw new NullArgumentException("Wrong argument lastName==null");
		}
		
		if(workingDuration<=0){
			throw new IllegalArgumentException("Wrong argument workingDuration<=0");
		}
		
		if(curriculumVitae==null){
			throw new NullArgumentException("Wrong argument curriculumVitae==null");
		}
		
		_employeeId=employeeId;
		_firstName=firstName;
		_lastName=lastName;
		_workingDuration=workingDuration;
		_curriculumVitae=curriculumVitae;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCurriculumVitae(){
		return _curriculumVitae;
	}
	
	/**
	 * 
	 * @return
	 */
	public SerializableEmployee getSerializableEmployee(){
		SerializableEmployee employee=new SerializableEmployee();
		employee.setFirstName(_firstName);
		employee.setLastName(_lastName);
		employee.setEmployeeId(_employeeId);
		employee.setCurriculumVitae(_curriculumVitae);
		return employee;
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
	 * @return
	 */
	public String getLastName() {
		return _lastName;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getWorkingDuration(){
		return _workingDuration;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEmployeeId(){
		return _employeeId;
	}
}
