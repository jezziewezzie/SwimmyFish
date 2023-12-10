package edu.highline.swimmyfish;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SwimmyFish extends Game {
    protected final static int WORLD_WIDTH = 1000;
    protected final static int WORLD_HEIGHT = 800;
    protected Camera camera;
    protected Viewport viewport;
    protected Stage stage;
    private GameScreen gameScreen;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        stage = new Stage(viewport);
        gameScreen = new GameScreen(this);

        this.setScreen(gameScreen);
    }

    @Override
    public void dispose() {
        stage.dispose();
        gameScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}
