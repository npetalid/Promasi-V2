/**
 *
 */
package org.promasi.server;
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

	public boolean RegisterTcpEventHandler(ITCPEventHandler tcpEventHandler){
		if(_lockObject.tryLock()){
			try{
				_tcpEventHandler=tcpEventHandler;
			}
			finally	{
				_lockObject.unlock();
			}
			return true;
		}else{
			return false;
		}
	}


	public boolean OnReceive(TCPClient tcpClient,String line){
		if(_lockObject.tryLock()){
			boolean result=true;
			try{
				if(_tcpEventHandler!=null){
					result=_tcpEventHandler.OnReceive(tcpClient,line);
				}
			}
			finally{
				_lockObject.unlock();
			}
			return result;
		}else{
			return true;
		}
	}


	public boolean OnDisconnect(TCPClient tcpClient){
		if(_lockObject.tryLock()){
			try{
				if(_tcpEventHandler!=null){
					_tcpEventHandler.OnDisconnect(tcpClient);
				}
			}finally{
				_lockObject.unlock();
			}
			return true;
		}else{
			return false;
		}
	}

	public boolean OnConnect(TCPClient tcpClient){
		if(_lockObject.tryLock()){
			boolean result=true;
			try{
				if(_tcpEventHandler!=null){
					result=_tcpEventHandler.OnConnect(tcpClient);
				}
			}finally{
				_lockObject.unlock();
			}
			return result;
		}
		return true;
	}


	public void OnConnectionError(TCPClient tcpClient){
		if(_lockObject.tryLock()){
			try{
				if(_tcpEventHandler!=null){
					_tcpEventHandler.OnConnectionError(tcpClient);
				}
			}finally{
				_lockObject.unlock();
			}
		}
	}


	public ITCPEventHandler GetTcpEventHandler(){
		if(_lockObject.tryLock()){
			try{
				return _tcpEventHandler;
			}finally{
				_lockObject.unlock();
			}
		}
		return null;
	}
}
