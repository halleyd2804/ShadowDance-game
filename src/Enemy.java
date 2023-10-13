import bagel.Image;
import bagel.Window;
import bagel.util.Point;

public class Enemy implements Collidable {
    private Image image = new Image("res/enemy.png");
    private Point position;
    private int direction;
    private int speed = 1;
    public boolean rendering = true;
    private final int COLLISION_DISTANCE = 62;
    public Enemy(){
        position = new Point(getRandomNumber(100,900), getRandomNumber(100, 500));
        direction = getRandomNumber(0,1) == 1 ? 1 : -1;
    }

    // generate a random number in a range
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // render enemy
    public void draw(){
        if(rendering){
            if(position.x + direction * speed < 0 || position.x + direction * speed > Window.getWidth()){
                direction = -direction;
            }
            position = new Point(position.x + direction * speed, position.y);
            image.draw(position.x, position.y);
        }
    }
    public Point getPosition(){
        return position;
    }

    // check if the enemy collide with arrows
    @Override
    public boolean collide(Point point2) {
        double distance = position.distanceTo(point2);
        return distance <= COLLISION_DISTANCE;
    }
}
