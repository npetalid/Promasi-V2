package org.promasi.protocol;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.tcp.TcpClient;

public class TcpResponseTimer extends TimerTask{
	/**
	 * 
	 */
	private static final int _responseTimeout=20000;
	
	/**
	 * 
	 */
	private Timer _timer;
	
	/**
	 * 
	 */
	private TcpClient _client;
	
	/**
	 * 
	 * @param client
	 * @throws NullArgumentException
	 */
	public TcpResponseTimer(TcpClient client)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument tcpClient==null");
		}
		
		_client=client;
		_timer=new Timer(true);
		_timer.schedule(this,_responseTimeout);
	}
	
	/**
	 * 
	 */
	public void run()
	{
		_client.disconnect();
	}
	
	/**
	 * 
	 */
	public synchronized void stopTimer()
	{
		_timer.cancel();
	}
}
