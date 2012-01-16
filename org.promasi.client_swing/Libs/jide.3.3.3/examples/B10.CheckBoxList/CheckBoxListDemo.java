/*
 * @(#)CheckBoxListDemo.java 4/21/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.SearchableUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Demoed Component: {@link com.jidesoft.swing.CheckBoxList} <br> Required jar files: jide-common.jar <br> Required L&F:
 * any L&F
 */
public class CheckBoxListDemo extends AbstractDemo {
    private CheckBoxList _list;
    private static final long serialVersionUID = -5982509597978327419L;
    private DefaultListModel _model;

    public CheckBoxListDemo() {
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
                "com.jidesoft.list.CheckBoxList";
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

    private CheckBoxList createCheckBoxList() {
        _model = new DefaultListModel();
        String[] name = DemoData.getCountryNames();
        for (String s : name) {
            _model.addElement(s);
        }
        final CheckBoxList list = new CheckBoxList(_model) {
            @Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }

            @Override
            public boolean isCheckBoxEnabled(int index) {
                return !_model.getElementAt(index).equals("Afghanistan")
                        && !_model.getElementAt(index).equals("Albania")
                        && !_model.getElementAt(index).equals("Antarctica");
            }
        };
        list.getCheckBoxListSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

// uncomment the lines below to see a customize cell renderer.
//        list.setCellRenderer(new DefaultListCellRenderer() {
//            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                label.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.FileType.JAVA));
//                return label;
//            }
//        });
        SearchableUtils.installSearchable(list);
        list.getCheckBoxListSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
//                    int[] selected = list.getCheckBoxListSelectedIndices();
//                    for (int i = 0; i < selected.length; i++) {
//                        int select = selected[i];
//                        System.out.print(select + " ");
//                    }
//                    System.out.println("\n---");
                }
            }
        });
        list.setCheckBoxListSelectedIndices(new int[]{2, 3, 20});
        return list;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new CheckBoxListDemo());
            }
        });

    }

    @Override
    public String getDemoFolder() {
        return "B10.CheckBoxList";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
        JButton selectAllButton = new JButton(new AbstractAction("Select All") {
            private static final long serialVersionUID = 6274336964872530476L;

            public void actionPerformed(ActionEvent e) {
                _list.getCheckBoxListSelectionModel().addSelectionInterval(0, _list.getModel().getSize() - 1);
            }
        });
        JButton selectNoneButton = new JButton(new AbstractAction("Select None") {
            private static final long serialVersionUID = -4521675380480250420L;

            public void actionPerformed(ActionEvent e) {
                _list.getCheckBoxListSelectionModel().clearSelection();
            }
        });

        final JCheckBox checkBoxEnabled = new JCheckBox("Enabled Checking");
        checkBoxEnabled.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -2419513753995612223L;

            public void actionPerformed(ActionEvent e) {
                _list.setCheckBoxEnabled(checkBoxEnabled.isSelected());
            }
        });
        checkBoxEnabled.setSelected(_list.isCheckBoxEnabled());

        final JCheckBox clickInCheckBoxOnly = new JCheckBox("Check only valid inside CheckBox");
        clickInCheckBoxOnly.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 5234198740430142668L;

            public void actionPerformed(ActionEvent e) {
                _list.setClickInCheckBoxOnly(clickInCheckBoxOnly.isSelected());
            }
        });
        clickInCheckBoxOnly.setSelected(_list.isClickInCheckBoxOnly());

        final JCheckBox allowAll = new JCheckBox("Enable (All)");
        allowAll.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -380261992533230412L;

            public void actionPerformed(ActionEvent e) {
                boolean selected = allowAll.isSelected();
                if (selected) {
                    _model.insertElementAt(CheckBoxList.ALL, 0);
                }
                else {
                    _model.remove(0);
                }
            }
        });
        allowAll.setSelected(false);

        final JButton removeSelected = new JButton("Remove Selected Row");
        removeSelected.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 3785843307574034034L;

            public void actionPerformed(ActionEvent e) {
                int index = _list.getSelectedIndex();
                if (index != -1) {
                    ((DefaultListModel) _list.getModel()).remove(index);
                }
            }
        });

        panel.add(selectAllButton);
        panel.add(selectNoneButton);
        panel.add(removeSelected);
        panel.add(checkBoxEnabled);
        panel.add(allowAll);
        panel.add(clickInCheckBoxOnly);
        return panel;
    }
}