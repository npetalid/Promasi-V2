/**
 * 
 */
package org.promasi.desktop_swing.application.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
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
 * Represent's the budget widget in ProMaSi system.
 * This widget will draw the company's budget diagram
 * during the simulation process.
 */
public class BudgetWidget extends Widget implements ICompanyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The budget label. Will
	 * present the current state of the 
	 * company's budget.
	 */
	private JLabel _budgetLabel;
	
	/**
	 * Instance of {@link DateTime}
	 * represents the last diagram update date.
	 */
	private DateTime _lastUpdateDate;
	
	/**
	 * Instance of {@link Chart}
	 */
	private Chart _chart;
	
	/**
	 * The name of the current widget.
	 */
	private static final String CONST_CHART_NAME = "Financial Report";
	
	/**
	 * The Y axis key needed in order to draw the chart.
	 */
	private static final String CONST_Y_AXIS_KEY = "budget";
	
	/**
	 * Constructor will initialize the object.
	 * @throws GuiException in case of initialization error.
	 */
	public BudgetWidget() throws GuiException{
		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new BorderLayout());
		
		setLayout(new BorderLayout());
		setVisible(true);
		setOpaque(false);
		setPreferredSize(new Dimension(250,200));
		_budgetLabel = new JLabel("0.0");
		_budgetLabel.setBackground(Colors.White.alpha(0f));
		
		_chart = new Chart(CONST_CHART_NAME, "Date", CONST_Y_AXIS_KEY);
		internalPanel.setOpaque(false);
		internalPanel.setBorder(BorderFactory.createEmptyBorder(Widget.CONST_PANEL_OFFSET, 
																Widget.CONST_PANEL_OFFSET, 
																Widget.CONST_PANEL_OFFSET, 
																Widget.CONST_PANEL_OFFSET));
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
