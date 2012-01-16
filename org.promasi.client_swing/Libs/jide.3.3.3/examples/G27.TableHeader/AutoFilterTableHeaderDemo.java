/*
 * @(#)AutoFilterTableHeader.java 2/5/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.comparator.ObjectComparatorManager;
import com.jidesoft.converter.DoubleConverter;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.filter.FilterFactoryManager;
import com.jidesoft.grid.*;
import com.jidesoft.grouper.ObjectGrouperManager;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.Vector;

/**
 * Demoed Component: {@link com.jidesoft.grid.AutoFilterTableHeader} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: any L&F
 */
public class AutoFilterTableHeaderDemo extends AbstractDemo {
    protected AutoFilterTableHeader _header;
    protected FilterFactoryManager _filterManager;
    private SortableTable _sortableTable;
    private static final long serialVersionUID = -8262453726101747955L;

    public AutoFilterTableHeaderDemo() {
        CellEditorManager.initDefaultEditor();
        CellRendererManager.initDefaultRenderer();
        ObjectConverterManager.initDefaultConverter();
        ObjectComparatorManager.initDefaultComparator();
        DoubleConverter converter = new DoubleConverter();
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(2);
        converter.setNumberFormat(numberInstance);
        ObjectConverterManager.registerConverter(Double.class, converter);
        ObjectConverterManager.registerConverter(Integer.class, new DemoData.SalesConverter(), DemoData.SalesConverter.CONTEXT);

        ObjectGrouperManager.initDefaultGrouper();
        ObjectGrouperManager.registerGrouper(Float.class, new DemoData.SalesObjectGrouper(), DemoData.SalesObjectGrouper.CONTEXT);

        _filterManager = new FilterFactoryManager();
        _filterManager.registerDefaultFilterFactories();
    }

    public String getName() {
        return "AutoFilterTableHeader (Table) Demo";
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.AutoFilterTableHeader";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
        JCheckBox autoFilterCheckBox = new JCheckBox("Auto Filter");
        autoFilterCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _header.setAutoFilterEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        autoFilterCheckBox.setSelected(_header.isAutoFilterEnabled());

        JCheckBox showFilterNameCheckBox = new JCheckBox("Show Filter Name on Header");
        showFilterNameCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _header.setShowFilterName(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        showFilterNameCheckBox.setSelected(_header.isShowFilterName());

        JCheckBox showFilterNameAsToolTipCheckBox = new JCheckBox("Show Filter Name as ToolTip");
        showFilterNameAsToolTipCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _header.setShowFilterNameAsToolTip(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        showFilterNameAsToolTipCheckBox.setSelected(_header.isShowFilterNameAsToolTip());

        final JCheckBox showFilterIconCheckBox = new JCheckBox("Show Filter Icon on Header");
        showFilterIconCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _header.setShowFilterIcon(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        showFilterIconCheckBox.setSelected(_header.isShowFilterIcon());
        JCheckBox sortArrowVisibleCheckBox = new JCheckBox("Show Sort Arrow on Header");
        sortArrowVisibleCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _header.setShowSortArrow(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        sortArrowVisibleCheckBox.setSelected(_header.isShowSortArrow());
        JCheckBox allowMultipleValuesCheckBox = new JCheckBox("Allow Multiple Values as Filter");
        allowMultipleValuesCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _header.setAllowMultipleValues(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        allowMultipleValuesCheckBox.setSelected(_header.isAllowMultipleValues());

        JCheckBox pauseFiltering = new JCheckBox("Pause Filtering");
        pauseFiltering.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                TableModel tableModel = TableModelWrapperUtils.getActualTableModel(_sortableTable.getModel(), FilterableTableModel.class);
                if (tableModel instanceof FilterableTableModel) {
                    ((FilterableTableModel) tableModel).setFilteringPaused(e.getStateChange() == ItemEvent.SELECTED);
                }
            }
        });
        pauseFiltering.setSelected(false);

        JCheckBox useDefaultHeaderRenderer = new JCheckBox("Use Native Renderer");
        useDefaultHeaderRenderer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _header.setUseNativeHeaderRenderer(e.getStateChange() == ItemEvent.SELECTED);
                showFilterIconCheckBox.setText((e.getStateChange() == ItemEvent.SELECTED ? "Always " : "") + "Show Filter Icon on Header");
            }
        });
        useDefaultHeaderRenderer.setSelected(true);

