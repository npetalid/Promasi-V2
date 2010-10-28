/**
 * 
 */
package org.promasi.server;
import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;
import org.promasi.multiplayer.server.TcpEventHandler;
import org.promasi.network.tcp.TcpServer;

/**
 * @author m1cRo
 *
 */
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure( "Data" + File.separator + "log4j.xml" );
		TcpServer server=new TcpServer();
		server.registerTcpEventHandler(new TcpEventHandler());
		server.start(2222);
		do
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true);
	}

}
