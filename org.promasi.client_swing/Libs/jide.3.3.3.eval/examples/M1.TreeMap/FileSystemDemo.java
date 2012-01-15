/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.treemap.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FileSystemDemo extends AbstractDemo {
    public TreeMap _treeMap;
    public FileSystemTreeMapModel _model;

    public String getName() {
        return "TreeMap Demo (File System)";
    }

    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    public String getProduct() {
        return PRODUCT_NAME_TREEMAP;
    }

    public Component getDemoPanel() {
         _model = createFileSystemTreeTableModel();

        _treeMap = new TreeMap(_model);

        _treeMap.getModel().getSettings().setGroupBy(0);
        _treeMap.getModel().getSettings().setLabels(0);
        _treeMap.getModel().getSettings().setSize(1);
        _treeMap.getModel().getSettings().setColor(2);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(0), true);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(1), true);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(2), true);
        _treeMap.getModel().getSettings().setShowPopup(_treeMap.getModel().getTreeMapField(3), true);

        // Customize the color so that negative values are red and positive green.
//        final Double absMax = 20.0;
//        _treeMap.getModel().setColorMap(5, new SimpleColorMap(new ClosedInterval(-absMax, 2 * absMax),
//                new InterpolatedPalette(new InterpolatedPalette.Entry(0, Color.red), new InterpolatedPalette.Entry(0.5, Color.white), new InterpolatedPalette.Entry(1, new Color(0, 128, 0)))
//        ));

        _treeMap.getModel().getSettings().getDefaultFieldSettings().setHeaderFont(new Font("Tahoma", Font.BOLD, 14));
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabelingFont(new Font("Tahoma", Font.ITALIC, 9));

        _treeMap.getModel().getSettings().getDefaultFieldSettings().setAlgorithm(AlgorithmFactory.SQUARIFIED);
        _treeMap.getModel().getSettings().setRendering(RenderingFactory.CUSHION);
        _treeMap.getModel().getSettings().getDefaultFieldSettings().setLabeling(LabelingFactory.SURROUND);

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

        final JSlider slider = new JSlider(1, 10);
        panel.add(new JLabel("Maximum depth: "));
        panel.add(slider);
        slider.setMajorTickSpacing(1);
        slider.setPaintTrack(true);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setValue(_model.getMaximumDepth());
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!slider.getValueIsAdjusting()) {
                    _model.setMaximumDepth(slider.getValue());
                }
            }
        });

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

    static public void main(String[] s) {
         LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
         showAsFrame(new FileSystemDemo());
     }

    private FileSystemTreeMapModel createFileSystemTreeTableModel() {
        return new FileSystemTreeMapModel();
    }

}
