/*
 * @(#)LargeSortableTableDemo.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.SortableTableModel;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JidePopupMenu;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Demoed Component: {@link SortableTable} <br> Required jar files: jide-common.jar, jide-grids.jar <br> Required L&F:
 * any L&F
 */
public class LargeSortableTableDemo extends AbstractDemo {
    private static final long serialVersionUID = 8470522350348490198L;
    public DefaultTableModel _model;
    public SortableTable _sortableTable;
    protected JLabel _countLabel;
    protected JSlider _slider;

    public LargeSortableTableDemo() {
    }

    public static class LargeTableModel extends DefaultTableModel {

        private static final long serialVersionUID = -5456719708922578368L;

        @Override
        public Class<?> getColumnClass(int column) {
            switch (column) {
                case 0:
                    return Integer.class;
                case 1:
                    return Double.class;
                case 2:
                    return Boolean.class;
                case 3:
                    return String.class;
            }
            return Object.class;
        }
    }

    public String getName() {
        return "SortableTable Demo (Large Model)";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 2));
        _slider = new JSlider(1, 6, 4);
        panel.add(new JLabel("Data set size (to the power of 10):"));
        panel.add(_slider);
        _countLabel = new JLabel();
        panel.add(_countLabel);
        _slider.setMajorTickSpacing(1);
        _slider.setPaintTrack(true);
        _slider.setSnapToTicks(true);
        _slider.setPaintTicks(true);
        _slider.setPaintLabels(true);
        _slider.setPaintTicks(true);
        _slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!_slider.getValueIsAdjusting()) {
                    clearData();

                    final int value = (int) Math.pow(10, _slider.getValue());
                    populateTable(value);
                }
            }
        });
        populateTable(10000);

        panel.add(Box.createVerticalStrut(6));
        panel.add(new JButton(new AbstractAction("Insert a row") {
            private static final long serialVersionUID = -4663467797343622067L;

            public void actionPerformed(ActionEvent e) {
                int row = _sortableTable.getSelectedRow();
                if (row == -1) {
                    row = _sortableTable.getRowCount();
                }
                _model.insertRow(row, new Object[]{100000, Math.random(), Boolean.FALSE, "new row"});
                int visualRow = _sortableTable.getSortedRowAt(row);
                _sortableTable.changeSelection(visualRow, 0, false, false);
            }
        }));
        panel.add(new JButton(new AbstractAction("Delete selected rows") {
            private static final long serialVersionUID = 6583734909578856612L;

            public void actionPerformed(ActionEvent e) {
                // _sortableTable.getSelectedRows() returns the row indices after sorted,
                // so we need to convert them to the actual row indices as in the actual table model 
                int[] rows = TableModelWrapperUtils.getActualRowsAt(_sortableTable.getModel(), _sortableTable.getSelectedRows(), true);
                for (int i = rows.length - 1; i >= 0; i--) {
                    int row = rows[i];
                    _model.removeRow(row);
                }
            }
        }));
        panel.add(new JButton(new AbstractAction("Delete all rows") {
            private static final long serialVersionUID = -8149334113803021585L;

            public void actionPerformed(ActionEvent e) {
                clearData();
            }
        }));

        return panel;
    }

    private void clearData() {
        int rowCount = _model.getRowCount();
        _model.getDataVector().clear();
        _model.fireTableRowsDeleted(0, rowCount - 1);
    }

    private void populateTable(int value) {
        for (int i = 0; i < value; i++) {
            _model.addRow(new Object[]{i * 1024, Math.random(), i % 2 == 0, "row" + i});
        }
    }

    @Override
    public String getDescription() {
        return "This is a demo of SortableTable with a large table model. You can adjust the slider to get a table model as many as 1 million rows.\n" +
                "\nClick once on the header to sort ascending, click twice to sort descending, a third time to unsort. Hold CTRL key then click on several headers to see multiple columns sorting. With a large table model, you can experience the performance when sorting.\n" +
                "\nTo see the dynamic update when table model changes, press the buttons in options panel.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.SortableTable";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    public Component getDemoPanel() {
        _model = new LargeTableModel();
        _model.addColumn("int");
        _model.addColumn("double");
        _model.addColumn("boolean");
        _model.addColumn("string");

        _sortableTable = new SortableTable(_model);
        _sortableTable.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
        _sortableTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                int column = ((JTableHeader) e.getSource()).getColumnModel().getColumnIndexAtX(e.getPoint().x);
                JMenuItem[] menuItems = ((SortableTableModel) _sortableTable.getModel()).getPopupMenuItems(column);
                JPopupMenu popupMenu = new JidePopupMenu();
                for (JMenuItem item : menuItems) {
                    popupMenu.add(item);
                }
                popupMenu.show((Component) e.getSource(), e.getPoint().x, e.getPoint().y);
            }
        });
        _model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                _countLabel.setText("Total row counts: " + ObjectConverterManager.toString(_model.getRowCount()));
            }
        });

        _sortableTable.setPreferredScrollableViewportSize(new Dimension(550, 400));
        return new JScrollPane(_sortableTable);
    }

    @Override
    public String getDemoFolder() {
        return "G3.LargeSortableTable";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new LargeSortableTableDemo());
            }
        });

    }
}
