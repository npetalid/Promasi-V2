/**
 *
 */
package org.promasi.server;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author m1cRo
 *
 */
public class TCPClientManager implements Runnable
{
	private LinkedList<TCPClient> _clients;

	private Thread _managementThread;

	private boolean _running;

	private Lock _lockObject;

	TCPClientManager(){
		_clients=new LinkedList<TCPClient>();
		_lockObject=new ReentrantLock();
		_running=true;
		_managementThread=new Thread(this);
		_managementThread.start();
	}

	@Override
	public void run() {
		while(_running){
			if(_lockObject.tryLock()){
				try{
					for(TCPClient client:_clients){
						if(!client.IsConnected()){
							_clients.remove(client);
							break;
						}
					}
				}finally{
					_lockObject.unlock();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void finalize(){
		_running=false;
	}


	public boolean AddClient(TCPClient client){
		if(_lockObject.tryLock()){
			try{
				_clients.add(client);
			}finally{
				_lockObject.unlock();
			}
		}else{
			return false;
		}
		return true;
	}


	public boolean RemoveClient(TCPClient client){
		if(_lockObject.tryLock()){
			try{
				_clients.remove(client);
			}finally{
				_lockObject.unlock();
			}
		}else{
			return false;
		}
		return true;
	}


	public boolean RegisterTcpEventHandler(ITCPEventHandler tcpEventHandler){
		if(_lockObject.tryLock()){
			try{
				for(TCPClient client:_clients){
					if(!client.RegisterTcpEventHandler(tcpEventHandler)){
						return false;
					}
				}
			}finally{
				_lockObject.unlock();
			}
		}else{
			return false;
		}
		return true;
	}


	public boolean Clear(){
		if(_lockObject.tryLock()){
			try{
				for(TCPClient client:_clients){
					client.Disconnect();
				}
				_clients.clear();
			}finally{
				_lockObject.unlock();
			}
		}else{
			return false;
		}
		return true;
	}

}
