package com.game.main.game.scenes.map.entity.common.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.main.Constants;
import com.game.main.Core;
import com.game.main.SoundEnum;
import com.game.main.SpriteEnum;
import com.game.main.game.events.DamageEvent;
import com.game.main.game.events.Event;
import com.game.main.game.input.Controllable;
import com.game.main.game.input.Controller;
import com.game.main.game.input.Input;
import com.game.main.game.scenes.map.Damage;
import com.game.main.game.scenes.map.EntityRenderLayer;
import com.game.main.game.scenes.map.MapCamera;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.abstracts.MobEntity;
import com.game.main.game.scenes.map.entity.abstracts.SpriteEntity;
import com.game.main.game.scenes.map.entity.common.LocalEventEntity;
import com.game.main.game.scenes.map.entity.common.animation.AnimationEntity;
import com.game.main.game.scenes.map.entity.common.animation.CircleAnimationEntity;
import com.game.main.game.scenes.map.entity.common.skills.FlameSkill;
import com.game.main.game.scenes.map.entity.common.skills.Skill;
import com.game.main.game.scenes.map.entity.common.skills.StabSwordSkill;
import com.game.main.util.BoundingBox;
import com.game.main.util.Direction;
import com.game.main.util.Point;

public class Prota extends MobEntity{
	
	private MapCamera camera;
	
	private Controller controller;
	
	private Skill[] skills;
	
	
	/** Constructs new Protagonist */
	public Prota(MapScene map, Point originTile, MapCamera camera) {
		super(map, originTile, 14, SpriteEnum.LINK);
		super.setLocationToTileLocation();
		super.body2d.setActive(true);
		super.setRenderLayer(EntityRenderLayer.PLAYER);
		this.camera = camera;
		this.controller = Core.getController();
		super.setBaseSpeed(1f);
		super.setDamageSource(Damage.DamageSource.PLAYER);
		this.skills = new Skill[2];
		for(int c=0; c<skills.length; c++){
			skills[c] = new Skill.None(this);
		}
		skills[0] = new FlameSkill(this);
		skills[1] = new StabSwordSkill(this);
	}
	
	@Override public void updateMobEntity(float delta){
		for(Skill skill : skills){
			skill.passiveUpdate(delta);
		}
		boolean dir = false;
		Direction mDir = super.getSprite().getDir();
		Point walking = new Point(0, 0);
		for(Input input : controller.getActiveInputs()){
			Direction tempDir = Input.convertToDirection(input);
			if(tempDir!=null){
				walking.add(tempDir.getDir());
				mDir = tempDir;
				dir = true;
			}
		}
		for(Input input : controller.getLatestInputs()){
			Input[] inputs = new Input[]{Input.ACTION_A, Input.ACTION_B};
			for(int c=0; c<inputs.length; c++){
				if(input==inputs[c]){
					if(this.skills[c].usable()){
						this.skills[c].use();
					}
				}
			}
		}
		Point newLoc = super.getBoundingBox().getLocation();
		walking = new Point(walking.getX()*getBaseSpeed(), walking.getY()*getBaseSpeed());
		super.setTrajectory(walking);
		super.setDirection(mDir);
		super.updateOrStopResetAnimation(dir);
		camera.setLocation(newLoc);
	}
	
}
