package com.game.main.game.scenes.map.entity.abstracts;

import com.game.main.SpriteEnum;
import com.game.main.game.events.DamageEvent;
import com.game.main.game.events.Event;
import com.game.main.game.scenes.map.Damage;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.common.LocalEventEntity;
import com.game.main.util.Point;
import com.game.main.util.movementEngine.MovementEngine;

/** The basis of generic projectiles */
public abstract class Projectile extends SpriteEntity{
	
	/** The Default despawn time */
	private static final float DEFAULT_DESPAWN_TIME = 1f;
	
	/** The origin of the Projectile */
	protected Point origin;
	
	/** The movement the Projectile uses */
	protected MovementEngine movement;
	
	/** The damage event this Projectile dishes out */
	protected DamageEvent damageEvent;
	
	/** The time this projectile takes before despawning (if not changed) */
	protected float despawnTime;
	
	
	/** Constructs new Projectile */
	public Projectile(MapScene map, Point location, float size, SpriteEnum sprite, DamageEvent damage, MovementEngine movement, float despawnTime) {
		super(map, location, size, sprite);
		this.movement = movement;
		this.origin = location.clone();
		this.despawnTime = despawnTime;
		this.damageEvent = damage;
		updateSpriteEntity(0);
	}
	
	/** Constructs new Projectile */
	public Projectile(MapScene map, Point location, float size, SpriteEnum sprite, DamageEvent damage, MovementEngine movement){
		this(map, location, size, sprite, damage, movement, DEFAULT_DESPAWN_TIME);
	}
	
	
	/** Updates the Projectile to the movement's coordinates */
	@Override public void updateSpriteEntity(float delta){
		this.setLocation(movement.calcCurrentLocation(this.origin, delta));
		float size = super.getBoundingBox().getHeight();
		new LocalEventEntity(super.getMap(), super.getLocation(), size, this.damageEvent, false, true, 0);
		if(calcDespawn()){
			this.killEntity();
		}
	}
	
	/** Determines whether the Projectile will despwan */
	protected boolean calcDespawn(){
		return super.getAge()>=this.despawnTime;
	}
}
