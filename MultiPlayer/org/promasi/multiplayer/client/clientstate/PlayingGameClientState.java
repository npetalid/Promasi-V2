/**
 * 
 */
package org.promasi.multiplayer.client.clientstate;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.shell.Shell;
import org.promasi.shell.model.communication.ModelMessageReceiver;
import org.promasi.shell.playmodes.multiplayerscoremode.MultiPlayerScorePlayMode;
import org.promasi.ui.promasiui.promasidesktop.DesktopMainFrame;

/**
 * @author m1cRo
 *
 */
public class PlayingGameClientState extends AbstractClientState {

	/**
	 * 
	 */
	private DesktopMainFrame _mainFrame;
	
	/**
	 * 
	 * @param shell
	 * @throws NullArgumentException
	 * @throws ConfigurationException
	 */
	public PlayingGameClientState(MultiPlayerScorePlayMode playMode)throws NullArgumentException, ConfigurationException
	{
		if(playMode==null)
		{
			throw new NullArgumentException("Wrong argument playMode==null");
		}

    	ICommunicator communicator=new Communicator();
    	communicator.setMainReceiver(  new ModelMessageReceiver() );
    	
    	Shell shell = new Shell(playMode);
		_mainFrame=new DesktopMainFrame(shell);
		_mainFrame.showMainFrame( );
		playMode.start();
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.multiplayer.server.clientstate.IClientState#onReceive(org.promasi.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)
			throws NullArgumentException {
		// TODO Auto-generated method stub

	}

}
