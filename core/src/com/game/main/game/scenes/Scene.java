package com.game.main.game.scenes;

import com.game.main.game.events.EventProcessor;

/** Represents a Scene in the Game */
public abstract class Scene implements EventProcessor{
	
	/** Initializes the Scene (if necessary) */
	public abstract void init();
	
	/** Update the Scene's logic */
	public abstract void update(float delta);
	
	/** Renders the Scene */
	public abstract void render();
	
	/** Dispose this Scene */
	public abstract void dispose();
	
}
