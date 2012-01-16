/*
 * @(#)JideSplitButtonDemo.java 3/19/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.basic.ThemePainter;
import com.jidesoft.swing.JideSplitButton;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.JideToggleSplitButton;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.swing.JideButton} <br> Required jar files: jide-common.jar, jide-components.jar
 * <br> Required L&F: Jide L&F extension required
 */
public class JideSplitButtonDemo extends AbstractDemo {
    private static final long serialVersionUID = -8707660318949717143L;
    private JideSplitButton[] _buttons;

    public JideSplitButtonDemo() {
    }

    public String getName() {
        return "JideSplitButton Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "JideSplitButton is a combination of button and menu. It has a button part just like JideButton but it also has a menu part where a popup menu could be added.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.JideSplitButton\n";
    }

    public Component getDemoPanel() {
        int numberOfButtons = 8;
        JPanel panel = new JPanel(new GridLayout(numberOfButtons, 1, 2, 2));
        _buttons = new JideSplitButton[numberOfButtons];
        int i = 0;
        _buttons[i++] = createJideSplitButton("Copy the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.COPY));
        _buttons[i++] = createJideSplitButton("Cut the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.CUT));
        _buttons[i++] = createJideSplitButton("Paste the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.PASTE));
        _buttons[i++] = createJideSplitButton("Delete the text", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.DELETE));
        _buttons[i++] = createJideSplitButton("Refresh the content", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.REFRESH));
        _buttons[i++] = createJideSplitButton("Undo the action", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.UNDO));
        _buttons[i++] = createJideSplitButton("Redo the action", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.REDO));
        _buttons[i] = createJideSplitButton("Action history", ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.HISTORY));

        for (JideSplitButton button : _buttons) {
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
                showAsFrame(new JideSplitButtonDemo());
            }
        });
    }

    @Override
    public Component getOptionsPanel() {
        JPanel switchPanel = new JPanel(new GridLayout(0, 1, 2, 2));

        final JRadioButton style1 = new JRadioButton("Toolbar Style");
        final JRadioButton style2 = new JRadioButton("Toolbox Style");
        final JRadioButton style3 = new JRadioButton("Flat Style");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(style1);
        buttonGroup.add(style2);
        buttonGroup.add(style3);

        switchPanel.add(new JLabel("Styles:"));
        switchPanel.add(style1);
        switchPanel.add(style2);
        switchPanel.add(style3);
        style1.setSelected(true);

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (style1.isSelected()) {
                    for (JideSplitButton button : _buttons) {
                        button.setButtonStyle(JideSplitButton.TOOLBAR_STYLE);
                    }
                }
                else if (style2.isSelected()) {
                    for (JideSplitButton button : _buttons) {
                        button.setButtonStyle(JideSplitButton.TOOLBOX_STYLE);
                    }
                }
                else if (style3.isSelected()) {
                    for (JideSplitButton button : _buttons) {
                        button.setButtonStyle(JideSplitButton.FLAT_STYLE);
                    }
                }
            }
        };
        style1.addItemListener(itemListener);
        style2.addItemListener(itemListener);
        style3.addItemListener(itemListener);

        switchPanel.add(new JLabel("Options: "));

        final JCheckBox option1 = new JCheckBox("Button part enabled");
        final JCheckBox option2 = new JCheckBox("Both parts enabled");
        final JCheckBox option3 = new JCheckBox("Button part selected");
        final JCheckBox option4 = new JCheckBox("Always Dropdown");

        switchPanel.add(option1);
        switchPanel.add(option2);
        switchPanel.add(option3);
        switchPanel.add(option4);

        option1.setSelected(true);
        option1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                for (JideSplitButton button : _buttons) {
                    button.setButtonEnabled(option1.isSelected());
                }
            }
        });

        option2.setSelected(true);
        option2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                for (JideSplitButton button : _buttons) {
                    button.setEnabled(option2.isSelected());
                }
            }
        });

        option3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                for (JideSplitButton button : _buttons) {
                    button.setButtonSelected(option3.isSelected());
                }
            }
        });

        option4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                for (JideSplitButton button : _buttons) {
                    button.setAlwaysDropdown(option4.isSelected());
                }
            }
        });

        return switchPanel;
    }

    static JideSplitButton createJideSplitButton(String name, Icon icon) {
        final JideSplitButton button = new JideSplitButton(name);
        button.setForegroundOfState(ThemePainter.STATE_DEFAULT, Color.BLACK);
        button.setIcon(icon);
        button.add(new AbstractAction("Sample Menu Item") {
            public void actionPerformed(ActionEvent e) {
            }
        });
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("button is clicked");
            }
        });
        button.getPopupMenu().addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                System.out.println("menu is clicked");
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        button.setFocusable(false);
        return button;
    }

    static JideSplitButton createJideToggleSplitButton(String name, Icon icon) {
        final JideSplitButton button = new JideToggleSplitButton(name);
        button.setIcon(icon);
        button.setForegroundOfState(ThemePainter.STATE_DEFAULT, Color.BLACK);
        button.add(new AbstractAction("Sample Menu Item") {
            public void actionPerformed(ActionEvent e) {
            }
        });
        button.setFocusable(false);
        return button;
    }
}
