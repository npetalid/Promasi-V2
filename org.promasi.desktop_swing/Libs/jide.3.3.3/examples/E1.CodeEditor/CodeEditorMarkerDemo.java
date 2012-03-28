/*
 * @(#)CodeEditorMarkerDemo.java 6/8/2010
 *
 * Copyright 2002 - 2010 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.editor.CodeEditor;
import com.jidesoft.editor.ListDataCodeEditorIntelliHints;
import com.jidesoft.editor.SyntaxStyle;
import com.jidesoft.editor.status.CodeEditorStatusBar;
import com.jidesoft.editor.tokenmarker.JavaTokenMarker;
import com.jidesoft.marker.Marker;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.Searchable;
import com.jidesoft.swing.SearchableBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;

/**
 * Demoed Component: {@link com.jidesoft.editor.CodeEditor} <br> Required jar files: jide-common.jar, jide-editor.jar,
 * jide-components.jar, jide-shortcut.jar, jide-editor.jar <br> Required L&F: any L&F
 */
public class CodeEditorMarkerDemo extends AbstractDemo {
    public CodeEditor _editor;
    private static final long serialVersionUID = 146545943327662356L;

    public CodeEditorMarkerDemo() {
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

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JCheckBox checkBox = new JCheckBox("Underline jidesoft");
        checkBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                List<Marker> list = _editor.getMarkerModel().getMarkers();
                for (Marker marker : list) {
                    marker.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
                }
            }
        });
        checkBox.setSelected(true);
        panel.add(checkBox);
        return panel;
    }

    public Component getDemoPanel() {
        _editor = new CodeEditor();
        try {
            StringBuffer buf = readInputStream(CodeEditorMarkerDemo.class.getClassLoader().getResourceAsStream("CodeEditorDemo.java"));
            _editor.setTokenMarker(new JavaTokenMarker());
            String string = buf.toString();
            _editor.setText(string);
            _editor.setPreferredSize(new Dimension(600, 500));
            _editor.setHorizontalScrollBarPolicy(ScrollPane.SCROLLBARS_AS_NEEDED);
            _editor.setVerticalScrollBarPolicy(ScrollPane.SCROLLBARS_AS_NEEDED);
            Set<String> stringSet = JavaTokenMarker.getKeywords().keyWordSet();
            new ListDataCodeEditorIntelliHints<String>(_editor, stringSet.toArray(new String[stringSet.size()]));

            final JPanel panel = new JPanel(new BorderLayout());
            panel.add(_editor);
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
            int fromIndex = 0;

            while (fromIndex < string.length()) {
                int index = string.indexOf("jidesoft", fromIndex);
                if (index < 0) {
                    break;
                }
                fromIndex = index + 8;
                _editor.getMarkerModel().addMarker(index, fromIndex - 1, com.jidesoft.marker.Marker.TYPE_CUSTOM_STYLE, "We make you love being a Swing developer.");
            }
            SyntaxStyle style = new SyntaxStyle();
            style.setForeground(Color.blue);
            style.setEffect(SyntaxStyle.EFFECT_UNDERLINE);
            style.setEffectColor(Color.blue);
            _editor.getStyles().addStyle(com.jidesoft.marker.Marker.TYPE_CUSTOM_STYLE, style);
            _editor.setShowToolTipOverMarkedText(true);
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

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeel();
                showAsFrame(new CodeEditorMarkerDemo());
            }
        });

    }
}