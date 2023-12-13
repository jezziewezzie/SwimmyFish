package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen {
    public final SwimmyFish game;
    public Stage gameStage;
    public Stage menuStage;
    public final InputMultiplexer inputMultiplexer;
    private GameWorld world;
    public Overlay overlay;
    public InputProcessor startInputProcessor;
    public InputProcessor gameInputProcessor;
    public InputProcessor gameOverInputProcessor;
    public GameOver gameOver;
    public Start start;
    private final Sound deathSound;
    private boolean paused;

    public GameScreen(final SwimmyFish game) {
        this.game = game;
        gameStage = new Stage(this.game.viewport);
        menuStage = new Stage(this.game.viewport);
        inputMultiplexer = new InputMultiplexer();
        world = new GameWorld(game, this);
        inputMultiplexer.addProcessor(startInputProcessor);
        inputMultiplexer.addProcessor(gameInputProcessor);
        inputMultiplexer.addProcessor(gameOverInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        gameOver = new GameOver(this.game);
        gameOver.setVisible(false);
        menuStage.addActor(gameOver);

        start = new Start(game, new CountdownCallback() {
            @Override
            public void onFinish() {
                setState(State.PLAYING);
            }
        });
        menuStage.addActor(start);

        deathSound = Gdx.audio.newSound(Gdx.files.internal("death.wav"));

        paused = true;

        setState(State.START);
    }

    public void setState(State state) {
        switch (state) {
            case START:
                world = new GameWorld(game, this);
                overlay.setVisible(false);
                gameOver.setVisible(false);
                inputMultiplexer.setProcessors(startInputProcessor);
                paused = false;
                break;
            case PLAYING:
                gameOver.setVisible(false);
                start.setVisible(false);
                inputMultiplexer.setProcessors(gameInputProcessor);
                break;
            case GAME_OVER:
                showGameOverView();
                break;
        }
    }

    public void showGameOverView() {
        deathSound.play();
        pause();
        gameOver.setVisible(true);
        inputMultiplexer.setProcessors(gameOverInputProcessor);
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

        gameStage.draw();
        menuStage.draw();
    }

    @Override
    public void resize(int ignoredWidth, int ignoredHeight) {
        // do nothing
    }

    @Override
    public void pause() {
        paused = true;
        overlay.setVisible(true);
    }

    @Override
    public void resume() {
        paused = false;
        overlay.setVisible(false);
    }

    @Override
    public void hide() {
        pause();
    }

    @Override
    public void dispose() {
        deathSound.dispose();
        gameStage.dispose();
        menuStage.dispose();
        world.dispose();
        overlay.dispose();
        gameOver.dispose();
    }

    public enum State {
        START, PLAYING, GAME_OVER
    }
}
