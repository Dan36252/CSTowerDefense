import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Entity {
    protected String typeName;
    protected int x, y;
    protected double speed;
    protected  int radius;
    protected boolean isAlive;
    public Entity(String typeName, int x, int y, double speed, int radius) {
        this.typeName = typeName;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        this.isAlive = true;
    }



    public void drawTexture(PApplet pApplet){}
    public void drawTexture(PApplet pApplet, PImage texture){}
    public void act(ArrayList<Entity> list) {}

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public double getSpeed(){
        return speed;
    }

    public int getRadius(){
        return radius;
    }

    public String getEntityType(){
        return typeName;
    }

    public void setAlive(boolean alive){this.isAlive = alive;}
    public boolean getIsAlive(){
        return isAlive;
    }
    public String getType() {
        return this.typeName;
    }
}
