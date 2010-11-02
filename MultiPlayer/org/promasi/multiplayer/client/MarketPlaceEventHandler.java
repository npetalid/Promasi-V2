package org.promasi.multiplayer.client;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Employee;
import org.promasi.model.IMarketPlaceEventHandler;
import org.promasi.multiplayer.ProMaSiClient;


public class MarketPlaceEventHandler implements IMarketPlaceEventHandler {

	private ProMaSiClient _client;
	
	public MarketPlaceEventHandler(ProMaSiClient client)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		_client=client;
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
