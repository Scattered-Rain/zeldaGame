package com.game.main.game.scenes.map.entity.common.animation;

import com.game.main.SpriteEnum;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.util.Direction;
import com.game.main.util.Point;
import com.game.main.util.movementEngine.DirectionalMovementEngine;
import com.game.main.util.movementEngine.MovementEngine;
import com.game.main.util.movementEngine.RotationEngine;

/** Animation Entity which spins in a circle */
public class CircleAnimationEntity extends AnimationEntity{
	
	/** Origin of this Animation */
	private Point origin;
	
	/** The Rotation */
	private MovementEngine rotation;
	
	
	/** Constructs New Animation */
	public CircleAnimationEntity(MapScene map, Point location, SpriteEnum sprite, Direction dir, float speed, float radius){
		super(map, location, sprite, dir);
		this.origin = location.clone();
		this.rotation = new RotationEngine(speed, radius);
	}
	
	/** Constructs new Animation, defaults to UP Direction in Sprite */
	public CircleAnimationEntity(MapScene map, Point location, SpriteEnum sprite, float speed, float radius){
		this(map, location, sprite, Direction.UP, speed, radius);
	}
	
	
	/** Once animation is over kill this Entity */
	@Override public void updateSpriteEntity(float delta){
		super.setLocation(rotation.calcCurrentLocation(origin, delta));
	}
	
}
