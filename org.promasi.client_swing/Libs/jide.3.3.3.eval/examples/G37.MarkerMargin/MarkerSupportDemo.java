/*
 * @(#)MarkerDemo.java 7/21/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.SortableTable;
import com.jidesoft.marker.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.*;

import javax.swing.*;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Demoed Component: {@link com.jidesoft.swing.Searchable} <br> Required jar files: jide-common.jar
 */
public class MarkerSupportDemo extends AbstractDemo {

    private static final long serialVersionUID = -4388098473670236073L;

    public MarkerSupportDemo() {
    }

    public String getName() {
        return "Marker Support Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "Marker provides the marker support to Searchable component so that user can see all matching elements as markers on the MarkerArea's marker stripe. This is just one of the many usages of MarkerArea\n" +
                "1. Press any letter key to start the search in one of the components (JList, JTable, JTree or JTextArea) \n" +
                "2. You can see markers appear on the MarkerArea next to the component\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.Searchable\n" +
                "com.jidesoft.marker.MarkerUtils\n";
    }

    public Component getDemoPanel() {
        JList list = createList();

        // add Searchable and marker support to JList
        Searchable listSearchable = SearchableUtils.installSearchable(list);
        MarkerSupport listMarkerSupport = new ListMarkerSupport(list);
        MarkerArea listMarkerArea = new MarkerArea(listMarkerSupport);
        MarkerUtils.registerSearchable(listSearchable, listMarkerArea, Marker.TYPE_NOTE, Color.YELLOW);

        JTable table = createTable();

        // add Searchable and marker support to JTable
        TableSearchable tableSearchable = SearchableUtils.installSearchable(table);
        tableSearchable.setMainIndex(0); // search for the first columns
        MarkerSupport tableMarkerSupport = new TableRowMarkerSupport(table);
        MarkerArea tableMarkerArea = new MarkerArea(tableMarkerSupport);
        MarkerUtils.registerSearchable(tableSearchable, tableMarkerArea, Marker.TYPE_NOTE, Color.YELLOW);

        JTextArea textArea = createTextArea();

        // add Searchable and marker support to JTextArea
        TextComponentSearchable textSearchable = SearchableUtils.installSearchable(textArea);
        MarkerSupport textMarkerSupport = new TextAreaRowMarkerSupport(textArea);
        MarkerArea textMarkerArea = new MarkerArea(textMarkerSupport);
        MarkerUtils.registerSearchable(textSearchable, textMarkerArea, Marker.TYPE_NOTE, Color.YELLOW);

        JTree tree = createTree();

        JPanel treePanel = createTitledPanel(new JLabel("Searchable JTree"), 'E', new JScrollPane(tree));
        JCheckBox checkbox = new JCheckBox("Recursive");
        checkbox.setMnemonic('R');

        // add Searchable and marker support to JTree
        final TreeSearchable treeSearchable = SearchableUtils.installSearchable(tree);
        checkbox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    treeSearchable.setRecursive(true);
                }
                else {
                    treeSearchable.setRecursive(false);
                }
            }
        });
        checkbox.setSelected(treeSearchable.isRecursive());
        treePanel.add(checkbox, BorderLayout.AFTER_LAST_LINE);

        MarkerSupport treeMarkerSupport = new TreeMarkerSupport(tree);
        MarkerArea treeMarkerArea = new MarkerArea(treeMarkerSupport);
        MarkerUtils.registerSearchable(treeSearchable, treeMarkerArea, Marker.TYPE_NOTE, Color.YELLOW);
        treePanel.add(treeMarkerArea, BorderLayout.AFTER_LINE_ENDS);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new JideBoxLayout(listPanel, JideBoxLayout.Y_AXIS, 6));
        JPanel lPanel = createTitledPanel(new JLabel("Searchable JList"), 'L', new JScrollPane(list));
        listPanel.add(lPanel, JideBoxLayout.VARY);
        lPanel.add(listMarkerArea, BorderLayout.AFTER_LINE_ENDS);

        // add to the parent panel
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JideSplitPane pane1 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        JideSplitPane pane2 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        panel.add(pane1);
        panel.add(pane2);

        pane1.add(treePanel);
        pane2.add(listPanel);

        JPanel tPanel = createTitledPanel(new JLabel("Searchable JTable (Configured to search for the first column only.)"), 'T', new JScrollPane(table));
        pane1.add(tPanel);
        tPanel.add(tableMarkerArea, BorderLayout.AFTER_LINE_ENDS);

        JPanel textPanel = createTitledPanel(new JLabel("Searchable JTextArea (CTRL-F or CMD-F to start searching)"), 'S', new JScrollPane(textArea));
        pane2.add(textPanel);
        textPanel.add(textMarkerArea, BorderLayout.AFTER_LINE_ENDS);

        return panel;
    }

    private JTree createTree() {
        JTree tree = new JTree() {
            @Override
            public TreePath getNextMatch(String prefix, int startingRow, Position.Bias bias) {
                return null;
            }
        };
        tree.setVisibleRowCount(15);
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
        return textArea;
    }

    private JTable createTable() {
        JTable table = new SortableTable(DemoData.createQuoteTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(200, 100));
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        return table;
    }

    private JList createList() {
        String[] names = DemoData.getCountryNames();
        final java.util.List<String> nameList = new ArrayList<String>();
        Collections.addAll(nameList, names);
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
                showAsFrame(new MarkerSupportDemo());
            }
        });

    }

    private static JPanel createTitledPanel(JLabel label, char mnemonic, JComponent component) {
        JPanel panel = new JPanel(new JideBorderLayout(2, 2));
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
}
