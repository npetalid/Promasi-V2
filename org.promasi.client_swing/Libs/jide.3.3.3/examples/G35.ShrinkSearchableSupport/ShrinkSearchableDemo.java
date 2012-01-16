/*
 * @(#)ShrinkSearchableDemo.java 5/14/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 *
 */

import com.jidesoft.combobox.*;
import com.jidesoft.grid.*;
import com.jidesoft.list.AbstractGroupableListModel;
import com.jidesoft.list.GroupList;
import com.jidesoft.list.GroupListShrinkSearchableSupport;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.SearchableUtils;
import com.jidesoft.swing.TableSearchable;
import com.jidesoft.swing.TreeSearchable;
import com.jidesoft.tree.TreeShrinkSearchableSupport;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Demoed Component: {@link com.jidesoft.grid.ShrinkSearchableSupport} <br> Required jar files: jide-common.jar,
 * jide-grids.jar</br>
 */
public class ShrinkSearchableDemo extends AbstractDemo {
    private static final long serialVersionUID = -1487209204059326031L;

    public ShrinkSearchableDemo() {
    }

    public String getName() {
        return "Shrinkable Searchable Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    @Override
    public String getDescription() {
        return "ShrinkSearchableSupport is a collection of several classes that enable shrinkable quick search feature on JList, JComboBox or JTable.\n" +
                "1. Press any letter key to start the search and you will see the list will be shrunk based on what you typed\n" +
                "2. Press up/down arrow key to navigation to next or previous matching occurrence\n" +
                "3. Hold CTRL key while pressing up/down arrow key to to multiple selection\n" +
                "4. Press CTRL+A to select all matching occurrences\n" +
                "5. Use '?' to match any character or '*' to match several characters (except in JTextComponent) \n" +
                "6. A lot of customization options using the API\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.Searchable\n" +
                "com.jidesoft.swing.ListSearchable\n" +
                "com.jidesoft.swing.TreeSearchable\n" +
                "com.jidesoft.swing.ComboBoxSearchable\n" +
                "com.jidesoft.swing.TableSearchable\n" +
                "com.jidesoft.swing.SearchableUtils\n" +
                "com.jidesoft.swing.Searchable\n" +
                "com.jidesoft.grid.ShrinkSearchableSupport\n" +
                "com.jidesoft.grid.ListShrinkSearchableSupport\n" +
                "com.jidesoft.list.GroupListShrinkSearchableSupport\n" +
                "com.jidesoft.tree.TreeShrinkSearchableSupport\n" +
                "com.jidesoft.grid.TableShrinkSearchableSupport\n" +
                "com.jidesoft.grid.ComboBoxShrinkSearchableSupport\n" +
                "com.jidesoft.grid.ListComboBoxShrinkSearchableSupport\n" +
                "com.jidesoft.grid.TableComboBoxShrinkSearchableSupport\n";
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
        new TreeShrinkSearchableSupport(treeSearchable);

        final String[] names = DemoData.getCountryNames();
        JList list = new JList(names) {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }
        };
        list.setVisibleRowCount(10);
        new ListShrinkSearchableSupport(SearchableUtils.installSearchable(list));

        JComboBox editableComboBox = new JComboBox(names);
        editableComboBox.setEditable(true); // combobox searchable only works when combobox is not editable.
        new ComboBoxShrinkSearchableSupport(SearchableUtils.installSearchable(editableComboBox));

        JComboBox comboBox = new JComboBox(names);
        new ComboBoxShrinkSearchableSupport(SearchableUtils.installSearchable(comboBox));

        ListExComboBox listExComboBox = new ListExComboBox(names);
        new ListComboBoxShrinkSearchableSupport(new ListExComboBoxSearchable(listExComboBox));

        ListExComboBox editableListExComboBox = new ListExComboBox(names);
        editableListExComboBox.setEditable(true); // combobox searchable only works when combobox is not editable.
        new ListComboBoxShrinkSearchableSupport(new ListExComboBoxSearchable(editableListExComboBox));

        final TableModel tableModel = DemoData.createQuoteTableModel();
        TableExComboBox tableExComboBox = new TableExComboBox(tableModel);
        new TableComboBoxShrinkSearchableSupport(new TableExComboBoxSearchable(tableExComboBox));

        TableExComboBox editableTableExComboBox = new TableExComboBox(tableModel);
        editableTableExComboBox.setEditable(true); // combobox searchable only works when combobox is not editable.
        new TableComboBoxShrinkSearchableSupport(new TableExComboBoxSearchable(editableTableExComboBox));

        JTable table = new SortableTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(200, 100));
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        TableSearchable tableSearchable = SearchableUtils.installSearchable(table);
        tableSearchable.setMainIndex(-1); // search for all columns
        new TableShrinkSearchableSupport(tableSearchable);

