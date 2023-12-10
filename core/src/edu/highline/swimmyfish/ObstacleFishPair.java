package edu.highline.swimmyfish;

import java.util.Random;

public class ObstacleFishPair {
    private static final int TOTAL_POSSIBLE_BODY_SEGMENTS = 5;
    private final ObstacleFish bottomFish;
    private final ObstacleFish topFish;

    public ObstacleFishPair(SwimmyFish game) {
        bottomFish = new ObstacleFish(game,
                                      TOTAL_POSSIBLE_BODY_SEGMENTS,
                                      ObstacleFish.TopOrBottom.BOTTOM);
        topFish = new ObstacleFish(game,
                                   TOTAL_POSSIBLE_BODY_SEGMENTS,
                                   ObstacleFish.TopOrBottom.TOP);
    }

    public ObstacleFish getBottomFish() {
        return bottomFish;
    }

    public ObstacleFish getTopFish() {
        return topFish;
    }

    public void generateObstacle(float x) {
        Random rand = new Random();
        int bottomBodySegments = rand.nextInt(TOTAL_POSSIBLE_BODY_SEGMENTS);
        int topBodySegments =
                TOTAL_POSSIBLE_BODY_SEGMENTS - bottomBodySegments;

        bottomFish.constructFish(bottomBodySegments, x);
        topFish.constructFish(topBodySegments, x);
    }

    public void dispose() {
        bottomFish.dispose();
        topFish.dispose();
    }
}
