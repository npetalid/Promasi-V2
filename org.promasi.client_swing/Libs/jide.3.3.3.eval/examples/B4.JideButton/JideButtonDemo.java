/*
 * @(#)JideButtonDemo.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.AutoRepeatButtonUtils;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link JideButton} <br> Required jar files: jide-common.jar, jide-components.jar <br> Required L&F:
 * Jide L&F extension required
 */
public class JideButtonDemo extends AbstractDemo {

    private static final long serialVersionUID = -7769300787188774125L;
    private JideButton[] _buttons;

    public JideButtonDemo() {
    }

    public String getName() {
        return "JideButton Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "JideButton is a special JButton which is ideal for toolbar buttons. It has different styles and can adjust to different LookAndFeels.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.JideButton\n";
    }

    public Component getDemoPanel() {
        int numberOfButtons = 8;
        JPanel panel = new JPanel(new GridLayout(numberOfButtons, 1, 2, 2));
        _buttons = new JideButton[numberOfButtons];
        int i = 0;
        _buttons[i++] = createJideButton("Copy the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.COPY));
        _buttons[i++] = createJideButton("Cut the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.CUT));
        _buttons[i++] = createJideButton("Paste the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.PASTE));
        _buttons[i++] = createJideButton("Delete the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.DELETE));
        _buttons[i++] = createJideButton("Refresh the content", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.REFRESH));
        _buttons[i++] = createJideButton("Undo the action", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.UNDO));
        _buttons[i++] = createJideButton("Redo the action", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.REDO));
        _buttons[i] = createJideButton("Action history", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.HISTORY));
        for (AbstractButton button : _buttons) {
            panel.add(button);
        }
        return JideSwingUtilities.createTopPanel(panel);
    }

    @Override
    public String getDemoFolder() {
        return "B4.JideButton";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new JideButtonDemo());
            }
        });
    }

    @Override
    public Component getOptionsPanel() {
        final JRadioButton style1 = new JRadioButton("Toolbar Style");
        final JRadioButton style2 = new JRadioButton("Toolbox Style");
        final JRadioButton style3 = new JRadioButton("Flat Style");
        final JRadioButton style4 = new JRadioButton("Hyperlink Style");

        JCheckBox disabled = new JCheckBox("Disabled");
        disabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                for (AbstractButton button : _buttons) {
                    button.setEnabled(e.getStateChange() != ItemEvent.SELECTED);
                }
            }
        });
        disabled.setSelected(!_buttons[0].isEnabled());

        JCheckBox selected = new JCheckBox("Selected");
        selected.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                for (AbstractButton button : _buttons) {
                    button.setSelected(e.getStateChange() == ItemEvent.SELECTED);
                }
            }
        });
        selected.setSelected(_buttons[0].isSelected());

        JCheckBox opaque = new JCheckBox("Opaque");
        opaque.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                for (AbstractButton button : _buttons) {
                    button.setOpaque(e.getStateChange() == ItemEvent.SELECTED);
                }
            }
        });
        opaque.setSelected(_buttons[0].isOpaque());

        JCheckBox autoRepeat = new JCheckBox("Repeat actions when pressed and hold");
        autoRepeat.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    for (AbstractButton button : _buttons) {
                        AutoRepeatButtonUtils.install(button);
                    }
                }
                else {
                    for (AbstractButton button : _buttons) {
                        AutoRepeatButtonUtils.uninstall(button);
                    }
                }
            }
        });
        autoRepeat.setSelected(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(style1);
        buttonGroup.add(style2);
        buttonGroup.add(style3);
        buttonGroup.add(style4);
        JPanel switchPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        switchPanel.add(new JLabel("Styles:"));
        switchPanel.add(style1);
        switchPanel.add(style2);
        switchPanel.add(style3);
        switchPanel.add(style4);
        switchPanel.add(new JLabel("Options:"));
        switchPanel.add(selected);
        switchPanel.add(opaque);
        switchPanel.add(disabled);
        switchPanel.add(autoRepeat);

        style1.setSelected(true);
        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (style1.isSelected()) {
                    for (JideButton button : _buttons) {
                        button.setButtonStyle(JideButton.TOOLBAR_STYLE);
                    }
                }
                else if (style2.isSelected()) {
                    for (JideButton button : _buttons) {
                        button.setButtonStyle(JideButton.TOOLBOX_STYLE);
                    }
                }
                else if (style3.isSelected()) {
                    for (JideButton button : _buttons) {
                        button.setButtonStyle(JideButton.FLAT_STYLE);
                    }
                }
                else if (style4.isSelected()) {
                    for (JideButton button : _buttons) {
                        button.setButtonStyle(JideButton.HYPERLINK_STYLE);
                    }
                }
            }
        };
        style1.addItemListener(itemListener);
        style2.addItemListener(itemListener);
        style3.addItemListener(itemListener);
        style4.addItemListener(itemListener);

        return switchPanel;
    }

    static JideButton createJideButton(String name, Icon icon) {
        final JideButton button = new JideButton(new AbstractAction(name, icon) {
            private static final long serialVersionUID = 8861298458936950264L;

            public void actionPerformed(ActionEvent e) {
                System.out.println("actionPerformed");
            }
        });
        button.setHorizontalAlignment(SwingConstants.LEADING);
        button.setFocusable(false);
        return button;
    }
}
