/**
 * 
 */
package org.promasi.multiplayer.client;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.tcp.ITcpEventHandler;
import org.promasi.network.tcp.TcpClient;

/**
 * @author m1cRo
 *
 */
public class TcpEventHandler implements ITcpEventHandler {

	private ProMaSiClient _promasiClient;
	
	public TcpEventHandler(ProMaSiClient promasiClient)throws NullArgumentException
	{
		if( promasiClient==null)
		{
			throw new NullArgumentException("Wrong argument promasiClient==null");
		}
		
		_promasiClient=promasiClient;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.network.tcp.ITcpEventHandler#onReceive(org.promasi.network.tcp.TcpClient, java.lang.String)
	 */
	@Override
	public boolean onReceive(TcpClient tcpClient, String line)throws NullArgumentException {
		if(tcpClient==null)
		{
			throw new NullArgumentException("Wrong argument tcpClient==null");
		}
		
		if(line==null)
		{
			throw new NullArgumentException("Wrong argument line==null");
		}
		
		return _promasiClient.onReceiveData(line);
	}

	/* (non-Javadoc)
	 * @see org.promasi.network.tcp.ITcpEventHandler#onConnect(org.promasi.network.tcp.TcpClient)
	 */
	@Override
	public boolean onConnect(TcpClient tcpClient) throws NullArgumentException {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.promasi.network.tcp.ITcpEventHandler#onDisconnect(org.promasi.network.tcp.TcpClient)
	 */
	@Override
	public void onDisconnect(TcpClient tcpClient) throws NullArgumentException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.promasi.network.tcp.ITcpEventHandler#onConnectionError(org.promasi.network.tcp.TcpClient)
	 */
	@Override
	public void onConnectionError(TcpClient tcpClient) throws NullArgumentException {
		// TODO Auto-generated method stub

	}

}
