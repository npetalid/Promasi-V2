/*
 * @(#)ButtonPanelDemo.java	Dec 7, 2002
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.MultilineLabel;
import com.jidesoft.swing.StyledLabelBuilder;
import com.jidesoft.utils.SystemInfo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

/**
 * Demoed Component: {@link ButtonPanel} <br> Required jar files: jide-common.jar, jide-dialogs.jar <br> Required L&F:
 * Jide L&F extension required
 */
public class ButtonPanelDemo extends AbstractDemo {
    private static final long serialVersionUID = 4129503759270737288L;

    private static JTextField _orderTextField;
    private static JTextField _oppositeOrderTextField;
    private static JSpinner _grpGapTextField;
    private static JSpinner _btnGapTextField;
    private static JSpinner _minBtnWidthTextField;
    public ButtonPanel _buttonPanel;

    public static final String WINDOWS = "Windows L&F (on Windows only)";
    public static final String METAL = "Metal L&F";
    public static final String MAC_OS_X = "Aqua L&F (on Mac OS X only)";

    public ButtonPanelDemo() {
    }

    public String getName() {
        return "ButtonPanel Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "ButtonPanel is a panel to layout buttons based on button types, various options and platform preferences.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.dialog.ButtonPanel";
    }

    public Component getDemoPanel() {
        _buttonPanel = new ButtonPanel();
        _buttonPanel.setSizeConstraint(ButtonPanel.NO_LESS_THAN);
        JButton button = new JButton("Preview");
        _buttonPanel.add(button, ButtonPanel.OTHER_BUTTON);
        button = new JButton("Save As PDF...");
        _buttonPanel.add(button, ButtonPanel.OTHER_BUTTON);
        button = new JButton("Fax...");
        _buttonPanel.add(button, ButtonPanel.OTHER_BUTTON);
        button = new JButton("Cancel");
        _buttonPanel.add(button, ButtonPanel.CANCEL_BUTTON);
        button = new JButton("Print");
        _buttonPanel.add(button, ButtonPanel.AFFIRMATIVE_BUTTON);
        button = new JButton("Help");
        _buttonPanel.add(button, ButtonPanel.HELP_BUTTON);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel("This area is for the content of the dialog", JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        label.setPreferredSize(new Dimension(700, 400));
        panel.add(label, BorderLayout.CENTER);
        panel.add(_buttonPanel, BorderLayout.AFTER_LAST_LINE);

        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "W4.ButtonPanel";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new ButtonPanelDemo());
            }
        });
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));

        panel.add(new JLabel("LookAndFeel:"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        Vector<String> lnf = new Vector<String>();
        if (SystemInfo.isWindows()) {
            lnf.add(WINDOWS);
        }
        lnf.add(METAL);
        if (SystemInfo.isMacOSX()) {
            lnf.add(MAC_OS_X);
        }
        final JComboBox lnfComboBox = new JComboBox(lnf);
        if (SystemInfo.isWindows()) {
            lnfComboBox.setSelectedItem(WINDOWS);
        }
        else if (SystemInfo.isMacOSX()) {
            lnfComboBox.setSelectedItem(MAC_OS_X);
        }
        else {
            lnfComboBox.setSelectedItem(METAL);
        }

        lnfComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Runnable runnable = new Runnable() {
                        @SuppressWarnings({"EmptyCatchBlock"})
                        public void run() {
                            Object value = lnfComboBox.getSelectedItem();
                            try {
                                if (WINDOWS.equals(value)) {
                                    try {
                                        if (SystemInfo.isWindows()) {
                                            UIManager.setLookAndFeel(LookAndFeelFactory.WINDOWS_LNF);
                                        }
                                        else {
                                            UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
                                        }
                                    }
                                    catch (ClassNotFoundException e1) {
                                    }
                                    catch (InstantiationException e1) {
                                    }
                                    catch (IllegalAccessException e1) {
                                    }
                                    LookAndFeelFactory.installJideExtension();
                                }
                                else if (METAL.equals(value)) {
                                    try {
                                        UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
                                    }
                                    catch (ClassNotFoundException e1) {
                                    }
                                    catch (InstantiationException e1) {
                                    }
                                    catch (IllegalAccessException e1) {
                                    }
                                    LookAndFeelFactory.installJideExtension();
                                }
                                else if (MAC_OS_X.equals(value)) {
                                    try {
                                        if (SystemInfo.isMacOSX()) {
                                            UIManager.setLookAndFeel(LookAndFeelFactory.AQUA_LNF);
                                        }
                                        else {
                                            UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
                                        }
                                    }
                                    catch (ClassNotFoundException e1) {
                                    }
                                    catch (InstantiationException e1) {
                                    }
                                    catch (IllegalAccessException e1) {
                                    }
                                    LookAndFeelFactory.installJideExtension();
                                }
                            }
                            catch (UnsupportedLookAndFeelException e1) {
                            }
                            SwingUtilities.updateComponentTreeUI(_buttonPanel.getTopLevelAncestor());
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                    _orderTextField.setText(UIDefaultsLookup.getString("ButtonPanel.order"));
                    _oppositeOrderTextField.setText(UIDefaultsLookup.getString("ButtonPanel.oppositeOrder"));
                    _grpGapTextField.setValue(UIDefaultsLookup.getInt("ButtonPanel.groupGap"));
                    _btnGapTextField.setValue(UIDefaultsLookup.getInt("ButtonPanel.buttonGap"));
                    _minBtnWidthTextField.setValue(UIDefaultsLookup.getInt("ButtonPanel.minButtonWidth"));
                    doLayout(_buttonPanel);
                }
            }
        });
        panel.add(lnfComboBox);
        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(new JLabel("Alignment:"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        Object[] alignments = new Object[]{
                "SwingConstants.CENTER", "SwingConstants.TOP", "SwingConstants.LEFT", "SwingConstants.BOTTOM", "SwingConstants.RIGHT", "SwingConstants.LEADING", "SwingConstants.TRAILING"};
        final JComboBox comboBox = new JComboBox(alignments);
        int index = _buttonPanel.getAlignment();
        if (index > SwingConstants.RIGHT) {
            comboBox.setSelectedIndex(index == SwingConstants.LEADING ? 5 : 6);
        }
        else {
            comboBox.setSelectedIndex(index);
        }
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int selected = comboBox.getSelectedIndex();
                    if (selected > SwingConstants.RIGHT) {
                        JPanel panel = (JPanel) _buttonPanel.getParent();
                        panel.add(_buttonPanel, BorderLayout.AFTER_LAST_LINE);
                        _buttonPanel.setAlignment(selected == 5 ? SwingConstants.LEADING : SwingConstants.TRAILING);
                        doLayout(_buttonPanel);
                    }
                    else {
                        if (selected == SwingConstants.TOP || selected == SwingConstants.BOTTOM) {
                            JPanel panel = (JPanel) _buttonPanel.getParent();
                            panel.add(_buttonPanel, BorderLayout.AFTER_LINE_ENDS);
                            _buttonPanel.setAlignment(selected);
                            doLayout(_buttonPanel);
                        }
                        else {
                            JPanel panel = (JPanel) _buttonPanel.getParent();
                            panel.add(_buttonPanel, BorderLayout.AFTER_LAST_LINE);
                            _buttonPanel.setAlignment(selected);
                            doLayout(_buttonPanel);
                        }
                    }
                }
            }
        });
        panel.add(comboBox);

        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(new MultilineLabel("Please refer to JIDE Dialogs Developer Guide for the details about the options below."));
        panel.add(Box.createVerticalStrut(5), JideBoxLayout.FIX);

        panel.add(StyledLabelBuilder.createStyledLabel("{Button order:b} (available: A C O H):"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        _orderTextField = new JTextField(_buttonPanel.getButtonOrder());
        panel.add(_orderTextField);
        _orderTextField.setColumns(6);

        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(StyledLabelBuilder.createStyledLabel("{Opposite button order:b} (available: A C O H):"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        _oppositeOrderTextField = new JTextField(_buttonPanel.getOppositeButtonOrder());
        panel.add(_oppositeOrderTextField);
        _oppositeOrderTextField.setColumns(6);

        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(new JLabel("Gap between button groups:"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        _grpGapTextField = new JSpinner(new SpinnerNumberModel(_buttonPanel.getGroupGap(), 0, Integer.MAX_VALUE, 1));
        _grpGapTextField.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    Number number = (Number) _grpGapTextField.getValue();
                    _buttonPanel.setGroupGap(number.intValue());
                    doLayout(_buttonPanel);
                }
                catch (NumberFormatException e1) {
                }
            }
        });
        panel.add(_grpGapTextField);

        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(new JLabel("Gap between buttons:"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        _btnGapTextField = new JSpinner(new SpinnerNumberModel(_buttonPanel.getButtonGap(), 0, Integer.MAX_VALUE, 1));
        _btnGapTextField.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    Number number = (Number) _btnGapTextField.getValue();
                    _buttonPanel.setButtonGap(number.intValue());
                    doLayout(_buttonPanel);
                }
                catch (NumberFormatException e1) {
                }
            }
        });
        panel.add(_btnGapTextField);

        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(new JLabel("Minimum button width:"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        _minBtnWidthTextField = new JSpinner(new SpinnerNumberModel(_buttonPanel.getMinButtonWidth(), 0, 200, 1));
        _minBtnWidthTextField.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    Number number = (Number) _minBtnWidthTextField.getValue();
                    _buttonPanel.setMinButtonWidth(number.intValue());
                    doLayout(_buttonPanel);
                }
                catch (NumberFormatException e1) {
                }
            }
        });
        panel.add(_minBtnWidthTextField);

        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(new JLabel("Size constraint:"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        final JRadioButton sameSize = new JRadioButton("Same size");
        sameSize.setOpaque(false);
        panel.add(sameSize);

        final JRadioButton noLessThan = new JRadioButton("No less than ");
        noLessThan.setOpaque(false);
        panel.add(noLessThan);

        ButtonGroup size = new ButtonGroup();
        size.add(sameSize);
        size.add(noLessThan);

        sameSize.setSelected(_buttonPanel.getSizeConstraint() == ButtonPanel.SAME_SIZE);
        noLessThan.setSelected(_buttonPanel.getSizeConstraint() == ButtonPanel.NO_LESS_THAN);

        sameSize.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (sameSize.isSelected()) {
                    _buttonPanel.setSizeConstraint(ButtonPanel.SAME_SIZE);
                    doLayout(_buttonPanel);
                }
                else {
                    _buttonPanel.setSizeConstraint(ButtonPanel.NO_LESS_THAN);
                    doLayout(_buttonPanel);
                }
            }
        });

        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

        panel.add(Box.createGlue(), JideBoxLayout.VARY);

        final JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            @SuppressWarnings({"EmptyCatchBlock"})
            public void actionPerformed(ActionEvent e) {
                String text = _orderTextField.getText();
                _buttonPanel.setButtonOrder(text);
                String text2 = _oppositeOrderTextField.getText();
                _buttonPanel.setOppositeButtonOrder(text2);
                doLayout(_buttonPanel);
            }
        });
        panel.add(new MultilineLabel("Press refresh to see the change made to fields that are marked as bold"));
        panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
        panel.add(refresh);

        panel.setOpaque(false);
        return panel;
    }

    private static void doLayout(final ButtonPanel buttonPanel) {
        JPanel panel = (JPanel) buttonPanel.getParent();
        buttonPanel.invalidate();
        panel.doLayout();
        buttonPanel.doLayout();
        buttonPanel.repaint();
        panel.repaint();
    }
}
