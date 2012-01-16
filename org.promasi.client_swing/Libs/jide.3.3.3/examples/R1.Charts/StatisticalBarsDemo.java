/*
 * @(#)StatisticalBarsDemo.java 06-AUG-2010
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.DefaultNumericTickCalculator;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.axis.Tick;
import com.jidesoft.chart.model.*;
import com.jidesoft.chart.render.AbstractRenderer;
import com.jidesoft.chart.render.BarRenderer;
import com.jidesoft.chart.render.DefaultBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.style.StripePaint;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.Positionable;
import com.jidesoft.range.Range;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Point2D;

public class StatisticalBarsDemo extends AbstractDemo {
    private JPanel demoPanel;
    private Chart chart;
    private Category<String> processA = new ChartCategory<String>("Process A");
    private Category<String> processB = new ChartCategory<String>("Process B");
    private Category<String> processC = new ChartCategory<String>("Process C");
    private Highlight highlightA = new Highlight("A");
    private Highlight highlightB = new Highlight("B");
    private Highlight highlightC = new Highlight("C");
    private CategoryRange<String> categories = new CategoryRange<String>().add(processA).add(processB).add(processC);
    private static final String fontName = "Sans Serif";
    private Font titleFont = new Font(fontName, Font.BOLD, 22);
    private Font tickFont = new Font(fontName, Font.BOLD, 16);
    private Font labelFont = new Font(fontName, Font.BOLD, 18);
    private Stroke axisStroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            Dimension size = new Dimension(500, 450);
            demoPanel.setPreferredSize(size);
            demoPanel.setMinimumSize(size);
            JLabel title = new JLabel("Process Comparison", JLabel.CENTER);
            title.setFont(titleFont);
            title.setBorder(new EmptyBorder(20, 80, 0, 20));
            demoPanel.add(title, BorderLayout.NORTH);
            chart = new Chart();
            chart.setAnimateOnShow(false);
            chart.setBorder(new EmptyBorder(20, 40, 10, 40));
            demoPanel.setBackground(Color.white);

            CategoryAxis<String> xAxis = new CategoryAxis<String>(categories);
            categories.setMinimum(0.5);
            categories.setMaximum(categories.getPossibleValues().size()+0.5);
            xAxis.setTickLabelRotation(Math.PI/4);
            xAxis.setTickLabelOffset(16);
            xAxis.setStroke(axisStroke);
            xAxis.setAxisColor(Color.black);
            chart.setXAxis(xAxis);
            NumericAxis yAxis = new NumericAxis(0, 11);
            yAxis.setLabel(new AutoPositionedLabel("Measurement (unit)", Color.black, labelFont));
            yAxis.setStroke(axisStroke);
            yAxis.setAxisColor(Color.black);
            yAxis.setLabelWidth(75);
            // Add a bit of space between the ticks and their labels
            yAxis.setTickLabelOffset(5);
            yAxis.setTickCalculator(new DefaultNumericTickCalculator() {
                @Override
                public Tick[] calculateTicks(Range<Double> r) {
                    Tick[] ticks = new Tick[12];
                    for (int i=0; i<ticks.length; i++) {
                        ticks[i] = new Tick(i, Integer.toString(i));
                    }
                    return ticks;
                }
            });
            chart.setYAxis(yAxis);
            chart.setPanelBackground(Color.white);
            chart.setTickFont(tickFont);
            chart.setTickColor(Color.black);
            chart.setTickLength(7);
            // The ticks look best if they are the same width as the axis
            chart.setTickStroke(axisStroke);
            chart.setHorizontalGridLinesVisible(false);
            chart.setVerticalGridLinesVisible(false);
            demoPanel.add(chart, BorderLayout.CENTER);
            DefaultChartModel model = new DefaultChartModel();
            // Populate the model with some sample data
            ChartPoint dataA = new ChartPointWithSpread(processA, new RealPosition(10), new RealPosition(0.8));
            dataA.setHighlight(highlightA);
            ChartPoint dataB = new ChartPointWithSpread(processB, new RealPosition(7.5), new RealPosition(0.7));
            dataB.setHighlight(highlightB);
            ChartPoint dataC = new ChartPointWithSpread(processC, new RealPosition(8.5), new RealPosition(0.8));
            dataC.setHighlight(highlightC);
            model.addPoint(dataA);
            model.addPoint(dataB);
            model.addPoint(dataC);
            ChartStyle style = new ChartStyle().withBars();
            ChartStyle styleA = new ChartStyle(Color.blue).withBars();
            styleA.setBarPaint(createBrickTexture(10, 1.5f));
            ChartStyle styleB = new ChartStyle(Color.black).withBars();
            ChartStyle styleC = new ChartStyle(Color.green).withBars();
            styleC.setBarPaint(new StripePaint(45, 18, 2.5f));
            chart.setHighlightStyle(highlightA, styleA);
            chart.setHighlightStyle(highlightB, styleB);
            chart.setHighlightStyle(highlightC, styleC);
            style.setLineColor(Color.black);
            chart.setBarRenderer(new ErrorBarRenderer());
            // Set a proportional bar width of 60% of the available space
            style.setBarWidthProportion(0.6);
            chart.addModel(model, style);
        }
        return demoPanel;
    }

    public String getName() {
        return "Statistical Bar Chart";
    }

    @Override
    public String getDescription() {
        return "This demo is a bar chart in stark black and white, which works well in printed publications."+
        " The chart uses the StripePaint class to fill the bars and also shows how it is possible to modify an "+
        "existing bar renderer to add an error bar";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    class ChartPointWithSpread extends ChartPoint {
        private Positionable spread;

        public ChartPointWithSpread(Positionable x, Positionable y, Positionable spread) {
            super(x, y);
            setSpread(spread);
        }

        public Positionable getSpread() {
            return spread;
        }

        public void setSpread(Positionable spread) {
            this.spread = spread;
        }
    }

    /**
     * A custom point renderer that decorates the underlying render with an error bar
     */
    class ErrorBarRenderer extends AbstractRenderer implements BarRenderer {
        private DefaultBarRenderer delegate = new DefaultBarRenderer();

        public ErrorBarRenderer() {
            delegate.setOutlineColor(Color.black);
            delegate.setOutlineWidth(3f);
            delegate.setAlwaysShowOutlines(true);
        }

        public int getMinimumBreadth() {
            return 10;
        }

        public Shape renderBar(Graphics g, Chart chart, ChartModel m, Chartable p, boolean isSelected, boolean hasRollover,
                              boolean hasFocus, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            // First decorate the bar with the error bar
            ChartPointWithSpread cp = (ChartPointWithSpread) p;
            double xPos = cp.getX().position();
            double yPos = cp.getY().position();
            double spread = cp.getSpread().position();
            Point basePoint = chart.calculatePixelPoint(new Point2D.Double(xPos, yPos));
            Point topPoint = chart.calculatePixelPoint(new Point2D.Double(xPos, yPos+spread));
            g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g2d.setColor(Color.black);
            g2d.drawLine(basePoint.x, basePoint.y, topPoint.x, topPoint.y);
            g2d.drawLine(basePoint.x - width/3, topPoint.y, basePoint.x + width/3, topPoint.y);
            // Now do the normal bar rendering
            return delegate.renderBar(g, chart, m, p, isSelected, hasRollover, hasFocus, x, y, width, height);
        }
    }

    public static Paint createBrickTexture(int brickSize, float lineWidth) {
        StripePaint p1 = new StripePaint(-45, brickSize, lineWidth);
        BasicStroke stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, brickSize/2f, new float[] {brickSize/2f, brickSize/2f}, 0f);
        StripePaint p2 = new StripePaint(45, brickSize, stroke);
        p2.setBackground(p1);
        return p2;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new StatisticalBarsDemo());
    }

}
