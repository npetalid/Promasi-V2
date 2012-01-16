/*
 * @(#)TradingHierarchicalTableDemo.java 10/23/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.ListComboBox;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.grid.*;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.pane.BookmarkPane;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.plaf.basic.ThemePainter;
import com.jidesoft.swing.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Demoed Component: {@link com.jidesoft.grid.HierarchicalTable} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: Jide L&F extension required
 */
public class TradingHierarchicalTableDemo extends AbstractDemo {
    protected static final Color BG1 = new Color(255, 255, 255);
    private static final long serialVersionUID = -7904043279943293553L;

    protected TableModel _quotesTableModel;

    public TradingHierarchicalTableDemo() {
    }

    public String getName() {
        return "HierarchicalTable (Trading) Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    public Component getDemoPanel() {
        HierarchicalTable table = createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    @Override
    public String getDescription() {
        return "This is a demo of HierarchicalTable. HierarchicalTable is a JTable which supports nested components for each row.\n" +
                "\nIn this demo, you will see a table that looks just like a regular JTable. However when you select a row, you will see its nested component." +
                "It's special nested panel which looks like tabbed pane. Clicking on each \"tab\" will show its content. " +
                "As you can see from this demo, HierarchicalTable gives a lot of flexibilities to a table.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.HierarchicalTable\n" +
                "com.jidesoft.grid.HierarchicalTableModel";
    }

    @Override
    public String getDemoFolder() {
        return "G8.HierarchicalTable";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new TradingHierarchicalTableDemo());
            }
        });

    }

    // create property table
    private HierarchicalTable createTable() {
        _quotesTableModel = new QuoteTableModel();
        final HierarchicalTable table = new HierarchicalTable(_quotesTableModel);
        table.setTableStyleProvider(new RowStripeTableStyleProvider());
        table.setName("Quote Table");
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setHierarchicalColumn(-1);
        table.setSingleExpansion(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectedRow() != -1) {
                    table.expandRow(table.getSelectedRow());
                }
            }
        });
        table.setComponentFactory(new HierarchicalTableComponentFactory() {
            public Component createChildComponent(HierarchicalTable table, Object value, int row) {
                BookmarkPane pane = new BookmarkPane() {
                    @Override
                    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
                        if (!(component instanceof ResizablePanel)) {
                            ResizablePanel panel = new ResizablePanel(new BorderLayout()) {
                                @Override
                                protected Resizable createResizable() {
                                    Resizable resizable = new Resizable(this);
                                    resizable.setResizableCorners(Resizable.LOWER);
                                    return resizable;
                                }
                            };
                            panel.add(component);
                            panel.setBorder(new EmptyBorder(0, 0, 4, 0) {
                                @Override
                                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                                    super.paintBorder(c, g, x, y, width, height);
                                    ThemePainter painter = (ThemePainter) UIDefaultsLookup.get("Theme.painter");
                                    if (painter != null) {
                                        Insets insets = getBorderInsets(c);
                                        Rectangle rect = new Rectangle(0, y + height - insets.bottom, width, insets.bottom);
                                        painter.paintGripper((JComponent) c, g, rect, SwingConstants.HORIZONTAL, ThemePainter.STATE_DEFAULT);
                                    }
                                }
                            });
                            super.insertTab(title, icon, panel, tip, index);
                        }
                        else {
                            super.insertTab(title, icon, component, tip, index);
                        }
                    }
                };
                pane.setBorder(new HierarchicalPanelBorder());
                pane.addTab("Trade", new TradePanel(table, row));
                pane.addTab("Option", new OptionPanel(table, row));
                pane.addTab("Chart", new ChartPanel(table, row));
                return new HierarchicalPanel(pane, BorderFactory.createEmptyBorder());
            }

            public void destroyChildComponent(HierarchicalTable table, Component component, int row) {
            }
        });
        table.setPreferredScrollableViewportSize(new Dimension(600, 400));
        table.getSelectionModel().setAnchorSelectionIndex(0);
        table.getSelectionModel().setLeadSelectionIndex(0);
        return table;
    }


    static class QuoteTableModel extends DefaultTableModel implements HierarchicalTableModel {

        public QuoteTableModel() {
            super(DemoData.QUOTES, DemoData.QUOTE_COLUMNS);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public boolean hasChild(int row) {
            return true;
        }

        public boolean isExpandable(int row) {
            return true;
        }

        public boolean isHierarchical(int row) {
            return true;
        }

        public Object getChildValueAt(int row) {
            return null;
        }
    }

    static class ChartPanel extends JPanel {
        private HierarchicalTable _table;
        private int _row;

        public ChartPanel(HierarchicalTable table, int row) {
            _table = table;
            _row = row;
            initComponents();
        }

        public ChartPanel() {
            initComponents();
        }

        void initComponents() {
            setLayout(new BorderLayout(4, 4));
            setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialLineBorder(UIDefaultsLookup.getColor("controlShadow"), 1, PartialSide.NORTH), "Chart", JideTitledBorder.RIGHT, JideTitledBorder.CENTER),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            add(createChartPanel(), BorderLayout.CENTER);
            JideSwingUtilities.setOpaqueRecursively(this, false);
            setOpaque(true);
            setBackground(BG1);
        }

        JComponent createChartPanel() {
            return new JLabel(IconsFactory.getImageIcon(TradingHierarchicalTableDemo.class, "icons/chart.png"));
        }
    }

    static class TradePanel extends JPanel {
        private HierarchicalTable _table;
        private int _row;

        public TradePanel(HierarchicalTable table, int row) {
            _table = table;
            _row = row;
            initComponents();
        }

        public TradePanel() {
            initComponents();
        }

        void initComponents() {
            setLayout(new BorderLayout(4, 4));
            setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialLineBorder(UIDefaultsLookup.getColor("controlShadow"), 1, PartialSide.NORTH), "Trade", JideTitledBorder.RIGHT, JideTitledBorder.CENTER),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            add(createDetailPanel(), BorderLayout.CENTER);
//            add(createButtonPanel(), BorderLayout.AFTER_LAST_LINE);
            JideSwingUtilities.setOpaqueRecursively(this, false);
            setOpaque(true);
            setBackground(BG1);
        }

        JComponent createDetailPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            JTable table = new JTable(10, 5);
            panel.add(new JScrollPane(table));
