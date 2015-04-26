package fr.nonimad.ld32.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fr.nonimad.ld32.LudumDare32;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = LudumDare32.TITLE;
		config.width = LudumDare32.WIDTH;
		config.height = LudumDare32.HEIGHT;
		new LwjglApplication(new LudumDare32(), config);
	}
}
