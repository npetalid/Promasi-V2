package org.promasi.shell.model.actions;


import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.Message;
import org.promasi.shell.Shell;


/**
 *
 * An {@link IModelAction} that sends a mail from an employee with the specified
 * message and title. The employee is selected through XPath expressions. If the
 * employee is not found the message will not be sent.
 *
 * @author eddiefullmetal
 *
 */
public class EmployeeMailModelAction
        implements IModelAction
{

    /**
     * The XPath to choose the employee.
     */
    private String _employeeXPath;

    /**
     * The message to send.
     */
    private String _message;

    /**
     * The title of the message.
     */
    private String _title;

    /**
     * 
     */
    private Shell _shell;

    /**
     * Initializes the object.
     *
     */
    public EmployeeMailModelAction(Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
    	
    	_shell=shell;
    }

    @Override
    public void runAction ( )
    {
        Employee employee = (Employee) JXPathContext.newContext( _shell.getCompany( ) ).getValue( _employeeXPath );

        Company company = _shell.getCompany( );
        Message message = new Message( );
        message.setBody( _message );
        message.setTitle( _title );
        message.setRecipient(  company.getProjectManager( )  );
        message.setSender(  employee );

        _shell.sendMail( message );
    }

    @Override
    public boolean isValid ( )
    {
        return StringUtils.isBlank( _employeeXPath ) || StringUtils.isBlank( _message ) || StringUtils.isBlank( _title );
    }

    /**
     * @return the {@link #_employeeXPath}.
     */
    public String getEmployeeXPath ( )
    {
        return _employeeXPath;
    }

    /**
     * @param employeeXPath
     *            the {@link #_employeeXPath} to set.
     */
    public void setEmployeeXPath ( String employeeXPath )
    {
        _employeeXPath = employeeXPath;
    }

    /**
     * @return the {@link #_message}.
     */
    public String getMessage ( )
    {
        return _message;
    }

    /**
     * @param message
     *            the {@link #_message} to set.
     */
    public void setMessage ( String message )
    {
        _message = message;
    }

    /**
     * @return the {@link #_title}.
     */
    public String getTitle ( )
    {
        return _title;
    }

    /**
     * @param title
     *            the {@link #_title} to set.
     */
    public void setTitle ( String title )
    {
        _title = title;
    }

}
