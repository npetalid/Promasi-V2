/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.MarketPlaceModel;

/**
 * @author m1cRo
 *
 */
public class EmployeeDischargedRequest extends Message
{
	/**
	 * 
	 */
	private CompanyModel _company;
	
	/**
	 * 
	 */
	private MarketPlaceModel _marketPlace;

	/**
	 * 
	 */
	private EmployeeModel _employee;
	
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
	public EmployeeDischargedRequest( MarketPlaceModel marketPlace, CompanyModel company, EmployeeModel employee, String dateTime){
		_company=company;
		_marketPlace=marketPlace;
		_dateTime=dateTime;
		_employee=employee;
	}
	
	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyModel company) {
		_company = company;
	}

	/**
	 * @return the company
	 */
	public CompanyModel getCompany() {
		return _company;
	}

	/**
	 * @param marketPlace the marketPlace to set
	 */
	public void setMarketPlace(MarketPlaceModel marketPlace) {
		_marketPlace = marketPlace;
	}

	/**
	 * @return the marketPlace
	 */
	public MarketPlaceModel getMarketPlace() {
		return _marketPlace;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(EmployeeModel employee) {
		_employee = employee;
	}

	/**
	 * @return the employee
	 */
	public EmployeeModel getEmployee() {
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
