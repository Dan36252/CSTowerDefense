import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.io.*;
import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    public static PImage tankTexture;
    public static int money;
    public static int lives;
    public static int tanksDestroyed;
    public static int towerCount;

    private static int xPos;
    private static int lastTanksDestroyed;

    public static ArrayList<Entity> entities;
    public static ArrayList<Entity> tempEntities;
    private static int timer;
    private static boolean paused;
    public static int index;


    public void settings() {
        size(800, 800);   // set the window size

    }

    public void setup() {
        // TODO: initialize game variables
        tankTexture = loadImage("assets/TankTexture.png");
        money = 0;
        lives = 5;
        tanksDestroyed = 0;
        entities = new ArrayList<>();
        tempEntities = new ArrayList<>();
        xPos = 800;
        lastTanksDestroyed = 0;
        timer = 0;
        index = 0;
        paused = true;
    }

    private void resetBoard(){
        paused = true;
        index = 0;
        towerCount = 0;
        tanksDestroyed = 0;
        lastTanksDestroyed = 0;
        xPos = 800;
        timer = 0;
        Game.entities.clear();
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(255);    // paint screen white
        fill(0,255,0);          // load green paint color
        rect(0, 300, 800, 200);
        PImage img = new PImage();


        textSize(13);
        fill(0);
        text("Tanks Destroyed: " + tanksDestroyed, 20, 20);
        text("Money: " + money, 20, 40);
        text("Lives: " + lives, 20, 60);

        fill(0,255,0);


        for (Entity e : entities) {
            index++;
            if(e.getType() == "tank") {
                e.drawTexture(this, tankTexture);
            } else {
                e.drawTexture(this);
            }

            if (paused == false) {
                e.act(entities);
            }
        }

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

    public void keyReleased(){
        if (key == 's') {

            System.out.println("s pressed");

            // save all point data to a file
            try {
                PrintWriter out = new PrintWriter(new FileWriter("saveGame.txt"));


                for (int i = 0; i < Game.entities.size(); i++) {
                    Entity e = Game.entities.get(i);

                    out.println(e.getEntityType() + ", " + e.getX() + ", " + e.getY()  + ", " + e.getSpeed() + ", " + e.getRadius());
                }

                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            resetBoard();
        }

        if (key == 'r') {
            System.out.println("r pressed");
            resetBoard();
        }

        if (key == 'p') {
            System.out.println("p pressed");
            paused = !paused;

//            xPos = 800;
//            index = 0;
//            lastTanksDestroyed = 0;
        }

        if (key == 'l') {
            try {
                System.out.println("l pressed");

                BufferedReader in = new BufferedReader(new FileReader("saveGame.txt"));
                resetBoard();

                String line;
                while ((line = in.readLine()) != null) {
                    String[] vals = line.split (", ");
                    String type = vals[0];
                    int x = Integer.parseInt(vals[1]);
                    int y = Integer.parseInt(vals[2]);
                    double speed = Double.parseDouble(vals[3]);
                    int radius = Integer.parseInt(vals[4]);

                    Entity n = new Entity (type, x, y, speed, radius);


                    Game.tempEntities.add(n);

                    for (int i = 0; i < entities.size(); i++) {
                        Game.tempEntities.add(Game.entities.get(i));
                    }

                    Game.entities = Game.tempEntities;
                    Game.tempEntities.clear();
                }

                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }



        }

    }


    public static void increaseTanksDestroyed(){
        tanksDestroyed++;
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
}
