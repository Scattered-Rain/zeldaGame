package com.game.main.util;

import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import lombok.Setter;

/** Represents a position of two float values */
public class Point{
	
	/** Epsilon value for comparions of float values */
	private static final float EPSILON = 0.0001f;
	
	/** The x value this Point holds */
	@Getter @Setter private float x;
	/** The y value this Point holds */
	@Getter @Setter private float y;
	
	/** Constructs new Point given the x|y */
	public Point(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	/** Constructs Point from Vector2 */
	public Point(Vector2 vec2){
		this(vec2.x, vec2.y);
	}
	
	/** Adds given values to this Point */
	public Point add(float x, float y){
		this.x += x;
		this.y += y;
		return this;
	}
	
	/** Adds given Point to this Point */
	public Point add(Point point){
		return add(point.x, point.y);
	}
	
	/** Adds given value to this Point */
	public Point add(float xy){
		return add(xy, xy);
	}
	
	/** Substracts given Point from this Point */
	public Point substract(Point point){
		return add(-point.getX(), -point.getY());
	}
	
	/** Multiplies given values with this Point */
	public Point multiply(float x, float y){
		this.x = this.x*x;
		this.y = this.y*y;
		return this;
	}
	
	/** Multiplies given Point with Point */
	public Point multiply(Point point){
		return multiply(point.x, point.y);
	}
	
	/** Multiplies given value with this Point */
	public Point  multiply(float xy){
		return multiply(xy, xy);
	}
	
	/** Normalizes this Point (x+y = 1) */
	public Point normalize(){
		if(x==0 && y==0){
			return this;
		}
		else{
			int bx = x==0?0:(int)(Math.abs(x)/x);
			int by = y==0?0:(int)(Math.abs(y)/y);
			float wx = Math.abs(x);
			float wy = Math.abs(y);
			float sum = wx+wy;
			this.x = (wx/sum)*bx;
			this.y = (wy/sum)*by;
			return this;
		}
	}
	
	/** Returns the average Point between this point and the given Point */
	public Point avg(Point point){
		return new Point((x+point.getX())/2, (y+point.getY())/2);
	}
	
	/** Returns whether the the given point is (more or less) equivalent to this point */
	public boolean equalTo(Point point){
		return Math.abs(point.getX()-x)<=EPSILON && Math.abs(point.getY()-y)<=EPSILON;
	}
	
	/** Returns X value as Integer */
	public int getIntX(){
		return (int) x;
	}
	
	/** Returns Y value as Integer */
	public int getIntY(){
		return (int) y;
	}
	
	/** Returns Vector 2 of this Point */
	public Vector2 toVector2(){
		return new Vector2(x, y);
	}
	
	/** Returns deep clone of this Point */
	public Point clone(){
		return new Point(x, y);
	}
	
	/** Returns this Point as String */
	public String toString(){
		return "["+x+"|"+y+"]";
	}
}
