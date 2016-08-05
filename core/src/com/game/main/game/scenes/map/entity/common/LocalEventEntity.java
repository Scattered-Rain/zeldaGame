package com.game.main.game.scenes.map.entity.common;

import lombok.Getter;

import com.game.main.game.events.Event;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.abstracts.Entity;
import com.game.main.util.BoundingBox;
import com.game.main.util.Point;

/** This Entity describes a certain event allocated to a special region on the map */
public class LocalEventEntity extends Entity{
	
	/** The event to be sent */
	@Getter private Event event;
	
	/** Whether the local event is to be triggered when the Entity's center crosses the bounding box of this */
	private boolean centerPenetration;
	
	/** Whether the local event is to be triggered when the Entity's bounding box crosses the bounding box of this */
	private boolean boundingPenetration;
	
	/** Time this Entity has left before despawning (with 0 always being 1 frame, i.e. a single update before despawning) */
	private float despawnTime;
	
	
	/** Constructs new LocalEventEntity */
	public LocalEventEntity(MapScene map, Point location, float size, Event event, boolean centerPenetration, boolean boundingPenetration, float despawnTime) {
		super(map, location, size);
		this.event = event;
		this.centerPenetration = centerPenetration;
		this.boundingPenetration = boundingPenetration;
		this.despawnTime = despawnTime;
	}
	
	
	/** Checks for Entities applicable for this Event, checks for death */
	@Override public void updateEntity(float delta){
		//Entity event checking
		for(Entity e : super.getMap().getEntities()){
			if(!e.isEntity(this)){
				if(centerPenetration && this.getBoundingBox().getCenterPenetrate(e.getBoundingBox())){
					e.processEvent(event);
				}
				else if(boundingPenetration && this.getBoundingBox().getBoundingPenetrate(e.getBoundingBox())){
					e.processEvent(event);
				}
			}
		}
		//Death check etc.
		this.despawnTime -= delta;
		if(despawnTime<=0){
			this.killEntity();
		}
	}
	
}
