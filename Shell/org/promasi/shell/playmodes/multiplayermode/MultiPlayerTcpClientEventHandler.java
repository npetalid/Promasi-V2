package org.promasi.shell.playmodes.multiplayermode;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.tcp.ITcpEventHandler;
import org.promasi.network.tcp.TcpClient;
import org.promasi.shell.IShellListener;

public class MultiPlayerTcpClientEventHandler implements ITcpEventHandler {
	
	/**
	 * 
	 */
	private IShellListener _shellListener;
	
	/**
	 * 
	 * @param shellListener
	 * @throws IllegalArgumentException
	 */
	MultiPlayerTcpClientEventHandler(IShellListener shellListener)throws IllegalArgumentException
	{
		if(shellListener==null){
			throw new IllegalArgumentException();
		}
		
		_shellListener=shellListener;
	}
	
	@Override
	public boolean onReceive(TcpClient tcpClient, String line) throws NullArgumentException {

		return false;
	}

	@Override
	public boolean onConnect(TcpClient tcpClient) throws NullArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDisconnect(TcpClient tcpClient) throws NullArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionError(TcpClient tcpClient)
			throws NullArgumentException {
		// TODO Auto-generated method stub

	}

}
