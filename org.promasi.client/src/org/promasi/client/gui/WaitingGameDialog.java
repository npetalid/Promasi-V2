package org.promasi.client.gui;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * 
 * @author m1cRo
 *
 */
public class WaitingGameDialog extends Dialog 
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
	private IWaitingGameDialogListener _listener;
	
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
	public WaitingGameDialog(Shell parent, int style, String gameName, String gameDescription,IWaitingGameDialogListener listener, java.util.List<String> playersList)throws NullArgumentException {
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
		_receivedMessages=new LinkedList<WaitingGameDialog.Message>();
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
		
		_listener.dialogClosed(this);
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), getStyle());
		_shell.setSize(751, 568);
		_shell.setText(_gameName);
		
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		
		_pList = new List(_shell, SWT.BORDER);
		_pList.setBounds(0, 10, 134, 518);
		
		Browser gameInfoBrowser = new Browser(_shell, SWT.NONE);
		gameInfoBrowser.setBounds(142, 10, 593, 374);
		gameInfoBrowser.setText(_gameDescription);
		
		Button sendButton = new Button(_shell, SWT.NONE);
		sendButton.setBounds(613, 503, 122, 25);
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
		_messageText.setBounds(140, 505, 467, 25);
		
		_chatText = new StyledText(_shell, SWT.BORDER);
		_chatText.setBounds(140, 390, 595, 109);
		_chatText.setEditable(false);

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
					synchronized(WaitingGameDialog.this){
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
					synchronized(WaitingGameDialog.this){
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
}
