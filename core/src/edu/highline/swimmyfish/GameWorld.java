package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GameWorld {
    public static final float GRAVITY = -120;
    public static final float MOVEMENT_SPEED = 20;
    public static final float OBSTACLE_DISTANCE = 400;
    public static final float NUMBER_OF_OBSTACLES = 4;
    private final SwimmyFish game;
    private final PlayerFish playerFish;
    private Stack<ObstacleFishPair> obstacles;

    public GameWorld(GameScreen gameScreen) {
        game = gameScreen.game;
        playerFish = new PlayerFish(game);
        game.stage.addActor(playerFish);
        game.stage.setKeyboardFocus(playerFish);

        obstacles = new Stack<>();
        for (int i = 1; i <= NUMBER_OF_OBSTACLES; i++) {
            ObstacleFishPair obstacle = new ObstacleFishPair(game);
            game.stage.addActor(obstacle.getBottomFish());
            game.stage.addActor(obstacle.getTopFish());
            obstacles.push(obstacle);
        }
    }

    private void handleInput() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    playerFish.jump();
                    return true;
                }
                return false;
            }
        });
    }

    public void update(float deltaTime) {
        handleInput();
        playerFish.update(deltaTime);
        game.camera.position.x = playerFish.getX() + 400;
        game.camera.update();
    }

    public void dispose() {
        playerFish.dispose();
        //obstacleFish.dispose();
    }
}
