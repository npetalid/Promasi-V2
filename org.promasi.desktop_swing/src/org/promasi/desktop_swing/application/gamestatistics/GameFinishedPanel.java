/**
 * 
 */
package org.promasi.desktop_swing.application.gamestatistics;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.promasi.game.company.CompanyMemento;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 * Represents the last panel which will be shown to the
 * user after the game is finished.
 */
public class GameFinishedPanel extends JXPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor 
	 */
	public GameFinishedPanel(Map<String, CompanyMemento> players)throws GuiException{
		String[] columnNames = {"Name","Reputation","Budget"};
		
		DefaultTableModel model = new DefaultTableModel();
		
		JTable table = new JTable(model);
		model.setColumnIdentifiers(columnNames);
		
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(true);
		
		setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
		for( Map.Entry<String, CompanyMemento> entry : players.entrySet() ){
			model.addRow(new Object[] {entry.getKey(), entry.getValue().getPrestigePoints(), entry.getValue().getBudget()});
		}
	}
}
