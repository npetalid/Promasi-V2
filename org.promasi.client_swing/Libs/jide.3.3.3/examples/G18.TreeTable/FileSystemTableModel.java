/*
 * @(#)FileSystemTableModel.java 6/29/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.TreeTableModel;

import java.util.Date;
import java.util.List;

public class FileSystemTableModel extends TreeTableModel {
    static final protected String[] COLUMN_NAMES = {"Name", "Size", "Type", "Modified"};
    private static final long serialVersionUID = -8793367287009514567L;

    public FileSystemTableModel(List rows) {
        super(rows);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return FileRow.class;
            case 1:
                return Long.class;
            case 2:
                return String.class;
            case 3:
                return Date.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
