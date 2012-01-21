/**
 * 
 */
package org.promasi.client_swing.gui.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.promasi.client_swing.components.JList.MenuCellRenderer;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.application.EMail.EmailClientDesktopApplication;
import org.promasi.client_swing.gui.desktop.application.Scheduler.SchedulerDesktopApplication;
import org.promasi.client_swing.gui.desktop.application.Statistics.StatisticsApplication;
import org.promasi.client_swing.gui.desktop.application.WebBrowser.WebBrowserDesktopApplication;
import org.promasi.game.IGame;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class StartMenuJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_PREFERED_WIDTH = 200;
	
	/**
	 * 
	 */
	public static final int CONST_PREFERED_HEIGHT = 200;
	
	/**
	 * 
	 */
	private IDesktop _desktop;
	
	/**
	 * 
	 */
	private JList<ADesktopApplication> _appList;
	
	/**
	 * Constructor.
	 * @param desktop
	 */
	public StartMenuJPanel( IGame game, String username, IDesktop desktop )throws GuiException{
		super();
		
		if( desktop == null ){
			throw new GuiException( "Wrong argument desktop == null" );
		}
		
		if( username == null ){
			throw new GuiException( "Wrong argument username == null" );
		}
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		setPreferredSize(new Dimension(CONST_PREFERED_WIDTH, CONST_PREFERED_HEIGHT));
		_desktop = desktop;
		
		JLabel usernameLabel;
		try {
			Icon userIcon = new ImageIcon( RootDirectory.getInstance().getImagesDirectory() + "user.png" );
			usernameLabel = new JLabel(username, userIcon , SwingConstants.LEFT);
			usernameLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
			usernameLabel.setPreferredSize( new Dimension( CONST_PREFERED_WIDTH,  userIcon.getIconHeight() ) );
			add( usernameLabel, BorderLayout.NORTH );
		} catch (IOException ex) {
			throw new GuiException(ex.getMessage());
		}

		Vector<ADesktopApplication> apps = new Vector<ADesktopApplication>();
		try {
			apps.add(new SchedulerDesktopApplication( game, desktop ));
			apps.add(new EmailClientDesktopApplication(game, desktop ));
			apps.add(new WebBrowserDesktopApplication(game, desktop));
			apps.add(new StatisticsApplication(game, desktop));
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		_appList = new JList<ADesktopApplication>(apps);
		_appList.setCellRenderer(new MenuCellRenderer());
		_appList.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height ));
		add(_appList, BorderLayout.CENTER);
		
		_appList.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent mouseEvent) {
				Point p = new Point(mouseEvent.getX(),mouseEvent.getY());
				_appList.setSelectedIndex(_appList.locationToIndex(p));
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		_appList.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ADesktopApplication app = _appList.getSelectedValue();
				_appList.clearSelection();
				_desktop.runApplication(app);
			}
		});
	}

}
