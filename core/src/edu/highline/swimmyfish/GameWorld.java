package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayDeque;

public class GameWorld {
    public static final int OBSTACLE_SPACING = 300;
    public static final int NUMBER_OF_OBSTACLES = 6;
    private final SwimmyFish game;
    private final Background background;
    private final PlayerFish player;
    private final Score score;
    private final Array<ObstacleFishPair> obstacles;
    private ObstacleFishPair closestObstacle;

    public GameWorld(GameScreen gameScreen) {
        game = gameScreen.game;

        background = new Background(game);
        game.stage.addActor(background);

        player = new PlayerFish(game);
        player.setX(OBSTACLE_SPACING / -2f);
        player.setY(game.camera.viewportHeight / 2, Align.center);

        score = new Score(game);

        obstacles = new Array<>();
        float obstacleWidth = new ObstacleFish(game, false, 0, 0, 0).getWidth();
        for (int i = 1; i <= NUMBER_OF_OBSTACLES; i++) {
            ObstacleFishPair obstacle =
                    new ObstacleFishPair(game, (OBSTACLE_SPACING + obstacleWidth) * i);
            game.stage.addActor(obstacle.getBottomFish());
            game.stage.addActor(obstacle.getTopFish());
            obstacles.add(obstacle);
        }
        closestObstacle = obstacles.first();

        game.stage.addActor(player);
        game.stage.setKeyboardFocus(player);
        game.stage.addActor(score);
    }

    public void update(float deltaTime) {
        handleInput();
        player.update(deltaTime);
        game.camera.position.x = player.getX() + OBSTACLE_SPACING;
        game.camera.update();

        for (ObstacleFishPair obstacle : new Array.ArrayIterator<>(obstacles)) {
            if (game.camera.position.x - game.camera.viewportWidth / 2 >
                obstacle.getX() + obstacle.getWidth())
            {
                obstacle.update(obstacle.getX() +
                                (obstacle.getWidth() + OBSTACLE_SPACING) * NUMBER_OF_OBSTACLES);
            }
            if (Intersector.overlaps(player.getBounds(), obstacle.getBottomFish().getBounds()) ||
                Intersector.overlaps(player.getBounds(), obstacle.getTopFish().getBounds()))
            {
                game.setScreen(new GameScreen(game));
            }
        }

        if (player.getX(Align.center) > closestObstacle.getX() + closestObstacle.getWidth()) {
            if (!closestObstacle.isPassed()) {
                score.update();
                player.increaseSpeed();

                closestObstacle.setPassed(true);
                obstacles.removeValue(closestObstacle, true);
                obstacles.add(closestObstacle);
                closestObstacle = obstacles.first();
                closestObstacle.setPassed(false);
            }
        }
    }

    private void handleInput() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    player.jump();
                    return true;
                }
                return false;
            }
        });
    }

    public void dispose() {
        background.dispose();
        for (ObstacleFishPair obstacle : new Array.ArrayIterator<>(obstacles)) {
            obstacle.dispose();
        }
        player.dispose();
        score.dispose();
    }
}
