package org.promasi.ui.exportwizard.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author antoxron
 *
 */
public class GameStories {
	
	List<GameStory> _gameStories;
	
	public GameStories() {
		_gameStories = new ArrayList<GameStory>();
	}
	
	public void setGameStories(List<GameStory> gameStories) {
		_gameStories = gameStories;
	}
	public List<GameStory> getGameStories() {
		return _gameStories;
	}
	
	public void addGameStory(GameStory gameStory) {
		_gameStories.add(gameStory);
	}

}
