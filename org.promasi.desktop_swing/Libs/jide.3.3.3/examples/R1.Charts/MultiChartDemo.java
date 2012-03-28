/*
 * @(#)MultiChartDemo.java 8/22/2009
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.axis.*;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Range;
import com.jidesoft.range.TimeRange;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@SuppressWarnings("serial")
public class MultiChartDemo extends AbstractDemo {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
    
    private JPanel demoPanel;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new GridLayout(2, 1, 2, 15));
            demoPanel.add(new VerticalMultiChartPanel());
            demoPanel.add(new HorizontalMultiChartPanel());
            demoPanel.setPreferredSize(new Dimension(700, 500));
        }
        return demoPanel;
    }

    public String getName() {
        return "Multi-Chart Demo";
    }
    
    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    @Override
    public String getDescription() {
       return "You can align charts so that they share the same axes";
    }
    
    private DefaultChartModel createModel(String modelName, Range<?> xRange, double min, double max) {
        DefaultChartModel model = new DefaultChartModel(modelName);
        double start = xRange.minimum();
        double end = xRange.maximum();
        int steps = 50;
        double stepSize = (end - start)/steps;
        double x = start;
        for (int i=0; i <= steps; i++) {
            model.addPoint(x, min + (max-min) * Math.random());
            x += stepSize;
        }
        return model;
    }
    
    /**
     * A MultiChart with three panels placed next to each other horizontally
     */
    class HorizontalMultiChartPanel extends JPanel {
        private Chart chart1 = new Chart();
        private Chart chart2 = new Chart();
        private Chart chart3 = new Chart();
        private TimeRange xRange; 
        private NumericRange yRange = new NumericRange(1.5, 1.7);
        private JPanel chartPanel = new JPanel();
        private JPanel legendPanel = new JPanel();

        public HorizontalMultiChartPanel() {
            setLayout(new BorderLayout());
            try {
                xRange = new TimeRange(dateFormat.parse("01-Jan-2001"),  dateFormat.parse("31-Dec-2001"));
                TimeAxis axis1 = new TimeAxis(xRange, "2001");
                TimeAxis axis2 = new TimeAxis(xRange, "2001");
                TimeAxis axis3 = new TimeAxis(xRange, "2001");
                TimeTickCalculator timeTicks = new AbstractTimeTickCalculator() {
                    @Override
                    public Tick[] calculateTicks(Range<Date> r) {
                        try {
                            Tick jan = new Tick(dateFormat.parse("15-Jan-2001").getTime(), "Jan");
                            Tick apr = new Tick(dateFormat.parse("15-Apr-2001").getTime(), "Apr");
                            Tick jul = new Tick(dateFormat.parse("15-Jul-2001").getTime(), "Jul");
                            Tick oct = new Tick(dateFormat.parse("15-Oct-2001").getTime(), "Oct");
                            return new Tick[] {jan, apr, jul, oct};
                        } catch (ParseException pe) {
                            return new Tick[0];
                        }
                    }
                };
                axis1.setTickCalculator(timeTicks);
                axis2.setTickCalculator(timeTicks);
                axis3.setTickCalculator(timeTicks);
                chart1.setXAxis(axis1);
                chart2.setXAxis(axis2);
                chart3.setXAxis(axis3);
                NumericAxis yAxis = new NumericAxis(yRange, "Exchange Rate");
                yAxis.setNumberFormat("#0.000"); // to 3 decimal places
                AxisComponent yAxisComponent = new AxisComponent(chart1, yAxis);
                chart1.setYAxis(yAxis);
                Axis yAxis2 = new NumericAxis(yRange);
                Axis yAxis3 = new NumericAxis(yRange);
                chart2.setYAxis(yAxis2);
                chart3.setYAxis(yAxis3);
                yAxis.setVisible(false);
                yAxis2.setVisible(false);
                yAxis3.setVisible(false);
                GradientPaint chartBackground = new GradientPaint(0, 0, Color.gray, 0, 300, Color.black);
                ChartStyle lineStyle = new ChartStyle(Color.red).withLines();
                lineStyle.setLineWidth(2);
                chart1.setModel(createModel("Currency 1", xRange, yRange.minimum(), yRange.maximum()), lineStyle);
                chart1.setChartBackground(chartBackground);
                ChartStyle fillStyle = new ChartStyle(Color.blue).withLines();
                fillStyle.setLineFill(new GradientPaint(0, 0, Color.cyan, 0, 300, Color.blue));
                chart2.setModel(createModel("Currency 2", xRange, yRange.minimum(), yRange.maximum()), fillStyle);
                chart2.setChartBackground(chartBackground);
                ChartStyle barStyle = new ChartStyle(Color.yellow).withBars();
                barStyle.setBarWidth(2);
                chart3.setModel(createModel("Currency 3", xRange, yRange.minimum(), yRange.maximum()), barStyle);
                chart3.setChartBackground(chartBackground);
                GridBagLayout layout = new GridBagLayout();
                chartPanel.setLayout(layout);
                chartPanel.add(yAxisComponent, new GridBagConstraints(0, 0, 1, 1, 0, 1.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
                chartPanel.add(chart1, new GridBagConstraints(1, 0, 1, 1, 0.33, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,2,0,2), 0, 0));
                chartPanel.add(chart2, new GridBagConstraints(2, 0, 1, 1, 0.33, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,2,0,2), 0, 0));
                chartPanel.add(chart3, new GridBagConstraints(3, 0, 1, 1, 0.33, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,2,0,2), 0, 0));
                Legend legend = new Legend();
                legend.setColumns(3);
                legend.addChart(chart1);
                legend.addChart(chart2);
                legend.addChart(chart3);
                legendPanel.add(legend);
                add(chartPanel, BorderLayout.CENTER);
                add(legendPanel, BorderLayout.SOUTH);
                
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
    }
    
    class VerticalMultiChartPanel extends JPanel {
        private Paint chartBackground = new GradientPaint(0, 0, Color.gray, 0, 150, Color.black);
        private Chart chart1 = new Chart();
        private Chart chart2 = new Chart();
        private NumericRange xRange = new NumericRange(0, 160); 
        private NumericRange range1 = new NumericRange(0, 20000);
        private NumericRange range2 = new NumericRange(0, 10000);
        private NumericRange range3 = new NumericRange(0, 12000);
        private JPanel chartPanel = new JPanel();
        private JPanel legendPanel = new JPanel();
        private Legend legend = new Legend();

        public VerticalMultiChartPanel() {
            setLayout(new BorderLayout());

            NumericAxis xAxis1 = new NumericAxis(xRange);
            xAxis1.setTicksVisible(true);
            xAxis1.setVisible(false);
            NumericAxis xAxis2 = new NumericAxis(xRange);
            NumericAxis yAxis1 = new NumericAxis(range1, "Axis 1");
            yAxis1.setNumberFormat(NumberFormat.getIntegerInstance());
            // Make sure the widths of the two axes on the left are exactly the same
            int labelWidth = 70;
            yAxis1.setLabelWidth(labelWidth);
            Axis yAxis2 = new NumericAxis(range2, "Axis 2");
            yAxis2.setLabelWidth(labelWidth);
            yAxis2.setPlacement(AxisPlacement.TRAILING);
            Axis yAxis3 = new NumericAxis(range3, "Axis 3");
            yAxis3.setLabelWidth(labelWidth);
            Axis yAxis4 = new NumericAxis(range2, "Axis 4");
            yAxis4.setLabelWidth(labelWidth);
            yAxis4.setPlacement(AxisPlacement.TRAILING);
            yAxis4.setTicksVisible(false);
            yAxis4.setLabelVisible(false);

            yAxis3.setVisible(true);
            ChartStyle fillStyle = new ChartStyle(Color.blue).withLines();
            fillStyle.setLineFill(new GradientPaint(0, 0, Color.cyan, 0, 300, Color.blue));
            ChartStyle barStyle = new ChartStyle(Color.yellow).withBars();
            barStyle.setBarWidth(2);
            
            GridBagLayout gridBagLayout = new GridBagLayout();
            chartPanel.setLayout(gridBagLayout);
            legend.setColumns(4);
            
            legendPanel.add(legend);
            add(chartPanel, BorderLayout.CENTER);
            chart1.setXAxis(xAxis1);
            chart1.setYAxis(yAxis1);
            chart1.addYAxis(yAxis2);
            chart1.setAxisLabelPadding(0);
            DefaultChartModel model1 = createModel("Series 1", xRange, 0, 20000);
            chart1.addModel(model1, new ChartStyle(Color.red).withLines());
            chart1.addModel(createModel("Series 2", xRange, 0, 20000), new ChartStyle(Color.blue).withLines());
            chart1.addModel(createModel("Series 3", xRange, 0, 20000), new ChartStyle(Color.green).withLines());
            chart1.setChartBackground(chartBackground);
            
            chart1.setPreferredSize(new Dimension(200, 200));
            GridBagConstraints gbc_1 = new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0, 0, 5, 5),0,0);
            chartPanel.add(chart1, gbc_1);
            
            legend.addChart(chart1);
            
            chart2.setXAxis(xAxis2);
            chart2.setYAxis(yAxis3);
            chart2.addYAxis(yAxis4);
            chart2.setAxisLabelPadding(0);
            chart2.setModel(createModel("Series 4", xRange, 0, 10000), fillStyle);
            chart2.setChartBackground(chartBackground);
            chart2.setPreferredSize(new Dimension(200, 200));
            
            GridBagConstraints gbc_2 = new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(0, 0, 0, 5),0,20);
            chartPanel.add(chart2, gbc_2);
            legend.addChart(chart2);
            add(legendPanel, BorderLayout.SOUTH);
        }
    }
    
    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new MultiChartDemo());
    }
}
