/*
 * @(#)GroupableTableModelDemo.java 4/13/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.*;
import com.jidesoft.grid.*;
import com.jidesoft.grouper.date.DateMonthGrouper;
import com.jidesoft.grouper.date.DateQuarterGrouper;
import com.jidesoft.grouper.date.DateWeekOfYearGrouper;
import com.jidesoft.grouper.date.DateYearGrouper;
import com.jidesoft.hssf.HssfTableUtils;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.SearchableBar;
import com.jidesoft.swing.TableSearchable;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Demoed Component: {@link com.jidesoft.grid.JideTable} <br> Required jar files: jide-common.jar, jide-grids.jar <br>
 * Required L&F: any L&F
 */
public class GroupTableDemo extends AbstractDemo {
    public GroupTable _table;
    public JLabel _message;
    protected TableModel _tableModel;
    private DefaultGroupTableModel _groupTableModel;
    private boolean _useStyle;
    private String _lastDirectory = ".";
    private static final long serialVersionUID = 256315903870338341L;

    public GroupTableDemo() {
        ObjectConverterManager.initDefaultConverter();
        // instead of using the default toString method provided by DefaultGroupRow, we add our own
        // converter so that we can display the number of items.
        ObjectConverterManager.registerConverter(DefaultGroupRow.class, new ObjectConverter() {
            public String toString(Object object, ConverterContext context) {
                if (object instanceof DefaultGroupRow) {
                    DefaultGroupRow row = (DefaultGroupRow) object;
                    StringBuffer buf = new StringBuffer(row.toString());
                    int allVisibleChildrenCount = TreeTableUtils.getDescendantCount(_table.getModel(), row, true, true);
                    buf.append(" (").append(allVisibleChildrenCount).append(" items)");
                    return buf.toString();
                }
                return null;
            }

            public boolean supportToString(Object object, ConverterContext context) {
                return true;
            }

            public Object fromString(String string, ConverterContext context) {
                return null;
            }

            public boolean supportFromString(String string, ConverterContext context) {
                return false;
            }
        });
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public String getName() {
        return "GroupTable Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }


    protected static final Color BACKGROUND1 = new Color(159, 155, 217);
    protected static final Color BACKGROUND2 = new Color(197, 194, 232);

    public static final CellStyle style1 = new CellStyle();
    public static final CellStyle style2 = new CellStyle();
    public static final CellStyle styleGroup1 = new CellStyle();
    public static final CellStyle styleGroup2 = new CellStyle();

    static {
        style1.setBackground(BACKGROUND1);
        style2.setBackground(BACKGROUND2);
        style1.setHorizontalAlignment(SwingConstants.CENTER);
        style2.setHorizontalAlignment(SwingConstants.CENTER);
        styleGroup1.setBackground(BACKGROUND1);
        styleGroup2.setBackground(BACKGROUND2);
        styleGroup1.setFontStyle(Font.BOLD);
        styleGroup2.setFontStyle(Font.BOLD);
    }

    @Override
    public Component getOptionsPanel() {
        JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1, 0, 2));
        JCheckBox singleLevel = new JCheckBox("Single Level");
        singleLevel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _groupTableModel.setSingleLevelGrouping(e.getStateChange() == ItemEvent.SELECTED);
                _groupTableModel.groupAndRefresh();
                _groupTableModel.fireTableStructureChanged();
                _table.expandAll();
            }
        });
        singleLevel.setSelected(_groupTableModel.isSingleLevelGrouping());

        JCheckBox keepColumnOrder = new JCheckBox("Keep Column Order");
        keepColumnOrder.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String preference = TableUtils.getTablePreferenceByName(_table);
                _groupTableModel.setKeepColumnOrder(e.getStateChange() == ItemEvent.SELECTED);
                _groupTableModel.groupAndRefresh();
                _groupTableModel.fireTableStructureChanged();
                TableUtils.setTablePreferenceByName(_table, preference);
                _table.expandAll();
            }
        });
        keepColumnOrder.setSelected(_groupTableModel.isKeepColumnOrder());

        JCheckBox showGroupColumns = new JCheckBox("Show Group Columns");
        showGroupColumns.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _groupTableModel.setDisplayGroupColumns(e.getStateChange() == ItemEvent.SELECTED);
                _groupTableModel.groupAndRefresh();
                _groupTableModel.fireTableStructureChanged();
                _table.expandAll();
            }
        });
        showGroupColumns.setSelected(_groupTableModel.isDisplayGroupColumns());

        JCheckBox showCountColumn = new JCheckBox("Show Count Columns");
        showCountColumn.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _groupTableModel.setDisplayCountColumn(e.getStateChange() == ItemEvent.SELECTED);
                _groupTableModel.groupAndRefresh();
                _groupTableModel.fireTableStructureChanged();
                TableUtils.saveColumnOrders(_table, false);
                _table.expandAll();
            }
        });
        showCountColumn.setSelected(_groupTableModel.isDisplayCountColumn());

        JCheckBox showSeparateGroupColumn = new JCheckBox("Show Separate Group Column");
        showSeparateGroupColumn.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _groupTableModel.setDisplaySeparateGroupColumn(e.getStateChange() == ItemEvent.SELECTED);
                _groupTableModel.groupAndRefresh();
                _groupTableModel.fireTableStructureChanged();
                TableUtils.saveColumnOrders(_table, false);
                _table.expandAll();
            }
        });
        showSeparateGroupColumn.setSelected(_groupTableModel.isDisplaySeparateGroupColumn());

        JCheckBox showStyle = new JCheckBox("Add Styles");
        showStyle.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _useStyle = e.getStateChange() == ItemEvent.SELECTED;
                _table.setShowGrid(!_useStyle);
                _table.setIntercellSpacing(_useStyle ? new Dimension(0, 0) : new Dimension(1, 1));
                _table.setShowTreeLines(!_useStyle);
                _table.repaint();
            }
        });
        showStyle.setSelected(false);

        final JCheckBox showConnectLine = new JCheckBox("Show Connection Line");
        showConnectLine.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (_table.getTableHeader() instanceof GroupTableHeader) {
                    ((GroupTableHeader) _table.getTableHeader()).setConnectionLineVisible(e.getStateChange() == ItemEvent.SELECTED);
                }
            }
        });
        showConnectLine.setSelected(true);
        showConnectLine.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        final JCheckBox showIndention = new JCheckBox("Indent Group Column");
        showIndention.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (_table.getTableHeader() instanceof GroupTableHeader) {
                    boolean enabled = e.getStateChange() == ItemEvent.SELECTED;
                    ((GroupTableHeader) _table.getTableHeader()).setVerticalIndention(enabled ? -1 : 0);
                    showConnectLine.setEnabled(enabled);
                }
            }
        });
        showIndention.setSelected(true);
        showIndention.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        JCheckBox showGroupHeader = new JCheckBox("Show Group Header");
        showGroupHeader.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (_table.getTableHeader() instanceof GroupTableHeader) {
                    boolean enabled = e.getStateChange() == ItemEvent.SELECTED;
                    ((GroupTableHeader) _table.getTableHeader()).setGroupHeaderEnabled(enabled);
                    showIndention.setEnabled(enabled);
                    showConnectLine.setEnabled(showIndention.isSelected() && enabled);
                }
            }
        });
        showGroupHeader.setSelected(true);

        JCheckBox autoFilterCheckBox = new JCheckBox("Enable Auto Filter");
        autoFilterCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ((GroupTableHeader) _table.getTableHeader()).setAutoFilterEnabled(e.getStateChange() == ItemEvent.SELECTED);
                ((GroupTableHeader) _table.getTableHeader()).setUseNativeHeaderRenderer(true);
            }
        });
        autoFilterCheckBox.setSelected(((GroupTableHeader) _table.getTableHeader()).isAutoFilterEnabled());


        checkBoxPanel.add(singleLevel);
        checkBoxPanel.add(keepColumnOrder);
        checkBoxPanel.add(showGroupColumns);
        checkBoxPanel.add(showCountColumn);
        checkBoxPanel.add(showSeparateGroupColumn);
        checkBoxPanel.add(showStyle);
        checkBoxPanel.add(showGroupHeader);
        checkBoxPanel.add(showIndention);
        checkBoxPanel.add(showConnectLine);
        checkBoxPanel.add(autoFilterCheckBox);
        return checkBoxPanel;
    }

    public Component getDemoPanel() {
        final JideSplitPane panel = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);

        _tableModel = DemoData.createProductReportsTableModel(true, 0);
        final CalculatedTableModel calculatedTableModel = setupProductDetailsCalculatedTableModel(_tableModel);
        FilterableTableModel filterableTableModel = new FilterableTableModel(calculatedTableModel);

        _groupTableModel = new StyledGroupTableModel(filterableTableModel);
        _groupTableModel.addGroupColumn(0, DefaultGroupTableModel.SORT_GROUP_COLUMN_ASCENDING);
        _groupTableModel.addGroupColumn(1, DefaultGroupTableModel.SORT_GROUP_COLUMN_DESCENDING);
        _groupTableModel.groupAndRefresh();

        _table = new GroupTable(_groupTableModel);
        GroupTableHeader header = new GroupTableHeader(_table);
        _table.setTableHeader(header);
        header.setGroupHeaderEnabled(true);
        header.setAutoFilterEnabled(true);
        header.setUseNativeHeaderRenderer(true);
