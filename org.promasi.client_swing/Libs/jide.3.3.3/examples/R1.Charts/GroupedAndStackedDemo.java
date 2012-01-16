/*
 * @(#)GroupedAndStackedDemo.java
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.ZeroAlignedAutoRanger;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.event.PointDescriptor;
import com.jidesoft.chart.model.*;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.Positionable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GroupedAndStackedDemo extends AbstractDemo {
    private ChartCategory<String> catA = new ChartCategory<String>("A");
    private ChartCategory<String> catB = new ChartCategory<String>("B");
    private CategoryRange<String> range = new CategoryRange<String>().add(catA).add(catB);
    private CategoryAxis<String> xAxis = new CategoryAxis<String>(range);
    private JPanel demoPanel;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            Dimension size = new Dimension(500, 400);
            demoPanel.setPreferredSize(size);
            demoPanel.setMinimumSize(new Dimension(400, 300));
            final Chart chart = new Chart();
            chart.setTitle(new AutoPositionedLabel("Grouped & Stacked Bars", Color.black, UIManager.getFont("Label.font").deriveFont(16f)));
            chart.setBarsGrouped(true);
            chart.setAutoRanging(true);
            ZeroAlignedAutoRanger autoRanger = new ZeroAlignedAutoRanger();
            autoRanger.setLeadingYMarginProportion(0);
            autoRanger.setAlwaysIncludeZeros(true);
            chart.setAutoRanger(autoRanger);
            chart.setBarGroupGapProportion(0.2);
            chart.setBorder(new EmptyBorder(20, 20, 20, 20));
            //chart.setAnimateOnShow(false);
            RaisedBarRenderer renderer = new RaisedBarRenderer(10);
            renderer.setSelectionColor(Color.black);
            renderer.setOutlineWidth(5f);
            chart.setBarRenderer(renderer);
            //chart.setBarRenderer(new CylinderBarRenderer());
            //chart.setBarRenderer(new Bar3DRenderer());
            NumericAxis yAxis = new NumericAxis(0, 180);
            range.setMinimum(0.5);
            range.setMaximum(2.5);
            chart.setXAxis(xAxis);
            chart.setYAxis(yAxis);
            demoPanel.add(chart, BorderLayout.CENTER);

            // Use some chart points with some extra information; in this case just a string
            SpecialPoint o1 = new SpecialPoint(catA, 30, "First Point");
            SpecialPoint o2 = new SpecialPoint(catB, 50, "Second Point");
            DefaultChartModel model1 = new DefaultChartModel("Orange").addPoint(o1).addPoint(o2);
            DefaultChartModel model2 = new DefaultChartModel("Green").addPoint(catA, 20).addPoint(catB, 30);
            DefaultChartModel model3 = new DefaultChartModel("Red").addPoint(catA, 40).addPoint(catB, 70);

            SummingChartModel sumModel = new SummingChartModel("Sum", model1, model2);

            ChartStyle sumStyle = new ChartStyle(Color.cyan).withBars();
            sumStyle.setBarWidthProportion(0.7);
            chart.addModel(sumModel, sumStyle);
            ChartStyle style = new ChartStyle(Color.red).withBars();
            style.setBarWidthProportion(0.7);
            chart.addModel(model3, style);

            ChartStyle model1Style = new ChartStyle(Color.orange).withBars();
            ChartStyle model2Style = new ChartStyle(Color.green.darker()).withBars();
            chart.setStyle(model1, model1Style);
            chart.setStyle(model2, model2Style);

            JPanel panel = new JPanel();
            Legend legend = new Legend(chart, 0);
            panel.add(legend);
            legend.addIncludedModel(model1);
            legend.addIncludedModel(model2);
            legend.addExcludedModels(sumModel);
            demoPanel.add(panel, BorderLayout.SOUTH);

            chart.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (Chart.PROPERTY_CURRENT_CHART_POINT.equals(evt.getPropertyName())) {
                        Chartable chartable = chart.getCurrentChartPoint();
                        if (chartable == null) {
                            chart.setToolTipText(null);
                        } else {
                            Point screenLocation = MouseInfo.getPointerInfo().getLocation();
                            Point p = new Point(screenLocation);
                            SwingUtilities.convertPointFromScreen(p, chart);
                            PointDescriptor descriptor = chart.containingBar(p);
                            chartable = descriptor.getChartable();
                            ChartModel model = descriptor.getModel();
                            PointDescriptor subDescriptor = descriptor.getSubPoint();
                            if (subDescriptor != null) {
                                chartable = subDescriptor.getChartable();
                                model = subDescriptor.getModel();
                            }
                            String toolTipText;
                            if (chartable instanceof SpecialPoint) {
                                // Extract the additional information and display it in the tool tip
                                SpecialPoint sp = (SpecialPoint) chartable;
                                toolTipText = String.format("%s %.2f %s", model.getName(), sp.getY().position(), sp.getSpecialString());
                            } else {
                                toolTipText = String.format("%s: %.2f", model.getName(), chartable.getY().position());
                            }
                            chart.setToolTipText(toolTipText);
                        }
                    }
                }
            });
        }
        return demoPanel;
    }

    public String getName() {
        return "Grouped & Stacked Bars";
    }

    @Override
    public String getDescription() {
        return "This demo shows that it is possible to display groups or categories of information and then for each"+
                " group, display a stacked set of bars. If you move the mouse over the bars it also shows you tool tips"+
                " corresponding to the data under the mouse";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    /**
     * This class shows how you can create custom classes of your own that carry information -
     * and even with a grouped and stacked bar chart we are able to extract the information
     * and display it in a tool tip
     */
    class SpecialPoint extends ChartPoint {
        private String specialString;

        public SpecialPoint(Positionable x, double y, String specialString) {
            super(x, y);
            this.specialString = specialString;
        }

        public String getSpecialString() {
            return specialString;
        }
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new GroupedAndStackedDemo());
    }
}
