/*
 * @(#)DifferenceChartDemo.java 8/05/2010
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.DifferenceMarker;
import com.jidesoft.chart.ShadowVisibility;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.DefaultLineRenderer;
import com.jidesoft.chart.render.SmoothLineRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.util.ChartUtils;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

@SuppressWarnings("serial")
public class DifferenceChartDemo extends AbstractDemo {
    private Chart chart;
    private NumericAxis xAxis, yAxis;
    private JPanel demoPanel;
    private JPanel optionsPanel;
    private DifferenceMarker differenceMarker;
    private Paint redTexture, blueTexture;
    private SmoothLineRenderer smoothLineRenderer;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            demoPanel.setPreferredSize(new Dimension(600, 500));
            chart = new Chart();
            smoothLineRenderer = new SmoothLineRenderer(chart);
            differenceMarker = new DifferenceMarker(chart);
            differenceMarker.setModel1DominantFill(Color.pink);
            differenceMarker.setModel2DominantFill(Color.cyan);
            demoPanel.add(chart, BorderLayout.CENTER);
            xAxis = new NumericAxis(0, 100);
            yAxis = new NumericAxis(0, 100);
            chart.setXAxis(xAxis);
            chart.setYAxis(yAxis);
            chart.addMousePanner().addMouseZoomer();
            chart.setBorder(new EmptyBorder(20,20,0,20));
            updateModels();
            JLabel titleLabel = new JLabel("Difference Chart", JLabel.CENTER);
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
            demoPanel.add(titleLabel, BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            JButton button = new JButton("Regenerate");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    updateModels();
                    chart.startAnimation();
                }
            });
            buttonPanel.add(button);
            demoPanel.add(buttonPanel, BorderLayout.SOUTH);
        }
        return demoPanel;
    }

    private void updateModels() {
        chart.removeModels();
        chart.removeDrawables();
        ChartModel model1 = createModel();
        ChartModel model2 = createModel();
        ChartStyle style1 = new ChartStyle(Color.red, true, true);
        ChartStyle style2 = new ChartStyle(Color.blue, true, true);
        chart.addModel(model1, style1);
        chart.addModel(model2, style2);
        differenceMarker.setModel1(model1);
        differenceMarker.setModel2(model2);
        chart.addDrawable(differenceMarker);
    }

    private ChartModel createModel() {
        DefaultChartModel model = new DefaultChartModel();
        for (int i=0; i<=10; i++) {
            double y = 5+Math.random()*90;
            model.addPoint(i*10, y);
        }
        return model;
    }

    @Override
    public Component getOptionsPanel() {
        if (optionsPanel == null) {
            optionsPanel = new JPanel();
            BoxLayout optionsLayout = new BoxLayout(optionsPanel, BoxLayout.Y_AXIS);
            optionsPanel.setLayout(optionsLayout);
            JPanel fillPanel = new JPanel(new GridLayout(3, 1));
            fillPanel.setBorder(new TitledBorder("Fill"));
            ButtonGroup fillButtonGroup = new ButtonGroup();
            JRadioButton translucent = new JRadioButton("Translucent");
            JRadioButton opaque = new JRadioButton("Opaque");
            JRadioButton textured = new JRadioButton("Textured");
            fillButtonGroup.add(translucent);
            fillButtonGroup.add(opaque);
            fillButtonGroup.add(textured);
            fillPanel.add(opaque);
            fillPanel.add(translucent);
            fillPanel.add(textured);
            opaque.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    differenceMarker.setModel1DominantFill(new Color(255, 200, 200));
                    differenceMarker.setModel2DominantFill(new Color(200, 200, 255));
                    chart.repaint();
                }
            });
            translucent.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    differenceMarker.setModel1DominantFill(new Color(255, 0, 0, 50));
                    differenceMarker.setModel2DominantFill(new Color(0, 0, 255, 50));
                    chart.repaint();
                }
            });
            textured.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (redTexture == null) {
                        redTexture = ChartUtils.createTexture(chart, "TextureRed.png");
                    }
                    if (blueTexture == null) {
                        blueTexture = ChartUtils.createTexture(chart, "TextureBlue.png");
                    }
                    differenceMarker.setModel1DominantFill(redTexture);
                    differenceMarker.setModel2DominantFill(blueTexture);
                    chart.repaint();
                }
            });
            opaque.setSelected(true);
            optionsPanel.add(fillPanel);
            // Now create a panel to set up the line type - straight line or curve
            JPanel edgePanel = new JPanel();
            GridBagLayout edgeLayout = new GridBagLayout();
            edgePanel.setLayout(edgeLayout);
            edgePanel.setBorder(new TitledBorder("Edge"));
            final JRadioButton lineButton = new JRadioButton("Line");
            final JRadioButton curveButton = new JRadioButton("Curve");
            GridBagConstraints c1 = new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0);
            GridBagConstraints c2 = new GridBagConstraints(0,1,1,1,1,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0);
            GridBagConstraints c3 = new GridBagConstraints(0,2,1,1,1,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0);
            final JSlider smoothnessSlider = new JSlider(0, 100, 50);
            ButtonGroup lineGroup = new ButtonGroup();
            lineGroup.add(lineButton);
            lineGroup.add(curveButton);
            edgePanel.add(lineButton, c1);
            edgePanel.add(curveButton, c2);
            curveButton.setSelected(true);
            setSmooth(true);
            ActionListener lineListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setSmooth(!lineButton.isSelected());
                    smoothnessSlider.setEnabled(!lineButton.isSelected());
                }
            };
            lineButton.addActionListener(lineListener);
            curveButton.addActionListener(lineListener);
            JPanel smoothnessPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            smoothnessPanel.add(new JLabel("Smoothness:", JLabel.LEFT));
            
            Hashtable<Integer, JComponent> labels = new Hashtable<Integer, JComponent>();
            labels.put(0, new JLabel("0"));
            labels.put(50, new JLabel("0.5"));
            labels.put(100, new JLabel("1"));
            smoothnessSlider.setMajorTickSpacing(50);
            smoothnessSlider.setMinorTickSpacing(10);
            smoothnessSlider.setPaintTicks(true);
            smoothnessSlider.setPaintLabels(true);
            smoothnessSlider.setLabelTable(labels);
            smoothnessPanel.add(smoothnessSlider);
            smoothnessSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    int value = smoothnessSlider.getValue();
                    double smoothness = value/100.0;
                    differenceMarker.setSmoothness(smoothness);
                    smoothLineRenderer.setSmoothness(smoothness);
                    chart.repaint();
                }
            });
            edgePanel.add(smoothnessPanel, c3);
            optionsPanel.add(edgePanel);
            JPanel shadowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            shadowPanel.setBorder(new TitledBorder("Shadow Visibility"));
            final JCheckBox shadowCheckbox = new JCheckBox("With Shadow");
            shadowCheckbox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (shadowCheckbox.isSelected()) {
                        chart.setShadowVisibility(ShadowVisibility.ALL);
                    } else {
                        chart.setShadowVisibility(ShadowVisibility.NONE);
                    }
                }
            });
            shadowPanel.add(shadowCheckbox);
            optionsPanel.add(shadowPanel);
        }
        return optionsPanel;
    }

    private void setSmooth(boolean smooth) {
        differenceMarker.setSmooth(smooth);
        chart.setLineRenderer(smooth ? smoothLineRenderer : new DefaultLineRenderer(chart));
        chart.repaint();
    }

    public String getName() {
        return "Difference Chart";
    }

    @Override
    public String getDescription() {
        return "This demo shows how to fill in the region between two traces. In this example we " +
        "fill in the area where the red line is above the blue line in red and use a blue fill for " +
        "the area where the blue line is above the red line. (You don't have to use two different fills "+
        "for this, but in certain application areas it is very useful.) You can now also specify that the "+
        "edges of the difference region should be shown with a smooth curve rather than straight line segments.\n\n"+
        "You can now also apply a shadow to a difference chart.";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new DifferenceChartDemo());
    }

}
