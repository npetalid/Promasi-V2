package org.promasi.client.gui;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.promasi.client.playmode.multiplayer.MultiPlayerPlayMode;
import org.promasi.game.GameModel;
import org.promasi.game.singleplayer.SinglePlayerGameBuilder;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.file.RootDirectory;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;

/**
 * 
 * @author m1cRo
 *
 */
public class MakeGameDialog extends Dialog 
{
	/**
	 * 
	 */
	protected Object _result;
	
	/**
	 * 
	 */
	protected Shell _shell;

	/**
	 * 
	 */
	private List _localGamesList;
	
	/**
	 * 
	 */
	private Browser _gameInfoBrowser;
	
	/**
	 * 
	 */
	private Map<String, GameModel> _availableGames;
	
	/**
	 * 
	 */
	private java.util.List<IMakeGameDialogListener> _listeners;
	
	/**
	 * 
	 */
	private Text _gameIdText;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MakeGameDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		_availableGames=readLocalGames();
		_listeners=new LinkedList<IMakeGameDialogListener>();
		createContents();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public synchronized void open() {
		_shell.open();
		_shell.layout();
		Display display = getParent().getDisplay();
		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		for(IMakeGameDialogListener listener : _listeners){
			listener.dialogClosed(this);
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), getStyle());
		_shell.setSize(788, 574);
		_shell.setText("Local games");
		
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		
		_localGamesList = new List(_shell, SWT.BORDER);
		
		_localGamesList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_localGamesList.getSelectionIndex();
				if(selectionIndex>=0){
					String gameId=_localGamesList.getItem(selectionIndex);
					if(_availableGames.containsKey(gameId)){
						_gameInfoBrowser.setText(_availableGames.get(gameId).getGameDescription());
					}
				}else{
					_gameInfoBrowser.setText("");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		_gameInfoBrowser = new Browser(_shell, SWT.NONE);
		
		Button createGameButton = new Button(_shell, SWT.NONE);
		createGameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_localGamesList.getSelectionIndex();
				if(selectionIndex>=0 && !_gameIdText.getText().isEmpty()){
					String gameId=_localGamesList.getItem(selectionIndex);
					if(_availableGames.containsKey(gameId)){
						GameModel game=_availableGames.get(gameId);
						for(IMakeGameDialogListener listener : _listeners){
							listener.creteGameButtonPressed(_gameIdText.getText(), game);
							_shell.dispose();
						}
					}
				}

			}
		});
		createGameButton.setText("Create");
		
		_gameIdText = new Text(_shell, SWT.BORDER);
		GroupLayout gl__shell = new GroupLayout(_shell);
		gl__shell.setHorizontalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.add(10)
					.add(_localGamesList, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
					.add(6)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(gl__shell.createSequentialGroup()
							.add(_gameIdText, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
							.add(11)
							.add(createGameButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.add(_gameInfoBrowser, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl__shell.setVerticalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.add(10)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(GroupLayout.TRAILING, gl__shell.createSequentialGroup()
							.add(_gameInfoBrowser, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(gl__shell.createParallelGroup(GroupLayout.BASELINE)
								.add(createGameButton, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.add(_gameIdText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.add(_localGamesList, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE))
					.add(10))
		);
		_shell.setLayout(gl__shell);
		
		for(Map.Entry<String, GameModel> entry : _availableGames.entrySet()){
			_localGamesList.add(entry.getKey());
		}
	}

	/**
	 * 
	 * @return
	 */
	private Map<String, GameModel> readLocalGames() {
		Map<String, GameModel> games=new TreeMap<String, GameModel>();
		String uri;
		try {
			uri = RootDirectory.getInstance().getRootDirectory()+MultiPlayerPlayMode.CONST_MULTIPLAYER_PLAYMODE_FOLDER_NAME;
			File file=new File(uri);
			if(file.isDirectory()){
				String gamesFolders[]=file.list();
				for(int i=0;i<gamesFolders.length;i++){
					try{
						SinglePlayerGameBuilder builder;
						builder = new SinglePlayerGameBuilder(uri+RootDirectory.getInstance().getSeparator()+gamesFolders[i]);
						games.put(gamesFolders[i],builder.getGame() );
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
	
	/**
	 * 
	 */
	public synchronized void close(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					_shell.dispose();		
				}
			});
		}
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean addListener(IMakeGameDialogListener listener)throws NullArgumentException{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(_listeners.contains(listener)){
			return false;
		}
		
		_listeners.add(listener);
		return true;
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean removeListener(IMakeGameDialogListener listener)throws NullArgumentException{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(!_listeners.contains(listener)){
			return false;
		}
		
		_listeners.remove(listener);
		return true;
	}
}
