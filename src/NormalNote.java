import bagel.*;
import bagel.util.Point;

/**
 * Class for normal notes
 */
public class NormalNote extends Note {

    public NormalNote(String dir, int appearanceFrame, int x) {
        super(new Image("res/note" + dir + ".png"), appearanceFrame, 100,x,  false,false);
    }

    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // evaluate accuracy of the key press
            int score = accuracy.evaluateScore(this.y, targetHeight, input.wasPressed(relevantKey));
            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }

        }

        return 0;
    }

}

