import processing.core.PApplet;

import java.util.ArrayList;

public class Bullet extends Entity {
    private static double BULLET_SPEED = 10;
    private static int BULLET_RADIUS = 5;
    private int direction;
    public Bullet(int x, int y, int dir) {
        super("bullet", x, y, BULLET_SPEED, BULLET_RADIUS);
        this.direction = dir;
    }

    public void drawTexture(PApplet pApplet) {
        if(this.isAlive == false) {return;}
        pApplet.ellipse(x, y, BULLET_RADIUS, BULLET_RADIUS);
    }

    public void act() {
        if(this.isAlive == false) {
            return;
        }
        y += speed*direction;
    }
}
