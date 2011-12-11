/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import org.promasi.client_swing.components.JList.CheckBoxCellRenderer;
import org.promasi.client_swing.components.JList.CheckBoxListEntry;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.IDepartmentListener;

/**
 * @author alekstheod
 *
 */
public class EmployeesJPanel extends JPanel implements IDepartmentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JList _employeesList;
	
	/**
	 * 
	 */
	private Map<String, CheckBoxListEntry> _employees;
	
	/**
	 * 
	 * @param game
	 */
	public EmployeesJPanel(IGame game)throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		setLayout(new BorderLayout());
		_employeesList= new JList();
		_employees= new TreeMap<String, CheckBoxListEntry>();
		_employeesList.setCellRenderer(new CheckBoxCellRenderer());
		_employeesList.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {	}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CheckBoxListEntry entry = (CheckBoxListEntry)_employeesList.getSelectedValue();
				entry.onClick();
				_employeesList.repaint();
			}
		});
		
		add(_employeesList, BorderLayout.CENTER);
		setBorder(BorderFactory.createTitledBorder("Employees"));
		game.addDepartmentListener(this);
	}

	/**
	 * 
	 * @param employees
	 */
	private void updateEmployeeList( final Map<String, EmployeeMemento> employees ){
		if( employees != null ){
			_employees.clear();
			Vector<CheckBoxListEntry> dataSet = new Vector<CheckBoxListEntry>();
			for(Map.Entry<String,EmployeeMemento> entry : employees.entrySet() ){
				if( entry.getValue() !=null && entry.getValue().getEmployeeId() != null ){
					CheckBoxListEntry newEntry = new CheckBoxListEntry(entry.getValue(), entry.getValue().getCurriculumVitae());
					dataSet.add(newEntry);
					_employees.put(entry.getKey(), newEntry);
				}
			}
			
			_employeesList.setListData(dataSet);
		}
	}
	
	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void departmentAssigned(String director, DepartmentMemento department) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, EmployeeMemento> getSelectedEmployees(){
		Map<String, EmployeeMemento> result = new TreeMap<String, EmployeeMemento>();
		
		for(Map.Entry<String, CheckBoxListEntry> entry : _employees.entrySet()){
			if( entry.getValue().isSelected() ){
				result.put(entry.getKey(), (EmployeeMemento)entry.getValue().getObject());
			}
		}
		
		return result;
	}
}
