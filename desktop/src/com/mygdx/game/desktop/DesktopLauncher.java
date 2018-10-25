package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.SuperSmashShoot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SuperSmashShoot(), config);
		System.out.println(SuperSmashShoot.SCREEN_WIDTH);
		config.width = 1920;
		config.height = 1080;
	}
}
