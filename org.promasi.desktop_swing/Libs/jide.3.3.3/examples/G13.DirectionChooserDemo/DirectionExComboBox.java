/*
 * @(#)DirectionExComboBox.java 3/26/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.ExComboBox;
import com.jidesoft.combobox.PopupPanel;
import com.jidesoft.utils.SystemInfo;

import javax.swing.*;
import java.awt.*;

/**
 */
public class DirectionExComboBox extends ExComboBox {
    /**
     * Creates a new <code>DirectionComboBox</code> using DirectionChooserPanel with 40 directions.
     */
    public DirectionExComboBox() {
        super(DROPDOWN, DirectionConverter.CONTEXT);
        setType(int.class);
        setEditable(false);
    }

    @Override
    public PopupPanel createPopupComponent() {
        PopupPanel panel = new DirectionChooserPanel();
        if (!SystemInfo.isMacOSX()) { // don't set background for Mac OS X
            panel.setBackground(new Color(249, 248, 247));
        }
        return panel;
    }

    /**
     * Gets selected direction.
     *
     * @return the selected direction
     */
    public int getSelectedDirection() {
        updateDirectionFromEditorComponent();
        if (getSelectedItem() instanceof Integer) {
            return (Integer) getSelectedItem();
        }
        else {
            return SwingConstants.CENTER;
        }
    }

    protected void updateDirectionFromEditorComponent() {
        Object editorValue = getEditor().getItem();
        if (editorValue instanceof Integer && !editorValue.equals(getSelectedItem())) {
            setSelectedDirection((Integer) editorValue);
        }
    }

    /**
     * Sets selected direction.
     *
     * @param direction
     */
    public void setSelectedDirection(int direction) {
        setSelectedItem(direction);
    }

    @Override
    public void customizeRendererComponent(Component rendererComponent, Object value, int index, boolean selected, boolean cellHasFocus) {
        super.customizeRendererComponent(rendererComponent, value, index, selected, cellHasFocus);
        if (rendererComponent instanceof JLabel) {
            ((JLabel) rendererComponent).setIcon(DirectionChooserPanel.getDirectionIcon((Integer) value));
        }
    }
}
