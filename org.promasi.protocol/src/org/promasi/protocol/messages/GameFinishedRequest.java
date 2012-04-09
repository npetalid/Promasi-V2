/**
 * 
 */
package org.promasi.protocol.messages;

import java.util.Map;
import java.util.Queue;

import org.promasi.game.GameModelMemento;
import org.promasi.game.project.ProjectMemento;
import org.promasi.game.project.ProjectTaskMemento;

/**
 * @author m1cRo
 *
 */
public class GameFinishedRequest extends Message {

	/**
	 * 
	 */
	private GameModelMemento _gameModel;
	
	/**
	 * 
	 */
	private String _clientId;
	
	/**
	 * 
	 */
	private Map<String, GameModelMemento> _otherPlayersModels;

	/**
	 * 
	 */
	public GameFinishedRequest(){
	}
	
	/**
	 * 
	 * @param clientId
	 * @param gameModel
	 */
	public GameFinishedRequest(String clientId, GameModelMemento gameModel,Map<String, GameModelMemento> otherPlayersModels){
		_clientId=clientId;
		if(otherPlayersModels!=null){
			for(Map.Entry<String, GameModelMemento> entry : otherPlayersModels.entrySet()){
				if(entry.getValue()!=null){
					normilizeGameModel(entry.getValue());
				}
			}
		}
		
		normilizeGameModel(gameModel);
		_gameModel=gameModel;
		_otherPlayersModels=otherPlayersModels;
	}
	
	/**
	 * @param _gameModel the _gameModel to set
	 */
	public void setGameModel(GameModelMemento gameModel) {
		normilizeGameModel(gameModel);
		this._gameModel = gameModel;
	}

	/**
	 * @return the _gameModel
	 */
	public GameModelMemento getGameModel() {
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
	private void normilizeGameModel(GameModelMemento gameModel){
		if(gameModel!=null){
			Queue<ProjectMemento> projects=gameModel.getProjects();
			if(projects!=null){
				for(ProjectMemento project : projects){
					if(project!=null){
						Map<String, ProjectTaskMemento> tasks=project.getProjectTasks();
						if(tasks!=null){
							for(Map.Entry<String, ProjectTaskMemento> entry : tasks.entrySet()){
								if(entry.getValue()!=null){
									ProjectTaskMemento task=entry.getValue();
									task.setSdSystem(null);
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
	public void setOtherPlayersModels(Map<String, GameModelMemento> otherPlayersModels) {
		if(otherPlayersModels!=null){
			for(Map.Entry<String, GameModelMemento> entry : otherPlayersModels.entrySet()){
				if(entry.getValue()!=null){
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
	public Map<String, GameModelMemento> getOtherPlayersModels() {
		return _otherPlayersModels;
	}
}
