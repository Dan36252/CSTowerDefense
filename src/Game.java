import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    public static PImage tankTexture;
    public static int money;
    public static int lives;
    public static int tanksDestroyed;
    public static int towerCount;
    public static int pathPosY;
    public static int pathWidth;

    private static int xPos;
    private static int lastTanksDestroyed;

    private static ArrayList<Entity> entities;

    private static int timer;

    public void settings() {
        size(800, 800);   // set the window size

    }

    public void setup() {
        // TODO: initialize game variables
        tankTexture = loadImage("assets/TankTexture.png");
        money = 0;
        lives = 5;
        tanksDestroyed = 0;
        pathPosY = 300;
        pathWidth = 200;
        entities = new ArrayList<>();

        xPos = 800;
        lastTanksDestroyed = 0;
        timer = 0;

    @Override
    public void mouseClicked() {
        System.out.println("Mouse Pressed: "+mouseX+", "+mouseY);
        Tower.PlaceOnGrid(mouseX, mouseY);
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(255);    // paint screen white
        fill(0,255,0);          // load green paint color
        rect(0, pathPosY, 800, pathWidth);
        fill(220, 220, 220);
        int[] gridPos = Tower.getGridPosFromRaw(mouseX, mouseY);
        rect(gridPos[0], gridPos[1], 40, 40);
        PImage img = new PImage();

        fill(0,255,0);

        int index = 0;
        for (Entity e : entities) {
            index++;
            if(e.getType() == "tank") {
                e.drawTexture(this, tankTexture);
            } else {
                e.drawTexture(this);
            }
            e.act();
        }


            textSize(13);
            fill(0);
            text("Tanks Destroyed: " + tanksDestroyed, 20, 20);
            text("Money: " + money, 20, 40);
            text("Lives: " + lives, 20, 60);


            timer--;
            if (timer <= 0) {
                timer = 100;
                Tank t = new Tank();
                entities.add(t);
            }

            if (tanksDestroyed % 5 == 0 && tanksDestroyed > 0) {
                if (tanksDestroyed == lastTanksDestroyed) {
                    return;
                }
                lastTanksDestroyed = tanksDestroyed;
                if (towerCount % 2 == 0) {
                    xPos -= 40;
                }
                int yPos = 260 + 240 * (towerCount % 2);
                Tower r = new Tower(xPos, yPos);
                entities.add(r);
                towerCount++;
            }
//        ellipse(mouseX, mouseY, 60, 60);  // draw circle at mouse loc
//        ellipse(mouseX - 80, mouseY, 60, 60);  // draw circle at mouse loc
//        ellipse(mouseX + 80, mouseY, 60, 60);  // draw circle at mouse loc
    }

    public static void increaseTanksDestroyed(){
        tanksDestroyed++;
        money += 10;
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
}
