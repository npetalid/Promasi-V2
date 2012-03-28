/*
 * Copyright (c) 2011 Macrofocus GmbH. All Rights Reserved.
 */

import com.jidesoft.treemap.AbstractTreeMapNode;
import com.jidesoft.treemap.TreeMapField;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.*;

public class FileTreeMapNode extends AbstractTreeMapNode {
    private final FileSystemTreeMapModel _treeMapModel;

    static FileSystemView _fileSystemView;
    private File _file;
    private List<FileTreeMapNode> _children;

    private static final Long SIZE_NO_AVAILABLE = -1L;

    static HashMap<FileTreeMapNode, Icon> _icons = new HashMap();

    private transient boolean _isFile;
    private transient long _date;
    private transient String _name;
    private transient String _description;
    private transient long _length;
    private transient Icon _icon;

    private TreeMapField<FileTreeMapNode> groupByField;

    public FileTreeMapNode(FileSystemTreeMapModel treeMapModel, File file, TreeMapField<FileTreeMapNode> groupByField) {
        super(treeMapModel);

        this._treeMapModel = treeMapModel;
        this.groupByField = groupByField;

        _file = file;
        _isFile = _file.isFile();
        _date = _file.lastModified();
        _name = getName(_file);
        _description = getTypeDescription(_file);
        _length = _isFile ? _file.length() : -1;
//        _icon = retrieveIcon();
    }

    public boolean isFile() {
        return _isFile;
    }

    static FileSystemView getFileSystemView() {
        if (_fileSystemView == null) {
            _fileSystemView = FileSystemView.getFileSystemView();
        }
        return _fileSystemView;
    }

    public Object getValueAt(int columnIndex) {
        try {
            switch (columnIndex) {
                case 0:
                    return this.toString();
                case 1:
                    if (_isFile) {
                        return _length;
                    }
                    else {
                        return SIZE_NO_AVAILABLE;
                    }
                case 2:
                    return getExtension(_file);
                case 3:
                    return getTypeDescription();
                case 4:
                    return new Date(_date);
            }
        }
        catch (SecurityException se) {
            // ignore
        }
        return null;
    }

    public Class<?> getCellClassAt(int columnIndex) {
        return null;
    }

    public void setChildren(List<FileTreeMapNode> children) {
        _children = children;
        if (_children != null) {
            for (Object row : _children) {
                if (row instanceof FileTreeMapNode) {
                    ((FileTreeMapNode) row).setParent(this);
                }
            }
        }
    }

    public boolean hasChildren() {
        return !_isFile && getChildren() != null && getChildren().size() > 0;
    }

    public List<FileTreeMapNode> getChildren() {
        if(getLevel() - _treeMapModel.getLevel(_treeMapModel.getCurrentRoot()) < _treeMapModel.getMaximumDepth()) {
            if (_children != null) {
                return _children;
            }
            try {
                if (!_isFile) {
                    File[] files = getFileSystemView().getFiles(_file, true);
                    List<FileTreeMapNode> children = new ArrayList();
                    List<FileTreeMapNode> fileChildren = new ArrayList();
                    for (File file : files) {
                        FileTreeMapNode fileRow = createFileRow(file);
                        if (fileRow.isFile()) {
                            fileChildren.add(fileRow);
                        }
                        else {
                            children.add(fileRow);
                        }
                    }
                    children.addAll(fileChildren);
                    setChildren(children);
                }
            }
            catch (SecurityException se) {
                // ignore
            }
            if(_children != null) {
                return _children;
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

    protected FileTreeMapNode createFileRow(File file) {
        return new FileTreeMapNode(_treeMapModel, file, groupByField);
    }

    public File getFile() {
        return _file;
    }

    public String getName() {
        return _name;
    }

    public Icon getIcon() {
        return _icon;
    }

    private Icon retrieveIcon() {
        Icon icon = _icons.get(this);
        if (icon == null) {
            icon = getIcon(getFile());
            _icons.put(this, icon);
            return icon;
        }
        else {
            return icon;
        }
    }

    public String getTypeDescription() {
        return _description;
    }

    public static Icon getIcon(File file) {
        return getFileSystemView().getSystemIcon(file);
    }

    public static String getTypeDescription(File file) {
        String desc = getFileSystemView().getSystemTypeDescription(file);
        if(desc == null){
            if(file.isDirectory()) {
                return "Folder";
            }
            else {
                return "File";
            }
        }
        return desc;
    }

    public static String getExtension(File file) {
        String suffix = null;
        final String s = getName(file);
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            suffix = s.substring(i + 1).toLowerCase();
        }
        return suffix;
    }

    public static String getName(File file) {
        return getFileSystemView().getSystemDisplayName(file);
    }

    public int compareTo(FileTreeMapNode o) {
        FileTreeMapNode fileRow = o;
        return getName().compareToIgnoreCase(fileRow.getName());
    }

    public int getChildCount() {
        return getChildren().size();
    }

    public void add(AbstractTreeMapNode newChild) {
        throw new UnsupportedOperationException();

    }

    public AbstractTreeMapNode getChild(Object name) {
        return null;
    }

    public Object getNodeName() {
        return _file.toString();
    }

    public int getRow() {
        return 0;
    }

    public boolean isLeaf() {
        return !hasChildren();
    }

    public TreeMapField getGroupByField() {
        return null;
    }

    public TreeMapField getChildrenGroupByField() {
        return null;
    }

    public String toString() {
        return _file.getName();
    }
}
