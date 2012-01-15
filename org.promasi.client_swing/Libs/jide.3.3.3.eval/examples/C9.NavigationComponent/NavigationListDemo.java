/*
 * @(#)NavigationListDemo.java 11/4/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.navigation.NavigationList;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link com.jidesoft.navigation.NavigationList} <br> Required jar files: jide-common.jar,
 * jide-components.jar <br> Required L&F: Jide L&F extension required
 */
public class NavigationListDemo extends AbstractDemo {
    private static final long serialVersionUID = 4011969900407399964L;
    protected NavigationList _list;

    public NavigationListDemo() {
    }

    public String getName() {
        return "NavigationList Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMPONENTS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    private JComponent createList() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        _list = new NavigationList(DemoData.getFontNames());
        _list.setVisibleRowCount(30);

        JScrollPane scrollPane = new JScrollPane(_list);
        panel.add(scrollPane);
        return panel;
    }

    public Component getDemoPanel() {
        return createList();
    }

    @Override
    public Component getOptionsPanel() {
        JCheckBox wideSelection = new JCheckBox("Wide Selection");
        wideSelection.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _list.setWideSelection(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        wideSelection.setSelected(_list.isWideSelection());

        JCheckBox expandedTip = new JCheckBox("Expanded Tip");
        expandedTip.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _list.setExpandedTip(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        expandedTip.setSelected(_list.isExpandedTip());

        JPanel switchPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        switchPanel.add(wideSelection);
        switchPanel.add(expandedTip);

        return switchPanel;
    }

    @Override
    public String getDescription() {
        return "NavigationList is a special style JList that is designed for the navigation purpose.\n" +
                "Demoed classes:\n" +
                "com.jidesoft.navigation.NavigationList";
    }

    @Override
    public String getDemoFolder() {
        return "C9.NavigationComponent";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new NavigationListDemo());
            }
        });
    }
}

