/**
 *
 */
package org.promasi.protocol.messages;

import org.promasi.game.model.generated.GameModelModel;

/**
 * @author m1cRo
 *
 */
public class CreateGameRequest extends Message {

    /**
     *
     */
    private GameModelModel _gameModel;

    /**
     *
     */
    private String _gameId;

    /**
     *
     */
    public CreateGameRequest() {
    }

    /**
     *
     * @param gameId
     * @param gameModel
     */
    public CreateGameRequest(String gameId, GameModelModel gameModel) {
        _gameModel = gameModel;
        _gameId = gameId;
    }

    /**
     * @return the gameModel
     */
    public GameModelModel getGameModel() {
        return _gameModel;
    }

    /**
     *
     * @param gameModel
     */
    public void setGameModel(GameModelModel gameModel) {
        _gameModel = gameModel;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(String gameId) {
        _gameId = gameId;
    }

    /**
     * @return the gameId
     */
    public String getGameId() {
        return _gameId;
    }

}
