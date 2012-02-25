/**
 * 
 */
package org.promasi.client_swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.MainFrame;
import org.promasi.client_swing.gui.PlayModesJPanel;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;


import GameBuilder.GameMaker;

/**
 * @author alekstheod
 *
 */
public class Application {
	
	/**
	 * Logger
	 */
	private static final ILogger CONST_LOGGER = LoggerFactory.getInstance(Application.class);
	
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		com.jidesoft.utils.Lm.verifyLicense("Alex Theodoridis", "ProMaSi", "BYEuilHJx9N.HdDrNJDzRmot.sJAFQF2");
		CONST_LOGGER.info("Start application");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				try {
					
					try{
			            // set your theme
						//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					    /*for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					        if ("Nimbus".equals(info.getName())) {
					            UIManager.setLookAndFeel(info.getClassName());
					            break;
					        }
					    }*/
			            
			            //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					} catch (Exception e) {
					    // If Nimbus is not available, you can set the GUI to another look and feel.
					}
					
					GameMaker.makeGame();
	                // Create a JFrame, which is a Window with "decorations", i.e.
	                // title, border and close-button
	                MainFrame mainFrame = new MainFrame("ProMaSi");
	                
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
	                mainFrame.enableWizardMode();
	                mainFrame.setLayout(new BorderLayout());
	                
	        		JPanel panel;
					panel = new PlayModesJPanel( mainFrame );
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
