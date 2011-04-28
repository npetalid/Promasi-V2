/**
 *
 */
package org.promasi.network.tcp;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

import org.promasi.utilities.exceptions.NullArgumentException;


/**
 * @author m1cRo
 *
 */
public class TcpServer
{
	/**
	 *
	 */
	private ServerSocket _serverSocket;

	/**
	 *
	 */
	private Thread _acceptThread;

	/**
	 * 
	 */
	private Thread _clientCheckThread;
	
	/**
	 * 
	 */
	private List<TcpClient> _clients;
	
	/**
	 *
	 */
	private boolean _listening;

	/**
	 * 
	 */
	private List<ITcpServerListener> _listeners;
	
	/**
	 *
	 */
	public TcpServer()
	{
		_acceptThread=null;
		_clientCheckThread=null;
		_listening=false;
		_clients=new LinkedList<TcpClient>();
		_listeners=new LinkedList<ITcpServerListener>();
	}

	/**
	 *
	 * @param tcpEventHandler
	 */
	public synchronized boolean addListener(ITcpServerListener listener)throws NullArgumentException
	{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		_listeners.add(listener);
		return true;
	}


	/**
	 * 
	 * @param portNumber
	 * @return
	 */
	public synchronized boolean start(int portNumber)
	{
		if(_listening){
			return false;
		}
		
		try{
			_serverSocket=new ServerSocket(portNumber);
			_listening=true;
			_acceptThread=new Thread(new Runnable() {
					
				@Override
				public void run() {
					while(_listening){
						try{
							Socket clientSocket=_serverSocket.accept();
							synchronized(TcpServer.this){
								TcpClient client=new TcpClient(clientSocket);
								_clients.add(client);
								for(ITcpServerListener listener : _listeners){
									listener.clientConnected(client);
								}	
							}
						}catch(IOException e){
								//Logger
						}	
					}
				}
			});
				
			_acceptThread.start();
			_clientCheckThread=new Thread(new Runnable() {
					
				@Override
				public void run() {
					while(_listening){
						synchronized(TcpServer.this){
							List<TcpClient> clients=new LinkedList<TcpClient>();
							for(TcpClient client : _clients){
								if(client.isConnected()){
									clients.add(client);
								}
							}
								
							for(TcpClient client : clients){
								if(!_clients.contains(client)){
									for(ITcpServerListener listener : _listeners){
										listener.clientDisconnected(client);
									}
								}
							}
							
							_clients=clients;
						}
					}
				}
			});
				
			for(ITcpServerListener listener : _listeners){
				listener.serverStarted(portNumber);
			}
		}catch(IOException e){
			return false;
		}

		return true;	
	}


	/**
	 * 
	 * @return
	 */
	public synchronized boolean stop(){
		try{
			_serverSocket.close();
			_listening=false;
			while(_acceptThread.isAlive() || _clientCheckThread.isAlive()){
				try{
					Thread.sleep(100);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
				
			for(ITcpServerListener listener : _listeners){
				listener.serverStopped();
			}
				
			_clients.clear();
			_acceptThread=null;
		}catch(IOException e){
			return false;
		}
			
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized boolean isRunning(){
		return _listening;
	}
	
}
