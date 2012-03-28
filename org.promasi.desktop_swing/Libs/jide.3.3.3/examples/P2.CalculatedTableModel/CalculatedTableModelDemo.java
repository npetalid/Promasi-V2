/*
 * @(#)CalculatedTableModelDemo.java 7/14/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.comparator.ObjectComparatorManager;
import com.jidesoft.converter.DoubleConverter;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.grid.*;
import com.jidesoft.grouper.AbstractObjectGrouper;
import com.jidesoft.grouper.date.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Demoed Component: {@link com.jidesoft.grid.SortableTable} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
public class CalculatedTableModelDemo extends AbstractDemo {
    public CachedTableModel _cachedTableModel;
    private static final long serialVersionUID = 1025588992093208356L;

    public CalculatedTableModelDemo() {
    }

    public String getName() {
        return "CalculatedTableModel Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_PIVOT;
    }

    @Override
    public String getDescription() {
        return "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.CalculatedTableModel";
    }

    public Component getDemoPanel() {
        CellEditorManager.initDefaultEditor();
        CellRendererManager.initDefaultRenderer();
        ObjectConverterManager.initDefaultConverter();
        ObjectComparatorManager.initDefaultComparator();
        DoubleConverter converter = new DoubleConverter();
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(2);
        converter.setNumberFormat(numberInstance);
        ObjectConverterManager.registerConverter(Double.class, converter);

        final TableModel tableModel = DemoData.createProductReportsTableModel(true, 0);
        if (tableModel == null) {
            return new JLabel("Failed to read data file");
        }

        JPanel panel1 = createSortableTable(tableModel);
        JPanel panel2 = createCalculatedTable(tableModel);

        JideSplitPane pane = new JideSplitPane();
        pane.add(panel1);
        pane.add(panel2, JideBoxLayout.VARY);
        panel1.setPreferredSize(new Dimension(300, 500));
        panel2.setPreferredSize(new Dimension(500, 500));

        return pane;
    }

    private JPanel createCalculatedTable(TableModel tableModel) {
        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.setName("CalculatedTable");
        panel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "CalculatedTableModel (extra columns are added)", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        CalculatedTableModel calculatedTableModel = new CalculatedTableModel(tableModel);
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 0));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 1));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 2));
        SingleColumn column = new SingleColumn(tableModel, 2, "Tag");
        column.setObjectGrouper(new AbstractObjectGrouper() {
            public String getName() {
                return "Tag";
            }

            public Class getType() {
                return String.class;
            }

            public Object getValue(Object value) {
                if (value instanceof Float) {
                    float sales = (Float) value;
                    if (sales < 100) {
                        return "S";
                    }
                    else if (sales < 1000) {
                        return "M";
                    }
                    else if (sales < 10000) {
                        return "L";
                    }
                    else if (sales < 100000) {
                        return "Super";
                    }
                }
                return null;
            }
        });
        calculatedTableModel.addColumn(column);
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 3));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 3, "Year", new DateYearGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 3, "Qtr", new DateQuarterGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 3, "Month", new DateMonthGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 3, "Week", new DateWeekOfYearGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 3, "Day of Year", new DateDayOfYearGrouper()));
        calculatedTableModel.addColumn(new SingleColumn(tableModel, 3, "Day of Month", new DateDayOfMonthGrouper()));
        try {
            ExpressionCalculatedColumn longName = new ExpressionCalculatedColumn(tableModel, "Long Name", "LENGTH([ProductName]) > 4");
            longName.setDependingColumns(new int[]{1});
            calculatedTableModel.addColumn(longName);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        calculatedTableModel.addColumn(new AbstractCalculatedColumn(tableModel, "Amount", Float.class) {
            private static final long serialVersionUID = 2262942915907503179L;

            public Object getValueAt(int rowIndex) {
                Object valueAt = getActualModel().getValueAt(rowIndex, 2);
                if (valueAt instanceof Float) {
                    return (Float) valueAt * 2;
                }
                return "--";
            }

            public int[] getDependingColumns() {
                return new int[]{2};
            }
        });

        _cachedTableModel = new CachedTableModel(calculatedTableModel);
        _cachedTableModel.setCacheEnabled(true);
        SortableTable derivedTable = new SortableTable(_cachedTableModel);
        JScrollPane scrollPane = new JScrollPane(derivedTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane);
        return panel;
    }

/*
    private void timingIt(CachedTableModel model) {
        long start = System.currentTimeMillis();
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                model.getValueAt(row, col);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
*/

    private JPanel createSortableTable(TableModel tableModel) {
        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.setName("Original TableModel");
        panel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Original TableModel", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        SortableTable sortableTable = new SortableTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(sortableTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "P2.CalculatedTableModel";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new CalculatedTableModelDemo());
            }
        });
    }
}
