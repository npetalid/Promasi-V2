/**
 *
 */
package org.promasi.multiplayer.game;

import java.util.Map;

/**
 * @author m1cRo
 *
 */
public interface IGameState
{
	public void setGameInputs(String userId,Map<String,Double> gameInputs);
}
