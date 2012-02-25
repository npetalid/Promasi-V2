package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements IMainFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Panel shown on the main window.
	 */
	private JPanel _panel;
	
	/**
	 * Constructor
	 * @param caption
	 */
	public MainFrame( String caption ){
		super(caption);
	}

	@Override
	public void changePanel(JPanel panel) {
		if ( panel != null ){
			if( _panel != null ){
				this.remove(_panel);
				_panel.removeAll();
			}
			
			_panel = panel;
		}
		
		this.add(_panel, BorderLayout.CENTER);
		this.validate();
		this.repaint();
	}

	@Override
	public void closeMainFrame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maximize() {
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	@Override
	public void enablePlayingGameMode() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		setBounds(new Rectangle(screenSize.width/10, screenSize.height/10, screenSize.width - screenSize.width/5, screenSize.height- screenSize.height/5));
	}

	@Override
	public void enableWizardMode() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		setBounds(new Rectangle(screenSize.width/2 - screenSize.width/4, screenSize.height/2 - screenSize.height/4, screenSize.width/2, screenSize.height/2));
	}
}
