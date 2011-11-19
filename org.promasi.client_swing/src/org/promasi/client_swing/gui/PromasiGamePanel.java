/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.desktop.taskbar.TaskBarPanel;
import org.promasi.game.IGame;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;
import org.promasi.game.singleplayer.IClientGameListener;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class PromasiGamePanel extends JPanel implements IClientGameListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final String CONST_BG_IMAGE_NAME = "wallpaper.png";
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private TaskBarPanel _taskBarPanel;
	
	/**
	 * 
	 */
	private Image _bgImage;
	
	/**
	 * 
	 */
	public PromasiGamePanel( IGame game, String username )throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( username == null || username.isEmpty() ){
			throw new GuiException("Wrong argument username");
		}
		
		_game = game;
		_game.addListener(this);
		setLayout(new BorderLayout());
		_taskBarPanel = new TaskBarPanel( username );
		add( _taskBarPanel, BorderLayout.SOUTH );
		try {
			String imagePath = RootDirectory.getInstance().getImagesDirectory() + CONST_BG_IMAGE_NAME;
			_bgImage = new ImageIcon(imagePath).getImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics graphics) {
		graphics.drawImage(_bgImage, 0, 0, this.getBounds().width, this.getBounds().height, null);
	}

	@Override
	public void gameStarted(IGame game, SerializableGameModel gameModel,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectAssigned(IGame game, SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(IGame game, SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(IGame game, SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeDischarged(IGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTasksAttached(IGame game, SerializableCompany company,
			SerializableEmployee employee,
			List<SerializableEmployeeTask> employeeTasks, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskDetached(IGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee,
			SerializableEmployeeTask employeeTask, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(IGame game, SerializableCompany company,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(IGame game, SerializableCompany company,
			SerializableProject assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(IGame game, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_taskBarPanel.updateTime(dateTime);
			}
		});
		
	}

	@Override
	public void gameFinished(IGame game, SerializableGameModel gameModel,
			SerializableCompany company) {
		// TODO Auto-generated method stub
		
	}

}
