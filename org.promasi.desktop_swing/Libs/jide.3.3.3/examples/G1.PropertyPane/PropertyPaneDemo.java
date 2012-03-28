/*
 * @(#) PropertyPaneDemo.java
 * Last modified date: 2/9/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.action.CommandBar;
import com.jidesoft.combobox.*;
import com.jidesoft.converter.*;
import com.jidesoft.converter.StringConverter;
import com.jidesoft.grid.*;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Demoed Component: {@link PropertyPane}, {@link PropertyTable} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: Jide L&F extension required
 */
public class PropertyPaneDemo extends AbstractDemo {

    protected static final Color BACKGROUND1 = new Color(253, 253, 244);
    protected static final Color BACKGROUND2 = new Color(255, 255, 255);

    private static PropertyTable _table;
    private static PropertyPane _pane;
    private static int _editorStyle = EditorStyleTableModel.EDITOR_STYLE_NORMAL;
    private static final long serialVersionUID = 7477343900692892168L;

    private final static CellStyle CELL_STYLE_REQUIRED = new CellStyle();
    private final static CellStyle CELL_STYLE_EXPERT = new CellStyle();

    static {
        CELL_STYLE_REQUIRED.setBorder(new IconBorder(IconsFactory.getImageIcon(Demo.class, "icons/required.gif")));
        CELL_STYLE_EXPERT.setBorder(new MatteBorder(0, 0, 0, 6, Color.BLUE));
    }

    public PropertyPaneDemo() {
    }

    @Override
    public String getDescription() {
        return "This is a demo of PropertyTable and PropertyPane. PropertyTable is a special two-column table which is ideal for showing name/value pairs. PropertyPane is a wrapper around PropertyTable with toolbar buttons to support typical operations on PropertyTable\n" +
                "\nPropertyPane is backed by PropertyTableModel. You can define multiple levels of nested categories and still be able to show them in PropertyTable.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.grid.PropertyPane\n" +
                "com.jidesoft.grid.PropertyTable\n" +
                "com.jidesoft.grid.PropertyTableModel\n" +
                "com.jidesoft.grid.Property\n" +
                "com.jidesoft.grid.CellEditorManager\n" +
                "com.jidesoft.grid.CellRendererManager\n" +
                "com.jidesoft.converter.ObjectConverterManager";
    }

    @Override
    public String getDemoFolder() {
        return "G1.PropertyPane";
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_UPDATED;
    }

