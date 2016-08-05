package com.game.main;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.main.util.Direction;

import lombok.Getter;
import lombok.Setter;

/** Contains a Sprite and animation aid */
public class SpriteContainer{
	
	/** The Sprite Enum this Sprite Container is based on */
	@Getter private SpriteEnum sprite;
	/** The actual sprite sheet as described in Texture Regions */
	private TextureRegion[][] spriteSheet;
	/** Time required to finish one line of animation of the sheet */
	@Getter @Setter private float aniTime;
	
	/** Direction this Sprite is currently facing */
	@Getter @Setter private Direction dir;
	
	/** Flag on whether the Sprite is animated or not */
	@Getter @Setter private boolean animate;
	
	/** Means of locally keeping track of time within this Sprite */
	private float localTime;
	
	/** Keeps track of whether this animation has at least gone through completely once */
	private boolean oneIterationComplete;
	
	
	/** Construct new Sprite Container */
	public SpriteContainer(SpriteEnum sprite, TextureAtlas atlas, Direction dir){
		this.oneIterationComplete = false;
		this.sprite = sprite;
		this.aniTime = sprite.getAniTime();
		this.spriteSheet = sprite.getSpriteSheet(atlas);
		this.animate = true;
		this.dir = dir;
	}
	
	/** Construct new Sprite Container */
	public SpriteContainer(SpriteEnum sprite, TextureAtlas atlas){
		this(sprite, atlas, Direction.DOWN);
	}
	
	
	/** Advances the time of the Sprite by given delta */
	public void advance(float delta){
		if(localTime+delta >= aniTime){
			this.oneIterationComplete = true;
		}
		this.localTime = (localTime+delta)%aniTime;
	}
	
	/** Resets the animation (including tracking) */
	public void reset(){
		this.localTime = 0;
		this.oneIterationComplete = false;
	}
	
	/** Returns whether the Sprite Container has at least finished one iteration of the Sprite animation */
	public boolean oneIterationComplete(){
		return oneIterationComplete;
	}
	
	/** Returns currently active Image */
	public TextureRegion getImg(){
		int frame = (int)((localTime/aniTime)*spriteSheet[0].length);
		if(!animate){
			frame = 0;
		}
		return spriteSheet[dir.getIndex()][frame];
	}
	
}
