package org.promasi.game;

import java.util.List;


/**
 * 
 * @author alekstheod
 *
 */
public interface IGamesServerListener {
	void updateGamesList( List<IGame> games);
	void onJoinGame( IGame game );
}
