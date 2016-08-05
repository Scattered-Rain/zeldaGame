package com.game.main.game.scenes.map.entity;

import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.common.Grass;
import com.game.main.game.scenes.map.entity.common.mob.BlueBird;
import com.game.main.util.Point;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
/** Keeps track of the keys and properties of Entities spawned by the map */
public enum SpawnEntityEnum{
	GRASS("GRASS", new Spawner(){public void spawn(MapScene map, Point loc, String spawnInfo){
		new Grass(map, loc.clone());
	}}),
	ROCK("ROCK", new Spawner(){public void spawn(MapScene map, Point loc, String spawnInfo){
		new BlueBird(map, loc.clone());
	}}),
	SIGN("SIGN", new Spawner()),
	;
	
	/** The main key which identifies this entity */
	@Getter private String mainKey;
	/** Method used to actually spawn Entity */
	private Spawner spawner;
	
	/** Invokes Spawning */
	public void spawn(MapScene map, Point loc, String spawnInfo){
		this.spawner.spawn(map, loc, spawnInfo);
	}
	
	
	/** Inner class to describe spawn process */
	private static class Spawner{
		public void spawn(MapScene map, Point loc, String spawnInfo){}
	}
}
