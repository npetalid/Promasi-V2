/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.WebBrowser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.promasi.client_swing.components.HtmlCellRenderer;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.application.Employee;
import org.promasi.game.IGame;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.game.company.MarketPlaceMemento;

/**
 * @author alekstheod
 *
 */
public class MarketPlaceJPanel extends JPanel implements IMarketPlaceListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final String CONST_SITENAME = "Marketplace";
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private JList _employeesList;
	
	/**
	 * 
	 * @param game
	 */
	public MarketPlaceJPanel( IGame game )throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_employeesList = new JList();
		JScrollPane scrollPane = new JScrollPane(_employeesList);
		
		_employeesList.setCellRenderer(new HtmlCellRenderer());
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel marketPlaceMenu = new JPanel();
		marketPlaceMenu.setLayout(new BorderLayout());
		
		JButton hireButton = new JButton("Hire");
		hireButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( !_employeesList.isSelectionEmpty() ){
					Employee employee = (Employee)_employeesList.getSelectedValue();
					_game.hireEmployee(employee.getEmployeeMemento().getEmployeeId());
				}
			}
		});
		
		marketPlaceMenu.add(hireButton, BorderLayout.EAST);
		
		add( marketPlaceMenu, BorderLayout.SOUTH );
		
		_game = game;
		_game.addMarketPlaceListener(this);
	}
	
	@Override
	public void MarketPlaceChanged(final MarketPlaceMemento marketPlace) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if( marketPlace != null && marketPlace.getAvailableEmployees() != null ){
					Vector<Employee> dataSet = new Vector<Employee>();
					for(Map.Entry<String,EmployeeMemento> entry : marketPlace.getAvailableEmployees().entrySet() ){
						if( entry.getValue() !=null && entry.getValue().getEmployeeId() != null ){
							try {
								dataSet.add(new Employee(entry.getValue()));
							} catch (GuiException e) {
								// TODO log
							}
						}
					}
					
					_employeesList.setListData(dataSet);
				}
			}
		});

	}

}
