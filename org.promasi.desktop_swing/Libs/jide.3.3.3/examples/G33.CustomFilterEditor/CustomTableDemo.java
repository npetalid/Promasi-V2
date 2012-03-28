/*
 * @(#)CustomTableDemo.java 9/10/2008
 *
 * Copyright 2002 - 2008 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.AbstractCalculatedColumn;
import com.jidesoft.grid.CalculatedTableModel;
import com.jidesoft.grid.SingleColumn;
import com.jidesoft.grouper.AbstractObjectGrouper;
import com.jidesoft.grouper.date.*;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Demoed Component: {@link com.jidesoft.pivot.PivotTablePane} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
abstract public class CustomTableDemo extends AbstractDemo {
    protected TableModel _tableModel;

    public CustomTableDemo() {
    }

    public Component getDemoPanel() {
        final DefaultTableModel tableModel = (DefaultTableModel) DemoData.createProductReportsTableModel(false, 0);

        if (tableModel == null) {
            return new JLabel("Failed to read data file");
        }

        JTable table = createTable(tableModel);

        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.add(createEditorPanel(), BorderLayout.BEFORE_FIRST_LINE);
        JPanel tablePanel = new JPanel(new BorderLayout(6, 6));

        tablePanel.add(new JScrollPane(table));

        JButton updateButton = createUpdateButton();
        if (updateButton != null) {
            tablePanel.add(JideSwingUtilities.createCenterPanel(updateButton), BorderLayout.BEFORE_FIRST_LINE);
        }

        panel.add(tablePanel);
        return panel;
    }

    abstract public JTable createTable(DefaultTableModel tableModel);

    abstract public JButton createUpdateButton();

    abstract public Component createEditorPanel();

    protected CalculatedTableModel setupProductDetailsCalculatedTableModel(TableModel tableModel) {
        CalculatedTableModel calculatedTableModel = new CalculatedTableModel(tableModel);
        calculatedTableModel.addAllColumns();
        SingleColumn column = new SingleColumn(tableModel, "ProductName");
        column.setObjectGrouper(new AbstractObjectGrouper() {
            public Class getType() {
                return String.class;
            }

            public Object getValue(Object value) {
                if (value != null) {
                    String name = value.toString();
                    return name.substring(0, 1);
                }
                else {
                    return "";
                }
            }

            public String getName() {
                return "Alphabetical";
            }
        });
        calculatedTableModel.addColumn(column);
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Year", new DateYearGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Quarter", new DateQuarterGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Month", new DateMonthGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Week", new DateWeekOfYearGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Day of Year", new DateDayOfYearGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Day of Month", new DateDayOfMonthGrouper()));
        calculatedTableModel.addColumn(new AbstractCalculatedColumn(tableModel, "Sales2", Float.class) {
            public Object getValueAt(int rowIndex) {
                Object valueAt = getActualModel().getValueAt(rowIndex, 2);
                if (valueAt instanceof Float) {
                    return new Float(((Float) valueAt).floatValue() * 2);
                }
                return "--";
            }

            public int[] getDependingColumns() {
                return new int[]{2};
            }
        });

        return calculatedTableModel;
    }

}