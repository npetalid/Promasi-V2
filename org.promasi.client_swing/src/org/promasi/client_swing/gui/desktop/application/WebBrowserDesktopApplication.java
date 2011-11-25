/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application;

import java.io.IOException;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class WebBrowserDesktopApplication extends ADesktopApplication implements ICompanyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String CONST_APPNAME = "WebBrowser";
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "internet.png";
	
	private IGame _game;
	
	/**
	 * 
	 * @throws GuiException
	 * @throws IOException 
	 */
	public WebBrowserDesktopApplication( IGame game ) throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_game = game;
		_game.addCompanyListener(this);
	}

	@Override
	public void projectAssigned(String owner, CompanyMemento company,
			ProjectMemento project) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyMemento company,
			ProjectMemento assignedProject) {
		// TODO Auto-generated method stub
		
	}
}
