/**
 * 
 */
package org.promasi.utilities.design;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author alekstheod
 * Generic implementation of Observer
 * design pattern.
 */
public class Observer<Listener> {
	/**
	 * A list of listeners.
	 */
	private List< Listener > _listeners;
	
	/**
	 * Lock object needed to lock the listeners list.
	 */
	private Lock _lockObject;
	
	/**
	 * Constructor will initialize the object.
	 */
	public Observer(){
		_listeners = new LinkedList<>();
		_lockObject = new ReentrantLock();
	}
	
	/**
	 * Will return the copy of listeners list.
	 * @return A copy of the listeners list.
	 */
	protected List< Listener > getListeners(){
		try{
			_lockObject.lock();
			return new LinkedList<>(_listeners);
		}finally{
			_lockObject.unlock();
		}
	}
	
	/**
	 * Will add a given listener to the listeners list.
	 * @param listener instance of class which represent the listener.
	 * @return true if succeed, false otherwise
	 */
	public boolean addListener( Listener listener ){
		boolean result = false;
		try{
			_lockObject.lock();
			if( listener != null && !_listeners.contains(listener)){
				result = _listeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * Will remove a given listener from the listeners list.
	 * @param listener instance of class which represent the listener.
	 * @return true if succeed, false otherwise
	 */
	public boolean removeListener ( Listener listener ){
		boolean result = false;
		try{
			_lockObject.lock();
			if( listener != null && _listeners.contains(listener)){
				result = _listeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;	
	}
	
	/**
	 * Will remove all listeners from the
	 * listeners list.
	 */
	public void clearListeners(){
		try{
			_lockObject.lock();
			_listeners.clear();
		}finally{
			_lockObject.unlock();
		}		
	}
}
