/*
 * @(#)BreadcrumbBarDemo.java 10/29/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.*;
import com.jidesoft.navigation.BreadcrumbBar;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.SimpleScrollPane;
import com.jidesoft.swing.TableSearchable;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Demoed Component: {@link com.jidesoft.grid.TreeTable} <br> Required jar files: jide-common.jar, jide-grids.jar <br>
 * Required L&F: any L&F
 */
public class BreadcrumbBarDemo extends AbstractDemo {
    private static final long serialVersionUID = 6179258778560693306L;
    private TreeTable _table;
    public BreadcrumbBar _breadcrumbBar;


    public BreadcrumbBarDemo() {
    }

    public String getName() {
        return "BreadcrumbBar Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMPONENTS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    @Override
    public String getDescription() {
        return "This is a BreadcrumbBar demo to navigate through the file system.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.navigation.BreadcrumbBar";
    }

    static final TableCellRenderer FILE_RENDERER = new FileRowCellRenderer();
    static final TableCellRenderer FILE_SIZE_RENDERER = new FileSizeCellRenderer();
    static final TableCellRenderer FILE_DATE_RENDERER = new FileDateCellRenderer();

    public Component getDemoPanel() {
        File[] roots = FileSystemView.getFileSystemView().getRoots();
        FileTreeModel treeModel = new FileTreeModel(roots[0]);

        JPanel panel = new JPanel(new BorderLayout(6, 6));
        Object[] path = {treeModel.getRoot(), treeModel.getChild(treeModel.getRoot(), 0)};

        _breadcrumbBar = new BreadcrumbBar(treeModel, new TreePath(path));
        SimpleScrollPane simpleScrollPane = new SimpleScrollPane(_breadcrumbBar);
        simpleScrollPane.getViewport().setBackground(UIDefaultsLookup.getColor("TextField.background"));
        simpleScrollPane.getViewport().setOpaque(true);

        final JTextField textField = new JTextField();

        final CardLayout cardLayout = new CardLayout();
        final JPanel header = new JPanel(cardLayout);
        header.add("BreadcrumbBar", simpleScrollPane);
        header.add("TextField", textField);

        simpleScrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(header, "TextField");
                Object component = _breadcrumbBar.getSelectedPath().getLastPathComponent();
                if (component instanceof File) {
                    textField.setText(component.toString());
                }
                textField.requestFocus();
            }
        });
        textField.registerKeyboardAction(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(header, "BreadcrumbBar");
                    }
                }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED);
        textField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                cardLayout.show(header, "BreadcrumbBar");
            }
        });

        panel.add(header, BorderLayout.BEFORE_FIRST_LINE);

        _table = createTreeTable((File) _breadcrumbBar.getSelectedPath().getLastPathComponent());
        TableUtils.autoResizeAllColumns(_table);
        _table.setColumnAutoResizable(true);
        JScrollPane scrollPane = new JScrollPane(_table);

        _breadcrumbBar.addPropertyChangeListener(BreadcrumbBar.PROPERTY_SELECTED_PATH, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                TreePath treePath = (TreePath) evt.getNewValue();
                String tablePreferenceByName = TableUtils.getTablePreferenceByName(_table);
                _table.setModel(createTreeTableModel((File) treePath.getLastPathComponent()));
                _table.getColumnModel().getColumn(0).setCellRenderer(FILE_RENDERER);
                _table.getColumnModel().getColumn(1).setCellRenderer(FILE_SIZE_RENDERER);
                _table.getColumnModel().getColumn(3).setCellRenderer(FILE_DATE_RENDERER);
                TableUtils.setTablePreferenceByName(_table, tablePreferenceByName);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(700, 500));

        return panel;
    }

    private TreeTableModel createTreeTableModel(File file) {
        File[] files = file.listFiles();
        List<FileRow> rootList = new ArrayList<FileRow>();
        if (files != null) {
            for (File f : files) {
                if (!f.isFile()) {
                    rootList.add(new FileRow(f));
                }
            }
            for (File f : files) {
                if (f.isFile()) {
                    rootList.add(new FileRow(f));
                }
            }
        }
        return new FileSystemTableModel(rootList);
    }

    public TreeTable createTreeTable(File file) {
        final TreeTable table = new NavigationTreeTable(createTreeTableModel(file));
        table.setSortable(true);
// you can also set different sortable option on each column.
//        ((SortableTreeTableModel) table.getModel()).setSortableOption(1, SortableTreeTableModel.SORTABLE_NONE);
//        ((SortableTreeTableModel) table.getModel()).setSortableOption(2, SortableTreeTableModel.SORTABLE_LEAF_LEVEL);

        // configure the TreeTable
        table.setExpandAllAllowed(false);
        table.setRowHeight(20);

        // do not select row when expanding a row.
        table.setSelectRowWhenToggling(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        table.getColumnModel().getColumn(0).setCellRenderer(FILE_RENDERER);
        table.getColumnModel().getColumn(1).setCellRenderer(FILE_SIZE_RENDERER);
        table.getColumnModel().getColumn(3).setCellRenderer(FILE_DATE_RENDERER);

        // add searchable feature
        TableSearchable searchable = new TableSearchable(table) {
            @Override
            protected String convertElementToString(Object item) {
                if (item instanceof FileRow) {
                    return ((FileRow) item).getName();
                }
                return super.convertElementToString(item);
            }
        };
        searchable.setMainIndex(0); // only search for name column

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    Row rowAt = table.getRowAt(row);
                    if (rowAt instanceof FileRow) {
                        File file = ((FileRow) rowAt).getFile();
                        if (file.isDirectory()) {
                            _breadcrumbBar.setSelectedPath(_breadcrumbBar.getSelectedPath().pathByAddingChild(file));
                        }
                    }
                }
            }
        });

        return table;
    }

    @Override
    public Component getOptionsPanel() {
        JCheckBox dropDownAllowed = new JCheckBox("Drop Down Allowed");
        dropDownAllowed.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _breadcrumbBar.setDropDownAllowed(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        dropDownAllowed.setSelected(_breadcrumbBar.isDropDownAllowed());

        JPanel switchPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        switchPanel.add(dropDownAllowed);

        return switchPanel;
    }

    @Override
    public String getDemoFolder() {
        return "V2.BreadcrumbBar";
    }

    @Override
    public String[] getDemoSource() {
        return new String[]{"BreadcrumbBarDemo.java", "FileTreeModel.java"};
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new BreadcrumbBarDemo());
            }
        });

    }
}

