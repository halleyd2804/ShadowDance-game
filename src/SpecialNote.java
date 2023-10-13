import bagel.Image;
import bagel.Input;
import bagel.Keys;

public abstract class SpecialNote extends Note{
    private String type;
    private final int EFFECT_FRAME = 480;
    public SpecialNote(Image image, int appearanceFrame, int y, int x, boolean active, boolean completed, String type) {
        super(image, appearanceFrame, y,x, active, completed);
        this.type = type;
    }
    public String getType(){
        return type;
    }
    public abstract void setEffect();
    // Update the score when a special note is pressed and set effect for them
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        int score = accuracy.checkScoreSpecial(this.y, targetHeight, input.wasPressed(relevantKey));
        if (isActive()) {
            if (score != Accuracy.NOT_SCORED && score != Accuracy.MISS_SCORE) {
                accuracy.setMessage(type);
                setEffect();
                deactivate();
                return score;
            } else {
                if (score == Accuracy.MISS_SCORE){
                    deactivate();
                }
            }
        }

        return 0;
    }
}
