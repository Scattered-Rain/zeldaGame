package com.game.main.game.scenes.map;

import com.game.main.SoundEnum;
import com.game.main.SpriteEnum;
import com.game.main.game.scenes.map.entity.common.animation.AnimationEntity;
import com.game.main.util.Direction;
import com.game.main.util.Point;

import lombok.AllArgsConstructor;

@AllArgsConstructor
/** Factory for creating and spawning Animations as well as Sounds */
public enum AnimationFactory{
	CUT_GRASS(new Spawner(){public void spawn(MapScene map, Point origin){
		new AnimationEntity(map, origin, SpriteEnum.CUT_LEAFS);
	}}),
	ENEMY_DEATH(new Spawner(){public void spawn(MapScene map, Point origin){
		new AnimationEntity(map, origin, SpriteEnum.EXPLOSION, Direction.UP);
		SoundEnum.BIG_EXPLOSION.play();
	}}),
	;
	
	/** Inner held spawner */
	private Spawner spawner;
	
	/** Spawns in animation */
	public void spawnAni(MapScene map, Point origin){
		spawner.spawn(map, origin);
	}
	
	/** Internal Spawner class */
	private static class Spawner{
		public void spawn(MapScene map, Point origin){}
	}
}
