package org.promasi.client_swing.playmode;

import java.util.List;

import org.promasi.game.IGame;

/**
 * 
 * @author alekstheod
 *
 */
public interface IGamesServerListener {
	void updateGamesList( List<IGame> games);
	void onJoinGame( IGame game );
}