    public static void main(String[] args) {
// use the following code to make cell editor start editing after two clicks
//        CellEditorManager.addCellEditorCustomizer(new CellEditorManager.CellEditorCustomizer(){
//            public void customize(CellEditor cellEditor) {
//                if(cellEditor instanceof AbstractJideCellEditor) {
//                    ((AbstractJideCellEditor) cellEditor).setClickCountToStart(2);
//                }
//                else if(cellEditor instanceof DefaultCellEditor) {
//                    ((DefaultCellEditor) cellEditor).setClickCountToStart(2);
//                }
//            }
//        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new PropertyPaneDemo());
            }
        });

    }

    @Override
    public Component getOptionsPanel() {
        JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
        JCheckBox paintMargin = new JCheckBox("Paint Margin");
        paintMargin.setSelected(true);
        paintMargin.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _table.setPaintMarginBackground(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        JCheckBox paintMarginComponent = new JCheckBox("Paint the Selected Row Indicator");
        paintMarginComponent.setSelected(false);
        paintMarginComponent.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _table.setMarginRenderer(new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            JLabel label = (JLabel) super.getTableCellRendererComponent(table, "", false, false, row, column);
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setOpaque(false);
                            if (!((Property) value).hasChildren() && isSelected) {
                                label.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.Arrow.RIGHT));
                            }
                            else {
                                label.setIcon(null);
                            }
                            return label;
                        }
                    });
                }
                else {
                    _table.setMarginRenderer(null);
                }
            }
        });

        JCheckBox showDescriptionArea = new JCheckBox("Show Description Area");
        showDescriptionArea.setSelected(true);
        showDescriptionArea.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _pane.setShowDescription(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        JCheckBox showToolBar = new JCheckBox("Show Tool Bar");
        showToolBar.setSelected(true);
        showToolBar.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _pane.setShowToolBar(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        final JComboBox editorStyleComboBox = new JComboBox(new String[]{"Normal", "Select Only", "Read Only"});
        editorStyleComboBox.setSelectedIndex(0);
        editorStyleComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    _editorStyle = editorStyleComboBox.getSelectedIndex();
                }
            }
        });

        checkBoxPanel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Editor Style:"), editorStyleComboBox, BorderLayout.BEFORE_LINE_BEGINS));
        checkBoxPanel.add(paintMargin);
        checkBoxPanel.add(paintMarginComponent);
        checkBoxPanel.add(showDescriptionArea);
        checkBoxPanel.add(showToolBar);

        return checkBoxPanel;
    }

    public String getName() {
        return "PropertyPane Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        _table = createTable();
        _pane = new PropertyPane(_table) {
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

        JPanel quickSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        QuickTableFilterField filterField = new QuickTableFilterField(_table.getModel());
        filterField.setHintText("Type here to filter properties");
        filterField.setObjectConverterManagerEnabled(true);
        quickSearchPanel.add(filterField);
        quickSearchPanel.setBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "QuickTableFilterField", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP));

        _table.setModel(filterField.getDisplayTableModel());
        _table.setRowHeight(10, _table.getRowHeight() * 3);
        panel.add(quickSearchPanel, BorderLayout.BEFORE_FIRST_LINE);

        _pane.setBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "PropertyPane", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP));
        panel.add(_pane, BorderLayout.CENTER);
        return panel;
    }

    // create property table

    private PropertyTable createTable() {
        ObjectConverterManager.initDefaultConverter();
        CellEditorManager.initDefaultEditor();

        DecimalFormat format = new DecimalFormat();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        DoubleConverter converter = new DoubleConverter(format);
        ObjectConverterManager.registerConverter(Double.class, converter);

        CellEditorManager.registerEditor(Double.class, new CellEditorFactory() {
            public CellEditor create() {
                DoubleCellEditor editor = new DoubleCellEditor();
                DecimalFormat format = new DecimalFormat();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(10);
                DoubleConverter editorConverter = new DoubleConverter(format);
                editor.setConverter(editorConverter);
                return editor;
            }
        });

        // before use MaskFormatter, you must register converter context and editor context
        ConverterContext ssnConverterContext;
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("###-##-####");
            mask.setValueContainsLiteralCharacters(false);
        }
        catch (ParseException e) {
            // ignore
        }
        ssnConverterContext = new ConverterContext("SSN", mask);
        EditorContext ssnEditorContext = new EditorContext("SSN");
        CellEditorManager.registerEditor(String.class, new CellEditorFactory() {
            public CellEditor create() {
                return new FormattedTextFieldCellEditor(String.class);
            }
        }, ssnEditorContext);
        CellEditorManager.registerEditor(String.class, new CellEditorFactory() {
            public CellEditor create() {
                return new FileNameCellEditor() {
                    private static final long serialVersionUID = -6976383504966480782L;

                    @Override
                    protected FileNameChooserExComboBox createFileNameChooserComboBox() {
                        final FileNameChooserExComboBox comboBox = new FileNameChooserExComboBox() {
                            @Override
                            public PopupPanel createPopupComponent() {
                                FileChooserPanel panel = new FileChooserPanel("" + getSelectedItem()) {
                                    @Override
                                    protected JFileChooser createFileChooser() {
                                        JFileChooser fileChooser = new JFileChooser(getCurrentDirectoryPath());
                                        fileChooser.setFileFilter(new FileFilter() {
                                            @Override
                                            public boolean accept(File f) {
                                                return f.isDirectory() || f.getName().endsWith(".java");
                                            }

                                            @Override
                                            public String getDescription() {
                                                return "Java files (*.java)";
                                            }
                                        });
                                        try {
                                            fileChooser.setSelectedFile(new File(getCurrentDirectoryPath()));
                                        }
                                        catch (Exception e) {
                                            // ignore
                                        }
                                        return fileChooser;
                                    }
                                };
                                panel.setTitle("Choose a file");
                                return panel;
                            }
                        };
                        comboBox.setEditable(true);
                        return comboBox;
                    }
                };
            }
        }, FileNameCellEditor.CONTEXT);

        CellEditorManager.registerEditor(String.class, new CellEditorFactory() {
            public CellEditor create() {
                return new FontNameCellEditor() {
                    private static final long serialVersionUID = 4604545814290369934L;

                    @Override
                    protected ListExComboBox createListComboBox(ComboBoxModel model, Class type) {
                        ListExComboBox listComboBox = super.createListComboBox(model, type);
                        listComboBox.setEditable(false);
                        new ListExComboBoxSearchable(listComboBox);
                        return listComboBox;
                    }
                };
            }
        }, new EditorContext("FontName-Noneditable"));

        final EnumConverter priorityConverter = new EnumConverter("Priority", Integer.class,
                new Object[]{
                        0,
                        1,
                        2,
                        3
                },
                new String[]{
                        "Low",
                        "Normal",
                        "High",
                        "Urgent"},
                1);
        ObjectConverterManager.registerConverter(priorityConverter.getType(), priorityConverter, priorityConverter.getContext());
        CellRendererManager.registerRenderer(priorityConverter.getType(), new ContextSensitiveCellRenderer(), new EditorContext(priorityConverter.getName()));
        CellEditorManager.registerEditor(priorityConverter.getType(), new CellEditorFactory() {
            public CellEditor create() {
                return new EnumCellEditor(priorityConverter);
            }
        }, new EditorContext(priorityConverter.getName()));

        CellEditorManager.registerEditor(String[].class, new CellEditorFactory() {
            public CellEditor create() {
                return new CheckBoxListComboBoxCellEditor();
            }
        }, new EditorContext("ABCDE"));
        CellEditorManager.registerEditor(String.class, new CellEditorFactory() {
            public CellEditor create() {
                return new MultilineTableCellEditor() {
                    private static final long serialVersionUID = -4872173142037207324L;

                    @Override
                    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                        if (table != null) {
                            JideSwingUtilities.installColorsAndFont(_textArea, table.getBackground(), table.getForeground(), table.getFont());
                        }
                        setCellEditorValue(value);
                        if (table != null) {
                            customizeTextArea();
//                                    table.setRowHeight(row, _scrollPane.getPreferredSize().height);
                        }
                        if (getTextArea() != null && getEditorStyle() != EditorStyleTableModel.EDITOR_STYLE_NORMAL) {
                            getTextArea().setEditable(getEditorStyle() == EditorStyleTableModel.EDITOR_STYLE_EDITABLE);
                        }
                        return _scrollPane;
                    }
                };
            }
        }, new EditorContext("Text"));

        ArrayList<SampleProperty> list = new ArrayList<SampleProperty>();

        SampleProperty property;

        property = new SampleProperty("Background", "The row is intended to show how to create a cell to input color in RGB format.", Color.class, "Appearance");
        list.add(property);

        property = new SampleProperty("Foreground", "The row is intended to show how to create a cell to input color in HEX format.", Color.class, "Appearance", ColorConverter.CONTEXT_HEX);
        list.add(property);

        property = new SampleProperty("Opaque", "The row is intended to show how to create a cell to input a boolean value. This one use combobox to choose a boolean type", Boolean.class, "Appearance");
        list.add(property);

        property = new SampleProperty("Bounds", "The row is intended to show how to create a cell to input a Rectangle.", Rectangle.class, "Appearance");
        list.add(property);

        property = new SampleProperty("Dimension", "The row is intended to show how to create a cell to input a Dimension.", Dimension.class, "Appearance");
        list.add(property);

        property = new SampleProperty("Name", "Name of the component", String.class);
        property.setCategory("Appearance");
        list.add(property);

        property = new SampleProperty("Font Name", "The row is intended to show how to create a cell to choose a font name", String.class);
        property.setEditorContext(new EditorContext("FontName"));
        property.setConverterContext(new ConverterContext("FontName"));
        property.setCategory("Appearance");
        list.add(property);

        property = new SampleProperty("Font", "The row is intended to show how to create a cell to choose a font", Font.class);
        property.setCategory("Appearance");
        list.add(property);

        property = new SampleProperty("Default Font Name", "The row is intended to show how to create a cell to choose a font name using a non-editable ListComoboBox", String.class);
        property.setEditorContext(new EditorContext("FontName-Noneditable"));
        property.setConverterContext(new ConverterContext("FontName"));
        property.setCategory("Appearance");
        list.add(property);

        property = new SampleProperty("Text", "Text of the component", String.class);
        property.setEditorContext(new EditorContext("Text"));
        property.setCategory("Appearance");
        list.add(property);

        property = new SampleProperty("Visible", "The row is intended to show how to create a cell to input a boolean value. This one use check box to choose a boolean type", Boolean.class);
        property.setEditorContext(BooleanCheckBoxCellEditor.CONTEXT);
        property.setCategory("Appearance");
        list.add(property);

        property = new SampleProperty("File Name", "The row is intended to show how to create a cell to input a file using FileChooser.", String.class);
        property.setConverterContext(StringConverter.CONTEXT_FILENAME);
        property.setEditorContext(FileNameCellEditor.CONTEXT);
        list.add(property);

        property = new SampleProperty("Folder Name", "The row is intended to show how to create a cell to input a folder using FolderChooser.", String.class);
        property.setEditorContext(FolderNameCellEditor.CONTEXT);
        list.add(property);

        property = new SampleProperty("CreationDate", "The row is intended to show how to create a cell to input Date using DateComboBox.", Calendar.class);
        list.add(property);

        property = new SampleProperty("ExpirationDate", "The row is intended to show how to create a cell to input month/year using MonthComboBox.", Calendar.class);
        property.setConverterContext(MonthConverter.CONTEXT_MONTH);
        property.setEditorContext(MonthCellEditor.CONTEXT);
        list.add(property);

        property = new SampleProperty("DateTime", "The row is intended to show how to create a cell to input date and time using DateComboBox.", Calendar.class);
        property.setConverterContext(DateConverter.DATETIME_CONTEXT);
        property.setEditorContext(DateCellEditor.DATETIME_CONTEXT);
        list.add(property);

        property = new SampleProperty("Not Editable", "The row is intended to show a readonly cell.");
        property.setEditable(false);
        list.add(property);

        property = new SampleProperty("Double", "The row is intended to show how to create a cell to input a double.", Double.class);
        list.add(property);

        property = new SampleProperty("Float", "The row is intended to show how to create a cell to input a float.", Float.class);
        list.add(property);

        property = new SampleProperty("Integer", "The row is intended to show how to create a cell to input an integer using SpinnerCellEditor.", Integer.class);
        property.setEditorContext(SpinnerCellEditor.CONTEXT);
        list.add(property);

        property = new SampleProperty("Slider", "The row is intended to show how to create a cell to input an integer with a slider.", Integer.class);
        property.setEditorContext(SliderCellEditor.CONTEXT);
        list.add(property);

        property = new SampleProperty("Long", "The row is intended to show how to create a cell to input a long.", Long.class);
        list.add(property);

        property = new SampleProperty("Calculator", "The row is intended to show how to create a cell using Calculator.", Double.class);
        property.setEditorContext(CalculatorCellEditor.CONTEXT);
        list.add(property);

        property = new SampleProperty("Long text to test tooltips. If you see the whole text, you are OK.", "The row is intended to show how to tooltip of the cell value is too long.");
        list.add(property);

        property = new SampleProperty("SSN", "This row is intended to show how to create a String cell editor which uses MaskFormatter",
                String.class);
        property.setConverterContext(ssnConverterContext);
        property.setEditorContext(ssnEditorContext);
        list.add(property);

        property = new SampleProperty("IP Address", "This row is intended to show how to create an IP Address cell editor",
                String.class);
        property.setEditorContext(IPAddressCellEditor.CONTEXT);
        list.add(property);

        property = new SampleProperty("Required", "This row shows a required property. The indicator can be customized using CellStyle", String.class);
        property.setRequired(true);
        list.add(property);

        property = new SampleProperty("Preferred", "This row shows a preferred property which is indicated by a bold text", String.class);
        property.setPreferred(true);
        list.add(property);

        property = new SampleProperty("Expert", "This row shows an expert property. The indicator can be customized using CellStyle", String.class);
        property.setExpert(true);
        list.add(property);

        property = new SampleProperty("Hidden", "This row is intended to hide a hidden property", String.class);
        property.setHidden(true);
        list.add(property);

        property = new SampleProperty("Level 1", "The row is intended to show how to create several levels.");

        SampleProperty property2 = new SampleProperty("Level 2", "This level 2 is editable", String.class);
        property.addChild(property2);

        SampleProperty property31 = new SampleProperty("Level 3.1");
        property2.addChild(property31);

        SampleProperty property32 = new SampleProperty("Level 3.2");
        property2.addChild(property32);

        SampleProperty property33 = new SampleProperty("Level 3.3 - Hidden");
        property33.setHidden(true);
        property2.addChild(property33);

        SampleProperty property34 = new SampleProperty("You can have as many levels as you want.");
        property2.addChild(property34);

        list.add(property);

        property = new SampleProperty("String Array", "This row shows you how to create a String array cell editor",
                String[].class);
        list.add(property);

        final SampleProperty priorityProperty = new SampleProperty("Priority", "This row shows you how to create a cell editor for enum",
                Integer.class);
        priorityProperty.setConverterContext(priorityConverter.getContext());
        priorityProperty.setEditorContext(new EditorContext(priorityConverter.getName()));
        list.add(priorityProperty);

        final SampleProperty tristateProperty = new SampleProperty("Tristate", "This row shows you how to tristate check box for integer", Integer.class);
        tristateProperty.setEditorContext(TristateCheckBoxCellEditor.CONTEXT);
        list.add(tristateProperty);

        property = new SampleProperty("Multiline", "This row shows you how to create a cell editor for multiple line text",
                String.class);
        property.setEditorContext(MultilineStringCellEditor.CONTEXT);
        property.setConverterContext(MultilineStringConverter.CONTEXT);
        list.add(property);

        property = new SampleProperty("Multiple Values", "This row shows you how to create a cell editor for multiple values and dynamically change its value set according to the priority property",
                String[].class);
        property.setEditorContext(new EditorContext("ABCDE"));
        list.add(property);

        property = new SampleProperty("Password", "This row shows you how to create a cell editor for password",
                char[].class);
        property.setConverterContext(PasswordConverter.CONTEXT);
        property.setEditorContext(PasswordCellEditor.CONTEXT);
        list.add(property);

        PropertyTableModel model = new EditorStylePropertyTableModel<SampleProperty>(list);
        PropertyTable table = new PropertyTable(model) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                Property property = getPropertyTableModel().getPropertyAt(row);
                if (property != null && "Text".equals(property.getName()) && column == 1) {
                    return new MultilineTableCellRenderer();
                }
                return super.getCellRenderer(row, column);
            }
        };
        table.setPreferredScrollableViewportSize(new Dimension(400, 600));
        table.expandFirstLevel();
