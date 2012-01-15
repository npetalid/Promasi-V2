/*
 * @(#)FilterDemo.java 1/1/2012
 *
 * Copyright 2002 - 2012 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.icons.ColorFilter;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.icons.TintFilter;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import java.awt.*;

public class ImageFilterDemo extends AbstractDemo {
    private static final long serialVersionUID = -2632476113931681263L;

    public String getName() {
        return "ImageFilter Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_COMMON;
    }

    @Override
    public String getDescription() {
        return "This demo shows you several ImageFilter classes.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.icons.ColorFilter\n" +
                "com.jidesoft.icons.GrayFilter\n" +
                "com.jidesoft.icons.TintFilter";
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
        ImageIcon icon = IconsFactory.getImageIcon(ImageFilterDemo.class, "icons/save.png");
        panel.add(new JLabel("Original image", icon, JLabel.LEFT));
        panel.add(new JLabel("Brighter image", new ImageIcon(ColorFilter.createBrighterImage(icon.getImage())), JLabel.LEFT));
        panel.add(new JLabel("Darker image", new ImageIcon(ColorFilter.createDarkerImage(icon.getImage())), JLabel.LEFT));
        panel.add(new JLabel("Disabled image", new ImageIcon(GrayFilter.createDisabledImage(icon.getImage())), JLabel.LEFT));
        panel.add(new JLabel("Tinted Image (with red)", new ImageIcon(TintFilter.createTintedImage(icon.getImage(), Color.RED, new Insets(0, 0, 0, 0))), JLabel.LEFT));
        panel.add(new JLabel("Tinted Image (with green)", new ImageIcon(TintFilter.createTintedImage(icon.getImage(), Color.GREEN, new Insets(0, 0, 0, 0))), JLabel.LEFT));
        return panel;
    }

    @Override
    public String getDemoFolder() {
        return "B2.IconsFactory";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new ImageFilterDemo());
            }
        });
    }
}
