/**
 * 
 */
package org.promasi.desktop_swing.application;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.PainterFactory;
import org.promasi.utils_swing.components.RoundedJPanel;

/**
 * @author alekstheod
 * Represent the visual employees list.
 */
public class EmployeesPanel extends JXPanel {

	/**
	 * 
	 */
	private JList<Employee> _employeesList;
	
	/**
	 * 
	 */
	private RoundedJPanel _bgPanel;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @throws GuiException
	 */
	public EmployeesPanel() throws GuiException{
		setLayout(new BorderLayout());
		
		_employeesList = new JList<Employee>();
		_employeesList.setOpaque(false);
		_employeesList.setBackground(Colors.White.alpha(0f));
		_employeesList.setCellRenderer(new EmployeeCellRenderer());
		
		JScrollPane scrollPane = new JScrollPane(_employeesList);
		
		_bgPanel = new RoundedJPanel();
		_bgPanel.add(scrollPane);
		_bgPanel.setBackgroundPainter(PainterFactory.getInstance(PainterFactory.ENUM_PAINTER.Background));
		
		add(_bgPanel);
	}
	
	public List<Employee> getSelectedEmployees(){
		return _employeesList.getSelectedValuesList();
	}
	
	public boolean updateList( Vector<Employee> employees){
		boolean result = false;
		
		if( employees != null ){
			_employeesList.setListData(employees);
			result = true;
		}
		
		return result;
	}

}
