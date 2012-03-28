/*
 * @(#)ButtonsIconsFactory.java
 *
 * Copyright 2002-2003 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.icons.IconsFactory;

import javax.swing.*;

/**
 * A helper class to contain icons for demo of JIDE products. Those icons are copyrighted by JIDE Software, Inc.
 */
public class ButtonsIconsFactory {

    public static class Buttons {
        public static final String COPY = "icons/Edit-Copy.png";
        public static final String CUT = "icons/Edit-Cut.png";
        public static final String PASTE = "icons/Edit-Paste.png";
        public static final String DELETE = "icons/Edit-Delete.png";
        public static final String REFRESH = "icons/Edit-Refresh.png";
        public static final String HISTORY = "icons/Edit-History.png";
        public static final String REDO = "icons/Edit-Redo.png";
        public static final String UNDO = "icons/Edit-Undo.png";
    }

    public static ImageIcon getImageIcon(String name) {
        if (name != null)
            return IconsFactory.getImageIcon(ButtonsIconsFactory.class, name);
        else
            return null;
    }

    public static void main(String[] argv) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                IconsFactory.generateHTML(ButtonsIconsFactory.class);
            }
        });
    }


}
