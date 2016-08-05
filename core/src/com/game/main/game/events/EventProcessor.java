package com.game.main.game.events;

/** Interface that quarantees that this object handles Events */
public interface EventProcessor {
	
	/** Processing the given Event */
	public void processEvent(Event event);
	
}
