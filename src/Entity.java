import processing.core.PApplet;

import java.util.ArrayList;

public class Entity {
    protected int x, y;
    protected double speed;
    protected  int radius;
    protected boolean isAlive;
    public Entity(int x, int y, double speed, int radius) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        this.isAlive = true;
    }
    public void drawTexture(PApplet pApplet){

    }
    public void act() {}

    public void setAlive(boolean alive){this.isAlive = alive;}
}
