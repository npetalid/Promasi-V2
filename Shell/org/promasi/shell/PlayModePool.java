package org.promasi.shell;


import java.util.List;
import java.util.Vector;

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
     * Singleton instance.
     */
    private static PlayModePool INSTANCE;

    /**
     * Initializes the object.
     */
    private PlayModePool( )
    {
        _playModes = new Vector<IPlayMode>( );
        loadDefaultPlayModes( );
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
     * @return the singleton instance.
     */
    public static PlayModePool getInstance ( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new PlayModePool( );
        }
        return INSTANCE;
    }

    /**
     * Registers all the default play modes.
     */
    private void loadDefaultPlayModes ( )
    {
        registerPlayMode( new SinglePlayerScorePlayMode( ) );
    }
}
