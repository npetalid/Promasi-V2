/*
 * @(#)WrapStyledLabelDemo.java 8/3/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WrapStyledLabelDemo extends AbstractDemo {
    private static final long serialVersionUID = 50583030238760164L;

    private StyledLabel _label;
    private String _annotation = "{This is a demo to show a style label that is able to wrap automatically. : b} " +
            "{It is a long text to be wrapped. : f:blue} By default, it should occupy 3 rows. It can only be wrapped to between 2 and 5 rows." +
            "You could resize the panel to see how it automatically resizes. @rows:3:2:5";

    public String getName() {
        return "StyledLabel Demo (Line Wrap)";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        final JTextArea textArea = new JTextArea(_annotation);
        textArea.setLineWrap(true);
        JPanel textAreaPanel = new JPanel(new BorderLayout(2, 2));
        textAreaPanel.add(new JScrollPane(textArea));
        textAreaPanel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Annotation", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        panel.add(textAreaPanel);
        panel.add(new JButton(new AbstractAction("Refresh") {
            private static final long serialVersionUID = -3891515915512696213L;

            public void actionPerformed(ActionEvent e) {
                StyledLabelBuilder.setStyledText(_label, textArea.getText());
            }
        }), BorderLayout.AFTER_LAST_LINE);
        final JComboBox horizontalAlignment = new JComboBox(new String[]{"Leading", "Center", "Trailing"});
        horizontalAlignment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (horizontalAlignment.getSelectedIndex()) {
                    case 0:
                        _label.setHorizontalAlignment(SwingConstants.LEADING);
                        break;
                    case 1:
                        _label.setHorizontalAlignment(SwingConstants.CENTER);
                        break;
                    case 2:
                        _label.setHorizontalAlignment(SwingConstants.TRAILING);
                        break;
                }
            }
        });
        horizontalAlignment.setSelectedIndex(0);
        final JComboBox verticalAlignment = new JComboBox(new String[]{"Top", "Center", "Bottom"});
        verticalAlignment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (verticalAlignment.getSelectedIndex()) {
                    case 0:
                        _label.setVerticalAlignment(SwingConstants.TOP);
                        break;
                    case 1:
                        _label.setVerticalAlignment(SwingConstants.CENTER);
                        break;
                    case 2:
                        _label.setVerticalAlignment(SwingConstants.BOTTOM);
                        break;
                }
            }
        });
        verticalAlignment.setSelectedIndex(1);
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new JideBoxLayout(optionPanel, JideBoxLayout.Y_AXIS, 6));
        optionPanel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Horizontal Alignment: "), horizontalAlignment, BorderLayout.BEFORE_LINE_BEGINS));
        optionPanel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Vertical Alignment: "), verticalAlignment, BorderLayout.BEFORE_LINE_BEGINS));
        optionPanel.add(panel);
        return optionPanel;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    @Override
    public String getDescription() {
        return "StyledLabel is an enhanced JLabel that is able to wrap automatically. The actual annotated string is shown in the option panel. You can change the text and click \"Refresh\" button to see the change.\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.StyledLabel";
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        _label = StyledLabelBuilder.createStyledLabel(_annotation);
        panel.add(_label);
        return panel;
    }

    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new WrapStyledLabelDemo());
            }
        });
    }
}
