package com.game.main.util.movementEngine;

import com.game.main.util.Direction;
import com.game.main.util.Point;

/** Movment based on the standard directions */
public class DirectionalMovementEngine extends MovementEngine{
	
	/** The Direction this movment goes towards */
	private Direction dir;
	
	/** Amount of pixels traveled per second */
	private float speed;
	
	/** Current state of the movement (pixels traveled) */
	private float progression;
	
	
	/** Constructs new Directional Movement Engine */
	public DirectionalMovementEngine(Direction dir, float speed){
		this.dir = dir;
		this.speed = speed;
		this.progression = 0;
	}
	
	
	/** Returns current Point of movement */
	@Override public Point calcCurrentLocation(Point origin, float delta) {
		this.progression += delta*speed;
		return origin.clone().add(dir.getDir().multiply(progression));
	}

}
