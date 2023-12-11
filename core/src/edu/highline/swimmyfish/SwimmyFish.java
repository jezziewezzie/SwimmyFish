package edu.highline.swimmyfish;

import com.badlogic.gdx.Game;

public class SwimmyFish extends Game {
    public final static int WORLD_WIDTH = 1000;
    public final static int WORLD_HEIGHT = 800;
    public final static String SCENERY_ATLAS_FILENAME = "scenery.atlas";
    public final static String FISH_ATLAS_FILENAME = "fish.atlas";
    public final static String NUMBERS_ATLAS_FILENAME = "numbers.atlas";
    private GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);

        this.setScreen(gameScreen);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}
