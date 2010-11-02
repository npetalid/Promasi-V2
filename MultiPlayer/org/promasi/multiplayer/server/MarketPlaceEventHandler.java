package org.promasi.multiplayer.server;

import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Employee;
import org.promasi.model.IMarketPlaceEventHandler;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.game.GameModel;
import org.promasi.network.protocol.server.request.EmployeeDischargedRequest;
import org.promasi.network.protocol.server.request.EmployeeHiredRequest;

public class MarketPlaceEventHandler implements IMarketPlaceEventHandler {

	private Map<ProMaSiClient,GameModel> _gameModels;
	
	public MarketPlaceEventHandler(Map<ProMaSiClient,GameModel> gameModels)throws NullArgumentException
	{
		if(gameModels==null)
		{
			throw new NullArgumentException("Wrong argument gameModels==null");
		}
		
		_gameModels=gameModels;
	}

	@Override
	public void hireEmployee(Employee employee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dischargeEmployee(Employee employee) {
		// TODO Auto-generated method stub
		
	}
}
