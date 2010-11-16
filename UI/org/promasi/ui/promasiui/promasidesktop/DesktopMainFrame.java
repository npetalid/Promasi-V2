package org.promasi.ui.promasiui.promasidesktop;

import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.game.IGame;
import org.promasi.ui.utilities.ScreenUtils;



/**
 * The main frame of the Promasi application.
 *
 * @author eddiefullmetal
 *
 */
public class DesktopMainFrame extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( DesktopMainFrame.class );

    /**
     * The main toolbar.
     */
    private DesktopToolbar _toolbar;

    /**
     * The desktop pane to show the program windows.
     */
    private JDesktopPane _desktopPane;

    /**
     *	System shell.
     */
    private IGame _game;

    /**
     * Initializes the object.
     * @throws IOException 
     */
    public DesktopMainFrame(IGame game )throws NullArgumentException
    {
    	if(game==null)
    	{
    		throw new NullArgumentException("Wrong argument game==null");
    	}
    	
    	_game=game;
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "PRO.MA.SI" );
        setSize( ScreenUtils.sizeForPercentage( 1, 1 ) );
        setUndecorated( true );
        ScreenUtils.centerInScreen( this );
        
        _desktopPane = new JDesktopPane( );
        _toolbar = new DesktopToolbar(_desktopPane,_game );
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _toolbar, new CC( ).dockNorth( ) );
        add( _desktopPane, new CC( ).grow( ) );
        _game.startGame();
    }
}
