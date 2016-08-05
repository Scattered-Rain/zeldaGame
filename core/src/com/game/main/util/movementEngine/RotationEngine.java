package com.game.main.util.movementEngine;

import com.game.main.util.Point;

import lombok.Getter;
import lombok.Setter;

/** Object that keeps track of rotation */
public class RotationEngine extends MovementEngine{
	
	/** Initial rotation of the RotationEngine (between 0 and 1) */
	private float initialRotation;
	
	/** The time a single rotation takes to complete in seconds */
	private float speed;
	
	/** The radius of the rotation in pixels */
	@Getter private float radius;
	
	/** Rotational progression of the circle, 0 being 0 degrees and 1 being 360 degrees. Indipendant of initial rotation */
	@Getter private float currentRotation;
	
	
	/** Constructs new Rotation Engine */
	public RotationEngine(float initialRotation, float speed, float radius){
		this.initialRotation = initialRotation;
		this.speed = speed;
		this.radius = radius;
		this.currentRotation = 0;
		if(initialRotation==0){
			initialRotation = Float.MIN_VALUE;
		}
	}
	
	/** Constructs new Rotation Engine */
	public RotationEngine(float speed, float radius){
		this((float)Math.random(), speed, radius);
	}
	
	
	/** Returns current Point of rotation */
	@Override public Point calcCurrentLocation(Point origin, float delta){
		this.currentRotation += delta;
		float relRota = (float)Math.toRadians((((initialRotation+currentRotation)/speed)*360)%360);
		float x = (float)Math.cos(relRota)*radius;
		float y = (float)Math.sin(relRota)*radius;
		return origin.clone().add(x, y);
	}
	
}
