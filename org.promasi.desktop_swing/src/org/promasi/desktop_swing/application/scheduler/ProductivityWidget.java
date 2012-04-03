/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
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
 * This widget represent the 
 * company's productivity.
 */
public class ProductivityWidget extends Widget implements ICompanyListener{

	/**
	 * 
	 */
	private Chart _chart;
	
	/**
	 * Widget name. This widget represent the 
	 * company's productivity.
	 */
	public static final String CONST_CHART_NAME = "Productivity Report";
	
	/**
	 * The X axis name.
	 */
	public static final String CONST_X_KEY = "Date";
	
	/**
	 * The Y axis name.
	 */
	public static final String CONST_Y_KEY = "Productivity";
	
	/**
	 * 
	 */
	private double _lastProjectProgressState;
	
	/**
	 * 
	 */
	private DateTime _lastUpdate;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor will initialize the object.
	 * @throws GuiException
	 */
	public ProductivityWidget() throws GuiException{
		setLayout(new BorderLayout());
		add(new JLabel("TEST"));
		setOpaque(false);
		setBackground(Colors.White.alpha(0f));
		
		_chart = new Chart(CONST_CHART_NAME, CONST_X_KEY, CONST_Y_KEY);
		add(_chart);
	}

	@Override
	public void projectAssigned(String owner, CompanyMemento company,
			final ProjectMemento project, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_lastProjectProgressState = project.getOverallProgress();
				_lastUpdate = dateTime;
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
	public void onExecuteWorkingStep(String owner, CompanyMemento company,
			final ProjectMemento assignedProject, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if( _lastUpdate.plusDays(1).isBefore(dateTime)){
					double progress = assignedProject.getOverallProgress();
					double difference = progress - _lastProjectProgressState;
					_lastProjectProgressState = progress;
					
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(dateTime.toDate());
					SimpleDateFormat dateFromat = new SimpleDateFormat("dd MMMMM yyyy");
					_chart.addValue(dateFromat.format(calendar.getTime()) , difference);
					_lastUpdate = dateTime;
				}
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
		
	}
}
