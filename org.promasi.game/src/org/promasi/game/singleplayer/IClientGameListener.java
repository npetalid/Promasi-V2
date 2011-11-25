/**
 * 
 */
package org.promasi.game.singleplayer;

import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.project.SerializableProject;

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
	public void gameStarted(final IGame game, final SerializableGameModel gameModel, final DateTime dateTime);
	
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
	public void gameFinished(IGame game,final SerializableGameModel gameModel, final SerializableCompany company);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param assignedProject
	 * @param dateTime
	 */
	public void onExecuteStep(IGame game, SerializableCompany company,SerializableProject assignedProject, DateTime dateTime);
}