//            panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.X_AXIS));
//            panel.add(new LabelCombobox("Order Type:", 'O', new String[]{"Select one", "Buy", "Sell", "Sell short", "Buy to over"}));
//            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
//            panel.add(new LabelTextField("Quality:", 'Q', 8));
//            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
//            panel.add(new LabelTextField("Price:", 'P', 8));
//            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
//            panel.add(new LabelCombobox("Price Type:", 'R', new String[]{"Select one", "Limit", "Market", "Stop", "Stop limit"}));
//            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
//            panel.add(new LabelCombobox("Term:", 'T', new String[]{"Day", "GTC"}));
            return panel;
        }

        JComponent createButtonPanel() {
            ButtonPanel buttonPanel = new ButtonPanel();
            buttonPanel.addButton(new JButton(new AbstractAction("Trade") {
                public void actionPerformed(ActionEvent e) {
                }
            }));
            buttonPanel.addButton(new JButton(new AbstractAction("Cancel") {
                public void actionPerformed(ActionEvent e) {
                }
            }));
            return buttonPanel;
        }
    }

    static class OptionPanel extends JPanel {
        private HierarchicalTable _table;
        private int _row;

        public OptionPanel(HierarchicalTable table, int row) {
            _table = table;
            _row = row;
            initComponents();
        }

        public OptionPanel() {
            initComponents();
        }

        void initComponents() {
            setLayout(new BorderLayout(4, 4));
            setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialLineBorder(UIDefaultsLookup.getColor("controlShadow"), 1, PartialSide.NORTH), "Option", JideTitledBorder.RIGHT, JideTitledBorder.CENTER),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            add(createDetailPanel(), BorderLayout.CENTER);
            add(createButtonPanel(), BorderLayout.AFTER_LAST_LINE);
            JideSwingUtilities.setOpaqueRecursively(this, false);
            setOpaque(true);
            setBackground(BG1);
        }

        JComponent createDetailPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.X_AXIS));
            panel.add(new LabelCombobox("Order Type:", 'O', new String[]{"Select one", "Buy to open", "Buy to close", "Sell to open", "Sell to close"}));
            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
            panel.add(new LabelTextField("# of Contracts:", 'C', 8));
            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
            panel.add(new LabelTextField("Price:", 'P', 8));
            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
            panel.add(new LabelCombobox("Order Type:", 'R', new String[]{"Select one", "Limit", "Market", "Stop market", "Stop limit"}));
            panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
            panel.add(new LabelCombobox("Term:", 'T', new String[]{"Day", "GTC"}));
            return panel;
        }

        JComponent createButtonPanel() {
            ButtonPanel buttonPanel = new ButtonPanel();
            buttonPanel.addButton(new JButton(new AbstractAction("Trade") {
                public void actionPerformed(ActionEvent e) {
                }
            }));
            buttonPanel.addButton(new JButton(new AbstractAction("Cancel") {
                public void actionPerformed(ActionEvent e) {
                }
            }));
            return buttonPanel;
        }
    }

    static class LabelTextField extends JPanel {
        JTextField _textField;
        JLabel _label;

        public LabelTextField(String label, char mnemonic, int width) {
            _label = new JLabel(label);
            _textField = new JTextField(width);
            _label.setLabelFor(_textField);
            _label.setDisplayedMnemonic(mnemonic);
            setLayout(new BorderLayout(2, 2));
            add(_label, BorderLayout.BEFORE_FIRST_LINE);
            add(_textField, BorderLayout.CENTER);
        }
    }

    static class LabelCombobox extends JPanel {
        ListComboBox _comboxBox;
        JLabel _label;

        public LabelCombobox(String label, char mnemonic, Object[] values) {
            _label = new JLabel(label);
            _comboxBox = new ListComboBox(values);
            _comboxBox.setSelectedItem(values[0]);
            _label.setLabelFor(_comboxBox);
            _label.setDisplayedMnemonic(mnemonic);
            setLayout(new BorderLayout(2, 2));
            add(_label, BorderLayout.BEFORE_FIRST_LINE);
            add(_comboxBox, BorderLayout.CENTER);
        }
    }

    class HierarchicalPanelBorder implements Border {
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(UIDefaultsLookup.getColor("controlShadow"));
            g.drawLine(x, y, x + width - 1, y);
            g.setColor(UIDefaultsLookup.getColor("controlShadow"));
            g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
            g.setColor(UIDefaultsLookup.getColor("control"));
            g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
            g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }

        public boolean isBorderOpaque() {
            return true;
        }

    }
}
