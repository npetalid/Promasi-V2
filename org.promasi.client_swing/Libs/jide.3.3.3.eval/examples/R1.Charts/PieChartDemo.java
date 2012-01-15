/*
 * @(#)PieChartDemo.java 8/22/2009
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.ChartType;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.model.*;
import com.jidesoft.chart.render.*;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.util.ChartUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PieChartDemo extends AbstractDemo {
    private static final Font sectionFont = UIManager.getFont("Label.font").deriveFont(Font.BOLD, 11f);
    private static final float outlineWidth = 3f;
    private ChartStyle r,g,b;
    private Chart barChart;
    private ChartCategory<String> red, green, blue;
    private Chart pieChart;
    private JPanel demoPanel, optionsPanel;
    private JRadioButton flatSegmentsRadioButton, raisedSegmentsRadioButton, threeDSegmentsRadioButton,
            texturedSegmentsRadioButton;
    private JRadioButton lineLabelsRadioButton;
    private JRadioButton simpleLabelsRadioButton;
    private JCheckBox rolloverCheckbox, selectionOutlineCheckbox, alwaysOutlineCheckbox;
    private JCheckBox selectionExplodeCheckbox, doubleClickCheckbox;

    private DefaultPieSegmentRenderer defaultPieSegmentRenderer;

    private final JCheckBox shadowCheckBox = new JCheckBox();

    public PieChartDemo() {

    }

    private JPanel createDemo() {
        demoPanel = new JPanel();
        demoPanel.setMinimumSize(new Dimension(400, 400));
        demoPanel.setLayout(new GridLayout(2, 1));
        ButtonGroup segmentButtonGroup = new ButtonGroup();
        ButtonGroup labelButtonGroup = new ButtonGroup();
        Highlight redHighlight = new Highlight("Red");
        Highlight greenHighlight = new Highlight("Green");
        Highlight blueHighlight = new Highlight("Blue");
        red = new ChartCategory<String>("Red", redHighlight);
        green = new ChartCategory<String>("Green", greenHighlight);
        blue = new ChartCategory<String>("Blue", blueHighlight);
        CategoryRange<String> colors = new CategoryRange<String>().add(red).add(green).add(blue);
        r = new ChartStyle(new Color(255, 64, 64, 245));
        g = new ChartStyle(new Color(64, 255, 64, 245));
        b = new ChartStyle(new Color(100, 100, 255, 245));
        Axis xAxis = new CategoryAxis<String>(colors);
        Axis yAxis = new Axis(new NumericRange(0, 40));
        barChart = new Chart();
        DefaultChartModel pieChartModel = new DefaultChartModel("Sample Model");
        pieChartModel.addPoint(new ChartPoint(red, new RealPosition(20), redHighlight));
        pieChartModel.addPoint(new ChartPoint(green, new RealPosition(30), greenHighlight));
        pieChartModel.addPoint(new ChartPoint(blue, new RealPosition(15), blueHighlight));
        pieChart = new Chart(new Dimension(200, 200));
        defaultPieSegmentRenderer = (DefaultPieSegmentRenderer) pieChart.getPieSegmentRenderer();
        barChart.setTitle("RGB Bar Chart");
        final ChartStyle style = new ChartStyle();
        style.setBarsVisible(true);
        style.setLinesVisible(false);
        barChart.setStyle(pieChartModel, style);
        barChart.setGridColor(new Color(150, 150, 150));
        barChart.setChartBackground(new GradientPaint(0f, 0f, Color.lightGray.brighter(), 300f, 300f, Color.lightGray));

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        JPanel displayOptionsPanel = new JPanel();
        displayOptionsPanel.setLayout(new JideBoxLayout(displayOptionsPanel, BoxLayout.Y_AXIS));
        PartialEtchedBorder partialEtchedBorder = new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH);
        JideTitledBorder displayBorder = new JideTitledBorder(
                partialEtchedBorder,
                "Display Options",
                JideTitledBorder.DEFAULT_JUSTIFICATION,
                JideTitledBorder.DEFAULT_POSITION,
                sectionFont);
        displayOptionsPanel.setBorder(displayBorder);
        optionsPanel.add(displayOptionsPanel);

        JPanel interactionOptionsPanel = new JPanel();
        interactionOptionsPanel.setLayout(new JideBoxLayout(interactionOptionsPanel, BoxLayout.Y_AXIS));
        JideTitledBorder interactionBorder = new JideTitledBorder(
                partialEtchedBorder,
                "Interaction Options",
                JideTitledBorder.DEFAULT_JUSTIFICATION,
                JideTitledBorder.DEFAULT_POSITION,
                sectionFont);
        interactionOptionsPanel.setBorder(interactionBorder);
        optionsPanel.add(interactionOptionsPanel);

        rolloverCheckbox = new JCheckBox("Rollover");
        selectionOutlineCheckbox = new JCheckBox("Selection shows outline");
        alwaysOutlineCheckbox = new JCheckBox("Always show outline");
        selectionExplodeCheckbox = new JCheckBox("Selection shows Exploded Segments");
        doubleClickCheckbox = new JCheckBox("Double Click");
        
        flatSegmentsRadioButton = new JRadioButton("Flat");
        raisedSegmentsRadioButton = new JRadioButton("Raised");
        threeDSegmentsRadioButton = new JRadioButton("3D");
        texturedSegmentsRadioButton = new JRadioButton("Textured");
        lineLabelsRadioButton = new JRadioButton("Line Labels");
        simpleLabelsRadioButton = new JRadioButton("Simple Labels");
        JRadioButton noLabelsRadioButton = new JRadioButton("No Labels");

        displayOptionsPanel.add(flatSegmentsRadioButton);
        displayOptionsPanel.add(raisedSegmentsRadioButton);
        displayOptionsPanel.add(threeDSegmentsRadioButton);
        displayOptionsPanel.add(texturedSegmentsRadioButton);

        segmentButtonGroup.add(raisedSegmentsRadioButton);
        segmentButtonGroup.add(flatSegmentsRadioButton);
        segmentButtonGroup.add(threeDSegmentsRadioButton);
        segmentButtonGroup.add(texturedSegmentsRadioButton);

        demoPanel.add(pieChart);
        pieChart.setPreferredSize(new Dimension(500, 250));
        pieChart.setTitle("RGB Pie");
        pieChart.addModel(pieChartModel);
        pieChart.setChartType(ChartType.PIE);
        pieChart.setStyle(pieChartModel, style);
        pieChart.setSelectionShowsOutline(false);
        barChart.setSelectionShowsOutline(false);
        defaultPieSegmentRenderer.setAlwaysShowOutlines(false);
        defaultPieSegmentRenderer.setOutlineWidth(outlineWidth);
        DefaultBarRenderer barRenderer = new DefaultBarRenderer();
        barChart.setBarRenderer(barRenderer);
        barRenderer.setAlwaysShowOutlines(false);
        barRenderer.setOutlineWidth(outlineWidth);
        useColorHighlights();

        displayOptionsPanel.add(shadowCheckBox);
        shadowCheckBox.setText("Shadow");

        displayOptionsPanel.add(lineLabelsRadioButton);
        displayOptionsPanel.add(simpleLabelsRadioButton);
        displayOptionsPanel.add(noLabelsRadioButton);
        labelButtonGroup.add(lineLabelsRadioButton);
        labelButtonGroup.add(simpleLabelsRadioButton);
        labelButtonGroup.add(noLabelsRadioButton);

        final JSlider offsetAngleSlider = new JSlider(0, 360, 0);
        offsetAngleSlider.setMajorTickSpacing(90);
        offsetAngleSlider.setMinorTickSpacing(10);
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        JLabel offsetAngleLabel = new JLabel("Offset Angle: ", JLabel.LEFT);
        offsetAngleLabel.setToolTipText("Offset angle in the range 0 to 360 degrees");
        sliderPanel.add(offsetAngleLabel);
        sliderPanel.add(offsetAngleSlider);
        displayOptionsPanel.add(sliderPanel);

        offsetAngleSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = offsetAngleSlider.getValue();
                style.setPieOffsetAngle(value);
                pieChart.repaint();
            }
        });

        interactionOptionsPanel.add(rolloverCheckbox);
        interactionOptionsPanel.add(selectionOutlineCheckbox);
        interactionOptionsPanel.add(alwaysOutlineCheckbox);
        interactionOptionsPanel.add(selectionExplodeCheckbox);
        interactionOptionsPanel.add(doubleClickCheckbox);

        shadowCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                pieChart.setShadowVisible(shadowCheckBox.isSelected());
                barChart.setShadowVisible(shadowCheckBox.isSelected());
                demoPanel.repaint();
            }
        });
        shadowCheckBox.setSelected(true);

        rolloverCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pieChart.setRolloverEnabled(rolloverCheckbox.isSelected());
                barChart.setRolloverEnabled(rolloverCheckbox.isSelected());
            }
        });
        rolloverCheckbox.setRolloverEnabled(false);

        ActionListener labelsSelectionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AbstractPieSegmentRenderer renderer = (AbstractPieSegmentRenderer) pieChart.getPieSegmentRenderer();
                PieLabelRenderer labelRenderer;
                if (lineLabelsRadioButton.isSelected()) {
                    labelRenderer = new LinePieLabelRenderer();
                }
                else if (simpleLabelsRadioButton.isSelected()) {
                    labelRenderer = new SimplePieLabelRenderer();
                }
                else {
                    labelRenderer = null;
                }
                renderer.setPieLabelRenderer(labelRenderer);
                demoPanel.repaint();
            }
        };
        lineLabelsRadioButton.setSelected(true);
        lineLabelsRadioButton.addActionListener(labelsSelectionListener);
        simpleLabelsRadioButton.addActionListener(labelsSelectionListener);
        noLabelsRadioButton.addActionListener(labelsSelectionListener);

        selectionOutlineCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pieChart.setSelectionShowsOutline(selectionOutlineCheckbox.isSelected());
                barChart.setSelectionShowsOutline(selectionOutlineCheckbox.isSelected());
                boolean selectionEnabled = selectionOutlineCheckbox.isSelected() || selectionExplodeCheckbox.isSelected();
                pieChart.setSelectionEnabled(selectionEnabled);
                barChart.setSelectionEnabled(selectionEnabled);
                pieChart.update();
                barChart.update();
            }
        });
        selectionOutlineCheckbox.setSelected(false);

        alwaysOutlineCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AbstractRenderer pieRenderer = (AbstractRenderer) pieChart.getPieSegmentRenderer();
                AbstractRenderer barRenderer = (AbstractRenderer) barChart.getBarRenderer();
                pieRenderer.setAlwaysShowOutlines(alwaysOutlineCheckbox.isSelected());
                barRenderer.setAlwaysShowOutlines(alwaysOutlineCheckbox.isSelected());
                demoPanel.repaint();
            }
        });

        selectionExplodeCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pieChart.setSelectionShowsExplodedSegments(selectionExplodeCheckbox.isSelected());
                boolean selectionEnabled = selectionOutlineCheckbox.isSelected() || selectionExplodeCheckbox.isSelected();
                pieChart.setSelectionEnabled(selectionEnabled);
                barChart.setSelectionEnabled(selectionEnabled);
                pieChart.update();
                barChart.update();
            }
        });
        selectionOutlineCheckbox.setSelected(false);

        final ActionListener doubleClickListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Chart source = (Chart) e.getSource();
                ChartPoint cp = (ChartPoint) source.getCurrentChartPoint();
                if (cp != null) {
                    String message = String.format("Chart '%s': You double-clicked on %s", source.getName(), cp.getHighlight().name());
                    JOptionPane.showMessageDialog(pieChart, message, "Double Click", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        doubleClickCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (doubleClickCheckbox.isSelected()) {
                    pieChart.addDoubleClickListener(doubleClickListener);
                    barChart.addDoubleClickListener(doubleClickListener);
                }
                else {
                    pieChart.removeDoubleClickListener(doubleClickListener);
                    barChart.addDoubleClickListener(doubleClickListener);
                }
            }
        });

        barChart.addModel(pieChartModel);
        barChart.setLayout(new BorderLayout());
        barChart.setBarGap(5);
        barChart.setMinimumSize(new Dimension(100, 100));
        barChart.setPreferredSize(new Dimension(100, 100));
        barChart.setXAxis(xAxis);
        barChart.setYAxis(yAxis);
        barChart.setVerticalGridLinesVisible(false);
        demoPanel.add(barChart);

        ListSelectionModel selectionModel = pieChart.getSelectionsForModel(pieChartModel);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Repaint when the selection changes and triggered by the other component
                demoPanel.repaint();
            }
        });
        // Transfer the selection model from the pie chart to the bar chart
        // Now they will share the same selections 
        barChart.setSelectionsForModel(pieChartModel, selectionModel);

        flatSegmentsRadioButton.setSelected(true);

        ActionListener buttonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JRadioButton button = (JRadioButton) e.getSource();
                if (button.isSelected()) {
                    AbstractPieSegmentRenderer pieRenderer = (AbstractPieSegmentRenderer) pieChart.getPieSegmentRenderer();
                    boolean alwaysShowOutlines = pieRenderer.isAlwaysShowOutlines();
                    PieLabelRenderer labelRenderer = pieRenderer.getPieLabelRenderer();
                    if (button == flatSegmentsRadioButton) {
                        // Make sure we use the same label renderer
                        defaultPieSegmentRenderer.setPieLabelRenderer(labelRenderer);
                        // And the same setting for showing outlines
                        defaultPieSegmentRenderer.setAlwaysShowOutlines(alwaysShowOutlines);
                        pieChart.setPieSegmentRenderer(defaultPieSegmentRenderer);
                        useColorHighlights();
                        DefaultBarRenderer barRenderer = new DefaultBarRenderer();
                        barRenderer.setOutlineWidth(outlineWidth);
                        barRenderer.setAlwaysShowOutlines(alwaysShowOutlines);
                        barChart.setBarRenderer(barRenderer);
                        barChart.getXAxis().setAxisRenderer(new NoAxisRenderer());
                    }
                    else if (button == raisedSegmentsRadioButton) {
                        AbstractPieSegmentRenderer r = new RaisedPieSegmentRenderer();
                        r.setOutlineWidth(outlineWidth);
                        r.setPieLabelRenderer(labelRenderer);
                        r.setAlwaysShowOutlines(pieRenderer.isAlwaysShowOutlines());
                        pieChart.setPieSegmentRenderer(r);
                        useColorHighlights();
                        RaisedBarRenderer barRenderer = new RaisedBarRenderer();
                        barRenderer.setOutlineWidth(outlineWidth);
                        barRenderer.setAlwaysShowOutlines(alwaysShowOutlines);
                        barChart.setBarRenderer(barRenderer);
                        barChart.getXAxis().setAxisRenderer(new NoAxisRenderer());
                    }
                    else if (button == threeDSegmentsRadioButton) {
                        AbstractPieSegmentRenderer r = new Pie3DRenderer();
                        r.setPieLabelRenderer(labelRenderer);
                        r.setOutlineWidth(outlineWidth);
                        r.setAlwaysShowOutlines(pieRenderer.isAlwaysShowOutlines());
                        pieChart.setPieSegmentRenderer(r);
                        useColorHighlights();
                        CylinderBarRenderer barRenderer = new CylinderBarRenderer();
                        barRenderer.setAlwaysShowOutlines(alwaysShowOutlines);
                        barRenderer.setOutlineWidth(outlineWidth);
                        barChart.setBarRenderer(barRenderer);
                        barChart.getXAxis().setAxisRenderer(new Axis3DRenderer());
                    }
                    else if (button == texturedSegmentsRadioButton) {
                        defaultPieSegmentRenderer.setPieLabelRenderer(labelRenderer);
                        defaultPieSegmentRenderer.setAlwaysShowOutlines(pieRenderer.isAlwaysShowOutlines());
                        pieChart.setPieSegmentRenderer(defaultPieSegmentRenderer);
                        useTextureHighlights();
                        DefaultBarRenderer barRenderer = new DefaultBarRenderer();
                        barRenderer.setOutlineWidth(outlineWidth);
                        barRenderer.setAlwaysShowOutlines(alwaysShowOutlines);
                        barChart.setBarRenderer(barRenderer);
                        barChart.getXAxis().setAxisRenderer(new NoAxisRenderer());
                    }
                    pieChart.startAnimation();
                    barChart.startAnimation();
                    pieChart.update();
                    barChart.update();
                    demoPanel.revalidate();
                    demoPanel.repaint();
                }
            }
        };

        flatSegmentsRadioButton.addActionListener(buttonListener);
        raisedSegmentsRadioButton.addActionListener(buttonListener);
        threeDSegmentsRadioButton.addActionListener(buttonListener);
        texturedSegmentsRadioButton.addActionListener(buttonListener);

        return demoPanel;
    }

    private void useColorHighlights(Chart chart) {
        // Highlights are semantic tags. Here we specify what they actually mean
        chart.setHighlightStyle(red.getHighlight(), new ChartStyle(r).withBars());
        chart.setHighlightStyle(green.getHighlight(), new ChartStyle(g).withBars());
        chart.setHighlightStyle(blue.getHighlight(), new ChartStyle(b).withBars());
    }

    private void useColorHighlights() {
        useColorHighlights(pieChart);
        useColorHighlights(barChart);
    }

    private void useTextureHighlights(Chart chart) {
        // Highlights are semantic tags. Here we specify what they actually mean
        chart.setHighlightStyle(red.getHighlight(), createTextureStyle(chart, "TextureRed.png"));
        chart.setHighlightStyle(green.getHighlight(), createTextureStyle(chart, "TextureGreen.png"));
        chart.setHighlightStyle(blue.getHighlight(), createTextureStyle(chart, "TextureBlue.png"));
    }

    private void useTextureHighlights() {
        useTextureHighlights(pieChart);
        useTextureHighlights(barChart);
    }

    private ChartStyle createTextureStyle(Chart chart, String fileName) {
        Paint p = ChartUtils.createTexture(chart, fileName);
        ChartStyle style = new ChartStyle().withBars();
        style.setBarPaint(p);
        return style;
    }

    @Override
    public String getDescription() {
        return "The Pie Chart shown here uses exactly the same model as the bar chart. " +
                "If you move the mouse over the pie chart, the segment under the mouse responds by changing its outline color. " +
                "Also, if you click on a segment, it is 'exploded' and separated from the main pie chart. Any number of segments can" +
                " be exploded.";
    }

    @Override
    public boolean isCommonOptionsPaneVisible() {
        return false;
    }

    @Override
    public Component getOptionsPanel() {
        return optionsPanel;
    }

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = createDemo();
        }
        return demoPanel;
    }

    public String getName() {
        return "Pie Chart Demo";
    }

    @Override
    public String getDemoFolder() {
        return "R1.Charts";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new PieChartDemo());
    }

}