//        QuickTableFilterField field = new QuickTableFilterField(_table.getModel());
//
//        SortableTreeTableModel sortableTTM = new SortableTreeTableModel(field.getDisplayTableModel());
//        sortableTTM.setSortableOption(0, SortableTreeTableModel.SORTABLE_ROOT_LEVEL);
//        _table.setModel(sortableTTM);
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_table) {
            @Override
            protected void customizeMenuItems(final JTableHeader header, final JPopupMenu popup, final int clickingColumn) {
                super.customizeMenuItems(header, popup, clickingColumn);

                addSeparatorIfNecessary(popup);

                final JMenuItem exportToCsv = new JMenuItem(new AbstractAction("Export to CSV format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        outputToCsv(e);
                    }
                });

                final JMenuItem export = new JMenuItem(new AbstractAction("Export to Excel 2003 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _table.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
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
                        _table.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2007);
                        if (!HssfTableUtils.isXssfInstalled()) {
                            JOptionPane.showMessageDialog((Component) e.getSource(), "Export to Excel 2007 feature is disabled because one or several POI-XSSF dependency jars are missing in the classpath. Please include all the jars from poi release in the classpath and try to run again.");
                            return;
                        }
                        outputToExcel(e);
                    }
                });
                popup.add(export);
                popup.add(export2007);
                popup.add(exportToCsv);
            }
        };
        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new GroupTablePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new SelectTablePopupMenuCustomizer());
        _table.setExpandedIcon(IconsFactory.getImageIcon(GroupTableDemo.class, "icons/outlook_collapse.png"));
        _table.setCollapsedIcon(IconsFactory.getImageIcon(GroupTableDemo.class, "icons/outlook_expand.png"));
        _table.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
        _table.setOptimized(true);

        // hide the grid lines is good for performance
        _table.setShowLeafNodeTreeLines(false);
        _table.setShowTreeLines(false);
        _table.setExportCollapsedRowsToExcel(true);

        _table.expandAll();

        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Right click on the table header to see more options"),
                new JScrollPane(_table), BorderLayout.BEFORE_FIRST_LINE));
