package com.game.main.game.scenes.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.main.Constants;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** Creates and adds Polygons describing the collision of the map to the world2d */
public class MapCollisionPolygonFactory{
	
	/** Quick reference for the size of a tile */
	private static final float TILE_SIZE = Constants.TILE;
	
	/** Quick reference to Solid collision type */
	private static final CollisionType SOLID = CollisionType.SOLID;
	
	/** Debug Counter */
	private static int rectCounter;
	
	/** Creates and adds Polygons of the collision to the world */
	public static void createAndAddMapCollision(World world2d, CollisionType[][] collMap){
		rectCounter = 0;
		boolean[][] alreadyAdded = new boolean[collMap.length][collMap[0].length];
		for(int cy=0; cy<collMap.length; cy++){
			for(int cx=0; cx<collMap[0].length; cx++){
				if(collMap[cy][cx]==SOLID && !alreadyAdded[cy][cx]){
					rectangle(new Point(cx, cy), world2d, collMap, alreadyAdded);
				}
			}
		}
	}
	
	/** Starts Rectangling */
	private static void rectangle(Point tile, World world2d, CollisionType[][] collMap, boolean[][] alreadyAdded){
		rectCounter++;
		//Calculate Rectangle
		Point lowerRight = tile.clone();
		boolean expandOnX = true;
		boolean expandOnY = true;
		while(expandOnX || expandOnY){
			if(expandOnX){
				boolean legal = check(collMap, tile, lowerRight.clone().add(1, 0));
				if(legal){
					lowerRight = lowerRight.add(1, 0);
				}else{
					expandOnX = false;
				}
			}
			if(expandOnY){
				boolean legal = check(collMap, tile, lowerRight.clone().add(0, 1));
				if(legal){
					lowerRight = lowerRight.add(0, 1);
				}else{
					expandOnY = false;
				}
			}
		}
		//Note Rectangle
		for(int cy=tile.getIntY(); cy<=lowerRight.getIntY(); cy++){
			for(int cx=tile.getIntX(); cx<=lowerRight.getIntX(); cx++){
				alreadyAdded[cy][cx] = true;
			}
		}
		//Build Rectangle
		float left = tile.getX();
		float right = lowerRight.getX();
		float up = tile.getY();
		float down = lowerRight.getY();
		Vector2[] verts = new Vector2[4];
		verts[0] = getCorner(true, true, new Point(left, up));
		verts[1] = getCorner(true, false, new Point(left, down));
		verts[2] = getCorner(false, false, new Point(right, down));
		verts[3] = getCorner(false, true, new Point(right, up));
		PolygonShape rect = new PolygonShape();
		rect.set(verts);
		//Add to world
		FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rect;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        Body temp = world2d.createBody(bodyDef);
        temp.createFixture(fixtureDef);
        temp.setFixedRotation(true);
		//Dispose Shape
		rect.dispose();
	}
	
	/** Checks whether the described rectangle contains only collision tiles */
	private static boolean check(CollisionType[][] collMap, Point upperLeft, Point lowerRight){
		if(lowerRight.getIntY()>=collMap.length || lowerRight.getIntX()>=collMap[0].length){
			return false;
		}
		for(int cy=upperLeft.getIntY(); cy<=lowerRight.getIntY(); cy++){
			for(int cx=upperLeft.getIntX(); cx<=lowerRight.getIntX(); cx++){
				if(collMap[cy][cx]!=SOLID){
					return false;
				}
			}
		}
		return true;
	}
	
	/** Returns a Point representing the vertex of the given TILE on the corner described by the two directions (!left = right, etc.) */
	private static Vector2 getCorner(boolean left, boolean up, Point tile){
		int horizontalAdd = left?0:1;
		int verticalAdd = up?0:1;
		return new Point((tile.getX()+horizontalAdd)*TILE_SIZE, (tile.getY()+verticalAdd)*TILE_SIZE).toVector2();
	}
	
}
