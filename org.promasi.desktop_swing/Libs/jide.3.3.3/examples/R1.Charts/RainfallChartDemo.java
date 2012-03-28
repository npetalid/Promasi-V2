/*
 * @(#)RainfallChartDemo.java 8/22/2009
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.DefaultTimeTickCalculator;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.fit.Polynomial;
import com.jidesoft.chart.fit.QuadraticFitter;
import com.jidesoft.chart.model.AnnotatedChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.SelectableChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.TimeRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import static java.awt.GridBagConstraints.*;

/**
 * An example that shows some historical data for the temperatures in Cambridge, UK
 */
@SuppressWarnings("serial")
public class RainfallChartDemo extends AbstractDemo {
    private static final Logger logger = Logger.getLogger(RainfallChartDemo.class.getName());
    private JPanel demoPanel;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
    private static QuadraticFitter fitter = QuadraticFitter.getInstance();
    private SelectableChartModel[] selectableModels = new SelectableChartModel[12];
    private static WeatherData weatherData;
    private JToggleButton button = new JToggleButton("Show Trends");
    // See www.metoffice.gov.uk
    private JLabel acknowledgement = new JLabel("<html><small>Data courtesy of The UK Meteorological Office</small></html>", JLabel.RIGHT);
    private Chart[] charts = new Chart[12];
    private JPanel chartPanel = new JPanel();
    private TimeRange xRange;

    public RainfallChartDemo() {

    }

    public JPanel createDemo() {
        weatherData = WeatherData.getInstance();
        demoPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        demoPanel.setLayout(layout);
        chartPanel.setLayout(new GridLayout(4, 3));
        try {
            Date from = dateFormat.parse("01-Jan-1959");
            Date to = dateFormat.parse("31-Dec-2006");
            xRange = new TimeRange(from, to);

            JLabel titleLabel = new JLabel("Cambridge Rainfall Charts, 1959-2006", JLabel.CENTER);
            Font labelFont = UIManager.getFont("Label.font");
            Font axisLabelFont = labelFont.deriveFont(10f);
            Font titleFont = labelFont.deriveFont(Font.BOLD, 14f);
            Font tickFont = labelFont.deriveFont(9f);
            titleLabel.setForeground(Color.blue);
            titleLabel.setFont(titleFont);
            demoPanel.add(titleLabel, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 2, 0, 2), 0, 0));
            demoPanel.add(chartPanel, new GridBagConstraints(1, 2, 2, 1, 1.0, 1.0, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0));
            demoPanel.add(button, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, WEST, NONE, new Insets(0, 0, 0, 0), 0, 0));
            demoPanel.add(acknowledgement, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, EAST, NONE, new Insets(0, 0, 0, 10), 0, 0));
            int m = 0;
            AnnotatedChartModel[] trends = new AnnotatedChartModel[12];
            for (Month month : Month.values()) {
                Axis xAxis = new TimeAxis(xRange);
                DefaultTimeTickCalculator calc = new DefaultTimeTickCalculator();
                calc.setDateFormat(new SimpleDateFormat("yy"));
                xAxis.setTickCalculator(calc);
                xAxis.setLabel(new AutoPositionedLabel("Year", Color.black, axisLabelFont));
                xAxis.setLabelWidth(22);
                Axis yAxis = new Axis(new NumericRange(0, 170));
                yAxis.setLabel(new AutoPositionedLabel("Rainfall (mm)", Color.black, axisLabelFont));
                charts[m] = new Chart("Rainfall " + month);
                charts[m].setAxisLabelPadding(0);
                charts[m].setXAxis(xAxis);
                charts[m].setYAxis(yAxis);
                charts[m].setVerticalGridLinesVisible(false);
                charts[m].setGridColor(new Color(220,220,220));
                charts[m].setTickFont(tickFont);
                charts[m].setTitle(new AutoPositionedLabel(month.toString(), Color.blue));
                DefaultChartModel model = weatherData.getRainfallModel(month);
                Polynomial curve = fitter.performRegression(model);
                trends[m] = fitter.createModel(model.getName(), curve, model.getXRange(), 10);
                SelectableChartModel selectableModel = new SelectableChartModel(model, trends[m]);
                selectableModels[m] = selectableModel;
                ChartStyle style = new ChartStyle(month.getColor(), false, false);
                style.setBarsVisible(true);
                style.setBarColor(month.getColor());
                charts[m].addModel(selectableModels[m], style);
                charts[m].addMouseZoomer(true, false).addMousePanner(true, false);
                chartPanel.add(charts[m]);
                m++;
            }
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int modelIndex = button.isSelected() ? 1 : 0;
                    if (selectableModels != null) {
                        for (SelectableChartModel pm : selectableModels) {
                            pm.setModelIndex(modelIndex);
                        }
                    }
                }
            });

            demoPanel.setPreferredSize(new Dimension(600, 600));
        }
        catch (Exception e) {
            logger.severe("Exception while creating chart: " + e.getMessage());
            e.printStackTrace();
        }
        return demoPanel;
    }

    @Override
    public String getDescription() {
        return "This is a chart of some historical temperature data collected in Cambridge UK. " +
                "It shows the rainfall in each of the calendar months for years ranging from 1959 to 2006." +
                "You can see the trends in the data by clicking on the 'Show Trends' button." +
                "You can zoom in and out of the individual graphs by using the mouse wheel, and pan by clicking and dragging.";
    }

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = createDemo();
        }
        return demoPanel;
    }

    public String getName() {
        return "Cambridge Rainfall Charts";
    }

    @Override
    public String getDemoFolder() {
        return "R1.Charts";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new RainfallChartDemo());
    }
}
