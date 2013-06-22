/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import org.promasi.game.model.generated.ProjectTaskModel;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class ProjectTask {

	/**
	 * 
	 */
	private ProjectTaskModel _model;
	
	/**
	 * 
	 * @param Model
	 * @throws GuiException 
	 */
	public ProjectTask( ProjectTaskModel Model ) throws GuiException{
		if( Model == null ){
			throw new GuiException("Wrong argument Model == null");
		}
		
		_model = Model;
	}
	
	@Override
	public String toString(){
		return _model.getName();
	}
}
