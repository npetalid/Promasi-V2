/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXPanel;
import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.ProjectModel;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class DurationJPanel extends JXPanel implements ICompanyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private DateTime _projectAssignDate;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private JSpinner _durationSpinner;
	
	
	/**
	 * 
	 */
	private JXDatePicker _startDatePicket;
	
	/**
	 * 
	 * @param game
	 * @throws GuiException
	 */
	public DurationJPanel( IGame game )throws GuiException
	{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
	
		_projectAssignDate = new DateTime();
		_lockObject = new ReentrantLock();
		setLayout(new BorderLayout());
		
		JPanel startDatePanel = new JPanel();
		startDatePanel.setLayout(new BorderLayout());
		_startDatePicket = new JXDatePicker();
		_startDatePicket.setDate(new Date());
		startDatePanel.add(_startDatePicket, BorderLayout.WEST);
		startDatePanel.setBorder(BorderFactory.createTitledBorder("Start at"));
		add(startDatePanel, BorderLayout.WEST);
		setBorder(BorderFactory.createTitledBorder("Time scheduler"));
		
		JPanel endDatePanel = new JPanel();
		endDatePanel.setLayout(new BorderLayout());
		_durationSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
		_durationSpinner.setPreferredSize(new Dimension(100,20));
		endDatePanel.add(_durationSpinner, BorderLayout.EAST);
		endDatePanel.setBorder(BorderFactory.createTitledBorder("Duration"));
		add(endDatePanel, BorderLayout.EAST);
		
		game.addCompanyListener(this);
		setOpaque(false);
		setBackground(Colors.White.alpha(0f));
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFirstStep(){
		int result = 0;
		
		try{
			_lockObject.lock();
			Calendar startDate = Calendar.getInstance();
			startDate.setTime(_startDatePicket.getDate());
			
			Calendar assignDate = Calendar.getInstance();
			assignDate.setTime(_projectAssignDate.toDate());
			
			long differenceFromStart = (startDate.getTimeInMillis() - assignDate.getTimeInMillis())/(60 * 60 * 1000);
			result = (int)differenceFromStart;
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLastStep(){
		return getFirstStep() + (Integer)_durationSpinner.getValue()*24;
	}
	
	@Override
	public void projectAssigned(String owner, CompanyModel company,final ProjectModel project, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					_projectAssignDate = dateTime;
					_startDatePicket.setDate(dateTime.toDate());
				}finally{
					_lockObject.unlock();
				}
			}
		});
		
	}

	@Override
	public void projectFinished(String owner, CompanyModel company,ProjectModel project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyModel company,ProjectModel assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyModel company, ProjectModel assignedProject,final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					if( dateTime.toDate().after(_startDatePicket.getDate()) ){
						_startDatePicket.setDate(dateTime.plusDays(2).toDate());
					}
				}finally{
					_lockObject.unlock();
				}
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyModel company) {
		// TODO Auto-generated method stub
		
	}
}
