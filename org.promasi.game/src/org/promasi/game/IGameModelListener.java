/**
 * 
 */
package org.promasi.game;


import org.promasi.game.company.CompanyMemento;

import org.promasi.game.project.ProjectMemento;

/**
 * @author m1cRo
 *
 */
public interface IGameModelListener 
{
	/**
	 * 
	 */
	public void onExecuteStep(final GameModel game, final CompanyMemento company,final ProjectMemento assignedProject);
	
	/**
	 * 
	 * @param game
	 * @param company
	 */
	public void gameFinished(final GameModel game, final CompanyMemento company);
}
