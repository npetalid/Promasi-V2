/**
 *
 */
package org.promasi.protocol.request;

import org.promasi.server.core.GameModel;

/**
 * @author m1cRo
 *
 */
public class CreateNewGameRequest extends AbstractRequest
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private GameModel _gameModel;

	private String _gameId;
}
