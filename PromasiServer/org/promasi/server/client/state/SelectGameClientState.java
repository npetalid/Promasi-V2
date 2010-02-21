/**
 *
 */
package org.promasi.server.client.state;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.net.ProtocolException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.protocol.request.JoinGameRequest;
import org.promasi.protocol.request.RetreiveGamesRequest;
import org.promasi.protocol.response.LoginResponse;
import org.promasi.protocol.response.RetreiveGamesResponse;
import org.promasi.server.core.AbstractClientState;
import org.promasi.server.core.ProMaSi;
import org.promasi.server.core.ProMaSiClient;

/**
 * @author m1cRo
 *
 */
public class SelectGameClientState extends AbstractClientState {

	/**
	 *
	 */
	private ProMaSi _promasi;

	/**
	 *
	 * @param promasi
	 */
	public SelectGameClientState(ProMaSi promasi)
	{
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi");
		}
		_promasi=promasi;
	}

	/* (non-Javadoc)
	 * @see org.promasi.protocol.state.IProtocolState#OnReceive(org.promasi.server.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws ProtocolException,NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}

		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		XMLDecoder decoder=new XMLDecoder(new ByteArrayInputStream(recData.getBytes()));
		try
		{
			Object object=decoder.readObject();
			decoder.close();
			if(object instanceof RetreiveGamesRequest)
			{
				LinkedList<String> gameList=_promasi.retreiveGames();
				RetreiveGamesResponse response=new RetreiveGamesResponse(gameList);
				client.sendMessage(response.toXML());
			}
			else if( object instanceof JoinGameRequest)
			{
				JoinGameRequest joinRequest=(JoinGameRequest)object;
				//ToDo
			}
			else
			{
				throw new ProtocolException("Wrong protocol");
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			client.sendMessage(new LoginResponse(false,e.getMessage()).toXML());
			throw new ProtocolException("Wrong protocol");
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new LoginResponse(false,e.getMessage()).toXML());
			throw new ProtocolException("Wrong protocol");
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new LoginResponse().toXML());
			throw new ProtocolException("Wrong protocol");
		}
		catch(NoSuchElementException e)
		{
			throw new ProtocolException("Wrong protocol");
		}
	}
}
