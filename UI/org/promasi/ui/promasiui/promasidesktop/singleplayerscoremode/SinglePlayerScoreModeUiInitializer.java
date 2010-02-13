package org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode;


import org.promasi.shell.UiManager;
import org.promasi.shell.playmodes.singleplayerscoremode.SinglePlayerScorePlayMode;
import org.promasi.shell.ui.playmode.IPlayUiModeInitializer;
import org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode.projectFinishedUi.SinglePlayerScoreModeProjectFinishedUi;


/**
 * 
 * Initializes all the components of the {@link SinglePlayerScorePlayMode}.
 * 
 * @author eddiefullmetal
 * 
 */
public class SinglePlayerScoreModeUiInitializer
        implements IPlayUiModeInitializer
{

    @Override
    public void registerLoginUi ( )
    {
        UiManager.getInstance( ).registerLoginUi( SinglePlayerScorePlayMode.class, new SinglePlayerScoreModeLoginUi( ) );
    }

    @Override
    public void registerProjectFinishedUi ( )
    {
        UiManager.getInstance( ).registerProjectFinishedUi( SinglePlayerScorePlayMode.class, new SinglePlayerScoreModeProjectFinishedUi( ) );
    }
}
