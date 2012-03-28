/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import org.promasi.game.project.ProjectTaskMemento;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class ProjectTask {

	/**
	 * 
	 */
	private ProjectTaskMemento _memento;
	
	/**
	 * 
	 * @param memento
	 * @throws GuiException 
	 */
	public ProjectTask( ProjectTaskMemento memento ) throws GuiException{
		if( memento == null ){
			throw new GuiException("Wrong argument memento == null");
		}
		
		_memento = memento;
	}
	
	@Override
	public String toString(){
		return _memento.getName();
	}
}
