/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.game.IGame;

import org.promasi.utilities.file.RootDirectory;


/**
 * @author alekstheod
 *
 */
public class SchedulerDesktopApplication extends ADesktopApplication implements ISchedulerApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String CONST_APPNAME = "Scheduler";
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "planner.png";
	
	/**
	 * 
	 */
	private JPanel _internalPanel;
	
	/**
	 * 
	 * @param game
	 * @throws GuiException
	 * @throws IOException
	 */
	public SchedulerDesktopApplication( IGame game ) throws GuiException, IOException{
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		setLayout(new BorderLayout());
		_internalPanel = new JPanel();
		_internalPanel.setLayout(new BorderLayout());
		add(_internalPanel, BorderLayout.CENTER);
		_internalPanel.add( new SchedulerJPanel( game , this), BorderLayout.CENTER);
	}

	@Override
	public void setPanel(JPanel panel) {
		if( panel != null ){
			this.validate();
			this.repaint();
			_internalPanel.removeAll();
			_internalPanel.add(panel, BorderLayout.CENTER);
			_internalPanel.validate();
			_internalPanel.repaint();
		}
	}

}
