/**
 * 
 */
package org.promasi.desktop_swing.application.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.desktop_swing.Chart;
import org.promasi.desktop_swing.Widget;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class BudgetWidget extends Widget implements ICompanyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JLabel _budgetLabel;
	
	/**
	 * 
	 */
	private DateTime _lastUpdateDate;
	
	/**
	 * 
	 */
	private Chart _chart;
	
	/**
	 * 
	 */
	private static final String CONST_CHART_NAME = "Financial Report";
	
	/**
	 * 
	 */
	private static final String CONST_Y_AXIS_KEY = "budget";
	
	/**
	 * @throws GuiException 
	 * 
	 */
	public BudgetWidget() throws GuiException{
		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new BorderLayout());
		
		setLayout(new BorderLayout());
		setVisible(true);
		setPreferredSize(new Dimension(250,200));
		_budgetLabel = new JLabel("0.0");
		setOpaque(false);
		_budgetLabel.setBackground(Colors.White.alpha(0f));
		
		_chart = new Chart(CONST_CHART_NAME, "Date", CONST_Y_AXIS_KEY);
		internalPanel.add(_chart, BorderLayout.CENTER);
		internalPanel.add(_budgetLabel, BorderLayout.SOUTH);
		
		add(internalPanel);
	}

	@Override
	public void projectAssigned(String owner, CompanyMemento company,
			ProjectMemento project, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_lastUpdateDate = dateTime;
			}
		});
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
	public void onExecuteWorkingStep(String owner, final CompanyMemento company,
			ProjectMemento assignedProject, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_budgetLabel.setText( new String(String.format("Budget : %.2f$", company.getBudget())));
				if( _lastUpdateDate != null && _lastUpdateDate.plusDays(1).isBefore(dateTime)){
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(dateTime.toDate());
					SimpleDateFormat dateFromat = new SimpleDateFormat("dd MMMMM yyyy");
					_chart.addValue(dateFromat.format(calendar.getTime()) , company.getBudget());
					_lastUpdateDate = dateTime;
				}	
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
	}
}
