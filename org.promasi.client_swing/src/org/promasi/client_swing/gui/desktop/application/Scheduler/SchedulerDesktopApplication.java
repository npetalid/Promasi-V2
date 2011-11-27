/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.io.IOException;
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
	 * @param game
	 * @throws GuiException
	 * @throws IOException
	 */
	public SchedulerDesktopApplication( IGame game ) throws GuiException, IOException{
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		setLayout(new BorderLayout());
		add( new SchedulerJPanel( game ), BorderLayout.CENTER);
	}

	@Override
	public void createTask(String taskName, Task task) {
		// TODO Auto-generated method stub
		
	}

}
