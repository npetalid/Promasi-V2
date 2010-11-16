package org.promasi.ui.promasiui.promasidesktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.IGameEventHandler;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;

import org.promasi.ui.promasiui.promasidesktop.programs.marketplace.MarketPlaceFrame;
import org.promasi.ui.promasiui.promasidesktop.programsi.planner.PlannerFrame;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.utilities.file.RootDirectory;


/**
 * 
 * @author m1cRo
 *
 */
public class DesktopToolbar extends JToolBar implements IGameEventHandler
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_INTERNALFRAME_POS_X=0;
	
	/**
	 * 
	 */
	public static final int CONST_INTERNALFRAME_POS_Y=0;
	
	/**
	 * 
	 */
	public static final int CONST_INTERNALFRAME_WIDTH=800;
	
	/**
	 * 
	 */
	public static final int CONST_INTERNALFRAME_HIGH=600;

	/**
     * The {@link ClockButton}.
     */
    private ClockButton _clock;

    /**
     * The {@link ProjectInfoButton}.
     */
    private ProjectInfoButton _projectInfoButton;

    /**
     * 
     */
    private JButton _messagesButton;
    
    /**
     * 
     */
    private JButton _plannerButton;
    
    /**
     * 
     */
    private JButton _marketPlaceButton;
    
    /**
     * The panel that contains all the quick launch buttons.
     */
    private JPanel _quickLaunchPanel;

    /**
     * 
     */
    private JDesktopPane _parentPane;
    
    /**
     * 
     */
    private MarketPlaceFrame _marketPlaceFrame;
    
    /**
     * 
     */
    private SerializableProject _assignedProject;
    
    /**
     * 
     */
    private DateTime _projectAssigneDate;
    
    /**
     * 
     */
    private IGame _game;
    
    /**
     * Initializes the object.
     * @throws IOException 
     */
    public DesktopToolbar(JDesktopPane parentPane,IGame game )throws NullArgumentException
    {
    	if(parentPane==null)
    	{
    		throw new NullArgumentException("Wrong argument parentPane==null");
    	}
    	
    	if(game==null)
    	{
    		throw new NullArgumentException("Wrong argument game==null");
    	}
    	
    	_game=game;
    	_game.registerGameEventHandler(this);
    	_parentPane=parentPane;
        
        _clock = new ClockButton( _game );
        _quickLaunchPanel = new JPanel( );
        _quickLaunchPanel.setBorder( BorderFactory.createEmptyBorder( ) );
        _quickLaunchPanel.setLayout( new MigLayout( new LC( ) ) );
        _projectInfoButton = new ProjectInfoButton( _game );
        
		setupMessageTools();
		setupMarketPlaceTools();
		setupPlannerTools();
        
        setFloatable( false );
        setBorder( BorderFactory.createEtchedBorder( ) );

        //addQuickLaunch( new InfoGateProgram( _game ) );
        
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JLabel( ResourceManager.getString( DesktopToolbar.class, "quickLaunch" ) ), new CC( ).dockWest( ) );
        add( new ExitButton( ), new CC( ).dockEast( ) );
        add( _clock, new CC( ).dockEast( ) );
        add( _projectInfoButton, new CC( ).dockEast( ) );
        add( _quickLaunchPanel, new CC( ).grow( ) );
    }

    /**
     * 
     */
    private void setupMessageTools() {
    	_messagesButton=new JButton();
    	try{
    		Icon icon=new ImageIcon(RootDirectory.getInstance().getResourcesPath()+File.separator+"message.png");
    		_messagesButton.setIcon(icon);
    	}catch (IOException e) {
			//Logger
		}
    	
    	_quickLaunchPanel.add( _messagesButton, new CC( ) );
    }
    
    /**
     * 
     */
    private void setupMarketPlaceTools(){
        _marketPlaceButton=new JButton();
        _marketPlaceFrame=new MarketPlaceFrame(_game );
        _marketPlaceButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_parentPane.add(_marketPlaceFrame,JDesktopPane.LEFT_ALIGNMENT);
				_marketPlaceFrame.setVisible(true);
				_marketPlaceFrame.setBounds(CONST_INTERNALFRAME_POS_X, CONST_INTERNALFRAME_POS_Y,CONST_INTERNALFRAME_WIDTH,CONST_INTERNALFRAME_HIGH);
				_marketPlaceFrame.toFront();
			}
		});
        
		try {
			Icon icon = new ImageIcon(RootDirectory.getInstance().getResourcesPath()+File.separator+"marketplace.png");
			_marketPlaceButton.setIcon(icon);
		} catch (IOException e) {
			//Logger
		}
		
		_quickLaunchPanel.add( _marketPlaceButton, new CC());
    }
    
    private void setupPlannerTools(){
        _plannerButton=new JButton();

        _plannerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openPlannerFrame();
			}
		});
        
		try {
			Icon icon = new ImageIcon(RootDirectory.getInstance().getResourcesPath()+File.separator+"planner.png");
			_plannerButton.setIcon(icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        _quickLaunchPanel.add( _plannerButton, new CC());
    }
    
    /**
     * 
     */
    private synchronized void openPlannerFrame(){
    	if(_assignedProject!=null && _projectAssigneDate!=null){
    		PlannerFrame plannerFrame=new PlannerFrame(_parentPane, _game, _assignedProject,_projectAssigneDate);
    		_parentPane.add(plannerFrame,JDesktopPane.LEFT_ALIGNMENT);
    		plannerFrame.setVisible(true);
    		plannerFrame.setBounds(CONST_INTERNALFRAME_POS_X, CONST_INTERNALFRAME_POS_Y,CONST_INTERNALFRAME_WIDTH,CONST_INTERNALFRAME_HIGH);
    		plannerFrame.toFront();
    	}
    }

	@Override
	public synchronized void projectAssigned(SerializableCompany company, SerializableProject project, DateTime dateTime) {
		_assignedProject=project;
		_projectAssigneDate=dateTime;
	}

	@Override
	public synchronized void projectFinished(SerializableCompany company, SerializableProject project, DateTime dateTime) {
		_assignedProject=null;
		_projectAssigneDate=null;
	}

	@Override
	public void employeeHired(SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskAttached(SerializableCompany company,
			SerializableEmployee employee, SerializableEmployeeTask employeeTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskDetached(SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			SerializableEmployeeTask employeeTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPay(SerializableCompany company,
			SerializableEmployee employee, Double salary, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(SerializableCompany company,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(SerializableCompany company,
			SerializableProject assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}
    
}
