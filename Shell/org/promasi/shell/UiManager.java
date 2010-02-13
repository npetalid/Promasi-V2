package org.promasi.shell;


import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.promasi.shell.ui.IMainFrame;
import org.promasi.shell.ui.IUiInitializer;
import org.promasi.shell.ui.playmode.ILoginUi;
import org.promasi.shell.ui.playmode.IPlayUiModeInitializer;
import org.promasi.shell.ui.playmode.IProjectFinishedUi;


/**
 * 
 * Class contains all register components for a play mode.
 * 
 * @author eddiefullmetal
 * 
 */
public final class UiManager
{

    /**
     * Contains all the registered {@link ILoginUi}s for a {@link IPlayMode}.
     */
    private Map<Class<? extends IPlayMode>, ILoginUi> _registerLoginUis;

    /**
     * Contains all the registered {@link IProjectFinishedUi}s for a
     * {@link IPlayMode}.
     */
    private Map<Class<? extends IPlayMode>, IProjectFinishedUi> _registerProjectFinishedUis;

    /**
     * Contains all the registered {@link IPlayUiModeInitializer}s s for a
     * {@link IPlayMode}.
     */
    private Map<Class<? extends IPlayMode>, IPlayUiModeInitializer> _registerPlayModeInitializers;

    /**
     * The {@link IMainFrame} for the current UI implementation.
     */
    private IMainFrame _registeredMainFrame;

    /**
     * The {@link IUiInitializer} for the current UI implementation.
     */
    private IUiInitializer _uiInitializer;

    /**
     * Singleton instance.
     */
    private static UiManager INSTANCE;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( UiManager.class );

    /**
     * Initializes the object.
     */
    private UiManager( )
    {
        _registerLoginUis = new Hashtable<Class<? extends IPlayMode>, ILoginUi>( );
        _registerPlayModeInitializers = new Hashtable<Class<? extends IPlayMode>, IPlayUiModeInitializer>( );
        _registerProjectFinishedUis = new Hashtable<Class<? extends IPlayMode>, IProjectFinishedUi>( );
    }

    /**
     * @param clazz
     *            The class to get the {@link IPlayUiModeInitializer} for.
     * @return The {@link IPlayUiModeInitializer} for the specified clazz or
     *         null if no registered {@link IPlayUiModeInitializer} exists.
     */
    public IPlayUiModeInitializer getPlayModeInitializer ( Class<? extends IPlayMode> clazz )
    {
        return _registerPlayModeInitializers.get( clazz );
    }

    /**
     * Registers an {@link IPlayUiModeInitializer} for an {@link IPlayMode}
     * class.
     * 
     * @param clazz
     *            The class to register the initializer for.
     * @param initializer
     *            The initializer to register.
     */
    public void registerPlayModeInitializer ( Class<? extends IPlayMode> clazz, IPlayUiModeInitializer initializer )
    {
        if ( !_registerPlayModeInitializers.containsKey( clazz ) )
        {
            LOGGER.info( "Registering play mode initializer " + initializer.getClass( ).getCanonicalName( ) + " for " + clazz.getSimpleName( ) );
            _registerPlayModeInitializers.put( clazz, initializer );
        }
    }

    /**
     * Registers an {@link ILoginUi} for an {@link IPlayMode} class.
     * 
     * @param clazz
     *            The class to register the login pane for.
     * @param loginUi
     *            The login pane to register.
     */
    public void registerLoginUi ( Class<? extends IPlayMode> clazz, ILoginUi loginUi )
    {
        if ( !_registerLoginUis.containsKey( clazz ) )
        {
            LOGGER.info( "Registering login pane " + loginUi.getClass( ).getCanonicalName( ) + " for " + clazz.getSimpleName( ) );
            _registerLoginUis.put( clazz, loginUi );
        }
    }

    /**
     * Registers an {@link IProjectFinishedUi} for an {@link IPlayMode} class.
     * 
     * @param clazz
     *            The class to register the projectFinished UI for.
     * @param projectFinishedUi
     *            The {@link IProjectFinishedUi} to register.
     */
    public void registerProjectFinishedUi ( Class<? extends IPlayMode> clazz, IProjectFinishedUi projectFinishedUi )
    {
        if ( !_registerProjectFinishedUis.containsKey( clazz ) )
        {
            LOGGER.info( "Registering login pane " + projectFinishedUi.getClass( ).getCanonicalName( ) + " for " + clazz.getSimpleName( ) );
            _registerProjectFinishedUis.put( clazz, projectFinishedUi );
        }
    }

    /**
     * @param clazz
     *            The class to get the {@link ILoginUi} for.
     * @return The {@link ILoginUi} for the specified clazz or null if no
     *         registered {@link ILoginUi} exists.
     */
    public ILoginUi getLoginUi ( Class<? extends IPlayMode> clazz )
    {
        return _registerLoginUis.get( clazz );
    }

    /**
     * @param clazz
     *            The class to get the {@link IProjectFinishedUi} for.
     * @return The {@link IProjectFinishedUi} for the specified clazz or null if
     *         no registered {@link IProjectFinishedUi} exists.
     */
    public IProjectFinishedUi getProjectFinishedUi ( Class<? extends IPlayMode> clazz )
    {
        return _registerProjectFinishedUis.get( clazz );
    }

    /**
     * @return the {@link #_registeredMainFrame}.
     */
    public IMainFrame getRegisteredMainFrame ( )
    {
        return _registeredMainFrame;
    }

    /**
     * @param registeredMainFrame
     *            the {@link #_registeredMainFrame} to set.
     */
    public void setRegisteredMainFrame ( IMainFrame registeredMainFrame )
    {
        _registeredMainFrame = registeredMainFrame;
    }

    /**
     * @return the {@link #_uiInitializer}.
     */
    public IUiInitializer getUiInitializer ( )
    {
        return _uiInitializer;
    }

    /**
     * @param uiInitializer
     *            the {@link #_uiInitializer} to set.
     */
    public void setUiInitializer ( IUiInitializer uiInitializer )
    {
        _uiInitializer = uiInitializer;
    }

    /**
     * @return The {@link #INSTANCE}.
     */
    public static UiManager getInstance ( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new UiManager( );
        }
        return INSTANCE;
    }
}
