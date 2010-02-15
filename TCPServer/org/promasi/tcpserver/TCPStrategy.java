/**
 *
 */
package org.promasi.tcpserver;
import java.util.concurrent.locks.*;

/**
 * @author m1cRo
 *
 */
public class TCPStrategy
{
	private ITCPEventHandler _tcpEventHandler;

	private Lock _lockObject;

	public TCPStrategy(){
		_lockObject=new ReentrantLock();
	}

	public boolean registerTcpEventHandler(ITCPEventHandler tcpEventHandler)
	{
		if(_lockObject.tryLock())
		{
			try
			{
				_tcpEventHandler=tcpEventHandler;
			}
			finally
			{
				_lockObject.unlock();
			}
			return true;
		}
		else
		{
			return false;
		}
	}


	public boolean onReceive(TCPClient tcpClient,String line)
	{
		if(_lockObject.tryLock())
		{
			boolean result=true;
			try
			{
				if(_tcpEventHandler!=null)
				{
					result=_tcpEventHandler.onReceive(tcpClient,line);
				}
			}
			finally
			{
				_lockObject.unlock();
			}
			return result;
		}
		else
		{
			return true;
		}
	}


	public boolean onDisconnect(TCPClient tcpClient)
	{
		if(_lockObject.tryLock())
		{
			try
			{
				if(_tcpEventHandler!=null)
				{
					_tcpEventHandler.onDisconnect(tcpClient);
				}
			}
			finally
			{
				_lockObject.unlock();
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean onConnect(TCPClient tcpClient)
	{
		if(_lockObject.tryLock())
		{
			boolean result=true;
			try
			{
				if(_tcpEventHandler!=null)
				{
					result=_tcpEventHandler.onConnect(tcpClient);
				}
			}
			finally
			{
				_lockObject.unlock();
			}
			return result;
		}
		return true;
	}


	public void onConnectionError(TCPClient tcpClient)
	{
		if(_lockObject.tryLock())
		{
			try
			{
				if(_tcpEventHandler!=null)
				{
					_tcpEventHandler.onConnectionError(tcpClient);
				}
			}
			finally
			{
				_lockObject.unlock();
			}
		}
	}


	public ITCPEventHandler getTcpEventHandler()
	{
		if(_lockObject.tryLock())
		{
			try{
				return _tcpEventHandler;
			}
			finally
			{
				_lockObject.unlock();
			}
		}
		return null;
	}
}
