package org.promasi.shell;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.shell.playmodes.multiplayermode.MultiPlayerScorePlayMode;
import org.promasi.shell.playmodes.singleplayerscoremode.SinglePlayerScorePlayMode;


/**
 *
 * Contains all available play modes. Singleton implementation.
 *
 * @author eddiefullmetal
 *
 */
public final class PlayModePool
{

    /**
     * All available play modes.
     */
    private List<IPlayMode> _playModes;
    
    /**
     * 
     */
    private String _hostname;
    
    /**
     * 
     */
    private int _port;

    /**
     * Initializes the object.
     */
    public PlayModePool(Shell shell ,String hostname, int port)throws NullArgumentException, IllegalArgumentException
    {
    	if( shell==null || hostname==null )
    	{
    		throw new NullArgumentException("Wrong argument");
    	}
    	
    	if( port < 0 ){
    		throw new IllegalArgumentException("Wrong argument port<0");
    	}
    	
    	_hostname=hostname;
    	_port=port;
        _playModes = new Vector<IPlayMode>( );
        loadDefaultPlayModes(shell );
    }

    /**
     * @return the {@link #_playModes}.
     */
    public List<IPlayMode> getPlayModes ( )
    {
        return _playModes;
    }

    /**
     * @param playMode
     *            The {@link IPlayMode} to add to the {@link #_playModes}.
     */
    public void registerPlayMode ( IPlayMode playMode )
    {
        _playModes.add( playMode );
    }

    /**
     * Registers all the default play modes.
     * @throws IOException 
     * @throws UnknownHostException 
     * @throws IllegalArgumentException 
     */
    private void loadDefaultPlayModes (Shell shell )throws IllegalArgumentException
    {
        registerPlayMode( new SinglePlayerScorePlayMode(shell));
        
        try {
			registerPlayMode( new MultiPlayerScorePlayMode(shell,_hostname,_port));
		} 
        catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
