/*
 * @(#)SearchableBarDemo.java 10/17/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTabbedPane;
import com.jidesoft.swing.Searchable;
import com.jidesoft.swing.SearchableBar;
import com.jidesoft.swing.SearchableUtils;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;

/**
 * Demoed Component: {@link com.jidesoft.swing.SearchableBar} <br> Required jar files: jide-common.jar,
 * jide-components.jar <br> Required L&F: Jide L&F extension required
 */
public class SearchableBarDemo extends AbstractDemo {
    private static final long serialVersionUID = 2129144432840257348L;

    public SearchableBar _textAreaSearchableBar;
    public SearchableBar _tableSearchableBar;

    public SearchableBarDemo() {
    }

    public String getName() {
        return "SearchableBar Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "SearchableBar works with Searchable components to provide a full size panel to achieve the searching feature. \n" +
                "Comparing to default Searchable feature, SearchableBar is more appropriate for components such as " +
                "a large text area or table.\n" +
                "1. Press any a specified key stroke to start the search. In the demo, we use CTRL-F or CMD-F to start searching but it could be customized to any key stroke.\n" +
                "2. Press up/down arrow key to navigation to next or previous matching occurrence\n" +
                "3. Press Highlights button to select all matching occurrences\n" +
                "4. A lot of customization options using the API\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.swing.SearchableBar";
    }

    @Override
    public Component getOptionsPanel() {
        JPanel switchPanel = new JPanel(new GridLayout(0, 1, 3, 3));

        final JRadioButton style1 = new JRadioButton("Full");
        final JRadioButton style2 = new JRadioButton("Compact");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(style1);
        buttonGroup.add(style2);

        switchPanel.add(new JLabel("Styles:"));
        switchPanel.add(style1);
        switchPanel.add(style2);
        style1.setSelected(true);

        style1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (style1.isSelected()) {
                    _tableSearchableBar.setCompact(false);
                    _textAreaSearchableBar.setCompact(false);
                }
            }
        });
        style2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (style2.isSelected()) {
                    _tableSearchableBar.setCompact(true);
                    _textAreaSearchableBar.setCompact(true);
                }
            }
        });

        switchPanel.add(new JLabel("Options: "));

        final JCheckBox option1 = new JCheckBox("Show close button");
        final JCheckBox option2 = new JCheckBox("Show navigation buttons");
        final JCheckBox option3 = new JCheckBox("Show highlights button");
        final JCheckBox option4 = new JCheckBox("Show match case check box");
        final JCheckBox option5 = new JCheckBox("Show repeats check box");
        final JCheckBox option6 = new JCheckBox("Show status");

        switchPanel.add(option1);
        switchPanel.add(option2);
        switchPanel.add(option3);
        switchPanel.add(option4);
        switchPanel.add(option5);
        switchPanel.add(option6);

        option1.setSelected(true);
        option1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateButtons(option1.isSelected(), SearchableBar.SHOW_CLOSE);
            }
        });

        option2.setSelected(true);
        option2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateButtons(option2.isSelected(), SearchableBar.SHOW_NAVIGATION);
            }
        });

        option3.setSelected(true);
        option3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateButtons(option3.isSelected(), SearchableBar.SHOW_HIGHLIGHTS);
            }
        });

        option4.setSelected(true);
        option4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateButtons(option4.isSelected(), SearchableBar.SHOW_MATCHCASE);
            }
        });

        option5.setSelected(false);
        option5.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateButtons(option5.isSelected(), SearchableBar.SHOW_REPEATS);
            }
        });

        option6.setSelected(true);
        option6.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateButtons(option6.isSelected(), SearchableBar.SHOW_STATUS);
            }
        });

        return switchPanel;
    }

    private void updateButtons(boolean selected, int bit) {
        if (selected) {
            _textAreaSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() | bit);
            _tableSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() | bit);
        }
        else {
            _textAreaSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() & ~bit);
            _tableSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() & ~bit);
        }
    }

    public Component getDemoPanel() {
        JideTabbedPane tabbedPane = new JideTabbedPane();
        tabbedPane.addTab("JTextArea with SearchableBar", createSearchableTextArea());
        tabbedPane.addTab("JTable with SearchableBar", createSearchableTable());
        return tabbedPane;
    }

    private JPanel createSearchableTextArea() {
        final JTextComponent textArea = SearchableBarDemo.createEditor("Readme.txt");
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        Searchable searchable = SearchableUtils.installSearchable(textArea);
        searchable.setRepeats(true);
        _textAreaSearchableBar = SearchableBar.install(searchable, KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK), new SearchableBar.Installer() {
            public void openSearchBar(SearchableBar searchableBar) {
                String selectedText = textArea.getSelectedText();
                if (selectedText != null && selectedText.length() > 0) {
                    searchableBar.setSearchingText(selectedText);
                }
                panel.add(searchableBar, BorderLayout.AFTER_LAST_LINE);
                panel.invalidate();
                panel.revalidate();
            }

            public void closeSearchBar(SearchableBar searchableBar) {
                panel.remove(searchableBar);
                panel.invalidate();
                panel.revalidate();
            }
        });
        _textAreaSearchableBar.getInstaller().openSearchBar(_textAreaSearchableBar);
        return panel;
    }

    private JPanel createSearchableTable() {
        JTable table = new JTable(DemoData.createQuoteTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(200, 100));
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        Searchable searchable = SearchableUtils.installSearchable(table);
        searchable.setRepeats(true);
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        _tableSearchableBar = SearchableBar.install(searchable, KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK), new SearchableBar.Installer() {
            public void openSearchBar(SearchableBar searchableBar) {
                panel.add(searchableBar, BorderLayout.AFTER_LAST_LINE);
                panel.invalidate();
                panel.revalidate();
            }

            public void closeSearchBar(SearchableBar searchableBar) {
                panel.remove(searchableBar);
                panel.invalidate();
                panel.revalidate();
            }
        });
        _tableSearchableBar.setName("TableSearchableBar");
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "B7.SearchableComponents";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                AbstractDemo.showAsFrame(new SearchableBarDemo());
            }
        });
    }

    public static JTextComponent createEditor(String fileName) {
        JTextComponent textComponent = new JTextArea() {
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(700, 400);
            }
        };
        Document doc = new DefaultStyledDocument();
        try {
            // try to start reading
            InputStream in = SearchableBarDemo.class.getResourceAsStream(fileName);
            if (in != null) {
                byte[] buff = new byte[4096];
                int nch;
                while ((nch = in.read(buff, 0, buff.length)) != -1) {
                    doc.insertString(doc.getLength(), new String(buff, 0, nch), null);
                }
                textComponent.setDocument(doc);
            }
            else {
                textComponent.setText("Copy readme.txt into the class output directory");
            }
        }
        catch (Exception e) {
            // ignore
        }
        return textComponent;
    }
}
