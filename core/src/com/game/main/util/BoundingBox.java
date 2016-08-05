package com.game.main.util;

import lombok.Getter;
import lombok.Setter;

/** Collision Box */
public class BoundingBox{
	
	/** The Location this Collision Box occupies */
	@Getter @Setter private Point location;
	
	/** Width of the complete bounding box */
	@Getter private float width;
	/** Height of the complete Bounding Box */
	@Getter private float height;
	
	/** Constructs new Bounding Box */
	public BoundingBox(Point location, float width, float height){
		this.location = location;
		this.width = width;
		this.height = height;
	}
	
	/** Returns whether any part of the given BoundingBox is within this BoundingBox */
	public boolean getBoundingPenetrate(BoundingBox penetrator){
		float x1 = location.getX()-(width/2);
		float y1 = location.getY()-(height/2);
		float w1 = width;
		float h1 = height;
		float x2 = penetrator.getLocation().getX()-(penetrator.getWidth()/2);
		float y2 = penetrator.getLocation().getY()-(penetrator.getHeight()/2);
		float w2 = penetrator.getWidth();
		float h2 = penetrator.getHeight();
		boolean nonIntersect = (x1+w1<x2 || x2+w2<x1 || y1+h1<y2 || y2+h2<y1);
		return !nonIntersect;
	}
	
	/** Returns whether the given BoundingBox's center is within this BoundingBox */
	public boolean getCenterPenetrate(BoundingBox penetrator){
		return getCenterPenetrate(penetrator.getLocation());
	}
	
	/** Returns whether the given Point's center is within this BoundingBox */
	public boolean getCenterPenetrate(Point penetrator){
		float x = penetrator.getX();
		float y = penetrator.getY();
		boolean xIn = x>=location.getX()-(width/2) && x<=location.getX()+(width/2);
		boolean yIn = y>=location.getY()-(height/2) && y<=location.getY()+(height/2);
		return xIn && yIn;
	}
	
}
