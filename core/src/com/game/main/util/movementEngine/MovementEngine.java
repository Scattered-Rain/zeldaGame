package com.game.main.util.movementEngine;

import com.game.main.util.Point;

import lombok.Getter;

public abstract class MovementEngine {
	
	/** Returns current Point of movement */
	public abstract Point calcCurrentLocation(Point origin, float delta);
	
}
