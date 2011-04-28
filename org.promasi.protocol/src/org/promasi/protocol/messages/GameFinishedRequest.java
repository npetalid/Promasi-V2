/**
 * 
 */
package org.promasi.protocol.messages;

import java.util.Map;
import java.util.Queue;

import org.promasi.game.SerializableGameModel;
import org.promasi.game.project.SerializableProject;
import org.promasi.game.project.SerializableProjectTask;
import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class GameFinishedRequest extends SerializableObject {

	/**
	 * 
	 */
	private SerializableGameModel _gameModel;
	
	/**
	 * 
	 */
	private String _clientId;
	
	/**
	 * 
	 */
	private Map<String, SerializableGameModel> _otherPlayersModels;

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
	public GameFinishedRequest(String clientId, SerializableGameModel gameModel,Map<String, SerializableGameModel> otherPlayersModels){
		_clientId=clientId;
		if(otherPlayersModels!=null){
			for(Map.Entry<String, SerializableGameModel> entry : otherPlayersModels.entrySet()){
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
	public void setGameModel(SerializableGameModel gameModel) {
		normilizeGameModel(gameModel);
		this._gameModel = gameModel;
	}

	/**
	 * @return the _gameModel
	 */
	public SerializableGameModel getGameModel() {
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
	private void normilizeGameModel(SerializableGameModel gameModel){
		if(gameModel!=null){
			Queue<SerializableProject> projects=gameModel.getProjects();
			if(projects!=null){
				for(SerializableProject project : projects){
					if(project!=null){
						Map<String, SerializableProjectTask> tasks=project.getProjectTasks();
						if(tasks!=null){
							for(Map.Entry<String, SerializableProjectTask> entry : tasks.entrySet()){
								if(entry.getValue()!=null){
									SerializableProjectTask task=entry.getValue();
									task.setSdSystem(null);
								}
							}
						}
					}
				}
			}
			
			Queue<SerializableProject> oldProjects=gameModel.getRunnedProjects();
			if(projects!=null){
				for(SerializableProject project : oldProjects){
					if(project!=null){
						Map<String, SerializableProjectTask> tasks=project.getProjectTasks();
						if(tasks!=null){
							for(Map.Entry<String, SerializableProjectTask> entry : tasks.entrySet()){
								if(entry.getValue()!=null){
									SerializableProjectTask task=entry.getValue();
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
	public void setOtherPlayersModels(Map<String, SerializableGameModel> otherPlayersModels) {
		if(otherPlayersModels!=null){
			for(Map.Entry<String, SerializableGameModel> entry : otherPlayersModels.entrySet()){
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
	public Map<String, SerializableGameModel> getOtherPlayersModels() {
		return _otherPlayersModels;
	}
}
