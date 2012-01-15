/*
 * @(#)StyledLabelTableHeaderDemo.java 8/17/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.DoubleConverter;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.grid.*;
import com.jidesoft.hssf.HssfTableUtils;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

public class StyledLabelTableHeaderDemo extends AbstractDemo {
    protected SortableTable _sortableTable;
    protected String _lastDirectory = ".";
    private static final long serialVersionUID = -5373007282200581748L;

    public StyledLabelTableHeaderDemo() {
        ObjectConverterManager.initDefaultConverter();
        DecimalFormat format = new DecimalFormat();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(10);
        DoubleConverter converter = new DoubleConverter(format);
        ObjectConverterManager.registerConverter(Double.class, converter);
    }

    public String getName() {
        return "CellStyleTableHeader Demo (StyledLabel)";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    @Override
    public String getDescription() {
        return "This is a demo of CellStyleTableHeader. \n" +
                "\n Resize the columns and see how each column wrap its text of header according to the annotation.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.CellStyleTableHeader";
    }

    public Component getDemoPanel() {
        TableModel model = new SampleTableModel();

        _sortableTable = new SortableTable(model);
        _sortableTable.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
        _sortableTable.setNestedTableHeader(true);
        _sortableTable.setFillsGrids(false);
        TableColumnGroup longText = new TableColumnGroup("This is a very long column header title. You can resize the table header column and see how this long text automatically wraps according to the column width. " +
                "The current configuration specified {minimum two rows:f:red} and {maximum five rows:f:blue}. You could take a look at StyledLabelBuilderDemo to see what other annotations are available in the text.@r:3:2:5");
        longText.add(_sortableTable.getColumnModel().getColumn(0));
        longText.add(_sortableTable.getColumnModel().getColumn(1));
        longText.add(_sortableTable.getColumnModel().getColumn(2));
        longText.add(_sortableTable.getColumnModel().getColumn(3));
        longText.add(_sortableTable.getColumnModel().getColumn(4));
        longText.add(_sortableTable.getColumnModel().getColumn(5));
        longText.add(_sortableTable.getColumnModel().getColumn(6));

        if (_sortableTable.getTableHeader() instanceof NestedTableHeader) {
            NestedTableHeader header = (NestedTableHeader) _sortableTable.getTableHeader();
            header.addColumnGroup(longText);
        }
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_sortableTable);
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());

        TableUtils.autoResizeAllColumns(_sortableTable);

        return new JScrollPane(_sortableTable);
    }

    static class SampleTableModel extends AbstractTableModel implements ToolTipSupport {
        private static final long serialVersionUID = 8798261997256893224L;

        public int getColumnCount() {
            return 8;
        }

        public int getRowCount() {
            return 8;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Java{TM:sp}";
                case 1:
                    return "CO{2:sb}";
                case 2:
                    return "{waved:w}";
                case 3:
                    return "{red:f:red} and {blue:f:blue}";
                case 4:
                    return "A long text with {bold:b} text in a table header@r:2:1:3";
                case 5:
                    return "{strikethrough:s}";
                case 6:
                    return "{double strikethrough:ds}";
                case 7:
                    return "{dotted:d}";
            }
            return "";
        }

        public Object getValueAt(int row, int column) {
            return null;
        }

        public String getToolTipText(int columnIndex) {
            return "Click to sort this " + getColumnName(columnIndex);
        }
    }

    @Override
    public String getDemoFolder() {
        return "G2.SortableTable";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new StyledLabelTableHeaderDemo());
            }
        });

    }

    private void outputToCsv(ActionEvent e) {
        JFileChooser chooser = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setTitle("Export the content to a CSV file");
                return dialog;
            }
        };
        chooser.setCurrentDirectory(new File(_lastDirectory));
        int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
            try {
                System.out.println("Exporting to " + chooser.getSelectedFile().getAbsolutePath());
                CsvTableUtils.export(_sortableTable, chooser.getSelectedFile().getAbsolutePath(), true, new HssfTableUtils.DefaultCellValueConverter() {
                    @Override
                    public int getDataFormat(JTable table, Object value, int rowIndex, int columnIndex) {
                        if (value instanceof Double) {
                            return 2; // use 0.00 format
                        }
                        else if (value instanceof Date) {
                            return 0xe; // use "m/d/yy" format
                        }
                        return super.getDataFormat(table, value, rowIndex, columnIndex);
                    }
                });
                System.out.println("Exported");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void outputToExcel(ActionEvent e) {
        JFileChooser chooser = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setTitle("Export the content to an Excel file");
                return dialog;
            }
        };
        chooser.setCurrentDirectory(new File(_lastDirectory));
        int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
            try {
                System.out.println("Exporting to " + chooser.getSelectedFile().getAbsolutePath());
                HssfTableUtils.export(_sortableTable, chooser.getSelectedFile().getAbsolutePath(), "SortableTable", false, true, new HssfTableUtils.DefaultCellValueConverter() {
                    @Override
                    public int getDataFormat(JTable table, Object value, int rowIndex, int columnIndex) {
                        if (value instanceof Double) {
                            return 2; // use 0.00 format
                        }
                        else if (value instanceof Date) {
                            return 0xe; // use "m/d/yy" format
                        }
                        return super.getDataFormat(table, value, rowIndex, columnIndex);
                    }
                });
                System.out.println("Exported");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void dispose() {
    }
}