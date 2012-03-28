/*
 * @(#)CustomFilterDemo.java 9/10/2008
 *
 * Copyright 2002 - 2008 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.IFilterableTableModel;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableCustomFilterEditor;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.pivot.PivotTablePane} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
public class CustomFilterDemo extends CustomTableDemo {
    private static final long serialVersionUID = -3439866111482234541L;
    private FilterableTableModel _filterableTableModel;
    private TableCustomFilterEditor _tableCustomFilterEditor;

    public CustomFilterDemo() {
    }

    public String getName() {
        return "TableCustomFilterEditor Demo";
    }

    @Override
    public String getDemoFolder() {
        return "G33.CustomFilterEditor";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public Component getOptionsPanel() {
        ButtonPanel panel = new ButtonPanel(SwingConstants.TOP);

        final JCheckBox collapsible = new JCheckBox("Collapsible");

        JRadioButton list = new JRadioButton(new AbstractAction("Using JList to show filters") {
            private static final long serialVersionUID = -3694232432790117101L;

            public void actionPerformed(ActionEvent e) {
                _filterableTableModel.clearFilters();
                _filterableTableModel.setFiltersApplied(true);
                collapsible.setEnabled(false);
                _tableCustomFilterEditor.setStyle(TableCustomFilterEditor.STYLE_FILTER_LIST);
            }
        });
        panel.addButton(list);
        JRadioButton editor = new JRadioButton(new AbstractAction("Using in-line editor to show filters") {
            private static final long serialVersionUID = 4222735923709833536L;

            public void actionPerformed(ActionEvent e) {
                _filterableTableModel.clearFilters();
                _filterableTableModel.setFiltersApplied(true);
                collapsible.setEnabled(true);
                _tableCustomFilterEditor.setStyle(TableCustomFilterEditor.STYLE_INLINE_EDITOR);
            }
        });
        panel.addButton(editor);
        list.setSelected(_tableCustomFilterEditor.getStyle() == TableCustomFilterEditor.STYLE_FILTER_LIST);
        editor.setSelected(_tableCustomFilterEditor.getStyle() == TableCustomFilterEditor.STYLE_INLINE_EDITOR);

        collapsible.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tableCustomFilterEditor.setCollapsible(collapsible.isSelected());
            }
        });
        collapsible.setEnabled(editor.isSelected());
        collapsible.setSelected(_tableCustomFilterEditor.isCollapsible());
        JPanel leftPanel = JideSwingUtilities.createLeftPanel(collapsible);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        panel.add(leftPanel);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(list);
        buttonGroup.add(editor);
        return panel;
    }

    @Override
    public JTable createTable(DefaultTableModel tableModel) {
        _tableModel = setupProductDetailsCalculatedTableModel(tableModel);
        _filterableTableModel = new FilterableTableModel(_tableModel);
        final SortableTable table = new SortableTable(_filterableTableModel);
        table.setAutoResizeMode(SortableTable.AUTO_RESIZE_FILL);
        table.setPreferredScrollableViewportSize(new Dimension(650, 500));
        return table;
    }

    @Override
    public JButton createUpdateButton() {
        return null;
    }

    @Override
    public Component createEditorPanel() {
        _tableCustomFilterEditor = new TableCustomFilterEditor(_tableModel) {
            @Override
            protected IFilterableTableModel createFilterableTableModel(TableModel model) {
                return _filterableTableModel;
            }
        };
        _tableCustomFilterEditor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("TableCustomFilterEditor"),
                BorderFactory.createEmptyBorder(5, 10, 10, 10)));
        return _tableCustomFilterEditor;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new CustomFilterDemo());
            }
        });

    }
}