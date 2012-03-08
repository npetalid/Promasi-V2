/**
 * 
 */
package org.promasi.server;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.promasi.network.tcp.NetworkException;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 * @author m1cRo
 * The main server application class,
 * Will initialize the java application.
 */
public class Application {

	/**
	 * 
	 */
	public static final String CONST_SERVER_SETTINGS_FILE_NAME="Settings.xml";
	
	/**
	 * Instance of {@link ILogger} interface implementation, needed
	 * for logging.
	 */
	private static final ILogger _logger = LoggerFactory.getInstance(Application.class);
	
	/**
	 * Will run the application.
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		try {
			try{
				_logger.info("Application started");
				ServerSettings settings=readServerSettings(RootDirectory.getInstance().getRootDirectory()+CONST_SERVER_SETTINGS_FILE_NAME);
				ProMaSiServer server=new ProMaSiServer(settings.getPortNumber());
				while(server.isRunning()){
					Thread.sleep(100);
				}
			}catch(FileNotFoundException e){
				ServerSettings settings=new ServerSettings();
				settings.setPortNumber(55555);
	            PrintWriter out = new PrintWriter(new FileWriter(RootDirectory.getInstance().getRootDirectory()+CONST_SERVER_SETTINGS_FILE_NAME));
	            out.print(settings.serialize());
	            out.close();
	            _logger.warn("Server configuration file was not found, server will generate a new configuration file");
			} catch (NetworkException e) {
				_logger.fatal("Server unabled to open the listening port (" + e.toString() +")");
			}
		}catch (InterruptedException e) {
			_logger.fatal("Internal exception (" + e.toString() +")");
		}catch(IOException e){
			_logger.fatal("Internal exception (" + e.toString() +")");
		}
	}

	/**
	 * Will read the server's setting from the given 
	 * file.
	 * @param filePath the path to the configurations file.
	 * @return Instance of {@link ServerSettings}.
	 * @throws FileNotFoundException In case if file was not found.
	 */
	private static ServerSettings readServerSettings(String filePath) throws FileNotFoundException{
		File companyFile=new File(filePath);
		FileInputStream fileInputStream=new FileInputStream(companyFile);
		XMLDecoder xmlDecoder=new XMLDecoder(fileInputStream);
		Object object=xmlDecoder.readObject();
		if(object instanceof ServerSettings){
			return (ServerSettings)object;
		}
		
		throw new FileNotFoundException("Company file not found");
	}
}
