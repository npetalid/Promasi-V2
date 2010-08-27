/**
 *
 */
package org.promasi.multiplayer.game;

/**
 * @author m1cRo
 *
 */
public interface IGameState
{
	public GameStats getGameStatistics();
	public void setGameInputs(String userId,GameInputs inputs);
}
