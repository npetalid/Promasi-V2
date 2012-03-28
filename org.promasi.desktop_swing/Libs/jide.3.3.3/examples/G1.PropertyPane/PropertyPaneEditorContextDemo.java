/*
 * @(#)${NAME}
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.action.CommandBar;
import com.jidesoft.combobox.ListExComboBox;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.*;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Demoed Component: {@link com.jidesoft.grid.PropertyPane}, {@link com.jidesoft.grid.PropertyTable} <br> This demo
 * shows you how to use EditorContext to pass in additional data so that cell editor can use it. <br> Required jar
 * files: jide-common.jar, jide-grids.jar <br> Required L&F: Jide L&F extension required
 */
public class PropertyPaneEditorContextDemo extends AbstractDemo {

    private static final long serialVersionUID = 3020280802050428404L;

    public PropertyPaneEditorContextDemo() {
        super();
    }

    public String getName() {
        return "PropertyPaneEditorContext Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    public Component getDemoPanel() {
        PropertyTable _table = createTable();

        return new PropertyPane(_table) {
            @Override
            protected JComponent createToolBarComponent() {
                CommandBar toolBar = new CommandBar();
                toolBar.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
                toolBar.setFloatable(false);
                toolBar.setStretch(true);
                toolBar.setPaintBackground(false);
                toolBar.setChevronAlwaysVisible(false);
                return toolBar;
            }
        };
    }

    public String getDemoFolder() {
        return "G1.PropertyPane";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new PropertyPaneEditorContextDemo());
            }
        });
    }

    // create property table
    private static PropertyTable createTable() {
        EditorContext genericEditorContext = new EditorContext("Generic");
        CellEditorManager.registerEditor(String.class,
                new CellEditorFactory() {
                    public CellEditor create() {
                        return new FormattedTextFieldCellEditor(String.class);
                    }
                }, genericEditorContext);

        ArrayList<Property> list = new ArrayList<Property>();

        SampleProperty property;

        property = new SampleProperty("two choices", "This row has two possible values", String.class, "Choices");
        genericEditorContext = new EditorContext("Generic", new String[]{"1", "2"});
        property.setEditorContext(genericEditorContext);
        list.add(property);

        property = new SampleProperty("three choices", "This row has three possible values", String.class, "Choices");
        genericEditorContext = new EditorContext("Generic", new String[]{"1", "2", "3"});
        property.setEditorContext(genericEditorContext);
        list.add(property);

        property = new SampleProperty("four choices", "This row has four possible values", String.class, "Choices");
        genericEditorContext = new EditorContext("Generic", new String[]{"1", "2", "3", "4"});
        property.setEditorContext(genericEditorContext);
        list.add(property);

        PropertyTableModel model = new PropertyTableModel<Property>(list);
        PropertyTable table = new PropertyTable(model);
        table.expandFirstLevel();
        return table;
    }

    static HashMap<String, Object> map = new HashMap<String, Object>();

    static {
        map.put("two choices", "2");
        map.put("three choices", "3");
        map.put("four choices", "4");
    }

    static class SampleProperty extends Property {
        private static final long serialVersionUID = 6104325936140463446L;

        public SampleProperty(String name, String description, Class type, String category, ConverterContext context) {
            super(name, description, type, category, context);
        }

        public SampleProperty(String name, String description, Class type, String category) {
            super(name, description, type, category);
        }

        public SampleProperty(String name, String description, Class type) {
            super(name, description, type);
        }

        public SampleProperty(String name, String description) {
            super(name, description);
        }

        public SampleProperty(String name) {
            super(name);
        }

        @Override
        public void setValue(Object value) {
            map.put(getFullName(), value);
        }

        @Override
        public Object getValue() {
            return map.get(getFullName());
        }

        @Override
        public boolean hasValue() {
            return map.get(getFullName()) != null;
        }

        static GenericCellEditor _cellEditor = new GenericCellEditor();

        @Override
        public CellEditor getCellEditor(int column) {
            if (column == 1) {
                return _cellEditor;
            }
            return super.getCellEditor(column);
        }
    }

    static class GenericCellEditor extends ListComboBoxCellEditor {
        private static final long serialVersionUID = -5327421827562327015L;

        public GenericCellEditor() {
            super(new Object[0]);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            Component component = super.getTableCellEditorComponent(table, value, isSelected, row, column);
            if (component instanceof ListExComboBox && table instanceof PropertyTable && table.getModel() instanceof PropertyTableModel) {
                PropertyTableModel model = (PropertyTableModel) table.getModel();
                Property property = model.getPropertyAt(row);
                EditorContext context = property.getEditorContext();
                if (context != null && context.getUserObject() instanceof String[]) {
                    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel((String[]) context.getUserObject());
                    comboBoxModel.setSelectedItem(value);
                    ((ListExComboBox) component).setModel(comboBoxModel);
                    ((ListExComboBox) component).setPopupVolatile(true);
                }
            }
            return component;
        }
    }
}
