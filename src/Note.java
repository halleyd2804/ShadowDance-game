import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

public abstract class Note implements Collidable {
    protected Image image;
    protected final int appearanceFrame;
    protected static int speed = 2;
    protected int y;
    protected int x;
    protected boolean active = true;
    protected boolean completed = false;

    public Note(Image image, int appearanceFrame, int y, int x, boolean active, boolean completed) {
        this.image = image;
        this.appearanceFrame = appearanceFrame;
        this.x = x;
        this.y = y;
        this.active = active;
        this.completed = completed;
    }
    public boolean isActive() {
        return active;
    }
    public boolean isCompleted() {return completed;}

    public void deactivate() {
        active = false;
        completed = true;
    }

    public void update() {
        if (active) {
            y += speed;
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }

    }

    public void draw(int x) {
        Guardian guardian = ShadowDance.getGuardian();
        if(guardian != null){
            Enemy enemy = guardian.nearestEnemy();
            if(enemy != null && collide(enemy.getPosition())){
                deactivate();
            }
        }
        if (active) {
            image.draw(x, y);
        }
    }
    public static void setNewSpeed(int speedDelta){
        speed += speedDelta;
    }
    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey);

    public boolean collide(Point a){
        Point position = new Point(x,y);
        double distance = position.distanceTo(a);
        if(distance <= 62) return true;
        return false;
    }
}
