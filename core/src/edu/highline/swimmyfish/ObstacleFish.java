package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class ObstacleFish extends Actor {
    private final SwimmyFish game;
    private final Texture headTexture;
    private final Texture bodyTexture;
    private final TopOrBottom topOrBottom;
    private Sprite headSprite;
    private ArrayList<Sprite> bodySprites;
    private int bodySegments;

    ObstacleFish(SwimmyFish game, int totalPossibleBodySegments,
                 TopOrBottom topOrBottom)
    {
        this.game = game;
        this.topOrBottom = topOrBottom;

        headTexture =
                new Texture(Gdx.files.internal("long tang fish head alt.png"));
        headTexture.setFilter(Texture.TextureFilter.Linear,
                              Texture.TextureFilter.Linear);
        bodyTexture = new Texture(Gdx.files.internal(
                "long tang fish middle alt" + ".png"));
        bodyTexture.setFilter(Texture.TextureFilter.Linear,
                              Texture.TextureFilter.Linear);

        headSprite = new Sprite(headTexture);
        if (topOrBottom == TopOrBottom.TOP) {
            headSprite.setRotation(180);
        }

        bodySprites = new ArrayList<>();
        for (int i = 0; i < totalPossibleBodySegments; i++) {
            bodySprites.add(new Sprite(bodyTexture));
        }

        setWidth(headTexture.getWidth());
    }

    public void constructFish(int bodySegments, float x) {
        if (topOrBottom == TopOrBottom.BOTTOM) {
            constructBottomFish(bodySegments, x);
        } else {
            constructTopFish(bodySegments, x);
        }
    }

    private void constructBottomFish(int bodySegments, float x) {
        this.bodySegments = bodySegments;

        float height = 0;
        for (int i = 0; i < bodySegments; i++) {
            Sprite sprite = bodySprites.get(i);
            sprite.setX(x);
            sprite.setY(height);
            height += sprite.getHeight();
        }
        headSprite.setX(x);
        headSprite.setY(height);
        height += headSprite.getHeight();

        setHeight(height);
        setX(x);
        setY(0);
    }

    private void constructTopFish(int bodySegments, float x) {
        this.bodySegments = bodySegments;

        float height = game.stage.getHeight();
        for (int i = 0; i < bodySegments; i++) {
            Sprite sprite = bodySprites.get(i);
            sprite.setX(x);
            sprite.setY(height - sprite.getHeight());
            height -= sprite.getHeight();
        }
        headSprite.setX(x);
        height -= headSprite.getHeight();
        headSprite.setY(height);

        setHeight(height);
        setX(x);
        setY(game.stage.getHeight() - height);
    }

    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
    }

    @Override
    public void draw(Batch batch, float ignoredParentAlpha) {
        headSprite.draw(batch);
        for (int i = 0; i < bodySegments; i++) {
            Sprite sprite = bodySprites.get(i);
            sprite.draw(batch);
        }
    }

    public enum TopOrBottom {
        TOP, BOTTOM
    }
}
