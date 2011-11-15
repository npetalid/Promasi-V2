/**
 * 
 */
package org.promasi.client.playmode.multiplayer;

import java.util.Date;
import java.util.List;

import org.promasi.game.GameException;
import org.promasi.game.IGame;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.singleplayer.IClientGameListener;
import org.promasi.protocol.messages.AssignEmployeeTasksRequest;
import org.promasi.protocol.messages.DischargeEmployeeRequest;
import org.promasi.protocol.messages.HireEmployeeRequest;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class MultiPlayerGame implements IGame 
{	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 * @param client
	 * @throws NullArgumentException
	 */
	public MultiPlayerGame(ProMaSiClient client)throws NullArgumentException{
		if(client==null){
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		_client=client;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#getGameDescription()
	 */
	@Override
	public String getGameDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#hireEmployee(java.lang.String)
	 */
	@Override
	public void hireEmployee(String employeeId) throws GameException {
		if(employeeId==null){
			throw new GameException("Wrong argument employeeId==null");
		}
		
		HireEmployeeRequest request=new HireEmployeeRequest(employeeId);
		_client.sendMessage(request.serialize());
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#dischargeEmployee(java.lang.String)
	 */
	@Override
	public void dischargeEmployee(String employeeId)
			throws GameException {
		if(employeeId==null){
			throw new GameException("Wrong argument employeeId==null");
		}
		
		DischargeEmployeeRequest request=new DischargeEmployeeRequest(employeeId);
		_client.sendMessage(request.serialize());
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#assignTasks(java.lang.String, java.util.List)
	 */
	@Override
	public boolean assignTasks(String employeeId, List<SerializableEmployeeTask> employeeTasks)throws GameException {
		if(employeeId==null){
			throw new GameException("Wrong argument employeeId==null");
		}
		
		if(employeeTasks==null){
			throw new GameException("Wrong argument employeeTasks==null");
		}
		
		_client.sendMessage(new AssignEmployeeTasksRequest(employeeId, employeeTasks).serialize());
		return true;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#addListener(org.promasi.game.singleplayer.ISinglePlayerGameListener)
	 */
	@Override
	public boolean addListener(IClientGameListener listener){
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#removeListener(org.promasi.game.singleplayer.ISinglePlayerGameListener)
	 */
	@Override
	public boolean removeListener(IClientGameListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#executeGameStep(org.joda.time.DateTime)
	 */
	@Override
	public boolean executeGameStep(Date currentDateTime) throws GameException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#startGame()
	 */
	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#stopGame()
	 */
	@Override
	public boolean stopGame() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#setGameSpeed(int)
	 */
	@Override
	public boolean setGameSpeed(int newSpeed) {
		// TODO Auto-generated method stub
		return false;
	}

}
