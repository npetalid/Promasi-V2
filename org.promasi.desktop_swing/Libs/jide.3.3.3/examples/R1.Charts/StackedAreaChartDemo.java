/*
 * @(#)StackedAreaChartDemo.java 06-AUG-2010
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.DefaultAutoRanger;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.SummingChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.utils.TimeUtils;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

@SuppressWarnings("serial")
public class StackedAreaChartDemo extends AbstractDemo {
    private Chart chart;
    private JPanel demoPanel;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            chart = new Chart();
            demoPanel.add(chart, BorderLayout.CENTER);
            demoPanel.setPreferredSize(new Dimension(500, 500));
            try {
                long from = TimeUtils.createTime("01-Jan-1970 00:00:00").getTime();
                long to = TimeUtils.createTime("01-Jan-1980 00:00:00").getTime();
                TimeAxis xAxis = new TimeAxis(from, to);
                NumericAxis yAxis = new NumericAxis(0, 110);
                chart.setXAxis(xAxis);
                chart.setYAxis(yAxis);
                chart.setVerticalGridLinesVisible(false);
                chart.setAutoRanging(true);
                DefaultAutoRanger autoRanger = new DefaultAutoRanger((double) from, 0.0, (double) to, null);
                chart.setAutoRanger(autoRanger);
                DefaultChartModel boys = createModel("Boys", from, to);
                DefaultChartModel girls = createModel("Girls ind.", from, to);
                SummingChartModel total = new SummingChartModel("Girls", boys, girls);
                ChartStyle totalStyle = new ChartStyle(Color.magenta, false, true, false);
                totalStyle.setLineFill(new Color(255, 240, 255, 200));
                chart.addModel(total, totalStyle);
                ChartStyle boyStyle = new ChartStyle(Color.blue, false, true, false);
                boyStyle.setLineFill(new Color(0, 255, 255, 50));
                chart.addModel(boys, boyStyle);
                Font titleFont = UIManager.getFont("Label.font").deriveFont(Font.BOLD, 16f);
                AutoPositionedLabel title = new AutoPositionedLabel("<html><u>Population Variation</u></html>", Color.blue, titleFont);
                title.setRotation(-Math.PI/4);
                chart.setTitle(title);
                Legend legend = new Legend(chart, 1);
                legend.setBackground(UIManager.getColor("Panel.background").brighter());
                legend.setLocation(40, 50);
                chart.addDrawable(legend);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return demoPanel;
    }

    public String getName() {
        return "Stacked Area Chart";
    }

    @Override
    public String getDemoFolder() {
        return "R1.Charts";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    @Override
    public String getDescription() {
        return "The idea with this demo is to show how two independent traces can be summed to give a resulting trace. "+
        " The trace you can see here for boys is an independent trace and there is a similar trace generated for girls "+
        "that is not shown. Instead, we add together the two traces and the magenta line shown is the sum of "+
        "the boys and girls; i.e., the total population. ";
    }

    public DefaultChartModel createModel(String modelName, long from, long to) {
        DefaultChartModel model = new DefaultChartModel(modelName);
        long interval = (to - from) / 10;
        long time = from;
        for (int i = 0; i <= 10; i++) {
            int y = (int) (75 * Math.random());
            model.addPoint(time, y);
            time += interval;
        }
        return model;
    }

    public static void main(String[] args) throws Exception {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new StackedAreaChartDemo());
    }
}

