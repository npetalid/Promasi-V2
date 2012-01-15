/**
 * 2002 - 2011 JIDE Software Incorporated. All rights reserved.
 * Copyright (c) Catalysoft Ltd, 2005-2011 All Rights Reserved
 */
import com.jidesoft.chart.*;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.event.PointSelection;
import com.jidesoft.chart.model.*;
import com.jidesoft.chart.render.DefaultPointRenderer;
import com.jidesoft.chart.render.SmoothLineRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CategoryRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

/**
 * This may have a passing visual resemblance to a graph seen at http://raphaeljs.com/analytics.html
 */
@SuppressWarnings("serial")
public class NotRaphaelDemo extends AbstractDemo {
    private static final int[] data = {8,25,27,25,54,59,79,47,27,44,44,51,56,83,12,91,52,12,40,8,60,29,7,33,56,25,1,78,70,68,2};
    public static final int TOOL_TIP_WIDTH = 100;
    public static final int TOOL_TIP_HEIGHT = 50;
    public static final int toolTipXOffset = 20;
    public static final int toolTipYOffset = -25;
    private static Color blue = new Color(0, 75, 190);
    private static Color blueFill = new Color(0, 75, 190, 75);
    private JScrollPane panel;
    private Chart chart;
    private ChartModel model;
    private Chartable selectedPoint;
    private final Highlight selectionHighlight = new Highlight("selection");
    private CustomToolTip toolTip;
    /**
     * We maintain this as a floating point position, but cast to integers before setting the position
     */
    private Point2D toolTipLocation;
    private Timer toolTipTimer;

    public NotRaphaelDemo() {
        super();
    }

