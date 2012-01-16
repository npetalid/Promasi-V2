/*
 * @(#)ComboBoxDemo.java 2/14/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Demoed Component: {@link ListComboBox}, {@link FileChooserComboBox}, {@link ColorComboBox}, {@link DateComboBox} <br>
 * Required jar files: jide-common.jar, jide-grids.jar <br> Required L&F: Jide L&F extension required
 */
public class ComboBoxDemo extends AbstractDemo {
    private static final long serialVersionUID = -2758050378982771174L;

    public ComboBoxDemo() {
    }

    public String getName() {
        return "AbstractComboBox Demo (replaced by ExComboBox)";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "This is a demo of various comboboxes. \n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.combobox.DateComboBox\n" +
                "com.jidesoft.combobox.ColorComboBox\n" +
                "com.jidesoft.combobox.TreeComboBox\n" +
                "com.jidesoft.combobox.ListComboBox\n" +
                "com.jidesoft.combobox.FileChooserComboBox";
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

    private AbstractComboBox[] _comboBoxes = new AbstractComboBox[30];

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));

        final JComboBox focusLost = new JComboBox(new String[]{"Commit", "Commit or Revert", "Revert", "Persist", "Commit or Reset", "Reset"});
        focusLost.setSelectedIndex(_comboBoxes[0].getFocusLostBehavior());
        focusLost.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    for (AbstractComboBox comboBox : _comboBoxes) {
                        if (comboBox != null) {
                            comboBox.setFocusLostBehavior(focusLost.getSelectedIndex());
                        }
                    }
                }
            }
        });

        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Focus Lost Behavior: "), focusLost, BorderLayout.BEFORE_LINE_BEGINS));

        final JComboBox popupCancel = new JComboBox(new String[]{"Persist", "Revert", "Reset"});
        popupCancel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    for (AbstractComboBox comboBox : _comboBoxes) {
                        if (comboBox != null) {
                            int selectedIndex = popupCancel.getSelectedIndex();
                            switch (selectedIndex) {
                                case 0:
                                    comboBox.setPopupCancelBehavior(AbstractComboBox.PERSIST);
                                    break;
                                case 1:
                                    comboBox.setPopupCancelBehavior(AbstractComboBox.REVERT);
                                    break;
                                case 2:
                                    comboBox.setPopupCancelBehavior(AbstractComboBox.RESET);
                                    break;
                            }
                        }
                    }
                }
            }
        });
        switch (_comboBoxes[0].getPopupCancelBehavior()) {
            case AbstractComboBox.PERSIST:
                popupCancel.setSelectedIndex(1);
                popupCancel.setSelectedIndex(0);
                break;
            case AbstractComboBox.REVERT:
                popupCancel.setSelectedIndex(0);
                popupCancel.setSelectedIndex(1);
                break;
            case AbstractComboBox.RESET:
                popupCancel.setSelectedIndex(0);
                popupCancel.setSelectedIndex(2);
                break;
        }

        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Popup Cancel Behavior: "), popupCancel, BorderLayout.BEFORE_LINE_BEGINS));

        return panel;
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 20, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox comboBox = new JComboBox(new String[]{"True", "False"});
        comboBox.setEditable(true);
        panel.add(createTitledComponent("Regular Editable JComboBox (For Comparison)", "Regular Editable JComboBox (For Comparison)", comboBox));

        JComboBox nonEditableComboBox = new JComboBox(new String[]{"True", "False"});
        nonEditableComboBox.setEditable(false);
        panel.add(createTitledComponent("Regular Non-editable JComboBox (For Comparison)", "Regular Non-editable JComboBox (For Comparison)", nonEditableComboBox));

        panel.add(new JPanel());
        panel.add(new JPanel());

        AbstractComboBox booleanComboBox = createBooleanComboBox();
        booleanComboBox.setSelectedItem(Boolean.FALSE);
        panel.add(createTitledComponent("Editable ListComboBox (Boolean)", "ComboBox to choose boolean", booleanComboBox));

        int i = 0;
        _comboBoxes[i++] = booleanComboBox;

        AbstractComboBox booleanComboBox2 = createBooleanComboBox();
        booleanComboBox2.setEditable(false);
        booleanComboBox2.setSelectedItem(Boolean.FALSE);
        panel.add(createTitledComponent("Non-editable ListComboBox (Boolean)", "ComboBox to choose boolean", booleanComboBox2));

        _comboBoxes[i++] = booleanComboBox2;

        // create font name combobox
        ListComboBox fontNameComboBox = createListComboBox();
        fontNameComboBox.setSelectedItem("Arial");
        panel.add(createTitledComponent("Editable ListComboBox (Font Name)", "ComboBox to choose font name or type in font name directly", fontNameComboBox));

        _comboBoxes[i++] = fontNameComboBox;

        ListComboBox fontNameComboBox2 = createListComboBox();
        fontNameComboBox2.setEditable(false);
        new ListComboBoxSearchable(fontNameComboBox2);
        fontNameComboBox2.setSelectedItem("Arial");
        panel.add(createTitledComponent("Non-editable ListComboBox (Font Name)", "ComboBox to choose font name from a list", fontNameComboBox2));

        _comboBoxes[i++] = fontNameComboBox2;

        CheckBoxListComboBox checkBoxListComboBox = createCheckBoxListComboBox();
        checkBoxListComboBox.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("CheckBoxListComboBox (Font Name) (Editable)", "ComboBox to choose multiple font names or type in font names directly", checkBoxListComboBox));

        _comboBoxes[i++] = checkBoxListComboBox;

        CheckBoxListComboBox checkBoxListComboBox2 = createCheckBoxListComboBox();
        checkBoxListComboBox2.setEditable(false);
