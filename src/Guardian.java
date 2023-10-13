import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import java.util.ArrayList;

public class Guardian {
    // Load the guardian image from a file
    private Image image = new Image("res/guardian.png");

    // Initialize the starting location of the guardian
    private Point location = new Point(800, 600);

    // Store a list of arrows fired by the guardian
    private final ArrayList<Arrow> arrows = new ArrayList<>();

    // Get the current location of the guardian
    public Point getLocation() {
        return location;
    }

    // Method to make the guardian shoot arrows at a specified enemy
    public void shootArrows(Enemy enemy) {
        arrows.add(new Arrow(enemy));
    }

    // Draw the guardian on the screen
    public void draw() {
        image.draw(location.x, location.y);
    }

    // Update the guardian's actions based on user input
    public void update(Input input) {
        for(int i = 0; i < arrows.size(); i++){
            arrows.get(i).draw();
            if (nearestEnemy() != null && nearestEnemy().collide(arrows.get(i).getPosition())) {
                nearestEnemy().rendering = false;
                arrows.get(i).rendering = false;
                arrows.remove(arrows.get(i));
            }
        }
        // Check if the left shift key was pressed to shoot arrows at the nearest enemy
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            shootArrows(nearestEnemy());
        }
    }

    // Find the nearest enemy to the guardian's location
    public Enemy nearestEnemy() {
        ArrayList<Enemy> enemies = ShadowDance.getEnemies();
        double minDistance = Integer.MAX_VALUE;
        Enemy nearestEnemy = null;
        for (Enemy enemy : enemies) {
            if (enemy.rendering && enemy.getPosition().distanceTo(location) < minDistance) {
                minDistance = enemy.getPosition().distanceTo(location);
                nearestEnemy = enemy;
            }
        }
        return nearestEnemy;
    }
}
