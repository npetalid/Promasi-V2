/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;


/**
 * @author alekstheod
 *
 */
public class SchedulerJPanel extends JPanel implements ICompanyListener, IDepartmentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private GanttScheduler _scheduler;
	
	/**
	 * 
	 */
	private ISchedulerApplication _app;
	
	/**
	 * 
	 */
	private boolean _needToUpdateCompany;
	
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
	public SchedulerJPanel( IGame game, ISchedulerApplication app ) throws GuiException, IOException {
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( app == null ){
			throw new GuiException("Wrong argument app == null");
		}
		
		_lockObject = new ReentrantLock();
		_app = app;
		setLayout(new BorderLayout());
		_scheduler = new GanttScheduler();
		
		JPanel wizardPanel = new JPanel();
		wizardPanel.setLayout(new BorderLayout());
		JButton createButton = new JButton("New");
		wizardPanel.add( createButton, BorderLayout.EAST);
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_app.setPanel(_taskPanel);
			}
		});
		
		add(_scheduler, BorderLayout.CENTER);
		add( wizardPanel, BorderLayout.SOUTH);
		_taskPanel = new TaskJPanel(game, _app, SchedulerJPanel.this);
		_needToUpdateCompany = true;
		game.addCompanyListener(this);
		game.addDepartmentListener(this);
	}

	@Override
	public void projectAssigned(final String owner, final CompanyMemento company,
			final ProjectMemento project, final DateTime dateTime) {
		
		if( project != null ){
			SwingUtilities.invokeLater( new Runnable() {
				
				@Override
				public void run() {
					try{
						_lockObject.lock();
						_scheduler.projectAssigned(owner, company, project, dateTime);
					}finally{
						_lockObject.unlock();
					}
					
				}
			});
		}
	}

	@Override
	public void projectFinished(final String owner, final CompanyMemento company,
			final ProjectMemento project, final DateTime dateTime) {

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					_scheduler.clearScheduler();
				}finally{
					_lockObject.unlock();
				}
			}
		});
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		projectFinished(owner, company, assignedProject, dateTime);
		_needToUpdateCompany = true;
	}

	@Override
	public void onExecuteWorkingStep(String owner, final CompanyMemento company,
			final ProjectMemento assignedProject, DateTime dateTime) {
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					if( _needToUpdateCompany ){
						_scheduler.setCompany(company, assignedProject);
						_needToUpdateCompany = false;
					}
					
				}finally{
					_lockObject.unlock();
				}
				
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		try{
			_lockObject.lock();
			_needToUpdateCompany = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateCompany = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateCompany = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateCompany = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateCompany = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void departmentAssigned(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}
}
