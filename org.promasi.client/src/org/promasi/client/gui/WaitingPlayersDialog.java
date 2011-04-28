package org.promasi.client.gui;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;

public class WaitingPlayersDialog extends Dialog 
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
	private Text _messageText;
	
	/**
	 * 
	 */
	private String _gameName;
	
	/**
	 * 
	 */
	private String _gameDescription;
	
	/**
	 * 
	 */
	private IWaitingPlayersDialogListener _listener;
	
	/**
	 * 
	 */
	private java.util.List<String> _playersList;
	
	/**
	 * 
	 */
	private List _pList;
	
	/**
	 * 
	 */
	private StyledText _chatText;
	
	/**
	 * 
	 */
	private Queue<Message> _receivedMessages;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public WaitingPlayersDialog(Shell parent, int style, String gameName, String gameDescription,IWaitingPlayersDialogListener listener, java.util.List<String> playersList)throws NullArgumentException {
		super(parent, style);
		setText("SWT Dialog");
		
		if(gameName==null){
			throw new NullArgumentException("Wrong argument gameName==null");
		}
		
		if(gameDescription==null){
			throw new NullArgumentException("Wrong argument gameDescription==null");
		}
		
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(playersList==null){
			throw new NullArgumentException("Wrong argument playersList==null");
		}
		
		_playersList=new LinkedList<String>(playersList);
		_listener=listener;
		_gameName=gameName;
		_gameDescription=gameDescription;
		_receivedMessages=new LinkedList<Message>();
		createContents();
	}

	/**
	 * 
	 */
	public void open() {
		_shell.open();
		_shell.layout();
		Display display = getParent().getDisplay();
		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		_listener.dialogClosed();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), getStyle());
		_shell.setSize(751, 559);
		_shell.setText(_gameName);
		
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		
		_pList = new List(_shell, SWT.BORDER);
		
		Browser gameInfoBrowser = new Browser(_shell, SWT.NONE);
		gameInfoBrowser.setText(_gameDescription);
		
		Button sendButton = new Button(_shell, SWT.NONE);
		sendButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(_messageText.getText().length()>0){
					_listener.sendButtonPressed(_messageText.getText());
				}
			}
		});
		
		sendButton.setText("Send");
		
		_messageText = new Text(_shell, SWT.BORDER);
		
		_chatText = new StyledText(_shell, SWT.BORDER);
		_chatText.setEditable(false);
		
		Button startGameButton = new Button(_shell, SWT.NONE);
		startGameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				_listener.startGameButtonPressed();
			}
		});
		startGameButton.setText("Start");
		GroupLayout gl__shell = new GroupLayout(_shell);
		gl__shell.setHorizontalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.addContainerGap()
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(startGameButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(_pList, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.UNRELATED)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(_chatText, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
						.add(gl__shell.createSequentialGroup()
							.add(_messageText, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.UNRELATED)
							.add(sendButton, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
						.add(GroupLayout.TRAILING, gameInfoBrowser, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl__shell.setVerticalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.addContainerGap()
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(gl__shell.createSequentialGroup()
							.add(gameInfoBrowser, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.add(_chatText, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
						.add(_pList, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING, false)
						.add(startGameButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(gl__shell.createParallelGroup(GroupLayout.BASELINE)
							.add(_messageText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.add(19))
		);
		_shell.setLayout(gl__shell);

		for(String playerId : _playersList){
			_pList.add(playerId);
		}
	}
	
	/**
	 * 
	 * @param playersList
	 * @throws NullArgumentException
	 */
	public synchronized void updatePlayersList(java.util.List<String> playersList)throws NullArgumentException{
		if(playersList==null){
			throw new NullArgumentException("Wrong argument playersList==null");
		}
		
		_playersList.clear();
		for(String playerId : playersList){
			_playersList.add(playerId);
		}
		
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					_pList.removeAll();
					synchronized(WaitingPlayersDialog.this){
						for(String playerId : _playersList){
							_pList.add(playerId);
						}
					}

				}
			});
		}
	}
	
	/**
	 * 
	 * @author m1cRo
	 *
	 */
	class Message{
		/**
		 * 
		 */
		private String _playerId;
		
		/**
		 * 
		 */
		private String _message;
		
		/**
		 * 
		 * @param playerId
		 * @param message
		 * @throws NullArgumentException
		 */
		public Message(String playerId, String message)throws NullArgumentException{
			if(playerId==null){
				throw new NullArgumentException("Wrong argument playerId==null");
			}
			
			if(message==null){
				throw new NullArgumentException("Wrong argument message==null");
			}
			
			_playerId=playerId;
			_message=message;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getMessageString(){
			return _playerId+" : "+_message+"\n";
		}
	}
	
	/**
	 * 
	 * @param playerId
	 * @param message
	 * @throws NullArgumentException
	 */
	public synchronized void messageReceived(String playerId, String message)throws NullArgumentException{
		if(playerId==null){
			throw new NullArgumentException("Wrong argument playerId==null");
		}
		
		if(message==null){
			throw new NullArgumentException("Wrong argument message==null");
		}
		
		Message newMessage=new Message(playerId, message);
		_receivedMessages.add(newMessage);
		
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					synchronized(WaitingPlayersDialog.this){
						while(!_receivedMessages.isEmpty()){
							Message msg=_receivedMessages.poll();
							_chatText.insert(msg.getMessageString());
						}
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
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					_shell.dispose();
				}
			});
		}
	}
	
	/**
	 * 
	 */
	public void hide(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					if(!_shell.isDisposed()){
						_shell.setVisible(false);
					}
				}
			});
		}
	}
	
	/**
	 * 
	 */
	public void show(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					if(!_shell.isDisposed()){
						_shell.setVisible(true);
					}
				}
			});
		}
	}
}
