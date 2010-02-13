package org.promasi.model;


/**
 * 
 * Represents a property of an employee. The designer can create any properties
 * he thinks an employee should have and he can use them on his sdmodel.
 * 
 * @author eddiefullmetal
 * 
 */
public class EmployeeProperty
{
    /**
     * The name of the property
     */
    private String _name;

    /**
     * The value of the property.
     */
    private Double _value;

    /**
     * Initializes the object.
     * 
     */
    public EmployeeProperty( )
    {

    }

    /**
     * Initializes the object.
     * 
     * @param name
     * @param value
     */
    public EmployeeProperty( String name, Double value )
    {
        super( );
        _name = name;
        _value = value;
    }

    /**
     * @return the {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }

    /**
     * @param name
     *            the {@link #_name} to set.
     */
    public void setName ( String name )
    {
        _name = name;
    }

    /**
     * @return the {@link #_value}.
     */
    public Double getValue ( )
    {
        return _value;
    }

    /**
     * @param value
     *            the {@link #_value} to set.
     */
    public void setValue ( Double value )
    {
        _value = value;
    }

}
