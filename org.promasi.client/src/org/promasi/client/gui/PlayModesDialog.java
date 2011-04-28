package org.promasi.client.gui;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.promasi.client.playmode.multiplayer.MultiPlayerPlayMode;
import org.promasi.client.playmode.singleplayer.SinglePlayerPlayMode;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class PlayModesDialog extends Dialog {

	protected Object result;
	protected Shell _shell;
	private Shell _parentShell;
	private List _playModeList;
	private Browser _infoBrowser;
	boolean _playModeSelected;
	
	/**
	 * SinglePlayer mode title shown in PlayModes list.
	 */
	public static final String CONST_SINGLEPLAYER_PLAYMODE_NAME="Single-Player";
	
	/**
	 * MultiPlayer mode title shown in PlayModes list.
	 */
	public static final String CONST_MULTIPLAYER_PLAYMODE_NAME="Multi-Player";
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PlayModesDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		_parentShell=parent;
		_playModeSelected=false;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		_shell.open();
		_shell.layout();
		Display display = getParent().getDisplay();
		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		if(!_playModeSelected){
			_parentShell.dispose();
		}
		
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE);
		_shell.setSize(713, 484);
		_shell.setText("Select PlayMode");
		
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		_shell.setLayout(new FormLayout());
		
		_playModeList= new List(_shell, SWT.BORDER);
		FormData fd__playModeList = new FormData();
		fd__playModeList.bottom = new FormAttachment(0, 403);
		fd__playModeList.right = new FormAttachment(0, 688);
		fd__playModeList.top = new FormAttachment(0, 10);
		fd__playModeList.left = new FormAttachment(0, 542);
		_playModeList.setLayoutData(fd__playModeList);
		
		
		_playModeList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_playModeList.getSelectionIndex();
				if(selectionIndex==0){
					_infoBrowser.setText(SinglePlayerPlayMode.CONST_PLAYMODE_DESCRIPTION);
				}else if(selectionIndex==1){
					_infoBrowser.setText(MultiPlayerPlayMode.CONST_PLAYMODE_DESCRIPTION);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		_playModeList.add(CONST_SINGLEPLAYER_PLAYMODE_NAME);
		_playModeList.add(CONST_MULTIPLAYER_PLAYMODE_NAME);
		
		_infoBrowser = new Browser(_shell, SWT.NONE);
		FormData fd__infoBrowser = new FormData();
		fd__infoBrowser.left = new FormAttachment(0, 10);
		fd__infoBrowser.right = new FormAttachment(_playModeList, -6);
		fd__infoBrowser.bottom = new FormAttachment(100, -53);
		fd__infoBrowser.top = new FormAttachment(0, 10);
		_infoBrowser.setLayoutData(fd__infoBrowser);
		
		Button _selectButton = new Button(_shell, SWT.NONE);
		FormData fd__selectButton = new FormData();
		fd__selectButton.right = new FormAttachment(0, 688);
		fd__selectButton.top = new FormAttachment(0, 409);
		fd__selectButton.left = new FormAttachment(0, 542);
		_selectButton.setLayoutData(fd__selectButton);
		_selectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(_playModeList.getSelectionIndex()==0){
					try {
						SinglePlayerPlayMode playMode;
						playMode = new SinglePlayerPlayMode(getParent());
						playMode.play();
						_playModeSelected=true;
						_shell.dispose();
					} catch (NullArgumentException e) {
						//Logger
					}
					
					_shell.dispose();
				}else if(_playModeList.getSelectionIndex()==1){
					try {
						MultiPlayerPlayMode playMode = new MultiPlayerPlayMode(getParent());
						playMode.play();
						_playModeSelected=true;
						_shell.dispose();
					} catch (NullArgumentException e) {
						//Logger
					} catch (IllegalArgumentException e) {
						//Logger
					} catch (UnknownHostException e) {
						_shell.open();
					} catch (IOException e) {
						//Logger
					}
				}
			}
		});
		_selectButton.setText("Select");

	}
}
