package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MainFrame extends JFrame implements IMainFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The status pannel of the ProMaSi application.
	 */
	private JPanel _statusPanel;

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
		_statusPanel = new JPanel();
		setLayout(new BorderLayout());
		_statusPanel.setPreferredSize(new Dimension(200,20));
		_statusPanel.setLayout(new BorderLayout());
		_statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		JLabel promasiLabel = new JLabel("Pro.Ma.Si");
		promasiLabel.setPreferredSize(new Dimension(200,200));
		_statusPanel.add(promasiLabel, BorderLayout.WEST);
	}

	@Override
	public void changePanel(JPanel panel) {
		if ( panel != null ){
			if( _panel != null ){
				this.remove(_panel);
				this.remove(_statusPanel);
				_panel.removeAll();
			}
			
			_panel = panel;
		}
		
		this.add(_panel, BorderLayout.CENTER);
		add(_statusPanel, BorderLayout.SOUTH);
		_statusPanel.setBackground(Color.gray);
		this.validate();
		this.repaint();
	}

	@Override
	public void closeMainFrame() {
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
		setBounds(new Rectangle(screenSize.width/2 - 320, screenSize.height/2 - 200, 640, 400));
	}
}
