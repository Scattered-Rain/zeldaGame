package com.game.main.game.scenes.map.entity.common.skills;

import com.game.main.Constants;
import com.game.main.Core;
import com.game.main.SoundEnum;
import com.game.main.SpriteContainer;
import com.game.main.SpriteEnum;
import com.game.main.game.events.DamageEvent;
import com.game.main.game.scenes.map.Damage;
import com.game.main.game.scenes.map.EntityRenderLayer;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.abstracts.MobEntity;
import com.game.main.game.scenes.map.entity.abstracts.Projectile;
import com.game.main.game.scenes.map.entity.common.player.Prota;
import com.game.main.util.Direction;
import com.game.main.util.Point;
import com.game.main.util.movementEngine.DirectionalMovementEngine;

public class FlameSkill extends Skill{
	
	/** Cool Down */
	private static final float COOL_DOWN = 0.5f;
	/** Player Freeze */
	private static final float PLAYER_FREEZE = 0.2f;
	
	
	/** Constructs new Flame Skill */
	public FlameSkill(MobEntity player) {
		super(player, new SpriteContainer(SpriteEnum.FLAME, Core.getAtlas(), Direction.UP), COOL_DOWN);
	}
	
	
	/** Spawns Flame */
	@Override public void use(){
		super.use();
		Direction dir = user.getDir();
		new FlameProjectile(user.getMap(), user.getLocation().clone().add(dir.getDir().multiply(Constants.TILE/2)), dir, super.user);
		user.setFreeze(PLAYER_FREEZE);
		SoundEnum.EXPLOSION.play();
		if(user.getDamageSource()==Damage.DamageSource.PLAYER){
			user.setSpecialAnimation(SpriteEnum.LINK_ATTACK_HOLD, dir, PLAYER_FREEZE);
		}
	}
	/** Does nothing */
	@Override public void useHold(float delta){}
	
	
	/** Inner class describing the Flame Projectile */
	private static class FlameProjectile extends Projectile{
		
		/** Parameters describing the Flame */
		private static final float SIZE = 12f;
		private static final SpriteEnum SPRITE = SpriteEnum.FLAME;
		private static final Damage.DamageType DAMAGE_TYPE = Damage.DamageType.FLAME;
		private static final int DAMAGE_VAL = 1;
		private static final float SPEED = Constants.TILE*6;
		private static final float DESPAWN_TIME = 0.5f;
		
		/** Constructs new Flame Projectile */
		public FlameProjectile(MapScene map, Point location, Direction dir, MobEntity user){
			super(map, location, SIZE, SPRITE, new DamageEvent(DAMAGE_TYPE, DAMAGE_VAL, user.getDamageSource()), new DirectionalMovementEngine(dir, SPEED), DESPAWN_TIME);
			super.setDirection(dir);
			super.setRenderLayer(EntityRenderLayer.TOP);
		}
	}
	
}
