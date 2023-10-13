import bagel.Image;

import java.util.ArrayList;

public class BombNote extends SpecialNote {
    // Constructor for BombNote
    private String dir;
    public BombNote(String dir, int appearanceFrame, int location) {
        super(new Image("res/noteBomb.png"), appearanceFrame, 100, location, false, false, "Clear Lane");
        this.dir = dir;
    }

    @Override
    public void setEffect() {
        ArrayList<Lane> lanes = ShadowDance.getLanes();
        for(Lane lane : lanes){
            if(lane.getType().equals(dir)) lane.clearLane();
        }
    }
}
