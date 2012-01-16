/*
 * @(#)PageNavigationBarDemo.java 9/10/2008
 *
 * Copyright 2002 - 2008 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.jidesoft.list.ListModelWrapperUtils;
import com.jidesoft.paging.PageNavigationBar;
import com.jidesoft.paging.PageNavigationSupport;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSplitPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class PageNavigationBarDemo extends AbstractDemo {
    private static final long serialVersionUID = -1524944612788502154L;
    private TableModel _tableModel;
    private ListModel _listModel;
    private PageNavigationBar _listPageNavigationBar;
    private PageNavigationBar _tablePageNavigationBar;

    public PageNavigationBarDemo() {
    }

    public String getName() {
        return "PageNavigationBar Demo";
    }

    @Override
    public String getDemoFolder() {
        return "T1.NavigationBar";
    }

    public String getProduct() {
        return PRODUCT_NAME_DATAGRIDS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public Component getDemoPanel() {
        readData();
        JideSplitPane pane = new JideSplitPane();
        pane.addPane(createListPanel());
        pane.addPane(createTablePanel());
        return pane;
    }

    protected Component createListPanel() {
        final JList list = new JList(_listModel != null ? _listModel : new DefaultListModel());
        list.setVisibleRowCount(20);
        final JScrollPane scroller = new JScrollPane(list);
        scroller.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int rowCount = scroller.getViewport().getHeight() / list.getCellBounds(0, 0).height;
                PageNavigationSupport pageNavigationSupport = (PageNavigationSupport) ListModelWrapperUtils.getActualListModel(list.getModel(), PageNavigationSupport.class);
                if (pageNavigationSupport != null) {
                    pageNavigationSupport.setPageSize(rowCount);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Navigation Bar for JList"),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(scroller);
        _listPageNavigationBar = new PageNavigationBar(list);
        panel.add(_listPageNavigationBar, BorderLayout.AFTER_LAST_LINE);
        return panel;
    }

    protected Component createTablePanel() {
        final SortableTable table = new SortableTable(_tableModel != null ? _tableModel : new DefaultTableModel());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        final JScrollPane scroller = new JScrollPane(table);
        scroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                table.setCellContentVisible(!e.getValueIsAdjusting());
            }
        });
        scroller.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int rowCount = scroller.getViewport().getHeight() / table.getRowHeight();
                PageNavigationSupport pageNavigationSupport = (PageNavigationSupport) TableModelWrapperUtils.getActualTableModel(table.getModel(), PageNavigationSupport.class);
                if (pageNavigationSupport != null) {
                    pageNavigationSupport.setPageSize(rowCount);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Navigation Bar for JTable"),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(scroller);
        PageNavigationBar pageNavigationBar = new PageNavigationBar(table);
        pageNavigationBar.setName("tablePageNavigationBar");
        _tablePageNavigationBar = pageNavigationBar;
        panel.add(pageNavigationBar, BorderLayout.AFTER_LAST_LINE);
        return panel;
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        JCheckBox enabled = new JCheckBox("Enabled");
        enabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _listPageNavigationBar.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
                _tablePageNavigationBar.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        enabled.setSelected(true);
        panel.add(enabled);
        return panel;
    }

    protected void readData() {
        _tableModel = DemoData.createSongTableModel();
        _listModel = DemoData.createCountryListModel();
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new PageNavigationBarDemo());
            }
        });
    }
}