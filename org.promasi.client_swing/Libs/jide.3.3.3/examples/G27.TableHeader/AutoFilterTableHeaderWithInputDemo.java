/*
 * @(#)AutoFilterTableHeaderWithInputDemo.java 7/19/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
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
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

/**
 * Demoed Component: {@link com.jidesoft.grid.AutoFilterTableHeader} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: any L&F
 */
public class AutoFilterTableHeaderWithInputDemo extends AbstractDemo {
    protected AutoFilterTableHeader _header;
    protected FilterFactoryManager _filterManager;
    private static final long serialVersionUID = -5748117718410594572L;

    public AutoFilterTableHeaderWithInputDemo() {
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
        return "AutoFilterTableHeader (with Input Box) Demo";
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

    public Component getDemoPanel() {
        final TableModel tableModel = DemoData.createProductReportsTableModel(true, 0);
        if (tableModel == null) {
            return new JLabel("Failed to read data file");
        }

        return createSortableTable(tableModel);
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
        checkBoxPanel.add(autoFilterCheckBox);
        return checkBoxPanel;
    }

    private JPanel createSortableTable(TableModel tableModel) {
        final JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "AutoFilterTableHeader", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        SortableTable sortableTable = new SortableTable(tableModel);
        _header = new AutoFilterTableHeader(sortableTable) {
            @Override
            protected IFilterableTableModel createFilterableTableModel(TableModel model) {
                return new FilterableTableModel(model) {
                    private static final long serialVersionUID = 7072186511643823323L;

                    @Override
                    public boolean isColumnAutoFilterable(int column) {
                        return column != 2;
                    }

                    @Override
                    public boolean isValuePredetermined(int column) {
                        return column != 3;
                    }
                };
            }

            @Override
            protected void customizeAutoFilterBox(AutoFilterBox autoFilterBox) {
                super.customizeAutoFilterBox(autoFilterBox);
                autoFilterBox.setSearchingDelay(-1);
            }
        };
        _header.setAutoFilterEnabled(true);
        _header.setAcceptTextInput(true);
        sortableTable.setTableHeader(_header);
        sortableTable.setPreferredScrollableViewportSize(new Dimension(600, 500));
        sortableTable.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(sortableTable);
        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
        panel.add(new JScrollPane(sortableTable));
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
                showAsFrame(new AutoFilterTableHeaderWithInputDemo());
            }
        });
    }
}