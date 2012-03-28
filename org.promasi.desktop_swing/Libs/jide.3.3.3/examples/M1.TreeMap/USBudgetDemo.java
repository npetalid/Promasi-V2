/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.colormap.ColorMapFactory;
import com.jidesoft.colormap.MutableColorMap;
import com.jidesoft.grid.*;
import com.jidesoft.palette.PaletteFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideTabbedPane;
import com.jidesoft.treemap.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class USBudgetDemo extends AbstractDemo {
    public TreeMap _treeMap;

    public String getName() {
        return "TreeMap Demo (US Budget)";
    }

    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    public String getProduct() {
        return PRODUCT_NAME_TREEMAP;
    }

    public Component getDemoPanel() {
        TableModel tableModel = createUSBudgetTableModel();

        _treeMap = new TreeMap(tableModel);

        _treeMap.getModel().getSettings().setGroupBy(1);
        _treeMap.getModel().getSettings().setLabels(0);
        _treeMap.getModel().getSettings().setSize(3);
        _treeMap.getModel().getSettings().setColor(4);

        // Customize color gradient
        final MutableColorMap colormap = ColorMapFactory.getInstance().createColorMap(tableModel, 4);
        colormap.setPalette(PaletteFactory.getInstance().createColorGradient("RdYlGn"));
        colormap.getInterval().setValue(0, 1);
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(4)).setColorMap(colormap);

        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderFont(new Font("Tahoma", Font.BOLD, 16));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabelingFont(new Font("Tahoma", Font.ITALIC, 8));

        _treeMap.getModel().getSettings().getDefaultFieldSettings().setAlgorithm(AlgorithmFactory.SQUARIFIED);
        _treeMap.getModel().getSettings().setRendering(RenderingFactory.CUSHION);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabeling(LabelingFactory.SURROUND);

        _treeMap.getView().setShowTiming(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(_treeMap, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(800, 800));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GroupTable table = new GroupTable(new DefaultGroupTableModel(tableModel));
        GroupTableHeader header = new GroupTableHeader(table);
        table.setTableHeader(header);
        header.setGroupHeaderEnabled(true);
        header.setAutoFilterEnabled(true);
        header.setUseNativeHeaderRenderer(true);
        table.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
        TableUtils.autoResizeAllColumns(table);
        table.setColumnAutoResizable(true);

        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JideTabbedPane tabbedPane = new JideTabbedPane();
        tabbedPane.add("TreeMap", panel);
        tabbedPane.add("Table", tablePanel);
        tabbedPane.setTabShape(JideTabbedPane.SHAPE_BOX);

        return tabbedPane;
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

    public USBudgetDemo() {
    }

    static public void main(String[] s) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new USBudgetDemo());
    }

    private TableModel createUSBudgetTableModel() {
        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream("usbudget2010.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Vector data = new Vector();
            Vector columnNames = new Vector();
            Vector columnTypes = new Vector();

            String columnsLine = reader.readLine(); // skip first line
            String[] columnValues = columnsLine.split("\t");
            columnNames.addAll(Arrays.asList(columnValues));

            String typesLine = reader.readLine(); // skip second line
            columnValues = typesLine.split("\t");
            columnTypes.addAll(Arrays.asList(columnValues));

            int count = 0;
            do {
                String line = reader.readLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                String[] values = line.split("\t");
                Vector lineData = new Vector();
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    Object type = columnTypes.get(i);
                    if (type.equals("String")) {
                        lineData.add(value);
                    }
                    else if (type.equals("Integer")) {
                        if (value != null && value.length() != 0) {
                            lineData.add(Integer.parseInt(value));
                        }
                        else {
                            lineData.add(null);
                        }
                    }
                    else if (type.equals("Float")) {
                        if (value != null && value.length() != 0) {
                            lineData.add(Float.parseFloat(value));
                        }
                        else {
                            lineData.add(null);
                        }
                    }
                    else if (type.equals("Double")) {
                        if (value != null && value.length() != 0) {
                            lineData.add(Double.parseDouble(value));
                        }
                        else {
                            lineData.add(null);
                        }
                    }
                    else {
                        lineData.add(value);
                    }
                }

                for (int i = 0; i < 1; i++) {
                    data.add(lineData);
                }
            }
            while (true);
            return new USBudgetTableModel(data, columnNames, columnTypes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class USBudgetTableModel extends DefaultTableModel {
        private Vector columnTypes;

        public USBudgetTableModel(Vector data, Vector columnNames, Vector columnTypes) {
            super(data, columnNames);
            this.columnTypes = columnTypes;
        }

        public Class<?> getColumnClass(int columnIndex) {
            Object type = columnTypes.get(columnIndex);
            if (type.equals("String")) {
                return String.class;
            }
            else if (type.equals("Integer")) {
                return Integer.class;
            }
            else if (type.equals("Float")) {
                return Float.class;
            }
            else if (type.equals("Double")) {
                return Double.class;
            }
            else {
                return Object.class;
            }
        }
    }


}
