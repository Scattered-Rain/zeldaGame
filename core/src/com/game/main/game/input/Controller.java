package com.game.main.game.input;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.badlogic.gdx.InputAdapter;

/** The main Controller in the game */
public class Controller extends InputAdapter{
	
	/** List of all active inputs  */
	@Getter private List<Input> activeInputs;
	
	/** Latest Inputs */
	@Getter private List<Input> latestInputs;
	
	
	/** Constructs new Controller */
	public Controller(){
		this.activeInputs = new ArrayList<Input>();
		this.latestInputs = new ArrayList<Input>();
	}
	
	
	/** Processes Press Down Events */
	@Override public boolean keyDown(int key){
		for(Input input : Input.values()){
			if(input.getDefaultKey()==key){
				activeInputs.add(input);
				latestInputs.add(input);
				return true;
			}
		}
		return false;
	}
	
	/** Processes Press Up Events */
	@Override public boolean keyUp(int key){
		boolean removedOne = false;
		for(Input input : Input.values()){
			if(input.getDefaultKey()==key){
				for(int c=0; c<activeInputs.size(); c++){
					if(activeInputs.get(c).equals(input)){
						activeInputs.remove(c);
						removedOne = true;
						c--;
					}
				}
				return removedOne;
			}
		}
		return true;
	}
	
	/** Clears list of latest Inputs (Should be called after each pull) */
	public void clearLatestInputs(){
		this.latestInputs.clear();
	}
	
	/** Resets both input lists */
	public void reset(){
		this.activeInputs.clear();
		this.latestInputs.clear();
	}
	
}
