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
     * Initializes the object.
     */
    public PlayModePool(Shell shell)throws NullArgumentException, IllegalArgumentException
    {
    	if( shell==null)
    	{
    		throw new NullArgumentException("Wrong argument");
    	}

        _playModes = new Vector<IPlayMode>( );
        
        registerPlayMode( new SinglePlayerScorePlayMode(shell));
        
        try {
			registerPlayMode( new MultiPlayerScorePlayMode(shell) );
		} 
        catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

}
