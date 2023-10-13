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
    public boolean active = true;
    private final int COLLISION_DISTANCE_NOTE = 104;
    protected boolean completed = false;

    // Constructor for the Note class
    public Note(Image image, int appearanceFrame, int y, int x, boolean active, boolean completed) {
        this.image = image;
        this.appearanceFrame = appearanceFrame;
        this.x = x;
        this.y = y;
        this.active = active;
        this.completed = completed;
    }

    // Check if the note is active
    public boolean isActive() {
        return active;
    }

    // Check if the note is completed
    public boolean isCompleted() {
        return completed;
    }

    // Deactivate the note
    public void deactivate() {
        active = false;
        completed = true;
    }

    // Update the note's position
    public void update() {
        if (active) {
            y += speed;
        }

        // Activate the note when the current frame matches the appearance frame
        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    // Draw the note on the screen
    public void draw(int x) {
        Guardian guardian = ShadowDance.getGuardian();
        if (guardian != null) {
            Enemy enemy = guardian.nearestEnemy();
            if (enemy != null && collide(enemy.getPosition())) {
                deactivate();
            }
        }
        if (active) {
            image.draw(x, y);
        }
    }

    // Increase or decrease the speed of all notes
    public static void setNewSpeed(int speedDelta) {
        speed += speedDelta;
    }

    // Abstract method to check the score based on player input and accuracy
    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey);

    // Check if the note collides with an enemy (used for collision detection)
    public boolean collide(Point a) {
        Point position = new Point(x, y);
        double distance = position.distanceTo(a);
        return distance <= COLLISION_DISTANCE_NOTE;
    }
}
