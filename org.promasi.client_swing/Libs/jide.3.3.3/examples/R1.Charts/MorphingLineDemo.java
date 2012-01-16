/*
 * @(#)MorphingLineDemo.java
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jidesoft.chart.Chart;
import com.jidesoft.chart.axis.AxisPlacement;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.ModelMorpher;
import com.jidesoft.chart.render.SmoothLineRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;


@SuppressWarnings("serial")
public class MorphingLineDemo extends AbstractDemo {
    private JPanel demoPanel;
    private Chart chart;
    private JPanel optionsPanel;
    private ChartStyle style;
    private ChartModel activeModel;
    private final double min = -11;
    private final double max = 11;
    private ModelMorpher morpher;
    
    public MorphingLineDemo() {
        
    }

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            chart = new Chart();
            morpher = new ModelMorpher(chart);
            morpher.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (ModelMorpher.PROPERTY_MORPH_ENDED.equals(evt.getPropertyName())) {
                        chart.addModel(activeModel, style);
                    }
                }
            });
            NumericAxis xAxis = new NumericAxis(min, max);
            xAxis.setLabel("x");
            NumericAxis yAxis = new NumericAxis(min, max);
            yAxis.setLabel("y");
            xAxis.setPlacement(AxisPlacement.CENTER);
            yAxis.setPlacement(AxisPlacement.CENTER);
            chart.setXAxis(xAxis);
            chart.setYAxis(yAxis);
            SmoothLineRenderer renderer = new SmoothLineRenderer(chart);
            chart.setLineRenderer(renderer);
            demoPanel.add(chart, BorderLayout.CENTER);
            demoPanel.setPreferredSize(new Dimension(600, 500));
            style = new ChartStyle(Color.blue, false, true);
            style.setLineWidth(5);
            activeModel = createModel(new Line("y = 5", 0, 5), min, max);
            chart.addModel(activeModel, style);
        }
        return demoPanel;
    }
    
    private ChartModel createModel(Function f, double from, double to) {
        DefaultChartModel model = new DefaultChartModel(f.getName());
        int np = f.getNumPoints();
        for (int i = 0; i < np; i++) {
            double x = from + i * (to - from)/ (np-1);
            double y = f.eval(x);
            model.addPoint(x, y);
            //logger.info(String.format("Adding point (%f, %f)", x, y));
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
    public Component getOptionsPanel() {
        if (optionsPanel == null) {
            // Create the functions of interest
            Function[] fns = new Function[] {
                    new Line("y = 5", 0, 5), 
                    new Line("y = x", 1, 0),
                    new Line("y = x + 5", 1, 5),
                    new Line("y = 5 - x", -1, 5),
                    new Line("y = x / 10", 0.1, 0),
                    new Line("y = -x / 10", -0.1, 0),
                    new Sine("y = sin(x)", 1, 1),
                    new Sine("y = sin(2x)", 1, 2),
                    new Sine("y = 5sin(x)", 5, 1),
                    new Sine("y = 5sin(2x)", 5, 2),
                    new Cosine("y = cos(x)", 1, 1),
                    new Cosine("y = cos(2x)", 1, 2),
                    new Cosine("y = 5cos(x)", 5, 1),
                    new Cosine("y = 5cos(2x)", 5, 2),
                    new PolynomialTerm("y = x*x", 1, 2),
                    new PolynomialTerm("y = - x*x", -1, 2)
                    };
            GridLayout layout = new GridLayout((fns.length+1)/2, 2);
            optionsPanel = new JPanel(layout);
            for (Function f : fns) {
                final Function fn = f;
                JButton b = new JButton(f.getName());
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        switchToModel(createModel(fn, min, max));
                    }
                });
                optionsPanel.add(b);
            }
        }
        return optionsPanel;
    }

    @Override
    public String getDescription() {
        return "This demo shows how you can create smooth transitions from one chart display to another.";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }
    
    public String getName() {
        return "Morphing Line Demo";
    }

    interface Function {
        /**
         * Every good function deserves a name
         */
        public String getName();
        
        /**
         * Number of points to generate in the chart model
         */
        public int getNumPoints();
        /**
         * Evaluate an input value to return an output value
         */
        public double eval(double x);
    }
    
    /**
     * A function to compute f(x) = m * x + c
     */
    class Line implements Function {
        private double m = 1.0;
        private double c = 0.0;
        private String name = null;
        
        public Line() {
        }
        
        public Line(String name, double m, double c) {
            this.name = name;
            this.m = m;
            this.c = c;
        }

        public double eval(double x) {
            return m * x + c;
        }
        
        public String getName() {
            if (name != null) {
                return name;
            } else {
                return String.format("y = %.1fx + %.1f", m, c);
            }
        }

        public int getNumPoints() {
            return 5;
        }
        
    }
    
    /**
     * A function to compute f(x) = m * x^n
     */
    class PolynomialTerm implements Function {
        private double m = 1.0;
        private double n = 0.0;
        private String name = null;
        
        public PolynomialTerm() {
        }
        
        public PolynomialTerm(String name, double m, double n) {
            this.name = name;
            this.m = m;
            this.n = n;
        }

        public double eval(double x) {
            return m * Math.pow(x, n);
        }
        
        public String getName() {
            if (name != null) {
                return name;
            } else {
                return String.format("y = %.1f * x^%.1f", m, n);
            }
        }

        public int getNumPoints() {
            return 20;
        }
        
    }
    
    /**
     * A function to compute f(x) = k * sin (m*x)
     */
    class Sine implements Function {
        private double k = 1.0;
        private double m = 1.0;
        private String name = null;
        
        public Sine() {}
        
        public Sine(String name, double k, double m) {
            this.name = name;
            this.k = k;
            this.m = m;
            }
        
        public double eval(double x) {
           return k * Math.sin(m*x);
        }
        
        public String getName() {
            if (name != null) {
                return name;
            } else {
                return String.format("y = %.1f * sin (%.1fc * x)", k, m);
            }
        }
        
        public int getNumPoints() {
            return 80;
        }
    }
    
    /**
     * A function to compute f(x) = k * cos (m*x)
     */
    class Cosine implements Function {
        private double k = 1.0;
        private double m = 1.0;
        private String name = null;
        
        public Cosine() {
            
        }
        
        public Cosine(String name, double k, double m) {
            this.name = name;
            this.k = k;
            this.m = m;
        }
        
        public double eval(double x) {
            return k * Math.cos(m * x);
        }
        
        public String getName() {
            if (name != null) {
                return name;
            } else {
                return String.format("y = %.1f * cos (%.1fc * x)", k, m);
            }
        }
        
        public int getNumPoints() {
            return 80;
        }
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new MorphingLineDemo());
    }

}
