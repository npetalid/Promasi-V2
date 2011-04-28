package org.promasi.utilities.clock;

import java.util.List;
import java.util.Vector;

import org.joda.time.DateTime;

/**
 * 
 * @author m1cRo
 *
 */
public final class Clock implements Runnable
{
    /**
     * The Current date time of the clock.
     */
    private DateTime _currentDateTime;

    /**
     * Indicates if the clock must stop.
     */
    private volatile boolean _isRunning;

    /**
     * This thread advances the clock according to the {@link #_fieldType}.
     */
    private Thread _clockThread;

    /**
     * Object used for locking.
     */
    private Object _lockObject;

    /**
     * All the {@link IClockListener}s.
     */
    private List<IClockListener> _listeners;

    /**
     * The speed of the clock. Basically how milliseconds it will wait before it
     * ticks again. By default it is set to 1000.
     */
    private int _speed;

    /**
     * The name of the {@link #_clockThread}.
     */
    private static final String THREAD_NAME = "Clock-Main";

    /**
     * The default value of the {@link #_speed}.
     */
    private static final int DEFAULT_SPEED = 1000;

    /**
     * Initializes the object. Sets the {@link #_fieldType} to minutes.
     */
    public Clock( )
    {
        _listeners = new Vector<IClockListener>( );
        _clockThread = new Thread( this, THREAD_NAME );
        _isRunning = false;
        _speed = DEFAULT_SPEED;
        _lockObject = new Object( );
        _currentDateTime = new DateTime();
    }

    /**
     * @return The {@link #_currentDateTime} in {@link DateTime} format so that
     *         it cannot be modified.
     */
    public DateTime getCurrentDateTime ( )
    {
    	return _currentDateTime.toDateTime( );
    }

    /**
     * @param listener
     *            The {@link IClockListener} to add to the {@link #_listeners}.
     */
    public boolean addListener ( IClockListener listener )
    {
    	synchronized(_lockObject){
        	if(listener==null)
        	{
        		return false;
        	}

            if ( !_listeners.contains( listener ) )
            {
              _listeners.add( listener );
            }
            
    	}

        return true;
    }

    /**
     * @param listener
     *            The {@link IClockListener} to remove from the
     *            {@link #_listeners}.
     */
    public boolean removeListener ( IClockListener listener )
    {
    	synchronized(_lockObject){
        	if(listener==null)
        	{
        		return false;
        	}

            if ( _listeners.contains( listener ) ){
               _listeners.remove( listener );
            }else{
            	return false;
            }	
    	}

        return true;
    }

    @Override
    public void run ( )
    {
        while ( _isRunning )
        {
            synchronized ( _lockObject )
            {
                _currentDateTime=_currentDateTime.plusHours(1);
            	for(IClockListener listener : _listeners){
            		listener.onTick(_currentDateTime);
            	}
            }
            
            // Do not sleep inside the synchronized block.
            try
            {
                Thread.sleep( _speed );
            }
            catch ( InterruptedException e )
            {
                // Just warn no one is going to interrupt this thread.
                //LOGGER.warn( "An InterruptedException occured while the clock was waiting.", e );
            }
        }
    }

    /**
     * Starts the clock.
     */
    public void start ( )
    {
    	synchronized(_lockObject){
            //LOGGER.info( "Starting clock..." );
            if ( !_clockThread.isAlive( ) )
            {
                _isRunning = true;
                _clockThread.start( );
            }
    	}
    }

    /**
     * @param speed
     *            the {@link #_speed} to set.
     */
    public void setDelay ( int speed )
    {
    	synchronized(_lockObject){
    		_speed = speed;
    	}
    }

    /**
     * Stops the clock.
     */
    public void stop ( )
    {
        synchronized(_lockObject){
        	_isRunning = false;
        }
    }
}
