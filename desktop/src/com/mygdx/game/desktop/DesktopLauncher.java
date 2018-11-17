package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.SuperSmashShoot;

public class DesktopLauncher {

	public static String host;

	public static void main (String[] arg) {

		if(arg.length > 0)
			host = arg[0];
		else
			host = "localhost";

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SuperSmashShoot(host), config);
		config.width = 1920;
		config.height = 1080;
	}
}
