package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static edu.highline.swimmyfish.SwimmyFish.WORLD_HEIGHT;
import static edu.highline.swimmyfish.SwimmyFish.WORLD_WIDTH;

public class GameScreen implements Screen {
    private final SwimmyFish game;

    private final Stage stage;
    private final Player player;

    public GameScreen(final SwimmyFish game) {
        this.game = game;
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        player = new Player();
        stage.addActor(player);
        stage.setKeyboardFocus(player);

        player.setPosition(30, (float) WORLD_HEIGHT / 2);

        MoveByAction jump = Actions.action(MoveByAction.class);

        player.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (character == Keys.SPACE) {

                }
                return false;
            }
        });
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {

        }
    }

    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(width, height, true);
        //game.generateRegions(Gdx.graphics.getBackBufferWidth(),
        //                     Gdx.graphics.getBackBufferHeight());
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    class Player extends Actor {
        TextureRegion region;

        public Player() {
            region = game.getRegion("red fish");
            setBounds(region.getRegionX(), region.getRegionY(),
                      region.getRegionWidth(), region.getRegionHeight());
            System.out.println(getWidth() + "x" + getHeight());
        }

        @Override
        public void draw(Batch batch, float ignoredParentAlpha) {
            batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                       getWidth(), getHeight(), getScaleX(), getScaleY(),
                       getRotation());
        }
    }
}
