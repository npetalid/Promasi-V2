/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;

/**
 * @author alekstheod
 *
 */
public class TaskJPanel extends JPanel implements IDepartmentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private ISchedulerApplication _application;
	
	/**
	 * 
	 */
	private ProjectMemento _project;
	
	/**
	 * 
	 */
	private CompanyMemento _company;
	
	/**
	 * 
	 */
	private JPanel _prevPanel;
	
	/**
	 * 
	 * @param game
	 * @param app
	 * @throws GuiException
	 */
	public TaskJPanel( IGame game, ISchedulerApplication app, CompanyMemento company, ProjectMemento project, JPanel prevPanel)throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( app == null ){
			throw new GuiException("Wrong argument app == null");
		}
		
		if( project == null ){
			throw new GuiException("Wrong argument project == null");
		}
		
		if( company == null ){
			throw new GuiException("Wrong argument company == null");
		}
		
		if( prevPanel == null ){
			throw new GuiException("Wrong argument prevPanel == null");
		}
		
		setLayout(new BorderLayout());
		
		_company = company;
		_project = project;
		_game = game;
		_application = app;
		_prevPanel = prevPanel;
	
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.SOUTH);
		
		JButton createTaskButton = new JButton("Add Task");
		controlPanel.add(createTaskButton, BorderLayout.EAST);
		
		
		JButton backButton = new JButton("Back");
		controlPanel.add(backButton, BorderLayout.WEST);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_application.setPanel(_prevPanel);
			}
		});
		
		JPanel taskPanel = new JPanel();
		add( taskPanel, BorderLayout.CENTER);
		
	}

	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					_application.setPanel(_prevPanel);
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}

}
