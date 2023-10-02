import bagel.Image;

public class Note {
    protected Image image;
    protected final int appearanceFrame;
    protected final int speed = 2;
    private int y;
    protected boolean active = false;
    protected boolean completed = false;

    public Note(Image image, int appearanceFrame, int y, boolean active, boolean completed) {
        this.image = image;
        this.appearanceFrame = appearanceFrame;
        this.y = y;
        this.active = active;
        this.completed = completed;
    }
}
