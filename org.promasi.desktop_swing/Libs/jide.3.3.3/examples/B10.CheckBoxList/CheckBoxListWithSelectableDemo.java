/*
 * @(#)CheckBoxListDemo.java 4/21/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.CheckBoxListWithSelectable;
import com.jidesoft.swing.SearchableUtils;
import com.jidesoft.swing.Selectable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.swing.CheckBoxListWithSelectable} <br> Required jar files: jide-common.jar <br>
 * Required L&F: any L&F
 */
public class CheckBoxListWithSelectableDemo extends AbstractDemo {
    private static final long serialVersionUID = -2462584914427598103L;

    private CheckBoxListWithSelectable _list;

    public CheckBoxListWithSelectableDemo() {
    }

    public String getName() {
        return "CheckBoxList Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "CheckBoxList is a list with check box.\n" +
                "\nYou can click on the check box to select/unselect the list item. Or you can press SPACE key to toggle the selection.\n" +
                "\nSome items can be marked as disable. In this case, user will not be able to toggle the selection status. from UI, they appear as gray color. There are three diabled items (row 3, 6, and 10) in this demo.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.list.CheckBoxList\n" +
                "com.jidesoft.list.Selectable\n" +
                "com.jidesoft.list.DefaultSelectable";
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setLayout(new BorderLayout(4, 4));
        panel.add(new JLabel("List of countries: "), BorderLayout.BEFORE_FIRST_LINE);
        _list = createCheckBoxList();
        panel.add(new JScrollPane(_list));
        return panel;
    }

    private CheckBoxListWithSelectable createCheckBoxList() {
        String[] names = DemoData.getCountryNames();
        CheckBoxListWithSelectable list = new CheckBoxListWithSelectable(names);
// uncomment the lines below to see a customize cell renderer.
//        list.setCellRenderer(new DefaultListCellRenderer() {
//            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                label.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.FileType.JAVA));
//                return label;
//            }
//        });
        SearchableUtils.installSearchable(list);
        list.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
// you can use item listener to detect any change in the check box selection.
//                System.out.println(e.getItem() + " " + e.getStateChange());
            }
        });
        ((Selectable) list.getModel().getElementAt(2)).setEnabled(false);
        ((Selectable) list.getModel().getElementAt(5)).setEnabled(false);
        ((Selectable) list.getModel().getElementAt(9)).setEnabled(false);
        list.setSelectedObjects(new Object[]{"Albania", "China", "United States"});
        return list;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new CheckBoxListWithSelectableDemo());
            }
        });

    }

    @Override
    public String getDemoFolder() {
        return "B10.CheckBoxList";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        JButton selectAllButton = new JButton(new AbstractAction("Select All") {
            private static final long serialVersionUID = 8895551324466265191L;

            public void actionPerformed(ActionEvent e) {
                _list.selectAll();
            }
        });
        JButton selectNoneButton = new JButton(new AbstractAction("Select None") {
            private static final long serialVersionUID = 2758269583107061665L;

            public void actionPerformed(ActionEvent e) {
                _list.selectNone();
            }
        });

        final JCheckBox checkBoxEnabled = new JCheckBox("Enabled Checking");
        checkBoxEnabled.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -5539557087369293073L;

            public void actionPerformed(ActionEvent e) {
                _list.setCheckBoxEnabled(checkBoxEnabled.isSelected());
            }
        });
        checkBoxEnabled.setSelected(_list.isCheckBoxEnabled());

        final JCheckBox clickInCheckBoxOnly = new JCheckBox("Click only valid in CheckBox");
        clickInCheckBoxOnly.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 5234198740430142668L;

            public void actionPerformed(ActionEvent e) {
                _list.setClickInCheckBoxOnly(clickInCheckBoxOnly.isSelected());
            }
        });
        clickInCheckBoxOnly.setSelected(_list.isClickInCheckBoxOnly());

        panel.add(selectAllButton);
        panel.add(selectNoneButton);
        panel.add(checkBoxEnabled);
        panel.add(clickInCheckBoxOnly);
        return panel;
    }
}