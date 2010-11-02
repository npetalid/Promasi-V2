/**
 * 
 */
package org.promasi.network.protocol.server.request;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Employee;
import org.promasi.network.protocol.client.request.AbstractRequest;

/**
 * @author m1cRo
 *
 */
public class EmployeeHiredRequest extends AbstractRequest 
{
	/**
	 * 
	 */
	private Integer _employeeId;
	
	/**
	 * 
	 */
	private Employee _employee;
	
	/**
	 * 
	 */
	public EmployeeHiredRequest()
	{
		_employeeId=0;
		_employee=new Employee();
	}
	
	/**
	 * 
	 * @param employeeId
	 * @param employee
	 * @throws NullArgumentException
	 */
	public EmployeeHiredRequest(Integer employeeId, Employee employee)throws NullArgumentException
	{
		if(employeeId==null)
		{
			throw new NullArgumentException("Wrong argument employeeId==null");
		}
		
		if(employee==null)
		{
			throw new NullArgumentException("Wrong argument employee==null");
		}
		
		_employeeId=employeeId;
		_employee=employee;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Integer getEmployeeId()
	{
		return _employeeId;
	}
	
	/**
	 * 
	 * @param employeeId
	 * @throws NullArgumentException
	 */
	public synchronized void setEmployeeId(Integer employeeId)throws NullArgumentException
	{
		if(employeeId==null)
		{
			throw new NullArgumentException("Wrong argument employeeId==null");
		}
		
		_employeeId=employeeId;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Employee getEmployee()
	{
		return _employee;
	}
	
	/**
	 * 
	 * @param employee
	 * @throws NullArgumentException
	 */
	public synchronized void setEmployee(Employee employee)throws NullArgumentException
	{
		if(employee==null)
		{
			throw new NullArgumentException("Wrong argument employee==null");
		}
		
		_employee=employee;
	}
}
