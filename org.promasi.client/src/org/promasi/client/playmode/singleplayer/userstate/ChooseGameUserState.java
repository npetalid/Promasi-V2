package org.promasi.client.playmode.singleplayer.userstate;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.widgets.Shell;
import org.promasi.client.gui.GamesDialog;
import org.promasi.client.gui.IGamesDialogListener;
import org.promasi.client.playmode.IPlayMode;
import org.promasi.client.playmode.singleplayer.AbstractUserState;
import org.promasi.game.IGame;
import org.promasi.game.singleplayer.SinglePlayerGame;
import org.promasi.game.singleplayer.SinglePlayerGameBuilder;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.file.RootDirectory;

public class ChooseGameUserState  extends AbstractUserState implements IUserState,IGamesDialogListener
{
	/**
	 * 
	 */
	private GamesDialog _frame;
	
	/**
	 * 
	 */
	private Map<String, IGame> _games;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private String _selectedGame;
	
	/**
	 * 
	 * @throws NullArgumentException
	 * @throws IOException
	 */
	public ChooseGameUserState(Shell shell, IPlayMode playMode) throws NullArgumentException, IOException{
		super(playMode);
		_frame=new GamesDialog(shell,0);
		_games=readPlayModeGames();
		Map<String, String> gameList=new TreeMap<String, String>();
		for(Map.Entry<String, IGame> entry : _games.entrySet()){
			gameList.put(entry.getKey(), entry.getValue().getGameDescription());
		}
	
		_shell=shell;
		_frame.updateGameList(gameList);
		_frame.registerGamesDialogListener(this);
	}
	
	/**
	 * 
	 * @return
	 */
	private Map<String, IGame> readPlayModeGames() {
		Map<String, IGame> games=new TreeMap<String, IGame>();
		String uri;
		try {
			uri = _playMode.getUri();
			File file=new File(uri);
			if(file.isDirectory()){
				String gamesFolders[]=file.list();
				for(int i=0;i<gamesFolders.length;i++){
					try{
						SinglePlayerGameBuilder builder;
						builder = new SinglePlayerGameBuilder(uri+RootDirectory.getInstance().getSeparator()+gamesFolders[i]);
						games.put(gamesFolders[i], new SinglePlayerGame(builder.getGame()) );
					}catch(IllegalArgumentException e){
						//Logger
					}catch(NullArgumentException e){
						//Logger
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return games;
	}
	
	@Override
	public void run(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			
			_shell.getDisplay().asyncExec(new Runnable() {
						
				@Override
				public void run() {
					try {
						_frame.open();
					} catch (IllegalArgumentException e) {
						//Logger
					}
				}
			});
		}
	}

	@Override
	public synchronized void gameSelected(GamesDialog dialog, String selectedGame) {
		// TODO Auto-generated method stub
		_selectedGame=selectedGame;
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {		
				@Override
				public void run() {
					synchronized (ChooseGameUserState.this) {
						try {
							if(_games.containsKey(_selectedGame)){
								IGame game=_games.get(_selectedGame);
								_frame.hide();
								changeUserState(new PlayingGameUserState(_frame.getParent(), _playMode, game));
								_frame.removeGamesDialogListener(ChooseGameUserState.this);
								_frame.close();
							}
						} catch (IllegalArgumentException e) {
							//Logger
						} catch (NullArgumentException e) {
							//Logger
						}
					}
				}
			});
		}
	}

	@Override
	public void gamesDialogClosed(GamesDialog dialog) {
		_shell.dispose();
	}

}
