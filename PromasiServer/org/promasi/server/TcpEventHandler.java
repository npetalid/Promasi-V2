package org.promasi.server;

import org.promasi.server.core.ProMaSi;
import org.promasi.tcpserver.ITCPEventHandler;
import org.promasi.tcpserver.TCPClient;

/**
 * @author m1cRo
 *
 */
public class TcpEventHandler implements ITCPEventHandler
{

	private ProMaSi _promasi;

	public TcpEventHandler()
	{
		_promasi=new ProMaSi();
	}

	/* (non-Javadoc)
	 * @see org.promasi.server.ITCPEventHandler#OnConnect(org.promasi.server.TCPClient)
	 */
	@Override
	public boolean onConnect(TCPClient tcpClient)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.server.ITCPEventHandler#OnConnectionError(org.promasi.server.TCPClient)
	 */
	@Override
	public void onConnectionError(TCPClient tcpClient)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.promasi.server.ITCPEventHandler#OnDisconnect(org.promasi.server.TCPClient)
	 */
	@Override
	public void onDisconnect(TCPClient tcpClient)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.promasi.server.ITCPEventHandler#OnReceive(org.promasi.server.TCPClient, java.lang.String)
	 */
	@Override
	public boolean onReceive(TCPClient tcpClient, String line)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
