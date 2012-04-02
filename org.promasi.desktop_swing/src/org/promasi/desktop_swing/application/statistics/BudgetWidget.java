/**
 * 
 */
package org.promasi.desktop_swing.application.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.joda.time.DateTime;
import org.promasi.desktop_swing.Widget;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utils_swing.Colors;

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
	private JFreeChart _chart;
	
	/**
	 * 
	 */
	public BudgetWidget(){
		setLayout(new BorderLayout());
		setVisible(true);
		setPreferredSize(new Dimension(200,100));
		_budgetLabel = new JLabel("0.0");
		_budgetLabel.setBackground(Colors.White.alpha(0f));
		
		DefaultKeyedValues2DDataset dataSet = new  DefaultKeyedValues2DDataset();
		_chart = ChartFactory.createLineChart("Budget", "Bydget", "Date", dataSet, PlotOrientation.HORIZONTAL, false, false, false);
		
		add(_budgetLabel, BorderLayout.SOUTH);
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
	public void onExecuteWorkingStep(String owner, final CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_budgetLabel.setText( new String("Budget : $") + company.getBudget() );
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
	}

}
