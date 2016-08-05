package com.game.main.game.scenes.map.entity.common;

import com.game.main.SpriteEnum;
import com.game.main.game.scenes.map.AnimationFactory;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.abstracts.MobEntity;
import com.game.main.game.scenes.map.entity.abstracts.SpriteEntity;
import com.game.main.game.scenes.map.entity.common.animation.AnimationEntity;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** Grass */
public class Grass extends MobEntity{
	
	/** Constructs new Grass */
	public Grass(MapScene map, Point tile) {
		super(map, tile, 16f, SpriteEnum.GRASS);
		super.setLocationToTileLocation();
		super.setAdvanceSprite(false);
		super.reInitBox2d(false);
	}
	
	/** Spawns some dead leafs */
	@Override public boolean deathProcess(){
		spawnAnimation(AnimationFactory.CUT_GRASS);
		return true;
	}
	
}
