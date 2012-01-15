/*
 * @(#)LuceneQuickFilterTableDemo.java 4/13/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 *
 */

import com.jidesoft.grid.AutoFilterTableHeader;
import com.jidesoft.grid.QuickTableFilterField;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.jidesoft.lucene.LuceneFilterableTableModel;
import com.jidesoft.lucene.LuceneQuickTableFilterField;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Demoed Component: {@link com.jidesoft.lucene.LuceneFilterableTableModel} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: any L&F
 */
public class LuceneQuickFilterTableDemo extends AbstractDemo {
    private static final long serialVersionUID = 5584665725477627412L;
    private SortableTable _table;

    public LuceneQuickFilterTableDemo() {
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public String getName() {
        return "QuickFilter Demo (Table, using Lucene)";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "This is a demo of LuceneFilterableTableModel and LuceneQuickTableFilterField.\n" +
                "LuceneFilterableTableModel is a special FilterableTableModel which supports lucene query so it has better performance at high volume data scenario.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.lucene.LuceneFilterableTableModel\n" +
                "com.jidesoft.lucene.LuceneQuickTableFilterField";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        AbstractAction buttonActionAddRowsSelection = new AbstractAction("Add one row before the selected row") {
            private static final long serialVersionUID = 4738900276109744653L;

            public void actionPerformed(ActionEvent e) {
                int rowIndex = _table.getSelectionModel().getMinSelectionIndex();
                if (rowIndex == -1) {
                    return;
                }
                int actualRowIndex = TableModelWrapperUtils.getActualRowAt(_table.getModel(), rowIndex, DemoData.ProductTableModel.class);
                DemoData.ProductTableModel innerModel = (DemoData.ProductTableModel) TableModelWrapperUtils.getActualTableModel(_table.getModel(), DemoData.ProductTableModel.class);
                innerModel.getDataVector().add(actualRowIndex, innerModel.getDataVector().get(0));
                innerModel.fireTableRowsInserted(actualRowIndex, actualRowIndex);
            }
        };
        buttonActionAddRowsSelection.putValue(Action.NAME, "Add one row before the selected row");
        JButton addRowsSelection = new JButton(buttonActionAddRowsSelection);

        AbstractAction buttonActionDeleteRow = new AbstractAction("Delete the selected row(s)") {
            private static final long serialVersionUID = -8456309293833159549L;

            public void actionPerformed(ActionEvent e) {
                int startIndex = _table.getSelectionModel().getMinSelectionIndex();
                int endIndex = _table.getSelectionModel().getMaxSelectionIndex();
                for (int rowIndex = endIndex; rowIndex >= startIndex; rowIndex--) {
                    if (rowIndex < 0 || rowIndex >= _table.getRowCount()) {
                        return;
                    }
                    int actualRowIndex = TableModelWrapperUtils.getActualRowAt(_table.getModel(), rowIndex, DemoData.ProductTableModel.class);
                    DemoData.ProductTableModel innerModel = (DemoData.ProductTableModel) TableModelWrapperUtils.getActualTableModel(_table.getModel(), DemoData.ProductTableModel.class);
                    innerModel.removeRow(actualRowIndex);
                }
            }
        };
        buttonActionDeleteRow.putValue(Action.NAME, "Delete the selected row(s)");
        JButton deleteRow = new JButton(buttonActionDeleteRow);

        AbstractAction buttonActionTestDeleteRow = new AbstractAction("Delete almost all rows") {
            private static final long serialVersionUID = -5220068228238042288L;

            public void actionPerformed(ActionEvent e) {
                DemoData.ProductTableModel innerModel = (DemoData.ProductTableModel) TableModelWrapperUtils.getActualTableModel(_table.getModel(), DemoData.ProductTableModel.class);
                if (innerModel.getRowCount() >= 3) {
                    int endRow = innerModel.getRowCount() - 3;
                    for (int i = endRow; i >= 0; i--) {
                        innerModel.getDataVector().remove(i);
                    }
                    innerModel.fireTableRowsDeleted(0, endRow);
                }
            }
        };
        buttonActionTestDeleteRow.putValue(Action.NAME, "Delete almost all rows");
        JButton testDeleteRow = new JButton(buttonActionTestDeleteRow);

        AbstractAction buttonActionFireTableStructureChanged = new AbstractAction("Fire table structure changed event") {
            private static final long serialVersionUID = 4604545883324437771L;

            public void actionPerformed(ActionEvent e) {
                DemoData.ProductTableModel innerModel = (DemoData.ProductTableModel) TableModelWrapperUtils.getActualTableModel(_table.getModel(), DemoData.ProductTableModel.class);
                innerModel.fireTableStructureChanged();
            }
        };
        buttonActionFireTableStructureChanged.putValue(Action.NAME, "Fire table structure changed event");
        JButton fireTableStructureChangedEvent = new JButton(buttonActionFireTableStructureChanged);

        AbstractAction buttonActionFireTableChanged = new AbstractAction("Fire table data changed event") {
            private static final long serialVersionUID = 7284092794187071284L;

            public void actionPerformed(ActionEvent e) {
                DemoData.ProductTableModel innerModel = (DemoData.ProductTableModel) TableModelWrapperUtils.getActualTableModel(_table.getModel(), DemoData.ProductTableModel.class);
                innerModel.fireTableDataChanged();
            }
        };
        buttonActionFireTableChanged.putValue(Action.NAME, "Fire table data changed event");
        JButton fireTableDataChangedEvent = new JButton(buttonActionFireTableChanged);

        AbstractAction buttonActionFireTableRowUpdated = new AbstractAction("Fire table row updated event") {
            private static final long serialVersionUID = 4443293008936148279L;

            public void actionPerformed(ActionEvent e) {
                DemoData.ProductTableModel innerModel = (DemoData.ProductTableModel) TableModelWrapperUtils.getActualTableModel(_table.getModel(), DemoData.ProductTableModel.class);
                if (innerModel.getRowCount() >= 2) {
                    innerModel.fireTableRowsUpdated(0, innerModel.getRowCount() - 2);
                }
            }
        };
        buttonActionFireTableRowUpdated.putValue(Action.NAME, "Fire table row updated event");
        JButton fireTableRowUpdatedEvent = new JButton(buttonActionFireTableRowUpdated);

        AbstractAction buttonActionFireTableCellUpdated = new AbstractAction("Fire table cell updated event") {
            private static final long serialVersionUID = -6493447457782714380L;

            public void actionPerformed(ActionEvent e) {
                DemoData.ProductTableModel innerModel = (DemoData.ProductTableModel) TableModelWrapperUtils.getActualTableModel(_table.getModel(), DemoData.ProductTableModel.class);
                for (int row = innerModel.getRowCount() - 1; row >= Math.max(innerModel.getRowCount() - 11, 0); row--) {
                    innerModel.fireTableCellUpdated(row, 0);
                }
            }
        };
        buttonActionFireTableCellUpdated.putValue(Action.NAME, "Fire table cell updated event");
        JButton fireTableCellUpdatedEvent = new JButton(buttonActionFireTableCellUpdated);

        buttonPanel.add(addRowsSelection);
        buttonPanel.add(deleteRow);
        buttonPanel.add(testDeleteRow);
        buttonPanel.add(fireTableStructureChangedEvent);
        buttonPanel.add(fireTableDataChangedEvent);
        buttonPanel.add(fireTableRowUpdatedEvent);
        buttonPanel.add(fireTableCellUpdatedEvent);
        return buttonPanel;
    }

    public Component getDemoPanel() {
        TableModel model = DemoData.createProductReportsTableModel(false, 0);
        QuickTableFilterField filterField = new LuceneQuickTableFilterField(model);
        filterField.setHintText("Type here to filter");
        filterField.setObjectConverterManagerEnabled(true);

        _table = new SortableTable(new LuceneFilterableTableModel(filterField.getDisplayTableModel()));
        _table.setRowResizable(true);
        _table.setVariousRowHeights(true);

        AutoFilterTableHeader _header = new AutoFilterTableHeader(_table);
        _header.setAutoFilterEnabled(true);
        _header.setUseNativeHeaderRenderer(true);
        _table.setTableHeader(_header);

        JPanel panel = new JPanel(new BorderLayout(3, 3));
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        fieldPanel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "LuceneQuickTableFilterField", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        fieldPanel.add(filterField);

        JPanel tablePanel = new JPanel(new BorderLayout(3, 3));
        tablePanel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Filtered Product Sales Item", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        tablePanel.add(new JScrollPane(_table));

        panel.add(fieldPanel, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(tablePanel);

        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "G15.QuickFilter";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new LuceneQuickFilterTableDemo());
            }
        });

    }
}