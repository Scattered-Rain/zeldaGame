package com.game.main.game.scenes.map.entity.common.skills;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.main.Core;
import com.game.main.SpriteContainer;
import com.game.main.SpriteEnum;
import com.game.main.game.scenes.map.entity.abstracts.MobEntity;
import com.game.main.util.Direction;

import lombok.Getter;

/** Represents a player skill */
public abstract class Skill{
	
	/** Reference to the Player entity */
	protected MobEntity user;
	
	/** Sprite used to visually represent this skill */
	private SpriteContainer sprite;
	
	/** Time it needs for the skill to cool down */
	@Getter private float cooldownTime;
	
	/** Current cooldown state */
	@Getter private float currentCooldown;
	
	
	/** Constructs new Skill */
	public Skill(MobEntity player, SpriteContainer sprite, float cooldownTime){
		this.user = player;
		this.cooldownTime = cooldownTime;
		this.currentCooldown = 0;
		this.sprite = sprite;
	}
	
	
	/** Called when the Skill is activated */
	public void use(){
		this.currentCooldown = cooldownTime;
	}
	
	/** Called when the skill is kept activated (button is held) */
	public abstract void useHold(float delta);
	
	/** Returns whether the Skill can be used (resource wise) */
	public boolean canUse(){
		return true;
	}
	
	/** Returns whether the skill can be used by the player now */
	public final boolean usable(){
		return isCooledDown() && canUse();
	}
	
	/** Returns whether the skill is cooled down */
	public final boolean isCooledDown(){
		return currentCooldown <= 0;
	}
	
	/** Cooldown is activated */
	public final void setCooldown(){
		this.currentCooldown = this.cooldownTime;
	}
	
	/** Method that allows for passive updates, called every frame */
	public void passiveUpdate(float delta){
		cooldownUpdate(delta);
		this.sprite.advance(delta);
	}
	
	/** Updates for cool down */
	protected final void cooldownUpdate(float delta){
		if(currentCooldown>0){
			this.currentCooldown -= delta;
			if(currentCooldown<0){
				this.currentCooldown = 0;
			}
		}
	}
	
	/** Returns image standing as visual representation of this Skill */
	public final TextureRegion getImg(){
		return sprite.getImg();
	}
	
	/** Skill that does nothing */
	public static class None extends Skill{
		public None(MobEntity player) {
			super(player, new SpriteContainer(SpriteEnum.GRASS, Core.getAtlas()), 0);
		}
		@Override public void use(){}
		@Override public void useHold(float delta){}
	}
	
}
