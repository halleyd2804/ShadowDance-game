import bagel.*;

import java.util.ArrayList;

/**
 * Class for dealing with accuracy of pressing the notes
 */
public class Accuracy {
    public static final int SPECIAL_SCORE = 15;
    public static final int PERFECT_SCORE = 10;
    public static final int GOOD_SCORE = 5;
    public static final int BAD_SCORE = -1;
    public static final int MISS_SCORE = -5;
    public static final int NOT_SCORED = 0;
    public static final String PERFECT = "PERFECT";
    public static final String GOOD = "GOOD";
    public static final String BAD = "BAD";
    public static final String MISS = "MISS";
    private static final int PERFECT_RADIUS = 15;
    private static final int GOOD_RADIUS = 50;
    private static final int BAD_RADIUS = 100;
    private static final int MISS_RADIUS = 200;
    private static final Font ACCURACY_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static final int RENDER_FRAMES = 30;
    private String currAccuracy = null;
    private int frameCount = 0;
    private int multiplier = 1;
    private final ArrayList<Integer> doubleScoreEffects = new ArrayList<Integer>();

    public void setAccuracy(String accuracy) {
        currAccuracy = accuracy;
        frameCount = 0;
    }

    public int evaluateScore(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);

        if (triggered) {
            if (distance <= PERFECT_RADIUS) {
                setAccuracy(PERFECT);
                return PERFECT_SCORE*multiplier;
            } else if (distance <= GOOD_RADIUS) {
                setAccuracy(GOOD);
                return GOOD_SCORE*multiplier;
            } else if (distance <= BAD_RADIUS) {
                setAccuracy(BAD);
                return BAD_SCORE*multiplier;
            } else if (distance <= MISS_RADIUS) {
                setAccuracy(MISS);
                return MISS_SCORE*multiplier;
            }

        } else if (height >= (Window.getHeight())) {
            setAccuracy(MISS);
            return MISS_SCORE*multiplier;
        }

        return NOT_SCORED;

    }

    public int checkScoreSpecial(int height, int targetHeight, boolean triggered){
        int distance = Math.abs(height - targetHeight);
        if(triggered){
            if(distance <= GOOD_RADIUS){
                return SPECIAL_SCORE;
            }
        }
        return NOT_SCORED;
    }

    public void update() {
        frameCount++;
        if (currAccuracy != null && frameCount < RENDER_FRAMES) {
            ACCURACY_FONT.drawString(currAccuracy,
                    Window.getWidth()/2 - ACCURACY_FONT.getWidth(currAccuracy)/2,
                    Window.getHeight()/2);
        }
        for(int i = 0; i < doubleScoreEffects.size(); i++){
           if(doubleScoreEffects.get(i) == 0) {
                multiplier /= 2;
            }
            doubleScoreEffects.set(i, doubleScoreEffects.get(i)-1);

        }
    }
    public void setMessage(String message){
        currAccuracy = message;
        frameCount = 0;
    }

    public void addDoubleScoreEffect() {
        doubleScoreEffects.add(480);
        multiplier *= 2;
    }
}
