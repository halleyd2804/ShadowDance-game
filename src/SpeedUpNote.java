import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class SpeedUpNote extends SpecialNote{

    public SpeedUpNote(String dir, int appearanceFrame, int x) {
        super(new Image("res/note" + dir + ".png"), appearanceFrame, 100, x, false,false, "Speed Up");
    }

    // Set effect for speed up note
    public void setEffect(){
        Note.setNewSpeed(2);
    }
}
