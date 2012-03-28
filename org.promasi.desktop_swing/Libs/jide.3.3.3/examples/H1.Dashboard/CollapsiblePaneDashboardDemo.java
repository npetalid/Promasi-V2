/*
 * @(#)DashboardDemo.java 7/7/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.dashboard.*;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.dialog.StandardDialogPane;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.pane.CollapsiblePaneTitleButton;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideBoxLayout;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class CollapsiblePaneDashboardDemo extends AbstractDemo {
    private static final long serialVersionUID = -6050228050982730583L;

    public static String _lastDirectory = ".";
    protected DashboardTabbedPane _tabbedPane;
    private boolean _vertical = false;

    public CollapsiblePaneDashboardDemo() {
    }

    public String getName() {
        return "Dashboard Demo (using CollapsiblePane)";
    }

    public String getProduct() {
        return PRODUCT_NAME_DASHBOARD;
    }

    @Override
    public String getDemoFolder() {
        return "H1.Dashboard";
    }

    @Override
    public String getDescription() {
        return "This is a demo of Dashboard using CollapsiblePane as the gadget component. \n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.dasbhoard.GadgetManager\n" +
                "com.jidesoft.dasbhoard.Gadget\n" +
                "com.jidesoft.dasbhoard.GadgetComponent\n" +
                "com.jidesoft.dasbhoard.Dashboard\n" +
                "com.jidesoft.dasbhoard.DashboardTabbedPand";
    }

    public Component getDemoPanel() {
        GadgetManager manager = new GadgetManager() {
            @Override
            protected boolean validateGadgetDragging(Gadget gadget, Container targetContainer) {
                if ("Notes".equals(gadget.getName()) && targetContainer instanceof JComponent) {
                    StandardDialog dialog;
                    dialog = new GadgetDragDialog();
                    dialog.setLocationRelativeTo(targetContainer);
                    dialog.pack();
                    dialog.setVisible(true);
                    return dialog.getDialogResult() == StandardDialog.RESULT_AFFIRMED;
                }
                return super.validateGadgetDragging(gadget, targetContainer);
            }
        };
        manager.addGadget(createGadget("Calculator"));
        manager.addGadget(createGadget("Call"));
        manager.addGadget(createGadget("Clock"));
        manager.addGadget(createGadget("Find"));
        manager.addGadget(createGadget("Image"));
        manager.addGadget(createGadget("Network"));
        manager.addGadget(createGadget("News"));
        manager.addGadget(createGadget("Notes"));
        manager.addGadget(createGadget("Chart"));
        manager.setColumnResizable(true);

        _tabbedPane = new DashboardTabbedPane(manager) {
            @Override
            protected Container createToolBarComponent() {
                // uncomment this to see an extra button on the toolbar area
//                toolBarComponent.add(new JideButton("My Button"));
                return super.createToolBarComponent();
            }

            @Override
            protected GadgetPalette createGadgetPalette() {
                GadgetPalette palette = new GadgetPalette(getGadgetManager(), this) {
                    @Override
                    protected String getResourceString(String key) {
                        if (_vertical && "GadgetPalette.hint".equals(key)) {
                            return "...";
                        }
                        return super.getResourceString(key);
                    }
                };
                if (_vertical) {
                    palette.setButtonLayout(new GridLayout(0, 1));
                }
                else {
                    palette.setButtonLayout(new GridLayout(1, 0));
                }
//                palette.setButtonLayout(new GridLayout(2, 0)); // you could uncomment this to have two rows of gadget buttons
                return palette;
            }
        };
        _tabbedPane.setPreferredSize(new Dimension(1110, 700));

        final Dashboard dashBoard = _tabbedPane.createDashboard("Home Page");
        dashBoard.setColumnCount(3);
        dashBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        _tabbedPane.setUseFloatingPalette(false);
        _tabbedPane.getGadgetManager().addDashboard(dashBoard);
        manager.addGadgetListener(new GadgetListener() {
            public void eventHappened(GadgetEvent e) {
                if (e.getID() == GadgetEvent.GADGET_COMPONENT_MAXIMIZED) {
                    GadgetComponent gadgetComponent = e.getGadgetComponent();
                    while (gadgetComponent instanceof ResizableGadgetComponent) {
                        gadgetComponent = ((ResizableGadgetComponent) gadgetComponent).getActualGadgetComponent();
                    }
                    if (gadgetComponent instanceof CollapsiblePaneGadget) {
                        ((CollapsiblePaneGadget) gadgetComponent).setMaximized(true);
                    }
                }
                else if (e.getID() == GadgetEvent.GADGET_COMPONENT_RESTORED) {
                    GadgetComponent gadgetComponent = e.getGadgetComponent();
                    while (gadgetComponent instanceof ResizableGadgetComponent) {
                        gadgetComponent = ((ResizableGadgetComponent) gadgetComponent).getActualGadgetComponent();
                    }
                    if (gadgetComponent instanceof CollapsiblePaneGadget) {
                        ((CollapsiblePaneGadget) gadgetComponent).setMaximized(false);
                    }
                }
            }
        });

        return _tabbedPane;

    }

    @Override
    public Component getOptionsPanel() {
        ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JCheckBox columnResizable = new JCheckBox("Column Resizable");
        columnResizable.setSelected(_tabbedPane.getGadgetManager().isColumnResizable());
        columnResizable.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tabbedPane.getGadgetManager().setColumnResizable(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        buttonPanel.add(columnResizable);

        JCheckBox dragOutside = new JCheckBox("Allow Drag Outside");
        dragOutside.setSelected(_tabbedPane.getGadgetManager().isAllowDragOutside());
        dragOutside.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tabbedPane.getGadgetManager().setAllowDragOutside(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        buttonPanel.add(dragOutside);

        JCheckBox allowMultipleInstances = new JCheckBox("Allow Multiple Gadget Instances");
        allowMultipleInstances.setSelected(_tabbedPane.getGadgetManager().isAllowMultipleGadgetInstances());
        allowMultipleInstances.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tabbedPane.getGadgetManager().setAllowMultipleGadgetInstances(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        buttonPanel.add(allowMultipleInstances);

        JPanel comboBoxPanel = new JPanel(new BorderLayout(6, 6));
        JComboBox flowLayout = new JComboBox(new Object[]{"Jide Box Layout", "Grid Layout", "Flow Layout"});
        flowLayout.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if ("Jide Box Layout".equals(e.getItem())) {
                        _tabbedPane.getGadgetManager().getDashboard(0).setColumnCount(3);
                        _tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0).setLayout(new JideBoxLayout(_tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0), BoxLayout.Y_AXIS, Dashboard.V_GAP));
                    }
                    else if ("Grid Layout".equals(e.getItem())) {
                        _tabbedPane.getGadgetManager().getDashboard(0).setColumnCount(1);
                        _tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0).setLayout(new GridLayout(0, 3));
                    }
                    else if ("Flow Layout".equals(e.getItem())) {
                        _tabbedPane.getGadgetManager().getDashboard(0).setColumnCount(1);
                        _tabbedPane.getGadgetManager().getDashboard(0).getGadgetContainer(0).setLayout(new FlowLayout(FlowLayout.LEFT));
                    }
                }
            }
        });
        comboBoxPanel.add(flowLayout, BorderLayout.CENTER);
        comboBoxPanel.add(new JLabel("Select a Layout Manager: "), BorderLayout.BEFORE_LINE_BEGINS);
        buttonPanel.add(comboBoxPanel);

        JPanel palettePanel = new JPanel(new BorderLayout(6, 6));
        JComboBox paletteSideComboBox = new JComboBox(new Object[]{"SOUTH", "NORTH", "EAST", "WEST"});
        paletteSideComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if ("SOUTH".equals(e.getItem())) {
                        _vertical = false;
                        _tabbedPane.setPaletteSide(SwingConstants.SOUTH);
                    }
                    else if ("NORTH".equals(e.getItem())) {
                        _vertical = false;
                        _tabbedPane.setPaletteSide(SwingConstants.NORTH);
                    }
                    else if ("EAST".equals(e.getItem())) {
                        _vertical = true;
                        _tabbedPane.setPaletteSide(SwingConstants.EAST);
                    }
                    else if ("WEST".equals(e.getItem())) {
                        _vertical = true;
                        _tabbedPane.setPaletteSide(SwingConstants.WEST);
                    }
                }
            }
        });
        palettePanel.add(paletteSideComboBox, BorderLayout.CENTER);
        palettePanel.add(new JLabel("Select Palette Side: "), BorderLayout.BEFORE_LINE_BEGINS);
        buttonPanel.add(palettePanel);

        buttonPanel.addButton(new JButton(new AbstractAction("Save") {
            private static final long serialVersionUID = 6962555879727170794L;

            public void actionPerformed(ActionEvent e) {
                try {
//                    DashboardPersistenceUtils.save(tabbedPane, "C:\\dashboard.xml");
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
                        DashboardPersistenceUtils.save(_tabbedPane, chooser.getSelectedFile().getAbsolutePath());
                    }
                }
                catch (ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }));
        buttonPanel.addButton(new JButton(new AbstractAction("Load") {
            private static final long serialVersionUID = -679728270492525122L;

            public void actionPerformed(ActionEvent e) {
                try {
//                    DashboardPersistenceUtils.load(tabbedPane, "c:\\dashboard.xml");
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
                        DashboardPersistenceUtils.load(_tabbedPane, chooser.getSelectedFile().getAbsolutePath());
                    }
                }
                catch (SAXException e1) {
                    e1.printStackTrace();
                }
                catch (ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }));

        buttonPanel.addButton(new JButton(new AbstractAction("Toggle Column Count") {
            private static final long serialVersionUID = 1654341566173989620L;

            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = _tabbedPane.getActiveDashboard();
                if (dashboard != null) {
                    if (dashboard.getColumnCount() == 3)
                        dashboard.setColumnCount(2);
                    else if (dashboard.getColumnCount() == 2)
                        dashboard.setColumnCount(3);
                }
            }
        }));

        buttonPanel.addButton(new JButton(new AbstractAction("Remove \"Clock\" Gadget") {
            private static final long serialVersionUID = -8616408184059236279L;

            public void actionPerformed(ActionEvent e) {
                GadgetManager gadgetManager = _tabbedPane.getGadgetManager();
                if (gadgetManager.getGadget("Clock") == null) {
                    gadgetManager.addGadget(createGadget("Clock"), 2);
                    ((JButton) e.getSource()).setText("Remove \"Clock\" Gadget");
                }
                else {
                    gadgetManager.removeGadget("Clock");
                    ((JButton) e.getSource()).setText("Add \"Clock\" Gadget");
                }
            }
        }));

        buttonPanel.addButton(new JButton(new AbstractAction("Show \"Calculator\" Gadget at the second column") {
            private static final long serialVersionUID = -71583815415701766L;

            public void actionPerformed(ActionEvent e) {
                GadgetManager gadgetManager = _tabbedPane.getGadgetManager();
                Gadget gadget = gadgetManager.getGadget("Calculator");
                if (gadget != null) {
                    gadgetManager.showGadget(gadget, gadgetManager.getDashboard(gadgetManager.getActiveDashboardKey()), 1, 0);
                }
            }
        }));

        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new CollapsiblePaneDashboardDemo());
            }
        });
    }

    protected AbstractGadget createGadget(String key) {
        AbstractGadget dashboardElement = new AbstractGadget(key,
                IconsFactory.getImageIcon(CollapsiblePaneDashboardDemo.class, "icons/" + key.toLowerCase() + "_32x32.png"),
                IconsFactory.getImageIcon(CollapsiblePaneDashboardDemo.class, "icons/" + key.toLowerCase() + "_64x64.png")) {
            public GadgetComponent createGadgetComponent() {
                final CollapsiblePaneGadget gadget = new CollapsiblePaneGadget(this);
                gadget.getContentPane().setLayout(new BorderLayout());
                gadget.getContentPane().setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
                if (getKey().startsWith("Calculator")) {
                    gadget.getContentPane().add(GadgetFactory.createCalculator());
                }
                else if (getKey().startsWith("Call")) {
                    gadget.getContentPane().add(GadgetFactory.createCalendar());
                }
                else if (getKey().startsWith("Notes")) {
                    gadget.getContentPane().add(GadgetFactory.createNotes());
                }
                else if (getKey().startsWith("Find")) {
                    gadget.getContentPane().add(GadgetFactory.createFind());
                }
                else if (getKey().startsWith("News")) {
                    gadget.getContentPane().add(GadgetFactory.createNews());
                }
                else if (getKey().startsWith("Chart")) {
                    gadget.getContentPane().add(GadgetFactory.createChart());
                }
                else if (getKey().startsWith("Clock")) {
                    gadget.getContentPane().add(GadgetFactory.createClock());
                }
                else {
                    gadget.getContentPane().setPreferredSize(new Dimension(200, 100 + (int) (Math.random() * 100)));
                }
                CollapsiblePaneTitleButton toolButton = new CollapsiblePaneTitleButton(gadget, IconsFactory.getImageIcon(CollapsiblePaneDashboardDemo.class, "icons/gadget_tool.png"));
                toolButton.addActionListener(new AbstractAction() {
                    private static final long serialVersionUID = -6649978301193237112L;

                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(gadget, "Settings dialog goes here!");
                    }
                });
                gadget.addButton(toolButton, 1);
                gadget.setMessage("Last update on " + ObjectConverterManager.toString(Calendar.getInstance()));
                final ResizableGadgetComponent actualGadgetComponent = new ResizableGadgetComponent(gadget);
                gadget._maximizeButton.addActionListener(new AbstractAction() {
                    private static final long serialVersionUID = 7214349428761246735L;

                    public void actionPerformed(ActionEvent e) {
                        actualGadgetComponent.getGadget().getGadgetManager().maximizeGadgetComponent(actualGadgetComponent);
                        gadget.setMaximized(true);
                    }
                });
                gadget._restoreButton.addActionListener(new AbstractAction() {
                    private static final long serialVersionUID = 1610272975150978358L;

                    public void actionPerformed(ActionEvent e) {
                        actualGadgetComponent.getGadget().getGadgetManager().restoreGadgetComponent();
                        gadget.setMaximized(false);
                    }
                });
                return actualGadgetComponent;
            }

            public void disposeGadgetComponent(GadgetComponent component) {
                // do nothing in this case as we didn't allocate any resource in createGadgetComponent.
            }
        };
        dashboardElement.setDescription("Description is " + key);
        return dashboardElement;
    }

    public class GadgetDragDialog extends StandardDialog {
        public GadgetDragDialog() throws HeadlessException {
            super((Frame) null, "Gadget Drag Dialog Example");
        }

        @Override
        public JComponent createBannerPanel() {
            return null;
        }

        @Override
        protected StandardDialogPane createStandardDialogPane() {
            return new DefaultStandardDialogPane() {
                @Override
                protected void layoutComponents(Component bannerPanel, Component contentPanel, ButtonPanel buttonPanel) {
                    setLayout(new JideBoxLayout(this, BoxLayout.Y_AXIS));
                    if (bannerPanel != null) {
                        add(bannerPanel);
                    }
                    if (contentPanel != null) {
                        add(contentPanel);
                    }
                    add(buttonPanel, JideBoxLayout.FIX);
                }
            };
        }

        @Override
        public JComponent createContentPanel() {
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

            JLabel label = new JLabel("Allow drop the gadget to this location?");
            label.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(label, BorderLayout.CENTER);
            return panel;
        }

        @Override
        public ButtonPanel createButtonPanel() {
            ButtonPanel buttonPanel = new ButtonPanel();
            JButton okButton = new JButton();
            JButton cancelButton = new JButton();
            okButton.setName(OK);
            cancelButton.setName(CANCEL);
            buttonPanel.addButton(okButton, ButtonPanel.AFFIRMATIVE_BUTTON);
            buttonPanel.addButton(cancelButton, ButtonPanel.CANCEL_BUTTON);

            okButton.setAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {
                private static final long serialVersionUID = -8839840510614782122L;

                public void actionPerformed(ActionEvent e) {
                    setDialogResult(RESULT_AFFIRMED);
                    setVisible(false);
                    dispose();
                }
            });
            cancelButton.setAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {
                private static final long serialVersionUID = -2467038074716992814L;

                public void actionPerformed(ActionEvent e) {
                    setDialogResult(RESULT_CANCELLED);
                    setVisible(false);
                    dispose();
                }
            });

            setDefaultCancelAction(cancelButton.getAction());
            setDefaultAction(okButton.getAction());
            getRootPane().setDefaultButton(okButton);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPanel.setSizeConstraint(ButtonPanel.NO_LESS_THAN); // since the checkbox is quite wide, we don't want all of them have the same size.
            return buttonPanel;
        }
    }
}
