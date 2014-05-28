package org.promasi.utilities.clock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.utilities.design.Observer;

/**
 * 
 * @author alekstheod
 * Represent the clock.
 */
public final class Clock extends Observer<IClockListener> implements IClock
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
    private Lock _lockObject;

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
        _clockThread = new Thread( new Runnable() {
			
			public void run() {
				boolean running = true;
				while ( running ) {
		            try{
			            try{
			            	 Thread.sleep( _speed );
			            }catch ( InterruptedException e ){
			                // Just warn no one is going to interrupt this thread.
			                //LOGGER.warn( "An InterruptedException occured while the clock was waiting.", e );
			            }
			            
		            	_lockObject.lock();
		            	running = _isRunning;
		                _currentDateTime=_currentDateTime.plusHours(1);
		            	for(IClockListener listener : getListeners()){
		            		listener.onTick(_currentDateTime);
		            	}
		            }finally{
		            	_lockObject.unlock();
		            }
		        }
				
			}
		}, THREAD_NAME );
        
        _isRunning = false;
        _speed = DEFAULT_SPEED;
        _lockObject = new ReentrantLock( );
        _currentDateTime = new DateTime();
    }

    /**
     * @return The {@link #_currentDateTime} in {@link DateTime} format so that
     *         it cannot be modified.
     */
    public DateTime getCurrentDateTime ( )
    {
    	DateTime result = new DateTime();
    	try{
    		_lockObject.lock();
			result = _currentDateTime.toDateTime( );
			result = result.minusMinutes(result.getMinuteOfHour());
			result = result.minusSeconds(result.getSecondOfMinute());
			result = result.minusMillis(result.getMillisOfSecond());			
    	}finally{
    		_lockObject.unlock();
    	}
    	return result;
    }

    /**
     * Starts the clock.
     */
    public void start ( ){
    	try{
    		_lockObject.lock();
            //LOGGER.info( "Starting clock..." );
            if ( !_isRunning ){
                _isRunning = true;
                _clockThread.start( );
            }
    	}finally{
    		_lockObject.unlock();
    	}
    }

    /**
     * @param speed
     *            the {@link #_speed} to set.
     */
    public void setDelay ( int speed )
    {
    	try{
    		_lockObject.lock();
    		_speed = speed;
    	}finally{
    		_lockObject.unlock();
    	}
    }

    /**
     * Stops the clock.
     */
    public void stop ( )
    {
        try{
        	_lockObject.lock();
        	_isRunning = false;
        }finally{
        	_lockObject.unlock();
        }
        
        try {
			_clockThread.join();
		} catch (InterruptedException e) {
			//TODO : log
		}
    }
    
    /**
     * 
     * @return
     */
    public boolean isActive(){
    	boolean result = false;
        try{
        	_lockObject.lock();
        	result = _clockThread.isAlive();
        }finally{
        	_lockObject.unlock();
        }
        
        return result;
    }
}
