/*
 * @(#)TransparentChartDemo.java 4/3/2011
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.annotation.ChartLabel;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.DefaultBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.style.StripePaint;
import com.jidesoft.chart.util.ChartUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CategoryRange;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TransparentChartDemo extends AbstractDemo {
    private Font font = new Font("Tahoma", Font.PLAIN, 11);
    private JPanel demoPanel;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new FlowLayout());
            Dimension size = new Dimension(380, 295);
            demoPanel.setSize(size);
            demoPanel.setMinimumSize(size);
            demoPanel.setPreferredSize(size);
            demoPanel.setMaximumSize(size);
            Chart chart = new Chart();
            chart.setOpaque(true);
            chart.setSize(size);
            chart.setPreferredSize(size);

            Paint paint = ChartUtils.createTexture(chart, "slate.png");
            chart.setChartBackground(new Color(0, 0, 0, 0));
            chart.setBorder(new EmptyBorder(50, 40, 40, 40));
            chart.setPanelBackground(paint);
            ChartCategory<String> home = new ChartCategory<String>("Lunch at Home");
            ChartCategory<String> packed = new ChartCategory<String>("Packed Lunch");
            ChartCategory<String> school = new ChartCategory<String>("School Lunch");
            CategoryRange<String> lunches = new CategoryRange<String>().add(school).add(packed).add(home);
            CategoryAxis<String> xAxis = new CategoryAxis<String>(lunches);
            chart.setXAxis(xAxis);
            lunches.setMinimum(0.5);
            lunches.setMaximum(3.5);
            NumericAxis yAxis = new NumericAxis(0, 80);
            chart.setYAxis(yAxis);
            chart.setMinorTickLength(0);
            chart.setTickColor(Color.white);
            chart.setTickFont(font);
            chart.setLabelColor(Color.white);
            xAxis.setAxisColor(Color.white);
            yAxis.setAxisColor(Color.white);
            chart.setHorizontalGridLinesVisible(false);
            chart.setVerticalGridLinesVisible(false);
            DefaultChartModel model = new DefaultChartModel();
            model.addPoint(home, 5);
            model.addPoint(packed, 65);
            model.addPoint(school, 30);
            ChartStyle style = new ChartStyle().withBars();
            StripePaint barFill = new StripePaint(-45, 20, 2f);
            barFill.setBackground(new Color(0, 0, 0, 0));
            barFill.setForeground(Color.white);
            style.setBarPaint(barFill);
            chart.addModel(model, style);
            style.setBarWidth(50);
            DefaultBarRenderer barRenderer = new DefaultBarRenderer();
            chart.setBarRenderer(barRenderer);
            barRenderer.setOutlineColor(Color.white);
            barRenderer.setOutlineWidth(2f);
            barRenderer.setAlwaysShowOutlines(true);
            demoPanel.add(chart, BorderLayout.CENTER);
            ChartLabel title = new ChartLabel(2, 73, "Popularity of Different School Lunches");
            title.setFont(font.deriveFont(12f)); // Make the title font slightly larger
            model.addAnnotation(title);
        }
        return demoPanel;
    }

    public String getName() {
        return "Transparent Chart";
    }
    
    @Override
    public String getDescription() {
        return "This demo shows that it is possible to create a chart with a transparent background that shows through to an image underneath";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new TransparentChartDemo());
    }
}
