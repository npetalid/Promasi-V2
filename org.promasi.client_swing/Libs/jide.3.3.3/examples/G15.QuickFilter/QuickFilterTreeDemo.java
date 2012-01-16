/*
 * @(#)FilterableTreeDemo.java 7/16/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.icons.IconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.SearchableUtils;
import com.jidesoft.tree.QuickTreeFilterField;
import com.jidesoft.tree.TreeUtils;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Demoed Component: {@link com.jidesoft.tree.QuickTreeFilterField} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: any L&F
 */
public class QuickFilterTreeDemo extends AbstractDemo {
    private static final long serialVersionUID = -6164985316458114634L;

    public QuickFilterTreeDemo() {
    }

    public String getName() {
        return "QuickFilter Demo (Tree)";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "This is a demo of QuickTreeFilterField and FilterableTreeModel. \n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.tree.FilterableTreeModel\n" +
                "com.jidesoft.tree.QuickTreeFilterField";
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));

        JPanel quickSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        quickSearchPanel.setBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "QuickTreeFilterField", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP));
        final TreeModel treeModel = DemoData.createSongTreeModel();
        final QuickTreeFilterField field = new QuickTreeFilterField(treeModel);
        field.setHintText("Type here to filter songs");
        field.setHideEmptyParentNode(true);
        quickSearchPanel.add(field);

        JPanel treePanel = new JPanel(new BorderLayout(2, 2));
        treePanel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Filtered Albums", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        final JLabel label = new JLabel(getFilterStatus(field.getDisplayTreeModel(), treeModel));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        final JTree tree = new JTree(field.getDisplayTreeModel()) {
            @Override
            public TreePath getNextMatch(String prefix, int startingRow, Position.Bias bias) {
                return null;
            }

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(400, 400);
            }
        };
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setLeafIcon(IconsFactory.getImageIcon(QuickFilterTreeDemo.class, "/icons/song.png"));
        renderer.setClosedIcon(IconsFactory.getImageIcon(QuickFilterTreeDemo.class, "/icons/album.png"));
        renderer.setOpenIcon(IconsFactory.getImageIcon(QuickFilterTreeDemo.class, "/icons/album.png"));

        field.setTree(tree);
        SearchableUtils.installSearchable(tree);
        TreeUtils.expandAll(tree, true);
        tree.registerKeyboardAction(new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        TreePath[] selections = tree.getSelectionPaths();
                        if (selections != null) {
                            for (TreePath selection : selections) {
                                ((DefaultTreeModel) field.getTreeModel()).removeNodeFromParent((DefaultMutableTreeNode) selection.getLastPathComponent());
                            }
                        }
                    }
                }, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_FOCUSED);
        tree.registerKeyboardAction(new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {

                        TreePath[] selections = tree.getSelectionPaths();
                        if (selections != null) {
                            for (TreePath selection : selections) {
                                DefaultMutableTreeNode node = (DefaultMutableTreeNode) selection.getLastPathComponent();
                                DefaultMutableTreeNode newNode = (DefaultMutableTreeNode) node.clone();
                                DefaultTreeModel defaultTreeModel = (DefaultTreeModel) field.getTreeModel();
                                defaultTreeModel.insertNodeInto(newNode, (DefaultMutableTreeNode) defaultTreeModel.getChild(defaultTreeModel.getRoot(), 0), 0);
                            }
                        }
                    }
                }, KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), JComponent.WHEN_FOCUSED);

        tree.getModel().addTreeModelListener(new TreeModelListener() {
            public void treeNodesChanged(TreeModelEvent e) {
                updateLabel(e);
            }

            public void treeNodesInserted(TreeModelEvent e) {
                updateLabel(e);
            }

            public void treeNodesRemoved(TreeModelEvent e) {
                updateLabel(e);
            }

            public void treeStructureChanged(TreeModelEvent e) {
                updateLabel(e);
            }

            protected void updateLabel(TreeModelEvent e) {
                if (e.getSource() instanceof TreeModel) {
                    final TreeModel model = (TreeModel) e.getSource();
                    label.setText(getFilterStatus(model, treeModel));
                }
            }
        });

        treePanel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        treePanel.add(new JScrollPane(tree));

        panel.add(treePanel);
        panel.add(quickSearchPanel, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(new JButton(new AbstractAction("Remove") {
            public void actionPerformed(ActionEvent e) {
                DefaultTreeModel defaultTreeModel = ((DefaultTreeModel) field.getTreeModel());
                if (tree.getSelectionPath() != null) {
                    TreePath treePath = tree.getSelectionPath();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
//                  TreeNode parent = node.getParent();
                    defaultTreeModel.removeNodeFromParent(node);
//                  defaultTreeModel.nodeStructureChanged(parent);
                }
            }
        }), BorderLayout.PAGE_END);
//        final JTextField textField = new JTextField();
//        textField.addActionListener(new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                String text = textField.getText();
//                if (text != null && text.length() != 0) {
//                    DefaultTreeModel defaultTreeModel = ((DefaultTreeModel) field.getTreeModel());
//                    if (tree.getSelectionPath() != null) {
//                        TreePath treePath = tree.getSelectionPath();
//                        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) treePath.getLastPathComponent()).getParent();
//                        int index = defaultTreeModel.getIndexOfChild(parent, treePath.getLastPathComponent());
//                        defaultTreeModel.insertNodeInto(new DefaultMutableTreeNode(text), parent, index + 1);
//                    }
//                    else {
//                        defaultTreeModel.insertNodeInto(new DefaultMutableTreeNode(text), (DefaultMutableTreeNode) defaultTreeModel.getRoot(), 0);
//                    }
//                    textField.setText("");
//                }
//            }
//        });
//        panel.add(textField, BorderLayout.AFTER_LAST_LINE);
        return panel;
    }

    private String getFilterStatus(TreeModel displayModel, TreeModel originalModel) {
        int count = displayModel.getChildCount(displayModel.getRoot());
        return count + " out of " + originalModel.getChildCount(originalModel.getRoot()) + " albums";
    }

    @Override
    public String getDemoFolder() {
        return "G15.QuickFilter";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new QuickFilterTreeDemo());
            }
        });

    }
}
