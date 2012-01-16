/*
 * @(#)FileRowCellRenderer.java 11/4/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

public class FileTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof File) {
            File file = (File) value;
            JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, getName(file), sel, expanded, leaf, row, false);
            try {
                label.setIcon(getIcon(file));
            }
            catch (Exception e) {
                System.out.println(file.getAbsolutePath());
            }
            label.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));
            return label;
        }
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }

    static FileSystemView _fileSystemView;

    static FileSystemView getFileSystemView() {
        if (_fileSystemView == null) {
            _fileSystemView = FileSystemView.getFileSystemView();
        }
        return _fileSystemView;
    }

    public static String getName(File file) {
        return getFileSystemView().getSystemDisplayName(file);
    }

    public static Icon getIcon(File file) {
        return getFileSystemView().getSystemIcon(file);
    }

}
