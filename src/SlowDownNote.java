import bagel.Image;

public class SlowDownNote extends SpecialNote{
    public SlowDownNote(String dir, int appearanceFrame, int location) {
        super(new Image("res/note" + dir + ".png"), appearanceFrame, 100, location, false,false, "Slow Down");
    }
    public void setEffect(){
        Note.setNewSpeed(-1);
    }
}
