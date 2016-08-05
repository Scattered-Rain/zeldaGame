package com.game.main.game.scenes.map.entity.common.mob;

import java.util.ArrayList;
import java.util.List;

import com.game.main.Constants;
import com.game.main.Core;
import com.game.main.SpriteEnum;
import com.game.main.game.events.DamageEvent;
import com.game.main.game.scenes.map.AnimationFactory;
import com.game.main.game.scenes.map.CollisionType;
import com.game.main.game.scenes.map.Damage.DamageType;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.abstracts.MobEntity;
import com.game.main.game.scenes.map.entity.common.LocalEventEntity;
import com.game.main.game.scenes.map.entity.common.skills.FlameSkill;
import com.game.main.game.scenes.map.entity.common.skills.Skill;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** Simple Enemy */
public class BlueBird extends MobEntity{
	
	/** The tile this entity is on (or moving onto) */
	private Point perTarTraj;
	Point lastPoint;
	
	/** New Blue Bird */
	public BlueBird(MapScene map, Point location) {
		super(map, location, 14, SpriteEnum.BLUE_BIRD);
		this.perTarTraj = new Point(0, 0);
		super.setLocationToTileLocation();
		super.setBaseSpeed(1f);
		super.setSkills(new Skill[]{new FlameSkill(this)});
	}
	
	/** Blue Bird Ai */
	@Override public void aiUpdate(float delta){
		if(super.playerInRange(Constants.TILE*3)){
			if(lastPoint!=null && lastPoint.equalTo(getLocation())){
				Direction walkTo = Direction.getRandDir();
				this.perTarTraj = walkTo.getDir();
				super.setTrajectory(perTarTraj);
			}
			this.lastPoint = getLocation();
		}
	}
	
	/** Spawns some dead leafs */
	@Override public boolean deathProcess(){
		spawnAnimation(AnimationFactory.ENEMY_DEATH);
		return true;
	}
	
}
