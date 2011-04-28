package org.promasi.client;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.promasi.client.gui.PlayModesDialog;
import org.promasi.temp.GameMaker;
import org.promasi.utilities.file.RootDirectory;

/**
 * 
 * @author m1cRo
 *
 */
public class Application{

	/**
	 * 
	 */
	protected Shell _shell;
	
	/**
	 * 
	 */
	private Timer _timer=new Timer();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GameMaker.makeGame();
			Application window = new Application();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		_shell.open();
		_shell.layout();
		
		_timer.schedule(new TimerTask(){
			@Override
			public void run() {
				if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
					_shell.getDisplay().asyncExec( new Runnable() {	
						@Override
						public void run() {
							_shell.setAlpha(0);
							PlayModesDialog playModesDialog=new PlayModesDialog(_shell, 0);
							playModesDialog.open();
						}
					});
					
					_timer.cancel();
				}
			}
		}, 5000);
		
		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		_shell = new Shell(SWT.NONE);
		_shell.setSize(688, 198);
		_shell.setAlpha(240);
		_shell.setToolTipText("Project Manager Simulator");
		Rectangle displayBoudns = _shell.getDisplay().getBounds();
		Rectangle dialogBounds=_shell.getBounds();
		_shell.setLocation(displayBoudns.width/2-dialogBounds.width/2, displayBoudns.height/2-dialogBounds.height/2);
		_shell.setText("ProMaSi");
		
		try {
			Image img=new Image(_shell.getDisplay(),RootDirectory.getInstance().getImagesDirectory()+"promasi.png");
			_shell.setLayout(new FillLayout(SWT.HORIZONTAL));
			_shell.setImage(img);
			_shell.setBackgroundImage(img);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
