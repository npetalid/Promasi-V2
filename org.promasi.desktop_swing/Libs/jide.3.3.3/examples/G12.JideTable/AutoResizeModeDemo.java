/*
 * @(#)AutoResizeModeDemo.java 9/14/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.utils.ProductNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Demoed Component: {@link JideTable} <br> Required jar files: jide-common.jar, jide-grids.jar <br> Required L&F: any
 * L&F
 */
public class AutoResizeModeDemo extends AbstractDemo {
    private static final long serialVersionUID = -8856470365128446666L;
    public SortableTable _table;
    protected final Color BACKGROUND1 = new Color(253, 253, 220);
    protected final Color BACKGROUND2 = new Color(255, 255, 255);

    public AutoResizeModeDemo() {
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public String getName() {
        return "AutoResizeMode Table Demo";
    }

    public String getProduct() {
        return ProductNames.PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "This is a demo of JideTable with different auto resize mode.\n" +
                "\nJIDE added a new auto resize mode: AUTO_RESIZE_FILL.\n" +
                "\nIn that mode, the right blank area to the table contents in a JViewport will be filled automatically.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.JideTable";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));

        final JCheckBox fillWithStripes = new JCheckBox("Fill with stripes");
        fillWithStripes.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setFillsViewportWithStripe(e.getStateChange() == ItemEvent.SELECTED);
                _table.revalidate();
                _table.repaint();
            }
        });
        fillWithStripes.setSelected(_table.isFillsViewportWithStripe());

        final JCheckBox fillSelection = new JCheckBox("Fill the selection background");
        fillSelection.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setFillsSelection(e.getStateChange() == ItemEvent.SELECTED);
                _table.revalidate();
                _table.repaint();
            }
        });
        fillSelection.setSelected(_table.isFillsSelection());

        final JCheckBox fillRight = new JCheckBox("Fill the right area");
        fillRight.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setFillsRight(e.getStateChange() == ItemEvent.SELECTED);
                fillSelection.setEnabled(fillRight.isEnabled() && fillRight.isSelected());
                fillWithStripes.setEnabled(_table.isFillsBottom() || _table.isFillsRight());
                _table.revalidate();
                _table.repaint();
            }
        });
        fillRight.setSelected(_table.isFillsRight());

        fillRight.addPropertyChangeListener("enabled", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                fillSelection.setEnabled(fillRight.isEnabled() && fillRight.isSelected());
            }
        });

        final JCheckBox fillGrids = new JCheckBox("Fill with grid lines");
        fillGrids.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setFillsGrids(e.getStateChange() == ItemEvent.SELECTED);
                _table.revalidate();
                _table.repaint();
            }
        });
        fillGrids.setSelected(_table.isFillsGrids());

        final JCheckBox fillBottom = new JCheckBox("Fill the bottom area");
        fillBottom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setFillsBottom(e.getStateChange() == ItemEvent.SELECTED);
                fillGrids.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
                fillWithStripes.setEnabled(_table.isFillsBottom() || _table.isFillsRight());
                _table.revalidate();
                _table.repaint();
            }
        });
        fillBottom.setSelected(_table.isFillsBottom());

        JComboBox autoResizeMode = new JComboBox(new String[]{"Auto Resize Fill", "Auto Resize Off", "Auto Resize Next Column", "Auto Resize SubsequentColumns", "Auto Resize Last Column", "Auto Resize All Columns"});
        autoResizeMode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem() == null || e.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                if (e.getItem().equals("Auto Resize Off")) {
                    _table.setAutoResizeMode(JideTable.AUTO_RESIZE_OFF);
                    fillWithStripes.setEnabled(false);
                    fillRight.setEnabled(false);
                    fillBottom.setEnabled(false);
                    fillGrids.setEnabled(false);
                }
                else if (e.getItem().equals("Auto Resize Next Column")) {
                    _table.setAutoResizeMode(JideTable.AUTO_RESIZE_NEXT_COLUMN);
                    fillWithStripes.setEnabled(false);
                    fillRight.setEnabled(false);
                    fillBottom.setEnabled(false);
                    fillGrids.setEnabled(false);
                }
                else if (e.getItem().equals("Auto Resize SubsequentColumns")) {
                    _table.setAutoResizeMode(JideTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    fillWithStripes.setEnabled(false);
                    fillRight.setEnabled(false);
                    fillBottom.setEnabled(false);
                    fillGrids.setEnabled(false);
                }
                else if (e.getItem().equals("Auto Resize Last Column")) {
                    _table.setAutoResizeMode(JideTable.AUTO_RESIZE_LAST_COLUMN);
                    fillWithStripes.setEnabled(false);
                    fillRight.setEnabled(false);
                    fillBottom.setEnabled(false);
                    fillGrids.setEnabled(false);
                }
                else if (e.getItem().equals("Auto Resize All Columns")) {
                    _table.setAutoResizeMode(JideTable.AUTO_RESIZE_ALL_COLUMNS);
                    fillWithStripes.setEnabled(false);
                    fillRight.setEnabled(false);
                    fillBottom.setEnabled(false);
                    fillGrids.setEnabled(false);
                }
                else if (e.getItem().equals("Auto Resize Fill")) {
                    _table.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
                    fillWithStripes.setEnabled(true);
                    fillRight.setEnabled(true);
                    fillBottom.setEnabled(true);
                    fillGrids.setEnabled(_table.isFillsBottom());
                }
            }
        });

        final JCheckBox showHorizontal = new JCheckBox("Show horizontal grid lines");
        showHorizontal.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setShowHorizontalLines(e.getStateChange() == ItemEvent.SELECTED);
                _table.revalidate();
                _table.repaint();
            }
        });
        showHorizontal.setSelected(_table.getShowHorizontalLines());

        final JCheckBox showVertical = new JCheckBox("Show vertical grid lines");
        showVertical.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setShowVerticalLines(e.getStateChange() == ItemEvent.SELECTED);
                _table.revalidate();
                _table.repaint();
            }
        });
        showVertical.setSelected(_table.getShowVerticalLines());

        panel.add(autoResizeMode);
        panel.add(showHorizontal);
        panel.add(showVertical);
        panel.add(fillSelection);
        panel.add(fillRight);
        panel.add(fillBottom);
        panel.add(fillWithStripes);
        panel.add(fillGrids);
        return panel;
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        DefaultTableModel model = new DefaultTableModel(10, 4) {
            private static final long serialVersionUID = -930081547133724313L;

            @Override
            public Object getValueAt(int row, int column) {
                return "(" + row + " , " + column + ")";
            }
        };
        _table = new SortableTable(model);
        _table.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
        _table.setTableStyleProvider(new RowStripeTableStyleProvider(new Color[]{BACKGROUND1, BACKGROUND2}));

        panel.add(new JScrollPane(_table), BorderLayout.CENTER);
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "G12.JideTable";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                AbstractDemo.showAsFrame(new AutoResizeModeDemo());
            }
        });
    }
}