/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.project.ProjectTaskMemento;

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
