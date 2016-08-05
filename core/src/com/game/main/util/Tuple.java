package com.game.main.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Generic Tuple holding two values */
@AllArgsConstructor
public class Tuple<Generic> {
	
	/** The X of the Tuple */
	@Setter @Getter private Generic x;
	/** The Y of the Tuple */
	@Setter @Getter private Generic y;
	
	/** Returns deep clone of this Tuple */
	public Tuple<Generic> clone(){
		return new Tuple<Generic>(x, y);
	}
	
	/** Returns String of this Tuple */
	public String toString(){
		return "["+x+"|"+y+"]";
	}
	
}
