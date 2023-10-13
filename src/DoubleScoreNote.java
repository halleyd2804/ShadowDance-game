import bagel.Image;

public class DoubleScoreNote extends SpecialNote{
    public DoubleScoreNote(String dir, int appearanceFrame, int location) {
        super(new Image("res/note2x.png"), appearanceFrame, 100, location, false,false, "Double Score");
    }

    // Set effect Double Score
    @Override
    public void setEffect() {
        Accuracy accuracy = ShadowDance.getAccuracy();
        accuracy.addDoubleScoreEffect();
    }
}
