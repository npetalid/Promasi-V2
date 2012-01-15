/*
 * @(#)MarginSupportDemo.java 7/23/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.SortableTable;
import com.jidesoft.margin.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.*;
import com.jidesoft.tree.TreeUtils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Demoed Component: {@link com.jidesoft.swing.Searchable} <br> Required jar files: jide-common.jar
 */
public class MarginSupportDemo extends AbstractDemo {

    private static final long serialVersionUID = -4388098473670236073L;
    private JTable table;
    private Vector row;

    public MarginSupportDemo() {
    }

    public String getName() {
        return "Margin Support Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "Margin provides the a thin stripe along the left side of some components to display some useful information. What you need to display can be fully customized. In this demo, we " +
                "use it to display row numbers. \n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.margin.MarginSupport\n" +
                "com.jidesoft.margin.RowMarginSupport\n" +
                "com.jidesoft.margin.MarginArea\n";
    }

    public Component getDemoPanel() {
        JList list = createList();
        JScrollPane listScrollPane = new JScrollPane(list);

        // add RowMargin for list
        RowMarginSupport listMarginSupport = new ListRowMarginSupport(list, listScrollPane);
        MarginArea listMarginArea = new MarginArea();
        Insets listInsets = listScrollPane.getBorder().getBorderInsets(listScrollPane);
        listMarginArea.setBorder(BorderFactory.createEmptyBorder(listInsets.top, 0, listInsets.bottom, 0));
        listMarginArea.addMarginComponent(new RowNumberMargin(listMarginSupport));

        // ====

        table = createTable();
        JScrollPane tableScrollPane = new JScrollPane(table);

        // add RowMargin for table
        TableSearchable tableSearchable = SearchableUtils.installSearchable(table);
        tableSearchable.setMainIndex(-1); // search for all columns
        RowMarginSupport tableRowMarginSupport = new TableRowMarginSupport(table, tableScrollPane);
        MarginArea tableMarginArea = new MarginArea();
        Insets tableInsets = tableScrollPane.getBorder().getBorderInsets(tableScrollPane);
        tableMarginArea.setBorder(BorderFactory.createEmptyBorder(table.getTableHeader().getPreferredSize().height + tableInsets.top, 0, tableInsets.bottom, 0));
        tableMarginArea.addMarginComponent(new RowNumberMargin(tableRowMarginSupport));

        // ====
        JTextArea textArea = createTextArea();
        JScrollPane textScrollPane = new JScrollPane(textArea);

        // add RowMargin for text area
        RowMarginSupport textRowMarginSupport = new TextAreaRowMarginSupport(textArea, textScrollPane);
        MarginArea textMarginArea = new MarginArea();
        Insets textInsets = textScrollPane.getBorder().getBorderInsets(textScrollPane);
        textMarginArea.setBorder(BorderFactory.createEmptyBorder(textInsets.top, 0, textInsets.bottom, 0));
        textMarginArea.addMarginComponent(new RowNumberMargin(textRowMarginSupport));

        // ====
        JTree tree = createTree();
        JScrollPane treeScrollPane = new JScrollPane(tree);

        // add RowMargin for tree
        RowMarginSupport treeRowMarginSupport = new TreeRowMarginSupport(tree, treeScrollPane);
        MarginArea treeMarginArea = new MarginArea();
        Insets treeInsets = treeScrollPane.getBorder().getBorderInsets(treeScrollPane);
        treeMarginArea.setBorder(BorderFactory.createEmptyBorder(treeInsets.top, 0, treeInsets.bottom, 0));
        treeMarginArea.addMarginComponent(new RowNumberMargin(treeRowMarginSupport));
        JPanel treePanel = createTitledPanel(new JLabel("JTree with row number"), 'E', treeScrollPane, treeRowMarginSupport);
        treePanel.add(treeMarginArea, BorderLayout.BEFORE_LINE_BEGINS);

        // ====

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new JideBoxLayout(listPanel, JideBoxLayout.Y_AXIS, 6));
        JPanel lPanel = createTitledPanel(new JLabel("JList with row number"), 'L', listScrollPane, listMarginSupport);
        listPanel.add(lPanel, JideBoxLayout.VARY);
        lPanel.add(listMarginArea, BorderLayout.BEFORE_LINE_BEGINS);

        // add to the parent panel
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JideSplitPane pane1 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        JideSplitPane pane2 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        panel.add(pane1);
        panel.add(pane2);

        pane1.add(treePanel);
        pane2.add(listPanel);

