package com.game.main.game.scenes.map.entity.abstracts;

import lombok.Getter;
import lombok.Setter;

import com.game.main.SpriteEnum;
import com.game.main.game.events.DamageEvent;
import com.game.main.game.events.Event;
import com.game.main.game.events.EventType;
import com.game.main.game.scenes.map.Damage;
import com.game.main.game.scenes.map.EntityRenderLayer;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.common.skills.Skill;
import com.game.main.util.BoundingBox;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** Used as a basis for all Entities that care about damage and alike */
public abstract class MobEntity extends SpriteEntity{
	
	/** Max Health this Mob can have */
	@Getter private int maxHealth;
	/** Current Health this Mob has */
	@Getter private int currentHealth;
	/** Basic defence of this Mob */
	@Getter private int defence;
	/** Describes the effectiveness of any Damage type to this Entity, listed in order of the Enum */
	private int[] damageEffectiveness;
	/** All skills this mob has */
	@Getter @Setter private Skill[] skills;
	/** The skill (if !null) that is currently being used with hold */
	@Getter @Setter private Skill heldSkill;
	/** Describes the type of damage source this entity represents and is therefore resistant to */
	@Getter @Setter private Damage.DamageSource damageSource;
	
	
	/** Constructs new MobEntity */
	public MobEntity(MapScene map, Point location, SpriteEnum sprite) {
		super(map, location, sprite);
		initMob(1, 0);
	}
	
	/** Constructs new Sprite */
	public MobEntity(MapScene map, BoundingBox boundingBox, SpriteEnum sprite){
		super(map, boundingBox, sprite);
		initMob(1, 0);
	}
	
	/** Constructs new Sprite at given location and size */
	public MobEntity(MapScene map, Point location, float size, SpriteEnum sprite){
		super(map, location, size, sprite);
		initMob(1, 0);
	}
	
	/** Initialized Mob, to be called from all Constructors */
	private final void initMob(int maxHealth, int defence){
		super.setCollision(true);
		super.setRenderLayer(EntityRenderLayer.MOB);
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.defence = defence;
		this.damageSource = Damage.DamageSource.NEUTRAL;
		this.damageEffectiveness = new int[Damage.DamageType.values().length];
		this.heldSkill = null;
		this.skills = new Skill[0];
	}
	
	/** Does simple mob maintenance */
	@Override public final void updateSpriteEntity(float delta){
		//Checks for death
		if(currentHealth==0){
			boolean dispose = deathProcess();
			if(dispose){
				this.killEntity();
			}
		}
		if(this.getTrajectory().getX()!=0 || this.getTrajectory().getY()!=0){
			this.setDirection(calcDirFromTrajectory());
		}
		for(Skill skill : skills){
			skill.passiveUpdate(delta);
		}
		if(this.heldSkill!=null){
			heldSkill.useHold(delta);
		}
		this.updateMobEntity(delta);
		this.aiUpdate(delta);
	}
	
	/** Returns the direction most corresponding to the Trajectory */
	private final Direction calcDirFromTrajectory(){
		Point tr = this.getTrajectory();
		if(Math.abs(tr.getY())>=Math.abs(tr.getX())){
			if(tr.getY()>0){
				return Direction.DOWN;
			}
			else{
				return Direction.UP;
			}
		}
		else{
			if(tr.getX()>0){
				return Direction.RIGHT;
			}
			else{
				return Direction.LEFT;
			}
		}
	}
	
	/** Method for the ai of the entity to work on */
	protected void aiUpdate(float delta){}
	
	/** Uses this skill and sets it to the hold skill if flag is given, returns whether activation was legal */
	protected final boolean useSkill(Skill skill, boolean setHeld){
		if(skill.usable()){
			skill.use();
			if(setHeld){
				this.heldSkill = skill;
			}
			return true;
		}
		return false;
	}
	
	/** Removes the held skill of this Entity */
	protected final void clearHeldSkill(){
		this.heldSkill = null;
	}
	
	/** Processing of Events */
	@Override public void processEventEntity(Event event){
		if(event.getType()==EventType.DAMAGE){
			this.afflictDamage((DamageEvent) event);
		}
	}
	
	/** Afflicts damage to this Mob */
	protected void afflictDamage(DamageEvent damage){
		this.currentHealth -= calcDamage(damage);
		if(currentHealth<0){
			this.currentHealth = 0;
		}
	}
	
	/** Calculates Damage taken */
	protected int calcDamage(DamageEvent damageEvent){
		if(damageEvent.getDamageSource()!=this.damageSource){
			int damage = damageEvent.getDamage();
			damage -= this.defence;
			damage += getDamageEffectiveness(damageEvent.getDamageType());
			return damage;
		}
		return 0;
	}
	
	
	/** To be called when entity dies, returns whether Entity should be disposed */
	protected boolean deathProcess(){
		return true;
	}
	
	/** Returns the damage effectiveness of the given Damage Type for this Entity */
	public int getDamageEffectiveness(Damage.DamageType type){
		return this.damageEffectiveness[type.getIndex()];
	}
	
	/** Sets the effectiveness of the given damage type */
	protected void setDamageEffectiveness(Damage.DamageType type, int effectiveness){
		this.damageEffectiveness[type.getIndex()] = effectiveness;
	}
	
	public void updateMobEntity(float delta){}
	
	//Utility Move Methods
	/** Sets Move to given direction */
	public void setMove(Direction dir){
		this.setTrajectory(dir.getDir());
	}
	
	/** Returns whether the player is in range of this Entity */
	public boolean playerInRange(float range){
		return super.getMap().getPlayer().getBoundingBox().getBoundingPenetrate(new BoundingBox(this.getLocation(), range, range));
	}
	
}
