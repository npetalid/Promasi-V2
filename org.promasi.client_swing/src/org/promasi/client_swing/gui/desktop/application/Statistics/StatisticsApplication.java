package org.promasi.client_swing.gui.desktop.application.Statistics;

import java.io.IOException;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.IDesktop;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utilities.file.RootDirectory;

/**
 * 
 * @author alekstheod
 * Represent the company statistics application in
 * ProMaSi system. In the company statistics application 
 * user would be able to see all the statistics which are
 * related with his company such as company's budget, the running
 * project, company performance and so on.
 */
public class StatisticsApplication extends ADesktopApplication implements ICompanyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String CONST_APPNAME = "Company statistics";
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "monitor.png";
	
	/**
	 * 
	 * @param game
	 * @param desktop
	 * @throws GuiException
	 * @throws IOException 
	 */
	public StatisticsApplication(IGame game, IDesktop desktop) throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		if( game == null){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
		}
		
		game.addCompanyListener(this);
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
