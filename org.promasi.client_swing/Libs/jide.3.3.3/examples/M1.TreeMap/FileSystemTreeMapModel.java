/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.colormap.ColorMap;
import com.jidesoft.colormap.MutableColorMap;
import com.jidesoft.colormap.SimpleColorMap;
import com.jidesoft.palette.PaletteFactory;
import com.jidesoft.treemap.AbstractTreeMapModel;
import com.jidesoft.treemap.DefaultTreeMapField;
import com.jidesoft.treemap.TreeMapField;
import com.jidesoft.treemap.TreeMapWorker;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class FileSystemTreeMapModel extends AbstractTreeMapModel<FileTreeMapNode> {
    static final protected String[] COLUMN_NAMES = {"Name", "Size", "Extension", "Type", "Modified"};
    private int _maximumDepth = 2;
    private final Map<TreeMapField, String> _patterns = new HashMap<TreeMapField, String>();

    FileSystemTreeMapModel() {
    }

    public MutableColorMap createDefaultColorMap(TreeMapField field) {
        final SimpleColorMap colorMap = new SimpleColorMap(null, true, PaletteFactory.getInstance().createDefaultQualititativePalette());
        colorMap.setAssignments(ColorMap.Assignments.DYNAMIC);
        return colorMap;
    }

    public Double getNumericMin(TreeMapField columnIndex) {
        return null;
    }

    public Double getNumericMax(TreeMapField columnIndex) {
        return null;
    }

    public void setNumericMax(TreeMapField columnIndex, double max) {
    }

    public void setNumericMin(TreeMapField columnIndex, double min) {
    }

    protected FileTreeMapNode createRootNode(TreeMapField<FileTreeMapNode> groupByField) {
        File[] roots = FileSystemView.getFileSystemView().getRoots();
        return new FileTreeMapNode(this, roots[0], groupByField);
    }

    protected boolean doGroupBy(FileTreeMapNode parent, TreeMapWorker worker) {
        return false;
    }

    public String getLabelName(FileTreeMapNode node) {
        return node.toString();
    }

    public Color getColor(FileTreeMapNode node) {
        return node.getColor();
    }

    public void setColor(FileTreeMapNode node, Color color) {
        node.setColor(color);
    }

    public FileTreeMapNode getParent(FileTreeMapNode node) {
        return (FileTreeMapNode) node.getParent();
    }

    public boolean isRoot(FileTreeMapNode node) {
        return node.isRoot();
    }

    public Iterable<FileTreeMapNode> getChildren(FileTreeMapNode parent) {
        return parent.getChildren();
    }

    public int getChildCount(FileTreeMapNode node) {
        return node.getChildCount();
    }

    public BufferedImage getCushionImage(FileTreeMapNode node) {
        return node.getCushionImage();
    }

    public Color getCushionColor(FileTreeMapNode node) {
        return node.getCushionColor();
    }

    public TreeMapField getTreeMapField(int columnIndex) {
        return new DefaultTreeMapField(this, columnIndex);
    }

    public TreeMapField getTreeMapField(String columnName) {
        return null;
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
        case 0:
            return String.class;
        case 1:
            return Long.class;
        case 2:
            return String.class;
        case 3:
            return String.class;
        case 4:
            return Date.class;
        default:
            return null;
    }
    }

    public Object getValueAt(FileTreeMapNode node, int columnIndex) {
        return node.getValueAt(columnIndex);
    }

    public boolean isEveryValueUnique(TreeMapField<FileTreeMapNode> fileTreeMapNodeTreeMapField) {
        return true;
    }

    public int getMaximumDepth() {
        return _maximumDepth;
    }

    public void setMaximumDepth(int maximumDepth) {
        if(this._maximumDepth != maximumDepth) {
            this._maximumDepth = maximumDepth;

            scheduleUpdateLayout();
        }
    }


}
