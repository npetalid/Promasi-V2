/*
 * @(#)TableDiffPaneDemo.java 7/6/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.ColorExComboBox;
import com.jidesoft.diff.TableDiffPane;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class TableDiffPaneDemo extends AbstractDemo {
    private static final long serialVersionUID = -9024662391892939238L;
    public TableDiffPane _diffPane;
    protected String _lastDirectory = ".";

    public TableDiffPaneDemo() {
    }

    public String getName() {
        return "DiffPane Demo (JTable)";
    }

    public String getProduct() {
        return PRODUCT_NAME_DIFF;
    }

    @Override
    public String getDescription() {
        return "This is a demo for TableDiffPane to compare the difference between two text files." +
                "Demoed classes:\n" +
                "TableDiffPane";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ColorExComboBox changedColorExComboBox = new ColorExComboBox();
        changedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.changed"));
        changedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _diffPane.setChangedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Changed: "), changedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));
        panel.add(Box.createVerticalStrut(2));

        ColorExComboBox insertedColorExComboBox = new ColorExComboBox();
        insertedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.inserted"));
        insertedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _diffPane.setInsertedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Inserted: "), insertedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));
        panel.add(Box.createVerticalStrut(2));

        ColorExComboBox deletedColorExComboBox = new ColorExComboBox();
        deletedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.deleted"));
        deletedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _diffPane.setDeletedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Deleted"), deletedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));

        panel.add(Box.createVerticalStrut(6));
        panel.add(JideSwingUtilities.createCenterPanel(new JButton(new AbstractAction("Clear Diff Results") {
            private static final long serialVersionUID = 50201280516163621L;

            public void actionPerformed(ActionEvent e) {
                _diffPane.clearDiff();
            }
        })));

        return panel;
    }

    public Component getDemoPanel() {
        _diffPane = new TableDiffPane(createLeftTableModel(), createRightTableModel());
        _diffPane.setPreferredSize(new Dimension(600, 500));

        _diffPane.setFromTitle("Local");
        _diffPane.setToTitle("Remote");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(_diffPane);

        panel.add(new JButton(new AbstractAction("Compare") {
            private static final long serialVersionUID = -4133857847867200358L;

            public void actionPerformed(ActionEvent e) {
                _diffPane.diff();
            }
        }), BorderLayout.AFTER_LAST_LINE);
        return panel;
    }

    private Object createLeftTableModel() {
        DefaultTableModel model = new DefaultTableModel(100, 5);
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                if (i > 20) {
                    model.setValueAt((i + 1) + "," + j, i, j);
                }
                else {
                    model.setValueAt(i + "," + j, i, j);
                }
            }
        }
        model.setValueAt("Changed", 5, 2);
        return model;
    }

    private Object createRightTableModel() {
        DefaultTableModel model = new DefaultTableModel(100, 5);
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                if (i > 40) {
                    model.setValueAt((i + 1) + "," + j, i, j);
                }
                else {
                    model.setValueAt(i + "," + j, i, j);
                }
            }
        }
        model.setValueAt("New Text", 5, 2);
        return model;
    }

    private static StringBuffer readInputStream(InputStream in) throws IOException {
        Reader reader = new InputStreamReader(in);
        char[] buf = new char[1024];
        StringBuffer buffer = new StringBuffer();
        int read;
        while ((read = reader.read(buf)) != -1) {
            buffer.append(buf, 0, read);
        }
        reader.close();
        return buffer;
    }

    @Override
    public String getDemoFolder() {
        return "I1.DiffTextPane";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                AbstractDemo.showAsFrame(new TableDiffPaneDemo());
            }
        });

    }
}