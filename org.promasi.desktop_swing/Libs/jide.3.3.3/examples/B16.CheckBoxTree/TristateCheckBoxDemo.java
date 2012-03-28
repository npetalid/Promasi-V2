/*
 * @(#)TristateCheckBoxDemo.java 6/9/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.TristateCheckBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.swing.CheckBoxTree} <br> Required jar files: jide-common.jar, jide-grids.jar
 * <br> Required L&F: any L&F
 */
public class TristateCheckBoxDemo extends AbstractDemo {
    private static final long serialVersionUID = -2257592750601144428L;
    private TristateCheckBox _checkBox;

    public TristateCheckBoxDemo() {
    }

    public String getName() {
        return "TristateCheckBox Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "This is a demo of TristateCheckBox. \n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.TristateCheckBox";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        final JRadioButton mixed = new JRadioButton("Mixed");
        final JRadioButton selected = new JRadioButton("Selected");
        final JRadioButton unselected = new JRadioButton("Unselected");
        ButtonGroup group = new ButtonGroup();

        group.add(mixed);
        group.add(selected);
        group.add(unselected);
        mixed.setSelected(true);

        panel.add(mixed);
        panel.add(selected);
        panel.add(unselected);

        ItemListener listener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int state = mixed.isSelected() ? TristateCheckBox.STATE_MIXED : (selected.isSelected() ? TristateCheckBox.STATE_SELECTED : TristateCheckBox.STATE_UNSELECTED);
                if (_checkBox.getState() != state) {
                    _checkBox.setState(state);
                }
            }
        };
        mixed.addItemListener(listener);
        selected.addItemListener(listener);
        unselected.addItemListener(listener);

        _checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int state = _checkBox.getState();
                switch (state) {
                    case TristateCheckBox.STATE_MIXED:
                        if (!mixed.isSelected()) mixed.setSelected(true);
                        break;
                    case TristateCheckBox.STATE_UNSELECTED:
                        if (!unselected.isSelected()) unselected.setSelected(true);
                        break;
                    case TristateCheckBox.STATE_SELECTED:
                        if (!selected.isSelected()) selected.setSelected(true);
                        break;
                }
            }
        });

        final JCheckBox enabled = new JCheckBox("Enabled");
        enabled.setSelected(true);
        enabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _checkBox.setEnabled(enabled.isSelected());
            }
        });
        panel.add(enabled);

        return panel;
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, BoxLayout.Y_AXIS));

        _checkBox = new TristateCheckBox("Tristate Check Box");
        _checkBox.setState(TristateCheckBox.STATE_MIXED);

        panel.add(_checkBox);
        panel.add(Box.createGlue(), JideBoxLayout.VARY);
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "B16.CheckBoxTree";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new TristateCheckBoxDemo());
            }
        });
    }
}
