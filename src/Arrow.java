import bagel.DrawOptions;
import bagel.util.Point;
import bagel.Image;
import bagel.util.Vector2;

public class Arrow {
    // Load the arrow image from a file
    private Image image = new Image("res/arrow.png");

    // Initialize the starting position of the arrow
    public Point position = new Point(800, 600);

    // Set the speed of the arrow
    private int speed = 12;

    // Store the direction of the arrow's movement
    private double direction;
    public boolean rendering = true;

    // Constructor for the Arrow class
    public Arrow(Enemy enemy) {
        // Calculate the direction based on the enemy's position
        direction = findDirection(enemy);
    }

    // Calculate the direction angle to the target enemy
    public double findDirection(Enemy enemy) {
        if (enemy != null) {
            double opp = enemy.getPosition().y - 600;
            double adj = enemy.getPosition().x - 800;

            // Create a vector from the arrow to the enemy
            Vector2 vector = new Vector2(opp, adj);

            // Calculate the angle between the arrow and the enemy
            return Math.atan2(vector.normalised().x, vector.normalised().y);
        }
        // Return 0 if no enemy is provided (no change in direction)
        return 0;
    }

    // Get the speed of the arrow
    public int getSpeed() {
        return speed;
    }

    // Get the current position of the arrow
    public Point getPosition() {
        return position;
    }

    // Update the arrow's position and draw it on the screen
    public void draw() {
        // Calculate the new position based on the direction and speed
        if(rendering){
            position = new Point(position.x + (int)(Math.cos(direction) * speed),
                    position.y + (int)(Math.sin(direction) * speed));

            // Create DrawOptions to control the rotation of the arrow
            DrawOptions drawOptions = new DrawOptions();
            drawOptions.setRotation(direction);

            // Draw the arrow image at the updated position with the specified rotation
            image.draw(position.x, position.y, drawOptions);
        }
    }
}
