/**
 * 
 */
package org.promasi.game;

import org.promasi.game.model.CompanyModel;


/**
 * @author m1cRo
 *
 */
public interface IGameModelListener 
{
	/**
	 * 
	 */
	public void onExecuteStep( GameModel game, CompanyModel company);
	
	/**
	 * 
	 * @param game
	 * @param company
	 */
	public void gameFinished( GameModel game, CompanyModel company);
}
