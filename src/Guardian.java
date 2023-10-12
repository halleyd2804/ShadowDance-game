import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

import java.util.ArrayList;

public class Guardian {
    private Image image = new Image("res/guardian.png");
    private Point location = new Point(800, 600);
    private final ArrayList<Arrow> arrows = new ArrayList<>();
    public Point getLocation(){
        return location;
    }
    public void shootArrows(Enemy enemy){
        arrows.add(new Arrow(enemy));
    }
    public void draw() {
        image.draw(location.x, location.y);
    }

    public void update(Input input) {
        arrows.forEach(arrow -> {
            arrow.draw();
            if(nearestEnemy() != null && nearestEnemy().collide(arrow.getPosition())){
                nearestEnemy().rendering = false;
            }
        });
        if(input.wasPressed(Keys.LEFT_SHIFT))
            shootArrows(nearestEnemy());
    }

    public Enemy nearestEnemy(){
        ArrayList<Enemy> enemies = ShadowDance.getEnemies();
        double minDistance = 9999;
        Enemy nearestEnemy = null;
        for(Enemy enemy : enemies){
            if(enemy.rendering && enemy.getPosition().distanceTo(location) < minDistance){
                minDistance = enemy.getPosition().distanceTo(location);
                nearestEnemy = enemy;
            }
        }
        return nearestEnemy;
    }
}
