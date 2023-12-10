package edu.highline.swimmyfish;

import java.util.Random;

public class ObstacleFishPair {
    private static final int TOTAL_BODY_SEGMENTS = 7;
    private final ObstacleFish bottomFish;
    private final ObstacleFish topFish;

    public ObstacleFishPair(SwimmyFish game, int bottomBodySegments,
                            int topBodySegments, float x)
    {
        bottomFish = new ObstacleFish(game,
                                      false,
                                      TOTAL_BODY_SEGMENTS,
                                      bottomBodySegments,
                                      x);
        topFish = new ObstacleFish(game,
                                   true,
                                   TOTAL_BODY_SEGMENTS,
                                   topBodySegments,
                                   x);
        generateObstacleAtPosition(bottomBodySegments, topBodySegments, x);
    }

    public void generateObstacleAtPosition(int bottomBodySegments,
                                           int topBodySegments, float x)
    {
        bottomFish.constructFish(bottomBodySegments, x);
        topFish.constructFish(topBodySegments, x);
    }

    public ObstacleFishPair(SwimmyFish game, float x) {
        int[] segments = generateRandomNumBodySegments();
        int bottomBodySegments = segments[0];
        int topBodySegments = segments[1];
        bottomFish = new ObstacleFish(game,
                                      false,
                                      TOTAL_BODY_SEGMENTS,
                                      bottomBodySegments,
                                      x);
        topFish = new ObstacleFish(game,
                                   true,
                                   TOTAL_BODY_SEGMENTS,
                                   topBodySegments,
                                   x);
    }

    private int[] generateRandomNumBodySegments() {
        Random rand = new Random();
        int bottomBodySegments = rand.nextInt(TOTAL_BODY_SEGMENTS + 1);
        int topBodySegments = TOTAL_BODY_SEGMENTS - bottomBodySegments;
        if (bottomBodySegments > topBodySegments) {
            bottomBodySegments -= 1;
        } else {
            topBodySegments -= 1;
        }

        return new int[]{bottomBodySegments, topBodySegments};
    }

    public ObstacleFish getBottomFish() {
        return bottomFish;
    }

    public ObstacleFish getTopFish() {
        return topFish;
    }

    public float getX() {
        return bottomFish.getX();
    }

    public float getWidth() {
        return bottomFish.getWidth();
    }

    public void update(float x) {
        int[] segments = generateRandomNumBodySegments();
        int bottomBodySegments = segments[0];
        int topBodySegments = segments[1];
        generateObstacleAtPosition(bottomBodySegments, topBodySegments, x);
    }

    public void dispose() {
        bottomFish.dispose();
        topFish.dispose();
    }

}
