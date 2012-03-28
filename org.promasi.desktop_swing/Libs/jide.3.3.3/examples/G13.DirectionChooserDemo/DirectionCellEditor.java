/*
 * @(#)DirectionCellEditor.java 4/6/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.ExComboBox;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.ExComboBoxCellEditor;

/**
 * A direction cell editor.
 */
public class DirectionCellEditor extends ExComboBoxCellEditor {

    public static final EditorContext CONTEXT = new EditorContext("Direction");

    /**
     * Creates a DirectionCellEditor.
     */
    public DirectionCellEditor() {
    }

    @Override
    public ExComboBox createExComboBox() {
        return new DirectionExComboBox();
    }
}