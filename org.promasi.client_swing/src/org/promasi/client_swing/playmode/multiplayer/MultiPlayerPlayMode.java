/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.gui.LoginJPanel;
import org.promasi.client_swing.playmode.IPlayMode;
import org.promasi.network.tcp.NetworkException;
import org.promasi.network.tcp.TcpClient;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.compression.ZipCompression;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.jlist.IMenuEntry;

/**
 * @author m1cRo
 * Represent the multiplayer play mode in promasi system.
 */
public class MultiPlayerPlayMode implements IPlayMode, IMenuEntry, IClientListener
{
	/**
	 * PlayMode name.
	 */
	public static final String CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME="MultiPlayer";
	
	/**
	 * PlayMode description.
	 */
	public static final String CONST_PLAYMODE_DESCRIPTION	=	"The purpose of this play mode is to gather the highest score in the competition with other online game players.\n"+
																"You will chose one game from the list of available games, and will play this with other online players.\n"+
																"On each game you will have to complete one or more projects.\n"+
																"You will share the market place with your competitors and improve your skills as project manager";
	
	/**
	 * MultiPlayer client settings file name.
	 */
	public static final String CONST_MULTIPLAYER_CLIENT_SETTINGS_FILE_NAME="Settings.xml";
	
	/**
	 * 
	 */
	public static final String CONST_MENUIMAGE = "user_group.png";
	
	/**
	 * 
	 */
	private Icon _menuIcon;	
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 * @throws GuiException
	 */
	public MultiPlayerPlayMode()throws GuiException{
		try{
			MultiPlayerClientSettings settings=readClientSettings(RootDirectory.getInstance().getRootDirectory()+CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME+RootDirectory.getInstance().getSeparator()+CONST_MULTIPLAYER_CLIENT_SETTINGS_FILE_NAME);
			TcpClient client=new TcpClient(settings.getHostName(),settings.getPortNumber());
			_client=new ProMaSiClient(client, new ZipCompression());
		} catch (NetworkException e) {
			throw new GuiException(e);
		} catch( IOException e){
			throw new GuiException(e);
		}catch(Exception e){
			MultiPlayerClientSettings settings=new MultiPlayerClientSettings();
			settings.setHostName("localhost");
			settings.setPortNumber(55555);
            PrintWriter out;
			try {
				out = new PrintWriter(new FileWriter(RootDirectory.getInstance().getRootDirectory()+CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME+RootDirectory.getInstance().getSeparator()+CONST_MULTIPLAYER_CLIENT_SETTINGS_FILE_NAME));
			} catch (IOException e1) {
				throw new GuiException(e);
			}
			
            out.print(settings.serialize());
            out.close();
		}
		
		try{
			_menuIcon = new ImageIcon(RootDirectory.getInstance().getImagesDirectory() + CONST_MENUIMAGE);
		}catch( IOException e){
			//TODO log
		}
	}

	/* (non-Javadoc)
	 * @see org.promasi.playmode.IPlayMode#getDescription()
	 */
	@Override
	public String getDescription() {
		return CONST_PLAYMODE_DESCRIPTION;
	}

	/* (non-Javadoc)
	 * @see org.promasi.playmode.IPlayMode#getUri()
	 */
	@Override
	public String getUri(){
		String result = "";
		try{
			result = RootDirectory.getInstance().getRootDirectory()+RootDirectory.getInstance().getSeparator()+CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME;
		}catch( Exception e){
			//TODO log.
		}
		
		return result;
	}
	
	/**
	 * Method that will read the MultiPlayer client settings.
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	private MultiPlayerClientSettings readClientSettings(String filePath)throws FileNotFoundException{
		File companyFile=new File(filePath);
		FileInputStream fileInputStream=new FileInputStream(companyFile);
		XMLDecoder xmlDecoder=new XMLDecoder(fileInputStream);
		Object object=xmlDecoder.readObject();
		if(object instanceof MultiPlayerClientSettings){
			return (MultiPlayerClientSettings)object;
		}
		
		throw new FileNotFoundException("Company file not found");
	}

	@Override
	public Icon getIcon() {
		return _menuIcon;
	}

	@Override
	public void gotoNextPanel(IMainFrame mainFrame) {
        try {
			LoginJPanel lognPanel = new LoginJPanel(mainFrame, _client);
			mainFrame.changePanel(lognPanel);
		} catch (GuiException e) {
			// TODO log
		}
	}

	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onDisconnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String toString(){
		return "Multiplayer";
	}
}
