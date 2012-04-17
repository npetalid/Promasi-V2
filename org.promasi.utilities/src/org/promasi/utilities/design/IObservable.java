/**
 * 
 */
package org.promasi.utilities.design;

import java.util.List;

/**
 * @author alekstheod
 *
 */
public interface IObservable<Listener> {
	/**
	 * Will return the copy of listeners list.
	 * @return A copy of the listeners list.
	 */
	public List< Listener > getListeners();
	
	/**
	 * Will add a given listener to the listeners list.
	 * @param listener instance of class which represent the listener.
	 * @return true if succeed, false otherwise
	 */
	public boolean addListener( Listener listener );
	
	/**
	 * Will remove a given listener from the listeners list.
	 * @param listener instance of class which represent the listener.
	 * @return true if succeed, false otherwise
	 */
	public boolean removeListener ( Listener listener );
	
	/**
	 * Will remove all listeners from the
	 * listeners list.
	 */
	public void clearListeners();
}
