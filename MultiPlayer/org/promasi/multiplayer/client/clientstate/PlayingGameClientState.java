/**
 * 
 */
package org.promasi.multiplayer.client.clientstate;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.model.Company;
import org.promasi.model.MarketPlace;
import org.promasi.model.ProjectManager;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.shell.Shell;
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
	public PlayingGameClientState(Shell shell, Company company, MarketPlace marketPlace, ProjectManager projectManager)throws NullArgumentException, ConfigurationException
	{
		if(shell==null)
		{
			throw new NullArgumentException("Wrong argument shell==null");
		}

    	ICommunicator communicator=new Communicator();
    	communicator.setMainReceiver(  shell.getModelMessageReceiver() );
    	
        shell.setCompany( company );
        company.setProjectManager( projectManager);
    	
		_mainFrame=new DesktopMainFrame(shell);
		_mainFrame.showMainFrame( );
		_mainFrame.registerCommunicator(communicator);
    	shell.start();
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
