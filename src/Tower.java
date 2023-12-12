import processing.core.PApplet;

import java.net.Proxy;
import java.util.ArrayList;

public class Tower extends Entity{
    private static double TOWER_SHOOT_SPEED = 0.01;
    private static int TOWER_RADIUS = 40;

    public Tower(int x, int y) {
        super("tower", x, y, TOWER_SHOOT_SPEED, TOWER_RADIUS);
    }

    public static void PlaceOnGrid(int mouseX, int mouseY) {
        if(isValidPos(mouseX, mouseY)) {
            int[] gridPos = getGridPosFromRaw(mouseX, mouseY);
            if(isClear(gridPos[0], gridPos[1])) {
                Game.entities.add(new Tower(gridPos[0], gridPos[1]));
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
            if(e.typeName.equals("Tower") && e.x == gridX && e.y == gridY) clear = false;
        }
        return clear;
    }

    public void drawTexture(PApplet pApplet) {
        pApplet.fill(0, 50, 255);
        pApplet.rect(x, y, radius, radius);
    }
}
