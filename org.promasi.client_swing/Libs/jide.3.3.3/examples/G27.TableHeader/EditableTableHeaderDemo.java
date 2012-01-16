/*
 * @(#)EditableTableHeaderDemo.java 2/14/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.comparator.ObjectComparatorManager;
import com.jidesoft.converter.DoubleConverter;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.grid.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Demoed Component: {@link com.jidesoft.grid.EditableTableHeader} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: any L&F
 */
public class EditableTableHeaderDemo extends AbstractDemo {
    private static final long serialVersionUID = 2635779450724286413L;

    public EditableTableHeaderDemo() {
        CellEditorManager.initDefaultEditor();
        CellRendererManager.initDefaultRenderer();
        ObjectConverterManager.initDefaultConverter();
        ObjectComparatorManager.initDefaultComparator();
        DoubleConverter converter = new DoubleConverter();
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(2);
        converter.setNumberFormat(numberInstance);
        ObjectConverterManager.registerConverter(Double.class, converter);
    }

    public String getName() {
        return "EditableTableHeader Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.EditableTableHeader";
    }

    public Component getDemoPanel() {
        final TableModel tableModel = createProductReportsTableModel();
        if (tableModel == null) {
            return new JLabel("Failed to read data file");
        }

        return createSortableTable(tableModel);
    }

    private JPanel createSortableTable(TableModel tableModel) {
        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "ProductName and ShippedDate columns are editable; single click to start editing", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        JTable sortableTable = new SortableTable(tableModel);
        EditableTableHeader header = new EditableTableHeader(sortableTable.getColumnModel());
        sortableTable.setTableHeader(header);
        JScrollPane scrollPane = new JScrollPane(sortableTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);
        return panel;
    }

    private TableModel createProductReportsTableModel() {
        Vector[] data = DemoData.getProductReportsData(1, 0);
        return new EditableTableHeaderTableModel(data[0], data[1]);
    }

    static class EditableTableHeaderTableModel extends DefaultContextSensitiveTableModel implements EditableColumnTableModel {
        private static final long serialVersionUID = -2003405108941836306L;

        public EditableTableHeaderTableModel(Vector data, Vector columnNames) {
            super(data, columnNames);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                    return String.class;
                case 2:
                    return float.class;
                case 3:
                    return Date.class;
            }
            return super.getColumnClass(columnIndex);
        }

        public boolean isColumnHeaderEditable(int columnIndex) {
            return columnIndex == 1 || columnIndex == 3;
        }

        public TableCellEditor getColumnHeaderCellEditor(int columnIndex) {
            return null;
        }
    }

    @Override
    public String getDemoFolder() {
        return "G27.AutoFilterTableHeader";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new EditableTableHeaderDemo());
            }
        });

    }
}
