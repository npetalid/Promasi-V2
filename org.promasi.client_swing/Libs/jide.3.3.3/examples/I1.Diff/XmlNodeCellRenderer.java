/*
 * @(#)XmlTreeCellRenderer.java 7/29/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.ContextSensitiveCellRenderer;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.icons.IconsFactory;

import javax.swing.*;
import java.awt.*;

public class XmlNodeCellRenderer extends ContextSensitiveCellRenderer {

    public static final EditorContext CONTEXT = new EditorContext("XmlNode");

    private static Icon ICON_OPEN = IconsFactory.getImageIcon(XmlNodeCellRenderer.class, "icons/folder_open.png");
    private static Icon ICON_CLOSED = IconsFactory.getImageIcon(XmlNodeCellRenderer.class, "icons/folder_closed.png");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (rendererComponent instanceof JLabel) {
            if (value instanceof XmlNode) {
                if (((XmlNode) value).hasChildren()) {
                    if (((XmlNode) value).isExpanded()) {
                        ((JLabel) rendererComponent).setIcon(ICON_OPEN);
                    }
                    else {
                        ((JLabel) rendererComponent).setIcon(ICON_CLOSED);
                    }
                }
                else {
                    ((JLabel) rendererComponent).setIcon(null);
                }
            }
        }
        return rendererComponent;
    }
}
