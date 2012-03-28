/*
 * @(#)DualTableDemo.java 8/17/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.DefaultExpandableRow;
import com.jidesoft.grid.DualTable;
import com.jidesoft.grid.TableModelAdapter;
import com.jidesoft.list.DualList;
import com.jidesoft.list.DualListModel;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class DualTableDemo extends AbstractDemo {

    private DualTable _dualTable;
    private static final long serialVersionUID = 3026256070898636222L;

    public DualTableDemo() {
    }

    public String getName() {
        return "DualTable Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDemoFolder() {
        return "G31.DualList";
    }

    @Override
    public String getDescription() {
        return "DualTable is a pane that contains two tables. " +
                "The list on the left contains the original items. " +
                "The list on the right are selected items. " +
                "There are controls to move items back and forth. \n" +
                "We have full keyboard support in this component" +
                "\n1. UP and DOWN key to move change the selected items in the two tables;" +
                "\n2. LEFT, RIGHT, or ENTER keys to move the selected items between the two tables;" +
                "\n3. CTRL-UP and CTRL-DOWN keys will move the selected items up and down in the right table;" +
                "\n4. CTRL-HOME and CTRL-END keys will move the selected items to the top or the bottom in the right table;" +
                "\n5. CTRL-LEFT and CTRL-RIGHT keys will move focus between the two tables.";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 2));
        JRadioButton v = new JRadioButton("Remove Chosen Items", true);
        v.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _dualTable.setSelectionMode(DualListModel.REMOVE_SELECTION);
                }
            }
        });

        JRadioButton vw = new JRadioButton("Disabled Chosen Items");
        vw.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _dualTable.setSelectionMode(DualListModel.DISABLE_SELECTION);
                }
            }
        });

        JRadioButton h = new JRadioButton("Keep Chosen Items");
        h.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _dualTable.setSelectionMode(DualListModel.KEEP_SELECTION);
                }
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(h);
        group.add(v);
        group.add(vw);

        panel.add(h);
        panel.add(v);
        panel.add(vw);

        String[] commands = new String[]{
                DualList.COMMAND_MOVE_RIGHT,
                DualList.COMMAND_MOVE_LEFT,
                DualList.COMMAND_MOVE_ALL_RIGHT,
                DualList.COMMAND_MOVE_ALL_LEFT,
                DualList.COMMAND_MOVE_UP,
                DualList.COMMAND_MOVE_DOWN,
                DualList.COMMAND_MOVE_TO_TOP,
                DualList.COMMAND_MOVE_TO_BOTTOM,
        };
        for (final String command : commands) {
            final JCheckBox b = new JCheckBox(String.format("Show \"%s\"", command), true);
            b.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    _dualTable.setButtonVisible(command, b.isSelected());
                }
            });
            panel.add(b);
        }
        JCheckBox allowDuplicates = new JCheckBox("Allow Duplicate Selection");
        allowDuplicates.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dualTable.setAllowDuplicates(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        allowDuplicates.setSelected(_dualTable.isAllowDuplicates());
        panel.add(allowDuplicates);

        JCheckBox doubleClickEnabled = new JCheckBox("Enable Double Click");
        doubleClickEnabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dualTable.setDoubleClickEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        doubleClickEnabled.setSelected(_dualTable.isDoubleClickEnabled());
        panel.add(doubleClickEnabled);
        return panel;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public JComponent getDemoPanel() {
        java.util.List taskList = createTaskList();
        TableModelAdapter tableModelAdapter = new TaskTableModelAdapter();
        _dualTable = new DualTable(taskList, tableModelAdapter);
//        JTable originalTable = _table.getOriginalTable();
//        AutoFilterTableHeader header = new AutoFilterTableHeader(originalTable);
//        originalTable.setTableHeader(header);
//        header.setAutoFilterEnabled(true);
//        header.setAcceptTextInput(true);

//        _table.getModel().addListSelectionListener(new ListSelectionListener(){
//            public void valueChanged(ListSelectionEvent e) {
//                System.out.println(e.getFirstIndex() + " " + e.getLastIndex());
//            }
//        });

        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.add(_dualTable, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public String[] getDemoSource() {
        return new String[]{
                getClass().getName() + ".java",
                "CountryDualListModel.java",
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new DualTableDemo());
            }
        });

    }

    private java.util.List createTaskList() {
        java.util.List<Task> rows = new ArrayList<Task>();

        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream("Project.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

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
                    rows.add(project);
                }
                else if (name.startsWith("+")) {
                    Task task = new Task(name.substring(1), completed, duration, work, startTime, endTime);
                    rows.add(task);
                }
                else {
                    Task task = new Task(name, completed, duration, work, startTime, endTime);
                    rows.add(task);
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

    private static class Task extends DefaultExpandableRow {
        String name;
        double completed;
        double duration;
        double work;
        Date startTime;
        Date endTime;

        public Task() {
        }


        public Task(String name, double completed, double duration, double work, Date startTime, Date endTime) {
            this.name = name;
            this.completed = completed;
            this.duration = duration;
            this.work = work;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Object getValueAt(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return name;
                case 1:
                    return completed;
                case 2:
                    return duration;
                case 3:
                    return work;
                case 4:
                    return startTime;
                case 5:
                    return endTime;
            }
            return null;
        }

        @Override
        public void setValueAt(Object value, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    name = "" + value;
                    break;
                case 1:
                    completed = (Double) value;
                    break;
                case 2:
                    duration = (Double) value;
                    break;
                case 3:
                    work = (Double) value;
                    break;
                case 4:
                    startTime = (Date) value;
                    break;
                case 5:
                    endTime = (Date) value;
                    break;
            }
            super.setValueAt(value, columnIndex);
        }
    }

    private static class TaskTableModelAdapter implements TableModelAdapter {
        public TaskTableModelAdapter() {
        }

        public int getColumnCount() {
            return 6;
        }

        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Double.class;
                case 2:
                    return Double.class;
                case 3:
                    return Double.class;
                case 4:
                    return Date.class;
                case 5:
                    return Date.class;
            }
            return Object.class;
        }

        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Task Name";
                case 1:
                    return "% Completed";
                case 2:
                    return "Duration";
                case 3:
                    return "Work";
                case 4:
                    return "Start";
                case 5:
                    return "Finish";
            }
            return null;
        }
    }
}