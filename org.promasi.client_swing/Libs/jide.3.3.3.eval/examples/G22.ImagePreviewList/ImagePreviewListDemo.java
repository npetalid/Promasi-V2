/*
 * @(#)ImagePreviewListDemo.java 4/4/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.ColorExComboBox;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.list.DefaultPreviewImageIcon;
import com.jidesoft.list.ImagePreviewList;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.tooltip.ExpandedTipUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Demoed Component: {@link ImagePreviewList} <br> Required jar files: jide-common.jar, jide-grids.jar <br> Required
 * L&F: any L&F
 */
public class ImagePreviewListDemo extends AbstractDemo {
    private static final long serialVersionUID = 2020912432759857686L;
    protected ImagePreviewList _imagePreviewList;

    public ImagePreviewListDemo() {
    }

    public String getName() {
        return "ImagePreviewList Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    @Override
    public String getDescription() {
        return "This is a demo of ImagePreviewList. It can be used to preview a list of images in thumbnail view.\n" +
                "Demoed classes:\n" +
                "com.jidesoft.list.ImagePreviewList";
    }

    public Component getDemoPanel() {
        DefaultListModel list = new DefaultListModel();
        list.addElement(new DefaultPreviewImageIcon(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32), "Logo", "This is a 32x32 logo for JIDE product"));
        list.addElement(new DefaultPreviewImageIcon(JideIconsFactory.getImageIcon(JideIconsFactory.JIDELOGO_SMALL), "Logo", "This is a larger logo for JIDE product"));
        ImageIcon logo = JideIconsFactory.getImageIcon(JideIconsFactory.JIDELOGO);
        logo.setDescription("Logo\nThis is an even larger logo for JIDE product");
        list.addElement(logo);
        try {
            ImageIcon icon = IconsFactory.getImageIcon(ImagePreviewList.class, "/images/Freestyle.jpg");
            list.addElement(new DefaultPreviewImageIcon(icon, "Freestyle", "This is a sample description"));
//            for(int i = 0; i < 720; i += 15) {
//                list.addElement(new DefaultPreviewImageIcon(IconsFactory.createRotatedImage(new JLabel(), icon, i), "Freestyle", "This is a sample description"));
//            }
            list.addElement(new DefaultPreviewImageIcon(IconsFactory.getImageIcon(ImagePreviewList.class, "/images/Fish.jpg"), "Fish", "This is a sample description"));
            list.addElement(new DefaultPreviewImageIcon(IconsFactory.getImageIcon(ImagePreviewList.class, "/images/Overlooking Rio.jpg"), "Overlooking Rio", "This is a sample description"));
            list.addElement(new DefaultPreviewImageIcon(IconsFactory.getImageIcon(ImagePreviewList.class, "/images/Sunset.jpg"), "Sunset", "This is a sample description"));
            list.addElement(new DefaultPreviewImageIcon(IconsFactory.getImageIcon(ImagePreviewList.class, "/images/Water lilies.jpg"), "Water lilies", "This is a sample description"));
            list.addElement(new DefaultPreviewImageIcon(IconsFactory.getImageIcon(ImagePreviewList.class, "/images/Winter.jpg"), "Winter", "This is a sample description"));
            list.addElement(new DefaultPreviewImageIcon(IconsFactory.getImageIcon(ImagePreviewList.class, "/images/Big Wave.jpg"), "Big Wave", "The dimension is not real size", new Dimension(2, 2)));
            ImageIcon imageIcon = IconsFactory.getImageIcon(ImagePreviewList.class, "/images/At the Arch.jpg");
            imageIcon.setDescription("At the Arch\nThis is passed in as ImageIcon");
            list.addElement(imageIcon);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        list.addElement(new DefaultPreviewImageIcon(null, "", "This is a demo of image whose preview is not available", null));
        _imagePreviewList = new ImagePreviewList(list) {
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(750, 600);
            }
        };
        _imagePreviewList.setShowDetails(ImagePreviewList.SHOW_TITLE | ImagePreviewList.SHOW_DESCRIPTION | ImagePreviewList.SHOW_SIZE);
        _imagePreviewList.setCellDimension(new Dimension(250, 135));

        ExpandedTipUtils.install(_imagePreviewList);

        return new JScrollPane(_imagePreviewList);
    }

    @Override
    public Component getOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 2));
        panel.add(new JLabel("Grid Line Color: "));
        ColorExComboBox gridColor = new ColorExComboBox();
        gridColor.setSelectedColor(_imagePreviewList.getGridForeground());
        gridColor.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem() instanceof Color) {
                        _imagePreviewList.setGridForeground((Color) e.getItem());
                    }
                }
            }
        });
        panel.add(gridColor);
        panel.add(Box.createVerticalStrut(4));
        panel.add(new JLabel("Grid Background Color: "));
        ColorExComboBox gridBackground = new ColorExComboBox();
        gridBackground.setSelectedColor(_imagePreviewList.getGridBackground());
        gridBackground.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem() instanceof Color) {
                        _imagePreviewList.setGridBackground((Color) e.getItem());
                    }
                }
            }
        });
        panel.add(gridBackground);
        panel.add(Box.createVerticalStrut(4));
        panel.add(new JLabel("Selection Background Color: "));
        ColorExComboBox highlightBackground = new ColorExComboBox();
        Color highlight = _imagePreviewList.getHighlightBackground();
        highlightBackground.setSelectedColor(new Color(highlight.getRGB() & 0xFFFFFF));
        highlightBackground.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem() instanceof Color) {
                        Color newColor = (Color) e.getItem();
                        _imagePreviewList.setHighlightBackground(new Color(newColor.getRGB() & 0x64FFFFFF, true));
                    }
                }
            }
        });
        panel.add(highlightBackground);
        panel.add(Box.createVerticalStrut(4));
        panel.add(Box.createGlue());
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "G22.ImagePreviewList";
    }

    static public void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new ImagePreviewListDemo());
            }
        });
    }
}
