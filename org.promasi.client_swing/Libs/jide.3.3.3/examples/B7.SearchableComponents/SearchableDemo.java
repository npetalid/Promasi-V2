/*
 * @(#)SearchableDemo.java 2/14/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.swing.Searchable} <br> Required jar files: jide-common.jar
 */
public class SearchableDemo extends AbstractDemo {
    private static final long serialVersionUID = 1118036569637233586L;

    public SearchableDemo() {
    }

    public String getName() {
        return "Searchable Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "Searchable is a collection of several classes that enable quick search feature on JList, JComboBox, JTree, JTable or JTextComponent.\n" +
                "1. Press any letter key to start the search\n" +
                "2. Press up/down arrow key to navigation to next or previous matching occurrence\n" +
                "3. Hold CTRL key while pressing up/down arrow key to to multiple selection\n" +
                "4. Press CTRL+A to select all matching occurrences\n" +
                "5. Use '?' to match any character or '*' to match several characters (except in JTextComponent) \n" +
                "6. A lot of customization options using the API\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.Searchable\n" +
                "com.jidesoft.swing.TreeSearchable\n" +
                "com.jidesoft.swing.ListSearchable\n" +
                "com.jidesoft.swing.ComboBoxSearchable\n" +
                "com.jidesoft.swing.TableSearchable\n" +
                "com.jidesoft.swing.TextComponentSearchable\n" +
                "com.jidesoft.swing.SearchableUtils\n" +
                "com.jidesoft.swing.Searchable";
    }

    public Component getDemoPanel() {
        JTree tree = new JTree() {
            @Override
            public TreePath getNextMatch(String prefix, int startingRow, Position.Bias bias) {
                return null;
            }
        };
        tree.setVisibleRowCount(15);
        final TreeSearchable treeSearchable = SearchableUtils.installSearchable(tree);

        String[] names = DemoData.getCountryNames();
        JList list = new JList(names) {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }
        };
        list.setVisibleRowCount(15);
        SearchableUtils.installSearchable(list);

        JComboBox comboBox = new JComboBox(names);
        comboBox.setEditable(false); // combobox searchable only works when combobox is not editable.
        SearchableUtils.installSearchable(comboBox);

        ListExComboBox ListExComboBox = new ListExComboBox(names);
        ListExComboBox.setEditable(false); // combobox searchable only works when combobox is not editable.
        new ListExComboBoxSearchable(ListExComboBox);

        TreeExComboBox TreeExComboBox = new TreeExComboBox();
        TreeExComboBox.setEditable(false); // combobox searchable only works when combobox is not editable.
        TreeExComboBoxSearchable TreeExComboBoxSearchable = new TreeExComboBoxSearchable(TreeExComboBox);
        TreeExComboBoxSearchable.setPopupTimeout(1000);
        TreeExComboBoxSearchable.setRecursive(true);

        final TableModel tableModel = DemoData.createQuoteTableModel();
        TableExComboBox TableExComboBox = new TableExComboBox(tableModel);
        TableExComboBox.setEditable(false); // combobox searchable only works when combobox is not editable.
        new TableExComboBoxSearchable(TableExComboBox);

        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(200, 100));
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        TableSearchable tableSearchable = SearchableUtils.installSearchable(table);
        tableSearchable.setMainIndex(-1); // search for all columns 

        JTextArea textArea = new JTextArea();
        textArea.setRows(15);
        StringBuffer buf = new StringBuffer();
        for (String name : names) {
            buf.append(name);
            buf.append("\n");
        }
        textArea.setText(buf.toString());
        SearchableUtils.installSearchable(textArea);

        JPanel treePanel = createTitledPanel(new JLabel("Searchable JTree"), 'E', new JScrollPane(tree));
        JCheckBox checkbox = new JCheckBox("Recursive");
        checkbox.setMnemonic('R');
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

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new JideBoxLayout(listPanel, JideBoxLayout.Y_AXIS, 6));
        listPanel.add(createTitledPanel(new JLabel("Searchable JList"), 'L', new JScrollPane(list)), JideBoxLayout.VARY);
        listPanel.add(createTitledPanel(new JLabel("Searchable JComboBox"), 'C', comboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable ListExComboBox (in JIDE Grids)"), 'I', ListExComboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable TreeExComboBox (in JIDE Grids)"), 'R', TreeExComboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable TableExComboBox (in JIDE Grids)"), 'T', TableExComboBox), JideBoxLayout.FIX);

        // add to the parent panel
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JideSplitPane pane1 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        JideSplitPane pane2 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        panel.add(pane1);
        panel.add(pane2);

        pane1.add(treePanel);
        pane2.add(listPanel);
        pane1.add(createTitledPanel(new JLabel("Searchable JTable (Configured to search for all columns.)"), 'T', new JScrollPane(table)));
        pane2.add(createTitledPanel(new JLabel("Searchable JTextArea (CTRL+F to start searching)"), 'S', new JScrollPane(textArea)));

        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "B7.SearchableComponents";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new SearchableDemo());
            }
        });
    }

    private static JPanel createTitledPanel(JLabel label, char mnemonic, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(2, 2));
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
