package com.game.main.game.scenes;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.game.main.game.events.Event;

@AllArgsConstructor
/** Scene that holds and processes several Scenes */
public class CompositeScene extends Scene{
	
	/** The Scenes held by this Scene */
	@Getter private List<Scene> scenes;
	
	
	/** Constructs new Composite Scene out of given Scenes */
	public CompositeScene(Scene ... scenes){
		this.scenes = new ArrayList<Scene>();
		for(Scene scene : scenes){
			this.scenes.add(scene);
		}
	}
	
	
	/** Invokes initialization all Scenes held by the Composite */
	@Override public void init(){
		for(Scene scene : scenes){
			scene.init();
		}
	}
	
	/** Invokes updating all Scenes held by the Composite  */
	@Override public void update(float delta){
		for(Scene scene : scenes){
			scene.update(delta);
		}
	}
	
	/** Invokes Event processing for all Scenes held by the Composite */
	@Override public void processEvent(Event event){
		for(Scene scene : scenes){
			scene.processEvent(event);
		}
	}
	
	/** Invokes rendering in all Scenes held by the Composite */
	@Override public void render(){
		for(Scene scene : scenes){
			scene.render();
		}
	}
	
	/** Invokes disposing in all Scenes held by the Composite */
	@Override public void dispose(){
		for(Scene scene : scenes){
			scene.dispose();
		}
	}

}
