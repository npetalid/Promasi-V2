import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.tooltip.ExpandedTip;
import com.jidesoft.tooltip.ExpandedTipUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;

/**
 * Demoed Component: {@link com.jidesoft.tooltip.ExpandedTip} <br> Required jar files: jide-common.jar,
 * jide-components.jar
 */
public class ExpandedTipDemo extends AbstractDemo {
    private static final long serialVersionUID = 6052626060254128614L;

    public ExpandedTipDemo() {
    }

    public String getName() {
        return "ExpandedTip Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMPONENTS;
    }

    @Override
    public String getDescription() {
        return "ExpandedTip is a collection of several classes that enable expanded tip feature on JList and JTree. " +
                "You can see this feature if you move your mouse over a row or a node that is wider than the available viewport. \n" +
                "\nThe implementation of this feature was copied from IntelliJ IDEA Community Edition (Apache 2.0 license) " +
                "but was modified extensively and enhanced to be more easily used as a component. " +
                "The original implementation uses cell renderer to paint on the tooltip. We changed is to use the component's original " +
                "content to paint it so if you customized the paining of the component not using the cell renderer approach, our implementation will still work.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.tooltip.ExpandedTip\n" +
                "com.jidesoft.tooltip.TreeExpandedTip\n" +
                "com.jidesoft.tooltip.ListExpandedTip";
    }

    public Component getDemoPanel() {
        File[] roots = FileSystemView.getFileSystemView().getRoots();
        JTree tree = new JTree(new FileTreeModel(roots[0]));
        tree.setCellRenderer(new FileTreeCellRenderer());
        tree.setVisibleRowCount(20);
        tree.expandRow(1);

        ExpandedTipUtils.install(tree);

        String[] names = DemoData.getCountryNames();
        JList list = new JList(names);
        list.setVisibleRowCount(20);

        ExpandedTipUtils.install(list);

        TableModel songTableModel = DemoData.createQuoteTableModel();
        SortableTable table = new SortableTable(songTableModel);
        table.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);

        ExpandedTipUtils.install(table);
        ExpandedTipUtils.install(table.getTableHeader());

        final JPanel panel = new ScrollablePanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(IconsFactory.getImageIcon(ExpandedTipDemo.class, "icons/tulips.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        new ExpandedTip<JPanel>(panel) {
            @Override
            protected Rectangle getRowBounds(int index) {
                return new Rectangle(0, 0, panel.getWidth(), panel.getHeight());
            }

            @Override
            protected int rowAtPoint(Point point) {
                return 0;
            }
        };
        panel.setPreferredSize(new Dimension(500, 500));

        JPanel treePanel = createTitledPanel(new JLabel("ExpandedTip JTree:"), 'T', new JScrollPane(tree));
        JPanel listPanel = createTitledPanel(new JLabel("ExpandedTip JList:"), 'L', new JScrollPane(list));
        JPanel tablePanel = createTitledPanel(new JLabel("ExpandedTip JTable:"), 'L', new JScrollPane(table));
        JPanel emptyPanel = createTitledPanel(new JLabel("ExpandedTip for the whole panel:"), 'L', new JScrollPane(panel));

        // add to the parent panel
        JideSplitPane pane = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        pane.add(treePanel);
        pane.add(listPanel);
        pane.add(tablePanel);
        pane.add(emptyPanel);

        // make the pane narrow to show the ExpandedTip.
        pane.setPreferredSize(new Dimension(600, 600));
        return pane;
    }

    @Override
    public String getDemoFolder() {
        return "C7.Tip";
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new ExpandedTipDemo());
            }
        });
    }

    static class ScrollablePanel extends JPanel implements Scrollable {

        public ScrollablePanel() {
        }

        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 20;
        }

        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 20;
        }

        public boolean getScrollableTracksViewportWidth() {
            return false;
        }

        public boolean getScrollableTracksViewportHeight() {
            return true;
        }
    }

    private static JPanel createTitledPanel(JLabel label, char mnemonic, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        label.setDisplayedMnemonic(mnemonic);
        if (component instanceof JScrollPane) {
            label.setLabelFor(((JScrollPane) component).getViewport().getView());
        }
        else {
            label.setLabelFor(component);
        }
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}
