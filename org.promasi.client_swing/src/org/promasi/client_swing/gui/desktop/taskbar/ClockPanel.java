/**
 * 
 */
package org.promasi.client_swing.gui.desktop.taskbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.DateTime;

/**
 * @author alekstheod
 *
 */
public class ClockPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_CLOCK_PANEL_WIDTH = 120;
	
	/**
	 * 
	 */
	private JLabel _clockLabel;

	/**
	 * 
	 */
	public ClockPanel(){
		super();
		setBackground( Color.gray );
		setPreferredSize( new Dimension(CONST_CLOCK_PANEL_WIDTH, 100) );
		setLayout(new BorderLayout());
		_clockLabel = new JLabel();
		add(_clockLabel, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @param date
	 */
	public void updateTime(DateTime dateTime){
		if( dateTime != null ){
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime.toDate());
			SimpleDateFormat dateFromat = new SimpleDateFormat("dd MMMMM kk");
			_clockLabel.setText( dateFromat.format(calendar.getTime() ) );
		}
	}
}
