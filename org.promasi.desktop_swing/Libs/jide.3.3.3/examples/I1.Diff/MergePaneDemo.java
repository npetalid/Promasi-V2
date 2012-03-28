/*
 * @(#)MergePaneDemo.java 1/14/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.ColorExComboBox;
import com.jidesoft.diff.CodeEditorMergePane;
import com.jidesoft.editor.CodeEditor;
import com.jidesoft.editor.tokenmarker.JavaTokenMarker;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

public class MergePaneDemo extends AbstractDemo {
    private static final long serialVersionUID = -4551699728963382356L;
    public CodeEditorMergePane _mergePane;
    protected String _lastDirectory = ".";

    public MergePaneDemo() {
    }

    public String getName() {
        return "MergePane Demo (CodeEditor)";
    }

    public String getProduct() {
        return PRODUCT_NAME_DIFF;
    }

    @Override
    public String getDescription() {
        return "This is a demo for CodeEditorMergePane to merge the changes from two sources." +
                "Demoed classes:\n" +
                "CodeEditorMergePane";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(new JButton(new AbstractAction("Left Pane") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser() {
                    @Override
                    protected JDialog createDialog(Component parent) throws HeadlessException {
                        JDialog dialog = super.createDialog(parent);
                        dialog.setTitle("Load a file to compare");
                        return dialog;
                    }
                };
                chooser.setCurrentDirectory(new File(_lastDirectory));
                int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Open");
                if (result == JFileChooser.APPROVE_OPTION) {
                    _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                    String file = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        StringBuffer buf = readInputStream(new FileInputStream(file));
                        _mergePane.setFromText(buf.toString());
                        _mergePane.merge();
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }));
        buttonPanel.add(new JButton(new AbstractAction("Middle Pane") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser() {
                    @Override
                    protected JDialog createDialog(Component parent) throws HeadlessException {
                        JDialog dialog = super.createDialog(parent);
                        dialog.setTitle("Load a file to compare");
                        return dialog;
                    }
                };
                chooser.setCurrentDirectory(new File(_lastDirectory));
                int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Open");
                if (result == JFileChooser.APPROVE_OPTION) {
                    _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                    String file = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        StringBuffer buf = readInputStream(new FileInputStream(file));
                        _mergePane.setToText(buf.toString());
                        _mergePane.merge();
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }));
        buttonPanel.add(new JButton(new AbstractAction("Right Pane") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser() {
                    @Override
                    protected JDialog createDialog(Component parent) throws HeadlessException {
                        JDialog dialog = super.createDialog(parent);
                        dialog.setTitle("Load a file to compare");
                        return dialog;
                    }
                };
                chooser.setCurrentDirectory(new File(_lastDirectory));
                int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Open");
                if (result == JFileChooser.APPROVE_OPTION) {
                    _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                    String file = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        StringBuffer buf = readInputStream(new FileInputStream(file));
                        _mergePane.setOtherText(buf.toString());
                        _mergePane.merge();
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }));
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Choose a File for: "), buttonPanel, BorderLayout.BEFORE_FIRST_LINE));
        panel.add(Box.createVerticalStrut(6));
        ColorExComboBox changedColorExComboBox = new ColorExComboBox();
        changedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.changed"));
        changedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _mergePane.setChangedColor((Color) e.getItem());
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
                    _mergePane.setInsertedColor((Color) e.getItem());
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
                    _mergePane.setDeletedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Deleted"), deletedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));
        panel.add(Box.createVerticalStrut(2));
        ColorExComboBox conflictedColorExComboBox = new ColorExComboBox();
        conflictedColorExComboBox.setSelectedColor(UIDefaultsLookup.getColor("DiffMerge.conflicted"));
        conflictedColorExComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _mergePane.setConflictedColor((Color) e.getItem());
                }
            }
        });
        panel.add(JideSwingUtilities.createLabeledComponent(new JLabel("Conflicted"), conflictedColorExComboBox, BorderLayout.BEFORE_FIRST_LINE));

        panel.add(Box.createVerticalStrut(6));
        panel.add(JideSwingUtilities.createCenterPanel(new JButton(new AbstractAction("Clear Merge Results") {
            private static final long serialVersionUID = -7850846759615518093L;

            public void actionPerformed(ActionEvent e) {
                _mergePane.clearMerge();
            }
        })));

        return panel;
    }

    public Component getDemoPanel() {
        try {
            StringBuffer buf1 = readInputStream(MergePaneDemo.class.getClassLoader().getResourceAsStream("Diff1.txt"));
            StringBuffer buf2 = readInputStream(MergePaneDemo.class.getClassLoader().getResourceAsStream("Diff2.txt"));
            StringBuffer buf3 = readInputStream(MergePaneDemo.class.getClassLoader().getResourceAsStream("Diff3.txt"));
            _mergePane = new CodeEditorMergePane(buf3.toString(), buf1.toString(), buf2.toString()) {
                @Override
                protected void customizeEditor(CodeEditor editor, int index) {
                    super.customizeEditor(editor, index);
                    editor.setTokenMarker(new JavaTokenMarker());
                }
            };
            _mergePane.setPreferredSize(new Dimension(600, 500));

            _mergePane.setFromTitle("Local Modifications");
            _mergePane.setToTitle("Merge Result");
            _mergePane.setOtherTitle("Remote Modifications");

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(_mergePane);

            panel.add(new JButton(new AbstractAction("Compare") {
                private static final long serialVersionUID = -4133857847867200358L;

                public void actionPerformed(ActionEvent e) {
                    _mergePane.merge();
                }
            }), BorderLayout.AFTER_LAST_LINE);
            return panel;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                AbstractDemo.showAsFrame(new MergePaneDemo());
            }
        });

    }
}