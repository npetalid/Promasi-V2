/**
 * 
 */
package org.promasi.game.singleplayer;

import java.util.Map;

import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.GameModelModel;

/**
 * @author alekstheod
 *
 */
public interface IClientGameListener 
{
	/**
	 * 
	 * @param game
	 * @param gameModel
	 */
	public void gameStarted(final IGame game, final GameModelModel gameModel, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param dateTime
	 */
	public void onTick(final IGame game, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param players
	 */
	public void gameFinished(IGame game, Map<String, CompanyModel> players);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param assignedProject
	 * @param dateTime
	 */
	public void onExecuteStep(IGame game, CompanyModel company, DateTime dateTime);
}
