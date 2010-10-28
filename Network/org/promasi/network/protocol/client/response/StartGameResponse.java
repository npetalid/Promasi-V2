/**
 *
 */
package org.promasi.network.protocol.client.response;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;
import org.promasi.model.MarketPlace;

/**
 * @author m1cRo
 *
 */
public class StartGameResponse extends AbstractResponse
{
	/**
	 * 
	 */
	private Company _company;
	
	/**
	 * 
	 */
	private MarketPlace _marketPlace;
	
	/**
	 * 
	 */
	public StartGameResponse()
	{
		_company=new Company();
		_marketPlace=new MarketPlace();
	}
	
	/**
	 * 
	 * @param gameId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public StartGameResponse(Company company, MarketPlace marketPlace)throws NullArgumentException
	{
		if(company==null)
		{
			throw new NullArgumentException("Wrong argument company==null");
		}
		
		if(marketPlace==null)
		{
			throw new NullArgumentException("Wrong argument marketPlace==null");
		}
		
		_marketPlace=marketPlace;
		_company=company;
	}
	
	/**
	 * 
	 * @param company
	 * @throws NullArgumentException
	 */
	public synchronized void setCompany(Company company)throws NullArgumentException
	{
		if(company==null)
		{
			throw new NullArgumentException("Wrong argument company==null");
		}
		
		_company=company;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Company getCompany()
	{
		return _company;
	}
	
	/**
	 * 
	 * @param marketPlace
	 * @throws NullArgumentException
	 */
	public synchronized void setMarketPlace(MarketPlace marketPlace)throws NullArgumentException
	{
		if(marketPlace==null)
		{
			throw new NullArgumentException("Wrong argument marketPlace==null");
		}
		
		_marketPlace=marketPlace;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized MarketPlace getMarketPlace()
	{
		return _marketPlace;
	}
}
