/*
 * @(#)ClockDemo.java
 *
 * 2002 - 2011 JIDE Software Incorporated. All rights reserved.
 * Copyright (c) 2005 - 2011 Catalysoft Limited. All rights reserved.
 */

import com.jidesoft.chart.util.ChartUtils;
import com.jidesoft.gauge.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClockDemo extends AbstractDemo implements AnimatedDemo {
    private JPanel demoPanel, optionsPanel;
    private Clock clock;
    private DialFrame dialFrame;
    private static final Color w = new Color(250, 250, 250);
    private static final Color lg = new Color(175, 175, 180);
    private static final Color g = new Color(170, 170, 175);
    private static final Color dg = new Color(140, 140, 145);
    private DialLabel upperLabel, lowerLabel;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            clock = new Clock();
            Border outerBorder = BorderFactory.createEtchedBorder();
            Border innerBorder = new EmptyBorder(20,20,20,20);
            clock.setBorder(new CompoundBorder(outerBorder, innerBorder));
            clock.setDialBackground(JideSwingUtilities.getLinearGradientPaint(0f,0f,0f,450f,new float[] {0f, 1f},
                            new Color[] {Color.white, Color.lightGray}));
            clock.setResizeFonts(true);
            clock.setShadowVisible(false);
            demoPanel.add(clock, BorderLayout.CENTER);
            demoPanel.setPreferredSize(new Dimension(450, 450));
            dialFrame = new DialFrame();
            clock.setFrame(dialFrame);
            upperLabel = new DialLabel(clock, 0.4, 90, "JIDE CHARTS");
            upperLabel.setColor(Color.gray);
            clock.addDrawable(upperLabel);
            lowerLabel = new DialLabel(clock, 0.4, -90, "www.jidesoft.com");
            lowerLabel.setColor(Color.gray);
            clock.addDrawable(lowerLabel);
        }
        return demoPanel;
    }

    public String getName() {
        return "Clock Demo";
    }

    public String getDescription() {
        return "The Clock Demo shows the use of multiple needles on one dial, as well as demonstrating several different "+
                " fill techniques to customize the appearance.";
    }

    @Override
    public Component getOptionsPanel() {
        if (optionsPanel == null) {
            optionsPanel = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            optionsPanel.setLayout(layout);
            JLabel frameWidthLabel = new JLabel("Frame Width");
            final JSlider frameWidthSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
            optionsPanel.add(frameWidthLabel, constraints(0, 0));
            optionsPanel.add(frameWidthSlider, constraints(1, 0));
            frameWidthSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    int value = frameWidthSlider.getValue();
                    double frameWidth = 0.3 * value / 10;
                    dialFrame.setFrameWidth(frameWidth);
                    clock.update();
                }
            });
            frameWidthSlider.setValue(5);
            JLabel appearanceLabel = new JLabel("Appearance");
            JPanel appearancePanel = new JPanel();
            appearancePanel.setLayout(new BoxLayout(appearancePanel, BoxLayout.Y_AXIS));
            JRadioButton simpleAppearance = new JRadioButton("Simple");
            JRadioButton texturedAppearance = new JRadioButton("Textured");
            JRadioButton lightedAppearance = new JRadioButton("Light Effect");
            JRadioButton metallicAppearance = new JRadioButton("Metallic");
            appearancePanel.add(simpleAppearance);
            appearancePanel.add(texturedAppearance);
            appearancePanel.add(lightedAppearance);
            appearancePanel.add(metallicAppearance);
            ButtonGroup appearanceGroup = new ButtonGroup();
            appearanceGroup.add(simpleAppearance);
            appearanceGroup.add(texturedAppearance);
            appearanceGroup.add(lightedAppearance);
            appearanceGroup.add(metallicAppearance);
            simpleAppearance.setSelected(true);
            optionsPanel.add(appearanceLabel, constraints(0, 1));
            optionsPanel.add(appearancePanel, constraints(1, 1));
            simpleAppearance.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialFrame.setFill(Color.black);
                    clock.setShadowVisible(false);
                    clock.setFacePaint(null);
                    clock.setFaceColor(w);
                    upperLabel.setColor(Color.lightGray);
                    lowerLabel.setColor(Color.lightGray);
                    clock.update();
                }
            });
            texturedAppearance.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Paint fp = ChartUtils.createTexture(clock, "TextureOrange.png");
                    clock.setFacePaint(fp);
                    clock.setShadowVisible(false);
                    Paint framePaint = ChartUtils.createTexture(clock, "TextureGreen.png");
                    clock.getFrame().setFill(framePaint);
                    upperLabel.setColor(Color.black);
                    lowerLabel.setColor(Color.black);
                    clock.update();
                }
            });
            lightedAppearance.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Paint facePaint = new DialRadialPaint(clock,  new float[] {0.0f, 0.05f, 0.9f},
                            new Color[] {Color.white, Color.white, Color.lightGray}, 0.3f, 135f);
                    clock.setFacePaint(facePaint);
                    clock.setShadowVisible(true);
                    Paint framePaint = new DialLinearPaint(clock, 1f, -90f, 1f, 135f, new float[] {0.0f, 0.5f, 1.0f}, new Color[] {Color.black, Color.darkGray, Color.gray}, DialLinearPaint.REFLECT);
                    dialFrame.setFill(framePaint);
                    upperLabel.setColor(Color.gray);
                    lowerLabel.setColor(Color.gray);
                    clock.update();
                }
            });
            metallicAppearance.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    float[] angles = {15f, 30f, 45f, 75f, 105f, 120f, 135f, 165f, 190f, 210f, 230f, 255f, 280f, 300, 320f, 345f};
                    final Color[] colors = {lg, w, lg, dg, lg, w, lg, dg, lg, w, lg, dg, lg, w, lg, dg};
                    Paint dialPaint = new DialConicalPaint(clock, angles, colors);
                    clock.setFacePaint(dialPaint);
                    clock.setShadowVisible(true);
                    Color b = Color.black;
                    Color wh = Color.white;
                    final float[] angles2 = {0f, 45f, 75f, 90f, 115f, 135f, 170f, 210f, 270f, 330f};
                    final Color[] colors2 = {b, g, b, wh, b, g, b, g, b, g};
                    Paint framePaint = new DialConicalPaint(clock, angles2, colors2);
                    dialFrame.setFill(framePaint);
                    upperLabel.setColor(Color.darkGray);
                    lowerLabel.setColor(Color.darkGray);
                    clock.update();
                }
            });
            final JCheckBox secondHand = new JCheckBox();
            secondHand.setSelected(true);
            optionsPanel.add(new JLabel("Show Seconds"), constraints(0, 2));
            optionsPanel.add(secondHand, constraints(1, 2));
            secondHand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    NeedleStyle secondStyle = clock.getNeedleStyle("seconds");
                    if (secondHand.isSelected()) {
                        secondStyle.setVisible(true);
                        clock.getPivot().setVisible(true);
                    } else {
                        secondStyle.setVisible(false);
                        clock.getPivot().setVisible(false);
                    }
                    clock.repaint();
                }
            });
            final JComboBox labelOrientation = new JComboBox(DialLabelOrientation.values());
            optionsPanel.add(new JLabel("Label Orientation"), constraints(0,3));
            optionsPanel.add(labelOrientation, constraints(1,3));
            labelOrientation.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    DialLabelOrientation orientation = (DialLabelOrientation) labelOrientation.getSelectedItem();
                    clock.getAxis().setLabelOrientation(orientation);
                    // We need to call update whenever the face of the dial changes
                    clock.update();
                }
            });
            labelOrientation.setSelectedItem(DialLabelOrientation.UPRIGHT);
        }
        return optionsPanel;
    }

    public void startAnimation() {
        clock.setRunning(true);
    }

    public void stopAnimation() {
        clock.setRunning(false);
    }

    // Shorthand for creating GridBagConstraints
    private GridBagConstraints constraints(int x, int y) {
        return new GridBagConstraints(x, y, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2), 0, 0);
    }


    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new ClockDemo());
    }
}
