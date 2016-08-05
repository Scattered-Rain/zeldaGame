package com.game.main.game.scenes.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
/** Enum of all Keys that are used for defining Map properties */
public enum MapKeys{
	
	UNRENDERED("UNRENDERED"),
	GROUND("GROUND"),
	COLLISION("COLLISION"),
	SPAWN("SPAWN");
	
	/** The key used for identifying the attribute */
	@Getter private String key;
	
}
