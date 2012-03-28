/*
 * @(#)TextFieldTransferBoxDemo.java 8/17/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.list.DualList;
import com.jidesoft.list.TextFieldList;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TextFieldListDemo extends AbstractDemo {

    private TextFieldList _list;
    private static final long serialVersionUID = 3026256070898636222L;

    public TextFieldListDemo() {
    }

    public String getName() {
        return "TextFieldList Demo";
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
        return "TextFieldList is a pane that contains a text field and a list. " +
                "User can input text in the field and " +
                "transfer it to the list on the right are selected items. " +
                "There are controls to move items back and forth. \n" +
                "We have full keyboard support in this component" +
                "\n1. LEFT or ENTER keys to move the text in the field to the list;" +
                "\n2. RIGHT or DELETE keys to remove the selected item from the list;" +
                "\n3. CTRL-UP and CTRL-DOWN keys will move the selected items up and down in the right list;" +
                "\n4. CTRL-HOME and CTRL-END keys will move the selected items to the top or the bottom in the right list;" +
                "\n5. CTRL-LEFT and CTRL-RIGHT keys will move focus between the field and the list.";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 2));
        JRadioButton noAction = new JRadioButton("Field Transferred No Action", true);
        noAction.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _list.setFieldTransferredMode(TextFieldList.FIELD_TRANSFERRED_NO_ACTION);
                }
            }
        });

        JRadioButton clearText = new JRadioButton("Field Transferred Clear Text");
        clearText.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _list.setFieldTransferredMode(TextFieldList.FIELD_TRANSFERRED_CLEAR_TEXT);
                }
            }
        });

        JRadioButton selectAll = new JRadioButton("Field Transferred Select All");
        selectAll.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _list.setFieldTransferredMode(TextFieldList.FIELD_TRANSFERRED_SELECT_ALL);
                }
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(noAction);
        group.add(clearText);
        group.add(selectAll);

        panel.add(noAction);
        panel.add(clearText);
        panel.add(selectAll);

        String[] commands = new String[]{
                DualList.COMMAND_MOVE_RIGHT,
                DualList.COMMAND_MOVE_LEFT,
                DualList.COMMAND_MOVE_UP,
                DualList.COMMAND_MOVE_DOWN,
                DualList.COMMAND_MOVE_TO_TOP,
                DualList.COMMAND_MOVE_TO_BOTTOM,
        };
        for (final String command : commands) {
            final JCheckBox b = new JCheckBox(String.format("Show \"%s\"", command), true);
            b.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    _list.setButtonVisible(command, b.isSelected());
                }
            });
            panel.add(b);
        }
        JCheckBox allowDuplicates = new JCheckBox("Allow Duplicate Selection");
        allowDuplicates.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _list.setAllowDuplicates(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        allowDuplicates.setSelected(_list.isAllowDuplicates());
        panel.add(allowDuplicates);
        return panel;
    }

    public JComponent getDemoPanel() {
        _list = new TextFieldList() {
            @Override
            protected boolean isInputValid(String text) {
                return super.isInputValid(text) && text.startsWith("192.168.");
            }
        };
        _list.setStartString("192.168.");
        JTextComponent textComponent = _list.getOriginalField();
        if (textComponent instanceof JTextField) {
            ((JTextField) textComponent).setColumns(10);
        }

        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.add(_list, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public String[] getDemoSource() {
        return new String[]{
                "TextFieldListDemo.java",
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new TextFieldListDemo());
            }
        });

    }
}