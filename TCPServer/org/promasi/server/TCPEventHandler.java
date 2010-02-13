package org.promasi.server;

import org.promasi.server.ITCPEventHandler;
import org.promasi.server.TCPClient;

public class TCPEventHandler implements ITCPEventHandler {

	@Override
	public boolean OnConnect(TCPClient tcpClient) {
		System.out.println("Connected");
		return false;
	}


	@Override
	public void OnConnectionError(TCPClient tcpClient) {
		System.out.println("Connection error");
	}


	@Override
	public void OnDisconnect(TCPClient tcpClient) {
		// TODO Auto-generated method stub
		System.out.println("Disconnected");

	}

	@Override
	public boolean OnReceive(TCPClient tcpClient, String line) {
		System.out.println(line);
		return true;
	}

}
