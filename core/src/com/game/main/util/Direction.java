package com.game.main.util;

import com.game.main.Core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
/** Represents a Direction in the Game */
public enum Direction {
	
	UP(0, new Point(0, -1)),
	RIGHT(1, new Point(1, 0)),
	DOWN(2, new Point(0, 1)),
	LEFT(3, new Point(-1, 0));
	
	
	/** Number of all directions */
	public static final int NUM_DIRS = 4;
	/** The Directions listed in properly indexed order */
	private static final Direction[] DIRS = new Direction[]{UP, RIGHT, DOWN, LEFT};
	
	
	/** The index of this Direction */
	@Getter private int index;
	/** The Direction as represented by X and Y location to normal 1 */
	private Point dir;
	
	
	/** Returns array containing all Directions, listed according to index */
	public static Direction[] getAllDirs(){
		return new Direction[]{UP, RIGHT, DOWN, LEFT};
	}
	
	/** Returns array containing all Directions, listed according to index */
	public static Direction getRandDir(){
		return getAllDirs()[Core.getRandom().nextInt(4)];
	}
	
	
	/** Returns the Direction counterclockwise to this one */
	public Direction turnClockwise(){
		return DIRS[(index+1)%DIRS.length];
	}
	
	/** Returns the Direction counterclockwise of this Direction */
	public Direction turnCounterClockwise(){
		return turnClockwise().turnClockwise().turnClockwise();
	}
	
	/** Returns the Direction opposite to this one */
	public Direction turnBack(){
		return turnClockwise().turnClockwise();
	}
	
	/** Returns the Direction as represented by X and Y location to normal 1*/
	public Point getDir(){
		return dir.clone();
	}
	
}
