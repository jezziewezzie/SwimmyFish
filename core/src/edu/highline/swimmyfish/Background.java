package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import static edu.highline.swimmyfish.SwimmyFish.SCENERY_ATLAS_FILENAME;

public class Background extends Actor {
    private final GameScreen gameScreen;
    private final TextureAtlas atlas;
    private final Array<Sprite> sandTiles;
    private final Sprite oceanTile;
    private final Sprite waveTile;
    private final int tileSize;
    private final Sprite star;

    public Background(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
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
        ScreenUtils.clear(new Color(0xEBF9FCff), false);

        float x = gameScreen.camera.position.x - gameScreen.camera.viewportWidth / 2;

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
            waveTile.setBounds(x + tileSize * i, gameScreen.camera.viewportHeight - tileSize * 1.5f, tileSize,
                               tileSize);
            waveTile.draw(batch);
        }
    }
}
