 /*
  * @(#)FixedRangeDemo.java 1/21/2011
  *
  * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
  */

import com.jidesoft.gantt.DefaultGanttModel;
import com.jidesoft.gantt.GanttChart;
import com.jidesoft.gantt.GanttEntry;
import com.jidesoft.plaf.GanttUIDefaultsCustomizer;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.IntegerRange;
import com.jidesoft.range.Range;
import com.jidesoft.scale.*;
import com.jidesoft.swing.JideBoxLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class FixedRangeDemo extends AbstractDemo {

    public FixedRangeDemo() {
    }

    public String getName() {
        return "Gantt Chart Demo (Fixed range)";
    }

    public String getProduct() {
        return PRODUCT_NAME_GANTT_CHART;
    }

    @Override
    public String getDescription() {
        return "\n" + "Demoed classes:\n" + "com.jidesoft.gantt.GanttChart";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 5));

        return panel;
    }

    public Component getDemoPanel() {
        DefaultGanttModel<Integer, GanttEntry<Integer>> model = new DefaultGanttModel<Integer, GanttEntry<Integer>>();

        final NumberPeriod TEN = new NumberPeriod(10);
        final NumberPeriod HUNDRED = new NumberPeriod(100);
        final NumberPeriod TWO_FIFTY = new NumberPeriod(250);
        final NumberPeriod FIVE_HUNDRED = new NumberPeriod(500);
        final NumberPeriod THOUSAND = new NumberPeriod(1000);
        final NumberPeriod FIVE_THOUSAND = new NumberPeriod(5000);
        model.setScaleModel(new NumberScaleModel() {
            @Override
            public List<Period> getPeriods() {
                return Arrays.<Period>asList(TEN, HUNDRED, TWO_FIFTY, FIVE_HUNDRED, THOUSAND, FIVE_THOUSAND);
            }
        });
        model.setRange(new IntegerRange(0, 5000));
        model.setAutoUpdateRange(false);

        ScaleArea<Integer> scaleArea = new ScaleArea<Integer>() {
            @Override
            public void zoomPeriodWidth(double factor) {
                // do nothing
            }
        };
        scaleArea.setPeriodMargin(7);

        DefaultPeriodConverter<Integer> converter = new DefaultPeriodConverter<Integer>() {
            @Override
            public String getText(Integer start, Integer end) {
                if(start < 0 || start >= 5000) {
                    return "";
                }
                else {
                    return start + "-" + end;
                }
            }
        };
        converter.setPrototypeDisplayValue(4999);
        scaleArea.setDefaultPeriodConverter(converter);

        final GanttChart<Integer, GanttEntry<Integer>> chart = new GanttChart<Integer, GanttEntry<Integer>>(model, scaleArea) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        scaleArea.setVisiblePeriods(model.getScaleModel().getPeriods(), true);

        JScrollPane chartScroll = new JScrollPane(chart,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        ComponentAdapter listener = new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                componentResized(e);
            }

            @Override
            public void componentResized(ComponentEvent e) {
                ScaleArea<Integer> scaleArea = chart.getScaleArea();
                Range<Integer> range = chart.getModel().getRange();
                scaleArea.setVisiblePeriodsToFit(
                        range.lower(), range.upper(), chart.getWidth());

                List<Period> all = chart.getModel().getScaleModel().getPeriods();
                chart.getScaleArea().setVisiblePeriods(all.subList(
                        all.indexOf(scaleArea.getSmallestVisiblePeriod()), all.size()));
            }
        };
        chartScroll.addComponentListener(listener);
        return chartScroll;
    }

    public static void main(String[] s) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.addUIDefaultsCustomizer(new GanttUIDefaultsCustomizer());
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new FixedRangeDemo());
            }
        });
    }
}