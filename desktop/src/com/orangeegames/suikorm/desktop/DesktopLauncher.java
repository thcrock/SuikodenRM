package com.orangeegames.suikorm.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.orangeegames.suikorm.SuikodenRM;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Kanakan Stories Vol. 1");
		config.setWindowedMode(1280, 720);
		new Lwjgl3Application(new SuikodenRM(), config);
	}
}
