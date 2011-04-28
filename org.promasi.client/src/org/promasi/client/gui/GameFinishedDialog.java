package org.promasi.client.gui;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.promasi.game.SerializableGameModel;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * 
 * @author m1cRo
 *	This dialog will inform the user about last played game statistics.
 */
public class GameFinishedDialog extends Dialog {

	protected Object result;
	protected Shell _shell;
	private Table _table;
	private List<IDialogListener> _listeners;
	
	private String _playerId;
	private SerializableGameModel _gameModel;
	private Map<String, SerializableGameModel> _otherPlayerModels;
	
	/**
	 * 
	 * @param parent
	 * @param style
	 * @param playerId
	 * @param gameModel
	 * @param otherPlayerModels
	 * @throws NullArgumentException
	 */
	public GameFinishedDialog(Shell parent, int style, String playerId, SerializableGameModel gameModel, Map<String, SerializableGameModel> otherPlayerModels )throws NullArgumentException {
		super(parent, style);
		setText("SWT Dialog");
		_listeners=new LinkedList<IDialogListener>();
		
		if(playerId==null){
			throw new NullArgumentException("Wrong argument playerId==null");
		}
		
		if(gameModel==null){
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		if(otherPlayerModels==null){
			throw new NullArgumentException("Wrong argument otherPlayersModels==null");
		}
		
		_playerId=playerId;
		_gameModel=gameModel;
		_otherPlayerModels=otherPlayerModels;
		createContents();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					_shell.open();
					_shell.layout();
					Display display = getParent().getDisplay();
					while (!_shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
				}
			});
		}
		
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		_shell = new Shell(getParent(), getStyle());
		_shell.setSize(817, 552);
		_shell.setText("Game Statistics");
		
		TabFolder _tasksTab = new TabFolder(_shell, SWT.BORDER | SWT.BOTTOM);
		
		GroupLayout gl_shlGameStatistics = new GroupLayout(_shell);
		gl_shlGameStatistics.setHorizontalGroup(
			gl_shlGameStatistics.createParallelGroup(GroupLayout.LEADING)
				.add(GroupLayout.TRAILING, gl_shlGameStatistics.createSequentialGroup()
					.addContainerGap()
					.add(gl_shlGameStatistics.createParallelGroup(GroupLayout.TRAILING)
						.add(_tasksTab, GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_shlGameStatistics.setVerticalGroup(
			gl_shlGameStatistics.createParallelGroup(GroupLayout.TRAILING)
				.add(gl_shlGameStatistics.createSequentialGroup()
					.addContainerGap()
					.addPreferredGap(LayoutStyle.RELATED, 29, Short.MAX_VALUE)
					.add(_tasksTab, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		TabItem tbtmOverallProgress = new TabItem(_tasksTab, SWT.NONE);
		tbtmOverallProgress.setText("Overall Progress");
		
		_table = new Table(_tasksTab, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmOverallProgress.setControl(_table);
		_table.setHeaderVisible(true);
		_table.setLinesVisible(true);
		
		TableColumn tblclmnPlayer = new TableColumn(_table, SWT.NONE);
		tblclmnPlayer.setWidth(147);
		tblclmnPlayer.setText("Player");
		
		TableColumn tblclmnBadget = new TableColumn(_table, SWT.NONE);
		tblclmnBadget.setWidth(115);
		tblclmnBadget.setText("Budget");
		
		TableColumn tblclmnPrestigePoints = new TableColumn(_table, SWT.NONE);
		tblclmnPrestigePoints.setWidth(137);
		tblclmnPrestigePoints.setText("Prestige Points");
		
		TableColumn tblclmnPercentageComplete = new TableColumn(_table, SWT.NONE);
		tblclmnPercentageComplete.setWidth(133);
		tblclmnPercentageComplete.setText("Percentage Complete");
		
		TabItem tbtmOverallDiagram = new TabItem(_tasksTab, SWT.NONE);
		tbtmOverallDiagram.setText("Overall Diagram");
		_shell.setLayout(gl_shlGameStatistics);
		
		TableItem tableItem=new TableItem(_table,SWT.NONE);
		tableItem.setText(0, _playerId);
		
		String prestigePoints=new String()+_gameModel.getCompany().getPrestigePoints();
		tableItem.setText(2, prestigePoints);
		
		String budget=new String()+_gameModel.getCompany().getBudget();
		tableItem.setText(1, budget);
		
		for(Map.Entry<String, SerializableGameModel> entry : _otherPlayerModels.entrySet()){
			TableItem tItem=new TableItem(_table,SWT.NONE);
			tItem.setText(0, entry.getKey());
			
			String prstgPoints=new String()+entry.getValue().getCompany().getPrestigePoints();
			tItem.setText(2, prstgPoints);
			
			String bdget=new String()+entry.getValue().getCompany().getBudget();
			tItem.setText(1, bdget);
		}
	}
	
	/**
	 * 
	 * @param listener
	 * @throws NullArgumentException 
	 */
	public synchronized boolean registerDialogListener(IDialogListener listener) throws NullArgumentException{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(_listeners.contains(listener)){
			return false;
		}
		
		_listeners.add(listener);
		return true;
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean removeDialogListener(IDialogListener listener) throws NullArgumentException{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(!_listeners.contains(listener)){
			return false;
		}
		
		_listeners.remove(listener);
		return true;
	}
}
