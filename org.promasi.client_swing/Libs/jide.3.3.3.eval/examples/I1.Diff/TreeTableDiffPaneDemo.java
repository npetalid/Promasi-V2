/*
 * @(#)TreeTableDiffPaneDemo.java 7/8/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.ColorExComboBox;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.diff.TableDiffPane;
import com.jidesoft.filter.Filter;
import com.jidesoft.grid.*;
import com.jidesoft.margin.RowMarginSupport;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideSwingUtilities;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class TreeTableDiffPaneDemo extends AbstractDemo {
    public TableDiffPane _diffPane;
    protected String _lastDirectory = ".";

    public TreeTableDiffPaneDemo() {
    }

    public String getName() {
        return "DiffPane Demo (TreeTable)";
    }

    public String getProduct() {
        return PRODUCT_NAME_DIFF;
    }

    @Override
    public String getDescription() {
        return "This is a demo for TableDiffPane to compare the difference between two text files." +
                "Demoed classes:\n" +
                "TableDiffPane";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ColorExComboBox changedColorExComboBox = new ColorExComboBox();
        changedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.changed"));
        changedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _diffPane.setChangedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Changed: "), changedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));
        panel.add(Box.createVerticalStrut(2));

        ColorExComboBox insertedColorExComboBox = new ColorExComboBox();
        insertedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.inserted"));
        insertedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _diffPane.setInsertedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Inserted: "), insertedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));
        panel.add(Box.createVerticalStrut(2));

        ColorExComboBox deletedColorExComboBox = new ColorExComboBox();
        deletedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.deleted"));
        deletedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _diffPane.setDeletedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Deleted"), deletedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));

        panel.add(Box.createVerticalStrut(6));
        panel.add(JideSwingUtilities.createCenterPanel(new JButton(new AbstractAction("Clear Diff Results") {
            private static final long serialVersionUID = 50201280516163621L;

            public void actionPerformed(ActionEvent e) {
                _diffPane.clearDiff();
            }
        })));

        return panel;
    }

    public Component getDemoPanel() {
        ObjectConverterManager.initDefaultConverter();
        ObjectConverterManager.registerConverter(XmlNode.class, new XmlNodeConverter(), XmlNodeConverter.CONTEXT);
        CellRendererManager.initDefaultRenderer();
        CellRendererManager.registerRenderer(XmlNode.class, new XmlNodeCellRenderer(), XmlNodeCellRenderer.CONTEXT);

        XmlTreeTableModel jxttm1 = null;
        XmlTreeTableModel jxttm2 = null;
        try {
            InputStream inputStream1 = this.getClass().getResourceAsStream("/file1.xml");
            Reader reader1 = new InputStreamReader(inputStream1, "UTF-8");
            InputSource is1 = new InputSource(reader1);

            InputStream inputStream2 = this.getClass().getResourceAsStream("/file2.xml");
            Reader reader2 = new InputStreamReader(inputStream2, "UTF-8");
            InputSource is2 = new InputSource(reader2);

            XmlNode xmlNode1 = new XmlNode(TreeElementProvider.createDocument(is1).getDocumentElement());
            java.util.List<XmlNode> list1 = new ArrayList();
            list1.add(xmlNode1);
            jxttm1 = new XmlTreeTableModel(list1);

            XmlNode xmlNode2 = new XmlNode(TreeElementProvider.createDocument(is2).getDocumentElement());
            java.util.List<XmlNode> list2 = new ArrayList();
            list2.add(xmlNode2);
            jxttm2 = new XmlTreeTableModel(list2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        final IFilterableTableModel filterableTreeTableModel2 = new FilterableTreeTableModel(jxttm2);

        QuickTableFilterField filterField = new QuickTableFilterField(jxttm1) {
            @Override
            protected void removeFilter(IFilterableTableModel filterableTableModel, int columnIndex, Filter filter) {
                super.removeFilter(filterableTableModel, columnIndex, filter);
                filterableTreeTableModel2.removeFilter(columnIndex, filter);
            }

            @Override
            protected void addFilter(IFilterableTableModel filterableTableModel, int columnIndex, Filter filter) {
                super.addFilter(filterableTableModel, columnIndex, filter);
                filterableTreeTableModel2.addFilter(columnIndex, filter);
            }

            @Override
            public void applyFilter(String text) {
                super.applyFilter(text);
                filterableTreeTableModel2.setFiltersApplied(true);
            }

            @Override
            public void applyFilter() {
                super.applyFilter();
                ((FilterableTreeTableModel) filterableTreeTableModel2).setKeepParentNode(((FilterableTreeTableModel) getDisplayTableModel()).isKeepParentNode());
                ((FilterableTreeTableModel) filterableTreeTableModel2).setKeepAllChildren(((FilterableTreeTableModel) getDisplayTableModel()).isKeepAllChildren());
            }
        };
        final IFilterableTableModel filterableTreeTableModel1 = filterField.getDisplayTableModel();

        _diffPane = new TableDiffPane(filterableTreeTableModel1, filterableTreeTableModel2) {
            @Override
            public JComponent createComponent(Object item, int index) {
                return new TreeTable((TableModel) item);
            }

            @Override
            protected RowMarginSupport createRowMarginSupport(JComponent component, JScrollPane scrollPane) {
                return new TreeTableRowMarginSupport((TreeTable) component, scrollPane);
            }

            @Override
            protected void customizePane(JComponent component, int index) {
                super.customizePane(component, index);
                JTable table = (JTable) (index == 0 ? _fromComponent : _toComponent);
                if (table instanceof TreeTable) {
                    TreeTable treeTable = (TreeTable) table;
                    treeTable.expandAll();
                }
            }

            @Override
            protected Object[] createArrayFromTableModel(TableModel tableModel) {
                if (tableModel instanceof TreeTableModel) {
                    return ((TreeTableModel) tableModel).getRows().toArray();
                }
                return super.createArrayFromTableModel(tableModel);
            }

            @Override
            protected void customizePanes(JComponent[] panes) {
                super.customizePanes(panes);
                final TreeTable fromTable = (TreeTable) _fromComponent;
                final TreeTable toTable = (TreeTable) _toComponent;
                TableUtils.autoResizeAllColumns(fromTable, new int[]{100, 100}, new int[]{400, 400}, true, false);
                fromTable.addTreeExpansionListener(new TreeExpansionListener() {
                    public void treeExpanded(TreeExpansionEvent event) {
                        Object row = event.getPath().getLastPathComponent();
                        int fromRow = fromTable.getRowIndex((Row) row);
                        int toRow = getToMatchingRow(fromRow);
                        toTable.expandRow(toRow, true);
                    }

                    public void treeCollapsed(TreeExpansionEvent event) {
                        Object row = event.getPath().getLastPathComponent();
                        int fromRow = fromTable.getRowIndex((Row) row);
                        int toRow = getToMatchingRow(fromRow);
                        toTable.expandRow(toRow, false);
                    }
                });
                toTable.addTreeExpansionListener(new TreeExpansionListener() {
                    public void treeExpanded(TreeExpansionEvent event) {
                        Object row = event.getPath().getLastPathComponent();
                        int toRow = toTable.getRowIndex((Row) row);
                        int fromRow = getFromMatchingRow(toRow);
                        fromTable.expandRow(fromRow, true);
                    }

                    public void treeCollapsed(TreeExpansionEvent event) {
                        Object row = event.getPath().getLastPathComponent();
                        int toRow = toTable.getRowIndex((Row) row);
                        int fromRow = getFromMatchingRow(toRow);
                        fromTable.expandRow(fromRow, false);
                    }
                });
            }
        };
        _diffPane.setPreferredSize(new Dimension(600, 500));

        _diffPane.setFromTitle("Local");
        _diffPane.setToTitle("Remote");

        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.add(_diffPane);
        panel.add(filterField, BorderLayout.BEFORE_FIRST_LINE);

        panel.add(new JButton(new AbstractAction("Compare") {
            private static final long serialVersionUID = -4133857847867200358L;

            public void actionPerformed(ActionEvent e) {
                _diffPane.diff();
            }
        }), BorderLayout.AFTER_LAST_LINE);
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "I1.DiffTextPane";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                AbstractDemo.showAsFrame(new TreeTableDiffPaneDemo());
            }
        });

    }
}