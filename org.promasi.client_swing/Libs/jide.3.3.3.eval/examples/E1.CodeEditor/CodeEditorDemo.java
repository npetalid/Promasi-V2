/*
 * @(#)CodeEditorDemo.java 5/30/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.editor.CodeEditor;
import com.jidesoft.editor.ListDataCodeEditorIntelliHints;
import com.jidesoft.editor.language.LanguageSpec;
import com.jidesoft.editor.language.LanguageSpecManager;
import com.jidesoft.editor.status.CodeEditorStatusBar;
import com.jidesoft.editor.tokenmarker.JavaTokenMarker;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.Searchable;
import com.jidesoft.swing.SearchableBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Set;

/**
 * Demoed Component: {@link com.jidesoft.editor.CodeEditor} <br> Required jar files: jide-common.jar, jide-editor.jar,
 * jide-components.jar, jide-shortcut.jar, jide-editor.jar <br> Required L&F: any L&F
 */
public class CodeEditorDemo extends AbstractDemo {
    public CodeEditor _editor;
    private static final long serialVersionUID = 146545943327662356L;

    public CodeEditorDemo() {
    }

    public String getName() {
        return "CodeEditor Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_CODE_EDITOR;
    }

    @Override
    public String getDescription() {
        return "This is the simplest example of using CodeEditor. All it does is to show you how to open a CodeEditor" +
                "with Java syntax coloring. There is no other customization in this example. You can check CodeEditorDocumentPane " +
                "and other examples to see more advanced features.\n\n" +
                "Demoed classes:\n" +
                "CodeEditor";
    }

    public Component getDemoPanel() {
        _editor = new CodeEditor();
        try {
            StringBuffer buf = readInputStream(CodeEditorDemo.class.getClassLoader().getResourceAsStream("CodeEditorDemo.java"));
            _editor.setTokenMarker(new JavaTokenMarker());
            LanguageSpec languageSpec = LanguageSpecManager.getInstance().getLanguageSpec("Java");
            if (languageSpec != null) {
                languageSpec.configureCodeEditor(_editor);
            }
            _editor.setText(buf.toString());
            _editor.setPreferredSize(new Dimension(600, 500));
            _editor.setHorizontalScrollBarPolicy(ScrollPane.SCROLLBARS_AS_NEEDED);
            _editor.setVerticalScrollBarPolicy(ScrollPane.SCROLLBARS_AS_NEEDED);
            Set<String> stringSet = JavaTokenMarker.getKeywords().keyWordSet();
            String[] strings = stringSet.toArray(new String[stringSet.size()]);
            Arrays.sort(strings);
            new ListDataCodeEditorIntelliHints<String>(_editor, strings);

            final JPanel panel = new JPanel(new BorderLayout());
            panel.add(_editor.createOverlay());
            final JPanel barPanel = new JPanel(new BorderLayout());
            barPanel.add(new CodeEditorStatusBar(_editor), BorderLayout.CENTER);
            panel.add(barPanel, BorderLayout.AFTER_LAST_LINE);
            Searchable searchable = _editor.getSearchable();
            searchable.setRepeats(true);
            SearchableBar _textAreaSearchableBar = SearchableBar.install(searchable, KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), new SearchableBar.Installer() {
                public void openSearchBar(SearchableBar searchableBar) {
                    barPanel.add(searchableBar, BorderLayout.AFTER_LAST_LINE);
                    barPanel.invalidate();
                    barPanel.revalidate();
                }

                public void closeSearchBar(SearchableBar searchableBar) {
                    barPanel.remove(searchableBar);
                    barPanel.invalidate();
                    barPanel.revalidate();
                }
            });
            _textAreaSearchableBar.getInstaller().openSearchBar(_textAreaSearchableBar);
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
        return "E1.CodeEditor";
    }

    @Override
    public void dispose() {
        _editor = null;
    }

    protected String _lastDirectory = ".";

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JButton(new AbstractAction("Open File") {
            private static final long serialVersionUID = -5339862184315043027L;

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser() {
                    @Override
                    protected JDialog createDialog(Component parent) throws HeadlessException {
                        JDialog dialog = super.createDialog(parent);
                        dialog.setTitle("Export the content to an Excel file");
                        return dialog;
                    }
                };
                chooser.setCurrentDirectory(new File(_lastDirectory));
                int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Open...");
                if (result == JFileChooser.APPROVE_OPTION) {
                    _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                    _editor.setFileName(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        }));
        panel.add(new JButton(new AbstractAction("Export to File") {
            private static final long serialVersionUID = 6423961109414784138L;

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser() {
                    @Override
                    protected JDialog createDialog(Component parent) throws HeadlessException {
                        JDialog dialog = super.createDialog(parent);
                        dialog.setTitle("Export the content to an Excel file");
                        return dialog;
                    }
                };
                chooser.setCurrentDirectory(new File(_lastDirectory));
                int result = chooser.showDialog(((JButton) e.getSource()).getTopLevelAncestor(), "Export");
                if (result == JFileChooser.APPROVE_OPTION) {
                    _lastDirectory = chooser.getCurrentDirectory().getAbsolutePath();
                    try {
                        String fileName = chooser.getSelectedFile().getAbsolutePath();
                        if (!JideSwingUtilities.equals(fileName, _editor.getFileName())) { // cannot export to the same file the editor is reading
                            System.out.println("Writing to file: " + fileName);
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            _editor.exportToOutputStream(fileOut);
                            fileOut.close();
                            System.out.println("File Written");
                        }
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }));
        return panel;
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new CodeEditorDemo());
            }
        });

    }
}
