/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.util.Date;
import java.util.List;

import org.promasi.game.GameException;
import org.promasi.game.IGame;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.singleplayer.IClientGameListener;

/**
 * @author alekstheod
 *
 */
public class MultiPlayerGame implements IGame {

	@Override
	public String getGameDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hireEmployee(String employeeId) throws GameException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dischargeEmployee(String employeeId)throws GameException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean assignTasks(String employeeId, List<SerializableEmployeeTask> employeeTasks)throws GameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addListener(IClientGameListener listener)throws GameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeListener(IClientGameListener listener)throws GameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean executeGameStep(Date currentDateTime)throws GameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setGameSpeed(int newSpeed) {
		// TODO Auto-generated method stub
		return false;
	}


}