//        new ListComboBoxSearchable(checkBoxListComboBox2);
        checkBoxListComboBox2.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("Non-editable CheckBoxListComboBox (Font Name)", "ComboBox to choose multiple font names", checkBoxListComboBox2));

        _comboBoxes[i++] = checkBoxListComboBox2;

        MultiSelectListComboBox multiSelectListComboBox = createMultiSelectListComboBox();
        multiSelectListComboBox.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("MultiSelectListComboBox (Font Name) (Editable)", "ComboBox to choose multiple font names or type in font names directly", multiSelectListComboBox));

        _comboBoxes[i++] = multiSelectListComboBox;

        MultiSelectListComboBox multiSelectListComboBox2 = createMultiSelectListComboBox();
        multiSelectListComboBox2.setEditable(false);
//        new ListComboBoxSearchable(multiSelectListComboBox2);
        multiSelectListComboBox2.setSelectedItem(new String[]{"Arial", "Verdana"});
        panel.add(createTitledComponent("Non-editable MultiSelectListComboBox (Font Name)", "ComboBox to choose multiple font names", multiSelectListComboBox2));

        _comboBoxes[i++] = multiSelectListComboBox2;

        AbstractComboBox treeComboBox = new TreeComboBox();
        panel.add(createTitledComponent("Editable TreeComboBox", "ComboBox which has a tree as the selection list", treeComboBox));

        _comboBoxes[i++] = treeComboBox;

        TreeComboBox treeComboBox2 = new TreeComboBox() {
            @Override
            protected boolean isValidSelection(TreePath path) {
                TreeNode treeNode = (TreeNode) path.getLastPathComponent();
                return treeNode.isLeaf();
            }
        };
        treeComboBox2.setEditable(false);
        treeComboBox2.setDoubleClickExpand(true);
        TreeComboBoxSearchable treeComboBoxSearchable = new TreeComboBoxSearchable(treeComboBox2);
        treeComboBoxSearchable.setRecursive(true);
        panel.add(createTitledComponent("Non-editable TreeComboBox (only leafs are selectable)", "ComboBox which has a tree as the selection list", treeComboBox2));

        _comboBoxes[i++] = treeComboBox2;

        TableComboBox tableComboBox = new TableComboBox(DemoData.createQuoteTableModel()) {
            @Override
            protected JTable createTable(TableModel model) {
                return new SortableTable(model);
            }
        };
        tableComboBox.setSelectedItem("ALCOA INC");
        tableComboBox.setMaximumRowCount(12);
        tableComboBox.setValueColumnIndex(1); // display the second column value in the combobox.
        panel.add(createTitledComponent("Editable TableComboBox", "ComboBox which has a table as the selection list", tableComboBox));

        _comboBoxes[i++] = tableComboBox;

        TableComboBox tableComboBox2 = new TableComboBox(DemoData.createQuoteTableModel()) {
            @Override
            protected JTable createTable(TableModel model) {
                return new SortableTable(model);
            }
        };
        tableComboBox2.setSelectedItem("ALCOA INC");
        tableComboBox2.setEditable(false);
        new TableComboBoxSearchable(tableComboBox2);
        tableComboBox2.setMaximumRowCount(12);
        tableComboBox2.setValueColumnIndex(1); // display the second column value in the combobox.
        panel.add(createTitledComponent("Non-editable TableComboBox", "ComboBox which has a table as the selection list", tableComboBox2));

        _comboBoxes[i++] = tableComboBox2;

        // create date combobox
        DateComboBox dateComboBox = new DateComboBox();
        dateComboBox.setDate(null);
        panel.add(createTitledComponent("DateComboBox", "ComboBox to choose date", dateComboBox));

        _comboBoxes[i++] = dateComboBox;

        // create date combobox
        DateComboBox datetimeComboBox = new DateComboBox();
        datetimeComboBox.setTimeDisplayed(true);
        datetimeComboBox.setFormat(DateFormat.getDateTimeInstance());
        datetimeComboBox.setTimeFormat("hh:mm:ss a");
        datetimeComboBox.setDate(Calendar.getInstance().getTime());
        panel.add(createTitledComponent("DateComboBox (Time Displayed)", "ComboBox to choose date and time", datetimeComboBox));

        _comboBoxes[i++] = datetimeComboBox;

        // create month combobox
        MonthComboBox monthComboBox = new MonthComboBox();
        monthComboBox.setFormat(new SimpleDateFormat("MM/yyyy"));
        monthComboBox.setDate(null);
        panel.add(createTitledComponent("MonthComboBox", "ComboBox to choose a month", monthComboBox));

        _comboBoxes[i++] = monthComboBox;
