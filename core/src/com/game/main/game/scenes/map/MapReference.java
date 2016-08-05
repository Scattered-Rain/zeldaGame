package com.game.main.game.scenes.map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
/** Keeps Reference to the actual map assets */
public enum MapReference {
	WORLD("worldTest");
	
	/** The map's path */
	private String name;
	
	/** Returns the complete Path of this Map */
	public String getPath(){
		return "maps/"+name+".tmx";
	}
	
}
