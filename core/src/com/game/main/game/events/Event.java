package com.game.main.game.events;

/** Represents an Event */
public abstract class Event{
	
	/** Get the Type of Event this is */
	public EventType getType(){
		return EventType.DEFAULT;
	}
	
	/** Returns whether the given Type is this Event's Event Type */
	public final boolean isOfType(EventType type){
		return getType().equals(type);
	}
	
}
