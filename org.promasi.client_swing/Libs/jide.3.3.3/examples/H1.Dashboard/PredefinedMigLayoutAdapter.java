/*
 * @(#)PredefinedMigLayoutAdapter.java 12/20/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.dashboard.PredefinedLayoutAdapter;
import com.jidesoft.utils.SystemInfo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class PredefinedMigLayoutAdapter implements PredefinedLayoutAdapter {
    public PredefinedMigLayoutAdapter() {
        super();
    }

    public String layoutToString(LayoutManager layout) {
        if (layout instanceof MigLayout) {
            return "MigLayout:" + ((MigLayout) layout).getLayoutConstraints() + "\t" + ((MigLayout) layout).getColumnConstraints() + "\t" + ((MigLayout) layout).getRowConstraints();
        }
        return "";
    }

    public LayoutManager stringToLayout(String string) {
        if (string != null) {
            if (string.startsWith("MigLayout:")) {
                MigLayout layout = new MigLayout();
                String allConstraints = string.substring("MigLayout:".length());
                String[] constraints = allConstraints.split("\t");
                if (constraints.length >= 1) {
                    layout.setLayoutConstraints(constraints[0]);
                }
                if (constraints.length >= 2) {
                    layout.setColumnConstraints(constraints[1]);
                }
                if (constraints.length >= 3) {
                    layout.setRowConstraints(constraints[2]);
                }
                return layout;
            }
        }
        return new BorderLayout(3, 3);
    }

    public String constraintsToString(LayoutManager layout, Object constraints) {
        if (constraints != null) {
            if (layout instanceof MigLayout) {
                return constraints.toString();
            }
        }
        return "null";
    }

    public Object stringToConstraints(LayoutManager layout, String string) {
        if (string != null && !string.equals("null")) {
            if (layout instanceof MigLayout) {
                return string;
            }
        }
        return null;
    }

    public boolean isComponentResizable(LayoutManager layout, Component comp, int orientation) {
        return layout instanceof MigLayout;
    }

    public void resizeComponent(LayoutManager layout, Component compToResize, int orientation, Dimension originalSize, int offset) {
        if (SystemInfo.isJdk15Above() && layout instanceof MigLayout) {
            if (orientation == SwingConstants.HORIZONTAL) {
                compToResize.setPreferredSize(new Dimension(originalSize.width + offset, originalSize.height));
            }
            else {
                compToResize.setPreferredSize(new Dimension(originalSize.width, originalSize.height + offset));
            }
            compToResize.invalidate();
            compToResize.repaint();
            Container parent = compToResize.getParent();
            if (parent != null) {
                parent.validate();
                parent.repaint();
                parent.doLayout();
                parent = parent.getParent();
                if (parent != null) {
                    parent.validate();
                    parent.repaint();
                    parent.doLayout();
                }
            }
        }
    }
}
