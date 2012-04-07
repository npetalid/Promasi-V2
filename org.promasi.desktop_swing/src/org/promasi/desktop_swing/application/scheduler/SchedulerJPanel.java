/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXPanel;
import org.promasi.desktop_swing.IDesktop;
import org.promasi.game.IGame;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class SchedulerJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private GanttJPanel _scheduler;
	
	/**
	 * 
	 */
	private ISchedulerApplication _app;
	
	/**
	 * 
	 */
	private JPanel _taskPanel;
	
	/**
	 * 
	 * @param appName
	 * @throws GuiException
	 * @throws IOException 
	 */
	public SchedulerJPanel( IGame game, ISchedulerApplication app, IDesktop desktop ) throws GuiException, IOException {
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( app == null ){
			throw new GuiException("Wrong argument app == null");
		}
		
		if( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
		}
		
		
		_app = app;
		setLayout(new BorderLayout());
		_scheduler = new GanttJPanel(game);
		
		JXPanel wizardPanel = new JXPanel();
		wizardPanel.setBackground(Colors.LightBlue.alpha(1.f));
		
		wizardPanel.setLayout(new BorderLayout());
		JButton createButton = new JButton("Create Task");
		wizardPanel.add( createButton, BorderLayout.EAST);
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_app.setPanel(_taskPanel);
			}
		});
		
		add(_scheduler, BorderLayout.CENTER);
		add( wizardPanel, BorderLayout.SOUTH);
		_taskPanel = new NewTaskJPanel(game, _app, SchedulerJPanel.this, desktop);
	}
}
