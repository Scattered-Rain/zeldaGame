package com.game.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.main.Core;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Autopacker.packFolder("sprites", "sprites", "atlas");
		config.vSyncEnabled = true;
		config.title = "Zelda Game";
		config.resizable = false;
		config.fullscreen = false;
		new LwjglApplication(new Core(), config);
	}
}
