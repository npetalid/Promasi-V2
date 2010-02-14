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
	public boolean OnConnect(TCPClient tcpClient)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.server.ITCPEventHandler#OnConnectionError(org.promasi.server.TCPClient)
	 */
	@Override
	public void OnConnectionError(TCPClient tcpClient)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.promasi.server.ITCPEventHandler#OnDisconnect(org.promasi.server.TCPClient)
	 */
	@Override
	public void OnDisconnect(TCPClient tcpClient)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.promasi.server.ITCPEventHandler#OnReceive(org.promasi.server.TCPClient, java.lang.String)
	 */
	@Override
	public boolean OnReceive(TCPClient tcpClient, String line)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
