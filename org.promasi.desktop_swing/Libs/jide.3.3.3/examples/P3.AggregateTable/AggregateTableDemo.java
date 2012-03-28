/*
 * @(#)AggregateTableDemo.java 8/23/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.MonthNameConverter;
import com.jidesoft.converter.QuarterNameConverter;
import com.jidesoft.converter.YearNameConverter;
import com.jidesoft.filter.BetweenFilter;
import com.jidesoft.filter.EqualFilter;
import com.jidesoft.grid.*;
import com.jidesoft.grouper.date.DateMonthGrouper;
import com.jidesoft.grouper.date.DateQuarterGrouper;
import com.jidesoft.grouper.date.DateWeekOfYearGrouper;
import com.jidesoft.grouper.date.DateYearGrouper;
import com.jidesoft.hssf.HssfTableUtils;
import com.jidesoft.pivot.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.JideTabbedPane;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

/**
 * Demoed Component: {@link com.jidesoft.pivot.PivotTablePane} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
public class AggregateTableDemo extends AbstractDemo {
    public AggregateTable _aggregateTable;

    public AggregateTablePane _aggregateTablePane;
    private JideTabbedPane _tabPane;
    private FilterableAggregateTableModel _filterableTableModel;
    private static final long serialVersionUID = -4424254279039211160L;
    private boolean _cellStyleOn;

    public AggregateTableDemo() {
    }

    public String getName() {
        return "Aggregate Table Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_PIVOT;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    @Override
    public String getDescription() {
        return "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.pivot.AggregateTableModel" +
                "com.jidesoft.pivot.AggregateTable" +
                "com.jidesoft.pivot.AggregateTablePane";
    }

    static CellStyle HIGH_STYLE = new CellStyle();
    static CellStyle LOW_STYLE = new CellStyle();
    static CellStyle SUMMARY_STYLE = new CellStyle();
    static CellStyle DEFAULT_STYLE = new CellStyle();
    static CellStyle HEADER_STYLE = new CellStyle();

    static {
        HIGH_STYLE.setForeground(Color.WHITE);
        HIGH_STYLE.setBackground(Color.RED);

        LOW_STYLE.setBackground(Color.YELLOW);

        SUMMARY_STYLE.setBackground(new Color(255, 255, 215));

        HEADER_STYLE.setFontStyle(Font.BOLD);
    }

    private String _lastDirectory = ".";

    public Component getDemoPanel() {
        final TableModel tableModel = DemoData.createProductReportsTableModel(true, 200);

        if (tableModel == null) {
            return new JLabel("Failed to read data file");
        }

        QuickTableFilterField field = new QuickTableFilterField(tableModel);

        final CalculatedTableModel calculatedTableModel = setupProductDetailsCalculatedTableModel(field.getDisplayTableModel());

        _aggregateTable = new AggregateTable(calculatedTableModel);
        _filterableTableModel = new FilterableAggregateTableModel(_aggregateTable.getModel());
        _aggregateTable.setModel(_filterableTableModel);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Year").setSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Year").setGrandTotalSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Month").setSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Month").setGrandTotalSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Quarter").setSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Quarter").setGrandTotalSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Week").setSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("Week").setGrandTotalSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("ShippedDate").setSummaryType(PivotConstants.SUMMARY_NONE);
        _aggregateTable.getAggregateTableModel().getPivotDataModel().getField("ShippedDate").setGrandTotalSummaryType(PivotConstants.SUMMARY_NONE);

// the commented code is an example to enable subtotal.
//        _aggregateTable.getAggregateTableModel().getField("CategoryName").setSubtotalType(PivotConstants.SUBTOTAL_CUSTOM);
//        _aggregateTable.getAggregateTableModel().getField("CategoryName").setCustomSubtotals(new int[]{PivotConstants.SUMMARY_COUNT, PivotConstants.SUMMARY_MEAN});
//        _aggregateTable.getAggregateTableModel().setShowSummary(true);

        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_aggregateTable) {
            @Override
            protected void customizeMenuItems(final JTableHeader header, final JPopupMenu popup, final int clickingColumn) {
                super.customizeMenuItems(header, popup, clickingColumn);

                addSeparatorIfNecessary(popup);

                final JMenuItem export = new JMenuItem(new AbstractAction("Export to Excel 2003 format") {
                    private static final long serialVersionUID = 2581042425782595535L;

                    public void actionPerformed(ActionEvent e) {
                        _aggregateTable.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2003);
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
                        _aggregateTable.putClientProperty(HssfTableUtils.CLIENT_PROPERTY_EXCEL_OUTPUT_FORMAT, HssfTableUtils.EXCEL_OUTPUT_FORMAT_2007);
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

                JMenuItem verify = new JMenuItem("Verify");
                verify.addActionListener(new AbstractAction() {
                    private static final long serialVersionUID = -8742088923688901087L;

                    public void actionPerformed(ActionEvent e) {
                        CellSpanTable.verifyCellSpan(_aggregateTable.getAggregateTableModel().getPivotDataModel().getRowHeaderTableModel());
                    }
                });
                popup.add(verify);

            }
        };
        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new AggregateTablePopupMenuCustomizer());
        AggregateTableColumnChooserPopupMenuCustomizer menuCustomizer = new AggregateTableColumnChooserPopupMenuCustomizer();
        menuCustomizer.setFavoriteColumns(new int[]{0, 1, 3, 4});
        menuCustomizer.setHiddenColumns(new int[]{5});
        menuCustomizer.setFixedColumns(new int[]{0, 3});
        installer.addTableHeaderPopupMenuCustomizer(menuCustomizer);
        installer.addTableHeaderPopupMenuCustomizer(new SelectTablePopupMenuCustomizer());
//        installer.addTableHeaderPopupMenuCustomizer(new TableHeaderPopupMenuCustomizer() {
//            public void customizePopupMenu(JTableHeader header, JPopupMenu popup, int clickingColumn) {
//                popup.add(new JMenuItem(new AbstractAction("Custom...") {
//                    public void actionPerformed(ActionEvent e) {
//                        AggregateTableSettingsDialog dialog = new AggregateTableSettingsDialog((JFrame) _aggregateTable.getTopLevelAncestor(), _aggregateTable, "Custom ...");
//                        dialog.pack();
//                        dialog.setLocationRelativeTo(_aggregateTable.getParent());
//                        dialog.setVisible(true);
//                    }
//                }));
//            }
//        });
        _aggregateTable.getAggregateTableModel().setSummaryMode(true);
//        _aggregateTable.getAggregateTableModel().setShowSummary(true);
//        _aggregateTable.aggregate(new String[]{"Year", "Month", "CategoryName"});
//        _aggregateTable.getAggregateTableModel().setShowSummary(true);
//        _aggregateTable.getAggregateTableModel().setShowSummaryOnly(false);
        _aggregateTable.aggregate(new String[]{"CategoryName", "ProductName"});
        _aggregateTable.setPreferredScrollableViewportSize(new Dimension(400, 400));
        _aggregateTable.getAggregateTableModel().setCellStyleProvider(new CellStyleProvider() {
            public CellStyle getCellStyleAt(TableModel model, int rowIndex, int columnIndex) {
                if (_cellStyleOn && "ProductSales".equals(model.getColumnName(columnIndex))) {
                    Object value = model.getValueAt(rowIndex, columnIndex);
                    if (value instanceof Float && (Float) value > 2000) {
                        return HIGH_STYLE;
                    }
                    else if (value instanceof Float && (Float) value < 50) {
                        return LOW_STYLE;
                    }
                }

                return DEFAULT_STYLE;
            }
        });
        TableUtils.autoResizeAllColumns(_aggregateTable);
        AggregateTableHeader header = new AggregateTableHeader(_aggregateTable);
        _aggregateTable.setTableHeader(header);
//        header.setGroupHeaderEnabled(true);

        _aggregateTablePane = new AggregateTablePane(calculatedTableModel, new String[]{"CategoryName", "ProductName"});
        TableUtils.autoResizeAllColumns(_aggregateTablePane.getRowHeaderTable());
        TableUtils.autoResizeAllColumns(_aggregateTablePane.getMainTable());

        TableHeaderPopupMenuInstaller installer2 = new TableHeaderPopupMenuInstaller(_aggregateTablePane.getRowHeaderTable());
        installer2.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer2.addTableHeaderPopupMenuCustomizer(new AggregateTablePopupMenuCustomizer());
        installer2.addTableHeaderPopupMenuCustomizer(new SelectTablePopupMenuCustomizer());
        installer2.addTableHeaderPopupMenuCustomizer(new AggregateTableColumnChooserPopupMenuCustomizer());

        TableHeaderPopupMenuInstaller installer3 = new TableHeaderPopupMenuInstaller(_aggregateTablePane.getMainTable());
        installer3.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer3.addTableHeaderPopupMenuCustomizer(new AggregateTablePopupMenuCustomizer());
        installer3.addTableHeaderPopupMenuCustomizer(new SelectTablePopupMenuCustomizer());
        installer3.addTableHeaderPopupMenuCustomizer(new AggregateTableColumnChooserPopupMenuCustomizer());

        JideSplitPane pane = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);

        JideTabbedPane tabPane = new JideTabbedPane();
        _tabPane = tabPane;
        tabPane.add("AggregateTable", new JScrollPane(_aggregateTable));
        tabPane.add("AggregateTable in TableScrollPane", _aggregateTablePane);

        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.add(new JLabel("In comparison, below is the original table model before aggregation", SwingConstants.CENTER), BorderLayout.BEFORE_FIRST_LINE);
        panel.add(new JScrollPane(new SortableTable(field.getDisplayTableModel())));
        panel.add(field, BorderLayout.AFTER_LAST_LINE);

        pane.add(JideSwingUtilities.createLabeledComponent(new JLabel("Right click on the table header to see more options"),
                tabPane, BorderLayout.BEFORE_FIRST_LINE));
        pane.add(panel);
        return pane;
    }

    @Override
    public String getDemoFolder() {
        return "P3.AggregateTable";
    }

    @Override
    public Component getOptionsPanel() {
        final JCheckBox showExpandIcon = new JCheckBox("Show Expand Icon");
        showExpandIcon.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _aggregateTable.setExpandIconVisible(showExpandIcon.isSelected());
                ((CategorizedTable) _aggregateTablePane.getRowHeaderTable()).setExpandIconVisible(showExpandIcon.isSelected());
            }
        });
        showExpandIcon.setSelected(_aggregateTable.isExpandIconVisible());

        final JCheckBox filterRawRows = new JCheckBox("Apply filter to show \"qtr 3\" rows");
        filterRawRows.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _filterableTableModel.setFilterTarget(FilterableAggregateTableModel.FILTER_TARGET_ON_STANDARD_ROWS);
                _filterableTableModel.clearFilters();
                int modelIndex = _aggregateTable.getAggregateTableModel().getModelColumnIndex(5);
                EqualFilter<Integer> filter = new EqualFilter<Integer>();
                filter.setValue(2);
                _filterableTableModel.addFilter(modelIndex, filter);
                _filterableTableModel.setFiltersApplied(filterRawRows.isSelected());
            }
        });
        filterRawRows.setSelected(false);

        final JCheckBox filterSummaryRows = new JCheckBox("Apply filter to show \"summary sales between 100 and 1000\"");
        filterSummaryRows.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _filterableTableModel.setFilterTarget(FilterableAggregateTableModel.FILTER_TARGET_ON_SUMMARY_ROWS);
                _filterableTableModel.clearFilters();
                int modelIndex = _aggregateTable.getAggregateTableModel().getModelColumnIndex(2);
                BetweenFilter<Integer> filter = new BetweenFilter<Integer>(100, 1000);
                _filterableTableModel.addFilter(modelIndex, filter);
                _filterableTableModel.setFiltersApplied(filterSummaryRows.isSelected());
            }
        });
        filterSummaryRows.setSelected(false);

        final JCheckBox cellStyleOn = new JCheckBox("Turn On/Off Cell Style");
        cellStyleOn.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _cellStyleOn = cellStyleOn.isSelected();
                _aggregateTable.invalidate();
                _aggregateTable.repaint();
            }
        });

        JButton saveButton = new JButton(new AbstractAction("Save Layout as XML") {
            private static final long serialVersionUID = -5107715775596570738L;

            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser() {
                        @Override
                        protected JDialog createDialog(Component parent) throws HeadlessException {
                            JDialog dialog = super.createDialog(parent);
                            dialog.setTitle("Save the layout as an \".xml\" file");
                            return dialog;
                        }
                    };
                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Save");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        if (_tabPane.getSelectedIndex() == 0) {
                            AggregateTablePersistenceUtils.save(_aggregateTable, chooser.getSelectedFile().getAbsolutePath());
                        }
                        else {
                            AggregateTablePersistenceUtils.save((AggregateTable) _aggregateTablePane.getRowHeaderTable(), chooser.getSelectedFile().getAbsolutePath());
                        }
                    }
                }
                catch (ParserConfigurationException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
            }
        });
        JButton loadButton = new JButton(new AbstractAction("Load Layout from XML") {
            private static final long serialVersionUID = 3732486289243549658L;

            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser() {
                        @Override
                        protected JDialog createDialog(Component parent) throws HeadlessException {
                            JDialog dialog = super.createDialog(parent);
                            dialog.setTitle("Load an \".xml\" file");
                            return dialog;
                        }
                    };
                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Open");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        if (_tabPane.getSelectedIndex() == 0) {
                            AggregateTablePersistenceUtils.load(_aggregateTable, chooser.getSelectedFile().getAbsolutePath());
                        }
                        else {
                            AggregateTablePersistenceUtils.load((AggregateTable) _aggregateTablePane.getRowHeaderTable(), chooser.getSelectedFile().getAbsolutePath());
                        }
                    }
                }
                catch (SAXException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
                catch (ParserConfigurationException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
            }
        });

        JCheckBox showGroupHeader = new JCheckBox("Show Aggregate Header");
        showGroupHeader.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (_aggregateTable.getTableHeader() instanceof AggregateTableHeader) {
                    boolean enabled = e.getStateChange() == ItemEvent.SELECTED;
                    ((AggregateTableHeader) _aggregateTable.getTableHeader()).setGroupHeaderEnabled(enabled);
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(showExpandIcon);
        panel.add(filterRawRows);
        panel.add(filterSummaryRows);
        panel.add(cellStyleOn);
        panel.add(showGroupHeader);
        panel.add(saveButton);
        panel.add(loadButton);

        return panel;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new AggregateTableDemo());
            }
        });
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
                CsvTableUtils.export(_aggregateTable, chooser.getSelectedFile().getAbsolutePath());
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
                HssfTableUtils.export(_aggregateTable, chooser.getSelectedFile().getAbsolutePath(), "AggregateTable", false);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dispose() {
    }
}
