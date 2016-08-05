package com.game.main;

import java.util.Random;

import lombok.Getter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.game.main.game.input.Controller;
import com.game.main.game.scenes.CompositeScene;
import com.game.main.game.scenes.Scene;
import com.game.main.game.scenes.map.MapReference;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.util.Direction;

/** Core class of the Game */
public class Core extends ApplicationAdapter {
	
	/** The Root Scene being processed */
	@Getter private static Scene scene;
	
	/** The active core controller attached to the Gdx */
	@Getter private static Controller controller;
	
	/** The Texture Atlas */
	@Getter private static TextureAtlas atlas;
	
	/** Globally accessible RNG */
	@Getter private static Random random;
	
	/** The frame rate */
	@Getter private static float frameRate;
	
	
	/** Creates new Application */
	@Override public void create(){
		frameRate = 60;
		random = new Random();
		atlas =  new TextureAtlas(Gdx.files.internal("sprites/atlas.atlas"));
		controller = new Controller();
		Gdx.input.setInputProcessor(controller);
		scene = new CompositeScene(new MapScene(MapReference.WORLD));
	}
	
	/** Update Cycle */
	@Override public void render(){
		Gdx.graphics.getFramesPerSecond();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime();
		scene.update(delta);
		controller.clearLatestInputs();
		scene.render();
	}
	
}
