/**
 * 
 */
package org.promasi.game;


import org.promasi.game.company.SerializableCompany;

import org.promasi.game.project.SerializableProject;

/**
 * @author m1cRo
 *
 */
public interface IGameModelListener 
{
	/**
	 * 
	 */
	public void onExecuteStep(final GameModel game, final SerializableCompany company,final SerializableProject assignedProject);
	
	/**
	 * 
	 * @param game
	 * @param company
	 */
	public void gameFinished(final GameModel game, final SerializableCompany company);
}
