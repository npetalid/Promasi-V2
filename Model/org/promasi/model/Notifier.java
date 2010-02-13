package org.promasi.model;


import java.util.List;
import java.util.Vector;


/**
 * Notifies all registered {@link INotifierListener}s about stuff that's going
 * on in the company.
 * 
 * @author eddiefullmetal
 * 
 */
public class Notifier
{

    /**
     * All the registered {@link INotifierListener}s.
     */
    private List<INotifierListener> _listeners;

    /**
     * Initializes the object.
     */
    public Notifier( )
    {
        _listeners = new Vector<INotifierListener>( );
    }

    /**
     * @return the {@link #_listeners}.
     */
    public List<INotifierListener> getListeners ( )
    {
        return _listeners;
    }

    /**
     * @param listener
     *            The {@link INotifierListener} to add to the
     *            {@link #_listeners}.
     */
    public void addListener ( INotifierListener listener )
    {
        if ( !_listeners.contains( listener ) )
        {
            _listeners.add( listener );
        }
    }

    /**
     * @param listeners
     *            the {@link #_listeners} to set
     */
    public void setListeners ( List<INotifierListener> listeners )
    {
        _listeners = listeners;
    }

    /**
     * @param listener
     *            The {@link INotifierListener} to remove from the
     *            {@link #_listeners}.
     */
    public void removeListener ( INotifierListener listener )
    {
        if ( _listeners.contains( listener ) )
        {
            _listeners.remove( listener );
        }
    }

    /**
     * Calls the {@link INotifierListener#projectAssigned(Project)} of all
     * {@link #_listeners}.
     * 
     * @param project
     *            The newly assigned project.
     */
    public void projectAssigned ( Project project )
    {
        for ( INotifierListener listener : _listeners )
        {
            listener.projectAssigned( project );
        }
    }

    /**
     * Calls the {@link INotifierListener#employeeHired(Employee)} of all
     * {@link #_listeners}.
     * 
     * @param employee
     *            The newly hired employee.
     */
    public void employeeHired ( Employee employee )
    {
        for ( INotifierListener listener : _listeners )
        {
            listener.employeeHired( employee );
        }
    }
}
