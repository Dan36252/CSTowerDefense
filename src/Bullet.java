import processing.core.PApplet;

import java.util.ArrayList;

public class Bullet extends Entity {
    private static double BULLET_SPEED = 10;
    private static int BULLET_RADIUS = 5;
    private static int shootOffsetX = 20;
    private double speedX;
    private double speedY;
    private Tower origin;
    private Tank target;
    public Bullet(int x, int y, Tower origin, Tank target) {
        super("bullet", x, y, BULLET_SPEED, BULLET_RADIUS);
        this.origin = origin;
        this.target = target;
        double[] speeds = calculateSpeeds();
        this.speedX = speeds[0];
        this.speedY = speeds[1];
    }

    public void drawTexture(PApplet pApplet) {
        if(this.isAlive == false) {return;}
        pApplet.ellipse(x, y, BULLET_RADIUS, BULLET_RADIUS);
    }
    private double[] calculateSpeeds(){
        double dX = target.x-origin.x;
        double dY = origin.y-target.y;
        double dist = Math.sqrt(dX*dX+dY*dY);
        double sX = ;
        double sY = 0;
        double[] result = {sX, sY};
        return result;
    }

    public void act(ArrayList<Entity> list) {
        if(this.isAlive == false) {
            return;
        }

        x += speedX;
        y += speedY;
    }
}
