/*
 * @(#)RolloverTableDemo.java 1/6/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.*;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * <br> Required jar files: jide-common.jar <br> Required L&F: any L&F
 */
public class HyperlinkCellDemo extends AbstractDemo {

    private static final long serialVersionUID = 8973781413482347794L;

    public HyperlinkCellDemo() {
    }

    public String getName() {
        return "Hyperlink Cell Demo";
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "HyperlinkCellDemo show how to use RolloverTableUtils and TableCellEditorRenderer together to implement hyperlink feature in JTable.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.TableCellEditorRenderer\n" +
                "com.jidesoft.grid.HyperlinkTableCellEditorRenderer\n" +
                "com.jidesoft.grid.RolloverTableUtils";
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(createTable()));
        return panel;
    }

    final ImageIcon removeIcon = IconsFactory.getImageIcon(HyperlinkCellDemo.class, "icons/remove_package.png");
    final ImageIcon removeRolloverIcon = IconsFactory.getImageIcon(HyperlinkCellDemo.class, "icons/remove_package_rollover.png");
    final ImageIcon editIcon = IconsFactory.getImageIcon(HyperlinkCellDemo.class, "icons/package.png");
    final ImageIcon editRolloverIcon = IconsFactory.getImageIcon(HyperlinkCellDemo.class, "icons/package_rollover.png");

    class ButtonsCellEditorRenderer extends AbstractTableCellEditorRenderer implements CellRolloverSupport {
        private static final long serialVersionUID = -1559557761862573989L;

        public Component createTableCellEditorRendererComponent(JTable table, int row, int column) {
            JPanel panel = new JPanel(new GridLayout(1, 2));
            panel.setOpaque(true);
            panel.add(createButton(removeIcon, removeRolloverIcon));
            panel.add(createButton(editIcon, editRolloverIcon));
            return panel;
        }

        public void configureTableCellEditorRendererComponent(final JTable table, Component editorRendererComponent, boolean forRenderer, Object value, boolean isSelected, boolean hasFocus, final int row, int column) {
            if (!forRenderer) {
                JButton removeButton = (JButton) (((JPanel) editorRendererComponent).getComponent(0));
                removeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ((DefaultTableModel) table.getModel()).removeRow(row);
                    }
                });
                JButton editButton = (JButton) (((JPanel) editorRendererComponent).getComponent(1));
                editButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newItemName = JOptionPane.showInputDialog("New Item Name:", table.getModel().getValueAt(row, 0));
                        if (newItemName != null) {
                            table.getModel().setValueAt(newItemName, row, 0);
                        }
                    }
                });
            }
            editorRendererComponent.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            editorRendererComponent.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        }

        public Object getCellEditorValue() {
            return null;
        }

        public boolean isRollover(JTable table, MouseEvent e, int row, int column) {
            return true;
        }
    }

    private JButton createButton(Icon icon, Icon rolloverIcon) {
        JButton button = new JideButton(icon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(9, 9));
        button.setMaximumSize(new Dimension(9, 9));
        button.setMinimumSize(new Dimension(9, 9));
        button.setContentAreaFilled(false);
        button.setRolloverIcon(rolloverIcon);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setRequestFocusEnabled(false);
        return button;
    }

    private JTable createTable() {
        final JTable table = new CellSpanTable(createTableModel());
        HyperlinkTableCellEditorRenderer renderer = new HyperlinkTableCellEditorRenderer();
        renderer.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                JOptionPane.showMessageDialog(table, ((JideButton) source).getText() + " is clicked");
            }
        });
        table.getColumnModel().getColumn(0).setCellRenderer(renderer);
        table.getColumnModel().getColumn(0).setCellEditor(renderer);

        table.getColumnModel().getColumn(1).setCellEditor(new ButtonsCellEditorRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new ButtonsCellEditorRenderer());
        int columnWidth = 28;
        table.getColumnModel().getColumn(1).setPreferredWidth(columnWidth);
        table.getColumnModel().getColumn(1).setMaxWidth(columnWidth);
        table.getColumnModel().getColumn(1).setMinWidth(columnWidth);
        table.setRowHeight(14);
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(0, 0));
        RolloverTableUtils.install(table);
        return table;
    }

    protected TableModel createTableModel() {
        DefaultTableModel defaultTableModel = new DefaultTableModel(0, 2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

            @Override
            public String getColumnName(int column) {
                return column == 0 ? "Item" : "";
            }
        };
        for (int i = 0; i < 10; i++) {
            defaultTableModel.addRow(new Object[]{"Item " + i, ""});
        }
        return defaultTableModel;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new HyperlinkCellDemo());
            }
        });
    }
}
