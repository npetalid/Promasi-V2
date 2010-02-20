/**
 *
 */
package org.promasi.main;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.promasi.tcpserver.TCPServer;
import org.promasi.protocol.request.LoginRequest;
import org.promasi.server.TcpEventHandler;

/**
 * @author m1cRo
 *
 */
public class MainClass {
	/**
	 * @param args
	 */
	public static void main(String[] args){
		try {
			XMLDecoder decoder=new XMLDecoder(new FileInputStream("LoginRequest.xml"));
			Object newRequest=decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LoginRequest request=new LoginRequest("new","pass");
		System.out.print(request.toXML());
		TCPServer server=new TCPServer();
		TcpEventHandler eventHandler=new TcpEventHandler();
		server.registerTcpEventHandler(eventHandler);
		if(server.start(2222)){
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			server.stop();
		}
	}
}
