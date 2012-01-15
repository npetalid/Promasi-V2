/*
 * @(#)CompanySuesDemo.java 8/3/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.model.ChartModelListener;
import com.jidesoft.chart.model.TableToChartAdapter;
import com.jidesoft.chart.render.Bar3DRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Range;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * A demo that shows how a chart can be created dynamically based on selections from a table
 */
@SuppressWarnings("serial")
public class CompanySuesDemo extends AbstractDemo {
    private JPanel demoPanel, optionsPanel;
    protected Axis _xAxis;
    protected Axis _yAxis;
    protected TableToChartAdapter _adapter1;
    protected TableToChartAdapter _adapter2;
    protected Chart _chart;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout(10, 10));
            demoPanel.setOpaque(true);
            demoPanel.setBackground(Color.WHITE);
            _chart = new Chart();
            _chart.setOpaque(false);
            _chart.setShadowVisible(true);
            _chart.setChartBackground(new GradientPaint(0f, 0f, Color.white, 0f, 500f, new Color(180, 180, 250)));
            _chart.setPreferredSize(new Dimension(800, 300));
            demoPanel.add(_chart, BorderLayout.CENTER);
            String[] columns = {"company", "sues", "sued"};
            Object[][] data = new Object[][]{
                    {"apple", 976000, 505000},
                    {"google", 37300, 424000},
                    {"oracle", 210000, 248000},
                    {"microsoft", 383000, 145000},
                    {"samsung", 202000, 1050000},
                    {"motorola", 72500, 246000},
                    {"nokia", 143000, 52700},
                    {"hp", 256000, 11800},
                    {"sun", 9630, 11300},
                    {"ibm", 24400, 40100},
                    {"emc", 1680, 924},
                    {"ge", 10400, 3760}

            };
            TableModel tableModel = new DefaultTableModel(data, columns);
            SortableTable table = new SortableTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(400, 120));
            demoPanel.add(scrollPane, BorderLayout.SOUTH);
            _xAxis = new NumericAxis();
            _yAxis = new NumericAxis();
            _chart.setXAxis(_xAxis);
            _chart.setYAxis(_yAxis);
            _chart.setBarRenderer(new Bar3DRenderer());
            _chart.setBarGap(5);
            _chart.setBarsGrouped(true);
            _chart.setBarGroupGap(10);

            final ChartStyle style1 = new ChartStyle(new Color(79, 129, 190));
            style1.setPointsVisible(false);
            style1.setBarsVisible(true);
            style1.setLinesVisible(false);
            style1.setBarWidth(15);

            final ChartStyle style2 = new ChartStyle(new Color(193, 80, 77));
            style2.setPointsVisible(false);
            style2.setLinesVisible(false);
            style2.setBarsVisible(true);
            style2.setBarWidth(15);

            _adapter1 = new TableToChartAdapter("sues", table.getModel());
            _adapter1.setXColumn(0);
            _adapter1.setYColumn(1);

            _adapter2 = new TableToChartAdapter("sued", table.getModel());
            _adapter2.setXColumn(0);
            _adapter2.setYColumn(2);

            _chart.addModel(_adapter1, style1);
            _chart.addModel(_adapter2, style2);

            _adapter1.addChartModelListener(new ChartModelListener() {
                public void chartModelChanged() {
                    updateXRange();
                    updateYRange();
                }
            });

            updateXRange();
            updateYRange();

            Legend legend = new Legend(_chart);
            legend.setKeyLabelGap(6);
            legend.setOpaque(false);
            legend.setBorder(BorderFactory.createEmptyBorder());
            demoPanel.add(legend, BorderLayout.AFTER_LINE_ENDS);

            _chart.startAnimation();
        }
        return demoPanel;
    }

    /**
     * Update the x axis with the range from the table model adapter
     */
    @SuppressWarnings("unchecked")
    private void updateXRange() {
        CategoryRange<?> xRange = (CategoryRange) _adapter1.getXRange();
        _xAxis = new CategoryAxis(xRange, "companies");
        _chart.setXAxis(_xAxis);
    }

    /**
     * Update the y axis with the range from the table model adapter
     */
    @SuppressWarnings("unchecked")
    private void updateYRange() {
        Range<?> yRange1 = _adapter1.getYRange();
        Range<?> yRange2 = _adapter2.getYRange();
        NumericRange nRange = NumericRange.union((NumericRange) yRange1, (NumericRange) yRange2);
        if (nRange.getMin() == nRange.getMax()) {
            // Deal with the special case of only one point
            _yAxis = new NumericAxis(nRange.getMin(), nRange.getMax() + 1, "search engine hits");
        }
        else {
            _yAxis = new NumericAxis(nRange.stretch(1.0, 1.2), "search engine hits");
        }
        _chart.setYAxis(_yAxis);
    }

    public String getName() {
        return "Company Sues Demo";
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    @Override
    public String getDescription() {
        return "This is a demo of bar chart with bar grouping feature and the TableToChartAdapter. It shows the number of Google search hits for " +
                "keywords such as \"COMPANY sues\" and \"sues COMPANY\" where the COMPANY is replaced with the actual company name as listed in the table. " +
                "From the chart, you can see a comparison of a company sues someone v.s. a company being sued. Try to click on the table header below to " +
                "sort the table and notice the chart will change with it.\n" +
                "\nThe data in the table was taken on Google search engine on August 3, 2011." +
                "\n\n" +
                "Demoed classes:\n" +
                "com.jidesoft.chart.Chart\n" +
                "com.jidesoft.chart.ChartModel\n" +
                "com.jidesoft.chart.TableToChartAdapter";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new CompanySuesDemo());
    }

}
