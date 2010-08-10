/**
 *
 */
package org.promasi.server.core.game;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.core.SdSystem;
import org.promasi.model.Company;

/**
 * @author m1cRo
 *
 */
public class GameModel implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The current {@link Company} of this game model.
     */
	private Company _company;

    /**
     * The running sd system.
     */
    private SdSystem _sdSystem;
	
    /**
     * 
     * @param xmlModel
     * @throws NullArgumentException
     * @throws IllegalArgumentException
     */
	public GameModel(String xmlModel)throws NullArgumentException,IllegalArgumentException
	{
		if(xmlModel==null)
		{
			throw new NullArgumentException("Wrong argument xmlModel==null");
		}

		if(xmlModel.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument xmlModel.isEmpty()");
		}
	}

	
	public String toXML()
	{
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		XMLEncoder xmlEncoder=new XMLEncoder(outputStream);
		xmlEncoder.writeObject(this);
		xmlEncoder.close();
		String temp=outputStream.toString();
		String result=new String("");
		for(int i=0;i<temp.length();i++)
		{
			char ch=temp.charAt(i);
			if(ch!='\n' && ch!='\r')
			{
				result=result+ch;
			}
		}
		
		return result;
	}
	
	
	public void executeStep()
	{
		_sdSystem.executeStep();
	}
	
	/**
	 * 
	 * @return
	 */
	public Company getCompany()
	{
		return _company;
	}
	
	/**
	 * 
	 * @param company
	 * @throws NullArgumentException
	 */
	public void setCompany(Company company)throws NullArgumentException
	{
		if(company==null){
			throw new NullArgumentException("");
		}
		
		_company=company;
	}
	
	/**
	 * 
	 * @return
	 */
	public SdSystem getSdSystem()
	{
		return _sdSystem;
	}
	
	/**
	 * 
	 * @param sdSystem
	 * @throws NullArgumentException
	 */
	public void setSdSystem(SdSystem sdSystem)throws NullArgumentException
	{
		if(sdSystem==null){
			throw new NullArgumentException("");
		}
		
		_sdSystem=sdSystem;
	}
}
