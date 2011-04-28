/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class EmployeeDischargedRequest extends SerializableObject
{
	/**
	 * 
	 */
	private SerializableCompany _company;
	
	/**
	 * 
	 */
	private SerializableMarketPlace _marketPlace;

	/**
	 * 
	 */
	private SerializableEmployee _employee;
	
	/**
	 * 
	 */
	private String _dateTime;

	/**
	 * 
	 */
	public EmployeeDischargedRequest(){}
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public EmployeeDischargedRequest( SerializableMarketPlace marketPlace, SerializableCompany company, SerializableEmployee employee, String dateTime){
		_company=company;
		_marketPlace=marketPlace;
		_dateTime=dateTime;
		_employee=employee;
	}
	
	/**
	 * @param company the company to set
	 */
	public void setCompany(SerializableCompany company) {
		_company = company;
	}

	/**
	 * @return the company
	 */
	public SerializableCompany getCompany() {
		return _company;
	}

	/**
	 * @param marketPlace the marketPlace to set
	 */
	public void setMarketPlace(SerializableMarketPlace marketPlace) {
		_marketPlace = marketPlace;
	}

	/**
	 * @return the marketPlace
	 */
	public SerializableMarketPlace getMarketPlace() {
		return _marketPlace;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(SerializableEmployee employee) {
		_employee = employee;
	}

	/**
	 * @return the employee
	 */
	public SerializableEmployee getEmployee() {
		return _employee;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		_dateTime = dateTime;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return _dateTime;
	}
}
