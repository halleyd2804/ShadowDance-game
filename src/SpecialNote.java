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
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int score = accuracy.checkScoreSpecial(this.y, targetHeight, input.wasPressed(relevantKey));
            if (score != Accuracy.NOT_SCORED) {
                accuracy.setMessage(type);
                setEffect();
                deactivate();
                return score;
            }

        }

        return 0;
    }
}
