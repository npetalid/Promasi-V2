/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.colormap.SimpleColorMap;
import com.jidesoft.interval.ClosedInterval;
import com.jidesoft.palette.PaletteFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.treemap.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

public class DynamicPortfolioDemo extends AbstractDemo implements AnimatedDemo {
    public TreeMap _treeMap;
    public DefaultTreeMapModel _model;
    final JCheckBox swapTableModel = new JCheckBox("Swap TableModel", false);
    final JCheckBox varyTableModelSize = new JCheckBox("Vary TableModel size", false);
    private PortfolioTableModel _tableModel;
    protected Timer _timer;

    public String getName() {
        return "TreeMap Demo (Dynamic Portfolio)";
    }

    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    public String getProduct() {
        return PRODUCT_NAME_TREEMAP;
    }

    public void startAnimation() {
        if (_timer != null) {
            _timer.start();
        }
    }

    public void stopAnimation() {
        if (_timer != null) {
            _timer.stop();
        }
    }

    public Component getDemoPanel() {
        _tableModel = new PortfolioTableModel();
        _model = new DefaultTreeMapModel(_tableModel);

        _timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (swapTableModel.isSelected()) {
                    if (_tableModel == null) {
                        _tableModel = new PortfolioTableModel();
                    }
                    else {
                        _tableModel = new PortfolioTableModel(_tableModel);
                        _tableModel.update(varyTableModelSize.isSelected());
                    }
                    _model.setTableModel(_tableModel);
                }
                else {
                    _tableModel.update(varyTableModelSize.isSelected());
                }

            }
        });
        _timer.setInitialDelay(500);
        _timer.start();

        _treeMap = new TreeMap(_model);

        _treeMap.setGroupBy(1, 2);
        _treeMap.setLabels(0);
        _treeMap.setBackground(3);
        _treeMap.setSize(8);
        _treeMap.setColorByName("Change %");

        // Customize the color so that negative values are red and positive green.
        final Double absMax = 0.1;
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(5)).setColorMap(
                new SimpleColorMap(new ClosedInterval(-absMax, 2 * absMax),
                        PaletteFactory.getInstance().createDefaultDivergingPalette()

                ));

        _treeMap.setAlgorithm(AlgorithmFactory.SQUARIFIED);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderFont(new Font("Tahoma", Font.BOLD, 16));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabelingFont(new Font("Tahoma", Font.ITALIC, 18));

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

        panel.add(swapTableModel);
        panel.add(Box.createVerticalStrut(4));

        panel.add(varyTableModelSize);
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

    public DynamicPortfolioDemo() {
    }

    static public void main(String[] s) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new DynamicPortfolioDemo());
    }

    static String[] COLUMNS = new String[]{"Name", "Sector", "Industry", "Symbol", "Last price", "Change %", "Shares", "Cost basis", "Mkt value", "Gain", "Gain %", "Day's gain", "Overall Return"};
    static Class[] CLASSES = new Class[]{String.class, String.class, String.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class};

    static Object[][] PORTFOLIO = new Object[][]{
            new Object[]{"Adobe Systems Incorporated", "Technology", "Software and Programming", "ADBE", 34.69, 0, 750, 0, 0, 0, 0, 0, 0},
            new Object[]{"Red Hat, Inc.", "Technology", "Software and Programming", "RHT", 45.66, 0, 10000, 0, 0, 0, 0, 0, 0},
            new Object[]{"Apple Inc.", "Technology", "Computer Hardware", "AAPL", 363.13, 0, 1000, 0, 0, 0, 0, 0, 81.21},
            new Object[]{"Microsoft Corporation", "Technology", "Software and Programming", "MSFT", 27.02, 0, 500, 0, 0, 0, 0, 0},
            new Object[]{"UBS AG (USA)", "Financial", "Investment Services", "UBS", 19.51, 0, 8000, 0, 0, 0, 0},
            new Object[]{"Google Inc.", "Technology", "Computer Services", "GOOG", 624.22, 0, 200, 0, 0, 0, 0, 0},
            new Object[]{"Expedia, Inc.", "Services", "Personal Services", "EXPE", 21.19, 0, 5000, 0, 0, 0, 0, 0},
            new Object[]{"Roche Holding Ltd. (ADR)", null, null, "RHHBY", 36.37, 0, 10000, 0, 0, 0, 0, 0},
            new Object[]{"International Business Machines Corp.", "Technology", "Computer Services", "IBM", 163.4, 0, 400, 0, 0, 0, 0, 0},
            new Object[]{"Yahoo! Inc.", "Technology", "Computer Services", "YHOO", 17.76, 0, 2000, 0, 0, 0, 0, 0, 0},
    };

    static class PortfolioTableModel extends DefaultTableModel {
        Random random = new Random();
        int rowCount = 0;

        public PortfolioTableModel() {
            super(PORTFOLIO, COLUMNS);
        }

        PortfolioTableModel(PortfolioTableModel tableModel) {
            super(tableModel.getDataVector(), convertToVector(COLUMNS));
        }

        public Class<?> getColumnClass(int columnIndex) {
            return CLASSES[columnIndex];
        }

        public int getRowCount() {
            if (rowCount <= 0) {
                rowCount = PORTFOLIO.length;
            }

            return rowCount;
        }

        public Object getValueAt(int row, int column) {
            switch (column) {
                case 8:
                    return ((Number) getValueAt(row, 4)).doubleValue() * ((Number) getValueAt(row, 6)).doubleValue();
                default:
                    return super.getValueAt(row, column);
            }
        }

        public void update(boolean varyTableModelSize) {
            for (int row = 0; row < super.getRowCount(); row++) {
                double delta = random.nextGaussian() / 500.0;
                final double oldPrice = ((Number) getValueAt(row, 4)).doubleValue();
                final double newPrice = oldPrice * (1.0 + delta);
                setValueAt(newPrice, row, 4);
                final double newChange = ((Number) getValueAt(row, 5)).doubleValue() + delta;
                setValueAt(newChange, row, 5);
            }

            if (varyTableModelSize) {
                rowCount = random.nextInt(super.getRowCount() - 1) + 1;
            }
            else {
                rowCount = super.getRowCount();
            }
        }

    }
}
