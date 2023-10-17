import processing.core.PApplet;

import java.util.ArrayList;

public class Tank extends Entity {
    private static double TANK_SPEED = 2;
    private static int TANK_RADIUS = 30;
    public Tank() {
        super(-TANK_RADIUS, 400, TANK_SPEED, TANK_RADIUS);
    }

    public void drawTexture(PApplet pApplet) {
        if(this.isAlive == false) {return;}
        pApplet.fill(40, 150, 40);
        pApplet.rect(x, y-(radius/2), TANK_RADIUS, TANK_RADIUS);
    }

    public void act() {
        if(this.isAlive == false) {return;}
        x += speed;
        if(x > 400) {
            Game.increaseTanksDestroyed();
            this.setAlive(false);
        }
    }
}