        JPanel tPanel = createTitledPanel(new JLabel("JTable with row number"), 'T', tableScrollPane, tableRowMarginSupport);
        pane1.add(tPanel);
        tPanel.add(tableMarginArea, BorderLayout.BEFORE_LINE_BEGINS);

        JPanel textPanel = createTitledPanel(new JLabel("JTextArea with row number"), 'S', textScrollPane, textRowMarginSupport);
        pane2.add(textPanel);
        textPanel.add(textMarginArea, BorderLayout.BEFORE_LINE_BEGINS);

        return panel;
    }

    @Override
    public Component getOptionsPanel() {
//        JPanel panel = new JPanel();
//        panel.add(new JButton(new AbstractAction("Add rows") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                DefaultTableModel model = (DefaultTableModel) ((SortableTableModel) table.getModel()).getActualModel();
//                int count = model.getRowCount();
//                for(int i = 0; i < 20; i++) {
//                    model.getDataVector().add(row);
//                }
//                model.fireTableRowsInserted(count, count + 20);
//            }
//        }));
//        return panel;
        return super.getOptionsPanel();
    }

    private JTree createTree() {
        JTree tree = new JTree() {
            @Override
            public TreePath getNextMatch(String prefix, int startingRow, Position.Bias bias) {
                return null;
            }
        };
        tree.setVisibleRowCount(15);
        TreeUtils.expandAll(tree);
        return tree;
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setRows(15);
        String[] names = DemoData.getCountryNames();
        StringBuffer buf = new StringBuffer();
        for (String name : names) {
            buf.append(name);
            buf.append("\n");
        }
        textArea.setText(buf.toString());
        textArea.setCaretPosition(0);
        return textArea;
    }

    private JTable createTable() {
        DefaultTableModel model = (DefaultTableModel) DemoData.createQuoteTableModel();
        row = (Vector) model.getDataVector().get(0);
//        model.setRowCount(0);
        JTable table = new SortableTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(200, 100));
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setRowHeight(30);
        return table;
    }

    private JList createList() {
        String[] names = DemoData.getCountryNames();
        final java.util.List<String> nameList = new ArrayList<String>();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            nameList.add(name);
        }
        final JList list = new JList(names) {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }
        };
        list.setVisibleRowCount(15);
        return list;
    }

    @Override
    public String getDemoFolder() {
        return "B7.SearchableComponents";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new MarginSupportDemo());
            }
        });

    }

    private static JPanel createTitledPanel(JLabel label, char mnemonic,
                                            final JComponent component, final RowMarginSupport rowMarginSupport) {

        JPanel panel = new JPanel(new JideBorderLayout(2, 2))/* {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                int baseline = component.getBaseline(
                        component.getWidth(), component.getHeight())
                        + component.getY();

                JComponent view = (JComponent) ((JScrollPane) component).getViewport().getView();
                if (view instanceof JTable) {
                    baseline = view.getBaseline(
                            view.getWidth(), view.getHeight())
                            + view.getY() + component.getY() + ((JScrollPane) component).getViewport().getY();

                }

//                int rowHeight = rowMarginSupport.getRowHeight(0);
//                for(int row = 0; row < rowMarginSupport.getRowCount(); row++) {
//                    int offset = rowHeight * row;
//                    int y = baseline + offset;

                int y = baseline;
                g.setColor(Color.RED);
                g.drawLine(0, y, getWidth(), y);
//                }
            }
        }*/;
        panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        label.setDisplayedMnemonic(mnemonic);
        if (component instanceof JScrollPane) {
            label.setLabelFor(((JScrollPane) component).getViewport().getView());
        }
        else {
            label.setLabelFor(component);
        }
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    public static abstract class RowCountListener implements PropertyChangeListener, TableModelListener {
        protected final JTable table;
        private TableModel tableModel;

        public RowCountListener(JTable table) {
            assert table != null : "Can't display row count of null table!";

            this.table = table;
            this.table.addPropertyChangeListener("model", this);

            updateModelListener();
        }

        private void updateModelListener() {
            if (this.tableModel != null) {
                this.tableModel.removeTableModelListener(this);
            }

            this.tableModel = table.getModel();

            if (this.tableModel != null) {
                this.tableModel.addTableModelListener(this);
            }

            rowCountChanged(table);
        }

        protected abstract void rowCountChanged(JTable table);

        public void propertyChange(PropertyChangeEvent evt) {
            updateModelListener();
        }

        public void tableChanged(TableModelEvent e) {
            rowCountChanged(table);
        }
    }
}
