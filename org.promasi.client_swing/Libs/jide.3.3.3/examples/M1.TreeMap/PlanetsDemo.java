/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.treemap.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.MalformedURLException;
import java.net.URL;

public class PlanetsDemo extends AbstractDemo {
    public TreeMap _treeMap;

    public String getName() {
        return "TreeMap Demo (Planets)";
    }

    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    public String getProduct() {
        return PRODUCT_NAME_TREEMAP;
    }

    public Component getDemoPanel() {
        TableModel tableModel = new PlanetsTableModel();

        _treeMap = new TreeMap(tableModel);

        _treeMap.setGroupBy(1);
        _treeMap.setLabels();
        _treeMap.setBackground(0);
        _treeMap.setSize(2);
        _treeMap.setColorByName("Color code");

        _treeMap.setAlgorithm(AlgorithmFactory.CIRCULAR);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderFont(new Font("Tahoma", Font.BOLD, 16));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabelingFont(new Font("Tahoma", Font.ITALIC, 18));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabelingVerticalAlignment(SwingConstants.CENTER);

        _treeMap.getModel().getSettings().setRendering(RenderingFactory.CUSHION);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabeling(LabelingFactory.SURROUND);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(_treeMap, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(800, 800));

        return panel;
    }

    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, BoxLayout.Y_AXIS, 2));

        panel.add(new JLabel("Group By:"));
        panel.add(new JComboBox(_treeMap.getModel().getGroupByModel()));
        panel.add(Box.createVerticalStrut(4));

        panel.add(new JLabel("Label:"));
        panel.add(new JComboBox(_treeMap.getModel().getLabelModel()));
        panel.add(Box.createVerticalStrut(4));

        panel.add(new JLabel("Background:"));
        panel.add(new JComboBox(_treeMap.getModel().getBackgroundModel()));
        panel.add(Box.createVerticalStrut(4));

        panel.add(new JLabel("Size:"));
        panel.add(new JComboBox(_treeMap.getModel().getSizeModel()));
        panel.add(Box.createVerticalStrut(4));

        panel.add(new JLabel("Height:"));
        panel.add(new JComboBox(_treeMap.getModel().getHeightModel()));
        panel.add(Box.createVerticalStrut(4));

        panel.add(new JLabel("Color:"));
        panel.add(new JComboBox(_treeMap.getModel().getColorModel()));
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Algorithm> algorithms = AlgorithmFactory.getInstance().getAlgorithms();
        JComboBox algorithmComboBox = new JComboBox(algorithms.toArray(new Algorithm[algorithms.size()]));
        algorithmComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setAlgorithm((Algorithm) e.getItem());
                }
            }
        });
        algorithmComboBox.setSelectedItem(_treeMap.getModel().getSettings().getDefaultFieldSettings().getAlgorithm());

        panel.add(new JLabel("Algorithm:"));
        panel.add(algorithmComboBox);
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Aggregation> aggregations = AggregationFactory.getInstance().getAggregations();
        JComboBox aggregationComboBox = new JComboBox(aggregations.toArray(new Aggregation[aggregations.size()]));
        aggregationComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setAggregation((Aggregation) e.getItem());
                }
            }
        });
        aggregationComboBox.setSelectedItem(_treeMap.getModel().getSettings().getDefaultFieldSettings().getAggregation());

        panel.add(new JLabel("Aggregation:"));
        panel.add(aggregationComboBox);
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Scale> scales = ScaleFactory.getInstance().getScales();
        JComboBox scaleComboBox = new JComboBox(scales.toArray(new Scale[scales.size()]));
        scaleComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setScale((Scale) e.getItem());
                }
            }
        });
        scaleComboBox.setSelectedItem(_treeMap.getModel().getSettings().getDefaultFieldSettings().getScale());

        panel.add(new JLabel("Scale:"));
        panel.add(scaleComboBox);
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Nesting> nestings = NestingFactory.getInstance().getNestings();
        JComboBox nestingComboBox = new JComboBox(nestings.toArray(new Nesting[nestings.size()]));
        nestingComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setNesting((Nesting) e.getItem());
                }
            }
        });
        nestingComboBox.setSelectedItem(_treeMap.getModel().getSettings().getDefaultFieldSettings().getNesting());

        panel.add(new JLabel("Nesting:"));
        panel.add(nestingComboBox);
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Ordering> orderings = OrderingFactory.getInstance().getOrderings();
        JComboBox orderingComboBox = new JComboBox(orderings.toArray(new Ordering[orderings.size()]));
        orderingComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setOrdering((Ordering) e.getItem());
                }
            }
        });
        orderingComboBox.setSelectedItem(_treeMap.getModel().getSettings().getDefaultFieldSettings().getOrdering());

        panel.add(new JLabel("Ordering:"));
        panel.add(orderingComboBox);
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Depth> depths = DepthFactory.getInstance().getDepths();
        JComboBox depthComboBox = new JComboBox(depths.toArray(new Depth[depths.size()]));
        depthComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setDepth((Depth) e.getItem());
                }
            }
        });
        depthComboBox.setSelectedItem(_treeMap.getModel().getSettings().getDepth());

        panel.add(new JLabel("Depth:"));
        panel.add(depthComboBox);
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Labeling> labelings = LabelingFactory.getInstance().getLabelings();
        JComboBox labelingComboBox = new JComboBox(labelings.toArray(new Labeling[labelings.size()]));
        labelingComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setLabeling((Labeling) e.getItem());
                }
            }
        });
        labelingComboBox.setSelectedItem(_treeMap.getModel().getSettings().getDefaultFieldSettings().getLabeling());

        panel.add(new JLabel("Labeling:"));
        panel.add(labelingComboBox);
        panel.add(Box.createVerticalStrut(4));

        java.util.List<Rendering> renderings = RenderingFactory.getInstance().getRenderings();
        JComboBox renderingComboBox = new JComboBox(renderings.toArray(new Rendering[renderings.size()]));
        renderingComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _treeMap.setRendering((Rendering) e.getItem());
                }
            }
        });
        renderingComboBox.setSelectedItem(_treeMap.getModel().getSettings().getRendering());

        panel.add(new JLabel("Rendering:"));
        panel.add(renderingComboBox);
        panel.add(Box.createVerticalStrut(4));

        return panel;
    }

    public PlanetsDemo() {
    }

    static public void main(String[] s) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new PlanetsDemo());
    }

    static String[] PLANET_COLUMNS = new String[]{"Planet", "Region", "Spherical area", "Radius in km", "Mass relative to Earth", "Density g/cm^3", "Escape Vel. km/s", "Rotation days", "Year", "Distance from Sun km", "Rotational Inclination", "Inclination to ecliptic", "Albedo", "Color code", "Color name", "Wikipedia article", "Go to..."};
    static Class[] PLANET_CLASSES = new Class[]{String.class, String.class, Double.class, Integer.class, Double.class, Float.class, Float.class, Double.class, Double.class, Long.class, Float.class, Float.class, Float.class, Color.class, String.class, URL.class, Action.class};

    static Object[][] PLANETS = new Object[][]{
            new Object[]{"Mercury", "Inner Solar System", computeSpericalArea(2439), 2439, 0.0558, 5.43, 4.3, 59.6462, 0.24085, 57900000L, 0, 7, 0.106, new Color(0x666666), "Grey", createURL("http://en.wikipedia.org/wiki/Mercury_(planet)"), createAction("Mercury")},
            new Object[]{"Venus", "Inner Solar System", computeSpericalArea(6052), 6052, 0.815, 5.24, 10.3, -243.01, 0.6, 108200000L, 177.3, 3.39, 0.65, new Color(0xfff2b0), "Yellowish-white", createURL("http://en.wikipedia.org/wiki/Venus"), createAction("Venus")},
            new Object[]{"Earth", "Inner Solar System", computeSpericalArea(6378), 6378, 1, 5.52, 11.2, 0.99726968, 1, 149600000L, 23.45, Double.NaN, 0.367, new Color(0x0088b9), "Light blue", createURL("http://en.wikipedia.org/wiki/Earth"), createAction("Earth")},
            new Object[]{"Mars", "Inner Solar System", computeSpericalArea(3398), 3398, 0.1074, 3.94, 5, 1.02595675, 1.9, 227900000L, 25.19, 1.85, 0.15, new Color(0xed6639), "Red-orange", createURL("http://en.wikipedia.org/wiki/Mars"), createAction("Mars")},
            new Object[]{"Jupiter", "Outer Solar System", computeSpericalArea(71398), 71398, 317.83, 1.33, 59.5, 0.41354, 11.9, 778300000L, 3.12, 1.3, 0.52, new Color(0xf1666b), "Orange", createURL("http://en.wikipedia.org/wiki/Jupiter"), createAction("Jupiter")},
            new Object[]{"Saturn", "Outer Solar System", computeSpericalArea(60000), 60000, 95.159, 0.7, 35.6, 0.4375, 29.4, 1429400000L, 26.73, 2.49, 0.47, new Color(0xffdf91), "Pale yellow", createURL("http://en.wikipedia.org/wiki/Saturn"), createAction("Saturn")},
            new Object[]{"Uranus", "Outer Solar System", computeSpericalArea(25400), 25400, 14.5, 1.3, 21.22, -0.65, 84.5, 2875000000L, 97.86, 0.77, 0.51, new Color(0x95d6f6), "Light blue", createURL("http://en.wikipedia.org/wiki/Uranus"), createAction("Uranus")},
            new Object[]{"Neptune", "Outer Solar System", computeSpericalArea(24300), 24300, 17.204, 1.76, 23.6, 0.768, 164.8, 4504300000L, 29.56, 1.77, 0.41, new Color(0x6699cc), "Light blue", createURL("http://en.wikipedia.org/wiki/Neptune"), createAction("Neptune")},
            new Object[]{"Pluto", "Outer Solar System", computeSpericalArea(1550), 1550, 0.002, 1.1, 1.1, -6.3867, 254, 5900100000L, 118, 17.2, 0.3, new Color(0xd27c33), "Light brown", createURL("http://en.wikipedia.org/wiki/Pluto"), createAction("Pluto")}
    };

    private static double computeSpericalArea(double radius) {
        return Math.PI * radius * radius;
    }

    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Action createAction(final String name) {
        return new AbstractAction("Go to " + name + "...") {
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "You just landed on " + name + "!");
            }
        };
    }

    static class PlanetsTableModel extends DefaultTableModel {
        public PlanetsTableModel() {
            super(PLANETS, PLANET_COLUMNS);
        }

        public Class<?> getColumnClass(int columnIndex) {
            return PLANET_CLASSES[columnIndex];
        }
    }
}
