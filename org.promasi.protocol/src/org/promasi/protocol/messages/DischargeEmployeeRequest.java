/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class DischargeEmployeeRequest extends SerializableObject 
{
	/**
	 * 
	 */
	private String _employeeId;

	/**
	 * 
	 */
	public DischargeEmployeeRequest(){}
	
	/**
	 * 
	 * @param employeeId
	 */
	public DischargeEmployeeRequest(String employeeId){
		_employeeId=employeeId;
	}
	
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		_employeeId = employeeId;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return _employeeId;
	}
	
	
}
