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
	public void onExecuteStep( GameModel game, CompanyMemento company, ProjectMemento assignedProject);
	
	/**
	 * 
	 * @param game
	 * @param company
	 */
	public void gameFinished( GameModel game, CompanyMemento company);
}