        JButton addRowsSelection = new JButton(new AbstractAction("Add rows") {
            private static final long serialVersionUID = -4709145280400090007L;

            public void actionPerformed(ActionEvent e) {
                TableModel tableModel = TableModelWrapperUtils.getActualTableModel(_sortableTable.getModel(), DefaultContextSensitiveTableModel.class);
                if (tableModel instanceof DefaultTableModel) {
                    Vector[] rowDatas = new Vector[3];
                    rowDatas[0] = (Vector) ((DefaultTableModel) tableModel).getDataVector().elementAt(0);
                    rowDatas[1] = (Vector) ((DefaultTableModel) tableModel).getDataVector().elementAt(1);
                    rowDatas[2] = (Vector) ((DefaultTableModel) tableModel).getDataVector().elementAt(2);
                    ((DefaultContextSensitiveTableModel) tableModel).addRows(rowDatas);
                }
            }
        });

        checkBoxPanel.add(autoFilterCheckBox);
        checkBoxPanel.add(showFilterNameCheckBox);
        checkBoxPanel.add(showFilterNameAsToolTipCheckBox);
        checkBoxPanel.add(showFilterIconCheckBox);
        checkBoxPanel.add(sortArrowVisibleCheckBox);
        checkBoxPanel.add(allowMultipleValuesCheckBox);
        checkBoxPanel.add(useDefaultHeaderRenderer);
        checkBoxPanel.add(pauseFiltering);
        checkBoxPanel.add(addRowsSelection);
//        checkBoxPanel.add(new JButton(new AbstractAction("Add Produce") {
//            public void actionPerformed(ActionEvent e) {
//                _header.getFilterableTableModel().addFilter(0, new SingleValueFilter("Produce"));
//                _header.getFilterableTableModel().setFiltersApplied(true);
//            }
//        }));
//        checkBoxPanel.add(new JButton(new AbstractAction("Add Seafood") {
//            public void actionPerformed(ActionEvent e) {
//                _header.getFilterableTableModel().addFilter(0, new MultipleValuesFilter(new Object[]{"Seafood", "Produce"}));
//                _header.getFilterableTableModel().setFiltersApplied(true);
//            }
//        }));
        return checkBoxPanel;
    }

    public Component getDemoPanel() {
        final TableModel tableModel = DemoData.createProductReportsTableModel(true, 0);
        if (tableModel == null) {
            return new JLabel("Failed to read data file");
        }

        return createSortableTable(tableModel);
    }

    private JPanel createSortableTable(TableModel tableModel) {
        final JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "AutoFilterTableHeader", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        _sortableTable = new SortableTable(tableModel);
        _header = new AutoFilterTableHeader(_sortableTable) {
            @Override
            protected IFilterableTableModel createFilterableTableModel(TableModel model) {
                return new FilterableTableModel(model) {
                    private static final long serialVersionUID = 7072186511643823323L;

                    @Override
                    public boolean isColumnAutoFilterable(int column) {
                        return true;
                    }
                };
            }
        };
        _header.setAutoFilterEnabled(true);
        _header.setUseNativeHeaderRenderer(true);
        _sortableTable.setTableHeader(_header);
        _sortableTable.setPreferredScrollableViewportSize(new Dimension(600, 500));
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_sortableTable);
        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
        panel.add(new JScrollPane(_sortableTable));
        return panel;
    }


    @Override
    public String getDemoFolder() {
        return "G27.AutoFilterTableHeader";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new AutoFilterTableHeaderDemo());
            }
        });
    }
}
