/*
 * @(#)NavigationTreeDemo.java 11/4/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.navigation.NavigationTree;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

/**
 * Demoed Component: {@link com.jidesoft.navigation.NavigationTree} <br> Required jar files: jide-common.jar,
 * jide-components.jar <br> Required L&F: Jide L&F extension required
 */
public class NavigationTreeDemo extends AbstractDemo {

    private static final long serialVersionUID = 4562745051558734092L;
    protected NavigationTree _tree;

    public NavigationTreeDemo() {
    }

    public String getName() {
        return "NavigationTree Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMPONENTS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    private JComponent createTree() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        File[] roots = FileSystemView.getFileSystemView().getRoots();
        FileTreeModel treeModel = new FileTreeModel(roots[0]);
        _tree = new NavigationTree(treeModel);
        _tree.setCellRenderer(new FileTreeCellRenderer());
        _tree.setRowHeight(20);
        _tree.setVisibleRowCount(30);

        JScrollPane scrollPane = new JScrollPane(_tree);
        panel.add(scrollPane);
        return panel;
    }

    public Component getDemoPanel() {
        return createTree();
    }

    @Override
    public Component getOptionsPanel() {
        JCheckBox treeLines = new JCheckBox("Show Tree Lines");
        treeLines.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tree.setShowTreeLines(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        treeLines.setSelected(_tree.isShowTreeLines());

        JCheckBox animateTreeIcons = new JCheckBox("Fade Tree Icons When Inactive");
        animateTreeIcons.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tree.setFadeIcon(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        animateTreeIcons.setSelected(_tree.isIconFade());

        JCheckBox wideSelection = new JCheckBox("Wide Selection");
        wideSelection.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tree.setWideSelection(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        wideSelection.setSelected(_tree.isWideSelection());

        JCheckBox expandedTip = new JCheckBox("Expanded Tip");
        expandedTip.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _tree.setExpandedTip(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        expandedTip.setSelected(_tree.isExpandedTip());

        JPanel switchPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        switchPanel.add(animateTreeIcons);
        switchPanel.add(wideSelection);
        if (!LookAndFeelFactory.isLnfInUse(LookAndFeelFactory.AQUA_LNF) && !LookAndFeelFactory.isLnfInUse(LookAndFeelFactory.AQUA_LNF_6)) {
            switchPanel.add(treeLines);
        }
        switchPanel.add(expandedTip);

        return switchPanel;
    }

    @Override
    public String getDescription() {
        return "NavigationTree is a special style JTree that is designed for the navigation purpose.\n" +
                "Demoed classes:\n" +
                "com.jidesoft.navigation.NavigationTree";
    }

    @Override
    public String getDemoFolder() {
        return "C9.NavigationComponent";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new NavigationTreeDemo());
            }
        });
    }
}

