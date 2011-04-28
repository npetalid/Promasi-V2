package org.promasi.client.gui;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Button;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;

/**
 * 
 * @author m1cRo
 *
 */
public class GamesDialog extends Dialog implements SelectionListener 
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
	private List _gameList;
	
	/**
	 * 
	 */
	private Browser _gameInfoBrowser;
	
	/**
	 * 
	 */
	private java.util.List<IGamesDialogListener> _listeners;
	
	/**
	 * 
	 */
	private Map<String, String> _games;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GamesDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		_listeners=new LinkedList<IGamesDialogListener>();	
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
		
		for(IGamesDialogListener listener : _listeners){
			listener.gamesDialogClosed(this);
		}
	}
	
	/**
	 * 
	 */
	public synchronized void hide(){
		_shell.setVisible(false);
	}
	
	/**
	 * 
	 */
	public synchronized void close(){
		_shell.dispose();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		_shell.setEnabled(true);
		_shell.setSize(646, 430);
		_shell.setText("Available Games");
		_gameList = new List(_shell, SWT.BORDER);

		
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		
		_gameInfoBrowser = new Browser(_shell, SWT.NONE);
		
		_gameList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_gameList.getSelectionIndex();
				if(selectionIndex>=0){
					String gameName=_gameList.getItem(selectionIndex);
					if(_games.containsKey(gameName)){
						_gameInfoBrowser.setText(_games.get(gameName));
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button playButton = new Button(_shell, SWT.NONE);
		playButton.setText("Play");
		GroupLayout gl__shell = new GroupLayout(_shell);
		gl__shell.setHorizontalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(GroupLayout.TRAILING, gl__shell.createSequentialGroup()
					.add(10)
					.add(_gameInfoBrowser, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
					.add(6)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING, false)
						.add(GroupLayout.TRAILING, gl__shell.createSequentialGroup()
							.add(playButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
						.add(GroupLayout.TRAILING, gl__shell.createSequentialGroup()
							.add(_gameList, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.add(10))))
		);
		gl__shell.setVerticalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.add(10)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(_gameInfoBrowser, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
						.add(_gameList, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(playButton)
					.add(8))
		);
		_shell.setLayout(gl__shell);
		playButton.addSelectionListener(this);
	}
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public synchronized void updateGameList(Map<String, String> games)throws NullArgumentException, IllegalArgumentException{
		if(_gameList==null){
			return;
		}
		
		if(games==null){
			throw new NullArgumentException("Wrong arugment games==null");
		}
		
		for(Map.Entry<String, String> entry : games.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				throw new IllegalArgumentException("Wrong argument games contains null");
			}	
		}
		
		_games=new TreeMap<String, String>(games);
		for(Map.Entry<String, String> entry : _games.entrySet()){
			_gameList.add(entry.getKey());
		}
	}

	@Override
	public synchronized void widgetDefaultSelected(SelectionEvent arg0) {

	}

	@Override
	public synchronized void widgetSelected(SelectionEvent arg0) {
		int selectionIndex=_gameList.getSelectionIndex();
		if(selectionIndex>=0){
			String selectedItem=_gameList.getItem(selectionIndex);
			if( _games.containsKey(selectedItem) ){
				for(IGamesDialogListener listener : _listeners){
					listener.gameSelected(this, selectedItem);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param gamesDialogListener
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean registerGamesDialogListener(IGamesDialogListener gamesDialogListener)throws NullArgumentException{
		if(gamesDialogListener==null){
			throw new NullArgumentException("Wrong argument eventHandler==null");
		}
		
		if(_listeners.contains(gamesDialogListener)){
			return false;
		}
		
		_listeners.add(gamesDialogListener);
		return true;
	}
	
	/**
	 * 
	 * @param gamesDialogListener
	 * @return
	 */
	public synchronized boolean removeGamesDialogListener(IGamesDialogListener gamesDialogListener)throws NullArgumentException{
		if(gamesDialogListener==null){
			throw new NullArgumentException("Wrong argument eventHandler==null");
		}
		
		if(!_listeners.contains(gamesDialogListener)){
			return false;
		}
		
		_listeners.remove(gamesDialogListener);
		return true;
	}

}
