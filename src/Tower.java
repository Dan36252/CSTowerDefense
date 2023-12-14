import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

import java.net.Proxy;
import java.util.ArrayList;

public class Tower extends Entity{
    private static double TOWER_SHOOT_SPEED = 0.01;
    public static int TOWER_RADIUS = 40;
    private static int TOWER_RANGE = 300;
    private int cooldown = 100;
    private int timer = cooldown;
    private Tank target = null;

    public Tower(int x, int y) {
        super("tower", x, y, TOWER_SHOOT_SPEED, TOWER_RADIUS, 0);
    }

    public static void PlaceOnGrid(int mouseX, int mouseY) {
        if(isValidPos(mouseX, mouseY)) {
            int[] gridPos = getGridPosFromRaw(mouseX, mouseY);
            if(isClear(gridPos[0], gridPos[1]) && Game.canPlaceTower()) {
                Game.entities.add(new Tower(gridPos[0], gridPos[1]));
                Game.towerPlaced();
                System.out.println("Placed Grid Pos: " + gridPos[0] + ", " + gridPos[1]);
            }
        }
        //System.out.println(isValidPos(mouseX, mouseY));
    }

    private static boolean isValidPos(int mouseX, int mouseY) {
        boolean valid = true;
        if(mouseY > Game.pathPosY && mouseY < (Game.pathPosY + Game.pathWidth)) valid = false;
        return valid;
    }

    public static int[] getGridPosFromRaw(int mouseX, int mouseY) {
        int newX, newY;
        newX = TOWER_RADIUS*(mouseX/TOWER_RADIUS);
        newY = TOWER_RADIUS*((mouseY-(TOWER_RADIUS/2))/TOWER_RADIUS)+(TOWER_RADIUS/2);
        int[] result = {newX, newY};
        return result;
    }

    private static boolean isClear(int gridX, int gridY) {
        boolean clear = true;
        ArrayList<Entity> list = Game.entities;
        for (int i = 0; i < list.size(); i++) {
            Entity e = list.get(i);
            //System.out.println(e.typeName);
            if(e.typeName.equals("tower") && e.x == gridX && e.y == gridY) clear = false;
        }
        return clear;
    }

    private Tank findTarget(){
        int closestDisp = 800;
        int closestX = 0;
        Tank closest = null;
        int closestI = 0;
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if(e.typeName.equals("tank")) {
                int disp = Math.abs(e.x-this.x);
                if(closestDisp > disp){
                    closestDisp = disp;
                    closestX = e.x;
                    closest = (Tank) e;
                    closestI = i;
                }
            }
        }
//        if(closestI < Game.entities.size()-1) {
//            for (int i = closestI+1; i < Game.entities.size(); i++) {
//                Entity e = Game.entities.get(i);
//                if(e.typeName.equals("tank")) {
//                    closest = (Tank) e;
//                    break;
//                }
//            }
//        }
        if(closestDisp > TOWER_RANGE) closest = null;
        return closest;
    }

    public void drawTexture(PApplet pApplet) {
        pApplet.fill(0, 50, 255);
        pApplet.rect(x, y, radius, radius);
        //if(target != null) pApplet.rect(target.x, target.y, 20, 20);
    }

    public void act() {
        if(timer <= 0) {
            timer = cooldown;

            // Every (cooldown) ticks, this will run
            target = findTarget();
            if (target != null) {
                Game.entities.add(new Bullet(
                        x+(TOWER_RADIUS/2),
                        y+(TOWER_RADIUS/2),
                        this,
                        target
                ));
            }

        }
        timer--;
    }

    @Override
    public void drawTexture(PApplet pApplet, PImage texture) {
        System.err.println("not implemented texture");
    }

    public void act(ArrayList<Entity> list) {
        return;
    }
}
