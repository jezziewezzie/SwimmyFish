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
    private final SwimmyFish game;
    private final GameScreen gameScreen;
    private final Background background;
    private final PlayerFish player;
    private final Score score;
    private final Array<ObstacleFishPair> obstacles;
    private ObstacleFishPair closestObstacle;
    private final DeathView deathView;

    public GameWorld(SwimmyFish game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        background = new Background(this.game);

        gameScreen.gameGroup.addActor(background);

        obstacles = new Array<>();
        float obstacleWidth = new ObstacleFish(gameScreen, false, 0, 0, 0).getWidth();
        for (int i = 1; i <= NUMBER_OF_OBSTACLES; i++) {
            ObstacleFishPair obstacle =
                    new ObstacleFishPair(gameScreen, (OBSTACLE_SPACING + obstacleWidth) * i);
            gameScreen.gameGroup.addActor(obstacle.getBottomFish());
            gameScreen.gameGroup.addActor(obstacle.getTopFish());
            obstacles.add(obstacle);
        }
        closestObstacle = obstacles.first();

        player = new PlayerFish(this.game);
        player.setX(OBSTACLE_SPACING / -2f);
        player.setY(game.camera.viewportHeight / 2, Align.center);

        gameScreen.gameGroup.addActor(player);
        gameScreen.stage.setKeyboardFocus(player);

        score = new Score(this.game);

        gameScreen.gameGroup.addActor(score);

        deathView = new DeathView(this.game);

        gameScreen.deathViewGroup.addActor(deathView);

        gameScreen.stage.addActor(gameScreen.gameGroup);
        gameScreen.stage.addActor(gameScreen.deathViewGroup);

        gameScreen.gameInputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    player.jump();
                    return true;
                } else if (keycode == Input.Keys.ESCAPE) {
                    if (gameScreen.isPaused()) {
                        gameScreen.resume();
                    } else {
                        gameScreen.pause();
                    }
                    return true;
                }
                return false;
            }
        };

        gameScreen.deathViewInputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    gameScreen.setState(GameScreen.State.START);
                    return true;
                }
                return false;
            }
        };
    }

    public void update(float deltaTime) {
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
                gameScreen.playerDied();
            }
            if (player.getY() < 0 - player.getHeight()) {
                gameScreen.playerDied();
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

    public void dispose() {
        background.dispose();
        for (ObstacleFishPair obstacle : new Array.ArrayIterator<>(obstacles)) {
            obstacle.dispose();
        }
        player.dispose();
        score.dispose();
        deathView.dispose();
    }
}
