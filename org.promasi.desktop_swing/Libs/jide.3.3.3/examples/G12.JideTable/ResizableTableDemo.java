/*
 * @(#)ResizingTableDemo.java 9/8/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.*;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.grid.JideTable} <br> Required jar files: jide-common.jar, jide-grids.jar <br>
 * Required L&F: any L&F
 */
public class ResizableTableDemo extends AbstractDemo {
    private static final long serialVersionUID = 7343096906542360847L;
    public JideTable _table;
    boolean[] _rowFont;

    public ResizableTableDemo() {
    }

    public String getName() {
        return "Resizable Table Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "This is a demo of JideTable with several resizable options.\n" +
                "\nWhen a table is column resizable or row resizable, you drag the grid line " +
                "in the table to resize the column or row respectively." +
                "If a table is column auto resizable, you can double click on the grid line of the table header " +
                "and make the table column automatically resize to the widest size of all cells in that column.\n\n" +
                "The text in all the cells could be wrapped. You can see how auto resize rows feature works by shrinking one of the columns (such as the Name column).\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.TableColumnAutoResizer\n" +
                "com.jidesoft.grid.TableRowAutoResizer\n" +
                "com.jidesoft.grid.TableColumnResizer\n" +
                "com.jidesoft.grid.TableRowResizer\n" +
                "com.jidesoft.grid.JideTable";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel switchPanel = new JPanel(new GridLayout(0, 1));
        final JCheckBox option1 = new JCheckBox("Enable Column Chooser");
        final JCheckBox option2 = new JCheckBox("Column Auto-resizable when Double Clicking on the Header");
        final JCheckBox option3 = new JCheckBox("Column Resizable when Dragging the Vertical Grid Line");
        final JCheckBox option4 = new JCheckBox("Row Resizable when Dragging the Horizontal Grid Line");
        final JCheckBox option5 = new JCheckBox("Keep Rows at the Same Height");

        switchPanel.add(option1);
        switchPanel.add(option2);
        switchPanel.add(option3);
        switchPanel.add(option4);
        switchPanel.add(option5);

        option1.setSelected(true);
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_table);
        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
        option1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (option1.isSelected()) {
                    TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_table);
                    installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
                    installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
                }
                else {
                    TableHeaderPopupMenuInstaller.getTableHeaderPopupMenuInstaller(_table).uninstallListeners();
                }
            }
        });

        option2.setSelected(_table.isColumnAutoResizable());
        option2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setColumnAutoResizable(option2.isSelected());
            }
        });

        option3.setSelected(_table.isColumnResizable());
        option3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setColumnResizable(option3.isSelected());
            }
        });

        option4.setSelected(_table.isRowResizable());
        option4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setRowResizable(option4.isSelected());
            }
        });

        option5.setSelected(!_table.isVariousRowHeights());
        option5.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setVariousRowHeights(!option5.isSelected());
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        buttonPanel.add((new JButton(new AbstractAction("Auto resize all the columns") {
            private static final long serialVersionUID = -4810373483691452043L;

            public void actionPerformed(ActionEvent e) {
                TableUtils.autoResizeAllColumns(_table);
            }
        })));

        buttonPanel.add((new JButton(new AbstractAction("Auto resize all the rows") {
            private static final long serialVersionUID = -7074906006322128745L;

            public void actionPerformed(ActionEvent e) {
                TableUtils.autoResizeAllRows(_table);
            }
        })));

        buttonPanel.add((new JButton(new AbstractAction("Auto resize the selected rows") {
            private static final long serialVersionUID = 8165796026666212819L;

            public void actionPerformed(ActionEvent e) {
                int fromRow = _table.getSelectionModel().getMinSelectionIndex();
                int toRow = _table.getSelectionModel().getMaxSelectionIndex();
                if (fromRow != -1 && toRow != -1) {
                    TableUtils.autoResizeRows(_table, fromRow, toRow);
                }
            }
        })));

        JPanel optionPanel = new JPanel(new BorderLayout(6, 6));
        optionPanel.add(new JLabel("Options: "), BorderLayout.BEFORE_FIRST_LINE);
        optionPanel.add(switchPanel, BorderLayout.CENTER);
        optionPanel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
        return optionPanel;
    }

    class QuoteTableModel extends DefaultTableModel implements ContextSensitiveTableModel, StyleModel {
        private static final long serialVersionUID = 8709426264095661391L;
        CellStyle _biggerFont;

        public QuoteTableModel() {
            super(DemoData.QUOTES, DemoData.QUOTE_COLUMNS);
            ResizableTableDemo.this._rowFont = new boolean[getRowCount()];
            for (int i = 0; i < getRowCount(); i++) {
                ResizableTableDemo.this._rowFont[i] = false;
            }
            _biggerFont = new CellStyle();
            _biggerFont.setFont(new Font("Monospaced", Font.BOLD, 17));
        }

        public ConverterContext getConverterContextAt(int rowIndex, int columnIndex) {
            return null;
        }

        public EditorContext getEditorContextAt(int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                return MultilineTableCellRenderer.CONTEXT;
            }
            return null;
        }

        public Class<?> getCellClassAt(int rowIndex, int columnIndex) {
            return String.class;
        }

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            if (ResizableTableDemo.this._rowFont[rowIndex]) {
                return _biggerFont;
            }
            return null;
        }

        public boolean isCellStyleOn() {
            return true;
        }

        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        _table = new SortableTable(new QuoteTableModel());
        _table.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
        _table.setColumnResizable(true);
        _table.setRowResizable(true);
        _table.setVariousRowHeights(true);

        _table.setColumnAutoResizable(true);

        _table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        _table.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

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
                showAsFrame(new ResizableTableDemo());
            }
        });

    }
}

