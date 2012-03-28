/*
 * @(#)OutlookTabbedPaneDemo.java 11/17/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.pane.OutlookTabbedPane;
import com.jidesoft.pane.OutlookTabbedPanePersistenceUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

/**
 * Demoed Component: {@link com.jidesoft.pane.OutlookTabbedPane} <br> Required jar files: jide-common.jar,
 * jide-components.jar <br> Required L&F: Jide L&F extension required
 */
public class OutlookTabbedPaneDemo extends AbstractDemo {
    private static final long serialVersionUID = 7785681496241271538L;

    private OutlookTabbedPane _tabbedPane;
    protected String[] _savedTabOrder;
    protected int[] _savedVisibleTabs;
    private String _lastDirectory = ".";

    public OutlookTabbedPaneDemo() {
    }

    public String getName() {
        return "OutlookTabbedPane Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMPONENTS;
    }

    @Override
    public Component getOptionsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 6, 6));

        final JComboBox comboBox = new JComboBox(new String[]{"Align to Top", "Align to Bottom"});
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (comboBox.getSelectedIndex() == 0) {
                    _tabbedPane.setTabPlacement(OutlookTabbedPane.TOP);
                }
                else {
                    _tabbedPane.setTabPlacement(OutlookTabbedPane.BOTTOM);
                }
            }
        });
        comboBox.setSelectedIndex(1);
        buttonPanel.add(comboBox);

        JButton button = new JButton(new AbstractAction("Add a tab") {
            private static final long serialVersionUID = 3407567552036514745L;

            public void actionPerformed(ActionEvent e) {
                TabPanel tabPanel = createTabPanel(_tabbedPane.getTabCount() + 1);
                addTabPanel(tabPanel);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Insert a tab at index 2 (if exists)") {
            private static final long serialVersionUID = -3196078180496144680L;

            public void actionPerformed(ActionEvent e) {
                TabPanel tabPanel = createTabPanel(_tabbedPane.getTabCount() + 1);
                addTabPanel(tabPanel, 2);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Delete selected tab") {
            private static final long serialVersionUID = 1370077058343316420L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.removeTabAt(_tabbedPane.getSelectedIndex());
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Delete the 2nd tab (if exists)") {
            private static final long serialVersionUID = -4162988075925979198L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.removeTabAt(1);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Show/hide the 3rd tab") {
            private static final long serialVersionUID = 8821790606742501800L;

            @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
            public void actionPerformed(ActionEvent e) {
                _tabbedPane.setTabVisibleAt(2, !_tabbedPane.isTabVisibleAt(2));
                int[] tabs = _tabbedPane.getVisibleTabs();
                for (int tab : tabs) {
                    System.out.print(tab + ",");
                }
                System.out.println();
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Get the tab order") {
            private static final long serialVersionUID = 9145095439590306602L;

            @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
            public void actionPerformed(ActionEvent e) {
                _savedTabOrder = _tabbedPane.getTabOrder();
                for (String o : _savedTabOrder) {
                    System.out.print(o + ",");

                }
                System.out.println();
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Set the tab order") {
            private static final long serialVersionUID = 2175665964035818471L;

            public void actionPerformed(ActionEvent e) {
                if (_savedTabOrder != null) {
                    _tabbedPane.setTabOrder(_savedTabOrder);
                }
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Get the visible tabs") {
            private static final long serialVersionUID = -2519072080759579799L;

            @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
            public void actionPerformed(ActionEvent e) {
                _savedVisibleTabs = _tabbedPane.getVisibleTabs();
                for (int index : _savedVisibleTabs) {
                    System.out.print(index + ",");

                }
                System.out.println();
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Set visible tabs") {
            private static final long serialVersionUID = 9145650520493008254L;

            public void actionPerformed(ActionEvent e) {
                if (_savedVisibleTabs != null) {
                    _tabbedPane.setVisibleTabs(_savedVisibleTabs);
                }
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Set the bottom button count to 3") {
            private static final long serialVersionUID = -2079111371579395087L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.setBottomButtonCount(3);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Change the selected tab title") {
            private static final long serialVersionUID = -8040098578536100667L;

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
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Change the selected tab icon") {
            private static final long serialVersionUID = -1454366048899688582L;

            public void actionPerformed(ActionEvent e) {
                int selectedIndex = _tabbedPane.getSelectedIndex();
                _tabbedPane.setIconAt(selectedIndex, JideIconsFactory.getImageIcon("jide/dockableframe_blank.gif"));
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Change the selected tab tooltip") {
            private static final long serialVersionUID = -9178513655636810944L;

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
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Select tab 1") {
            private static final long serialVersionUID = 2564503446919642513L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.setSelectedIndex(1);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Disable tab 1") {
            private static final long serialVersionUID = 3824048435274856585L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.setEnabledAt(1, false);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        button = new JButton(new AbstractAction("Enable tab 1") {
            private static final long serialVersionUID = 2009547712664880954L;

            public void actionPerformed(ActionEvent e) {
                _tabbedPane.setEnabledAt(1, true);
            }
        });
        button.setRequestFocusEnabled(false);
        buttonPanel.add(button);

        JButton saveButton = new JButton(new AbstractAction("Save layout as XML") {
            private static final long serialVersionUID = -5107715775596570738L;

            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser() {
                        @Override
                        protected JDialog createDialog(Component parent) throws HeadlessException {
                            JDialog dialog = super.createDialog(parent);
                            dialog.setTitle("Save the layout as an \".xml\" file");
                            return dialog;
                        }
                    };
                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Save");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        OutlookTabbedPanePersistenceUtils.save(_tabbedPane, chooser.getSelectedFile().getAbsolutePath());
                    }
                }
                catch (ParserConfigurationException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
            }
        });
        JButton loadButton = new JButton(new AbstractAction("Load layout from XML") {
            private static final long serialVersionUID = 3732486289243549658L;

            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser() {
                        @Override
                        protected JDialog createDialog(Component parent) throws HeadlessException {
                            JDialog dialog = super.createDialog(parent);
                            dialog.setTitle("Load an \".xml\" file");
                            return dialog;
                        }
                    };
                    chooser.setCurrentDirectory(new File(_lastDirectory));
                    int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Open");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                        OutlookTabbedPanePersistenceUtils.load(_tabbedPane, chooser.getSelectedFile().getAbsolutePath());
                    }
                }
                catch (SAXException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
                catch (ParserConfigurationException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    //noinspection CallToPrintStackTrace
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        JCheckBox chevron = new JCheckBox("Show Chevron");
        chevron.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tabbedPane.setChevronVisible(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        chevron.setSelected(_tabbedPane.isChevronVisible());
        buttonPanel.add(chevron);

        return buttonPanel;
    }

    public Component getDemoPanel() {
        return createTabbedPane();
    }

    @Override
    public String getDescription() {
        return "OutlookTabbedPane is another type of tabbed pane. A typical tabbed pane has many panels and corresponding tabs. " +
                "The user can click on a tab to choose which panel to view. " +
                "Although a OutlookTabbedPane also has many panels, instead of using tabs, " +
                "it just uses buttons to switch between panels. " +
                "One famous example of it is the Outlook Bar in the Microsoft Outlook product. That's why we called it OutlookTabbedPane.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.pane.OutlookTabbedPane";
    }

    @Override
    public String getDemoFolder() {
        return "C4.OutlookTabbedPane";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new OutlookTabbedPaneDemo());
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
        _tabbedPane = new OutlookTabbedPane() {
            @Override
            protected void customizeButton(AbstractButton button) {
                super.customizeButton(button);
                for (int i = 0; i < DemoData.TITLES.length; i++) {
                    if (DemoData.TITLES[i].equals(button.getName())) {
                        button.setIcon(DemoData.ICONS_LARGE[i]);
                        button.setFont(UIDefaultsLookup.getFont("Button.font").deriveFont(Font.BOLD));
                        break;
                    }
                }
            }

            @Override
            protected void customizeBottomButton(AbstractButton button) {
                super.customizeBottomButton(button);
                for (int i = 0; i < DemoData.TITLES.length; i++) {
                    if (DemoData.TITLES[i].equals(button.getName())) {
                        button.setIcon(DemoData.ICONS[i]);
                        button.setFont(UIDefaultsLookup.getFont("Button.font"));
                        break;
                    }
                }
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

        _tabbedPane.setSelectedIndex(0);

        return _tabbedPane;
    }

    protected JComponent createTextArea(String text) {
        JScrollPane scrollPane = new JScrollPane(new JTextArea((text)));
        scrollPane.setName(text);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(200, 400));
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

    protected TabPanel createTabPanel(int index) {
        return new TabPanel("Tab " + index,
                JideIconsFactory.getImageIcon("jide/dockableframe_" + index + ".gif"),
                createTextArea(("Tab " + index)));
    }

}
