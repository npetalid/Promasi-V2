/*
 * @(#)WithImagesChartDemo.java 8/22/2009
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.PointShape;
import com.jidesoft.chart.annotation.AutoPositionedLabel;
import com.jidesoft.chart.annotation.ChartImage;
import com.jidesoft.chart.annotation.ChartLabel;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.RealPosition;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.chart.style.LabelStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.range.Range;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

@SuppressWarnings("serial")
public class WithImagesChartDemo extends AbstractDemo {
    private JPanel demoPanel;
    private Chart chart;

    private ChartCategory<Building> taipai, petronas, willis, jin_mao, empireState;
    private Range<Building> xRange;
    private final NumericRange yRange = new NumericRange(0, 2000);

    private JPanel createDemo() {
        taipai = new ChartCategory<Building>("Taipei", Building.TAIPEI);
        petronas = new ChartCategory<Building>("Petronas Towers", Building.PETRONAS_TOWERS);
        willis = new ChartCategory<Building>("Willis Tower", Building.WILLIS_TOWER);
        jin_mao = new ChartCategory<Building>("Jin Mao Building", Building.JIN_MAO_BUILDING);
        empireState = new ChartCategory<Building>("Empire State Building", Building.EMPIRE_STATE_BUILDING);
        
        xRange = new CategoryRange<Building>().add(taipai).add(petronas).add(willis).add(jin_mao).add(empireState);
        
        chart = new Chart();
        demoPanel = new JPanel();
        demoPanel.setPreferredSize(new Dimension(500, 500));

        final Axis xAxis = new Axis(xRange);
        final Axis yAxis = new Axis(yRange);
        yAxis.setLabel(new AutoPositionedLabel("Height (ft)", Color.white));
        chart.setChartBackground(new GradientPaint(0f, 0f, Color.blue, 0f, 800f, Color.green));
        chart.setPanelBackground(Color.black);
        chart.setLabelColor(Color.white);
        chart.setTickColor(Color.white);
        chart.setLabelColor(Color.white);
        chart.setBorder(new EmptyBorder(0, 5, 0, 0));
        ChartModel model = createModel();
        chart.addModel(model, new ChartStyle(Color.green, PointShape.CIRCLE, Color.magenta));
        chart.setXAxis(xAxis);
        xAxis.setTicksVisible(false);
        chart.setYAxis(yAxis);
        Font labelFont = UIManager.getFont("Label.font");
        chart.setTitle(new AutoPositionedLabel("World's Tallest Buildings", Color.yellow, labelFont.deriveFont(Font.BOLD, 16f)));
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        demoPanel.setLayout(new BorderLayout());

        demoPanel.add(chart);
        return demoPanel;
    }


    private ChartModel createModel() {
        DefaultChartModel model = new DefaultChartModel();
        // The heights given here are where we want to see the labels
        model.addAnnotation(createLabel(taipai, Building.TAIPEI.getHeight()));
        model.addAnnotation(createLabel(petronas, Building.PETRONAS_TOWERS.getHeight()));
        model.addAnnotation(createLabel(willis, Building.WILLIS_TOWER.getHeight()));
        model.addAnnotation(createLabel(jin_mao, Building.JIN_MAO_BUILDING.getHeight()));
        model.addAnnotation(createLabel(empireState, Building.EMPIRE_STATE_BUILDING.getHeight()));
        // Note that the positions of the images are given in user coordinates, not pixels
        model.addAnnotation(new ChartImage(0.6, 0, 1.3, Building.TAIPEI.getHeight(), Building.TAIPEI.getImage()));
        model.addAnnotation(new ChartImage(1.3, 0, 2.7, Building.PETRONAS_TOWERS.getHeight(), Building.PETRONAS_TOWERS.getImage()));
        model.addAnnotation(new ChartImage(2.7, 0, 3.5, Building.WILLIS_TOWER.getHeight(), Building.WILLIS_TOWER.getImage()));
        model.addAnnotation(new ChartImage(3.5, 0, 4.5, Building.JIN_MAO_BUILDING.getHeight(), Building.JIN_MAO_BUILDING.getImage()));
        model.addAnnotation(new ChartImage(4.5, 0, 5.5, Building.EMPIRE_STATE_BUILDING.getHeight(), Building.EMPIRE_STATE_BUILDING.getImage()));
        return model;
    }

    public ChartLabel createLabel(ChartCategory<?> cat, double height) {
        ChartLabel label = new ChartLabel(new RealPosition(cat.position()), new RealPosition(height), cat.getName());
        // We can raise the label slightly from the top of the tower
        label.setPixelOffset(new Point(0, -5));
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.setColor(Color.magenta);
        labelStyle.setRotation(-Math.PI / 8);
        label.setLabelStyle(labelStyle);
        return label;
    }

    @Override
    public String getDescription() {
        return "This example shows that you can add images to a chart. " +
                "The images have been scaled according to the heights of the buildings.";
    }

    public String getName() {
        return "With Images Chart";
    }

    @Override
    public String getDemoFolder() {
        return "R1.Charts";
    }

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = createDemo();
        }
        return demoPanel;
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new WithImagesChartDemo());
    }

}
