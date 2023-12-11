package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import static edu.highline.swimmyfish.SwimmyFish.SCENERY_ATLAS_FILENAME;

public class Background extends Actor {
    private final SwimmyFish game;
    private final TextureAtlas atlas;
    private final Array<TextureAtlas.AtlasRegion> sandTiles;
    private final TextureAtlas.AtlasRegion oceanTile;
    private final TextureAtlas.AtlasRegion waveTile;
    private final int tileSize;
    private final TextureAtlas.AtlasRegion star;

    public Background(SwimmyFish game) {
        this.game = game;
        atlas = new TextureAtlas(Gdx.files.internal(SCENERY_ATLAS_FILENAME));
        sandTiles = atlas.findRegions("sand");
        oceanTile = atlas.findRegion("ocean");
        waveTile = atlas.findRegion("wave");
        tileSize = 100;

        star = atlas.findRegion("star");
    }

    public void dispose() {
        atlas.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ScreenUtils.clear(new Color(0xEBF9FCff), false);
        batch.setColor(1, 1, 1, 0.6f * parentAlpha);

        float x = game.camera.position.x - game.camera.viewportWidth / 2;

        Array<TextureAtlas.AtlasRegion> orderedSandTiles = new Array<>();
        orderedSandTiles.add(sandTiles.get(1));
        orderedSandTiles.add(sandTiles.get(0));
        orderedSandTiles.add(sandTiles.get(1));
        orderedSandTiles.add(sandTiles.get(6));
        orderedSandTiles.add(sandTiles.get(3));
        orderedSandTiles.add(sandTiles.get(9));
        orderedSandTiles.add(sandTiles.get(8));
        orderedSandTiles.add(sandTiles.get(7));
        orderedSandTiles.add(sandTiles.get(8));
        orderedSandTiles.add(sandTiles.get(9));
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 10; i++) {
                batch.draw(oceanTile, x + tileSize * i, tileSize * (j + 1), tileSize, tileSize);
            }
        }

        batch.draw(star, x + 350, 90, star.getRegionWidth(), star.getRegionHeight());

        for (int i = 0; i < 10; i++) {
            batch.draw(oceanTile, x + tileSize * i, 0, tileSize, tileSize);
            batch.draw(orderedSandTiles.get(i), x + tileSize * i, 0, tileSize, tileSize);
        }


        for (int i = 0; i < 10; i++) {
            batch.draw(waveTile, x + tileSize * i, game.camera.viewportHeight - tileSize, tileSize,
                       tileSize);
        }
    }
}
