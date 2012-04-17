/**
 * 
 */
package org.promasi.client_swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.promasi.client_swing.gui.MainFrame;
import org.promasi.client_swing.gui.PlayModesJPanel;
import org.promasi.sdsystem.ISdSystem;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;
import org.promasi.sdsystem.sdobject.equation.CalculatedEquation;
import org.promasi.utilities.clock.IClock;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utilities.spring.SpringApplicationContext;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class Application {
	
	/**
	 * Logger
	 */
	private static final ILogger _logger = LoggerFactory.getInstance(Application.class);
	
	public static void run(){
		com.jidesoft.utils.Lm.verifyLicense("Alex Theodoridis", "ProMaSi", "BYEuilHJx9N.HdDrNJDzRmot.sJAFQF2");
		_logger.info("Start application");
		try {
			SpringApplicationContext context = SpringApplicationContext.getInstance("promasi_beans.xml");
			IClock clock = context.getBean(IClock.class);
			clock.getCurrentDateTime();
			
			Map<String, ISdObject> objects = new TreeMap<String, ISdObject>();
			objects.put("tets", new StockSdObject(new CalculatedEquation("1"), 0.0));
			ISdSystem sdSystem = context.getBean("SdSystem", new Object[] {objects});
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				try {
					
					try{
			            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					} catch (Exception e) {
						_logger.warn("Theme applying failed");
					}

	                MainFrame mainFrame = new MainFrame("ProMaSi");
	                mainFrame.setLayout(new FlowLayout());
	                mainFrame.pack();
	                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                mainFrame.enableWizardMode();
	                mainFrame.setLayout(new BorderLayout());
	                
	        		JPanel panel;
					panel = new PlayModesJPanel( mainFrame );
	        		mainFrame.changePanel(panel);
	                mainFrame.setVisible(true);
				} catch (GuiException e) {
					e.printStackTrace();
				}
            }
        });
	}
	
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		run();
	}

}
