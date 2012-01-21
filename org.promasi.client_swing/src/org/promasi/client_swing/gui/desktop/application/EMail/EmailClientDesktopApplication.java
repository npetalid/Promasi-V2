/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.EMail;

import java.io.IOException;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.IDesktop;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.project.ProjectMemento;

import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class EmailClientDesktopApplication extends ADesktopApplication implements ICompanyListener{

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
		
		_game = game;
		_game.addCompanyListener(this);
		desktop.addQuickStartButton(new QuickStartButton(this, desktop));
	}

	@Override
	public void projectAssigned(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
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

}
