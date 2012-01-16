/*
 * @(#)MemoryDialDemo.java
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Drawable;
import com.jidesoft.gauge.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.Positionable;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static java.awt.Color.*;

public class MemoryDialDemo extends AbstractDemo implements AnimatedDemo {
    private static final String NEEDLE_NAME = "value";
    private JPanel demoPanel, optionsPanel;
    private Dial dial;
    private ActionListener memoryListener = new MemoryListener();
    private boolean running;
    private enum Appearance {colorful, metallic};
    private Appearance appearance = Appearance.colorful;
    private Color br, d;
    private DialIndicator redIndicator, greenIndicator;
    private final float indicatorSize = 0.035f;
    private final float indicatorRadius = 0.6f;
    private final float redIndicatorAngle = 60f;
    private final float greenIndicatorAngle = 120f;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            demoPanel.setPreferredSize(new Dimension(600, 500));
            br = new Color(230, 230, 180);
            d = new Color(150, 150, 100);
            dial = configureDial(null, appearance);
            demoPanel.add(dial, BorderLayout.CENTER);
            Font titleFont = UIManager.getFont("Label.font").deriveFont(18f);
            JLabel title = new JLabel("Percent Memory Used", JLabel.CENTER);
            title.setFont(titleFont);
            demoPanel.add(title, BorderLayout.NORTH);

            JButton gcButton = new JButton("Collect Garbage");
            JPanel controlPanel = new JPanel();
            controlPanel.add(gcButton);
            demoPanel.add(controlPanel, BorderLayout.SOUTH);
            gcButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.gc();
                }
            });
            startAnimation();
        }
        return demoPanel;
    }

    @Override
    public Component getOptionsPanel() {
        if (optionsPanel == null) {
            optionsPanel = new JPanel();
            JPanel buttonPanel = new JPanel(new GridLayout(2,1));
            JRadioButton colorfulButton = new JRadioButton("Colorful");
            colorfulButton.setSelected(true); // selected by default
            JRadioButton metallicButton = new JRadioButton("Metallic");
            ButtonGroup group = new ButtonGroup();
            group.add(colorfulButton);
            group.add(metallicButton);
            buttonPanel.add(colorfulButton);
            buttonPanel.add(metallicButton);
            colorfulButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    appearance = Appearance.colorful;
                    dial = configureDial(dial, appearance);
                }
            });
            metallicButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    appearance = Appearance.metallic;
                    dial = configureDial(dial, appearance);
                }
            });
            optionsPanel.add(buttonPanel);
        }
        return optionsPanel;
    }


    private Dial configureDial(Dial dial, Appearance appearance) {
        Double value = null;
        if (dial == null) {
            dial = new Dial();
            // With animation, when we do a garbage collection and the memory usage suddenly drops the needle
            // will move around to the new value rather than jumping there suddenly
            dial.setAnimateOnChange(true);
            dial.setNeedleAnimationPeriod(100);
            dial.setStartAngle(0);
            dial.setEndAngle(180);
            dial.setBorder(new EmptyBorder(20, 20, 20, 20));

            GaugeModel animatedModel = dial.getAnimatedModel();
            animatedModel.addGaugeModelListener(new GaugeModelListener() {
                public void gaugeChanged(GaugeModelEvent e) {
                    Positionable pos = e.getPositionable();
                    if (redIndicator != null) {
                        redIndicator.setActive(pos.position() > 70);
                    }
                    if (greenIndicator != null) {
                        greenIndicator.setActive(pos.position() < 30);
                    }
                }
            });

        } else {
            // Remember the current value of the needle so that we can reinstate it immediately after the UI change
            value = dial.getValue(NEEDLE_NAME);
            dial.removeDrawables();
            dial.removeNeedles();
        }

        if (appearance == Appearance.colorful) {
            Paint radialPaint = new DialRadialPaint(dial, new float[] {0f, 0.35f, 1f}, new Color[] {br, br, d});
            dial.setFacePaint(radialPaint);
            dial.setShadowVisible(false);
        } else {
            dial.setShadowVisible(false);
            Paint conicalPaint = new DialConicalPaint(dial,
                    new float[] {0f, 60f, 100f, 160f, 220f},
                    new Color[] {lightGray, gray, lightGray, gray, lightGray});
            dial.setFacePaint(conicalPaint);
        }
        DialFrame frame = createFrame(appearance);
        dial.setFrame(frame);
        dial.setAxis(createAxis(dial, appearance));
        addMarkers(dial, appearance);
        addNeedle(dial, appearance);
        addPivot(dial, appearance);
        if (value != null) {
            dial.setValue(NEEDLE_NAME, value);
        }
        return dial;
    }

    private void addPivot(Dial dial, Appearance appearance) {
        if (appearance == Appearance.colorful) {
            DialRadialPaint pivot2Paint = new DialRadialPaint(dial, new float[] {0.15f, 0.2f}, new Color[] {new Color(125, 125, 50), br});
            Pivot pivot2 = new Pivot(dial, 0.2, pivot2Paint);
            dial.addDrawable(pivot2);
            Pivot pivot = new Pivot(dial, 0.15, new Color(75, 75, 0));
            dial.addDrawable(pivot);
            Pivot innerPivot = new Pivot(dial, 0.1, new Color(100, 100, 0));
            innerPivot.setOutlineWidth(5);
            innerPivot.setZOrder(100);
            dial.addDrawable(innerPivot);
        } else { // metallic
            DialConicalPaint pivotPaint = new DialConicalPaint(dial, new float[] {0f, 20f, 50f, 70f, 130f, 180f},
                    new Color[] {gray, lightGray, gray, darkGray, lightGray, gray});
            Pivot pivot = new Pivot(dial, 0.1, 0, 180, pivotPaint);
            pivot.setOutlineColor(Color.darkGray);
            pivot.setOutlineWidth(2);
            pivot.setZOrder(100);
            dial.addDrawable(pivot);
            dial.addDrawable(createFastener(dial, 0.025f, 0.65f, 5f));
            dial.addDrawable(createFastener(dial, 0.025f, 0.65f, 175f));
            dial.addDrawable(createFastener(dial, 0.025f, 0.6f, 90f));

            redIndicator = new DialIndicator(dial, indicatorSize, indicatorRadius, redIndicatorAngle, new Color(115, 20, 0));
            dial.addDrawable(redIndicator);
            greenIndicator = new DialIndicator(dial, indicatorSize, indicatorRadius, greenIndicatorAngle, new Color(20, 115, 0));
            dial.addDrawable(greenIndicator);
        }
    }

    private Drawable createFastener(Dial dial, float size, float radius, float angle) {
        DialLinearPaint paint = new DialLinearPaint(dial, 0.05f, 135f, 0.05f, 315f,
                new float[] {0f, 1f},
                new Color[] {Color.lightGray, Color.darkGray});
        paint.setOffsetAngle(angle);
        paint.setOffsetRadius(radius);
        Fastener fastener = new Fastener(dial, size, paint);
        fastener.setOffsetAngle(angle);
        fastener.setOffsetRadius(radius);
        return fastener;
    }

    private void addNeedle(Dial dial, Appearance appearance) {
        NeedleStyle style = new NeedleStyle();
        if (appearance == Appearance.colorful) {
            BasicStroke stroke = new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            style.setOutlineStroke(stroke);
            style.setOutlineColor(Color.black);
            DialRadialPaint needlePaint = new DialRadialPaint(dial,
                    new float[] {0.2f, 0.7f},
                    new Color[] {new Color(100, 100, 0), new Color(100, 100, 0, 100)});
            style.setFillPaint(needlePaint);
            style.setHeadLength(0.8);
            style.setHeadShape(NeedleEndShape.TRIANGULAR);
            style.setHeadWidth(0.1);
            style.setBaseWidth(0.1);
            style.setTailLength(0);
            style.setTailWidth(0.1);
            style.setTailShape(NeedleEndShape.ROUND);
        } else {
            BasicStroke stroke = new BasicStroke(1f);
            style.setOutlineStroke(stroke);
            style.setOutlineColor(Color.black);
            style.setFillPaint(Color.darkGray);
            style.setHeadLength(0.9);
            style.setHeadWidth(0.01);
            style.setHeadShape(NeedleEndShape.ROUND);
            style.setBaseWidth(0.05);
            style.setTailLength(0);
            style.setTailWidth(0.05);
            style.setTailShape(NeedleEndShape.ROUND);
        }
        dial.addNeedle(NEEDLE_NAME, style);
    }

    private DialAxis createAxis(Dial dial, Appearance appearance) {
        DialAxis axis = new DialAxis();
        axis.setBorderColor(Color.black);
        axis.setBorderStroke(new BasicStroke(2));
        Paint dialPaint;
        if (appearance == Appearance.colorful) {
            dialPaint = new DialConicalPaint(dial, new float[] {180, 90, 0}, new Color[] {Color.green, Color.orange, Color.red});
            axis.setStartAngle(180);
            axis.setEndAngle(0);
            axis.setLabelRadius(0.67);
            axis.setTickLabelColor(blue);
        } else {
            float innerRadius = 0.85f;
            float outerRadius = 0.95f;
            dialPaint = new DialRadialPaint(dial, new float[] {innerRadius, outerRadius}, new Color[] {Color.darkGray, Color.white});
            axis.setStartAngle(175);
            axis.setEndAngle(5);
            axis.setLabelRadius(0.8);
            axis.setTickLabelColor(black);
            axis.setInnerRadius(innerRadius);
            axis.setOuterRadius(outerRadius);
        }
        axis.setFill(dialPaint);
        axis.setLabelOrientation(DialLabelOrientation.TANGENTIAL_CLOCKWISE);
        axis.setTickLabelFont(UIManager.getFont("Label.font").deriveFont(20f));
        return axis;
    }

    private DialFrame createFrame(Appearance appearance) {
        DialFrame frame = new DialFrame();
        if (appearance == Appearance.colorful) {
            frame.setMidChordRadius(0.2);
            frame.setArcEndAngle(11);
        } else {
            frame.setMidChordRadius(0);
            frame.setArcEndAngle(0);
        }
        return frame;
    }

    private void addMarkers(Dial dial, Appearance appearance) {
        if (appearance == Appearance.colorful) {
            dial.addDrawable(createMarker(dial, 0.0, 30.0, 0.75, 0.84, Color.cyan));
            dial.addDrawable(createMarker(dial, 30.0, 70.0, 0.75, 0.84, Color.pink));
            dial.addDrawable(createMarker(dial, 70.0, 100.0, 0.75, 0.84, Color.magenta));
        }
    }

    private void updateValue() {
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long used = total - free;
        double percentUsed = 100.0 * used / total;
        dial.setValue(NEEDLE_NAME, percentUsed);
        Timer timer = new Timer(1000, memoryListener);
        timer.setRepeats(false);
        timer.start();
    }

    public void startAnimation() {
        running = true;
        updateValue();
    }

    public void stopAnimation() {
        running = false;
    }

    class MemoryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (running) {
                updateValue();
            }
        }
    }

    public String getName() {
        return "Memory Dial Demo";
    }

    @Override
    public String getDescription() {
        return "This demo is a semi-circular dial that displays the percent of memory used in the JVM. "+
                "It uses the optional animation feature so that when the memory usage changes the needle "+
                "moves around to the new value rather than suddenly jumping there. "+
                "It also shows some gradient fill techniques, for the axis, the needle and the outer pivot.";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    private DialIntervalMarker createMarker(Dial dial, double from, double to, double innerRadius, double outerRadius, Color fill) {
        DialIntervalMarker marker = new DialIntervalMarker(dial, from, to, innerRadius, outerRadius, fill);
        marker.setBorderColor(Color.black);
        marker.setBorderStroke(new BasicStroke(2));
        return marker;
    }

    class Fastener extends Pivot {
        private double rotation;

        public Fastener(Dial dial, double radius, Paint fill) {
            super(dial, radius, fill);
            rotation = Math.random() * Math.PI/2 - Math.PI/4; // between -45 and +45 degrees
        }

        @Override
        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            Point2D center = getCenter();
            double dialRadius = dial.getRadius();
            float pivotRadius = (float) (dialRadius * getOuterRadius());
            // Paint an imprint/shadow underneath the screw head
            Paint imprint = JideSwingUtilities.getRadialGradientPaint(center, 1.5f * pivotRadius, new float[] {0.75f, 1f}, new Color[] {Color.darkGray, new Color(192, 192, 192, 0)});
            Ellipse2D imprintShape = new Ellipse2D.Double(center.getX()-2*pivotRadius, center.getY()-2*pivotRadius, pivotRadius*4, pivotRadius*4);
            g2d.setPaint(imprint);
            g2d.fill(imprintShape);
            // Now use the superclass to draw the main shape of the screw head
            super.draw(g);
            // Now embellish by drawing the cross
            double breadth = 2 * pivotRadius * 0.20;
            double length = 2 * pivotRadius * 0.65;
            Color dd = Color.darkGray.darker();
            LinearGradientPaint hPaint = new LinearGradientPaint((float) center.getX(), (float)(center.getY()-breadth/2),
                    (float) (center.getX()), (float) (center.getY()+breadth/2),
                    new float[] {0f, 1f},
                    new Color[] {dd, Color.darkGray});
            g2d.setPaint(hPaint);
            g2d.translate(center.getX(), center.getY());
            g2d.rotate(rotation);
            Rectangle2D h = new Rectangle2D.Double(-length/2, - breadth/2, length, breadth);
            g2d.fill(h);
            g2d.rotate(-rotation);
            g2d.translate(-center.getX(), -center.getY());

            LinearGradientPaint vPaint = new LinearGradientPaint((float) center.getX(), (float)(center.getY()-length/2),
                    (float) (center.getX()), (float) (center.getY()+length/2),
                    new float[] {0f, 1f},
                    new Color[] {dd, Color.darkGray});
            g2d.setPaint(vPaint);
            g2d.translate(center.getX(), center.getY());
            g2d.rotate(rotation);
            Rectangle2D v = new Rectangle2D.Double(-breadth/2, - length/2, breadth, length);
            g2d.fill(v);
            g2d.rotate(-rotation);
            g2d.translate(-center.getX(), -center.getY());

            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new MemoryDialDemo());
    }
}
