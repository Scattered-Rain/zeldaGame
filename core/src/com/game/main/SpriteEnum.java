package com.game.main;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.main.util.Direction;
import com.game.main.util.Tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
/** Holds Information of any possible Sprite Sheet */
public enum SpriteEnum{
	LINK("link", 0.5f, 2, 4),
	LINK_ATTACK("linkAttack", 0.5f, 3, 4),
	LINK_ATTACK_HOLD("linkAttack", 0.5f, 3, 4, new int[]{2}),
	STAB_SWORD("stabSword", 0.5f, 1, 4),
	FLAME("flame", 0.5f, 3, 4, new int[]{0, 1, 2, 1}),
	CHEST("chest", 0.5f, 1, 2, new int[]{0}, new int[]{0, 1, 1, 1}),
	BLUE_BIRD("blueBird", 0.5f, 2, 4),
	EXPLOSION("explosion", 0.5f, 5, 4),
	GRASS("grass", 1, 1, 1, new int[]{0}, new int[]{0, 0, 0, 0}),
	ROCK("rock", 1, 1, 1, new int[]{0}, new int[]{0, 0, 0, 0}),
	CUT_LEAFS("cutLeafs", 0.3f, 3, 1, new int[]{0, 1, 2}, new int[]{0, 0, 0, 0}),;
	
	/** The name of the image in the atlas */
	@Getter private String name;
	/** The time needed to complete a line of animation of this Sprite */
	@Getter private float aniTime;
	/** The amount of individual frames in a row of the image */
	@Getter private int width;
	/** The amount of rows within the image */
	@Getter private int height;
	/** Mapping of the mapping of the image cells to the cells in the sprite, x for row y for col */
	private Tuple<Integer>[][] sheetMapping;
	
	
	/** Constructs new Sprite enum, mapping the sprite 1 to 1 in terms of the original image file */
	private SpriteEnum(String name, float aniTime, int width, int height){
		this.name = name;
		this.aniTime = aniTime;
		this.width = width;
		this.height = height;
		this.sheetMapping = new Tuple[Direction.NUM_DIRS][width];
		for(int cy=0; cy<sheetMapping.length; cy++){
			for(int cx=0; cx<sheetMapping[0].length; cx++){
				this.sheetMapping[cy][cx] = new Tuple<Integer>(cx, cy);
			}
		}
	}
	
	/** Constructs new Sprite enum, mapping the sprite for each row identically based on this row's images 
	 * (So the generic mapping describes the order of images in a row, every col being assumed to be mapped and mapped this way)*/
	private SpriteEnum(String name, float aniTime, int width, int height, int[] genericMapping){
		this.name = name;
		this.aniTime = aniTime;
		this.width = width;
		this.height = height;
		this.sheetMapping = new Tuple[Direction.NUM_DIRS][genericMapping.length];
		for(int cy=0; cy<sheetMapping.length; cy++){
			for(int cx=0; cx<sheetMapping[0].length; cx++){
				this.sheetMapping[cy][cx] = new Tuple<Integer>(genericMapping[cx], cy);
			}
		}
	}
	
	/** Constructs new Sprite enum, mapping the sprite for each row identically based on this row's images as decided by cols images 
	 * (The mapping of the images in a row like before, though the rows themselves described by the row mapping) */
	private SpriteEnum(String name, float aniTime, int width, int height, int[] genericMapping, int[] rowMapping){
		this.name = name;
		this.aniTime = aniTime;
		this.width = width;
		this.height = height;
		this.sheetMapping = new Tuple[Direction.NUM_DIRS][genericMapping.length];
		for(int cy=0; cy<sheetMapping.length; cy++){
			for(int cx=0; cx<sheetMapping[0].length; cx++){
				this.sheetMapping[cy][cx] = new Tuple<Integer>(genericMapping[cx], rowMapping[cy]);
			}
		}
	}
	
	
	/** Returns 2d array of Atlas Regions describing this SpriteSheet (in actual order) */
	public TextureRegion[][] getSpriteSheet(TextureAtlas atlas){
		AtlasRegion temp = atlas.findRegion(name);
		int width = temp.getRegionWidth();
		int height = temp.getRegionHeight();
		TextureRegion[][] reg = temp.split(width/this.width, height/this.height);
		TextureRegion[][] out = new TextureRegion[sheetMapping.length][sheetMapping[0].length];
		for(int cy=0; cy<out.length; cy++){
			for(int cx=0; cx<out[0].length; cx++){
				Tuple<Integer> tup = sheetMapping[cy][cx];
				out[cy][cx] = reg[tup.getY()][tup.getX()];
			}
		}
		return out;
	}
	
}
