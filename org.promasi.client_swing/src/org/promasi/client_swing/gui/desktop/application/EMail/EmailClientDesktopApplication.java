/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.EMail;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.IDesktop;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;

import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class EmailClientDesktopApplication extends ADesktopApplication implements ICompanyListener, IDepartmentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String CONST_APPNAME = "Email";
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "gmail.png";
	
	/**
	 * 
	 */
	private QuickStartButton _quickStartButton;
	
	/**
	 * A list with received messages \see="Message"
	 */
	private JTable _messageTable;
	
	/**
	 * 
	 */
	private MessageTableModel _msgTableModel;
	
	/**
	 * Lock object.
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 * @throws GuiException
	 * @throws IOException 
	 */
	public EmailClientDesktopApplication( IGame game, IDesktop desktop ) throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_lockObject = new ReentrantLock();
		_game = game;
		_quickStartButton = new QuickStartButton(this, desktop);
		desktop.addQuickStartButton(_quickStartButton);
		setLayout(new BorderLayout());
		
		_messageTable = new JTable();
		_messageTable.setShowHorizontalLines(false);
		_messageTable.setShowVerticalLines(true);
		_messageTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		JLabel label = (JLabel)_messageTable.getTableHeader().getDefaultRenderer();
		label.setHorizontalAlignment(JLabel.LEFT);
		
		_msgTableModel =  new MessageTableModel( new Vector<Message>( ) );
		_messageTable.setModel( _msgTableModel );
		JScrollPane scrollpane = new JScrollPane(_messageTable);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		
		Dimension minSize = new Dimension(100, 50);
		splitPane.setMinimumSize(minSize);
		splitPane.setMinimumSize(minSize);
		splitPane.setLeftComponent(scrollpane);
		
		add(splitPane, BorderLayout.CENTER);
		_game.addCompanyListener(this);
		_game.addDepartmentListener(this);
	}

	@Override
	public void projectAssigned(final String owner, CompanyMemento company,
			ProjectMemento project, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					_lockObject.lock();
					Message msg = new Message("CEO", "Project assigned", dateTime, "");
					List< Message > messages = _msgTableModel.getMessages();
					messages.add(msg);
					_msgTableModel = new MessageTableModel(messages);
					_messageTable.setModel(_msgTableModel);
					_quickStartButton.showPopupNotifier("New project assigned", "Project assigned");
				} catch (GuiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					_lockObject.unlock();
				}
				
			}
		});
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_quickStartButton.showPopupNotifier("Employee discharged", "Employee discharged");
			}
		});
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_quickStartButton.showPopupNotifier("Employee hired", "Employee hired");
			}
		});
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void departmentAssigned(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}

}
