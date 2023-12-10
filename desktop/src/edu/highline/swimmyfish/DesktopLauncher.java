package edu.highline.swimmyfish;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static edu.highline.swimmyfish.SwimmyFish.WORLD_WIDTH;
import static edu.highline.swimmyfish.SwimmyFish.WORLD_HEIGHT;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config =
                new Lwjgl3ApplicationConfiguration();
        config.setTitle("Swimmy Fish");
        config.setWindowedMode(WORLD_WIDTH, WORLD_HEIGHT);
        config.setResizable(false);
        config.setForegroundFPS(60);
        config.useVsync(true);
        new Lwjgl3Application(new SwimmyFish(), config);
    }
}
