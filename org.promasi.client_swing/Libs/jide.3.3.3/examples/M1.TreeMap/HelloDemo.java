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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Sort of a Hello World! application to demonstrate the most basic use of the TreeMap API
 */
public class HelloDemo extends AbstractDemo {
    public TreeMap _treeMap;

    public String getName() {
        return "TreeMap Demo (A Simple Example)";
    }

    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    public String getProduct() {
        return PRODUCT_NAME_TREEMAP;
    }

    public Component getDemoPanel() {
        // Defining the data, column names and types
        Object[][] data = new Object[][]{
                {"Hello", 12, 3.0},
                {"from", 11, 4.0},
                {"the", 9, 5.0},
                {"TreeMap", 8, 6.0},
                {"World!", 7, 7.0},
        };
        Object[] columnNames = new Object[]{"Name", "Value", "Strength"};
        final Class[] columnTypes = new Class[]{String.class, Integer.class, Double.class};

        // Creating a standard Swing TableModel
        TableModel tableModel = new DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };

        // Creating the TreeMap
        _treeMap = new TreeMap(tableModel);

        // Tuning the appearance of the TreeMap
        _treeMap.setAlgorithm(AlgorithmFactory.SQUARIFIED);
        _treeMap.setSizeByName("Value");
        _treeMap.setColor(2);
        _treeMap.setBackgroundByName("Name");
        _treeMap.setLabels();

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

    public HelloDemo() {
    }

    static public void main(String[] s) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new HelloDemo());
    }
}
