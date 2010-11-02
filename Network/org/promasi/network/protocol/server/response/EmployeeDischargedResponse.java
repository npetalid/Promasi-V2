package org.promasi.network.protocol.server.response;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Employee;
import org.promasi.network.protocol.client.response.AbstractResponse;

public class EmployeeDischargedResponse extends AbstractResponse 
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
	public EmployeeDischargedResponse()
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
	public EmployeeDischargedResponse(Integer employeeId, Employee employee)throws NullArgumentException
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
