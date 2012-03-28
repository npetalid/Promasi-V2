/*
 * @(#)ExComboBoxDemo.java 4/8/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 *
 */

import com.jidesoft.combobox.*;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Demoed Component: {@link com.jidesoft.combobox.ListExComboBox}, {@link com.jidesoft.combobox.FileChooserExComboBox},
 * {@link com.jidesoft.combobox.ColorExComboBox}, {@link com.jidesoft.combobox.DateExComboBox} <br> Required jar files:
 * jide-common.jar, jide-grids.jar <br> Required L&F: Jide L&F extension required
 */
public class ExComboBoxDemo extends AbstractDemo {

    private static final long serialVersionUID = -3502710976508025275L;

    public ExComboBoxDemo() {
    }

    public String getName() {
        return "ExComboBox Demo";
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
        return "This is a demo of various comboboxes. \n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.combobox.DateExComboBox\n" +
                "com.jidesoft.combobox.ColorExComboBox\n" +
                "com.jidesoft.combobox.TreeExComboBox\n" +
                "com.jidesoft.combobox.ListExComboBox\n" +
                "com.jidesoft.combobox.FileChooserExComboBox";
    }

    private JPanel createTitledComponent(String label, String toolTip, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 3));
        component.setToolTipText(toolTip);
        component.setName(label);
        panel.add(new JLabel(label));
        panel.add(component);
        panel.add(Box.createGlue(), JideBoxLayout.VARY);
        return panel;
    }

    private JComboBox[] _comboBoxes = new JComboBox[34];

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));

        final JComboBox focusLost = new JComboBox(new String[]{"Commit", "Commit or Revert", "Revert", "Persist", "Commit or Reset", "Reset"});
        focusLost.setSelectedIndex(((ExComboBox) _comboBoxes[4]).getFocusLostBehavior());
        focusLost.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    for (JComboBox comboBox : _comboBoxes) {
                        if (comboBox != null && comboBox instanceof ExComboBox) {
                            ((ExComboBox) comboBox).setFocusLostBehavior(focusLost.getSelectedIndex());
                        }
                    }
                }
            }
        });

        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Focus lost behavior: "), focusLost, BorderLayout.BEFORE_LINE_BEGINS));

        final JComboBox popupCancel = new JComboBox(new String[]{"Persist", "Revert", "Reset"});
        switch (((ExComboBox) _comboBoxes[4]).getPopupCancelBehavior()) {
            case ExComboBox.PERSIST:
                popupCancel.setSelectedIndex(0);
                break;
            case ExComboBox.REVERT:
                popupCancel.setSelectedIndex(1);
                break;
            case ExComboBox.RESET:
                popupCancel.setSelectedIndex(2);
                break;
        }
        popupCancel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    for (JComboBox comboBox : _comboBoxes) {
                        if (comboBox != null && comboBox instanceof ExComboBox) {
                            int selectedIndex = popupCancel.getSelectedIndex();
                            switch (selectedIndex) {
                                case 0:
                                    ((ExComboBox) comboBox).setPopupCancelBehavior(ExComboBox.PERSIST);
                                    break;
                                case 1:
                                    ((ExComboBox) comboBox).setPopupCancelBehavior(ExComboBox.REVERT);
                                    break;
                                case 2:
                                    ((ExComboBox) comboBox).setPopupCancelBehavior(ExComboBox.RESET);
                                    break;
                            }
                        }
                    }
                }
            }
        });

        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Popup cancel behavior: "), popupCancel, BorderLayout.BEFORE_LINE_BEGINS));

        final JComboBox downKey = new JComboBox(new String[]{"Select the next possible value if any", "Always show popup"});
        switch (((ExComboBox) _comboBoxes[4]).getDownKeyBehavior()) {
            case ExComboBox.DOWN_KEY_BEHAVIOR_SELECT_NEXT:
                downKey.setSelectedIndex(0);
                downKey.setSelectedIndex(0);
                break;
            case ExComboBox.DOWN_KEY_BEHAVIOR_SHOW_POPUP:
                downKey.setSelectedIndex(1);
                break;
        }
        downKey.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    for (JComboBox comboBox : _comboBoxes) {
                        if (comboBox != null && comboBox instanceof ExComboBox) {
                            int selectedIndex = downKey.getSelectedIndex();
                            switch (selectedIndex) {
                                case 0:
                                    ((ExComboBox) comboBox).setDownKeyBehavior(ExComboBox.DOWN_KEY_BEHAVIOR_SELECT_NEXT);
                                    break;
                                case 1:
                                    ((ExComboBox) comboBox).setDownKeyBehavior(ExComboBox.DOWN_KEY_BEHAVIOR_SHOW_POPUP);
                                    break;
                            }
                        }
                    }
                }
            }
        });

        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Down key behavior: "), downKey, BorderLayout.BEFORE_LINE_BEGINS));

        final JCheckBox enabled = new JCheckBox("Enabled");
        enabled.setSelected(true);
        enabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                for (JComboBox comboBox : _comboBoxes) {
                    if (comboBox != null) {
                        comboBox.setEnabled(enabled.isSelected());
                    }
                }
            }
        });
        panel.add(enabled);

        final JCheckBox keepPopupSize = new JCheckBox("After the popup is resized, remember the size when it is shown next time");
        keepPopupSize.setSelected(false);
        keepPopupSize.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                for (JComboBox comboBox : _comboBoxes) {
                    if (comboBox != null && comboBox instanceof ExComboBox) {
                        ((ExComboBox) comboBox).setKeepPopupSize(keepPopupSize.isSelected());
                    }
                }
            }
        });
        panel.add(keepPopupSize);

        return panel;
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 20, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] fontNames = DemoData.getFontNames();

        panel.add(new JLabel("Editable ComboBoxes:"));
        panel.add(new JLabel("Non-editable ComboBoxes:"));

        int i = 0;

        JComboBox comboBox = new JComboBox(new String[]{"True", "False"});
        comboBox.setEditable(true);
        panel.add(createTitledComponent("Regular JComboBox (For Comparison)", "Regular JComboBox (For Comparison)", comboBox));

        _comboBoxes[i++] = comboBox;

        JComboBox comboBox2 = new JComboBox(new String[]{"True", "False"});
        comboBox2.setEditable(false);
        panel.add(createTitledComponent("Regular JComboBox (For Comparison)", "Regular JComboBox (For Comparison)", comboBox2));

        _comboBoxes[i++] = comboBox2;

        JComboBox comboBox3 = new JComboBox(fontNames);
        comboBox3.setEditable(true);
        panel.add(createTitledComponent("Regular JComboBox (For Comparison)", "Regular JComboBox (For Comparison)", comboBox3));

        _comboBoxes[i++] = comboBox3;

        JComboBox comboBox4 = new JComboBox(fontNames);
        comboBox4.setEditable(false);
        panel.add(createTitledComponent("Regular JComboBox (For Comparison)", "Regular JComboBox (For Comparison)", comboBox4));

        _comboBoxes[i++] = comboBox4;

        panel.add(new JPanel());
        panel.add(new JPanel());

        ExComboBox booleanComboBox = createBooleanComboBox();
        booleanComboBox.setEditable(true);
        booleanComboBox.setSelectedItem(Boolean.FALSE);
        panel.add(createTitledComponent("ListExComboBox (Boolean)", "ExComboBox to choose boolean", booleanComboBox));

        _comboBoxes[i++] = booleanComboBox;

        ExComboBox booleanComboBox2 = createBooleanComboBox();
        booleanComboBox2.setEditable(false);
        booleanComboBox2.setSelectedItem(Boolean.FALSE);
        panel.add(createTitledComponent("ListExComboBox (Boolean)", "ExComboBox to choose boolean", booleanComboBox2));

        _comboBoxes[i++] = booleanComboBox2;

        // create font name combobox
        ExComboBox fontNameComboBox = createListComboBox(fontNames);
        fontNameComboBox.setEditable(true);
        fontNameComboBox.setSelectedItem("Arial");
        panel.add(createTitledComponent("ListExComboBox (Font Name)", "ExComboBox to choose font name or type in font name directly", fontNameComboBox));

        _comboBoxes[i++] = fontNameComboBox;

        ExComboBox fontNameComboBox2 = createListComboBox(fontNames);
        fontNameComboBox2.setEditable(false);
        new ListExComboBoxSearchable((ListExComboBox) fontNameComboBox2);
        fontNameComboBox2.setSelectedItem("Arial");
        panel.add(createTitledComponent("ListExComboBox (Font Name)", "ExComboBox to choose font name from a list", fontNameComboBox2));

        _comboBoxes[i++] = fontNameComboBox2;

        CheckBoxListExComboBox checkBoxListComboBox = createCheckBoxListComboBox(fontNames);
        checkBoxListComboBox.setEditable(true);
        checkBoxListComboBox.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("CheckBoxListExComboBox (Font Name)", "ExComboBox to choose multiple font names or type in font names directly", checkBoxListComboBox));

        _comboBoxes[i++] = checkBoxListComboBox;

        CheckBoxListExComboBox checkBoxListComboBox2 = createCheckBoxListComboBox(fontNames);
        checkBoxListComboBox2.setEditable(false);
