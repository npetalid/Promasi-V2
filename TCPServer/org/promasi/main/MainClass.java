/**
 *
 */
package org.promasi.main;
import org.promasi.tcpserver.TCPServer;
import org.promasi.server.TcpEventHandler;

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
		TcpEventHandler eventHandler=new TcpEventHandler();
		server.registerTcpEventHandler(eventHandler);
		if(server.start(2222)){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			server.stop();
		}
	}
}
