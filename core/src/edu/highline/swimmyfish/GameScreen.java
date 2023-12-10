package edu.highline.swimmyfish;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    public final SwimmyFish game;
    private final GameWorld world;

    public GameScreen(final SwimmyFish game) {
        this.game = game;
        world = new GameWorld(this);
    }

    @Override
    public void show() {}

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(Color.BLACK);

        world.update(deltaTime);
        game.stage.draw();
    }

    @Override
    public void resize(int ignoredWidth, int ignoredHeight) {
        // do nothing
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        world.dispose();
    }

}
