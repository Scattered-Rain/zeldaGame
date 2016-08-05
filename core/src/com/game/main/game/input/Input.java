package com.game.main.game.input;

import com.badlogic.gdx.Input.Keys;
import com.game.main.util.Direction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
/** Enumeration of possible controller inputs */
public enum Input {
	
	UP(Keys.W),
	RIGHT(Keys.D),
	DOWN(Keys.S),
	LEFT(Keys.A),
	ACTION_A(Keys.J),
	ACTION_B(Keys.K),
	START(Keys.ESCAPE),
	SELECT(Keys.ENTER);
	
	/** The Key the input defaults to */
	@Getter int defaultKey;
	
	/** Returns the direction corresponding to the given input, if the input is not directional, returns null */
	public static Direction convertToDirection(Input input){
		Direction[] dirs = new Direction[]{Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
		Input[] dirInputs = new Input[]{UP, RIGHT, DOWN, LEFT};
		for(int c=0; c<dirInputs.length; c++){
			if(dirInputs[c].equals(input)){
				return dirs[c];
			}
		}
		return null;
	}
	
}
