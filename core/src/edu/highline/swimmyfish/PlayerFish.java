package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerFish extends Actor {
    private static final float GRAVITY = -1000;
    private static final float MOVEMENT_SPEED = 200;
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
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void update(float deltaTime) {
        move(deltaTime);
        keepInBounds();
        //rotate(deltaTime);
    }

    private void move(float deltaTime) {
        setX(getX() + MOVEMENT_SPEED * deltaTime);
        setY(getY() + (yVelocity + deltaTime * GRAVITY / 2) * deltaTime);
        yVelocity += GRAVITY * deltaTime;
        int terminalVelocity = -800;
        if (yVelocity < terminalVelocity) {
            yVelocity = terminalVelocity;
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
        yVelocity = 450;
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
