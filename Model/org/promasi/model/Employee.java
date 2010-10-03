package org.promasi.model;


import java.util.List;
import java.util.Vector;


/**
 * 
 * Represents an employee.(Developer,Tester,Designer etc). All the fields of the
 * employee(experienced etc) have a range of 0.0-10.0
 * 
 * @author eddiefullmetal
 * 
 */
public class Employee extends Person
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
     * The salary of the employee.
     */
    private double _salary;

    
    /**
     * The CV of the employee.
     */
    private String _curriculumVitae;
    

    /**
     * all the {@link EmployeeProperty} of the employee.
     */
    private List<EmployeeProperty> _properties;

    /**
     * 
     */
    boolean _isHired;
    
    /**
     * Initializes the object.
     */
    public Employee( )
    {
        super( );
        _properties = new Vector<EmployeeProperty>( );
    }

    
    /**
     * @return The {@link #_salary}.
     */
    public double getSalary ( )
    {
        return _salary;
    }

    
    /**
     * @param salary
     *            The {@link #_salary}.
     */
    public void setSalary ( double salary )
    {
        _salary = salary;
    }

    
    /**
     * @return The {@link #_curriculumVitae}.
     */
    public String getCurriculumVitae ( )
    {
        return _curriculumVitae;
    }

    /**
     * @param curriculumVitae
     *            The {@link #_curriculumVitae} to set.
     */
    public void setCurriculumVitae ( String curriculumVitae )
    {
        _curriculumVitae = curriculumVitae;
    }
    

    /**
     * @return If the employee is hired.
     */
    public synchronized boolean isHired ( )
    {
        return _isHired;
    }

    /**
     * 
     */
    public synchronized void discharge()
    {
    	_isHired=false;
    }
    
    /**
     * @return the {@link #_properties}.
     */
    public List<EmployeeProperty> getProperties ( )
    {
        return _properties;
    }

    /**
     * @param properties
     *            the {@link #_properties} to set.
     */
    public void setProperties ( List<EmployeeProperty> properties )
    {
        _properties = properties;
    }

    /**
     * @param property
     *            The {@link EmployeeProperty} to add to the
     *            {@link #_properties}.
     */
    public void addProperty ( EmployeeProperty property )
    {
        _properties.add( property );
    }
    
    
    public synchronized boolean hireEmployee()
    {
    	if(_isHired){
    		return false;
    	}else{
    		_isHired=true;
    		return true;
    	}
    }

}
