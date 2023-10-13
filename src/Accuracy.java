import bagel.*;

import java.util.ArrayList;

/**
 * Class for dealing with accuracy of pressing the notes
 */
public class Accuracy {
    // Constants for scoring
    public static final int SPECIAL_SCORE = 15;
    public static final int PERFECT_SCORE = 10;
    public static final int GOOD_SCORE = 5;
    public static final int BAD_SCORE = -1;
    public static final int MISS_SCORE = -5;
    public static final int NOT_SCORED = 0;

    // Constants for accuracy strings
    public static final String PERFECT = "PERFECT";
    public static final String GOOD = "GOOD";
    public static final String BAD = "BAD";
    public static final String MISS = "MISS";

    // Constants for accuracy radius
    private static final int PERFECT_RADIUS = 15;
    private static final int GOOD_RADIUS = 50;
    private static final int BAD_RADIUS = 100;
    private static final int MISS_RADIUS = 200;

    // Font for displaying accuracy
    private static final Font ACCURACY_FONT = new Font(ShadowDance.FONT_FILE, 40);

    // Constants for rendering
    private static final int RENDER_FRAMES = 30;

    // Current accuracy string
    private String currAccuracy = null;

    // Frame count for rendering accuracy
    private int frameCount = 0;

    // Multiplier for scoring
    private int multiplier = 1;
    private final int DOUBLE_NOTE_FRAME = 480;

    // List to keep track of double score effects
    private final ArrayList<Integer> doubleScoreEffects = new ArrayList<Integer>();

    // Set the current accuracy
    public void setAccuracy(String accuracy) {
        currAccuracy = accuracy;
        frameCount = 0;
    }

    // Evaluate and score the player's input
    public int evaluateScore(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);

        if (triggered) {
            if (distance <= PERFECT_RADIUS) {
                setAccuracy(PERFECT);
                return PERFECT_SCORE * multiplier;
            } else if (distance <= GOOD_RADIUS) {
                setAccuracy(GOOD);
                return GOOD_SCORE * multiplier;
            } else if (distance <= BAD_RADIUS) {
                setAccuracy(BAD);
                return BAD_SCORE * multiplier;
            } else if (distance <= MISS_RADIUS) {
                setAccuracy(MISS);
                return MISS_SCORE * multiplier;
            }
        } else if (height >= (Window.getHeight())) {
            setAccuracy(MISS);
            return MISS_SCORE * multiplier;
        }

        return NOT_SCORED;
    }

    // Check and score special effects
    public int checkScoreSpecial(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);
        if (triggered) {
            if (distance <= GOOD_RADIUS) {
                return SPECIAL_SCORE;
            }
        } else if (height >= Window.getHeight())
            return MISS_SCORE;
        return NOT_SCORED;
    }

    // Update the rendering of accuracy
    public void update() {
        frameCount++;
        if (currAccuracy != null && frameCount < RENDER_FRAMES) {
            ACCURACY_FONT.drawString(currAccuracy,
                    Window.getWidth() / 2 - ACCURACY_FONT.getWidth(currAccuracy) / 2,
                    Window.getHeight() / 2);
        }
        for (int i = 0; i < doubleScoreEffects.size(); i++) {
            if (doubleScoreEffects.get(i) == 0) {
                multiplier /= 2;
            }
            doubleScoreEffects.set(i, doubleScoreEffects.get(i) - 1);
        }
    }

    // Set a message as the current accuracy
    public void setMessage(String message) {
        currAccuracy = message;
        frameCount = 0;
    }

    // Add a double score effect
    public void addDoubleScoreEffect() {
        doubleScoreEffects.add(DOUBLE_NOTE_FRAME);
        multiplier *= 2;
    }
}
