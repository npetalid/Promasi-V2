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
import javax.naming.ConfigurationException;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author m1cRo
 *
 */
public class Application {

	/**
	 * 
	 */
	public static final String CONST_SERVER_SETTINGS_FILE_NAME="Settings.xml";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			try{
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
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
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
