import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Tower extends Entity{
    private static double TOWER_SHOOT_SPEED = 0.01;
    private static int TOWER_RADIUS = 40;

    public Tower(int x, int y) {
        super("tower", x, y, TOWER_SHOOT_SPEED, TOWER_RADIUS);
    }

    public void drawTexture(PApplet pApplet){
        pApplet.fill(0, 50, 255);
        pApplet.rect(x, y, radius, radius);
    }

    @Override
    public void drawTexture(PApplet pApplet, PImage texture) {
        System.err.println("not implemented texture");
    }

    public void act(ArrayList<Entity> list) {
        return;
    }
}
