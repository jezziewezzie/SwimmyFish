package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class ObstacleFish extends Actor {
    private final SwimmyFish game;
    private final Texture headTexture;
    private final Texture bodyTexture;
    private final boolean isTop;
    private final Sprite headSprite;
    private final ArrayList<Sprite> bodySprites;
    private int bodySegments;

    public ObstacleFish(SwimmyFish game, boolean isTop,
                        int totalPossibleBodySegments, int bodySegments,
                        float x)
    {
        this.game = game;
        this.isTop = isTop;
        this.bodySegments = bodySegments;

        headTexture =
                new Texture(Gdx.files.internal("obstacle fish head.png"));
        headTexture.setFilter(Texture.TextureFilter.Linear,
                              Texture.TextureFilter.Linear);
        bodyTexture = new Texture(Gdx.files.internal(
                "obstacle fish body.png"));
        bodyTexture.setFilter(Texture.TextureFilter.Linear,
                              Texture.TextureFilter.Linear);

        headSprite = new Sprite(headTexture);
        if (isTop) {
            headSprite.setRotation(180);
            headSprite.setFlip(true, false);
        }

        bodySprites = new ArrayList<>();
        for (int i = 0; i < totalPossibleBodySegments; i++) {
            bodySprites.add(new Sprite(bodyTexture));
        }

        setWidth(bodyTexture.getWidth());
        constructFish(bodySegments, x);
    }

    public void constructFish(int bodySegments, float x) {
        if (isTop) {
            constructTopFish(bodySegments, x);
        } else {
            constructBottomFish(bodySegments, x);
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

    public Rectangle getBounds() {
        Rectangle bounds = headSprite.getBoundingRectangle();
        for (int i = 0; i < bodySegments; i++) {
            bounds = bounds.merge(bodySprites.get(i).getBoundingRectangle());
            bounds.setWidth(bodySprites.get(i).getWidth());
        }
        return bounds;
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
}
