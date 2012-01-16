/*
 * @(#)StyledLabelPerformanceDemo.java 8/11/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.StyleRange;
import com.jidesoft.swing.StyledLabel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;

/**
 * Demoed Component: {@link com.jidesoft.swing.StyledLabel} <br> Required jar files: jide-common.jar <br> Required L&F:
 * any L&F
 */
public class StyledLabelPerformanceDemo extends AbstractDemo {
    private static final long serialVersionUID = 216192209610822877L;
    private final int COUNT = 100;
    private final double RATIO = 1000000.0;

    public StyledLabelPerformanceDemo() {
        ObjectConverterManager.initDefaultConverter();
    }

    public String getName() {
        return "StyledLabel Demo (Performance Test)";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "StyledLabel is a special JLabel that can display the text in different colors and mixed with all kinds of line decorations.\n" +
                "\nSome features provided by StyledLabel can be achieved using html code in JLabel. However there are many advantages of using a StyledLabel than using an html JLabel. " +
                "One of the most important advantages is the performance. " +
                "StyledLabel is a very simple and almost as light as a plain JLabel, " +
                "thus the performance of StyledLabel is 20 to 40 times better than html JLabel based on our test. " +
                "This demo is to show you the difference. It will create " + COUNT + " plain text JLabel, " + COUNT + " StyledLabel, " + COUNT + " html JLabel. " +
                "The time taken by each case is shown on top of the corresponding panel. On our testing machine, the time used by plain JLabel and StyledLabel are almost the same (7 ms)" +
                "and both are 20 to 40 times faster than html labels (use 237 ms). After seeing the result, I guess you should be more careful when using html JLabel in your code.\n" +
                "\nDemoed classes:\n" +
                "com.jidesoft.swing.StyledLabel";
    }

    public Component getDemoPanel() {
        JideSplitPane panel = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);

        new JTextPane(); // another warm up to load classes related to JTextPane

        panel.add(createLabelsPanel());
        panel.add(createHtmlLabelsPanel());
        panel.add(createTextPanesPanel());
        panel.add(createStyledLabelsPanel());
        return panel;
    }

    private JComponent createStyledLabelsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 1));

        String text = "Bold Italic Underlined";
        new StyledLabel(text); // warm up to make sure classes are loaded. Otherwise the first JLabel test case will take longer time to complete as it has to load all the new classes

        // Creates a StyledLabel to warn up so that we don't include class loading time into the performance test.
        // This is the same for all three cases.
        StyledLabel label = new StyledLabel(text);
        // we could pub the creation of StyleRange[] outside the loop.
        // But to make the comparison fair, we kept it inside.
        label.setStyleRanges(new StyleRange[]{
                new StyleRange(0, 4, Font.BOLD),
                new StyleRange(5, 6, Font.ITALIC),
                new StyleRange(12, 10, Font.PLAIN, StyleRange.STYLE_UNDERLINED)
        });

        long start = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            label = new StyledLabel(text);
            // we could pub the creation of StyleRange[] outside the loop.
            // But to make the comparison fair, we kept it inside.
            label.setStyleRanges(new StyleRange[]{
                    new StyleRange(0, 4, Font.BOLD),
                    new StyleRange(5, 6, Font.ITALIC),
                    new StyleRange(12, 10, Font.PLAIN, StyleRange.STYLE_UNDERLINED)
            });
            panel.add(label);
        }
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialLineBorder(Color.gray, 1, true), " StyledLabel Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
                TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        return panel;
    }

    private JComponent createHtmlLabelsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 1));

        String text = "<HTML><B>Bold</B> <I>Italic</I> <U>Underlined</U></HTML>";
        new JLabel(text); // another warm up to load classes related to HTML views
        long start = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            JLabel label = new JLabel(text);
            panel.add(label);
        }
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialLineBorder(Color.gray, 1, true), " JLabel (HTML) Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
                TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        return panel;
    }

    private JComponent createTextPanesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 1));

        new JTextPane();

        long start = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            StyledDocument document = new DefaultStyledDocument();

            SimpleAttributeSet bold = new SimpleAttributeSet();
            bold.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
            SimpleAttributeSet italic = new SimpleAttributeSet();
            italic.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.TRUE);
            SimpleAttributeSet underlined = new SimpleAttributeSet();
            underlined.addAttribute(StyleConstants.CharacterConstants.Underline, Boolean.TRUE);

            try {
                document.insertString(document.getLength(), "Bold ", bold);
                document.insertString(document.getLength(), "Italic ", italic);
                document.insertString(document.getLength(), "Underlined", underlined);
            }
            catch (BadLocationException badLocationException) {
                System.err.println("Bad insert");
            }

            JTextPane textPane = new JTextPane(document);
            textPane.setOpaque(false);
            panel.add(textPane);
        }
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialLineBorder(Color.gray, 1, true), " JTextPane Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
                TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        return panel;
    }

    private JComponent createLabelsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 1));

        // Creates a StyledLabel to warn up so that we don't include class loading time into the performance test.
        // This is the same for all three cases.
        new JLabel("Bold Italic Underlined");

        long start = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            JLabel label = new JLabel("Bold Italic Underlined");
            panel.add(label);
        }
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialLineBorder(Color.gray, 1, true), " JLabel (Plain) Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
                TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "B15.StyledLabel";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new StyledLabelPerformanceDemo());
            }
        });
    }
}
