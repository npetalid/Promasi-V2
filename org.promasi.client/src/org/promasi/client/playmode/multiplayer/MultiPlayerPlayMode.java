/**
 * 
 */
package org.promasi.client.playmode.multiplayer;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Shell;
import org.promasi.client.playmode.IPlayMode;
import org.promasi.client.playmode.multiplayer.client.clientstate.LoginClientState;
import org.promasi.client.playmode.singleplayer.userstate.IUserState;
import org.promasi.network.tcp.TcpClient;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author m1cRo
 *
 */
public class MultiPlayerPlayMode implements IPlayMode
{
	/**
	 * 
	 */
	public static final String CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME="MultiPlayer";
	
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
	@SuppressWarnings("unused")
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private LoginClientState _loginClientState;
	
	/**
	 * 
	 * @param shell
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws IllegalArgumentException 
	 */
	public MultiPlayerPlayMode(final Shell shell)throws NullArgumentException, IllegalArgumentException, UnknownHostException, IOException{
		if(shell==null){
			throw new NullArgumentException("Wrong argument parentShell==null");
		}
		
		try{
			MultiPlayerClientSettings settings=readClientSettings(RootDirectory.getInstance().getRootDirectory()+CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME+RootDirectory.getInstance().getSeparator()+CONST_MULTIPLAYER_CLIENT_SETTINGS_FILE_NAME);
			TcpClient client=new TcpClient(settings.getHostName(),settings.getPortNumber());
			_loginClientState=new LoginClientState(shell);
			_client=new ProMaSiClient(client, _loginClientState);
		}catch(FileNotFoundException e){
			MultiPlayerClientSettings settings=new MultiPlayerClientSettings();
			settings.setHostName("localhost");
			settings.setPortNumber(55555);
            PrintWriter out = new PrintWriter(new FileWriter(RootDirectory.getInstance().getRootDirectory()+CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME+RootDirectory.getInstance().getSeparator()+CONST_MULTIPLAYER_CLIENT_SETTINGS_FILE_NAME));
            out.print(settings.serialize());
            out.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.playmode.IPlayMode#getName()
	 */
	@Override
	public String getName() {
		return "MultiPlayer";
	}

	/* (non-Javadoc)
	 * @see org.promasi.playmode.IPlayMode#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Online multiplayer game";
	}

	/* (non-Javadoc)
	 * @see org.promasi.playmode.IPlayMode#getUri()
	 */
	@Override
	public String getUri() throws IOException {
		return RootDirectory.getInstance().getRootDirectory()+RootDirectory.getInstance().getSeparator()+CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME;
	}

	/* (non-Javadoc)
	 * @see org.promasi.playmode.IPlayMode#play()
	 */
	@Override
	public void play() {
	}

	/* (non-Javadoc)
	 * @see org.promasi.playmode.IPlayMode#changeState(org.promasi.playmode.singleplayer.userstate.IUserState)
	 */
	@Override
	public boolean changeState(IUserState newState) {
		// TODO Auto-generated method stub
		return false;
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
}
