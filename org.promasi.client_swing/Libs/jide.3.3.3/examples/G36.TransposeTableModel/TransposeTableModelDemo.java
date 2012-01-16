/*
 * @(#)TransposeTableModelDemo.java 10/13/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTabbedPane;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Demoed Component: {@link com.jidesoft.grid.TableScrollPane} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
public class TransposeTableModelDemo extends AbstractDemo {

    protected TransposeTableModel _model;
    private static final long serialVersionUID = 633721222142039212L;
    public TableScrollPane _pane;

    public TransposeTableModelDemo() {
    }

    public String getName() {
        return "TransposeTableModel";
    }

    @Override
    public String getDescription() {
        return "This is a demo of TransposeTableModel. TransposeTableModel is a table model wrapper which supports rotating the original table model it wraps.\n" +
                "\nIn this particular demo, we show you how to use it in a TableScrollPane to rotate a table as wish.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.TransposeTableModel\n" +
                "com.jidesoft.grid.TableScrollPane";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 400));
        panel.setLayout(new BorderLayout());
        panel.add(createTabbedPane(), BorderLayout.CENTER);
        return panel;
    }

    private Component createTabbedPane() {
        JideTabbedPane tabbedPane = new JideTabbedPane();
        tabbedPane.setTabShape(JideTabbedPane.SHAPE_BOX);
        tabbedPane.addTab("TransposeTableModel", createTablePane());
        return tabbedPane;

    }

    private TableScrollPane createTablePane() {
        List<DummyRow> list = new ArrayList<DummyRow>();
        for (int i = 0; i < 30; i++) {
            list.add(new DummyRow("Day " + (i + 1)));
        }

        TableModel model = new DummyRowTableModel(list);
        _model = new TransposeTableModel(model);
        _model.setTransposedColumnName("Product");
        _pane = new TableScrollPane(_model, true);
        return _pane;
    }

    @Override
    public String getDemoFolder() {
        return "G36.TransposeTableModel";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JCheckBox transpose = new JCheckBox("Transpose");
        transpose.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _model.setTransposed(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        transpose.setSelected(_model.isTransposed());
        panel.add(transpose);
        return panel;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new TransposeTableModelDemo());
            }
        });

    }

    static class DummyRow extends DefaultExpandableRow {

        private String _name;
        private List<Integer> _values;

        public DummyRow(String name) {
            _name = name;
            _values = new ArrayList<Integer>();
            for (int i = 0; i < 20; i++) {
                _values.add((int) (Math.random() * 40));
            }
        }

        public Object getValueAt(int columnIndex) {
            if (columnIndex == 0) {
                return _name;
            }
            return _values.get(columnIndex - 1);
        }
    }

    private static class DummyRowTableModel extends DefaultContextSensitiveTableModel implements MultiTableModel {
        private static final long serialVersionUID = 8706721423900949194L;
        private List<DummyRow> _list;

        public DummyRowTableModel(List<DummyRow> list) {
            _list = list;
        }

        public int getTableIndex(int columnIndex) {
            return 0;
        }

        public int getColumnType(int columnIndex) {
            return columnIndex < 1 ? HEADER_COLUMN : REGULAR_COLUMN;
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Day";
            }
            else {
                return "Product " + column;
            }
        }

        public int getColumnCount() {
            return 21;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? String.class : Integer.class;
        }

        @Override
        public int getRowCount() {
            return _list != null ? _list.size() : 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return _list != null ? _list.get(rowIndex).getValueAt(columnIndex) : null;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (_list != null) {
                _list.get(row).setValueAt(aValue, column);
            }
        }
    }
}
