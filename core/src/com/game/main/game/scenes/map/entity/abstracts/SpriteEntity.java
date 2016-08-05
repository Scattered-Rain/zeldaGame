package com.game.main.game.scenes.map.entity.abstracts;

import lombok.Getter;
import lombok.Setter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.main.Constants;
import com.game.main.Core;
import com.game.main.SpriteContainer;
import com.game.main.SpriteEnum;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.util.BoundingBox;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** This abstract Entity represents any Entity which uses a Sprite */
public class SpriteEntity extends Entity{
	
	/** The Sprite Renderable by this Entity */
	@Getter private SpriteContainer sprite; 
	
	/** Whether this Sprite is supposed to render itself */
	@Getter @Setter private boolean toRender;
	
	/** Whether this Sprite is supposed to be advanced in terms of animation */
	@Getter @Setter private boolean advanceSprite;
	
	/** Describes the direction this Mob is facing in */
	@Getter private Direction dir;
	
	/** A special pose the entity does, is shown instead of actual sprite, but removed once 1 iteration is reached */
	private SpriteContainer specialAnimation;
	
	
	/** Constructs new Sprite */
	public SpriteEntity(MapScene map, BoundingBox boundingBox, SpriteEnum sprite){
		super(map, boundingBox);
		initSprite(sprite);
	}
	
	/** Constructs new Sprite at given location and size */
	public SpriteEntity(MapScene map, Point location, float size, SpriteEnum sprite){
		super(map, location, size);
		initSprite(sprite);
	}
	
	/** Constructs new Sprite at given location with the size of exactly one Tile */
	public SpriteEntity(MapScene map, Point location, SpriteEnum sprite){
		super(map, location);
		initSprite(sprite);
	}
	
	/** Properly sets up Sprite, should be invoked by all local constructors */
	private final void initSprite(SpriteEnum sprite){
		this.sprite = new SpriteContainer(sprite, Core.getAtlas());
		this.toRender = true;
		this.advanceSprite = true;
		this.dir = Direction.DOWN;
		this.specialAnimation = null;
	}
	
	
	/** Updates Sprite, invokes Sprite update */
	@Override public final void updateEntity(float delta){
		if(this.advanceSprite){
			advanceSprite(delta);
		}
		advanceSpecialAnimation(delta);
		updateSpriteEntity(delta);
	}
	
	/** Renders Sprite */
	@Override public final void renderEntity(SpriteBatch batch){
		if(this.toRender){
			renderSprite(batch);
		}
		renderSpriteEntity(batch);
	}
	
	/** Updates Sprite Entity */
	public void updateSpriteEntity(float delta){}
	
	/** For potential additional rendering purposes */
	public void renderSpriteEntity(SpriteBatch batch){}
	
	/** Renders Sprite at center of BoundingBox */
	protected final void renderSprite(SpriteBatch batch){
		if(this.specialAnimation!=null){
			utilDrawPixel(batch, getBoundingBox().getLocation(), specialAnimation.getImg());
		}
		else{
			utilDrawPixel(batch, getBoundingBox().getLocation(), sprite.getImg());
		}
	}
	
	/** Advances Sprite */
	protected final void advanceSprite(float delta){
		sprite.advance(delta);
	}
	
	/** Advances Special Animation, deletes it once done with one iteration */
	protected final void advanceSpecialAnimation(float delta){
		if(this.specialAnimation!=null){
			if(specialAnimation.oneIterationComplete()){
				this.specialAnimation = null;
			}
			else{
				this.specialAnimation.advance(delta);
			}
		}
	}
	
	/** Set Special Animation */
	public void setSpecialAnimation(SpriteEnum animation, Direction dir, float animationTime){
		this.specialAnimation = new SpriteContainer(animation, Core.getAtlas(), dir);
		this.specialAnimation.setAniTime(animationTime);
	}
	
	/** Set Special Animation (Default Animation Time) */
	public void setSpecialAnimation(SpriteEnum animation, Direction dir){
		this.setSpecialAnimation(animation, dir, animation.getAniTime());
	}
	
	
	//Utility Methods ---
	/** Properly draws the given image at the given pixel location onto the given batch */
	protected final void utilDrawPixel(SpriteBatch batch, Point loc, TextureRegion img){
		batch.draw(img, 0+loc.getX()-img.getRegionWidth()/2, -img.getRegionHeight()-loc.getY()+img.getRegionHeight()/2);
	}
	
	/** Properly draws the given image at the given Tile location onto the given batch */
	protected final void utilDrawTile(SpriteBatch batch, Point loc, TextureRegion img){
		batch.draw(img, 0+loc.getX()*Constants.TILE-img.getRegionWidth()/2, -img.getRegionHeight()-loc.getY()*Constants.TILE+img.getRegionHeight()/2);
	}
	
	/** Sets this Entities Sprite */
	public final void setSprite(SpriteEnum sprite){
		this.sprite = new SpriteContainer(sprite, Core.getAtlas());
	}
	
	/** Sets this Entities Sprite, and the animation speed it uses */
	public final void setSprite(SpriteEnum sprite, float aniTime){
		this.sprite = new SpriteContainer(sprite, Core.getAtlas());
		this.sprite.setAniTime(aniTime);
	}
	
	/** Resets Sprite Animation */
	public final void resetAnimation(){
		this.sprite.reset();
	}
	
	/** Resets and turns of updating of animation */
	public final void stopResetAnimation(){
		this.setAdvanceSprite(false);
		resetAnimation();
	}
	
	/** If true animation progression is turned on (no reset), if false its turned off with animation reset */
	public final void updateOrStopResetAnimation(boolean update){
		if(update){
			this.setAdvanceSprite(update);
		}
		else{
			stopResetAnimation();
		}
	}
	
	/** Set Sprite direction to given direction */
	public final void setDirection(Direction dir){
		this.sprite.setDir(dir);
		this.dir = dir;
	}
	
}
