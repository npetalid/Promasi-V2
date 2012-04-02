/**
 * 
 */
package org.promasi.desktop_swing.application.marketplace;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.promasi.desktop_swing.application.Employee;
import org.promasi.desktop_swing.application.EmployeesPanel;
import org.promasi.game.IGame;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.game.company.MarketPlaceMemento;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 * The market place panel represent the market
 * place on ProMaSi system. A player will be able
 * to hire employees by using this panel.
 */
public class MarketPlaceJPanel extends JPanel implements IMarketPlaceListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The marketplace panel name.
	 */
	public static final String CONST_SITENAME = "Marketplace";
	
	/**
	 * Instance of the {@link IGame} interface
	 * implementation needed in order to retrieve
	 * the information from the running game.
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private EmployeesPanel _employeesPanel;
	
	/**
	 * 
	 * @param game
	 */
	public MarketPlaceJPanel( IGame game )throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		setLayout(new BorderLayout());
		
		_employeesPanel = new EmployeesPanel();
		add(_employeesPanel, BorderLayout.CENTER);
		
		JPanel marketPlaceMenu = new JPanel();
		marketPlaceMenu.setLayout(new BorderLayout());
		
		JButton hireButton = new JButton("Hire Employee");
		hireButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Employee> employees = _employeesPanel.getSelectedEmployees();
				for( Object employee : employees){
					if( employee instanceof Employee){
						_game.hireEmployee(((Employee)employee).getEmployeeMemento().getEmployeeId());
					}
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
					
					_employeesPanel.updateList(dataSet);
				}
			}
		});

	}

}
