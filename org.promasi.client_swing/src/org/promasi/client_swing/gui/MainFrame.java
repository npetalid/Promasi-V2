package org.promasi.client_swing.gui;

import java.awt.BorderLayout;

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
}
