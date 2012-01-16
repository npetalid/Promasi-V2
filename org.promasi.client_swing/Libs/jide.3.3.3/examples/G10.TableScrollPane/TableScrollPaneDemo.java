/*
 * @(#)TableScrollPaneDemo.java
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.DoubleConverter;
import com.jidesoft.grid.*;
import com.jidesoft.hssf.HssfTableScrollPaneUtils;
import com.jidesoft.hssf.HssfTableUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTabbedPane;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.utils.Lm;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

/**
 * Demoed Component: {@link com.jidesoft.grid.TableScrollPane} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
public class TableScrollPaneDemo extends AbstractDemo {

    protected MultiTableModel _totalModel;
    protected MultiTableModel _subHeaderModel;
    protected MultiTableModel _model;
    public TableScrollPane _pane;
    private String _tablePref;
    private String _lastDirectory = ".";
    private static final long serialVersionUID = -5850105228695796397L;
    protected Color COLOR_MAIN = new Color(255, 254, 203);
    protected Color COLOR_HEADER = new Color(233, 254, 203);
    protected Color COLOR_CORNER = new Color(32, 32, 32);
    private static CellStyle SALES_STYLE = new CellStyle();
    private static CellStyle PROFITS_STYLE = new CellStyle();
    private static CellStyle BOLD_STYLE = new CellStyle();

    private static CellStyle FOOTER_STYLE = new CellStyle();

    static {
        SALES_STYLE.setForeground(new Color(0, 128, 0));
        SALES_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        PROFITS_STYLE.setForeground(Color.blue);
        PROFITS_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        BOLD_STYLE.setFontStyle(Font.BOLD);
        BOLD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);

        FOOTER_STYLE.setForeground(Color.YELLOW);
    }

    public TableScrollPaneDemo() {
    }

    public String getName() {
        return "TableScrollPane Demo";
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    @Override
    public String getDescription() {
        return "This is a demo of TableScrollPane. TableScrollPane is a special component which supports table row header, row footer and column footer.\n" +
                "\nIt also shows how to archive multiple line cell renderer. Try to resize \"Task\" column to make it smaller and see what happens.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.TableScrollPane\n" +
                "com.jidesoft.grid.JideTable";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public Component getOptionsPanel() {
        JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JCheckBox allowMultiSelection = new JCheckBox("Allow Multi Selection in Different Tables");
        allowMultiSelection.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _pane.setAllowMultiSelectionInDifferentTable(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        allowMultiSelection.setSelected(_pane.isAllowMultiSelectionInDifferentTable());
        checkBoxPanel.add(allowMultiSelection);

        JButton savePrefButton = new JButton(new AbstractAction("Save Preference") {
            private static final long serialVersionUID = -1383609484861319779L;

            public void actionPerformed(ActionEvent e) {
                _tablePref = TableUtils.getTablePreferenceByName(_pane);
                Lm.showPopupMessageBox("<HTML>The table column width and column order information has been saved. You can change width and order in the table now. " +
                        "<BR>After you are done with it, press \"Load Preference\" to restore the saved column width and order.</HTML>");
            }
        });
        JButton loadPrefButton = new JButton(new AbstractAction("Load Preference") {
            private static final long serialVersionUID = 8541271321784337549L;

            public void actionPerformed(ActionEvent e) {
                TableUtils.setTablePreferenceByName(_pane, _tablePref);
            }
        });
        JButton loadWidthButton = new JButton(new AbstractAction("Load Width only") {
            private static final long serialVersionUID = -6178675632788894514L;

            public void actionPerformed(ActionEvent e) {
                TableUtils.setTableColumnWidthByName(_pane, _tablePref);
            }
        });

        checkBoxPanel.add(savePrefButton);
        checkBoxPanel.add(loadPrefButton);
        checkBoxPanel.add(loadWidthButton);
        return checkBoxPanel;
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
        tabbedPane.addTab("TableScrollPane", createTablePane());
        SortableTable sortableTable = new SortableTable(new DummyTableModel());
        sortableTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabbedPane.addTab("Table in JScrollPane (for comparison)", new JScrollPane(sortableTable));
        return tabbedPane;

    }

    private TableScrollPane createTablePane() {
        _model = new DummyTableModel();
        _totalModel = new DummyFooterTableModel(_model);
        _subHeaderModel = new DummyHeaderTableModel(_model);
        _pane = new TableScrollPane(_model, _subHeaderModel, _totalModel, true);

        ((JideTable) _pane.getMainTable()).setNestedTableHeader(true);
        TableColumnModel columnModel = _pane.getMainTable().getColumnModel();
        TableColumnGroup sales = new TableColumnGroup("Sales (million dollar)@rows");
        sales.add(columnModel.getColumn(0));
        sales.add(columnModel.getColumn(1));
        sales.add(columnModel.getColumn(2));
        sales.add(columnModel.getColumn(3));
        TableColumnGroup profits = new TableColumnGroup("After-tax Profits (million dollar)@rows");
        profits.add(columnModel.getColumn(4));
        profits.add(columnModel.getColumn(5));
        profits.add(columnModel.getColumn(6));
        profits.add(columnModel.getColumn(7));

        if (_pane.getMainTable().getTableHeader() instanceof NestedTableHeader) {
            NestedTableHeader header = (NestedTableHeader) _pane.getMainTable().getTableHeader();
            header.addColumnGroup(sales);
            header.addColumnGroup(profits);
        }

        _pane.getRowHeaderTable().setBackground(COLOR_MAIN);
        _pane.getMainTable().setBackground(COLOR_MAIN);
        _pane.getRowFooterTable().setBackground(COLOR_MAIN);

        _pane.getColumnHeaderTable().setBackground(COLOR_HEADER);
        _pane.getRowHeaderColumnHeaderTable().setBackground(COLOR_HEADER);
        _pane.getRowFooterColumnHeaderTable().setBackground(COLOR_HEADER);

        _pane.getColumnFooterTable().setBackground(COLOR_CORNER);
        _pane.getRowHeaderColumnFooterTable().setBackground(COLOR_CORNER);
        _pane.getRowFooterColumnFooterTable().setBackground(COLOR_CORNER);

        Border border = new PartialLineBorder(Color.DARK_GRAY, 2, PartialSide.SOUTH);
        _pane.getRowHeaderColumnHeaderTable().setBorder(border);
        _pane.getColumnHeaderTable().setBorder(border);
        _pane.getRowFooterColumnHeaderTable().setBorder(border);

        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_pane.getMainTable()) {
            @Override
            protected void customizeMenuItems(final JTableHeader header, final JPopupMenu popup, final int clickingColumn) {
                super.customizeMenuItems(header, popup, clickingColumn);

                addSeparatorIfNecessary(popup);

                final JMenuItem export = new JMenuItem(new AbstractAction("Export to Excel 2003 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _pane.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
                        if (!HssfTableUtils.isHssfInstalled()) {
                            JOptionPane.showMessageDialog((Component) e.getSource(), "Export to Excel feature is disabled because POI-HSSF jar is missing in the classpath.");
                            return;
                        }
                        outputToExcel(e);
                    }
                });

                final JMenuItem export2007 = new JMenuItem(new AbstractAction("Export to Excel 2007 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _pane.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2007);
                        if (!HssfTableUtils.isXssfInstalled()) {
                            JOptionPane.showMessageDialog((Component) e.getSource(), "Export to Excel 2007 feature is disabled because one or several POI-XSSF dependency jars are missing in the classpath. Please include all the jars from poi release in the classpath and try to run again.");
                            return;
                        }
                        outputToExcel(e);
                    }
                });

                final JMenuItem exportToCsv = new JMenuItem(new AbstractAction("Export to CSV format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        outputToCsv(e);
                    }
                });
                popup.add(export);
                popup.add(export2007);
                popup.add(exportToCsv);
            }
        };
        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());

        TableHeaderPopupMenuInstaller installer2 = new TableHeaderPopupMenuInstaller(_pane.getRowFooterTable()) {
            @Override
            protected void customizeMenuItems(final JTableHeader header, final JPopupMenu popup, final int clickingColumn) {
                super.customizeMenuItems(header, popup, clickingColumn);

                addSeparatorIfNecessary(popup);

                final JMenuItem export = new JMenuItem(new AbstractAction("Export to Excel 2003 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _pane.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
                        if (!HssfTableUtils.isHssfInstalled()) {
                            JOptionPane.showMessageDialog((Component) e.getSource(), "Export to Excel feature is disabled because POI-HSSF jar is missing in the classpath.");
                            return;
                        }
                        outputToExcel(e);
                    }
                });

                final JMenuItem export2007 = new JMenuItem(new AbstractAction("Export to Excel 2007 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _pane.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2007);
                        if (!HssfTableUtils.isXssfInstalled()) {
                            JOptionPane.showMessageDialog((Component) e.getSource(), "Export to Excel 2007 feature is disabled because one or several POI-XSSF dependency jars are missing in the classpath. Please include all the jars from poi release in the classpath and try to run again.");
                            return;
                        }
                        outputToExcel(e);
                    }
                });

                final JMenuItem exportToCsv = new JMenuItem(new AbstractAction("Export to CSV format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        outputToCsv(e);
                    }
                });
                popup.add(export);
                popup.add(export2007);
                popup.add(exportToCsv);
            }
        };
        installer2.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer2.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());

        TableHeaderPopupMenuInstaller installer3 = new TableHeaderPopupMenuInstaller(_pane.getRowHeaderTable()) {
            @Override
            protected void customizeMenuItems(final JTableHeader header, final JPopupMenu popup, final int clickingColumn) {
                super.customizeMenuItems(header, popup, clickingColumn);

                addSeparatorIfNecessary(popup);

                final JMenuItem export = new JMenuItem(new AbstractAction("Export to Excel 2003 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _pane.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
                        if (!HssfTableUtils.isHssfInstalled()) {
                            JOptionPane.showMessageDialog((Component) e.getSource(), "Export to Excel feature is disabled because POI-HSSF jar is missing in the classpath.");
                            return;
                        }
                        outputToExcel(e);
                    }
                });

                final JMenuItem export2007 = new JMenuItem(new AbstractAction("Export to Excel 2007 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _pane.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2007);
                        if (!HssfTableUtils.isXssfInstalled()) {
                            JOptionPane.showMessageDialog((Component) e.getSource(), "Export to Excel 2007 feature is disabled because one or several POI-XSSF dependency jars are missing in the classpath. Please include all the jars from poi release in the classpath and try to run again.");
                            return;
                        }
                        outputToExcel(e);
                    }
                });

                final JMenuItem exportToCsv = new JMenuItem(new AbstractAction("Export to CSV format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        outputToCsv(e);
                    }
                });
                popup.add(export);
                popup.add(export2007);
                popup.add(exportToCsv);
            }
        };
        installer3.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer3.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());

        return _pane;
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
        int result = chooser.showDialog(((JMenuItem) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
            try {
                HssfTableScrollPaneUtils.export(_pane, chooser.getSelectedFile().getAbsolutePath(), "TreeTableScrollPane", false);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
        int result = chooser.showDialog(((JMenuItem) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
            try {
                CsvTableScrollPaneUtils.export(_pane, chooser.getSelectedFile().getAbsolutePath());
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getDemoFolder() {
        return "G10.TableScrollPane";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new TableScrollPaneDemo());
            }
        });

    }

    class DummyTableModel extends AbstractMultiTableModel implements ColumnIdentifierTableModel, HeaderStyleModel, StyleModel {
        private static final long serialVersionUID = 7142342324546147914L;
        private Double[][] values;

        public CellStyle getHeaderStyleAt(int rowIndex, int columnIndex) {
            if (columnIndex >= 1 && columnIndex <= 4) {
                return SALES_STYLE;
            }
            else if (columnIndex >= 5 && columnIndex <= 8) {
                return PROFITS_STYLE;
            }
            return BOLD_STYLE;
        }

        public boolean isHeaderStyleOn() {
            return true;
        }

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            if (columnIndex >= 1 && columnIndex <= 4) {
                return SALES_STYLE;
            }
            else if (columnIndex >= 5 && columnIndex <= 8) {
                return PROFITS_STYLE;
            }
            return null;
        }

        public boolean isCellStyleOn() {
            return true;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Year";
                case 1:
                    return "Q1";
                case 2:
                    return "Q2";
                case 3:
                    return "Q3";
                case 4:
                    return "Q4";
                case 5:
                    return "Q1";
                case 6:
                    return "Q2";
                case 7:
                    return "Q3";
                case 8:
                    return "Q4";
                case 9:
                    return "Sales Total@rows:1:1:2";
                case 10:
                    return "After-tax Profits Total@rows:2:1:2";
            }
            return "Column " + (column + 1);
        }

        public Object getColumnIdentifier(int column) {
            switch (column) {
                case 0:
                    return "Year";
                case 1:
                    return "Sales Q1";
                case 2:
                    return "Sales Q2";
                case 3:
                    return "Sales Q3";
                case 4:
                    return "Sales Q4";
                case 5:
                    return "Profits Q1";
                case 6:
                    return "Profits Q2";
                case 7:
                    return "Profits Q3";
                case 8:
                    return "Profits Q4";
                case 9:
                    return "Sales Total";
                case 10:
                    return "After-tax Profits Total";
            }
            return "Column " + (column + 1);
        }

        public int getColumnCount() {
            return 11;
        }

        public int getRowCount() {
            return 50;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? String.class : Double.class;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex > 10) {
                if (columnIndex == 0) {
                    return "";
                }
                else {
                    return null;
                }
            }
            if (values == null) {
                values = new Double[11][8];
                values[0][0] = 1398695d;
                values[0][1] = 1430276d;
                values[0][2] = 1447157d;
                values[0][3] = 1490965d;
                values[0][4] = 110934d;
                values[0][5] = 107759d;
                values[0][6] = 124032d;
                values[0][7] = 135152d;
                values[1][0] = 1241294d;
                values[1][1] = 1226253d;
                values[1][2] = 1289095d;
                values[1][3] = 1353997d;
                values[1][4] = 36559d;
                values[1][5] = 49946d;
                values[1][6] = 95282d;
                values[1][7] = 104617d;
                values[2][0] = 1623674d;
                values[2][1] = 1687223d;
                values[2][2] = 1661264d;
                values[2][3] = 1400121d;
                values[2][4] = 120311d;
                values[2][5] = 99284d;
                values[2][6] = 120320d;
                values[2][7] = -73490d;
                values[3][0] = 1456498d;
                values[3][1] = 1494618d;
                values[3][2] = 1521356d;
                values[3][3] = 1585911d;
                values[3][4] = 120354d;
                values[3][5] = 126496d;
                values[3][6] = 76348d;
                values[3][7] = 119686d;
                values[4][0] = 1445585d;
                values[4][1] = 1454570d;
                values[4][2] = 1452573d;
                values[4][3] = 1428549d;
                values[4][4] = 122653d;
                values[4][5] = 112943d;
                values[4][6] = 122834d;
                values[4][7] = 111922d;
                values[5][0] = 1300210d;
                values[5][1] = 1322831d;
                values[5][2] = 1373056d;
                values[5][3] = 1412359d;
                values[5][4] = 92180d;
                values[5][5] = 97277d;
                values[5][6] = 105470d;
                values[5][7] = 106082d;
                values[6][0] = 1182609d;
                values[6][1] = 1222192d;
                values[6][2] = 1243017d;
                values[6][3] = 1284205d;
                values[6][4] = 77185d;
                values[6][5] = 85249d;
                values[6][6] = 87432d;
                values[6][7] = 97998d;
                values[7][0] = 1101612d;
                values[7][1] = 1073429d;
                values[7][2] = 1103876d;
                values[7][3] = 1114595d;
                values[7][4] = 59496d;
                values[7][5] = 48201d;
                values[7][6] = 50763d;
                values[7][7] = 78128d;
                values[8][0] = 1024201d;
                values[8][1] = 1048687d;
                values[8][2] = 1064492d;
                values[8][3] = 1077936d;
                values[8][4] = 25422d;
                values[8][5] = 36375d;
                values[8][6] = 38694d;
                values[8][7] = 39239d;
                values[9][0] = 1114705d;
                values[9][1] = 1093520d;
                values[9][2] = 1058748d;
                values[9][3] = 1029272d;
                values[9][4] = -133d;
                values[9][5] = 14493d;
                values[9][6] = 8353d;
                values[9][7] = 13126d;
                values[10][0] = 1119619d;
                values[10][1] = 1127677d;
                values[10][2] = 1143982d;
                values[10][3] = 1122837d;
                values[10][4] = 77113d;
                values[10][5] = 69331d;
                values[10][6] = 71194d;
                values[10][7] = 52707d;
            }
            if (columnIndex == 0) {
                return 2011 - 1 - rowIndex;
            }
            else if (columnIndex <= 8 && rowIndex <= 10) {
                return values[rowIndex][columnIndex - 1];
            }
            else if (columnIndex == 9) {
                Double summary = 0d;
                for (int i = 1; i <= 4; i++) {
                    Object value = getValueAt(rowIndex, i);
                    if (value instanceof Double) {
                        summary += (Double) value;
                    }
                }
                return summary;
            }
            else if (columnIndex == 10) {
                Double summary = 0d;
                for (int i = 5; i <= 8; i++) {
                    Object value = getValueAt(rowIndex, i);
                    if (value instanceof Double) {
                        summary += (Double) value;
                    }
                }
                return summary;
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public int getTableIndex(int columnIndex) {
            return 0;
        }

        public int getColumnType(int column) {
            if (column < 1) {
                return HEADER_COLUMN;
            }
            else if (column >= getColumnCount() - 2) {
                return FOOTER_COLUMN;
            }
            else {
                return REGULAR_COLUMN;
            }
        }

        @Override
        public Class<?> getCellClassAt(int row, int column) {
            return getColumnClass(column);
        }

        @Override
        public ConverterContext getConverterContextAt(int row, int column) {
            return column >= 1 ? DoubleConverter.CONTEXT_FRACTION_NUMBER : null;
        }
    }

    class DummyHeaderTableModel extends AbstractMultiTableModel implements ColumnIdentifierTableModel, StyleModel {
        TableModel _model;
        private static final long serialVersionUID = -9132647394140127017L;

        public DummyHeaderTableModel(TableModel model) {
            _model = model;
        }

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            return ((StyleModel) _model).getCellStyleAt(0, columnIndex);
        }

        public boolean isCellStyleOn() {
            return true;
        }

        @Override
        public String getColumnName(int column) {
            return _model.getColumnName(column);
        }

        public Object getColumnIdentifier(int columnIndex) {
            return ((ColumnIdentifierTableModel) _model).getColumnIdentifier(columnIndex);
        }

        public int getColumnCount() {
            return _model.getColumnCount();
        }

        public int getRowCount() {
            return 1;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return _model.getColumnClass(columnIndex);
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return "2011";
            }
            else if (columnIndex == 1) {
                return 1596666d;
            }
            else if (columnIndex == 5) {
                return 144463d;
            }
            else if (columnIndex == 9) {
                Double summary = 0d;
                for (int i = 1; i <= 4; i++) {
                    Object value = getValueAt(rowIndex, i);
                    if (value instanceof Double) {
                        summary += (Double) value;
                    }
                }
                return summary;
            }
            else if (columnIndex == 10) {
                Double summary = 0d;
                for (int i = 5; i <= 8; i++) {
                    Object value = getValueAt(rowIndex, i);
                    if (value instanceof Double) {
                        summary += (Double) value;
                    }
                }
                return summary;
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public int getColumnType(int column) {
            return ((MultiTableModel) _model).getColumnType(column);
        }

        public int getTableIndex(int columnIndex) {
            return 0;
        }

        @Override
        public Class<?> getCellClassAt(int row, int column) {
            return getColumnClass(column);
        }

        @Override
        public ConverterContext getConverterContextAt(int row, int column) {
            return column >= 1 ? DoubleConverter.CONTEXT_FRACTION_NUMBER : null;
        }
    }

    class DummyFooterTableModel extends AbstractMultiTableModel implements ColumnIdentifierTableModel, StyleModel {
        TableModel _model;
        private static final long serialVersionUID = -9132647394140127017L;

        public DummyFooterTableModel(TableModel model) {
            _model = model;
        }

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            return FOOTER_STYLE;
        }

        public boolean isCellStyleOn() {
            return true;
        }

        @Override
        public String getColumnName(int column) {
            return _model.getColumnName(column);
        }

        public Object getColumnIdentifier(int columnIndex) {
            return ((ColumnIdentifierTableModel) _model).getColumnIdentifier(columnIndex);
        }

        public int getColumnCount() {
            return _model.getColumnCount();
        }

        public int getRowCount() {
            return 1;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return _model.getColumnClass(columnIndex);
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return "Average";
            }
            else {
                Double summary = 0d;
                for (int i = 0; i < _model.getRowCount(); i++) {
                    Object value = _model.getValueAt(i, columnIndex);
                    if (value instanceof Double) {
                        summary += (Double) value;
                    }
                }
                return summary / 11;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public int getColumnType(int column) {
            return ((MultiTableModel) _model).getColumnType(column);
        }

        public int getTableIndex(int columnIndex) {
            return 0;
        }

        @Override
        public Class<?> getCellClassAt(int row, int column) {
            return getColumnClass(column);
        }

        @Override
        public ConverterContext getConverterContextAt(int row, int column) {
            return column >= 1 ? DoubleConverter.CONTEXT_FRACTION_NUMBER : null;
        }
    }
}
