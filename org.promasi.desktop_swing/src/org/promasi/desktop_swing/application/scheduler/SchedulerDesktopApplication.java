/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.desktop_swing.IDesktop;
import org.promasi.desktop_swing.application.ADesktopApplication;
import org.promasi.desktop_swing.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.IDepartmentListener;

import org.promasi.utilities.file.RootDirectory;
import org.promasi.utils_swing.GuiException;


/**
 * @author alekstheod
 * Represent the tasks scheduler application in the ProMaSi system.
 * This class contains a Gantt chart implementation whcih will present 
 * to the user the scheduled gantt chart.
 */
public class SchedulerDesktopApplication extends ADesktopApplication implements ISchedulerApplication, IDepartmentListener {

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
	public static final String CONST_APP_ICON = "gantt.png";
	
	/**
	 * 
	 */
	private QuickStartButton _quickButton;
	
	/**
	 * 
	 */
	private JPanel _internalPanel;
	
	/**
	 * Constructor will initialize the object.
	 * When the constructor is called the instance of the
	 * current class will register the department listener on 
	 * the given instance of {@link IGame} interface implementation.
	 * @param game instance of {@link IGame} inteface implementation.
	 * @throws GuiException in case of invalid arguments.
	 * @throws IOException in case of initialization fail.
	 */
	public SchedulerDesktopApplication( IGame game, IDesktop desktop ) throws GuiException, IOException{
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		if( game == null){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
		}
		
		setLayout(new BorderLayout());
		_internalPanel = new JPanel();
		_internalPanel.setLayout(new BorderLayout());
		add(_internalPanel, BorderLayout.CENTER);
		_internalPanel.add( new SchedulerJPanel( game , this, desktop), BorderLayout.CENTER);
		_quickButton = new QuickStartButton(this, desktop); 
		desktop.addQuickStartButton(_quickButton);
		game.addDepartmentListener(this);
		
		ProductivityWidget prodWidget = new ProductivityWidget();
		desktop.addWidget(prodWidget);
		game.addCompanyListener(prodWidget);
	}

	@Override
	public void setPanel(JPanel panel) {
		if( panel != null ){
			_internalPanel.removeAll();
			_internalPanel.add(panel, BorderLayout.CENTER);
			_internalPanel.invalidate();
			_internalPanel.repaint();
		}
	}

	@Override
	public void employeeDischarged(String director,
			DepartmentMemento department, EmployeeMemento employee,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department,
			EmployeeMemento employee, DateTime dateTime) {
		// TODO Auto-generated method stub
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department,
			DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_quickButton.showPopupNotifier( "Tasks assigned");
			}
		});
	}

	@Override
	public void tasksAssignFailed(String director,
			DepartmentMemento department, DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_quickButton.showPopupNotifier( "Assign tasks failed");
			}
		});
	}

	@Override
	public void departmentAssigned(String director,
			DepartmentMemento department, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

}
