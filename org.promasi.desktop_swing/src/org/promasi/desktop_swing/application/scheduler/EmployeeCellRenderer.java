/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;
import org.promasi.desktop_swing.application.Employee;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.HtmlPanel;

/**
 * @author alekstheod
 *
 */
public class EmployeeCellRenderer extends DefaultListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JXPanel _mainPanel;
	
	/**
	 * 
	 */
	private JLabel _salaryLabel;
	
	/**
	 * 
	 */
	private JPanel _salaryPanel;
	
	/**
	 * 
	 */
	private HtmlPanel _htmlPane;
	
	/**
	 * Background color for the selected state of
	 * the cell.
	 */
	private Color _selectedBgColor; 
	
	/**
	 * Background color for the simple state of the cell.
	 */
	private Color _bgColor;
	
	/**
	 * @throws GuiException 
	 * 
	 */
	public EmployeeCellRenderer() throws GuiException{
		_selectedBgColor = Colors.Orange.alpha(0.5f);
		_bgColor = Colors.Gray.alpha(0.5f);
		setOpaque(true);
		init();
	}
	
	/**
	 * 
	 */
	public EmployeeCellRenderer(Color bgColor, Color selectedBgColor)throws GuiException{
		if( bgColor == null ){
			throw new GuiException("Wrong argument bgColor == null");
		}
		
		if( selectedBgColor == null ){
			throw new GuiException("Wrong argument selectedBgColor == null");
		}
		
		_bgColor = bgColor;
		_selectedBgColor = selectedBgColor;
		init();
	}
	
	private void init() throws GuiException{
		_mainPanel = new JXPanel();
		_mainPanel.setLayout( new MigLayout( new LC( ).fill( ) ));
		_mainPanel.setOpaque(true);
		_htmlPane = new HtmlPanel();
		_htmlPane.setPreferredSize(new Dimension(100,200));
		_htmlPane.setOpaque(false);
		_htmlPane.setVisible(true);
		_htmlPane.setFocusable(true);
		_htmlPane.setAutoscrolls(true);
		_mainPanel.add(_htmlPane, new CC( ).spanX( ).grow( ).gapX( "30px", "0px" ).gapY("0px", "10px"));
		_salaryLabel = new JLabel();
		_salaryLabel.setOpaque(false);
		_salaryLabel.setBackground(new Color(255,255,255,0));
		_salaryPanel = new JPanel();
		_salaryPanel.setOpaque(false);
		_salaryPanel.setBackground(new Color(255,255,255,0));
		_salaryPanel.setLayout(new BorderLayout());
		_salaryPanel.add(_salaryLabel, BorderLayout.EAST);
		_salaryPanel.setBackground(_bgColor);
		_salaryLabel.setFont(new Font("Courier New", Font.PLAIN, 15));
		_mainPanel.add(_salaryPanel, new CC().spanX().grow().gap("0px", "0px"));
		_mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
		_mainPanel.setBackgroundPainter(getPainter(_bgColor));
	}
	
	private Painter<Component> getPainter(Color bgColor) {
	    MattePainter mp = new MattePainter(bgColor);
	    GlossPainter gp = new GlossPainter(Colors.White.alpha(0.3f),
	                                       GlossPainter.GlossPosition.TOP);
	    PinstripePainter pp = new PinstripePainter(Colors.Gray.alpha(0.2f),
	                                               45d);
	   return (new CompoundPainter<Component>(mp, pp, gp));
	}
	
	/**
	 * 
	 */
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
		_htmlPane.setText( value.toString() );
		if( value instanceof Employee){
			Employee employee = (Employee)value;
			_salaryLabel.setText("Salary : " + Double.toString(employee.getEmployeeMemento().getSalary()));
		}
		
		if( isSelected ){
			_mainPanel.setBackgroundPainter(getPainter(_selectedBgColor));
		}else{
			_mainPanel.setBackgroundPainter(getPainter(_bgColor));
		}
		
		return _mainPanel;
	}
}
