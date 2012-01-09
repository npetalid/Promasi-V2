/**
 * 
 */
package org.promasi.game.singleplayer;

import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.GameModelMemento;
import org.promasi.game.company.CompanyMemento;

/**
 * @author m1cRo
 *
 */
public interface IClientGameListener 
{
	/**
	 * 
	 * @param game
	 * @param gameModel
	 */
	public void gameStarted(final IGame game, final GameModelMemento gameModel, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param dateTime
	 */
	public void onTick(final IGame game, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 */
	public void gameFinished(IGame game,final GameModelMemento gameModel, final CompanyMemento company);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param assignedProject
	 * @param dateTime
	 */
	public void onExecuteStep(IGame game, CompanyMemento company, DateTime dateTime);
}
