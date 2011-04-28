/**
 * 
 */
package org.promasi.client.playmode.singleplayer;

import java.io.IOException;

import org.eclipse.swt.widgets.Shell;
import org.promasi.client.playmode.IPlayMode;
import org.promasi.client.playmode.singleplayer.userstate.ChooseGameUserState;
import org.promasi.client.playmode.singleplayer.userstate.IUserState;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author m1cRo
 *
 */
public class SinglePlayerPlayMode implements IPlayMode 
{
	/**
	 * 
	 */
	public static final String CONST_SINGLEPLAYER_PLAYMODE_FOLDER_NAME="SinglePlayer";
	
	/**
	 * 
	 */
	private IUserState _userState;
	
	/**
	 * 
	 */
	public static final String CONST_PLAYMODE_NAME="Single-Player";
	
	/**
	 * 
	 */
	public static final String CONST_PLAYMODE_DESCRIPTION =	"The purpose of this play mode is to gather the highest score.<br>"
                											+ "You will play through various levels. On each level you will have to complete a project.<br>";
	
	/**
	 * 
	 */
	private Shell _parentShell;
	
	/**
	 * 
	 * @throws NullArgumentException
	 * @throws IOException
	 */
	public SinglePlayerPlayMode(Shell shell)throws NullArgumentException{
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell==null");
		}
		
		_parentShell=shell;
	}
	
	@Override
	public synchronized boolean changeState(IUserState newState){
		if(newState==null){
			return false;
		}
		
		_userState=newState;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.ui.promasiui.IPlayMode#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return CONST_PLAYMODE_NAME;
	}

	/* (non-Javadoc)
	 * @see org.promasi.ui.promasiui.IPlayMode#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return CONST_PLAYMODE_DESCRIPTION;
	}

	@Override
	public void play() {
		try {
			_userState=new ChooseGameUserState(_parentShell, this);
			_userState.run();
		} catch (NullArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String getUri() throws IOException {
		return RootDirectory.getInstance().getRootDirectory()+RootDirectory.getInstance().getSeparator()+CONST_SINGLEPLAYER_PLAYMODE_FOLDER_NAME;
	}
}
