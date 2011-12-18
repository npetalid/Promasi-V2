/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.locks.Lock;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jdesktop.swingx.JXDatePicker;
import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.project.ProjectMemento;

/**
 * @author alekstheod
 *
 */
public class DurationJPanel extends JPanel implements ICompanyListener{

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
	
		setLayout(new BorderLayout());
		
		JPanel startDatePanel = new JPanel();
		startDatePanel.setLayout(new BorderLayout());
		_startDatePicket = new JXDatePicker();
		startDatePanel.add(_startDatePicket, BorderLayout.WEST);
		startDatePanel.setBorder(BorderFactory.createTitledBorder("Start at"));
		add(startDatePanel, BorderLayout.WEST);
		setBorder(BorderFactory.createTitledBorder("Time scheduler"));
		
		JPanel endDatePanel = new JPanel();
		endDatePanel.setLayout(new BorderLayout());
		_durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
		_durationSpinner.setPreferredSize(new Dimension(100,20));
		endDatePanel.add(_durationSpinner, BorderLayout.EAST);
		endDatePanel.setBorder(BorderFactory.createTitledBorder("Duration"));
		add(endDatePanel, BorderLayout.EAST);
		
		game.addCompanyListener(this);
	}

	/**
	 * 
	 * @return
	 */
	public int getFirstStep(){
		return 33;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLastStep(){
		return 500;
	}
	
	@Override
	public void projectAssigned(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
		
	}
}
