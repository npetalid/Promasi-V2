/*
 * @(#)TableModelPivotValueProvider.java 3/19/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.comparator.ObjectComparatorManager;
import com.jidesoft.filter.Filter;
import com.jidesoft.pivot.*;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class TableModelPivotValueProvider implements PivotValueProvider, TableModelListener {
    private PivotField[] _rowFields;
    private PivotField[] _columnFields;
    private PivotField[] _dataFields;
    private PivotField[] _filterFields;
    private boolean _calculating = true;
    protected EventListenerList _listenerList = new EventListenerList();
    private boolean _summaryMode = true;
    private boolean _singleValueMode;
    protected final Map<CompoundKey, Map<CompoundKey, DataTableCellValue>> _dataCube = new HashMap<CompoundKey, Map<CompoundKey, DataTableCellValue>>();
    private CompoundKey[] _rowKeys;
    private CompoundKey[] _columnKeys;
    private SummaryCalculatorFactory _summaryCalculatorFactory;

    private Thread _calculationThread;
    private boolean _needStartNewCalculation;
    private PropertyChangeListener _fieldPropertyChangeListener;
    private PivotField[] _calculatingRowFields;
    private PivotField[] _calculatingColumnFields;
    private PivotField[] _calculatingDataFields;
    private PivotField[] _calculatingFilterFields;

    private TableModelPivotDataSource _dataSource;
    private Map<PivotField, Set<Object>> _possibleValues;
    private boolean _autoUpdate;
    private static final String NULL_VALUE = "NULL_VALUE";

    public TableModelPivotValueProvider(TableModel tableModel) {
        setModel(tableModel);
    }

    public void setModel(TableModel tableModel) {
        if (getDataSource() != null) {
            getDataSource().removeTableModelListener(this);
            if (tableModel == null) {
                return;
            }
            getDataSource().setTableModel(tableModel);
        }
        else {
            if (tableModel == null) {
                throw new IllegalArgumentException("When first initialization, the table model should not be null.");
            }
            _dataSource = createDefaultPivotDataSource(tableModel);
        }
        getDataSource().addTableModelListener(this);
    }

    private TableModelPivotDataSource getDataSource() {
        return _dataSource;
    }

    protected TableModelPivotDataSource createDefaultPivotDataSource(TableModel tableModel) {
        return new TableModelPivotDataSource(tableModel);
    }

    public boolean isCalculating() {
        return _calculating;
    }

    public void setCalculating(boolean calculating, boolean fireEvent) {
        if (_calculating != calculating) {
            _calculating = calculating;
            if (fireEvent) {
                firePivotValueProviderChanged(_calculating ? PivotValueProviderEvent.CALCULATION_START_REQUESTED : PivotValueProviderEvent.CALCULATION_END_WITH_STRUCTURE_CHANGED);
            }
        }
    }

    public void addPivotValueProviderListener(PivotValueProviderListener l) {
        if (!JideSwingUtilities.isListenerRegistered(_listenerList, PivotValueProviderListener.class, l)) {
            _listenerList.add(PivotValueProviderListener.class, l);
        }
    }

    public void removePivotValueProviderListener(PivotValueProviderListener l) {
        _listenerList.remove(PivotValueProviderListener.class, l);
    }

    public PivotValueProviderListener[] getPivotValueProviderListeners() {
        return _listenerList.getListeners(PivotValueProviderListener.class);
    }

    protected void firePivotValueProviderChanged(int type) {
        Object[] listeners = getPivotValueProviderListeners();
        for (int i = listeners.length - 1; i >= 0; i--) {
            ((PivotValueProviderListener) listeners[i]).pivotValueProviderEventHandler(new PivotValueProviderEvent(this, type));
        }
    }

    public void setPivotFields(PivotField[] rowFields, PivotField[] columnFields, PivotField[] dataFields, PivotField[] filterFields) {
        if (_fieldPropertyChangeListener != null) {
            if (_rowFields != null) {
                for (PivotField field : _rowFields) {
                    field.removePropertyChangeListener(_fieldPropertyChangeListener);
                }
            }
            if (_columnFields != null) {
                for (PivotField field : _columnFields) {
                    field.removePropertyChangeListener(_fieldPropertyChangeListener);
                }
            }
            if (_dataFields != null) {
                for (PivotField field : _dataFields) {
                    field.removePropertyChangeListener(_fieldPropertyChangeListener);
                }
            }
            if (_filterFields != null) {
                for (PivotField field : _filterFields) {
                    field.removePropertyChangeListener(_fieldPropertyChangeListener);
                }
            }
        }

        _rowFields = rowFields;
        _columnFields = columnFields;
        _dataFields = dataFields;
        _filterFields = filterFields;
        if (_rowFields == null && _columnFields == null) {
            setCalculating(true, true);
        }
        if (_fieldPropertyChangeListener == null) {
            _fieldPropertyChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (PivotField.PROPERTY_SELECTED_POSSIBLE_VALUES.equals(evt.getPropertyName()) ||
                            PivotField.PROPERTY_DESELECTED_POSSIBLE_VALUES.equals(evt.getPropertyName())) {
                        // possible values changed
                        PivotField field = (PivotField) evt.getSource();
                        if (field.getAreaType() == PivotConstants.AREA_DATA && field.isDataFieldFilterOnSummary()) {
//                            filterDataFieldsOnSummary();
                            return;
                        }
                        int modelIndex = field.getModelIndex();
                        PivotDataSource dataSource = getDataSource();
                        Object[] objects = field.getSelectedPossibleValues();
                        if (objects != null) {
                            dataSource.setFilter(objects, modelIndex, field.getAreaType() == PivotConstants.AREA_FILTER);
                        }
                        else {
                            objects = field.getFilteredPossibleValues();
                            if (objects != null) {
                                dataSource.setExcludeFilter(objects, modelIndex, field.getAreaType() == PivotConstants.AREA_FILTER);
                            }
                        }
                        dataSource.applyFilters(getFieldIndices(_rowFields), getFieldIndices(_columnFields), getFieldIndices(_dataFields), getFieldIndices(_filterFields));
                        firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_REQUESTED);
                        forceCalculate();
                    }
                    else if (PivotField.PROPERTY_FILTER.equals(evt.getPropertyName()) ||
                            PivotField.PROPERTY_FILTER_ON_SUMMARY.equals(evt.getPropertyName())) {
                        // possible values changed
                        PivotField field = (PivotField) evt.getSource();
                        if (field.getAreaType() == PivotConstants.AREA_DATA && field.isDataFieldFilterOnSummary()) {
//                            filterDataFieldsOnSummary();
                            return;
                        }
                        int modelIndex = field.getModelIndex();
                        PivotDataSource dataSource = getDataSource();
                        Filter filter = (Filter) evt.getNewValue();
                        dataSource.setFilter(filter, modelIndex, field.getAreaType() == PivotConstants.AREA_FILTER);
                        dataSource.applyFilters(getFieldIndices(_rowFields), getFieldIndices(_columnFields), getFieldIndices(_dataFields), getFieldIndices(_filterFields));
                        firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_REQUESTED);
                        forceCalculate();
                    }
                }
            };
        }
        if (_rowFields != null) {
            for (PivotField field : _rowFields) {
                field.addPropertyChangeListener(_fieldPropertyChangeListener);
            }
        }
        if (_columnFields != null) {
            for (PivotField field : _columnFields) {
                field.addPropertyChangeListener(_fieldPropertyChangeListener);
            }
        }
        if (_dataFields != null) {
            for (PivotField field : _dataFields) {
                field.addPropertyChangeListener(_fieldPropertyChangeListener);
            }
        }
        if (_filterFields != null) {
            for (PivotField field : _filterFields) {
                field.addPropertyChangeListener(_fieldPropertyChangeListener);
            }
        }
    }

    public boolean isAutoUpdate() {
        return _autoUpdate;
    }

    public void setAutoUpdate(boolean autoUpdate) {
        _autoUpdate = autoUpdate;
    }

    public void tableChanged(TableModelEvent e) {
        if (!isAutoUpdate()) {
            return;
        }
        switch (e.getType()) {
            case TableModelEvent.INSERT:
                firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_DATA_UPDATED);
                tableRowsInserted(e.getFirstRow(), e.getLastRow());
                firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_END_WITH_STRUCTURE_CHANGED);
                break;
            case TableModelEvent.DELETE:
                firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_DATA_UPDATED);
                tableRowsDeleted(e.getFirstRow(), e.getLastRow());
                firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_END_WITH_STRUCTURE_CHANGED);
                break;
            case TableModelEvent.UPDATE:
                // if the column index is HEADER_ROW, then the structure
                // of the table has changed
                if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
                    _rowFields = new PivotField[0];
                    _columnFields = new PivotField[0];
                    _filterFields = new PivotField[0];
                    _dataFields = new PivotField[0];
                    setCalculating(true, true);
                }
                // if the last row is Integer.MAX_VALUE, then all data
                // within the table has changed
                else if (e.getLastRow() == Integer.MAX_VALUE || getDataSource().hasFilter()) {
                    firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_DATA_UPDATED);
                    setCalculating(true, false);
                    forceCalculate();
                    firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_END_WITH_STRUCTURE_CHANGED);
                }
                // if the column is ALL_COLUMNS, then one or more entire
                // rows have been updated
                else if (e.getColumn() == TableModelEvent.ALL_COLUMNS) {
                    firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_DATA_UPDATED);
                    tableRowsUpdated(e.getFirstRow(), e.getLastRow());
                    firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_END_WITH_STRUCTURE_CHANGED);
                }
                // one or more rows has been updated in a specific column
                else {
                    firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_DATA_UPDATED);
                    tableCellsUpdated(e.getColumn(), e.getFirstRow(), e.getLastRow());
                    firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_END_WITH_STRUCTURE_CHANGED);
                }
                break;
            default:
                forceCalculate();
                break;
        }
        invalidatePossibleValues();
    }

    protected void tableRowsInserted(int firstRow, int lastRow) {

    }

    protected void tableRowsDeleted(int firstRow, int lastRow) {

    }

    protected void tableRowsUpdated(int firstRow, int lastRow) {

    }

    protected void tableCellsUpdated(int column, int firstRow, int lastRow) {

    }

    public boolean isSummaryMode() {
        return _summaryMode;
    }

    public void setSummaryMode(boolean summaryMode) {
        _summaryMode = summaryMode;
    }

    public boolean isSingleValueMode() {
        return _singleValueMode;
    }

    public void setSingleValueMode(boolean singleValueMode) {
        _singleValueMode = singleValueMode;
    }

    public CompoundKey[] getRowKeys() {
        if (isCalculating()) {
            return new CompoundKey[0];
        }
        return _rowKeys;
    }

    public CompoundKey[] getColumnKeys() {
        if (isCalculating()) {
            return new CompoundKey[0];
        }
        return _columnKeys;
    }

    public void forceCalculate() {
        if (_calculationThread != null && _calculationThread.isAlive() && isCalculating()) {
            _needStartNewCalculation = !JideSwingUtilities.equals(_rowFields, _calculatingRowFields, true) ||
                                       !JideSwingUtilities.equals(_columnFields, _calculatingColumnFields, true) ||
                                       !JideSwingUtilities.equals(_dataFields, _calculatingDataFields, true) ||
                                       !JideSwingUtilities.equals(_filterFields, _calculatingFilterFields, true);
            return;
        }
        Runnable runnable = new Runnable() {
            public void run() {
                synchronized (_dataCube) {
                    setCalculating(true, true);
                    _calculatingRowFields = _rowFields;
                    _calculatingColumnFields = _columnFields;
                    _calculatingDataFields = _dataFields;
                    _calculatingFilterFields = _filterFields;
                    try {
                        _dataCube.clear();
                        updateFilters();
                        invalidatePossibleValues();
                        int rowCount = getDataSource().getRowCount();
                        Set<CompoundKey> columnKeySet = new HashSet<CompoundKey>();
                        for (int row = 0; row < rowCount; row++) {
                            CompoundKey rowKey = getRowKeyAt(row);
                            CompoundKey columnKey = getColumnKeyAt(row);
                            addRowIndex(rowKey, columnKey, row);
                            columnKeySet.add(columnKey);
                        }
                        Set<CompoundKey> rowKeySet = _dataCube.keySet();
                        _rowKeys = rowKeySet.toArray(new CompoundKey[rowKeySet.size()]);
                        _columnKeys = columnKeySet.toArray(new CompoundKey[columnKeySet.size()]);
                    }
                    finally {
                        setCalculating(false, true);
                        if (_needStartNewCalculation) {
                            firePivotValueProviderChanged(PivotValueProviderEvent.CALCULATION_START_REQUESTED);
                            forceCalculate();
                            _needStartNewCalculation = false;
                        }
                    }
                }
            }
        };
        _calculationThread = new Thread(runnable);
        _calculationThread.start();
    }

    private void addValues(final List list, Object[] values) {
        List children = list;
        DefaultExpandableValue parent = null;
        StringBuffer stringValue = new StringBuffer();
        for (Object key : values) {
            DefaultExpandableValue field = null;
            int i = children.indexOf(new DefaultExpandableValue(key));
            if (i != -1) {
                field = (DefaultExpandableValue) children.get(i);
                stringValue.append(".").append(field.getValue());
            }

            if (field == null) {
                field = new DefaultExpandableValue(key);
                stringValue.append(".").append(field.getValue());

                field.setExpanded(true);
                field.setChildren(new ArrayList());

                if (parent != null) {
                    parent.addChild(field);
                }
                else {
                    children.add(field);
                }
            }

            children = field.getChildren();
            parent = field;
        }
    }

    private CompoundKey getRowKeyAt(int rowIndex) {
        if (_rowFields == null || _rowFields.length <= 0) {
            return null;
        }
        Object[] values = new Object[_rowFields.length];
        for (int i = 0; i < _rowFields.length; i++) {
            values[i] = getDataSource().getValueAt(rowIndex, _rowFields[i].getModelIndex());
        }
        return CompoundKey.newInstance(values);
    }

    private CompoundKey getColumnKeyAt(int rowIndex) {
        if (_columnFields == null || _columnFields.length <= 0) {
            return null;
        }
        Object[] values = new Object[_columnFields.length];
        for (int i = 0; i < _columnFields.length; i++) {
            values[i] = getDataSource().getValueAt(rowIndex, _columnFields[i].getModelIndex());
        }
        return CompoundKey.newInstance(values);
    }

    private void addRowIndex(CompoundKey rowKey, CompoundKey columnKey, int rowIndex) {
        Map<CompoundKey, DataTableCellValue> rowValues = _dataCube.get(rowKey);
        if (rowValues == null) {
            rowValues = new HashMap<CompoundKey, DataTableCellValue>();
            _dataCube.put(rowKey, rowValues);
        }
        DataTableCellValue cellValue = rowValues.get(columnKey);
        if (cellValue == null) {
            cellValue = new DataTableCellValue();
            rowValues.put(columnKey, cellValue);
        }
        cellValue.addRowIndex(rowIndex);
    }

    class DataTableCellValue {
        List<Integer> _rowIndices;
        Map<PivotField, Object> _dataFieldValueMap;

        public DataTableCellValue() {
        }

        public void addRowIndex(int rowIndex) {
            if (_rowIndices == null) {
                _rowIndices = new ArrayList<Integer>();
            }
            _rowIndices.add(rowIndex);
        }

        public void putValue(PivotField dataField, Object value) {
            if (_dataFieldValueMap == null) {
                _dataFieldValueMap = new HashMap<PivotField, Object>();
            }
            _dataFieldValueMap.put(dataField, value);
        }

        public List<Integer> getRowIndices() {
            return _rowIndices;
        }

        public void setRowIndices(List<Integer> rowIndices) {
            _rowIndices = rowIndices;
        }

        public void dispose() {
            _rowIndices.clear();
            _rowIndices = null;
            _dataFieldValueMap.clear();
            _dataFieldValueMap = null;
        }
    }

    public SummaryCalculatorFactory getSummaryCalculatorFactory() {
        if (_summaryCalculatorFactory == null) {
            _summaryCalculatorFactory = new SummaryCalculatorFactory() {
                public SummaryCalculator create() {
                    return new DefaultSummaryCalculator();
                }
            };
        }
        return _summaryCalculatorFactory;
    }

    public void setSummaryCalculatorFactory(SummaryCalculatorFactory summaryCalculatorFactory) {
        _summaryCalculatorFactory = summaryCalculatorFactory;
    }

    public int getStatisticsType(Values rowValues, Values columnValues, PivotField dataField) {
        if (columnValues instanceof RunningSummaryValues) {
            return PivotConstants.STATISTICS_CALCULATION_RUNNING;
        }
        if (rowValues instanceof SummaryValues) {
            if (((SummaryValues) rowValues).getSummaryType() == PivotConstants.SUMMARY_MEAN) {
                return PivotConstants.STATISTICS_CALCULATION_RUNNING;
            }
        }
        if (columnValues instanceof SummaryValues) {
            if (((SummaryValues) columnValues).getSummaryType() == PivotConstants.SUMMARY_MEAN) {
                return PivotConstants.STATISTICS_CALCULATION_RUNNING;
            }
        }
        if ((rowValues instanceof GrandTotalValues || columnValues instanceof GrandTotalValues) && dataField != null && dataField.getGrandTotalSummaryType() == PivotConstants.SUMMARY_MEAN) {
            return PivotConstants.STATISTICS_CALCULATION_RUNNING;
        }
        return PivotConstants.STATISTICS_CALCULATION_DEFAULT;
    }

    protected Object calculateStatistics(PivotField field, int summaryType, Object[] objects) {
        if (!isSummaryMode() || isSingleValueMode() || objects == null) {
            return objects == null || objects.length == 0 ? null : objects[0];
        }
        if (summaryType == PivotConstants.SUMMARY_CUSTOM && field != null) {
            if (field.getCustomSummary() != null) {
                return field.getCustomSummary().getResult(objects);
            }
            else {
                return null;
            }
        }
        else if (summaryType == PivotConstants.SUMMARY_NONE) {
            return null;
        }
        else if (field != null) {
            SummaryCalculatorFactory factory = getSummaryCalculatorFactory();
            if (factory == null) {
                return null;
            }
            SummaryCalculator calculator = factory.create();
            if (calculator != null) {
                calculator.clear();
                for (Object v : objects) {
                    calculator.addValue(TableModelPivotValueProvider.this, field, null, null, v);
                }

                if (summaryType != PivotConstants.SUMMARY_COUNT && calculator.getCount() == 0) {
                    return null;
                }
                else {
                    return calculator.getSummaryResult(summaryType);
                }
            }
        }
        return objects.length;
    }

    public Object getValueAt(PivotField dataField, Values rowValues, Values columnValues) {
        if (isCalculating() || _dataFields == null || _rowFields == null || _columnFields == null) {
            return null;
        }
        int dataFieldIndex = 0;
        for (; dataFieldIndex < _dataFields.length; dataFieldIndex++) {
            if (_dataFields[dataFieldIndex] == dataField) {
                break;
            }
        }
        if (dataFieldIndex >= _dataFields.length) {
            return null;
        }
        int type = getSummaryTypeAt(rowValues, columnValues, dataField);
        List<Integer> rowIndexList = getRowIndicesFromCellMap(rowValues, columnValues);

        Object[] objects = new Object[rowIndexList.size()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = dataField != null ? getDataSource().getValueAt(rowIndexList.get(i), dataField.getModelIndex()) : null;
        }
        return calculateStatistics(dataField, type, objects);
    }

    private CompoundKey getCompoundKey(Values rowValues) {
        if (rowValues instanceof DefaultValues) {
            return ((DefaultValues) rowValues).toCompoundKey();
        }
        Object[] rowValueArray = new Object[rowValues.getCount()];
        for (int i = 0; i < rowValueArray.length; i++) {
            rowValueArray[i] = rowValues.getValueAt(i).getValue();
        }
        return CompoundKey.newInstance(rowValueArray);
    }

    private List<Integer> getRowIndicesFromCellMap(Values rowValues, Values columnValues) {
        if (_dataCube == null) {
            return new ArrayList<Integer>();
        }
        CompoundKey rowKey = getCompoundKey(rowValues);
        CompoundKey columnKey = getCompoundKey(columnValues);
        Map<CompoundKey, DataTableCellValue> rowValueMap = _dataCube.get(rowKey);
        if (rowValueMap == null) {
            List<Integer> rowIndexList = getRowIndicesFromValues(rowValues, columnValues);
            if (rowIndexList.size() > 0) {
                rowValueMap = new HashMap<CompoundKey, DataTableCellValue>();
                _dataCube.put(rowKey, rowValueMap);
                DataTableCellValue cellValue = new DataTableCellValue();
                rowValueMap.put(columnKey, cellValue);
                cellValue.setRowIndices(rowIndexList);
            }
            return rowIndexList;
        }
        DataTableCellValue cellValue = rowValueMap.get(columnKey);
        if (cellValue == null) {
            List<Integer> rowIndexList = getRowIndicesFromValues(rowValues, columnValues);
            if (rowIndexList.size() > 0) {
                cellValue = new DataTableCellValue();
                rowValueMap.put(columnKey, cellValue);
                cellValue.setRowIndices(rowIndexList);
            }
            return rowIndexList;
        }
        return cellValue.getRowIndices();
    }

    private List<Integer> getRowIndicesFromValues(Values rowValues, Values columnValues) {
        List<Integer> rowIndexList = new ArrayList<Integer>();
        if (rowValues instanceof SummaryValues || rowValues instanceof GrandTotalValues || rowValues.getCount() < _rowFields.length) {
            List<CompoundKey> childCompoundKeys = new ArrayList<CompoundKey>();
            if (rowValues instanceof GrandTotalValues) {
                childCompoundKeys.addAll(Arrays.asList(_rowKeys));
            }
            else {
                int count = rowValues.getCount();
                for (CompoundKey rowKey : _rowKeys) {
                    Object[] keys = rowKey.getKeys();
                    boolean isChildKey = true;
                    if (keys.length < count) {
                        isChildKey = false;
                    }
                    else {
                        for (int i = 0; i < count; i++) {
                            if (!JideSwingUtilities.equals(rowValues.getValueAt(i).getValue(), keys[i])) {
                                isChildKey = false;
                                break;
                            }
                        }
                    }
                    if (isChildKey) {
                        childCompoundKeys.add(rowKey);
                    }
                }
            }
            for (CompoundKey childRowKey : childCompoundKeys) {
                rowIndexList.addAll(getRowIndicesFromCellMap(new DefaultValues(childRowKey.getKeys()), columnValues));
            }
        }
        else if (columnValues instanceof SummaryValues || columnValues instanceof GrandTotalValues || columnValues.getCount() < _columnFields.length) {
            List<CompoundKey> childCompoundKeys = new ArrayList<CompoundKey>();
            if (columnValues instanceof GrandTotalValues) {
                childCompoundKeys.addAll(Arrays.asList(_columnKeys));
            }
            else {
                int count = columnValues.getCount();
                for (CompoundKey columnKey : _columnKeys) {
                    Object[] keys = columnKey.getKeys();
                    boolean isChildKey = true;
                    if (keys.length < count) {
                        isChildKey = false;
                    }
                    else {
                        for (int i = 0; i < count; i++) {
                            if (!JideSwingUtilities.equals(columnValues.getValueAt(i).getValue(), keys[i])) {
                                isChildKey = false;
                                break;
                            }
                        }
                    }
                    if (isChildKey) {
                        childCompoundKeys.add(columnKey);
                    }
                }
            }
            for (CompoundKey childColumnKey : childCompoundKeys) {
                rowIndexList.addAll(getRowIndicesFromCellMap(rowValues, new DefaultValues(childColumnKey.getKeys())));
            }
        }
        return rowIndexList;
    }

    private int getSummaryTypeAt(Values rowValues, Values columnValues, PivotField field) {
        if (field == null) {
            return PivotConstants.SUMMARY_CUSTOM;
        }

        if (field.getSummaryType() == PivotConstants.SUMMARY_NONE) {
            return PivotConstants.SUMMARY_NONE;
        }

        if (columnValues instanceof SummaryValues) {
            int summaryType = ((SummaryValues) columnValues).getSummaryType();
            if (summaryType == -1) {
                return field.getSummaryType();
            }
            else {
                return summaryType;
            }
        }
        else if (rowValues instanceof SummaryValues) {
            int summaryType = ((SummaryValues) rowValues).getSummaryType();
            if (summaryType == -1) {
                return field.getSummaryType();
            }
            else {
                return summaryType;
            }
        }
        else if (columnValues instanceof GrandTotalValues || rowValues instanceof GrandTotalValues) {
            return field.getGrandTotalSummaryType();
        }

        int summaryType = field.getSummaryType();
        if (summaryType == -1 && isSummaryMode()) {
            return summaryType;
        }
        return summaryType;
    }

    protected void updateFilters() {
        getDataSource().clearFilters();
        List<PivotField> fields = new ArrayList<PivotField>();
        fields.addAll(Arrays.asList(_rowFields));
        fields.addAll(Arrays.asList(_columnFields));
        fields.addAll(Arrays.asList(_dataFields));
        fields.addAll(Arrays.asList(_filterFields));
        for (PivotField field : fields) {
            int modelIndex = field.getModelIndex();
            Filter filter = field.getFilter();
            if (field.getAreaType() == PivotConstants.AREA_DATA && field.isDataFieldFilterOnSummary()) {
                continue;
            }
            if (filter != null) {
                getDataSource().setFilter(filter, modelIndex, field.getAreaType() == PivotConstants.AREA_FILTER);
            }
            else {
                Object[] selectedPossibleValues = field.getSelectedPossibleValues();
                if (selectedPossibleValues != null) {
                    getDataSource().setFilter(selectedPossibleValues, modelIndex, field.getAreaType() == PivotConstants.AREA_FILTER);
                }
                else {
                    Object[] deselectedPossibleValues = field.getFilteredPossibleValues();
                    if (deselectedPossibleValues != null) {
                        getDataSource().setExcludeFilter(deselectedPossibleValues, modelIndex, field.getAreaType() == PivotConstants.AREA_FILTER);
                    }
                }
            }
        }

        getDataSource().applyFilters(getFieldIndices(_rowFields), getFieldIndices(_columnFields), getFieldIndices(_dataFields), getFieldIndices(_filterFields));
    }

    private int[] getFieldIndices(PivotField[] fields) {
        int[] indices = new int[fields.length];
        for (int i = 0; i < fields.length; i++) {
            PivotField field = fields[i];
            indices[i] = field.getModelIndex();
        }
        return indices;
    }

    private void invalidatePossibleValues() {
        if (_possibleValues == null) {
            _possibleValues = new HashMap<PivotField, Set<Object>>();
        }
        _possibleValues.clear();
    }

    public Object[] getPossibleValues(PivotField field) {
        Comparator comparator = ObjectComparatorManager.getComparator(field.getType(), field.getComparatorContext());
        Set<Object> possibleValues = _possibleValues.get(field);
        List<Object> values = new ArrayList<Object>();
        boolean hasNull = false;
        if (possibleValues == null) {
            possibleValues = getDataSource().getPossibleValues(field.getModelIndex(), field.getAreaType() == PivotConstants.AREA_FILTER, field.isNullValueAllowed());
            _possibleValues.put(field, possibleValues);
        }
        Object[] selectedValues = field.getSelectedPossibleValues();
        Object[] deselectedValues = field.getFilteredPossibleValues();
        if (selectedValues != null) {
            possibleValues.addAll(Arrays.asList(selectedValues));
        }
        if (deselectedValues != null) {
            possibleValues.addAll(Arrays.asList(deselectedValues));
        }
        for (Object o : possibleValues) {
            if (o == null) {
                continue;
            }
            if (NULL_VALUE.equals(o)) {
                hasNull = true;
                values.add(0, null); // insert at the first one
            }
            else {
                values.add(o);
            }
        }

        Object[] objects = values.toArray();
        if (objects.length >= 2) {
            if (hasNull) {
                Arrays.sort(objects, 1, objects.length, comparator);
            }
            else {
                Arrays.sort(objects, comparator);
            }
        }
        return objects;
    }
}