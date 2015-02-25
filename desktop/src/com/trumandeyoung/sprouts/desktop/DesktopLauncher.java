package com.trumandeyoung.sprouts.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.trumandeyoung.sprouts.Sprouts;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 900;
		config.width = 506;
        config.forceExit = false;
		new LwjglApplication(new Sprouts(), config);
	}
}
