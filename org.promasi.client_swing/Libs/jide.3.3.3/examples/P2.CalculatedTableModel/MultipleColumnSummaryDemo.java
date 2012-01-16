/*
 * @(#)MultipleColumnSummaryDemo.java 11/4/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.AbstractCalculatedColumn;
import com.jidesoft.grid.CalculatedTableModel;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.jidesoft.pivot.*;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MultipleColumnSummaryDemo extends AbstractDemo {
    private PivotTablePane _pane;
    private MultipleColumnSummaryCalculator _calculator;
    private static final long serialVersionUID = -6490105440281508770L;

    public MultipleColumnSummaryDemo() {
        super();
    }

    public String getName() {
        return "Multiple Column Summary Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_PIVOT;
    }

    @Override
    public String getDemoFolder() {
        return "P2.CalculatedTableModel";
    }

    @Override
    public String getDescription() {
        return "This demo is to demonstrate how you could utilize the DefaultSummaryCalculator to get a calculated field in PivotDataModel level instead of original table model level.\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.CalculatedTableModel\n" +
                "com.jidesoft.pivot.PivotDataModel\n" +
                "com.jidesoft.pivot.DefaultSummaryCalculator";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JButton(new AbstractAction("Add cost / ft") {

            private final MultipleColumnSummary footSummary = new MultipleColumnSummary() {

                public String getSummaryName() {
                    return "cost / ft";
                }

                public Class<?> getResultType() {
                    return Double.class;
                }

                public int[] getDependingColumns() {
                    return new int[]{3, 2};
                }

                public Object calculateSummary(List<Object[]> data) {
                    double numerator = 0.0;
                    double denominator = 0.0;
                    for (Object[] row : data) {
                        if (row[0] instanceof Double) {
                            numerator += (Double) row[0];
                        }
                        if (row[1] instanceof Double) {
                            denominator += (Double) row[1];
                        }
                    }
                    if (denominator != 0.0) {
                        return (numerator * 3.2808399) / denominator;
                    }
                    else {
                        return null; //Double.NaN;
                    }
                }
            };
            private static final long serialVersionUID = 691462348783937233L;

            public void actionPerformed(ActionEvent e) {
                IPivotDataModel pivotDataModel = _pane.getPivotDataModel();
                if (pivotDataModel.getField(footSummary.getSummaryName()) == null) {
                    putValue(NAME, "Remove cost / ft");

                    _calculator.addSummary((PivotDataModel) pivotDataModel, footSummary);

                    PivotField field = pivotDataModel.getField(5);
                    field.setAreaType(PivotConstants.AREA_DATA);
                    // while running you need to use 2 * column + 1
                    field.setAreaIndex(pivotDataModel.getDataFields().length * 2 + 1);
                }
                else {
                    putValue(NAME, "Add cost / ft");

                    _calculator.removeSummary((PivotDataModel) pivotDataModel, footSummary);
                }
                pivotDataModel.calculate();
                _pane.fieldsUpdated();
            }
        }), BorderLayout.PAGE_END);
        return panel;
    }

    public Component getDemoPanel() {
        Object[] columnNames = {"area", "material", "length (m)", "total cost"};
        Object[][] data = {
                {"plant A", "SS", 1.0, 100.0},
                {"plant B", "SS", 2.0, 100.0},
                {"plant A", "CS", 8.0, 100.0},
                {"plant B", "CS", 16.0, 100.0},

                {"plant A", "SS", 1.0, 100.0},
                {"plant B", "SS", 2.0, 100.0},
                {"plant A", "CS", 8.0, 100.0},
                {"plant B", "CS", 16.0, 100.0},};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = -3428720888828611674L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 1:
                        return String.class;
                    case 2:
                    case 3:
                        return Double.class;
                    default:
                        return Object.class;
                }
            }
        };
        CalculatedTableModel calculated = new CalculatedTableModel(model);
        calculated.addAllColumns();

        MultipleColumnSummary ratio = new MultipleColumnSummary() {

            public String getSummaryName() {
                return "cost / m";
            }

            public Class<?> getResultType() {
                return Double.class;
            }

            public int[] getDependingColumns() {
                return new int[]{3, 2};
            }

            public Object calculateSummary(List<Object[]> data) {
                double numerator = 0.0;
                double denominator = 0.0;
                for (Object[] row : data) {
                    if (row[0] instanceof Double) {
                        numerator += (Double) row[0];
                    }
                    if (row[1] instanceof Double) {
                        denominator += (Double) row[1];
                    }
                }
                if (denominator != 0.0) {
                    return numerator / denominator;
                }
                else {
                    return null;//Double.NaN;
                }
            }
        };

        final PivotDataModel pivotDataModel = new CalculatedPivotDataModel(calculated);
        _calculator = new MultipleColumnSummaryCalculator();
        pivotDataModel.setSummaryCalculator(_calculator);
        pivotDataModel.setShowGrandTotalForRow(true);
        pivotDataModel.setShowGrandTotalForColumn(true);

        _calculator.addSummary(pivotDataModel, ratio);

        PivotField field = pivotDataModel.getField(0);
        field.setAllowedAsDataField(false);
        field.setAreaType(PivotConstants.AREA_COLUMN);

        field = pivotDataModel.getField(1);
        field.setAllowedAsDataField(false);
        field.setAreaType(PivotConstants.AREA_ROW);

        field = pivotDataModel.getField(2);
        field.setAllowedAsColumnField(false);
        field.setAllowedAsFilterField(false);
        field.setAllowedAsRowField(false);
        field.setAreaType(PivotConstants.AREA_DATA);
        field.setAreaIndex(0);

        field = pivotDataModel.getField(3);
        field.setAllowedAsColumnField(false);
        field.setAllowedAsFilterField(false);
        field.setAllowedAsRowField(false);
        field.setAreaType(PivotConstants.AREA_DATA);
        field.setAreaIndex(1);

        field = pivotDataModel.getField(4);
        field.setAreaType(PivotConstants.AREA_DATA);
        field.setAreaIndex(2);

        _pane = new PivotTablePane(pivotDataModel);
        _pane.setFieldChooserVisible(false);
        _pane.setFilterFieldAreaVisible(false);
        return _pane;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new MultipleColumnSummaryDemo());
            }
        });
    }

    private static class MultipleColumnSummaryCalculator extends DefaultSummaryCalculator {

        private static final ConverterContext MULTIPLE_COLUMN_SUMMARY_CONTEXT = new ConverterContext(
                "multipleColumnSummary");

        private final List<MultipleColumnSummary> summaries = new ArrayList<MultipleColumnSummary>();

        private final List<Object[]> dependingValues = new ArrayList<Object[]>();
        private final List<DefaultSummaryCalculator> calculators = new ArrayList<DefaultSummaryCalculator>();
        private MultipleColumnSummary currentSummary;

        /*
         * NOTE: If the model is already used in a PivotTablePane call
         * <code>pane.fieldsUpdated();</code> after you add/removed all the summaries.
         *
         * @return The pivot field for the summary column
         */
        public PivotField addSummary(PivotDataModel model, MultipleColumnSummary summary) {
            this.summaries.add(summary);

            CalculatedTableModel calculated = (CalculatedTableModel) TableModelWrapperUtils
                    .getActualTableModel(model.getTableModel(), CalculatedTableModel.class);

            CalculatedSummaryColumn column = new CalculatedSummaryColumn(
                    calculated.getActualModel(), summary);
            calculated.addColumn(column);

            model.initialize();

            PivotField field = model.getField(column.getColumnName());
            field.setAllowedAsColumnField(false);
            field.setAllowedAsFilterField(false);
            field.setAllowedAsRowField(false);
            int type = getSummaryType(summary);
            field.setSummaryType(type);
            field.setGrandTotalSummaryType(type);
            field.setCustomizable(false);
            ConverterContext context = new ConverterContext(MULTIPLE_COLUMN_SUMMARY_CONTEXT.getName());
            context.setUserObject(summary);
            field.setConverterContext(context);

            return field;
        }

        /*
         * NOTE: If the model is already used in a PivotTablePane call
         * <code>pane.fieldsUpdated();</code> after you add/removed all the summaries.
         */
        public void removeSummary(PivotDataModel model, MultipleColumnSummary summary) {
            int index = summaries.indexOf(summary);
            if (index != -1) {
                summaries.remove(index);

                CalculatedTableModel calculated = (CalculatedTableModel) TableModelWrapperUtils
                        .getActualTableModel(model.getTableModel(), CalculatedTableModel.class);

                for (int i = calculated.getColumnCount() - 1; i >= 0; i--) {
                    if (calculated.getColumnName(i).equals(summary.getSummaryName())) {
                        calculated.removeColumn(calculated.getCalculatedColumnAt(i));
                        break;
                    }
                }

                model.initialize();
            }
        }

        public int getSummaryType(MultipleColumnSummary summary) {
            int index = summaries.indexOf(summary);
            if (index != -1) {
                return SUMMARY_RESERVED_MAX + index + 1;
            }
            else {
                return -1;
            }
        }

        @Override
        public int getNumberOfSummaries() {
            // note: not changing super result
            // so the multiple column summaries
            // don't show up for normal fields
            return super.getNumberOfSummaries();
        }

        @Override
        public String getSummaryName(Locale locale, int type) {
            MultipleColumnSummary summary = getSummary(type);
            if (summary != null) {
                return summary.getSummaryName();
            }
            else {
                return super.getSummaryName(locale, type);
            }
        }

        public MultipleColumnSummary getSummary(int type) {
            if (type > SUMMARY_RESERVED_MAX) {
                return summaries.get(type - SUMMARY_RESERVED_MAX - 1);
            }
            else {
                return null;
            }
        }

        @Override
        public void clear() {
            currentSummary = null;
            dependingValues.clear();
            calculators.clear();

            super.clear();
        }

        @Override
        public int[] getAllowedSummaries(Class<?> type, ConverterContext context) {
            if (MULTIPLE_COLUMN_SUMMARY_CONTEXT.equals(context)) {
                MultipleColumnSummary summary = (MultipleColumnSummary) context.getUserObject();
                int summaryType = getSummaryType(summary);
                if (summaryType != -1) {
                    return new int[]{summaryType};
                }
                else {
                    return new int[0];
                }
            }
            else {
                return super.getAllowedSummaries(type, context);
            }
        }

        @Override
        public void addValue(Object value) {
            if (value instanceof DependingColumnValues) {
                DependingColumnValues values = (DependingColumnValues) value;
                assert currentSummary == null || values.getSummary() == currentSummary : "Can only calculate one summary type at the time!";
                currentSummary = values.getSummary();

                Object[] data = values.getValues();
                dependingValues.add(data);
                for (int i = 0; i < data.length; i++) {
                    DefaultSummaryCalculator calculator;
                    if (calculators.size() == i) {
                        calculator = new DefaultSummaryCalculator();
                        calculators.add(calculator);
                    }
                    else {
                        calculator = calculators.get(i);
                    }
                    calculator.addValue(data[i]);
                }
                super.addValue(null);
            }
            else {
                super.addValue(value);
            }
        }

        @Override
        public Object getSummaryResult(int type) {
            MultipleColumnSummary summary = getSummary(type);
            if (summary != null) {
                assert currentSummary == summary : "Can only calculate one summary type at the time!";
                return summary.calculateSummary(dependingValues);
            }
            else if (!dependingValues.isEmpty()) {
                if (currentSummary == null) {
                    assert false : "Cannot calculate summary over no values";
                    return null;
                }

                Object[] data = new Object[calculators.size()];
                for (int i = 0; i < data.length; i++) {
                    data[i] = calculators.get(i).getSummaryResult(type);
                }
                return currentSummary.calculateSummary(Collections.singletonList(data));
            }
            else {
                return super.getSummaryResult(type);
            }
        }
    }

    private static interface MultipleColumnSummary {

        String getSummaryName();

        Class<?> getResultType();

        int[] getDependingColumns();

        Object calculateSummary(List<Object[]> data);
    }

    private static class CalculatedSummaryColumn extends AbstractCalculatedColumn {

        private final MultipleColumnSummary summary;
        private static final long serialVersionUID = 4608598955505476756L;

        public CalculatedSummaryColumn(TableModel model, MultipleColumnSummary summary) {
            super(model, summary.getSummaryName(), summary.getResultType());
            this.summary = summary;
        }

        public int[] getDependingColumns() {
            return summary.getDependingColumns();
        }

        public Object getValueAt(int row) {
            int[] columns = summary.getDependingColumns();
            Object[] values = new Object[columns.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = getActualModel().getValueAt(row, columns[i]);
            }
            return new DependingColumnValues(summary, values);
        }
    }

    private static class DependingColumnValues {

        private final MultipleColumnSummary summary;
        private final Object[] values;

        public DependingColumnValues(MultipleColumnSummary summary, Object... values) {
            this.summary = summary;
            this.values = values;
        }

        public MultipleColumnSummary getSummary() {
            return summary;
        }

        public Object[] getValues() {
            return values;
        }
    }
}