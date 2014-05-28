/**
 *
 */
package org.promasi.protocol.messages;

import java.util.List;
import java.util.Map;

import org.promasi.game.model.generated.GameModelModel;
import org.promasi.game.model.generated.ProjectModel;
import org.promasi.game.model.generated.ProjectTaskModel;

/**
 * @author m1cRo
 *
 */
public class GameFinishedRequest extends Message {

    /**
     *
     */
    private GameModelModel _gameModel;

    /**
     *
     */
    private String _clientId;

    /**
     *
     */
    private Map<String, GameModelModel> _otherPlayersModels;

    /**
     *
     */
    public GameFinishedRequest() {
    }

    /**
     *
     * @param clientId
     * @param gameModel
     * @param otherPlayersModels
     */
    public GameFinishedRequest(String clientId, GameModelModel gameModel, Map<String, GameModelModel> otherPlayersModels) {
        _clientId = clientId;
        if (otherPlayersModels != null) {
            for (Map.Entry<String, GameModelModel> entry : otherPlayersModels.entrySet()) {
                if (entry.getValue() != null) {
                    normilizeGameModel(entry.getValue());
                }
            }
        }

        normilizeGameModel(gameModel);
        _gameModel = gameModel;
        _otherPlayersModels = otherPlayersModels;
    }

    /**
     * @param gameModel
     */
    public void setGameModel(GameModelModel gameModel) {
        normilizeGameModel(gameModel);
        this._gameModel = gameModel;
    }

    /**
     * @return the _gameModel
     */
    public GameModelModel getGameModel() {
        return _gameModel;
    }

    /**
     * @param _clientId the _clientId to set
     */
    public void setClientId(String _clientId) {
        this._clientId = _clientId;
    }

    /**
     * @return the _clientId
     */
    public String getClientId() {
        return _clientId;
    }

    /**
     *
     * @param gameModel
     */
    private void normilizeGameModel(GameModelModel gameModel) {
        if (gameModel != null) {
            List<ProjectModel> projects = gameModel.getProjectModel();
            if (projects != null) {
                for (ProjectModel project : projects) {
                    if (project != null) {
                        ProjectModel.ProjectTasks tasks = project.getProjectTasks();
                        if (tasks != null) {
                            for (ProjectModel.ProjectTasks.Entry entry : tasks.getEntry() ) {
                                if (entry.getValue() != null) {
                                    ProjectTaskModel task = entry.getValue();
                                    task.setSimulationModel(null);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param otherPlayersModels
     */
    public void setOtherPlayersModels(Map<String, GameModelModel> otherPlayersModels) {
        if (otherPlayersModels != null) {
            for (Map.Entry<String, GameModelModel> entry : otherPlayersModels.entrySet()) {
                if (entry.getValue() != null) {
                    normilizeGameModel(entry.getValue());
                }
            }
        }

        this._otherPlayersModels = otherPlayersModels;
    }

    /**
     *
     * @return
     */
    public Map<String, GameModelModel> getOtherPlayersModels() {
        return _otherPlayersModels;
    }
}
