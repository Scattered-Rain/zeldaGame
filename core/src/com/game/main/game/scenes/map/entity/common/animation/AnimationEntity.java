package com.game.main.game.scenes.map.entity.common.animation;

import com.game.main.SoundEnum;
import com.game.main.SpriteEnum;
import com.game.main.game.scenes.map.EntityRenderLayer;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.abstracts.SpriteEntity;
import com.game.main.util.BoundingBox;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** This Entity represents a one time animation on the map, after which it kills itself */
public class AnimationEntity extends SpriteEntity{
	
	/** Constructs New Animation */
	public AnimationEntity(MapScene map, Point location, SpriteEnum sprite, Direction dir){
		super(map, location, sprite);
		super.getSprite().setDir(dir);
		super.setRenderLayer(EntityRenderLayer.ANIMATION);
	}
	
	/** Constructs new Animation, defaults to UP Direction in Sprite */
	public AnimationEntity(MapScene map, Point location, SpriteEnum sprite){
		this(map, location, sprite, Direction.UP);
	}
	
	/** Once animation is over kill this Entity */
	@Override public void updateSpriteEntity(float delta){
		if(super.getSprite().oneIterationComplete()){
			this.killEntity();
		}
	}
	
}
