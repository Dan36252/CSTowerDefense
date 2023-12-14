import com.sun.corba.se.impl.encoding.EncapsInputStream;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Tank extends Entity {
    private static double TANK_SPEED = 2;
    public static int TANK_RADIUS = 30;
    public Tank(int lives) {
        super("tank", -TANK_RADIUS, 400, TANK_SPEED, TANK_RADIUS, lives);
        this.lives = lives;
    }

    public Tank(int x, int y, int lives) {
        super("tank", x, y, TANK_SPEED, TANK_RADIUS, lives);
    }

    public void drawTexture(PApplet pApplet, PImage texture) {
        if(this.isAlive == false) {
            return;
        }
        pApplet.fill(40, 150, 40);
        pApplet.image(texture, x, y-(radius/2), radius, radius);
        pApplet.fill(230, 20, 20);
        pApplet.text(lives+" lives", x, y-(radius/2)-10);
        //pApplet.rect(x, y-(radius/2), TANK_RADIUS, TANK_RADIUS);
    }

    public void tankHit(){
        //System.out.println("hit!");
        lives--;
        if(lives <= 0) {
            Game.increaseTanksDestroyed();
            setAlive(false);
        }
    }

    public void act() {
        if (this.isAlive == false) {
                return;
        }

        x += speed;
        if (x > 800) {
            Game.tankReachedEnd();
            this.setAlive(false);
            //Game.shields--;
        }
    }

    public void drawTexture(PApplet pApplet) {
        pApplet.fill(0, 50, 255);
        pApplet.rect(x, y, radius, radius);
    }

}
