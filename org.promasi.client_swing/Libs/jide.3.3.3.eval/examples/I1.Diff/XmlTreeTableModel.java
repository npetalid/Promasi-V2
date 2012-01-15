/*
 * @(#)XmlTreeTableModel.java 7/8/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */


import java.util.List;

public class XmlTreeTableModel extends com.jidesoft.grid.TreeTableModel {
    public XmlTreeTableModel(List rows) {
        super(rows);
    }

    @Override
    public String getColumnName(int column) {
        return column == 0 ? "Node" : "Value";
    }

    public int getColumnCount() {
        return 2;
    }
}
