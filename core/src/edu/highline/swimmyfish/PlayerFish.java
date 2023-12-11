package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static edu.highline.swimmyfish.SwimmyFish.FISH_ATLAS_FILENAME;

public class PlayerFish extends Actor {
    private static final float GRAVITY = -1050;
    private final GameScreen gameScreen;
    private final TextureAtlas atlas;
    private final TextureRegion region;
    private int movementSpeed;
    private float yVelocity;

    public PlayerFish(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        atlas = new TextureAtlas(Gdx.files.internal(FISH_ATLAS_FILENAME));
        region = atlas.findRegion("player fish");
        setBounds(region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
                  region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        movementSpeed = 200;
        yVelocity = 0;
    }

    public Circle getBounds() {
        float radius = Math.min(getWidth(), getHeight()) / 2;
        radius -= 8; // make collision a bit more forgiving
        float centerX = getX() + getWidth() / 2;
        float centerY = getY() + getHeight() / 2;
        return new Circle(centerX, centerY, radius);
    }

    public void increaseSpeed() {
        movementSpeed += 5;
    }

    public void update(float deltaTime) {
        move(deltaTime);
        //rotate(deltaTime);
    }

    private void move(float deltaTime) {
        setX(getX() + movementSpeed * deltaTime);
        setY(getY() + (yVelocity + deltaTime * GRAVITY / 2) * deltaTime);
        yVelocity += GRAVITY * deltaTime;
        int terminalVelocity = -850;
        if (yVelocity < terminalVelocity) {
            yVelocity = terminalVelocity;
        }
        if (getY() > gameScreen.viewport.getWorldHeight() - 75) {
            yVelocity *= 0.25f * deltaTime;
        }
        if (getY() < 0 - getHeight()) {
            gameScreen.game.setScreen(new GameScreen(gameScreen.game));
        }
    }

    private void rotate(float deltaTime) {
        if (yVelocity < 0) {
            setRotation(getRotation() - 480 * deltaTime);
            if (getRotation() < -80) {
                setRotation(-80);
            }
        } else {
            setRotation(getRotation() + 320 * deltaTime * 2);
            if (getRotation() > 60) {
                setRotation(60);
            }
        }
    }

    public void jump() {
        yVelocity = 460;
    }

    public void dispose() {
        atlas.dispose();
    }

    @Override
    public void draw(Batch batch, float ignoredParentAlpha) {
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                   getScaleX(), getScaleY(), getRotation());
    }
}
