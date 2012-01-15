/*
 * @(#)NavigationTableDemo.java 11/5/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.NavigationSortableTable;
import com.jidesoft.grid.TableUtils;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.tooltip.ExpandedTipUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.navigation.NavigationTable} <br> Required jar files: jide-common.jar,
 * jide-components.jar <br> Required L&F: Jide L&F extension required
 */
public class NavigationTableDemo extends AbstractDemo {

    private static final long serialVersionUID = -5639277169280250990L;
    private NavigationSortableTable _table;

    public NavigationTableDemo() {
    }

    public String getName() {
        return "NavigationTable Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMPONENTS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    private JComponent createTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
//  you can also use NavigationTable which doesn't depend on JIDE Grids
//  JTable table = new NavigationTable(DemoData.createSongTableModel());
        _table = new NavigationSortableTable(DemoData.createSongTableModel());
        _table.setColumnAutoResizable(true);
        _table.setRowHeight(20);
        TableUtils.autoResizeAllColumns(_table, new int[]{150, 150, 60, 60, 60, 60}, null, false, true);

        JScrollPane scrollPane = new JScrollPane(_table);
        panel.add(scrollPane);

        return panel;
    }

    public Component getDemoPanel() {
        return createTable();
    }

    @Override
    public Component getOptionsPanel() {
        JCheckBox expandedTip = new JCheckBox("Expanded Tip");
        expandedTip.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ExpandedTipUtils.install(_table);
                ExpandedTipUtils.install(_table.getTableHeader());
            }
        });
        expandedTip.setSelected(false);

        JPanel switchPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        switchPanel.add(expandedTip);

        return switchPanel;
    }

    @Override
    public String getDescription() {
        return "NavigationTable is a special style JTable that is designed for the navigation purpose.\n" +
                "Demoed classes:\n" +
                "com.jidesoft.navigation.NavigationTable\n" +
                "com.jidesoft.grid.NavigationSortableTable";
    }

    @Override
    public String getDemoFolder() {
        return "C9.NavigationComponent";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new NavigationTableDemo());
            }
        });
    }
}

