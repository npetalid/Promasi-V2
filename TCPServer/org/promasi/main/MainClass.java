/**
 *
 */
package org.promasi.main;
import org.promasi.server.TCPServer;

/**
 * @author m1cRo
 *
 */
public class MainClass {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TCPServer server=new TCPServer();
		TCPEventHandler eventHandler=new TCPEventHandler();
		server.RegisterTcpEventHandler(eventHandler);
		if(server.Start(2222)){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			server.Stop();
		}
	}
}
