package edu.highline.swimmyfish;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mazatech.gdx.*;
import com.mazatech.svgt.SVGAssets;
import com.mazatech.svgt.SVGScaler;
import com.mazatech.svgt.SVGScalerMatchMode;

import java.util.Objects;

public class SwimmyFish extends Game {
    protected final static int WORLD_WIDTH = 1000;
    protected final static int WORLD_HEIGHT = 800;

    protected SVGAssetsGDX svg;
    protected SVGTextureAtlasGenerator atlasGenerator;
    protected SVGTextureAtlas atlas;
    protected SVGTextureAtlasRegion[] regions;

    @Override
    public void create() {
        int screenWidth = Gdx.graphics.getBackBufferWidth();
        int screenHeight = Gdx.graphics.getBackBufferHeight();
        svg = new SVGAssetsGDX(
                new SVGAssetsConfigGDX(screenWidth, screenHeight,
                                       Gdx.graphics.getPpiX()));
        atlasGenerator = svg.createAtlasGenerator(1, 1, true);
        atlasGenerator.setPow2Textures(true);
        atlasGenerator.add("texture.svg", true, 1);
        generateRegions(screenWidth, screenHeight);

        amanithsvgInfoDisplay();
        this.setScreen(new GameScreen(this));
    }

    private void amanithsvgInfoDisplay() {
        Gdx.app.log(this.getClass().getSimpleName(),
                    "AmanithSVG version = " + SVGAssets.getVersion());
    }

    protected void generateRegions(int screenWidth, int screenHeight) {
        SVGScaler scaler =
                new SVGScaler(WORLD_WIDTH, WORLD_HEIGHT,
                              SVGScalerMatchMode.MatchWidthOrHeight, 0.5f, 1);
        float scale = scaler.scaleFactorCalc(screenWidth, screenHeight);
        atlasGenerator.setScale(scale);
        if (atlas != null) {
            atlas.dispose();
        }
        try {
            atlas = atlasGenerator.generateAtlas();
        } catch (SVGTextureAtlasGenerator.SVGTextureAtlasPackingException e) {
            throw new RuntimeException(e);
        }
        // our SVG file will only ever have one page
        regions = atlas.getPages()[0].getRegions();
    }

    TextureRegion getRegion(String regionName) {
        SVGTextureAtlasRegion region = null;
        for (SVGTextureAtlasRegion r : regions) {
            if (Objects.equals(r.getElemName(), regionName)) {
                region = r;
            }
        }
        if (region == null) {
            throw new IllegalArgumentException("texture region not found");
        }

        return region;
    }

    @Override
    public void dispose() {
        svg.dispose();
        atlasGenerator.dispose();
        atlas.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

}
