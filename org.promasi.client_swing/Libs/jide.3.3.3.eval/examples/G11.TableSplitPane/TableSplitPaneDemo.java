/*
 * @(#)TableSplitPaneDemo.java
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.converter.PercentConverter;
import com.jidesoft.grid.*;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.zip.GZIPInputStream;

/**
 * Demoed Component: {@link com.jidesoft.grid.TableSplitPane} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
public class TableSplitPaneDemo extends AbstractDemo {
    private static final long serialVersionUID = -2307134916253267365L;

    public TableSplitPaneDemo() {
    }

    public String getName() {
        return "TableSplitPane Demo";
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    @Override
    public String getDescription() {
        return "This is a demo of TableSplitPane. TableSplitPane is a special component which supports spanning one large table model into multiple tables while still keeping seamless navigation, sorting, scrolling etc.\n" +
                "\nIt also shows you how to create a nested table column header which is the feature of JideTable (see the last table).\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.TableSplitPane\n" +
                "com.jidesoft.grid.JideTable";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    public Component getDemoPanel() {
        ObjectConverterManager.registerConverter(Boolean.class, new YesNoConverter(), YesNoConverter.CONTEXT);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 400));
        panel.setLayout(new BorderLayout());
        panel.add(createTablePane(), BorderLayout.CENTER);
        return panel;
    }

    private TableSplitPane createTablePane() {
        java.util.List list = createTaskList();
        if (list == null) {
            return null;
        }
        final TaskTreeTableModel tableModel = new TaskTreeTableModel(list);
        TableSplitPane pane = new TableSplitPane(new FilterableTreeTableModel(tableModel)) {
            @Override
            protected TableScrollPane createTableScrollPane(MultiTableModel tableModel, MultiTableModel headerTableModel, MultiTableModel footerTableModel, int tableIndex, boolean sortable) {
                if (tableIndex == 0) {
                    TableScrollPane scrollPane = new TableScrollPane(tableModel, headerTableModel, footerTableModel, sortable, tableIndex, false) {
                        @Override
                        protected JTable createTable(TableModel model, boolean sortable, int type) {
                            if (type == MultiTableModel.HEADER_COLUMN) {
                                return new TreeTable(model);
                            }
                            return super.createTable(model, sortable, type);
                        }

                        @Override
                        protected void customizeTable(JTable table) {
                            if (table instanceof JideTable) {
                                ((JideTable) table).setNestedTableHeader(true);
                                ((NestedTableHeader) table.getTableHeader()).setAutoFilterEnabled(true);
                                ((NestedTableHeader) table.getTableHeader()).setUseNativeHeaderRenderer(true);
                            }
                        }
                    };
                    scrollPane.putClientProperty(TABLE_INDEX, tableIndex);
                    return scrollPane;
                }
                return super.createTableScrollPane(tableModel, headerTableModel, footerTableModel, tableIndex, sortable);
            }

            @Override
            public TableCustomizer getTableCustomizer() {
                return new TableCustomizer() {
                    public void customize(JTable table) {
                        if (table instanceof JideTable) {
                            ((JideTable) table).setNestedTableHeader(true);
                            ((NestedTableHeader) table.getTableHeader()).setAutoFilterEnabled(true);
                            ((NestedTableHeader) table.getTableHeader()).setUseNativeHeaderRenderer(true);
                        }
                    }
                };
            }
        };
        TableScrollPane[] panes = pane.getTableScrollPanes();
        TableScrollPane scrollPane = panes[2];
        JTable table = scrollPane.getMainTable();

        // create nested table columns
        TableColumnModel cm = table.getColumnModel();
        TableColumnGroup first = new TableColumnGroup("Developer Group");
        first.add(cm.getColumn(0));
        first.add(cm.getColumn(1));
        first.add(cm.getColumn(2));
        first.add(cm.getColumn(3));
        TableColumnGroup second = new TableColumnGroup("Tester Group");
        second.add(cm.getColumn(4));
        second.add(cm.getColumn(5));
        second.add(cm.getColumn(6));
        NestedTableHeader nestedHeader = (NestedTableHeader) table.getTableHeader();
        nestedHeader.addColumnGroup(first);
        nestedHeader.addColumnGroup(second);
        TableUtils.autoResizeAllColumns(panes[0].getRowHeaderTable());
        TableUtils.autoResizeAllColumns(panes[1].getMainTable());
        TableUtils.autoResizeAllColumns(panes[2].getMainTable());
        ((TreeTable) panes[0].getRowHeaderTable()).expandAll();
        return pane;
    }

    @Override
    public String getDemoFolder() {
        return "G11.TableSplitPane";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new TableSplitPaneDemo());
            }
        });

    }

    // 0 header
    // 1 - 10
    // 11 - 20
    // 21 - 30
    static String[] HEADER = new String[]{
            " ",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6", "Week 7", "Week 8", "Week 9", "Week 10",
            "Sum 1", "Sum 2", "Sum 3", "Sum 4", "Sum 5", "Sum 6", "Sum 7", "Sum 8", "Sum 9", "Sum 10"
    };

    static Object[][] ENTRIES = new Object[][]{
    };

/*
    class SchedulerTableModelEx extends AbstractMultiTableModel {
        @Override
        public String getColumnName(int column) {
            return HEADER[column];
        }

        public int getColumnCount() {
            return HEADER.length;
        }

        public int getRowCount() {
            return 20;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return "(" + rowIndex + ", " + columnIndex + ")";
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0 && columnIndex != HEADER.length - 1; // not last one and not first one
        }

        public int getTableIndex(int columnIndex) {
            if (columnIndex <= 10) {
                return 0;
            }
            else if (columnIndex <= 20) {
                return 1;
            }
            else {
                return 2;
            }
        }

        public int getColumnType(int column) {
            if (column == 0) {
                return HEADER_COLUMN;
            }
            else if (column == 30) {
                return FOOTER_COLUMN;
            }
            else {
                return REGULAR_COLUMN;
            }
        }
    }

    class SchedulerTotalTableModel extends AbstractMultiTableModel {
        public int getColumnCount() {
            return HEADER.length;
        }

        public int getRowCount() {
            return 1;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return "Total (" + rowIndex + ", " + columnIndex + ")";
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        public int getTableIndex(int columnIndex) {
            if (columnIndex <= 10) {
                return 0;
            }
            else if (columnIndex <= 20) {
                return 1;
            }
            else {
                return 2;
            }
        }

        public int getColumnType(int column) {
            if (column == 0) {
                return HEADER_COLUMN;
            }
            else if (column == 30) {
                return FOOTER_COLUMN;
            }
            else {
                return REGULAR_COLUMN;
            }
        }
    }
*/

    private static String parseName(String[] values) {
        if (values.length >= 1) {
            return values[0];
        }
        return "";
    }

    private static Double parseCompleted(String[] values) {
        Double completed = 0.0;
        if (values.length >= 2) {
            try {
                completed = Double.parseDouble(values[1]);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return completed;
    }

    private static Double parseDuration(String[] values) {
        Double duration = 0.0;
        if (values.length >= 3) {
            try {
                duration = Double.parseDouble(values[2]);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return duration;
    }

    private static Double parseWork(String[] values) {
        Double work = 0.0;
        if (values.length >= 4) {
            try {
                work = Double.parseDouble(values[3]);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return work;
    }

    private static Date parseStartTime(String[] values, DateFormat format) {
        Date startTime = null;
        if (values.length >= 5) {
            try {
                startTime = format.parse(values[4]);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return startTime;
    }

    private static Date parseEndTime(String[] values, DateFormat format) {
        Date endTime = null;
        if (values.length >= 6) {
            try {
                endTime = format.parse(values[5]);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return endTime;
    }

    private java.util.List createTaskList() {
        java.util.List rows = new ArrayList();

        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream("Project.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            Task[] tasks = new Task[3];
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy kk:mm");

            do {
                String line = reader.readLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                String[] values = line.split(",");
                String name = parseName(values);
                double completed = parseCompleted(values);
                double duration = parseDuration(values);
                double work = parseWork(values);
                Date startTime = parseStartTime(values, format);
                Date endTime = parseEndTime(values, format);

                if (name.startsWith("++")) {
                    Task project = new Task(name.substring(2), completed, duration, work, startTime, endTime);
                    tasks[1].addChild(project);
                    tasks[2] = project;
                }
                else if (name.startsWith("+")) {
                    Task task = new Task(name.substring(1), completed, duration, work, startTime, endTime);
                    tasks[0].addChild(task);
                    tasks[1] = task;
                }
                else {
                    Task task = new Task(name, completed, duration, work, startTime, endTime);
                    rows.add(task);
                    tasks[0] = task;
                }
            }
            while (true);
            return rows;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Task extends DefaultExpandableRow {
        String name;
        String details;
        double completed;
        double duration;
        double work;
        Date startTime;
        Date endTime;
        Boolean[] resourceUsed;

        public Task() {
        }


        public Task(String name, double completed, double duration, double work, Date startTime, Date endTime) {
            this.name = name;
            this.completed = completed;
            this.details = "task description of " + name;
            this.duration = duration;
            this.work = work;
            this.startTime = startTime;
            this.endTime = endTime;
            resourceUsed = new Boolean[8];
            Random random = new Random();
            for (int i = 0; i < 8; i++) {
                resourceUsed[i] = random.nextBoolean();
            }
        }

        public Object getValueAt(int columnIndex) {
            if (columnIndex >= 7 && columnIndex < 15) {
                return resourceUsed[columnIndex - 7];
            }
            switch (columnIndex) {
                case 0:
                    return name;
                case 1:
                    return completed;
                case 2:
                    return details;
                case 3:
                    return duration;
                case 4:
                    return work;
                case 5:
                    return startTime;
                case 6:
                    return endTime;
            }
            return null;
        }

        @Override
        public void setValueAt(Object value, int columnIndex) {
            if (columnIndex >= 7 && columnIndex < 15 && value instanceof Boolean) {
                resourceUsed[columnIndex - 7] = (Boolean) value;
                return;
            }
            switch (columnIndex) {
                case 0:
                    name = "" + value;
                    break;
                case 1:
                    completed = (Double) value;
                    break;
                case 2:
                    details = "" + value;
                    break;
                case 3:
                    duration = (Double) value;
                    break;
                case 4:
                    work = (Double) value;
                    break;
                case 5:
                    startTime = (Date) value;
                    break;
                case 6:
                    endTime = (Date) value;
                    break;
            }
            super.setValueAt(value, columnIndex);
        }
    }

    static class TaskTreeTableModel extends TreeTableModel implements StyleModel {
        private static final long serialVersionUID = 3589523753024111735L;

        public TaskTreeTableModel() {
        }

        public TaskTreeTableModel(java.util.List rows) {
            super(rows);
        }

        public int getColumnCount() {
            return 15;
        }

        @Override
        public ConverterContext getConverterContextAt(int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                if (rowIndex == 0) {
//                    return super.getConverterContextAt(rowIndex, columnIndex);
                }
                return PercentConverter.CONTEXT;
            }
            return super.getConverterContextAt(rowIndex, columnIndex);
        }

        public EditorContext getEditorContextAt(int row, int column) {
            if (column >= 7) {
                return BooleanCheckBoxCellEditor.CONTEXT;
            }
            return null;
        }

        @Override
        public Class<?> getCellClassAt(int rowIndex, int columnIndex) {
            return getColumnClass(columnIndex);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex >= 6 && columnIndex < 14) {
                return Boolean.class;
            }
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Double.class;
                case 2:
                    return String.class;
                case 3:
                    return Double.class;
                case 4:
                    return Double.class;
                case 5:
                    return Date.class;
                case 6:
                    return Date.class;
            }
            return Object.class;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Task Name";
                case 1:
                    return "% Completed";
                case 2:
                    return "Task Description";
                case 3:
                    return "Duration";
                case 4:
                    return "Work";
                case 5:
                    return "Start";
                case 6:
                    return "Finish";
                case 7:
                    return "Bob";
                case 8:
                    return "Fred";
                case 9:
                    return "Chris";
                case 10:
                    return "Anna";
                case 11:
                    return "John";
                case 12:
                    return "Susan";
                case 13:
                    return "Emma";
                case 14:
                    return "Others";
            }
            return null;
        }

        static CellStyle BOLD = new CellStyle();

        static {
            BOLD.setFontStyle(Font.BOLD);
        }

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            Row row = getRowAt(rowIndex);
            if (row.getParent() == getRoot() || (row instanceof ExpandableRow && ((ExpandableRow) row).hasChildren())) {
                return BOLD;
            }
            return null;
        }

        public boolean isCellStyleOn() {
            return true;
        }

        @Override
        public int getTableIndex(int columnIndex) {
            if (columnIndex < 3) {
                return 0;
            }
            else if (columnIndex < 7) {
                return 1;
            }
            return 2;
        }

        @Override
        public int getColumnType(int columnIndex) {
            if (columnIndex < 1) {
                return HEADER_COLUMN;
            }
            return REGULAR_COLUMN;
        }
    }
}
