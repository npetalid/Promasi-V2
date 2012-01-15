/*
 * @(#)ScrollableBarChartDemo.java
 * 
 * 2002 - 2011 JIDE Software Incorporated. All rights reserved.
 * Copyright (c) 2005 - 2011 Catalysoft Limited. All rights reserved.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JScrollPane;

import com.jidesoft.chart.BarResizePolicy;
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.IntervalMarker;
import com.jidesoft.chart.LabelPlacement;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.axis.Axis;
import com.jidesoft.chart.axis.AxisComponent;
import com.jidesoft.chart.axis.CategoryAxis;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.model.ChartCategory;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.RaisedBarRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.NumericRange;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.utils.ColorUtils;

@SuppressWarnings("serial")
public class ScrollableBarChartDemo extends AbstractDemo {
  private static final int ENTITY_COUNT = 25;
  private Chart chart;
  private JScrollPane scrollPane;
  private Random rand = new Random(System.currentTimeMillis());
  
  public Component getDemoPanel() {
      chart = new Chart("Test");
      final List<Entity> entities = new ArrayList<Entity>();
      for (int i = 0; i < ENTITY_COUNT; ++i)
        entities.add(new Entity(i+1));

      List<Category<Entity>> catList = new ArrayList<Category<Entity>>();
      CategoryRange<Entity> catRange = new CategoryRange<Entity>();
      CategoryRange<Entity> catVisible = new CategoryRange<Entity>();
      for (int i = 0; i < entities.size(); ++i) {
        Category<Entity> c = new ChartCategory<Entity>(entities.get(i), catRange);
        if (i < 10)
          catVisible.add(c);
        catList.add(c);
        catRange.add(c);
      }

      DefaultChartModel reds = new DefaultChartModel("Red");
      DefaultChartModel greens = new DefaultChartModel("Green");
      DefaultChartModel blues = new DefaultChartModel("Blue");
      DefaultChartModel oranges = new DefaultChartModel("Orange");
      DefaultChartModel yellows = new DefaultChartModel("Yellow");
      for (int i = 0; i < entities.size(); ++i) {
        reds.addPoint(catList.get(i), entities.get(i).red);
        greens.addPoint(catList.get(i), entities.get(i).green);
        blues.addPoint(catList.get(i), entities.get(i).blue);
        oranges.addPoint(catList.get(i), entities.get(i).orange);
        yellows.addPoint(catList.get(i), entities.get(i).yellow);
      }

      chart.addModel(reds, createStyle(Color.red));
      chart.addModel(greens, createStyle(Color.green));
      chart.addModel(blues, createStyle(Color.blue));
      chart.addModel(oranges, createStyle(Color.orange));
      chart.addModel(yellows, createStyle(Color.yellow));

      Axis xAxis = new CategoryAxis<Entity>(catRange);
      xAxis.setTickLabelOffset(8);
      xAxis.setTickLabelRotation(Math.PI / 4);
      NumericAxis yAxis = new NumericAxis(new NumericRange(0D, 110D));

      chart.setXAxis(xAxis);
      chart.setYAxis(yAxis);
      chart.setBarResizePolicy(BarResizePolicy.RESIZE_OFF);
      chart.setBarGap(10);
      chart.setVerticalGridLinesVisible(false);
      chart.setBarRenderer(new RaisedBarRenderer(5));
      chart.setAnimateOnShow(false);
      chart.setShadowVisible(false);
      chart.setRolloverEnabled(true);
      Paint paint = JideSwingUtilities.getLinearGradientPaint(0f, 0f, 0f, 200f, new float[] {0.0f, 1.0f}, new Color[] {new Color(255,0,0,25), new Color(255,255,255,50)});
      IntervalMarker marker = new IntervalMarker(chart, Orientation.horizontal, 50.0, 100.0, paint);
      marker.setLabel("Critical Level");
      marker.setLabelPlacement(LabelPlacement.NORTH_WEST);
      marker.setLabelColor(Color.black);
      chart.addDrawable(marker);

      try {
        scrollPane = new JScrollPane(chart);
        scrollPane.setBorder(null);
        // Use an AxisComponent in the JScrollPane rather than an axis in the chart component
        yAxis.setVisible(false);
        AxisComponent yAxisView = new AxisComponent(chart, yAxis);
        scrollPane.setRowHeaderView(yAxisView);
        scrollPane.setPreferredSize(new Dimension(600, 500));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      return scrollPane;
  }
  
  private ChartStyle createStyle(Color color) {
      Color c = ColorUtils.getDerivedColor(color, 0.35f);
      ChartStyle style = new ChartStyle(c, false, false, true);
      style.setBarWidth(50);
      return style;
  }

  public String getName() {
      return "Scrollable Bar Chart Demo";
  }
  
  @Override
public String getDescription() {
      return "This demonstrates how to create a chart that scrolls horizontally while leaving the y axis in place";
  }

  public String getProduct() {
      return PRODUCT_NAME_CHARTS;
  }
  
  @Override
  public int getAttributes() {
      return ATTRIBUTE_BETA;
  }


class Entity {
      String name;
      double red, green, blue, orange, yellow;

      public Entity(int idx) {
        name = "Entity " + idx;
        red = rand.nextDouble() * 20;
        green = rand.nextDouble() * 30;
        blue = rand.nextDouble() * 20;
        orange = rand.nextDouble() * 20;
        yellow = rand.nextDouble() * 10;
      }

      @Override
      public String toString() {
        return name;
      }
    }
  
  public static void main(String[] args) {
      LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
      showAsFrame(new ScrollableBarChartDemo());
  }

}