        FontModel model = new FontModel();
        model.shuffle();
        model.putFont((String) model.getElementAt(2));
        model.putFont((String) model.getElementAt(model.getSize() - 1));

        final GroupList groupList = new GroupList(model) {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }
        };
        groupList.setVisibleRowCount(10);
        groupList.setCellRenderer(new FontListCellRenderer());
        groupList.setGroupCellRenderer(new GroupCellRenderer());
        new GroupListShrinkSearchableSupport(SearchableUtils.installSearchable(groupList));

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
        listPanel.add(createTitledPanel(new JLabel("Searchable GroupList"), 'L', new JScrollPane(groupList)), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable JComboBox"), 'C', comboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable JComboBox (Editable)"), 'E', editableComboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable ListExComboBox (in JIDE Grids)"), 'I', listExComboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable ListExComboBox (Editable, in JIDE Grids)"), 'I', editableListExComboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable TableExComboBox (in JIDE Grids)"), 'T', tableExComboBox), JideBoxLayout.FIX);
        listPanel.add(createTitledPanel(new JLabel("Searchable TableExComboBox (Editable, in JIDE Grids)"), 'T', editableTableExComboBox), JideBoxLayout.FIX);

        // add to the parent panel
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.add(listPanel);

        JPanel tableTreePanel = new JPanel(new BorderLayout(5, 5));
        tableTreePanel.add(createTitledPanel(new JLabel("Searchable JTable (Configured to search for \"symbol\" column only.)"), 'T', new JScrollPane(table)));
        tableTreePanel.add(treePanel, BorderLayout.AFTER_LAST_LINE);
        panel.add(tableTreePanel);

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
                showAsFrame(new ShrinkSearchableDemo());
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


    class FontModel extends AbstractGroupableListModel {
        private static final long serialVersionUID = 948780704659524032L;

        private String[] GROUP_NAMES = {"Recently Used Fonts", "All Fonts"};

        private java.util.List<String> _fonts;
        private int _limit;
        private java.util.List<String> _recentlyUsed;

        public FontModel() {
            this(8);
        }

        public FontModel(int limit) {
            _limit = limit;
            _recentlyUsed = new LinkedList<String>();
            _fonts = new ArrayList<String>();
            _fonts.addAll(Arrays.asList(DemoData.getFontNames()));
        }

        @Override
        public Object getGroupAt(int index) {
            if (index < _recentlyUsed.size()) {
                return GROUP_NAMES[0];
            }
            else {
                return GROUP_NAMES[1];
            }
        }

        public int getSize() {
            return _recentlyUsed.size() + _fonts.size();
        }

        public Object getElementAt(int index) {
            int usedSize = _recentlyUsed.size();
            if (index < usedSize) {
                return _recentlyUsed.get(index);
            }
            return _fonts.get(index - usedSize);
        }

        public java.util.List<String> getRecentlyUsedFont() {
            return Collections.unmodifiableList(_recentlyUsed);
        }

        public void putFont(String font) {
            if (_recentlyUsed.contains(font)) {
                _recentlyUsed.remove(font);
            }
            _recentlyUsed.add(0, font);
            if (_recentlyUsed.size() > _limit) {
                _recentlyUsed.remove(_recentlyUsed.size() - 1);
            }
            super.fireGroupChanged(this);
        }

        @Override
        public Object[] getGroups() {
            return GROUP_NAMES;
        }

        public void remove(int index) {
            int usedSize = _recentlyUsed.size();
            if (index < usedSize) {
                _recentlyUsed.remove(index);
            }
            else {
                _fonts.remove(index - usedSize);
            }
            fireGroupChanged(this);
        }

        public void shuffle() {
            Collections.shuffle(_recentlyUsed);
            Collections.shuffle(_fonts);
            fireGroupChanged(this);
        }
    }
}