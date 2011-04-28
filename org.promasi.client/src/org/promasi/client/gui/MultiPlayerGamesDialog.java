/**
 * 
 */
package org.promasi.client.gui;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;

/**
 * @author m1cRo
 *
 */
public class MultiPlayerGamesDialog extends Dialog {
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
	private Browser _gameInfo;
	
	/**
	 * 
	 */
	private java.util.List<IMultiPlayerGamesDialogListener> _listeners;
	
	/**
	 * 
	 */
	private Map<String, String> _games;
	private Button _button;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MultiPlayerGamesDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		_listeners=new LinkedList<IMultiPlayerGamesDialogListener>();	
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
	}
	
	/**
	 * 
	 */
	public void hide(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					if(!_shell.isDisposed()){
						_shell.setVisible(false);
					}
				}
			});
		}
	}
	
	public void show(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					if(!_shell.isDisposed()){
						_shell.setVisible(true);
					}
				}
			});
		}
	}
	
	/**
	 * 
	 */
	public void close(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					if(!_shell.isDisposed()){
						_shell.close();
					}
				}
			});
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		_shell.setEnabled(true);
		_shell.setSize(609, 460);
		_shell.setText("Available Games");
		_gameList = new List(_shell, SWT.BORDER);

		
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		
		_gameInfo = new Browser(_shell, SWT.NONE);
		
		_gameList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_gameList.getSelectionIndex();
				if(selectionIndex>=0){
					String gameName=_gameList.getItem(selectionIndex);
					if(_games.containsKey(gameName)){
						_gameInfo.setText(_games.get(gameName));
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
		
		_button = new Button(_shell, SWT.NONE);
		_button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				for(IMultiPlayerGamesDialogListener listener : _listeners){
					listener.createNewGameSelected();
				}
			}
		});
		
		_button.setText("Create");
		GroupLayout gl__shell = new GroupLayout(_shell);
		gl__shell.setHorizontalGroup(
			gl__shell.createParallelGroup(GroupLayout.TRAILING)
				.add(gl__shell.createSequentialGroup()
					.addContainerGap()
					.add(_gameList, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.UNRELATED)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(GroupLayout.TRAILING, gl__shell.createSequentialGroup()
							.add(_button, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(playButton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
						.add(_gameInfo, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl__shell.setVerticalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.addContainerGap()
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(_gameList, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
						.add(gl__shell.createSequentialGroup()
							.add(_gameInfo, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(gl__shell.createParallelGroup(GroupLayout.BASELINE)
								.add(playButton)
								.add(_button))))
					.addContainerGap())
		);
		_shell.setLayout(gl__shell);
		
		playButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_gameList.getSelectionIndex();
				if(selectionIndex>=0){
					String selectedItem=_gameList.getItem(selectionIndex);
					if( _games.containsKey(selectedItem) ){
						for(IMultiPlayerGamesDialogListener eventHandler : _listeners){
							eventHandler.gameSelected(MultiPlayerGamesDialog.this, selectedItem);
						}
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public void updateGameList(Map<String, String> games)throws NullArgumentException, IllegalArgumentException{
		if(games==null){
			throw new NullArgumentException("Wrong arugment games==null");
		}
		
		for(Map.Entry<String, String> entry : games.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				throw new IllegalArgumentException("Wrong argument games contains null");
			}	
		}
		
		_games=new TreeMap<String, String>(games);
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					if(!_shell.isDisposed()){
						if(_gameList==null || _games==null){
							return;
						}
					
						_gameList.removeAll();
						for(Map.Entry<String, String> entry : _games.entrySet()){
							_gameList.add(entry.getKey());
						}
					}
				}
			});
		}
	}
	
	/**
	 * 
	 * @param eventHandler
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean addListener(IMultiPlayerGamesDialogListener eventHandler)throws NullArgumentException{
		if(eventHandler==null){
			throw new NullArgumentException("Wrong argument eventHandler==null");
		}
		
		if(_listeners.contains(eventHandler)){
			return false;
		}
		
		_listeners.add(eventHandler);
		return true;
	}
}
