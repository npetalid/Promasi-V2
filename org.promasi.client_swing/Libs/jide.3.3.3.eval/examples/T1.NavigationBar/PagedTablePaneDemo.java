/*
 * @(#)PagedGroupTableDemo.java 3/18/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.*;
import com.jidesoft.grid.*;
import com.jidesoft.grouper.date.DateMonthGrouper;
import com.jidesoft.grouper.date.DateQuarterGrouper;
import com.jidesoft.grouper.date.DateWeekOfYearGrouper;
import com.jidesoft.grouper.date.DateYearGrouper;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.paging.PageNavigationBar;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSplitPane;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Demoed Component: {@link com.jidesoft.grid.JideTable} <br> Required jar files: jide-common.jar, jide-grids.jar <br>
 * Required L&F: any L&F
 */
public class PagedTablePaneDemo extends AbstractDemo {
    private static final long serialVersionUID = -3851786563040971574L;
    public GroupTable _table;
    public JLabel _message;
    protected TableModel _tableModel;
    private DefaultGroupTableModel _groupTableModel;

    public PagedTablePaneDemo() {
        ObjectConverterManager.initDefaultConverter();
        // instead of using the default toString method provided by DefaultGroupRow, we add our own
        // converter so that we can display the number of items.
        ObjectConverterManager.registerConverter(DefaultGroupRow.class, new ObjectConverter() {
            public String toString(Object object, ConverterContext context) {
                if (object instanceof DefaultGroupRow) {
                    DefaultGroupRow row = (DefaultGroupRow) object;
                    StringBuffer buf = new StringBuffer(row.toString());
                    int allVisibleChildrenCount = TreeTableUtils.getDescendantCount(_groupTableModel, row, true, true);
                    buf.append(" (").append(allVisibleChildrenCount).append(" items)");
                    return buf.toString();
                }
                return null;
            }

            public boolean supportToString(Object object, ConverterContext context) {
                return true;
            }

            public Object fromString(String string, ConverterContext context) {
                return null;
            }

            public boolean supportFromString(String string, ConverterContext context) {
                return false;
            }
        });
    }

    public String getName() {
        return "PagedTablePane with PageNavigationBar Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_DATAGRIDS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    protected static final Color BACKGROUND1 = new Color(159, 155, 217);
    protected static final Color BACKGROUND2 = new Color(197, 194, 232);

    public static final CellStyle style1 = new CellStyle();
    public static final CellStyle style2 = new CellStyle();
    public static final CellStyle styleGroup1 = new CellStyle();
    public static final CellStyle styleGroup2 = new CellStyle();

    static {
        style1.setBackground(BACKGROUND1);
        style2.setBackground(BACKGROUND2);
        style1.setHorizontalAlignment(SwingConstants.CENTER);
        style2.setHorizontalAlignment(SwingConstants.CENTER);
        styleGroup1.setBackground(BACKGROUND1);
        styleGroup2.setBackground(BACKGROUND2);
        styleGroup1.setFontStyle(Font.BOLD);
        styleGroup2.setFontStyle(Font.BOLD);
    }

    public Component getDemoPanel() {
        final JideSplitPane panel = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);

        _tableModel = DemoData.createProductReportsTableModel(true, 0);
        final CalculatedTableModel calculatedTableModel = setupProductDetailsCalculatedTableModel(_tableModel);

        _groupTableModel = new DefaultGroupTableModel(calculatedTableModel);
        _groupTableModel.addGroupColumn(0, DefaultGroupTableModel.SORT_GROUP_COLUMN_ASCENDING);
        _groupTableModel.addGroupColumn(1, DefaultGroupTableModel.SORT_GROUP_COLUMN_DESCENDING);
        _groupTableModel.groupAndRefresh();

        QuickTableFilterField field = new QuickTableFilterField(_groupTableModel);

        SortableTreeTableModel sortableTTM = new SortableTreeTableModel(field.getDisplayTableModel());
        sortableTTM.setSortableOption(0, SortableTreeTableModel.SORTABLE_ROOT_LEVEL);

        PagedTablePane pane = new PagedTablePane(sortableTTM) {
            @Override
            protected JTable createTable(TableModel model) {
                GroupTable table = new GroupTable(model);
                table.setExpandedIcon(IconsFactory.getImageIcon(GroupTableDemo.class, "icons/outlook_collapse.png"));
                table.setCollapsedIcon(IconsFactory.getImageIcon(GroupTableDemo.class, "icons/outlook_expand.png"));
                table.setAutoResizeMode(JideTable.AUTO_RESIZE_FILL);
                table.setOptimized(true);

                // hide the grid lines is good for performance
                table.setShowLeafNodeTreeLines(false);
                table.setShowTreeLines(false);
                return table;
            }
        };

        PageNavigationBar bar = new PagedTablePaneNavigationBar(pane);

        panel.add(bar, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(pane, BorderLayout.CENTER);
        panel.add(field, BorderLayout.AFTER_LINE_ENDS);

        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "T1.NavigationBar";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new PagedTablePaneDemo());
            }
        });
    }

    private CalculatedTableModel setupProductDetailsCalculatedTableModel(TableModel tableModel) {
        CalculatedTableModel calculatedTableModel = new CalculatedTableModel(tableModel);
        calculatedTableModel.addAllColumns();
        SingleColumn year = new SingleColumn(tableModel, "ShippedDate", "Year", new DateYearGrouper());
        year.setConverterContext(YearNameConverter.CONTEXT);
        calculatedTableModel.addColumn(year);
        SingleColumn qtr = new SingleColumn(tableModel, "ShippedDate", "Quarter", new DateQuarterGrouper());
        qtr.setConverterContext(QuarterNameConverter.CONTEXT);
        calculatedTableModel.addColumn(qtr);
        SingleColumn month = new SingleColumn(tableModel, "ShippedDate", "Month", new DateMonthGrouper());
        month.setConverterContext(MonthNameConverter.CONTEXT);
        calculatedTableModel.addColumn(month);
        calculatedTableModel.addColumn(new SingleColumn(tableModel, "ShippedDate", "Week", new DateWeekOfYearGrouper()));

        return calculatedTableModel;
    }
}
