package edu.highline.swimmyfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import static edu.highline.swimmyfish.SwimmyFish.NUMBERS_ATLAS_FILENAME;

public class Score extends Actor {
    private final GameScreen gameScreen;
    private final TextureAtlas atlas;
    private final ArrayList<Sprite> digits;
    private final ArrayList<TextureRegion> numbers;
    private int score;

    public Score(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        atlas = new TextureAtlas(Gdx.files.internal(NUMBERS_ATLAS_FILENAME));
        numbers = new ArrayList<>();
        numbers.add(atlas.findRegion("zero"));
        numbers.add(atlas.findRegion("one"));
        numbers.add(atlas.findRegion("two"));
        numbers.add(atlas.findRegion("three"));
        numbers.add(atlas.findRegion("four"));
        numbers.add(atlas.findRegion("five"));
        numbers.add(atlas.findRegion("six"));
        numbers.add(atlas.findRegion("seven"));
        numbers.add(atlas.findRegion("eight"));
        numbers.add(atlas.findRegion("nine"));

        score = 0;
        digits = new ArrayList<>();
        Sprite newSprite = new Sprite(numbers.get(0));
        digits.add(newSprite);
        setWidth(newSprite.getWidth());

        toFront();
    }

    public void update() {
        score++;
        ArrayList<Integer> newDigits = new ArrayList<>();
        int temp = score;
        while (temp > 0) {
            newDigits.add(0, temp % 10);
            temp /= 10;
        }

        for (int i = 0; i < newDigits.size(); i++) {
            if (i >= digits.size()) {
                Sprite newSprite = new Sprite(numbers.get(newDigits.get(i)));
                digits.add(newSprite);
                setWidth(getWidth() + newSprite.getWidth());
            } else {
                digits.get(i).setRegion(numbers.get(newDigits.get(i)));
            }
        }
    }

    public void dispose() {
        atlas.dispose();
    }

    @Override
    public void draw(Batch batch, float ignoredParentAlpha) {
        float y = gameScreen.camera.position.y + (gameScreen.camera.viewportHeight / 2) -
                  numbers.get(0).getRegionHeight() * 1.25f;
        float x = gameScreen.camera.position.x - getWidth() / 2;

        for (int i = 0; i < digits.size(); i++) {
            digits.get(i).setPosition(x + i * digits.get(i).getWidth(), y);
            digits.get(i).draw(batch);
        }
    }
}