//        monthComboBox.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    System.out.println("Date selected: " + ObjectConverterManager.toString(e.getItem()));
//                }
//            }
//        });

        // create date combobox
        MultilineStringComboBox multilineStringComboBox = new MultilineStringComboBox();
        panel.add(createTitledComponent("MultilineStringComboBox", "ComboBox to choose multiple line text", multilineStringComboBox));

        _comboBoxes[i++] = multilineStringComboBox;

        // create color combobox
        ColorComboBox colorComboBox = new ColorComboBox();
        colorComboBox.setSelectedColor(Color.GREEN);
        colorComboBox.setInvalidValueAllowed(true);
        panel.add(createTitledComponent("ColorComboBox", "ComboBox to choose color", colorComboBox));

        _comboBoxes[i++] = colorComboBox;

        // create font combobox
        FontComboBox fontComboBox = new FontComboBox();
        fontComboBox.setToolTipText("ComboBox to choose a font");
        panel.add(createTitledComponent("FontComboBox", "ComboBox to choose a font", fontComboBox));

        _comboBoxes[i++] = fontComboBox;

        // create file chooser combobox
        FileChooserComboBox fileComboBox = new FileChooserComboBox();
        panel.add(createTitledComponent("FileChooserComboBox", "ComboBox to choose a file", fileComboBox));

        _comboBoxes[i++] = fileComboBox;

        // create insets combobox
        InsetsComboBox insetsComboBox = new InsetsComboBox();
        panel.add(createTitledComponent("InsetsComboBox", "ComboBox to choose an insets", insetsComboBox));

        _comboBoxes[i++] = insetsComboBox;

        // create DateSpinnerComboBox
        DateSpinnerComboBox dateSpinnerComboBox = new DateSpinnerComboBox();
        panel.add(createTitledComponent("DateSpinnerComboBox", "DateSpinnerComboBox", dateSpinnerComboBox));

        _comboBoxes[i++] = dateSpinnerComboBox;

        // set it to empty mainly for Windows L&F as the extra gap doesn't look when spinner is inside the combobox.
        // UIManager.put("Spinner.arrowButtonInsets", new InsetsUIResource(0, 0, 0, 0));

        // create NumberSpinnerComboBox
        SpinnerNumberModel numberModel = new SpinnerNumberModel(10.00, 0.00, 99.99, 0.01);
        NumberSpinnerComboBox numberSpinnerComboBox = new NumberSpinnerComboBox(numberModel) {
            private JPopupMenu _menu;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e != null) { // when event is not null, it means the combobox button is pressed.
                    if (_menu == null || !_menu.isVisible()) {
                        _menu = new JPopupMenu();
                        for (int i = 0; i < 9; i++) {
                            _menu.add(createMenuItem(10.0 + i * 10.0, this));
                        }
                        JComponent c = (JComponent) e.getSource();
                        _menu.show(c, -_menu.getPreferredSize().width + c.getWidth(), c.getHeight());
                    }
                    else {
                        _menu.setVisible(false);
                        _menu = null;
                    }
                }
            }

            @Override
            public PopupPanel createPopupComponent() {
                return null;
            }
        };
        numberSpinnerComboBox.setDecimalFormatPattern("0.00");
        panel.add(createTitledComponent("NumberSpinnerComboBox", "NumberSpinnerComboBox", numberSpinnerComboBox));

        _comboBoxes[i] = numberSpinnerComboBox;
        return panel;
    }

    private JMenuItem createMenuItem(final double value, final NumberSpinnerComboBox comboBox) {
        JMenuItem item = new JMenuItem(new DecimalFormat("0.00").format(value));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBox.setSelectedItem(value);
            }
        });
        return item;
    }

    @Override
    public String getDemoFolder() {
        return "G4.ComboBoxes";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new ComboBoxDemo());
            }
        });

    }

    private static ListComboBox createBooleanComboBox() {
        return new ListComboBox(ListComboBox.BOOLEAN_ARRAY, Boolean.class);
    }

    private static ListComboBox createListComboBox() {
        String[] fontNames = DemoData.getFontNames();
        return new ListComboBox(fontNames, String.class);
    }

    private static CheckBoxListComboBox createCheckBoxListComboBox() {
        String[] fontNames = DemoData.getFontNames();
        return new CheckBoxListComboBox(fontNames, String[].class);
    }

    private static MultiSelectListComboBox createMultiSelectListComboBox() {
        String[] fontNames = DemoData.getFontNames();
        return new MultiSelectListComboBox(fontNames, String[].class);
    }
}
