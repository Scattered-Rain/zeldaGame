package com.game.main.game.scenes.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Class Containing all enums for purposes of representing damage */
public class Damage{
	
	@AllArgsConstructor
	/** Describes the types of damge */
	public static enum DamageType{
		
		EDGED(0),
		EXPLOSIVE(1),
		TOUCH(2),
		HEAL(3),
		FLAME(4),;
		
		/** The index of this damage Type */
		@Getter private int index;
	}
	
	@AllArgsConstructor
	/** Describes the source types of damage */
	public static enum DamageSource{
		
		PLAYER(0),
		ENEMY(1),
		NEUTRAL(2);
		
		/** The index of this damage Type */
		@Getter private int index;
	}
	
}
