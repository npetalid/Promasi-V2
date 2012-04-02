/**
 * 
 */
package org.promasi.desktop_swing.application.statistics;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
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
	private DateTime _lastUpdateDate;
	
	/**
	 * 
	 */
	private JFreeChart _chart;
	
	private static final String CONST_CHART_NAME = "Financial Report";
	
	/**
	 * 
	 */
	private static final String CONST_Y_AXIS_KEY = "budget";
	
	/**
	 * 
	 */
	private DefaultCategoryDataset _dataset;
	
	/**
	 * 
	 */
	public BudgetWidget(){
		setLayout(new BorderLayout());
		setVisible(true);
		setPreferredSize(new Dimension(250,200));
		_budgetLabel = new JLabel("0.0");
		_budgetLabel.setBackground(Colors.White.alpha(0f));
		
		_dataset = new DefaultCategoryDataset();
		_chart = createChart(_dataset);
		
		//dataSet.
		add(new ChartPanel(_chart), BorderLayout.CENTER);
		add(_budgetLabel, BorderLayout.SOUTH);
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
				_budgetLabel.setText( new String("Budget : ") + company.getBudget() + "$" );
				if( _lastUpdateDate != null && _lastUpdateDate.plusWeeks(1).isBefore(dateTime)){
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(dateTime.toDate());
					SimpleDateFormat dateFromat = new SimpleDateFormat("dd MMMMM yyyy");
					_dataset.addValue(company.getBudget(), "Budget", dateFromat.format(calendar.getTime()) );
					_lastUpdateDate = dateTime;
				}	
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
	}
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
        	CONST_CHART_NAME,          // chart title
            "Date",                    // domain axis label
            CONST_Y_AXIS_KEY,          // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
    }
}
