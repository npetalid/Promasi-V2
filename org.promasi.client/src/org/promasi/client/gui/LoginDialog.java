package org.promasi.client.gui;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.promasi.utilities.exceptions.NullArgumentException;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;

/**
 * 
 * @author m1cRo
 *
 */
public class LoginDialog extends Dialog 
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
	private Text _userName;
	
	/**
	 * 
	 */
	private Text _password;
	
	/**
	 * 
	 */
	private Label _label;

	/**
	 * 
	 */
	private List<ILoginDialogListener> _listeners;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public LoginDialog(Shell parent, int style) {
		super(parent, style);
		setText("Login");
		_listeners=new LinkedList<ILoginDialogListener>();
		createContents();
		_shell.open();
		_shell.layout();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public void open() {
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					Display display = getParent().getDisplay();
					while (!_shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
				}
			});
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), getStyle());
		_shell.setSize(324, 216);
		_shell.setText("Login");
		
		_userName = new Text(_shell, SWT.BORDER);
		
		_password = new Text(_shell, SWT.BORDER);
		
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		
		Button _loginButton = new Button(_shell, SWT.NONE);
		_loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				synchronized(_listeners){
					if(_userName.getText().isEmpty()){
						MessageBox msgBox=new MessageBox(_shell,SWT.ICON_ERROR);
						msgBox.setText("Wrong UserName");
						msgBox.setMessage("Please check the UserName field");
						msgBox.open();
						return;
					}
					
					if(_password.getText().isEmpty()){
						MessageBox msgBox=new MessageBox(_shell,SWT.ICON_ERROR);
						msgBox.setText("Wrong Password");
						msgBox.setMessage("Please check the Password field");
						msgBox.open();
						return;
					}
					
					for(ILoginDialogListener listener : _listeners){
						listener.loginButtonPressed(_userName.getText(), _password.getText());
					}
				}
			}
		});
		_loginButton.setText("Login");
		
		Label lblUsername = new Label(_shell, SWT.NONE);
		lblUsername.setText("UserName");
		
		Label lblPassword = new Label(_shell, SWT.NONE);
		lblPassword.setText("Password");
		
		_label = new Label(_shell, SWT.NONE);
		_label.setText("Login");
		GroupLayout gl__shell = new GroupLayout(_shell);
		gl__shell.setHorizontalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.add(17)
					.add(_label, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
				.add(gl__shell.createSequentialGroup()
					.add(17)
					.add(lblUsername, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.add(6)
					.add(_userName, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addContainerGap())
				.add(gl__shell.createSequentialGroup()
					.add(17)
					.add(lblPassword, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.add(31)
					.add(_password, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addContainerGap())
				.add(GroupLayout.TRAILING, gl__shell.createSequentialGroup()
					.add(240)
					.add(_loginButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.add(10))
		);
		gl__shell.setVerticalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.add(10)
					.add(_label)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(gl__shell.createSequentialGroup()
							.add(37)
							.add(lblUsername))
						.add(gl__shell.createSequentialGroup()
							.add(34)
							.add(_userName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(gl__shell.createSequentialGroup()
							.add(23)
							.add(lblPassword))
						.add(gl__shell.createSequentialGroup()
							.add(20)
							.add(_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.add(20)
					.add(_loginButton, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
		);
		_shell.setLayout(gl__shell);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean addListener(final ILoginDialogListener listener)throws NullArgumentException{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		synchronized(_listeners){
			if(_listeners.contains(listener)){
				return false;
			}
			
			_listeners.add(listener);
		}

		return true;
	}
	
	/**
	 * 
	 */
	public synchronized void showLoginFailedMessage(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					MessageBox msgBox=new MessageBox(_shell,SWT.ICON_ERROR);
					msgBox.setText("Login failed");
					msgBox.setMessage("Wrong UserName and or Password");
					msgBox.open();
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
	public synchronized boolean removeListener(final ILoginDialogListener listener)throws NullArgumentException{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		synchronized(_listeners){
			if(!_listeners.contains(listener)){
				return false;
			}
			
			_listeners.remove(listener);	
		}

		return true;
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
	 */
	public synchronized void hide(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					_shell.setVisible(false);
				}
			});
		}
	}
	
	/**
	 * 
	 */
	public synchronized void show(){
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					_shell.setVisible(true);
				}
			});
		}
	}
}
