package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import static edu.highline.swimmyfish.SwimmyFish.SCENERY_ATLAS_FILENAME;

public class Background extends Actor {
    private final SwimmyFish game;
    private final ShapeRenderer renderer;
    private final TextureAtlas atlas;
    private final int tileSize;
    private final Array<Sprite> sandTiles;
    private final Sprite oceanTile;
    private final Sprite waveTile;
    private final Sprite star;

    public Background(SwimmyFish game) {
        this.game = game;
        renderer = new ShapeRenderer();
        atlas = new TextureAtlas(Gdx.files.internal(SCENERY_ATLAS_FILENAME));
        tileSize = 100;
        sandTiles = atlas.createSprites("sand");
        oceanTile = atlas.createSprite("ocean");
        waveTile = atlas.createSprite("wave");
        waveTile.setRegionHeight(waveTile.getRegionHeight() + tileSize);

        star = atlas.createSprite("star");
    }

    public void dispose() {
        atlas.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = game.camera.position.x - game.camera.viewportWidth / 2;

        ScreenUtils.clear(new Color(0xEBF9FCff));
//        batch.end();
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(new Color(0xEBF9FCff));
//        renderer.rect(x, 0, game.camera.viewportWidth, game.camera.viewportHeight);
//        renderer.end();
//        batch.begin();

        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 10; i++) {
                oceanTile.setBounds(x + tileSize * i, tileSize * (j + 1), tileSize, tileSize);
                oceanTile.draw(batch);
            }
        }

        Array<Sprite> orderedSandTiles = new Array<>();
        orderedSandTiles.add(sandTiles.get(9));
        orderedSandTiles.add(sandTiles.get(6));
        orderedSandTiles.add(sandTiles.get(2));
        orderedSandTiles.add(sandTiles.get(8));
        orderedSandTiles.add(sandTiles.get(7));
        orderedSandTiles.add(sandTiles.get(6));
        orderedSandTiles.add(sandTiles.get(7));
        orderedSandTiles.add(sandTiles.get(8));
        orderedSandTiles.add(sandTiles.get(9));
        orderedSandTiles.add(sandTiles.get(2));
        for (int i = 0; i < 10; i++) {
            oceanTile.setBounds(x + tileSize * i, 0, tileSize, tileSize);
            oceanTile.draw(batch);
            Sprite sandTile = orderedSandTiles.get(i);
            sandTile.setBounds(x + tileSize * i, 0, tileSize, tileSize);
            sandTile.draw(batch);
        }

//        star.setBounds(x + 350, 90, star.getWidth(), star.getHeight());
//        star.draw(batch);

        for (int i = 0; i < 10; i++) {
            waveTile.setBounds(x + tileSize * i, game.camera.viewportHeight - tileSize * 1.5f, tileSize,
                               tileSize);
            waveTile.draw(batch);
        }
    }
}
