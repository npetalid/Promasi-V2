/**
 *
 */
package org.promasi.server;

/**
 * @author m1cRo
 *
 */
public interface IGameState
{
	public GameStats getGameStatistics();
	public void setGameInputs(String userId,GameInputs inputs);
}
