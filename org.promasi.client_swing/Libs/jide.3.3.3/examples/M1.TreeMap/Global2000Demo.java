/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.colormap.SimpleColorMap;
import com.jidesoft.grid.DefaultGroupTableModel;
import com.jidesoft.grid.GroupTable;
import com.jidesoft.grid.GroupTableHeader;
import com.jidesoft.interval.ClosedInterval;
import com.jidesoft.palette.InterpolatedPalette;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideTabbedPane;
import com.jidesoft.treemap.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class Global2000Demo extends AbstractDemo {
    public TreeMap _treeMap;

    public String getName() {
        return "TreeMap Demo (Global 2000)";
    }

    public int getAttributes() {
        return ATTRIBUTE_BETA | ATTRIBUTE_UPDATED;
    }

    public String getProduct() {
        return PRODUCT_NAME_TREEMAP;
    }

    public Component getDemoPanel() {
        // Prepare the input data
        TableModel tableModel = createGlobal2000TableModel();

        // Create the TreeMap
        _treeMap = new TreeMap(tableModel);

        // Configure fields of analysis
        _treeMap.getModel().getSettings().setGroupBy(1);
        _treeMap.getModel().getSettings().setLabels(0);
        _treeMap.getModel().getSettings().setSize(3);
        _treeMap.getModel().getSettings().setColor(5);

        // Configure the layout
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setAlgorithm(AlgorithmFactory.SQUARIFIED);
//        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(1)).setAlgorithm(AlgorithmFactory.SLICE);
//        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(2)).setAlgorithm(AlgorithmFactory.SLICE);
        _treeMap.getModel().getSettings().setRendering(RenderingFactory.CUSHION);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabeling(LabelingFactory.SURROUND);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setNesting(NestingFactory.FIXED);

        // Customize the color so that negative values are red and positive green.
        final Double absMax = 20.0;
        final SimpleColorMap colorMap = new SimpleColorMap(new ClosedInterval(-absMax, 2 * absMax),
                new InterpolatedPalette(new InterpolatedPalette.Entry(0, Color.red),
                        new InterpolatedPalette.Entry(0.5, Color.white),
                        new InterpolatedPalette.Entry(1, new Color(0, 128, 0)))
        );
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(5)).setColorMap(
                colorMap);

        // Configure the appearance of the headers (parent nodes)
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderFont(new Font("Tahoma", Font.BOLD, 16));
        final DefaultTreeMapHeaderRenderer headerRenderer = new DefaultTreeMapHeaderRenderer();
        headerRenderer.setEffect(DefaultTreeMapHeaderRenderer.Effect.Glow);
        headerRenderer.setRendering(DefaultTreeMapHeaderRenderer.Rendering.Truncate);
        headerRenderer.setHorizontalAlignment(DefaultTreeMapLabelRenderer.CENTER);
        headerRenderer.setVerticalAlignment(DefaultTreeMapLabelRenderer.TOP);
        _treeMap.getView().setHeaderRenderer(headerRenderer);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderForeground(new Color(190, 190, 190));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderEffectColor(new Color(0, 0, 0));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderBackground(new Color(96, 96, 96));

        // Configure the appearance of the labels (leaf nodes)
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabelingFont(new Font("Tahoma", Font.ITALIC, 8));
        final DefaultTreeMapLabelRenderer labelRenderer = new DefaultTreeMapLabelRenderer();
        labelRenderer.setRendering(DefaultTreeMapLabelRenderer.Rendering.Clip);
        labelRenderer.setHorizontalAlignment(DefaultTreeMapLabelRenderer.CENTER);
        labelRenderer.setMinimumCharactersToDisplay(5);
        labelRenderer.setDefaultVerticalAlignment(DefaultTreeMapLabelRenderer.BOTTOM);
        labelRenderer.setBorder(BorderFactory.createEmptyBorder(2, 1, 2, 1));
        _treeMap.getView().setLabelRenderer(labelRenderer);

        // Configure the appearance of the tooltip
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setTooltipFont(new Font("Tahoma", Font.ITALIC, 14));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setTooltipForeground(new Color(177, 225, 246));
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(0)).setTooltipForeground(new Color(227, 222, 12));
        _treeMap.getModel().getSettings().setTooltipBackground(new Color(255, 255, 0, 150));
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(3), true);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(4), true);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(5), true);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(6), true);
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(7)).setShowTooltipLabel(true);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(7), true);
        _treeMap.getView().setToolTip(new CustomTreeMapToolTip(_treeMap.getView()));

        // Only show the tooltip for leaf nodes
        _treeMap.getView().setTooltipRenderer(new DefaultTreeMapTooltipRenderer() {
            public Component getTreeMapTooltipRendererComponent(TreeMapView treeMapView, TreeMapField treeMapField, Object node, Dimension dimension) {
                if (treeMapView.getModel().isLeaf(node)) {
                    return super.getTreeMapTooltipRendererComponent(treeMapView, treeMapField, node, dimension);
                }
                else {
                    return null;
                }
            }
        });

        // Configure the formatting of values
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(3)).setFormat(new DecimalFormat("#,##0.00 $bil"));
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(4)).setFormat(new DecimalFormat("#,##0.00 $bil"));
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(5)).setFormat(new DecimalFormat("#,##0.00 $bil"));
        _treeMap.getModel().getSettings().getFieldSettings(_treeMap.getModel().getTreeMapField(6)).setFormat(new DecimalFormat("#,##0.00 $bil"));

        // Configure the appeance of the probing and selection
        _treeMap.getModel().getSettings().setProbingColor(new Color(255, 255, 0, 150));
        _treeMap.getModel().getSettings().setSelectionColor(Color.orange);

        // Configure the view
        _treeMap.getView().setProgressive(TreeMapView.Progressive.Incremental);
        _treeMap.getView().setShowTiming(true);

        // Only allow single selection
        final DefaultTreeMapController controller = new DefaultTreeMapController();
        controller.setMultipleSelectionEnabled(false);
        controller.setSelectOnPopupTrigger(true);

        _treeMap.setController(controller);

        // Add custom entries to the context menu
        controller.getPopupMenu().insert(new AbstractAction("Copy Image to Clipboard") {
                    public void actionPerformed(ActionEvent e) {
                        // Save the state
                        List selection = _treeMap.getModel().getSelection();
                        Object probing = _treeMap.getModel().getProbing();

                        // Hide selection and probing
                        _treeMap.getModel().setSelection(null);
                        _treeMap.getModel().setProbing(null);

                        final Dimension size = _treeMap.getSize();
                        final BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
                        _treeMap.paint(bufferedImage.createGraphics());

                        final Transferable imageSelection = new Transferable() {
                            public DataFlavor[] getTransferDataFlavors() {
                                return new DataFlavor[]{
                                        DataFlavor.imageFlavor
                                };
                            }

                            public boolean isDataFlavorSupported(DataFlavor flavor) {
                                return flavor.equals(DataFlavor.imageFlavor);
                            }

                            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                                if (flavor.equals(DataFlavor.imageFlavor) == false) {
                                    throw new UnsupportedFlavorException(flavor);
                                }
                                return bufferedImage;
                            }
                        };
                        final Toolkit toolkit = Toolkit.getDefaultToolkit();
                        toolkit.getSystemClipboard().setContents(imageSelection, null);

                        // Restore the state
                        _treeMap.getModel().setSelection(selection);
                        _treeMap.getModel().setSelection(selection);
                    }
                }, 0);
        controller.getPopupMenu().insert(new AbstractAction("Count selected items") {
                    public void actionPerformed(ActionEvent e) {
                        final List selection = _treeMap.getModel().getSelection();
                        final int selectionCount = selection == null ? 0 : selection.size();
                        JOptionPane.showMessageDialog(_treeMap.getView(), selectionCount + " items selected");
                    }
                }, 0);

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

        final JCheckBox antiAliasing = new JCheckBox("Anti-Aliasing", true);
        antiAliasing.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (antiAliasing.isSelected()) {
                    RenderingHints renderingHints = new RenderingHints(new HashMap<RenderingHints.Key, Object>());
                    renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    _treeMap.getView().setRenderingHints(renderingHints);
                }
                else {
                    RenderingHints renderingHints = new RenderingHints(new HashMap<RenderingHints.Key, Object>());
                    _treeMap.getView().setRenderingHints(renderingHints);
                }
            }
        });
        panel.add(antiAliasing);
        panel.add(Box.createVerticalStrut(4));

        final JCheckBox progressive = new JCheckBox("Progressive", (_treeMap.getView()).getProgressive() == TreeMapView.Progressive.Incremental);
        progressive.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (progressive.isSelected()) {
                    _treeMap.getView().setProgressive(TreeMapView.Progressive.Incremental);
                }
                else {
                    _treeMap.getView().setProgressive(TreeMapView.Progressive.Disabled);
                }
            }
        });
        panel.add(progressive);
        panel.add(Box.createVerticalStrut(4));

        final JCheckBox improvedCornerZooming = new JCheckBox("Improved Corner Zooming", ((DefaultTreeMapController) _treeMap.getController()).isImprovedBorderZooming());
        improvedCornerZooming.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ((DefaultTreeMapController) _treeMap.getController()).setImprovedBorderZooming(improvedCornerZooming.isSelected());
            }
        });
        panel.add(improvedCornerZooming);
        panel.add(Box.createVerticalStrut(4));

        return panel;
    }

    public Global2000Demo() {
    }

    static public void main(String[] s) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new Global2000Demo());
    }

    private TableModel createGlobal2000TableModel() {
        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream("global00to10.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "Latin1"));
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
                        if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
                            value = value.substring(1, value.length() - 2);
                        }
                        lineData.add(value);
                    }
                    else if (type.equals("Float")) {
                        if (value != null && value.length() != 0) {
                            lineData.add(Float.parseFloat(value));
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
            return new Global2000TableModel(data, columnNames, columnTypes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Global2000TableModel extends DefaultTableModel {
        private Vector columnTypes;

        public Global2000TableModel(Vector data, Vector columnNames, Vector columnTypes) {
            super(data, columnNames);
            this.columnTypes = columnTypes;
        }

        public Class<?> getColumnClass(int columnIndex) {
            Object type = columnTypes.get(columnIndex);
            if (type.equals("String")) {
                return String.class;
            }
            else if (type.equals("Float")) {
                return Float.class;
            }
            else {
                return Object.class;
            }
        }
    }


    private class CustomTreeMapToolTip extends TreeMapToolTip {
        public CustomTreeMapToolTip(TreeMapView view) {
            super(view);

            setOpaque(false);

            LineBorder roundedLineBorder = new LineBorder(Color.black, 1, true);
            TitledBorder roundedTitledBorder = new TitledBorder(roundedLineBorder, "Title");

            setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        public Insets getInsets() {
            return new Insets(4, 4, 4, 4);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            Insets insets = getInsets();
            int x = insets.left;
            int y = insets.top;
            int width = getWidth() - insets.left - insets.right;
            int height = getHeight() - insets.top - insets.bottom;

            g2.setPaint(new GradientPaint(x, y, new Color(0, 106, 149, 230), 0, height, new Color(0, 51, 43, 230)));
            g2.fill(new RoundRectangle2D.Double(x, y, width, height, 10, 10));

            g2.setPaint(new Color(78, 191, 242, 150));
            g2.setStroke(new BasicStroke(4f));
            g2.draw(new RoundRectangle2D.Double(2, 2, getWidth() - 4, getHeight() - 4, 10, 10));

            super.paintComponent(g);
        }
    }
}
