/**
 * 
 */
package org.promasi.server;
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
