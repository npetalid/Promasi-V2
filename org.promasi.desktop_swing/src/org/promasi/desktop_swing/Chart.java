package org.promasi.desktop_swing;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.promasi.utils_swing.GuiException;

public class Chart extends JXPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JFreeChart _chart;
	
	/**
	 * 
	 */
	private DefaultCategoryDataset _dataset;
	
	public static final int CONST_MAX_COLUMS_NUMBER = 20;
	
	/**
	 * 
	 */
	private String _xKey;
	
	/**
	 * 
	 */
	private String _yKey;
	
	/**
	 * 
	 * @param chartName
	 * @param xKey
	 * @param yKey
	 * @throws GuiException
	 */
	public Chart(String chartName, String xKey, String yKey) throws GuiException{
		if( chartName == null ){
			throw new GuiException("Wrong argument chartName == null");
		}
		
		if( xKey == null ){
			throw new GuiException("Wrong argument xKey == null");
		}
		
		if( yKey == null ){
			throw new GuiException("Wrong argument yKey == null");
		}
		
		_xKey = xKey;
		_yKey = yKey;
		_dataset = new DefaultCategoryDataset();
		_chart = ChartFactory.createLineChart(
	        	chartName,
	        	_xKey,
	            _yKey,
	            _dataset,                   
	            PlotOrientation.VERTICAL, 
	            true,                      
	            true,                     
	            false                      
	        );

        _chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) _chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//	        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        
        setLayout(new BorderLayout());
        add(new ChartPanel(_chart));
	}
	
	/**
	 * 
	 * @param xValue
	 * @param value
	 * @return
	 */
	public boolean addValue( String xValue, double value ){
		boolean result = false;
		
		if( xValue != null ){
			_dataset.addValue(value, _yKey, xValue );
			
			if( _dataset.getColumnKeys().size() > CONST_MAX_COLUMS_NUMBER){
				_dataset.removeColumn(0);
			}
			
			result = true;
		}
		
		return result;
	}

}
