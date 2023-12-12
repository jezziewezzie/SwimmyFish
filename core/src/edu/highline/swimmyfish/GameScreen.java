package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    public final SwimmyFish game;
    public Stage stage;
    public final InputMultiplexer inputMultiplexer;
    public InputProcessor gameInputProcessor;
    public InputProcessor deathViewInputProcessor;
    private GameWorld world;
    public Group gameGroup;
    public Group deathViewGroup;
    private boolean paused;
    private final Sound deathSound;

    public GameScreen(final SwimmyFish game) {
        this.game = game;
        stage = new Stage(this.game.viewport);
        gameGroup = new Group();
        deathViewGroup = new Group();
        deathViewGroup.setVisible(false);
        deathSound = Gdx.audio.newSound(Gdx.files.internal("death.wav"));

        inputMultiplexer = new InputMultiplexer();
        world = new GameWorld(game, this);
        inputMultiplexer.addProcessor(gameInputProcessor);
        inputMultiplexer.addProcessor(deathViewInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        paused = true;

        setState(State.GAME);
    }

    public void setState(State state) {
        switch (state) {
            case START:
                world = new GameWorld(game, this);
                deathViewGroup.setVisible(false);
                inputMultiplexer.setProcessors(gameInputProcessor);
                paused = false;
                break;
            case GAME:
                deathViewGroup.setVisible(false);
                inputMultiplexer.setProcessors(gameInputProcessor);
                break;
            case DEAD:
                playerDied();
                break;
        }
    }

    public void playerDied() {
        pause();
        inputMultiplexer.setProcessors(deathViewInputProcessor);
        deathSound.play();
        ScreenUtils.clear(1, 1, 1, 1);
        deathViewGroup.setVisible(true);
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void show() {
        paused = false;
    }

    @Override
    public void render(float deltaTime) {
        if (!paused) {
            world.update(deltaTime);
        }

        stage.draw();

        if (paused) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            ShapeRenderer renderer = new ShapeRenderer();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(1, 1, 1, 0.3f);
            renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            renderer.end();
        }
    }

    @Override
    public void resize(int ignoredWidth, int ignoredHeight) {
        // do nothing
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
        pause();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        deathSound.dispose();
    }

    public enum State {
        START, GAME, DEAD
    }
}
