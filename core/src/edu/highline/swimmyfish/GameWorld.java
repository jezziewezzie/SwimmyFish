package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class GameWorld {
    public static final int OBSTACLE_SPACING = 300;
    public static final int NUMBER_OF_OBSTACLES = 6;
    private final GameScreen gameScreen;
    private final Background background;
    private final PlayerFish player;
    private final Score score;
    private final Array<ObstacleFishPair> obstacles;
    private ObstacleFishPair closestObstacle;

    public GameWorld(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        background = new Background(this.gameScreen);
        this.gameScreen.stage.addActor(background);

        player = new PlayerFish(this.gameScreen);
        player.setX(OBSTACLE_SPACING / -2f);
        player.setY(gameScreen.camera.viewportHeight / 2, Align.center);

        score = new Score(this.gameScreen);

        obstacles = new Array<>();
        float obstacleWidth = new ObstacleFish(this.gameScreen, false, 0, 0, 0).getWidth();
        for (int i = 1; i <= NUMBER_OF_OBSTACLES; i++) {
            ObstacleFishPair obstacle =
                    new ObstacleFishPair(this.gameScreen, (OBSTACLE_SPACING + obstacleWidth) * i);
            this.gameScreen.stage.addActor(obstacle.getBottomFish());
            this.gameScreen.stage.addActor(obstacle.getTopFish());
            obstacles.add(obstacle);
        }
        closestObstacle = obstacles.first();

        this.gameScreen.stage.addActor(player);
        this.gameScreen.stage.setKeyboardFocus(player);
        this.gameScreen.stage.addActor(score);
    }

    public void update(float deltaTime) {
        handleInput();
        player.update(deltaTime);
        gameScreen.camera.position.x = player.getX() + OBSTACLE_SPACING;
        gameScreen.camera.update();

        for (ObstacleFishPair obstacle : new Array.ArrayIterator<>(obstacles)) {
            if (gameScreen.camera.position.x - gameScreen.camera.viewportWidth / 2 >
                obstacle.getX() + obstacle.getWidth())
            {
                obstacle.update(obstacle.getX() +
                                (obstacle.getWidth() + OBSTACLE_SPACING) * NUMBER_OF_OBSTACLES);
            }
            if (Intersector.overlaps(player.getBounds(), obstacle.getBottomFish().getBounds()) ||
                Intersector.overlaps(player.getBounds(), obstacle.getTopFish().getBounds()))
            {
                gameScreen.game.setScreen(new GameScreen(gameScreen.game));
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
