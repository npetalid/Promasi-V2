/**
 * 
 */
package org.promasi.desktop_swing;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.DateTime;

/**
 * @author alekstheod
 *
 */
public class ClockJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JLabel _clockLabel;

	/**
	 * 
	 */
	public ClockJPanel(){
		super();
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
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
