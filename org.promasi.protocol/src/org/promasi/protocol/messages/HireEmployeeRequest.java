/**
 * 
 */
package org.promasi.protocol.messages;

/**
 * @author m1cRo
 *
 */
public class HireEmployeeRequest extends Message 
{	
	/**
	 * 
	 */
	private String _employeeId;

	/**
	 * 
	 */
	public HireEmployeeRequest(){}
	
	/**
	 * 
	 * @param employee
	 */
	public HireEmployeeRequest(String employeeId){
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
