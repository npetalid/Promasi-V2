/**
 * 
 */
package org.promasi.game;


import org.promasi.game.company.CompanyMemento;


/**
 * @author m1cRo
 *
 */
public interface IGameModelListener 
{
	/**
	 * 
	 */
	public void onExecuteStep( GameModel game, CompanyMemento company);
	
	/**
	 * 
	 * @param game
	 * @param company
	 */
	public void gameFinished( GameModel game, CompanyMemento company);
}
