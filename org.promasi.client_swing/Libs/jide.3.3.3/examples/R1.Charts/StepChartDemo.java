/*
 * @(#)StepChartDemo.java 15-AUG-2010
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.RectangularRegionMarker;
import com.jidesoft.chart.axis.*;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.Highlight;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class StepChartDemo extends AbstractDemo {
    // Defines the width of the gap between the true and false values for a bit trace
    // This value must be between 0 and 0.5
    private static final double binaryOffset = 0.30;
    private final ChartStyle discontinuousStyle = new ChartStyle().withNothing();
    private final ChartStyle continuousStyle = new ChartStyle(Color.black).withLines();
    private final Highlight discontinuity = new Highlight("discontinuity");
    private final double xStart = 0.0;
    private final double xEnd = 4;
    private Chart chart;
    private JPanel demoPanel;
    private JPanel optionPanel;

    private CategoryRange<BinaryTrace> yRange = new CategoryRange<BinaryTrace>();

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            JLabel titleLabel = new JLabel("Step Charts", JLabel.CENTER);
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
            titleLabel.setBorder(new EmptyBorder(20,20,20,20));
            demoPanel.add(titleLabel, BorderLayout.NORTH);
            demoPanel.setPreferredSize(new Dimension(600, 500));
            
            chart = new Chart();
            chart.setAnimateOnShow(false);
            chart.setAntiAliasing(false);

            String[] categories = new String[] {"A", "NOT A", "B", "NOT B", "A OR B", "A AND B", "A XOR B"};
            BinaryTrace[] traces = new BinaryTrace[] {
                    new BinaryTrace(false, 2), 
                    new BinaryTrace(true, 2),
                    new BinaryTrace(false, 1, 2, 3), 
                    new BinaryTrace(true, 1, 2, 3),
                    new BinaryTrace(false, 1),
                    new BinaryTrace(false, 3),
                    new BinaryTrace(false, 1, 3)
            };
            for (int i = categories.length - 1; i >= 0; i--) {
                String categoryString = categories[i];
                BinaryTrace bt = i < traces.length ? traces[i] : new BinaryTrace(false);
                Category<BinaryTrace> category = new Category<BinaryTrace>(categoryString, bt);
                yRange.add(category);
            }
            CategoryAxis<BinaryTrace> yAxis = new CategoryAxis<BinaryTrace>(yRange);
            // Paint some 'tram lines' for each of the categories
            for (Category<BinaryTrace> category : yRange.getCategoryValues()) {
                double pos = category.position();
                RectangularRegionMarker marker = new RectangularRegionMarker(chart, xStart, xEnd, pos - binaryOffset, pos + binaryOffset, new Color(200, 200, 255, 75));
                marker.setOutlineColor(new Color(140, 140, 140, 75));
                chart.addDrawable(marker);
            }
            NumericAxis xAxis = new NumericAxis(xStart, xEnd);
            xAxis.setTicksVisible(false);
            chart.setXAxis(xAxis);
            chart.setYAxis(yAxis);
            chart.setHorizontalGridLinesVisible(false);
            chart.setVerticalGridLinesVisible(false);
            chart.setGridColor(new Color(220, 220, 220));
            for (Category<BinaryTrace> c : yRange.getCategoryValues()) {
                BinaryTrace b = c.getValue();
                ChartModel model = b.getModel(c);
                ChartStyle style = new ChartStyle(Color.black).withLines();
                style.setLineWidth(2);
                chart.addModel(model, style);
            }

            demoPanel.add(chart, BorderLayout.CENTER);
        }
        return demoPanel;
    }
    
    @Override
    public Component getOptionsPanel() {
        if (optionPanel == null) {
            optionPanel = new JPanel();
            optionPanel.setLayout(new BoxLayout(optionPanel,BoxLayout.Y_AXIS));
            final JRadioButton withoutDiscontinuitiesButton = new JRadioButton("Without Discontinuities");
            final JRadioButton withDiscontinuitiesButton = new JRadioButton("With Discontinuities");
            withoutDiscontinuitiesButton.setSelected(true);
            ButtonGroup group = new ButtonGroup();
            group.add(withoutDiscontinuitiesButton);
            group.add(withDiscontinuitiesButton);
            optionPanel.add(withoutDiscontinuitiesButton);
            optionPanel.add(withDiscontinuitiesButton);
            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ChartStyle continuityStyle = withDiscontinuitiesButton.isSelected() ? discontinuousStyle : continuousStyle;
                    chart.setHighlightStyle(discontinuity, continuityStyle);
                }
            };
            withoutDiscontinuitiesButton.addActionListener(listener);
            withDiscontinuitiesButton.addActionListener(listener);
        }
        return optionPanel;
    }

    class BinaryTrace {
        private double[] transitions;
        private boolean initialState;

        public BinaryTrace(boolean initialState, double... transitions) {
            this.initialState = initialState;
            this.transitions = transitions;
        }

        public ChartModel getModel(Category<?> category) {
            DefaultChartModel model = new DefaultChartModel();
            boolean state = initialState;
            model.addPoint(xStart, getY(category, state));
            for (double transition : transitions) {
                ChartPoint p1 = new ChartPoint(transition, getY(category, state));
                p1.setHighlight(discontinuity);
                model.addPoint(p1);
                state = !state; // toggle the state
                ChartPoint p2 = new ChartPoint(transition, getY(category, state));
                model.addPoint(p2);
            }
            model.addPoint(xEnd, getY(category, state));
            return model;
        }

        private double getY(Category<?> category, boolean state) {
            return state ? category.position() + binaryOffset : category.position() - binaryOffset;
        }
    }
    
    public String getName() {
        return "Step Chart Demo";
    }
    
    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }
    
    @Override
    public String getDescription() {
        return "This demo shows how the chart component can be used to display step charts."+
        "It also shows how to mark discontinuities in a trace without having to add the discontinuous segments as"+
        " separate chart models.";
    }

    public static void main(String[] args) throws Exception {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new StepChartDemo());
    }
}
