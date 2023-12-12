package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static edu.highline.swimmyfish.SwimmyFish.FISH_ATLAS_FILENAME;

public class DeathView extends Actor {
    private final SwimmyFish game;
    private final TextureAtlas atlas;
    private final TextureRegion skeleton;

    public DeathView(SwimmyFish game) {
        this.game = game;
        atlas = new TextureAtlas(Gdx.files.internal(FISH_ATLAS_FILENAME));
        skeleton = atlas.findRegion("dead fish");
    }

    @Override
    public void draw(Batch batch, float ignoredParentAlpha) {
        batch.draw(skeleton, game.camera.position.x - skeleton.getRegionWidth() / 2f,game.viewport.getWorldHeight() - skeleton.getRegionHeight() * 2f);
    }

    public void dispose() {
        atlas.dispose();
    }
}
