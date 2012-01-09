/**
 * 
 */
package org.promasi.client_swing.components.JList;

/**
 * 
 * @author alekstheod
 *
 */
public class CheckBoxListEntry{
	/**
	 * 
	 */
	private Object _object;
	
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
	public CheckBoxListEntry(Object object, String description){
		_object = object;
		_isSelected = false;
		_description = description;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getObject(){
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