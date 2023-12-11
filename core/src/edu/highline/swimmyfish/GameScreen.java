package edu.highline.swimmyfish;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static edu.highline.swimmyfish.SwimmyFish.WORLD_HEIGHT;
import static edu.highline.swimmyfish.SwimmyFish.WORLD_WIDTH;

public class GameScreen implements Screen {
    public final SwimmyFish game;
    public final Camera camera;
    public final Viewport viewport;
    public final Stage stage;
    private final GameWorld world;

    public GameScreen(final SwimmyFish game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        stage = new Stage(viewport);
        world = new GameWorld(this);
    }

    @Override
    public void show() {}

    @Override
    public void render(float deltaTime) {
        world.update(deltaTime);
        stage.draw();
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
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

}
