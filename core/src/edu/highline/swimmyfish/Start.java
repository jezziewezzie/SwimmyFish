package edu.highline.swimmyfish;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Start extends Actor {
    private final SwimmyFish game;
    private CountdownCallback callback;
    private final TextureRegion[] frames;
    private int currentFrame;
    private float timeElapsed;

    public Start(SwimmyFish game, CountdownCallback callback) {
        this.game = game;
        this.callback = callback;
        frames = new TextureRegion[]{game.atlas.findRegion("big one"),
                                               game.atlas.findRegion("big two"),
                                               game.atlas.findRegion("big three")};
        currentFrame = 2;
        timeElapsed = 0;
        setCurrentFrameDimensions();
    }

    private void setCurrentFrameDimensions() {
        setWidth(frames[currentFrame].getRegionWidth());
        setHeight(frames[currentFrame].getRegionHeight());
        setY(game.camera.viewportHeight / 2 - getHeight() / 2);
    }

    public void reset() {
        currentFrame = 2;
        timeElapsed = 0;
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);

        timeElapsed += deltaTime;
        if (timeElapsed > 1 && currentFrame > 0) {
            currentFrame--;
            setCurrentFrameDimensions();
            timeElapsed = 0;
        }
        Color color = getColor();
        if (color.a > 0) {
            color.a -= deltaTime;
            setColor(color);
        }
        if (currentFrame == 0) {
            callback.onFinish();
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float ignoredParentAlpha) {
        batch.setColor(getColor());
        batch.draw(frames[currentFrame], game.camera.position.x - frames[currentFrame].getRegionWidth() / 2f, getY(), getWidth(), getHeight());
        batch.setColor(Color.WHITE);
    }
}
