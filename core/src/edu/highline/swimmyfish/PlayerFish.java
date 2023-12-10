package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static edu.highline.swimmyfish.GameWorld.GRAVITY;
import static edu.highline.swimmyfish.GameWorld.MOVEMENT_SPEED;

public class PlayerFish extends Actor {
    private final SwimmyFish game;
    private final Texture texture;
    private final TextureRegion region;
    private float yVelocity;

    public PlayerFish(SwimmyFish game) {
        this.game = game;
        texture = new Texture(Gdx.files.internal("red fish alt.png"));
        texture.setFilter(Texture.TextureFilter.Linear,
                          Texture.TextureFilter.Linear);
        region = new TextureRegion(texture);
        setBounds(region.getRegionX(), region.getRegionY(),
                  region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        yVelocity = 0;

        //setPosition(1, (float) WORLD_HEIGHT / 2);
    }

    public void update(float deltaTime) {
        move(deltaTime);
        keepInBounds();
        rotate(deltaTime);
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

    private void keepInBounds() {
        if (getY() < 0) {
            setY(0);
        }
        if (getY() > game.viewport.getWorldHeight() - getHeight()) {
            setY(game.viewport.getWorldHeight() - getHeight());
        }
    }

    private void move(float deltaTime) {
        //setX(getX() + MOVEMENT_SPEED * deltaTime);
        setY(getY() + (yVelocity + deltaTime * GRAVITY / 2) * deltaTime);
        yVelocity += GRAVITY * deltaTime;
        if (yVelocity < -56) {
            yVelocity = -56;
        }
    }

    public void jump() {
        yVelocity = 640;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public void draw(Batch batch, float ignoredParentAlpha) {
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                   getWidth(), getHeight(), getScaleX(), getScaleY(),
                   getRotation());
    }
}
