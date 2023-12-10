package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class GameWorld {
    public static final int OBSTACLE_SPACING = 300;
    public static final int NUMBER_OF_OBSTACLES = 6;
    private final SwimmyFish game;
    private final PlayerFish player;
    private final ArrayList<ObstacleFishPair> obstacles;

    public GameWorld(GameScreen gameScreen) {
        game = gameScreen.game;
        player = new PlayerFish(game);
        player.setY(game.camera.viewportHeight / 2, Align.center);
        game.stage.addActor(player);
        game.stage.setKeyboardFocus(player);

        obstacles = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_OBSTACLES; i++) {
            float obstacleWidth =
                    new ObstacleFish(game, false, 0, 0, 0).getWidth();
            ObstacleFishPair obstacle = new ObstacleFishPair(game,
                                                             (OBSTACLE_SPACING
                                                                     + obstacleWidth)
                                                                     * i);
            game.stage.addActor(obstacle.getBottomFish());
            game.stage.addActor(obstacle.getTopFish());
            obstacles.add(obstacle);
        }
    }

    public void update(float deltaTime) {
        handleInput();
        player.update(deltaTime);

        game.camera.position.x = player.getX() + OBSTACLE_SPACING;
        for (ObstacleFishPair obstacle : obstacles) {
            if (game.camera.position.x - game.camera.viewportWidth / 2
                    > obstacle.getX() + obstacle.getWidth())
            {
                obstacle.update(obstacle.getX() + (obstacle.getWidth()
                        + OBSTACLE_SPACING) * NUMBER_OF_OBSTACLES);
            }
//            if (player.getBounds()
//                      .overlaps(obstacle.getBottomFish().getBounds()))
//            {
//                System.out.println("player " + player.getBounds());
//                System.out.println(
//                        "bottom " + obstacle.getBottomFish().getBounds());
//                System.out.println();
//                game.setScreen(new GameScreen(game));
//            }
//            if (player.getBounds()
//                      .overlaps(obstacle.getTopFish().getBounds()))
//            {
//                System.out.println("player " + player.getBounds());
//                System.out.println("top " + obstacle.getTopFish().getBounds());
//                System.out.println();
//                game.setScreen(new GameScreen(game));
//            }
        }
        game.camera.update();
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
        player.dispose();
        for (ObstacleFishPair obstacle : obstacles) {
            obstacle.dispose();
        }
    }
}
