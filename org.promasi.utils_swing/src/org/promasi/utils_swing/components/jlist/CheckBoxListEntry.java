/**
 * 
 */
package org.promasi.utils_swing.components.jlist;

/**
 * 
 * @author alekstheod
 *
 */
public class CheckBoxListEntry<T>{
	/**
	 * 
	 */
	private T _object;
	
	/**
	 * 
	 */
	private boolean _isSelected;
	
	/**
	 * 
	 */
	private String _description;
	
	/**
	 * 
	 * @param object
	 */
	public CheckBoxListEntry(T object, String description){
		_object = object;
		_isSelected = false;
		_description = description;
	}
	
	/**
	 * 
	 * @return
	 */
	public T getObject(){
		return _object;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSelected(){
		return _isSelected;
	}
	
	/**
	 * 
	 */
	public void onClick(){
		_isSelected = !_isSelected;
	}
	
	@Override
	public String toString(){
		return _description;
	}
}