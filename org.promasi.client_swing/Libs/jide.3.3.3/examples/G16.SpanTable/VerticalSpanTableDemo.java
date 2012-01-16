/*
 * @(#)VerticalSpanTableDemo.java 6/9/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.CurrencyConverter;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.grid.*;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Demoed Component: {@link com.jidesoft.grid.JideTable} <br> Required jar files: jide-common.jar, jide-grids.jar <br>
 * Required L&F: any L&F
 */
public class VerticalSpanTableDemo extends AbstractDemo {
    private static final long serialVersionUID = 5370475550971726747L;
    public JideTable _table;
    public JLabel _message;
    protected DefaultSpanTableModel _tableModel;

    public VerticalSpanTableDemo() {
    }

    public String getName() {
        return "CellSpanTable Demo (Vertical Text)";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "This is a demo of CellSpanTable. CellSpanTable is a JTable that supports cell spans. \n" +
                "\nTo see cell span, select an area and click on \"Merge selected cells\" to make merge selected cells into one cell. This is what we called CellSpan.\n" +
                "\nTo split an existing cell span, select the CellSpan and click on \"Split selected cell\".\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.DefaultSpanTableModel\n" +
                "com.jidesoft.grid.CellSpanTable";
    }

    private static String[] columns = {
            "", "Item #", "Product Name", "Price"
    };
    private static Object[][] products = {
            {"Individual Products", 2280, "JIDE Docking Framework", 299.99},
            {"Individual Products", 2980, "JIDE Action Framework", 299.99},
            {"Individual Products", 2380, "JIDE Components", 199.99},
            {"Individual Products", 2580, "JIDE Grids", 299.99},
            {"Individual Products", 2780, "JIDE Dialogs", 99.99},
            {"Individual Products", 6180, "JIDE Shortcut Editor", 49.99},
            {"Individual Products", 6280, "JIDE Pivot Grid", 149.99},
            {"Individual Products", 6380, "JIDE Code Editor", 249.99},
            {"Individual Products", 6480, "JIDE Feed Reader", 49.99},
            {"Individual Products", 6580, "JIDE Dashboard", 99.99},
            {"Individual Products", 6680, "JIDE Charts", 249.99},
            {"Individual Products", 6780, "JIDE Gantt Chart", 99.99},
            {"Individual Products", 6880, "JIDE Data Grids", 99.99},
            {"Individual Products", 6980, "JIDE Diff", 49.99},
            {"Individual Products", 6080, "JIDE TreeMap", 149.99},
            {"Individual Products", 8180, "JIDE Desktop Application Framework", 299.99},
            {"Product Suites", 4280, "JIDE Professional Suite", 449.99},
            {"Product Suites", 4680, "JIDE Enterprise Suite", 699.99},
            {"Product Suites", 4880, "JIDE Ultimate Suite", 1399.99},
    };

    static CellStyle CELL_STYLE_CENTER = new CellStyle();

    static {
        CELL_STYLE_CENTER.setHorizontalAlignment(JLabel.CENTER);
    }

    class SpanTableTableModel extends DefaultSpanTableModel implements ContextSensitiveTableModel, StyleModel {
        private static final long serialVersionUID = -5909809759875908976L;

        SpanTableTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 1:
                    return Integer.class;
                case 3:
                    return Double.class;
                default:
                    return String.class;
            }
        }

        public ConverterContext getConverterContextAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 3:
                    return CurrencyConverter.CONTEXT;
                default:
                    return null;
            }
        }

        public EditorContext getEditorContextAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return VerticalContextSensitiveCellRenderer.CONTEXT;
                default:
                    return null;
            }
        }

        public Class<?> getCellClassAt(int rowIndex, int columnIndex) {
            return getColumnClass(columnIndex);
        }

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            return columnIndex == 0 ? CELL_STYLE_CENTER : null;
        }

        public boolean isCellStyleOn() {
            return true;
        }
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        _tableModel = new SpanTableTableModel(products, columns);
        _table = new SortableTable(_tableModel);

        // add example spans
        _tableModel.addCellSpan(new CellSpan(0, 0, 16, 1));
        _tableModel.addCellSpan(new CellSpan(16, 0, 3, 1));

        panel.add(new JScrollPane(_table), BorderLayout.CENTER);
        return panel;
    }

    @Override
    public Component getOptionsPanel() {
        ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);

        return buttonPanel;
    }

    @Override
    public String getDemoFolder() {
        return "G16.SpanTable";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new VerticalSpanTableDemo());
            }
        });

    }
}