//        panel.add(field, BorderLayout.AFTER_LINE_ENDS);

        TableSearchable searchable = new GroupTableSearchable(_table);
        searchable.setSearchColumnIndices(new int[]{2, 3});
        searchable.setRepeats(true);
        SearchableBar searchableBar = SearchableBar.install(searchable, KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK), new SearchableBar.Installer() {
            public void openSearchBar(SearchableBar searchableBar) {
                panel.add(searchableBar, BorderLayout.AFTER_LAST_LINE);
                panel.invalidate();
                panel.revalidate();
            }

            public void closeSearchBar(SearchableBar searchableBar) {
                panel.remove(searchableBar);
                panel.invalidate();
                panel.revalidate();
            }
        });
        searchableBar.setName("TableSearchableBar");
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "G28.GroupableTableModel";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new GroupTableDemo());
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
        int result = chooser.showDialog(((JMenuItem) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
            try {
                CsvTableUtils.export(_table, chooser.getSelectedFile().getAbsolutePath());
            }
            catch (IOException ex) {
                ex.printStackTrace();
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
        int result = chooser.showDialog(((JMenuItem) e.getSource()).getTopLevelAncestor(), "Export");
        if (result == JFileChooser.APPROVE_OPTION) {
            _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
            try {
                HssfTableUtils.export(_table, chooser.getSelectedFile().getAbsolutePath(), "GroupTable", false);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private CalculatedTableModel setupProductDetailsCalculatedTableModel(TableModel tableModel) {
        CalculatedTableModel calculatedTableModel = new CalculatedTableModel(tableModel);
        calculatedTableModel.addAllColumns();
        SingleColumn year = new SingleColumn(tableModel, "ShippedDate", "Year", new DateYearGrouper());
        year.setConverterContext(YearNameConverter.CONTEXT);
        calculatedTableModel.addColumn(year);
        SingleColumn qtr = new SingleColumn(tableModel, "ShippedDate", "Quarter", new DateQuarterGrouper());
        qtr.setConverterContext(QuarterNameConverter.CONTEXT);
        calculatedTableModel.addColumn(qtr);
        SingleColumn month = new SingleColumn(tableModel, "ShippedDate", "Month", new DateMonthGrouper());
        month.setConverterContext(MonthNameConverter.CONTEXT);
        calculatedTableModel.addColumn(month);
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Week", new DateWeekOfYearGrouper()));

        return calculatedTableModel;
    }

    private class StyledGroupTableModel extends DefaultGroupTableModel implements StyleModel {
        private static final long serialVersionUID = 4936234855874300579L;

        public StyledGroupTableModel(TableModel tableModel) {
            super(tableModel);
        }

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            if (_useStyle && hasGroupColumns()) {
                Row row = getRowAt(rowIndex);
                boolean topLevel = false;
                if (row instanceof DefaultGroupRow) {
                    topLevel = true;
                }
                while (!(row instanceof DefaultGroupRow)) {
                    row = (Row) row.getParent();
                }
                if (getOriginalRows().indexOf(row) % 2 == 0) {
                    return topLevel ? styleGroup1 : style1;
                }
                else {
                    return topLevel ? styleGroup2 : style2;
                }
            }
            else {
                return null;
            }
        }

        public boolean isCellStyleOn() {
            return _useStyle;
        }
    }
}
