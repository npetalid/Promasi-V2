/*
 * @(#)MorphingBarDemo.java
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.ChartType;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.ChartPoint;
import com.jidesoft.chart.model.Chartable;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.Highlight;
import com.jidesoft.chart.model.ModelMorpher;
import com.jidesoft.chart.render.*;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings("serial")
public class MorphingBarDemo extends AbstractDemo {
    private Category<Month> jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;
    private CategoryRange<Month> months;
    private JPanel optionsPanel, demoPanel, buttonPanel;
    private DefaultPieSegmentRenderer segmentRenderer;
    private PointLabeler pointLabeler;
    private Chart chart;
    private ChartStyle style;
    private ChartModel activeModel;
    private ModelMorpher morpher;

    public MorphingBarDemo() {

    }

    public Component getDemoPanel() {
        if (demoPanel == null) {
            jan = new Category<Month>(Month.January);
            feb = new Category<Month>(Month.February);
            mar = new Category<Month>(Month.March);
            apr = new Category<Month>(Month.April);
            may = new Category<Month>(Month.May);
            jun = new Category<Month>(Month.June);
            jul = new Category<Month>(Month.July);
            aug = new Category<Month>(Month.August);
            sep = new Category<Month>(Month.September);
            oct = new Category<Month>(Month.October);
            nov = new Category<Month>(Month.November);
            dec = new Category<Month>(Month.December);
            demoPanel = new JPanel(new BorderLayout());
            buttonPanel = new JPanel(new GridLayout(4, 12, 1, 1));
            WeatherData wd = WeatherData.getInstance();
            ButtonGroup buttonGroup = new ButtonGroup();
            final Font titleFont = UIManager.getFont("Label.font").deriveFont(Font.BOLD, 16f);
            for (Integer year : wd.getSunshineModelKeys()) {
                JToggleButton button = new JToggleButton(Integer.valueOf((year-1900) % 100).toString());
                buttonGroup.add(button);
                buttonPanel.add(button);
                final Integer y = year;
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!morpher.isMorphing()) {
                            chart.setTitle(new AutoPositionedLabel("Sunshine for "+y, Color.black, titleFont));
                            switchToModel(createModel(y));
                        }
                    }
                });
            }
            demoPanel.add(buttonPanel, BorderLayout.SOUTH);
            chart = new Chart();
            chart.setShadowVisible(false);
            chart.setBarGap(20);
            morpher = new ModelMorpher(chart);
            morpher.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (ModelMorpher.PROPERTY_MORPH_ENDED.equals(evt.getPropertyName())) {
                        chart.addModel(activeModel, style);
                    }
                }
            });
            months = new CategoryRange<Month>().add(jan).add(feb).add(mar).add(apr).add(may).add(jun)
                .add(jul).add(aug).add(sep).add(oct).add(nov).add(dec);
            CategoryAxis<Month> xAxis = new CategoryAxis<Month>(months);
            xAxis.setLabel("Months");
            NumericAxis yAxis = new NumericAxis(0, 310);
            yAxis.setLabel("Sunshine (hrs)");
            chart.setXAxis(xAxis);
            chart.setYAxis(yAxis);
            chart.setVerticalGridLinesVisible(false);
            segmentRenderer = new DefaultPieSegmentRenderer();
            chart.setPieSegmentRenderer(segmentRenderer);
            pointLabeler = new PointLabeler() {
                public String getDisplayText(Chartable p) {
                    if (!morpher.isMorphing()) {
                        Category<Month> monthCategory = (Category<Month>) p.getX();
                        return String.format("%s: %.1f", monthCategory.getName(), p.getY().position());
                    } else {
                        return null;
                    }
                }
            };
            segmentRenderer.setPointLabeler(pointLabeler);
            SmoothLineRenderer renderer = new SmoothLineRenderer(chart);
            chart.setLineRenderer(renderer);
            demoPanel.add(chart, BorderLayout.CENTER);
            demoPanel.setPreferredSize(new Dimension(600, 500));
            style = new ChartStyle(new Color(60, 0, 255, 190), false, false, true);
            style.setLineWidth(5);
            activeModel = createModel(1959);
            chart.addModel(activeModel, style);
            chart.setTitle(new AutoPositionedLabel("Sunshine for 1959", Color.black, titleFont));
            for (Month m : Month.values()) {
                ChartStyle s = new ChartStyle(m.getColor()).withBars();
                s.setLinesVisible(true);
                Highlight h = new Highlight(m.name());
                chart.setHighlightStyle(h, s);
            }
        }
        return demoPanel;
    }
    
    @Override
    public JPanel getOptionsPanel() {
        if (optionsPanel == null) {
            optionsPanel = new JPanel(new GridLayout(2, 2));
            final JCheckBox lineCheckbox = new JCheckBox("with Trend Line");
            final JCheckBox simpleLabelsCheckBox = new JCheckBox("with Simple Labels");
            ButtonGroup buttonGroup = new ButtonGroup();
            JRadioButton barRadioButton = new JRadioButton("Bar");
            JRadioButton pieRadioButton = new JRadioButton("Pie");
            buttonGroup.add(barRadioButton);
            buttonGroup.add(pieRadioButton);
            optionsPanel.add(barRadioButton);
            optionsPanel.add(lineCheckbox);
            optionsPanel.add(pieRadioButton);
            optionsPanel.add(simpleLabelsCheckBox);
            simpleLabelsCheckBox.setEnabled(false);
            lineCheckbox.setEnabled(true);
            barRadioButton.setSelected(true);
            barRadioButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chart.setChartType(ChartType.XY);
                    simpleLabelsCheckBox.setEnabled(false);
                    lineCheckbox.setEnabled(true);
                    chart.repaint();
                }
            });
            pieRadioButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chart.setChartType(ChartType.PIE);
                    simpleLabelsCheckBox.setEnabled(true);
                    lineCheckbox.setEnabled(false);
                    chart.repaint();
                }
            });
            simpleLabelsCheckBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (simpleLabelsCheckBox.isSelected()) {
                        segmentRenderer.setPieLabelRenderer(new SimplePieLabelRenderer());
                    } else {
                        segmentRenderer.setPieLabelRenderer(new LinePieLabelRenderer());
                    }
                    segmentRenderer.setPointLabeler(pointLabeler);
                    chart.repaint();
                }
            });
            lineCheckbox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ChartStyle style = chart.getStyle(activeModel);
                    style.setLinesVisible(lineCheckbox.isSelected());
                    chart.repaint();
                }
            });
        }
        return optionsPanel;
    }
    
    public DefaultChartModel createModel(Integer year) {
        DefaultChartModel model = new DefaultChartModel(year.toString());
        WeatherData wd = WeatherData.getInstance();
        Double[] values = wd.getSunshineValues(year);
        for (int i=0; i<values.length; i++) {
            Month m = Month.getMonth(i+1);
            Category<Month> c = months.getCategoryValues().get(i);
            Highlight h = new Highlight(m.name());
            ChartPoint point = new ChartPoint(c, values[i], h);
            model.addPoint(point);
        }
        return model;
    }

    private void switchToModel(final ChartModel newModel) {
        assert SwingUtilities.isEventDispatchThread();
        if (newModel != null) {
            if (activeModel == null) {
                chart.addModel(newModel, style);
            } else {
                final ChartModel oldModel = activeModel;
                if (chart.containsModel(oldModel)) {
                    chart.removeModel(oldModel);
                }
                morpher.morph(oldModel, style, newModel, style);
            }
            activeModel = newModel;
        }
    }

    @Override
    public String getDescription() {
        return "This demo shows smooth transitions between bar charts. " +
                "By the way, there was a drought in the Summer of 1976.";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public String getName() {
        return "Morphing Bar/Pie Demo";
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new MorphingBarDemo());
    }

}