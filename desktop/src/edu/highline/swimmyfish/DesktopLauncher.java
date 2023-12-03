package edu.highline.swimmyfish;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import edu.highline.swimmyfish.SwimmyFish;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Swimmy Fish");
		config.setWindowedMode(800, 480);
		config.setForegroundFPS(144);
		config.useVsync(true);
		new Lwjgl3Application(new SwimmyFish(), config);
	}
}
