import bagel.DrawOptions;
import bagel.util.Point;
import bagel.Image;
import bagel.util.Vector2;

public class Arrow {
    private Image image = new Image("res/arrow.png");
    public Point position = new Point(800, 600);
    private int speed = 12;
    private double direction;
    public Arrow(Enemy enemy){
        direction = findDirection(enemy);
        System.out.println(direction);
    }
    public double findDirection(Enemy enemy){
        if(enemy != null){
            double opp = enemy.getPosition().y - 600;
            double adj = enemy.getPosition().x - 800;
            Vector2 vector = new Vector2(opp, adj);
            return Math.atan2(vector.normalised().x, vector.normalised().y);
        }
        return 0;
    }
    public int getSpeed(){
        return speed;
    }
    public Point getPosition(){
        return position;
    }

    public void draw(){
        position = new Point(position.x + (int)(Math.cos(direction)*speed), position.y + (int)(Math.sin(direction)*speed));

        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setRotation(direction);
        image.draw(position.x, position.y, drawOptions);
    }


}