//        table.setRowAutoResizes(true);
// Support for tree expansion listener
//        table.addTreeExpansionListener(new TreeExpansionListener() {
//            public void treeCollapsed(TreeExpansionEvent event) {
//                System.out.println("-" + event.getPath());
//            }
//
//            public void treeExpanded(TreeExpansionEvent event) {
//                System.out.println("+" + event.getPath());
//            }
//        });
        PropertyTableSearchable searchable = new PropertyTableSearchable(table);
        searchable.setRecursive(true);

        table.setTableStyleProvider(new RowStripeTableStyleProvider());
        return table;
    }

    static HashMap<String, Object> map = new HashMap<String, Object>();

    static {
        map.put("Bounds", new Rectangle(0, 0, 100, 200));
        map.put("Dimension", new Dimension(800, 600));
        map.put("Background", new Color(255, 0, 0));
        map.put("Foreground", new Color(255, 255, 255));
        map.put("File Name", "C:\\Sample.java");
        map.put("Folder Name", "C:\\");
        map.put("CreationDate", Calendar.getInstance());
        map.put("ExpirationDate", Calendar.getInstance());
        map.put("DateTime", Calendar.getInstance());
        map.put("Name", "Label1");
        map.put("Font Name", "Arial");
        map.put("Font", new Font("Tahoma", Font.BOLD, 11));
        map.put("Default Font Name", "Verdana");
        map.put("Text", "Data");
        map.put("Opaque", Boolean.FALSE);
        map.put("Visible", Boolean.TRUE);
        map.put("Not Editable", 10);
        map.put("Long", (long) 123456789);
        map.put("Integer", 1234);
        map.put("Slider", 50);
        map.put("Double", 1.234567890);
        map.put("Float", new Float(0.01));
        map.put("Calculator", 0.0);
        map.put("SSN", "000000000");
        map.put("IP Address", "192.168.0.1");
        map.put("Priority", 1);
        map.put("Tristate", 2);
        map.put("Multiline", "This is a multiple line cell editor. \nA new line starts here.");
        map.put("Password", new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'});
        map.put("Multiple Values", new String[]{"A", "B", "C"});
    }

    static class SampleProperty extends Property {
        private static final long serialVersionUID = -3739511507958060504L;

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
            Object old = getValue();
            if (!JideSwingUtilities.equals(old, value)) {
                map.put(getFullName(), value);
                firePropertyChange(PROPERTY_VALUE, old, value);
            }
        }

        @Override
        public Object getValue() {
            return map.get(getFullName());
        }

        @Override
        public boolean hasValue() {
            return map.get(getFullName()) != null;
        }
    }

    static class EditorStylePropertyTableModel<T extends Property> extends PropertyTableModel<T> implements EditorStyleTableModel, StyleModel {
        private static final long serialVersionUID = -4435995349055070783L;

        public EditorStylePropertyTableModel(List<T> properties) {
            super(properties);
        }

        public int getEditorStyleAt(int rowIndex, int columnIndex) {
            return _editorStyle;
        }

        @Override
        public ConverterContext getConverterContextAt(int row, int column) {
            T valueProperty = getPropertyAt(row);
            if (valueProperty == null) {
                return null;
            }
            T priorityProperty = getProperty("Priority");
            if ("Multiple Values".equals(valueProperty.getName())) {
                Object[] possibleValues = new Object[]{"A", "B", "C", "D", "E"};
                Object priorityValue = priorityProperty.getValue();
                if (priorityValue instanceof Integer && (Integer) priorityValue == 0) {
                    possibleValues = new Object[]{"A", "B"};
                }
                return new ConverterContext("ABCDE", possibleValues);
            }
            return super.getConverterContextAt(row, column);
        }

        @Override
        public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                T property = getPropertyAt(rowIndex);
                if (property.isRequired()) {
                    return CELL_STYLE_REQUIRED;
                }
                else if (property.isExpert()) {
                    return CELL_STYLE_EXPERT;
                }
            }
            return null;
        }

        @Override
        public boolean isCellStyleOn() {
            return true;
        }
    }
}
