/**
 * 
 */
package org.promasi.client_swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.MainFrame;
import org.promasi.client_swing.gui.PlayModesPanel;

/**
 * @author alekstheod
 *
 */
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				try {
	                // Create a JFrame, which is a Window with "decorations", i.e.
	                // title, border and close-button
	                MainFrame mainFrame = new MainFrame("ProMaSi");
	 
	                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
	                
	                // Set a simple Layout Manager that arranges the contained
	                // Components
	                mainFrame.setLayout(new FlowLayout());
	 
	                // "Pack" the window, making it "just big enough".
	                mainFrame.pack();
	 
	                // Set the default close operation for the window, or else the
	                // program won't exit when clicking close button
	                //  (The default is HIDE_ON_CLOSE, which just makes the window
	                //  invisible, and thus doesn't exit the app)
	                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	                
	                Rectangle rect = new Rectangle(screenSize.width/2 - screenSize.width/4, screenSize.height/2 - screenSize.height/4, screenSize.width/2, screenSize.height/2);
	                mainFrame.setBounds(rect);
	 
	                mainFrame.setLayout(new BorderLayout());
	                
	        		JPanel panel;
					panel = new PlayModesPanel( mainFrame );
	        		mainFrame.changePanel(panel);
	                
	                // Set the visibility as true, thereby displaying it
	                mainFrame.setVisible(true);
				} catch (GuiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        });
	}

}
