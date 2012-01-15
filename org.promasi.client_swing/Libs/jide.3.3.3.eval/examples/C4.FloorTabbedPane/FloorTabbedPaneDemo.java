/*
 * @(#)FloorTabbedPaneDemo.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.pane.FloorTabbedPane;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link FloorTabbedPane} <br> Required jar files: jide-common.jar, jide-components.jar <br> Required
 * L&F: Jide L&F extension required
 */
public class FloorTabbedPaneDemo extends AbstractDemo {
    private static final long serialVersionUID = 4013622503250156505L;
    private FloorTabbedPane _tabbedPane;

    public FloorTabbedPaneDemo() {
    }

    public String getName() {
        return "FloorTabbedPane Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMPONENTS;
    }

    @Override
    public Component getOptionsPanel() {
        ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);
        JButton button = new JButton(new AbstractAction("Add a tab") {
            private static final long serialVersionUID = -925833407827123913L;

            public void actionPerformed(ActionEvent e) {
                TabPanel tabPanel = createTabPanel();
                addTabPanel(tabPanel);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.addButton(button);

        button = new JButton(new AbstractAction("Insert a tab at index 2 (if exists)") {
            private static final long serialVersionUID = -3821200919438352758L;

            public void actionPerformed(ActionEvent e) {
                TabPanel tabPanel = createTabPanel();
                addTabPanel(tabPanel, 2);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.addButton(button);

        button = new JButton(new AbstractAction("Delete the selected tab") {
            private static final long serialVersionUID = 3071811842720209213L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.removeTabAt(_tabbedPane.getSelectedIndex());
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.addButton(button);

        button = new JButton(new AbstractAction("Delete the 2nd tab (if exists)") {
            private static final long serialVersionUID = 9021113217429404470L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.removeTabAt(1);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.addButton(button);

        button = new JButton(new AbstractAction("Change the selected tab title") {
            private static final long serialVersionUID = 5174945757907029905L;

            public void actionPerformed(ActionEvent e) {
                int selectedIndex = _tabbedPane.getSelectedIndex();
                String titleAt = _tabbedPane.getTitleAt(selectedIndex);
                if (Character.isUpperCase(titleAt.charAt(0))) {
                    _tabbedPane.setTitleAt(selectedIndex, titleAt.toLowerCase());
                }
                else {
                    _tabbedPane.setTitleAt(selectedIndex, titleAt.toUpperCase());
                }
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.addButton(button);

        button = new JButton(new AbstractAction("Change the selected tab icon") {
            private static final long serialVersionUID = 6582035593340023848L;

            public void actionPerformed(ActionEvent e) {
                int selectedIndex = _tabbedPane.getSelectedIndex();
                _tabbedPane.setIconAt(selectedIndex, DemoData.ICONS[(selectedIndex + 1) % DemoData.ICONS.length]);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.addButton(button);

        button = new JButton(new AbstractAction("Change the selected tab tooltip") {
            private static final long serialVersionUID = 2183127899141942199L;

            public void actionPerformed(ActionEvent e) {
                int selectedIndex = _tabbedPane.getSelectedIndex();
                String toolTipAt = _tabbedPane.getToolTipTextAt(selectedIndex);
                if (Character.isUpperCase(toolTipAt.charAt(0))) {
                    _tabbedPane.setToolTipTextAt(selectedIndex, toolTipAt.toLowerCase());
                }
                else {
                    _tabbedPane.setToolTipTextAt(selectedIndex, toolTipAt.toUpperCase());
                }
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.addButton(button);

        final JCheckBox orientationCheckBox = new JCheckBox("Toggle sliding orientation");
        orientationCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tabbedPane.setOrientation(orientationCheckBox.isSelected() ? SwingConstants.VERTICAL : SwingConstants.HORIZONTAL);
            }
        });
        orientationCheckBox.setSelected(_tabbedPane.getOrientation() == SwingConstants.VERTICAL);
        buttonPanel.addButton(orientationCheckBox);

        return buttonPanel;
    }

    public Component getDemoPanel() {
        return createTabbedPane();
    }

    @Override
    public String getDescription() {
        return "FloorTabbedPane is another type of tabbed pane. A typical tabbed pane has many panels and corresponding tabs. The user can click on a tab to choose which panel to view. Although a FloorTabbedPane also has many panels, instead of using tabs, it just uses buttons to switch between panels. The _panes are organized vertically, as floors in a storied building (that's how it gets the name of FloorTabbedPane). One famous example of it is the Outlook Bar in the Microsoft Outlook product. \n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.pane.FloorTabbedPane";
    }

    @Override
    public String getDemoFolder() {
        return "C4.FloorTabbedPane";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new FloorTabbedPaneDemo());
            }
        });
    }

    private class TabPanel extends JPanel {
        Icon _icon;
        String _title;
        JComponent _component;

        public TabPanel(String title, Icon icon, JComponent component) {
            _title = title;
            _icon = icon;
            _component = component;
        }

        public Icon getIcon() {
            return _icon;
        }

        public void setIcon(Icon icon) {
            _icon = icon;
        }

        public String getTitle() {
            return _title;
        }

        public void setTitle(String title) {
            _title = title;
        }

        public JComponent getComponent() {
            return _component;
        }

        public void setComponent(JComponent component) {
            _component = component;
        }
    }

    private JTabbedPane createTabbedPane() {
        _tabbedPane = new FloorTabbedPane() {
            @Override
            protected AbstractButton createButton(Action action) {
                return new FloorButton(action);
            }
        };

        for (int i = 0; i < DemoData.TITLES.length; i++) {
            TabPanel tabPanel = createTabPanel(DemoData.TITLES[i], DemoData.ICONS[i], createTextArea(DemoData.TITLES[i]));
            addTabPanel(tabPanel);
        }

        for (int i = 0; i < DemoData.MNEMONICS.length; i++) {
            int mnemonic = DemoData.MNEMONICS[i];
            _tabbedPane.setMnemonicAt(i, mnemonic);
        }

        return _tabbedPane;
    }

    protected JComponent createTextArea(String text) {
        JScrollPane scrollPane = new JScrollPane(new JTextArea((text)));
        scrollPane.setName(text);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(200, 400));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    private void addTabPanel(TabPanel tabPanel) {
        _tabbedPane.addTab(tabPanel.getTitle(), tabPanel.getIcon(), tabPanel.getComponent(), "tooltip for " + tabPanel.getTitle());
    }

    private void addTabPanel(TabPanel tabPanel, int index) {
        _tabbedPane.insertTab(tabPanel.getTitle(), tabPanel.getIcon(), tabPanel.getComponent(), "tooltip for " + tabPanel.getTitle(), index);
    }

    protected TabPanel createTabPanel(String title, Icon icon, JComponent component) {
        return new TabPanel(title, icon, component);
    }

    protected TabPanel createTabPanel() {
        JScrollPane pane = new JScrollPane(new JTextArea());
        pane.setPreferredSize(new Dimension(200, 400));
        return new TabPanel(DemoData.TITLES[0], DemoData.ICONS[0], pane);
    }
}