//        new ListExComboBoxSearchable(checkBoxListComboBox2);
        checkBoxListComboBox2.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("CheckBoxListExComboBox (Font Name)", "ExComboBox to choose multiple font names", checkBoxListComboBox2));

        _comboBoxes[i++] = checkBoxListComboBox2;

        MultiSelectListExComboBox multiSelectListComboBox = createMultiSelectListComboBox(fontNames);
        multiSelectListComboBox.setEditable(true);
        multiSelectListComboBox.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("MultiSelectListExComboBox (Font Name)", "ExComboBox to choose multiple font names or type in font names directly", multiSelectListComboBox));

        _comboBoxes[i++] = multiSelectListComboBox;

        MultiSelectListExComboBox multiSelectListComboBox2 = createMultiSelectListComboBox(fontNames);
        multiSelectListComboBox2.setEditable(false);
//        new ListExComboBoxSearchable(multiSelectListComboBox2);
        multiSelectListComboBox2.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("MultiSelectListExComboBox (Font Name)", "ExComboBox to choose multiple font names", multiSelectListComboBox2));

        _comboBoxes[i++] = multiSelectListComboBox2;

        ExComboBox treeComboBox = new TreeExComboBox();
        treeComboBox.setEditable(true);
        panel.add(createTitledComponent("TreeExComboBox", "ExComboBox which has a tree as the selection list", treeComboBox));

        _comboBoxes[i++] = treeComboBox;

        TreeExComboBox treeComboBox2 = new TreeExComboBox() {
            @Override
            protected boolean isValidSelection(TreePath path) {
                TreeNode treeNode = (TreeNode) path.getLastPathComponent();
                return treeNode.isLeaf();
            }
        };
        treeComboBox2.setEditable(false);
        TreeExComboBoxSearchable treeComboBoxSearchable = new TreeExComboBoxSearchable(treeComboBox2);
        treeComboBoxSearchable.setRecursive(true);
        panel.add(createTitledComponent("TreeExComboBox (only leafs are selectable)", "ExComboBox which has a tree as the selection list", treeComboBox2));

        _comboBoxes[i++] = treeComboBox2;

        TableExComboBox tableComboBox = new TableExComboBox(DemoData.createQuoteTableModel()) {
            @Override
            protected JTable createTable(TableModel model) {
                return new SortableTable(model);
            }
        };
        tableComboBox.setValueColumnIndex(1); // display the second column value in the combobox.
        tableComboBox.setSelectedItem("ALCOA INC");
        tableComboBox.setMaximumRowCount(12);
        tableComboBox.setEditable(true);
        panel.add(createTitledComponent("TableExComboBox", "ExComboBox which has a table as the selection list", tableComboBox));

        _comboBoxes[i++] = tableComboBox;

        TableExComboBox tableComboBox2 = new TableExComboBox(DemoData.createQuoteTableModel()) {
            @Override
            protected JTable createTable(TableModel model) {
                return new SortableTable(model);
            }
        };
        tableComboBox2.setValueColumnIndex(1); // display the second column value in the combobox.
        tableComboBox2.setSelectedItem("ALCOA INC");
        tableComboBox2.setEditable(false);
        new TableExComboBoxSearchable(tableComboBox2);
        tableComboBox2.setMaximumRowCount(12);
        panel.add(createTitledComponent("TableExComboBox", "ExComboBox which has a table as the selection list", tableComboBox2));

        _comboBoxes[i++] = tableComboBox2;

        ExComboBox dateComboBox = new DateExComboBox();
        dateComboBox.setEditable(true);
        panel.add(createTitledComponent("DateExComboBox", "ExComboBox to choose date", dateComboBox));

        _comboBoxes[i++] = dateComboBox;

        DateExComboBox dateComboBox2 = new DateExComboBox();
        dateComboBox2.setEditable(false);
        dateComboBox2.setTimeDisplayed(true);
        dateComboBox2.setFormat(DateFormat.getDateTimeInstance());
        dateComboBox2.setTimeFormat("hh:mm:ss a");
        dateComboBox2.setDate(Calendar.getInstance().getTime());
        panel.add(createTitledComponent("DateExComboBox", "ExComboBox to choose date (not editable)", dateComboBox2));

        _comboBoxes[i++] = dateComboBox2;

        // create month combobox
        MonthExComboBox monthComboBox = new MonthExComboBox();
        monthComboBox.setFormat(new SimpleDateFormat("MM/yyyy"));
        monthComboBox.setDate(null);
        monthComboBox.setEditable(true);
        panel.add(createTitledComponent("MonthExComboBox", "ExComboBox to choose a month", monthComboBox));

        _comboBoxes[i++] = monthComboBox;

        MonthExComboBox monthComboBox2 = new MonthExComboBox();
        monthComboBox2.setFormat(new SimpleDateFormat("MM/yyyy"));
        monthComboBox2.setDate(null);
        monthComboBox2.setEditable(false);
        panel.add(createTitledComponent("MonthExComboBox", "ExComboBox to choose a month", monthComboBox2));

        _comboBoxes[i++] = monthComboBox2;

        MultilineStringExComboBox multilineStringComboBox = new MultilineStringExComboBox();
        multilineStringComboBox.setEditable(true);
        panel.add(createTitledComponent("MultilineStringExComboBox", "ExComboBox to choose multiple line text", multilineStringComboBox));

        _comboBoxes[i++] = multilineStringComboBox;

        MultilineStringExComboBox multilineStringComboBox2 = new MultilineStringExComboBox();
        panel.add(createTitledComponent("MultilineStringExComboBox", "ExComboBox to choose multiple line text", multilineStringComboBox2));

        _comboBoxes[i++] = multilineStringComboBox2;

        StringArrayExComboBox stringArrayComboBox = new StringArrayExComboBox();
        stringArrayComboBox.setEditable(true);
        panel.add(createTitledComponent("StringArrayExComboBox", "ExComboBox to choose a string array", stringArrayComboBox));

        _comboBoxes[i++] = stringArrayComboBox;

        StringArrayExComboBox stringArrayComboBox2 = new StringArrayExComboBox();
        panel.add(createTitledComponent("StringArrayExComboBox", "ExComboBox to choose a string array", stringArrayComboBox2));

        _comboBoxes[i++] = stringArrayComboBox2;

        // create color combobox
        ExComboBox colorComboBox = new ColorExComboBox();
        colorComboBox.setEditable(true);
        colorComboBox.setToolTipText("ExComboBox to choose color");
        panel.add(createTitledComponent("ColorExComboBox", "ExComboBox to choose color", colorComboBox));

        _comboBoxes[i++] = colorComboBox;

        // create color combobox
        ExComboBox colorComboBox2 = new ColorExComboBox();
        colorComboBox2.setEditable(false);
        colorComboBox2.setToolTipText("ExComboBox to choose color (non-editable)");
        panel.add(createTitledComponent("ColorExComboBox", "ExComboBox to choose color (non-editable)", colorComboBox2));

        _comboBoxes[i++] = colorComboBox2;

        // create font combobox
        ExComboBox fontComboBox = new FontExComboBox();
        fontComboBox.setEditable(true);
        fontComboBox.setToolTipText("ExComboBox to choose a font");
        panel.add(createTitledComponent("FontExComboBox", "ExComboBox to choose a font", fontComboBox));

        _comboBoxes[i++] = fontComboBox;

        // create font combobox
        ExComboBox fontComboBox2 = new FontExComboBox();
        fontComboBox2.setToolTipText("ExComboBox to choose a font (non-editable)");
        panel.add(createTitledComponent("FontExComboBox", "ExComboBox to choose a font (non-editable)", fontComboBox2));

        _comboBoxes[i++] = fontComboBox2;

        // create file chooser combobox
        FileChooserExComboBox fileComboBox = new FileChooserExComboBox();
        fileComboBox.setEditable(true);
        panel.add(createTitledComponent("FileChooserExComboBox", "ExComboBox to choose a file", fileComboBox));

        _comboBoxes[i++] = fileComboBox;

        // create file chooser combobox
        FileChooserExComboBox fileComboBox2 = new FileChooserExComboBox();
        fileComboBox2.setEditable(false);
        panel.add(createTitledComponent("FileChooserExComboBox", "ExComboBox to choose a file", fileComboBox2));

        _comboBoxes[i++] = fileComboBox2;

        FolderChooserExComboBox foldComboBox = new FolderChooserExComboBox() {
            @Override
            public PopupPanel createPopupComponent() {
                PopupPanel popupComponent = super.createPopupComponent();
                popupComponent.setTitle("Save to ...");
                return popupComponent;
            }
        };
        foldComboBox.setEditable(true);
        panel.add(createTitledComponent("FolderChooserExComboBox", "ExComboBox to choose a folder", foldComboBox));

        _comboBoxes[i++] = foldComboBox;

        // create file chooser combobox
        FolderChooserExComboBox foldComboBox2 = new FolderChooserExComboBox();
        foldComboBox2.setEditable(false);
        panel.add(createTitledComponent("FolderChooserExComboBox", "ExComboBox to choose a folder", foldComboBox2));

        _comboBoxes[i++] = foldComboBox2;

        // create insets combobox
        InsetsExComboBox insetsComboBox = new InsetsExComboBox();
        insetsComboBox.setEditable(true);
        panel.add(createTitledComponent("InsetsExComboBox", "ExComboBox to choose an insets", insetsComboBox));

        _comboBoxes[i++] = insetsComboBox;

        // create insets combobox
        InsetsExComboBox insetsComboBox2 = new InsetsExComboBox();
        insetsComboBox2.setEditable(false);
        panel.add(createTitledComponent("InsetsExComboBox", "ExComboBox to choose an insets", insetsComboBox2));

        _comboBoxes[i] = insetsComboBox2;

        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "G4. ComboBoxes";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new ExComboBoxDemo());
            }
        });

    }

    private static ExComboBox createBooleanComboBox() {
        return new ListExComboBox(ListExComboBox.BOOLEAN_ARRAY, Boolean.class);
    }

    private static ExComboBox createListComboBox(String[] items) {
        return new ListExComboBox(items, String.class);
    }

    private static CheckBoxListExComboBox createCheckBoxListComboBox(String[] items) {
        return new CheckBoxListExComboBox(items, String[].class);
    }

    private static MultiSelectListExComboBox createMultiSelectListComboBox(String[] items) {
        return new MultiSelectListExComboBox(items, String[].class);
    }
}