    public JComponent getDemoPanel() {
        chart = new Chart();
        panel = new JScrollPane(chart);
        chart.setPreferredSize(new Dimension(800, 300));

        CategoryRange<Integer> range = new CategoryRange<Integer>();
        for (int i=1; i<=31; i++) {
            range.add(DayOfMonth.getDay(i));
        }
        CategoryAxis<Integer> xAxis = new CategoryAxis<Integer>(range);
        chart.setXAxis(xAxis);
        chart.setYAxis(new NumericAxis(0, 100));
        ChartStyle style = new ChartStyle(blue, true, true);
        style.setLineFill(blueFill);
        style.setLineWidth(4);
        style.setPointSize(10);
        ChartStyle selectionStyle = new ChartStyle(style);
        selectionStyle.setPointSize(16);
        chart.setHighlightStyle(selectionHighlight, selectionStyle);
        chart.getYAxis().setTicksVisible(false);
        chart.getYAxis().setVisible(false);
        model = createModel();
        chart.addModel(model, style);
        chart.setPanelBackground(Color.black);
        chart.setChartBackground(Color.black);
        Color gridColor = new Color(51, 51, 51);
        chart.setGridColor(gridColor);
        chart.setVerticalGridLinesVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setLabelColor(Color.white);
        DefaultPointRenderer pointRenderer = new DefaultPointRenderer();
        pointRenderer.setAlwaysShowOutlines(true);
        pointRenderer.setOutlineColor(Color.black);
        pointRenderer.setOutlineWidth(1);
        chart.setPointRenderer(pointRenderer);
        SmoothLineRenderer lineRenderer = new SmoothLineRenderer(chart);
        lineRenderer.setSmoothness(0.7);
        chart.setLineRenderer(lineRenderer);
        // Generate some custom grid lines to match the original chart
        int i = 1;
        while (i<=31) {
            DayOfMonth d = DayOfMonth.getDay(i);
            LineMarker marker = new LineMarker(chart, Orientation.vertical, d.position(), gridColor);
            chart.addDrawable(marker);
            i += 3;
        }
        for (i = 0; i<=100; i += 10) {
            RectangularRegionMarker marker = new RectangularRegionMarker(chart, 1, 31, i, i, gridColor);
            marker.setOutlineColor(gridColor);
            //LineMarker marker = new LineMarker(chart, Orientation.horizontal, i, gridColor);
            chart.addDrawable(marker);
        }
        // Listen to mouse movement and make the nearest point slightly bigger
        MouseMotionListener listener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                PointSelection ps = chart.nearestPoint(p, model);
                double distance = ps.getDistance();
                if (distance < 50) {
                    if (selectedPoint != ps.getSelected()) {
                        if (selectedPoint != null) {
                            Highlightable h = (Highlightable) selectedPoint;
                            h.setHighlight(null);
                        }
                        selectedPoint = ps.getSelected();
                        if (selectedPoint instanceof Highlightable) {
                            Highlightable h = (Highlightable) selectedPoint;
                            h.setHighlight(selectionHighlight);
                            if (toolTip == null) {
                                toolTip = new CustomToolTip();
                                chart.addDrawable(toolTip);
                            }
                            toolTip.setText(String.format("%.0f hits", selectedPoint.getY().position()));
                            toolTip.setSubText(String.format("%.0f Sep 2008", selectedPoint.getX().position()));
                            chart.repaint();
                        }
                        moveToolTipTo(getToolTipLocation(ps));
                    }
                }
                ps.getSelected();
            }
        };
        chart.addMouseMotionListener(listener);
        return panel;
    }

    /**
     * Starts a timer to move the tool tip to the specified location
     * @param targetLocation where to move the ToolTip to
     */
    public void moveToolTipTo(final Point targetLocation) {
        if (toolTipTimer != null && toolTipTimer.isRunning()) {
            toolTipTimer.stop();
        }
        Point2D currentPosition = toolTip.getLocation();
        if ((currentPosition != null && currentPosition.getX() == 0.0 && currentPosition.getY() == 0) || !toolTip.isVisible()) {
            // Go straight to the correct location for the first one
            toolTip.setVisible(true);
            toolTip.setLocation(targetLocation);
            return;
        }
        toolTipLocation = currentPosition == null ? null : new Point2D.Double(currentPosition.getX(), currentPosition.getY());
        ActionListener toolTipMover = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (toolTipLocation == null) {
                    return;
                }
                double xDiff = targetLocation.x - toolTipLocation.getX();
                double yDiff = targetLocation.y - toolTipLocation.getY();
                if (Math.abs(xDiff) < 1 && Math.abs(yDiff) < 1) {
                    toolTipTimer.stop();
                    toolTipDisappearDelay();
                } else {
                    final double convergenceSpeed = 5.0;
                    double x = toolTipLocation.getX() + xDiff/convergenceSpeed;
                    double y = toolTipLocation.getY() + yDiff/convergenceSpeed;
                    toolTipLocation = new Point2D.Double(x, y);
                    toolTip.setLocation(toolTipLocation);
                    chart.repaint();
                }
            }
        };
        toolTipTimer = new Timer(30, toolTipMover);
        toolTipTimer.start();
    }

    /**
     * Starts a timer for the disappearance of the tooltip
     */
    private void toolTipDisappearDelay() {
        ActionListener disappearListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolTip.setVisible(false);
                chart.repaint();
            }
        };
        toolTipTimer = new Timer(10000, disappearListener);
        toolTipTimer.setRepeats(false);
        toolTipTimer.start();
    }

    /**
     * Uses some basic intelligence about the size of the chart object to compute the
     * location of the tool tip
     */
    public Point getToolTipLocation(PointSelection ps) {
        Chartable chartable = ps.getSelected();
        Point2D userPoint = new Point2D.Double(chartable.getX().position(), chartable.getY().position());
        Point pixelPoint = chart.calculatePixelPoint(userPoint);
        Point location = new Point(pixelPoint.x + toolTipXOffset, pixelPoint.y + toolTipYOffset);
        if (location.y < 0) {
            location.y = 0;
        }
        if (location.y + TOOL_TIP_HEIGHT > chart.getChartHeight()) {
            location.y = chart.getChartHeight() - TOOL_TIP_HEIGHT;
        }

        if (location.x + TOOL_TIP_WIDTH > chart.getXEnd()) {
            location.x = pixelPoint.x - TOOL_TIP_WIDTH - toolTipXOffset;
        }
        return location;
    }


    private ChartModel createModel() {
        DefaultChartModel model = new DefaultChartModel();
        for (int i=0; i<data.length; i++) {
            model.addPoint(DayOfMonth.getDay(i+1), data[i]);
        }
        return model;
    }

    public String getName() {
        return "Not Raphael";
    }

    @Override
    public String getDescription() {
        return "This is a chart of web statistics gathered over a month. "+
                "It looks a little like a chart you might have seen at http://raphaeljs.com/analytics.html";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    /**
     * A Custom tool tip to explain the contents of a data point.
     * We used a custom tool tip rather than the standard Swing JToolTip so we can
     * easily position it (and animate its position) and also give it
     * rounded corners.
     */
    static class CustomToolTip implements Drawable {
        private Color background = Color.black;
        private Color foreground = Color.white;
        private Font labelFont = UIManager.getFont("Label.font");
        private Font textFont = labelFont.deriveFont(Font.BOLD, 10f);
        private Font subTextFont = labelFont.deriveFont(Font.PLAIN, 10f);
        private Point2D location;
        private boolean visible;
        private String text, subText;

        public CustomToolTip() {

        }

        public Point2D getLocation() {
            return location;
        }

        public void setLocation(Point2D location) {
            this.location = location;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setSubText(String text) {
            this.subText = text;
        }

        public void draw(Graphics g) {
            int width = TOOL_TIP_WIDTH;
            int height = TOOL_TIP_HEIGHT;
            if (g == null || !visible || width == 0 || height == 0) {
                return;
            }
            Point2D loc = getLocation();
            if (loc != null) {
                Graphics2D g2 = (Graphics2D) g.create((int) loc.getX(), (int) loc.getY(), width, height);
                g2.setColor(background);
                g2.fillRoundRect(0, 0, width, height, 12, 12);
                g2.setColor(foreground);
                FontMetrics fm = g2.getFontMetrics(textFont);
                FontMetrics fm2 = g2.getFontMetrics(subTextFont);
                int textWidth = fm.stringWidth(text);
                int subTextWidth = fm2.stringWidth(subText);
                g2.setFont(textFont);
                g2.setColor(Color.white);
                g2.drawString(text, (TOOL_TIP_WIDTH - textWidth)/2, 20);
                g2.setFont(subTextFont);
                g2.setColor(blue.brighter());
                g2.drawString(subText, (TOOL_TIP_WIDTH - subTextWidth)/2, 38);
                g2.setColor(Color.gray);
                g2.setStroke(new BasicStroke(3f));
                g2.drawRoundRect(0, 0, width-1, height-1, 12, 12);
                g2.dispose();
            }
        }

    }

    static class DayOfMonth extends ChartCategory<Integer> {
        private static Map<Integer, DayOfMonth> dayMap = new HashMap<Integer, DayOfMonth>();

        private DayOfMonth(String s, Integer day) {
            super(day.toString(), day);
        }

        static DayOfMonth getDay(final int day) {
            DayOfMonth d = dayMap.get(day);
            if (d == null) {
                Integer dm = day;
                d = new DayOfMonth(dm.toString(), dm);
                dayMap.put(dm, d);
            }
            return d;
        }
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new NotRaphaelDemo());
    }
}

