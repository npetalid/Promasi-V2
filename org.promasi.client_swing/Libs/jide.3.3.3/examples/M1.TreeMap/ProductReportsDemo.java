/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.colormap.ColorMapFactory;
import com.jidesoft.colormap.MutableColorMap;
import com.jidesoft.palette.PaletteFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.treemap.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class ProductReportsDemo extends AbstractDemo {
    public TreeMap _treeMap;

    public String getName() {
        return "TreeMap Demo (Product Reports)";
    }

    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    public String getProduct() {
        return PRODUCT_NAME_TREEMAP;
    }

    public Component getDemoPanel() {
        TableModel tableModel = createProductReportsTableModel();

        _treeMap = new TreeMap(tableModel);

        _treeMap.getModel().getSettings().setGroupBy(0);
        _treeMap.getModel().getSettings().setLabels(1);
        _treeMap.getModel().getSettings().setSize(2);
        _treeMap.getModel().getSettings().setColor(3);
        final MutableColorMap colorMap = ColorMapFactory.getInstance().createColorMap(tableModel, 3);
        colorMap.setPalette(PaletteFactory.getInstance().createColorGradient("RdYlGn"));
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(3)).setColorMap(colorMap);

        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderFont(new Font("Tahoma", Font.BOLD, 16));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabelingFont(new Font("Tahoma", Font.ITALIC, 8));

        _treeMap.getModel().getSettings().getDefaultFieldSettings().setAlgorithm(AlgorithmFactory.SQUARIFIED);
        _treeMap.getModel().getSettings().setRendering(RenderingFactory.CUSHION);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabeling(LabelingFactory.EXPAND);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderEffect(EnhancedJLabel.Effect.Outline);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderEffectColor(Color.lightGray);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderVerticalAlignment(JLabel.CENTER);

        final double borderSize = 4;
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderBackground(Color.darkGray);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setNesting(new Nesting() {
            public Rectangle2D subtract(Rectangle2D rectangle) {
                return new Rectangle2D.Double(rectangle.getX() + borderSize / 2, rectangle.getY() + borderSize / 2,
                        rectangle.getWidth() - borderSize, rectangle.getHeight() - borderSize);
            }
        });

        _treeMap.getView().setShowTiming(true);

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

        List<Algorithm> algorithms = AlgorithmFactory.getInstance().getAlgorithms();
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

        List<Aggregation> aggregations = AggregationFactory.getInstance().getAggregations();
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

        List<Scale> scales = ScaleFactory.getInstance().getScales();
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

        List<Nesting> nestings = NestingFactory.getInstance().getNestings();
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

        List<Ordering> orderings = OrderingFactory.getInstance().getOrderings();
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

        List<Depth> depths = DepthFactory.getInstance().getDepths();
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

        List<Labeling> labelings = LabelingFactory.getInstance().getLabelings();
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

        List<Rendering> renderings = RenderingFactory.getInstance().getRenderings();
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

    public ProductReportsDemo() {
    }

    static public void main(String[] s) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new ProductReportsDemo());
    }

    private TableModel createProductReportsTableModel() {
        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream("ProductReports.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Vector data = new Vector();
            Vector<String> columnNames = new Vector<String>();

            String columnsLine = reader.readLine(); // skip first line
            String[] columnValues = columnsLine.split("\t");
            columnNames.addAll(Arrays.asList(columnValues));

            int count = 0;
            do {
                String line = reader.readLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                String[] values = line.split("\t");
                Vector lineData = new Vector();
                lineData.add(values[0]); // category  name
                lineData.add(values[1]); // product name
                {
                    String value = values[2];
                    if (value.startsWith("$")) {
                        float f = Float.parseFloat(value.substring(1));
                        lineData.add(f); // product amount
                    }
                }
                {
                    String value = values[3];
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                        Date date = format.parse(value);
                        lineData.add(date); // order date
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 1; i++) {
                    data.add(lineData);
                }
            }
            while (true);
            return new ProductTableModel(data, columnNames);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class ProductTableModel extends DefaultTableModel {
        public ProductTableModel(Vector data, Vector columnNames) {
            super(data, columnNames);
        }

        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                    return String.class;
                case 2:
                    return Float.class;
                case 3:
                    return Date.class;
            }
            return super.getColumnClass(columnIndex);
        }
    }
}
