package com.game.main.game.events;

import com.game.main.game.scenes.map.Damage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
/** Event Representing a unit of damge */
public class DamageEvent extends Event{
	
	/** The type of damage communicated */
	@Getter private Damage.DamageType damageType;
	/** The base amount of damage communicated */
	@Getter private int damage;
	/** The Source Type of the damage */
	@Getter private Damage.DamageSource damageSource;
	
	/** Get the Type of Event this is */
	public EventType getType(){
		return EventType.DAMAGE;
	}
	
}
