import processing.core.PApplet;
import processing.core.PImage;

import java.io.*;
import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    public static PImage tankTexture;
    private static int money;
    private static int towerCost;
    private static int tankReward;
    public static int shields;
    private static int shieldCost;
    private static int shieldTax;
    private static int shieldsBoughtCounter;
    private static boolean canPurchaseShields;
    private static int tanksDestroyed;
    private static int towerCount;
    private static int tanksSpawned;

    private static int xPos;
    public static int pathPosY;
    public static int pathWidth;
    private static int lastTanksDestroyed;
    private static int lastTimerDecreaseTankNum;

    public static ArrayList<Entity> entities;
    public static ArrayList<Entity> tempEntities;
    private static int timer;
    private static int maxTimer;
    private static boolean paused;
    private static boolean lost;
    public static int index;



    public void settings() {
        size(800, 800);   // set the window size

    }

    public void setup() {
        // TODO: initialize game variables
        tankTexture = loadImage("assets/TankTexture.png");
        money = 0;
        towerCost = 10;
        tankReward = 5;
        shields = 5;
        shieldCost = 50;
        shieldTax = 1;
        shieldsBoughtCounter = 0;
        canPurchaseShields = false;
        tanksSpawned = 0;
        tanksDestroyed = 0;
        entities = new ArrayList<>();
        tempEntities = new ArrayList<>();
        xPos = 800;
        pathPosY = 300;
        pathWidth = 200;
        lastTanksDestroyed = 0;
        lastTimerDecreaseTankNum = 0;
        timer = 0;
        maxTimer = 100;
        index = 0;
        paused = true;
        lost = false;
    }

    private void resetBoard(){
        setup();
        Game.entities.clear();
    }

    public static boolean canPlaceTower(){
        boolean canPlace = true;
        if(paused) canPlace = false;
        if(lost) canPlace = false;
        if(money-towerCost < 0) canPlace = false;
        return canPlace;
    }

    public static void towerPlaced() {
        money -= towerCost;
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

                PrintWriter out2 = new PrintWriter(new FileWriter("gameStats.txt"));
                out2.println("Tanks Destroyed: " + tanksDestroyed);
                out2.println("Money: " + money);
                out2.println("Tank Reward: " + tankReward);
                out2.println("Tower Cost: " + towerCost);
                out2.println("Shields: " + shields);
                out2.println("Paused: " + paused);

                out2.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            paused = true;
        }

        if (key == 'r') {
            System.out.println("r pressed");
            resetBoard();
        }

        if (key == 'p') {
            //System.out.println("p pressed");
            if (!lost) {
                paused = !paused;
            }
        }

        if (key == 'i') {
            if(!lost) {
                if (money - shieldCost >= 0) {
                    money -= shieldCost;
                    shields++;
                    shieldsBoughtCounter++;
                    shieldCost += shieldTax;
                    shieldTax += shieldsBoughtCounter;
                }
            }
        }

        // developer key
        if (key == 'd') {
            if (!lost) {
                money += 10;
            }
        }

        if (key == 'c') {
            if (!lost) {
                money = 0;
            }
        }

        if (key == 'k') {
            if (!lost) {
                shields--;
            }
        }

        if (key == 'j') {
            if (!lost) {
                shields = 5;
            }
        }

        if (key == 'b') {
            if (!lost) {
                maxTimer = 20;
            }
        }

        if (key == 'a') {
            if (!lost) {
                maxTimer++;
            }
        }


        if (key == 'l') {
            try {
                System.out.println("l pressed");

                paused = true;

                BufferedReader in = new BufferedReader(new FileReader("saveGame.txt"));
                resetBoard();

                int i = 0;
                String line;
                while ((line = in.readLine()) != null) {
                    String[] vals = line.split (", ");
                    String type = vals[0];
                    int x = Integer.parseInt(vals[1]);
                    int y = Integer.parseInt(vals[2]);
                    double speed = Double.parseDouble(vals[3]);
                    int radius = Integer.parseInt(vals[4]);

                    if (type.equals("tank")){
                        Entity t = new Tank (x, y, speed, radius);
                        Game.entities.add(t);
                    }

                    if (type.equals("tower")){
                        Entity n = new Tower (x, y);
                        Game.entities.add(n);
                    }


                    System.out.println(i + " " + Game.entities);
                    i++;

                }

                in.close();

//                for (Entity e : entities) {
//                    index++;
//                    if (e.getType().equals("tank")) {
//                        e.drawTexture(this, tankTexture);
//                    } else {
//                        e.drawTexture(this);
//                    }
//
//                    e.act(entities);
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void mouseClicked() {
        System.out.println("Mouse Clicked");
        Tower.PlaceOnGrid(mouseX, mouseY);
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(255);

        // Tower placing indicator
        fill(220, 220, 220);
        int[] gridPos = Tower.getGridPosFromRaw(mouseX, mouseY);
        rect(gridPos[0], gridPos[1], Tower.TOWER_RADIUS, Tower.TOWER_RADIUS);

        // Path drawing
        fill(0, 255, 0);
        rect(0, pathPosY, 800, pathWidth);

        PImage img = new PImage();

        textSize(13);
        fill(0);
        int x = 20;
        text("Tanks Destroyed: " + tanksDestroyed, x, 20);
        text("Money: $" + money, x, 40);
        text("Tank Reward: $" + tankReward, x, 60);
        text("Tower Cost: $" + towerCost, x, 80);
        text("Shields: " + shields, x, 100);
        text("Can Purchase Shield?: " + canPurchaseShields, x, 120);
        text("Paused: " + paused, x, 160);
        text("Entity List: " + entities.size(), x, 180);
//        text("Timer: " + timer, x, 200);
        text("Tank Frequency: " + maxTimer, x, 200);
        text("Tank Spawned: " + tanksSpawned, x, 220);
//        text("Last Timer Decrease: " + lastTimerDecreaseTankNum, x, 260);


        String pauseState;
        if (paused){
            pauseState = "play";
        } else{
            pauseState = "pause";
        }

        if (!lost) {
            int x2 = 600;
            text("'r' to reset", x2, 20);
            text("'p' to " + pauseState, x2, 40);
            text("'i' to by a shield for $" + shieldCost, x2, 60);
            text("'s' to save game", x2, 80);
            //text("'l' to load", x2, 100);

            text("Developer Keys", x2 + 40, 120);
            text("'d' to add $10 to money", x2, 140);
            text("'c' to clear money", x2, 160);
            text("'k' to loose a shield", x2, 180);
            text("'j' to return to 5 shields", x2, 200);
            text("'b' to make maxTimer = 20", x2, 220);
            text("'a' to slow down tank frequency", x2, 240);

        }

        if (lost) {
            int x3 = 330;
            textSize(24);
            text("GAME OVER!", x3, 50);

            textSize(18);
            text("'r' to reset", x3 + 25, 80);

        }

        fill(0, 255, 0);

        if (money < 0){
            paused = true;
        }

        if (shields <= 0) {
            lost = true;
            paused = true;
        }

        for (int i = 0; i < entities.size(); i++) {
            if (!entities.get(i).getIsAlive()) {
                entities.remove(i);
                i--;
            }
        }
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.getType().equals("tank")) {
                e.drawTexture(this, tankTexture);
            } else {
                e.drawTexture(this);
            }

            if (!paused) {
                e.act();
            }
        }

        if (!paused) {
            timer--;

        }

        if (timer <= 0) {
            timer = maxTimer;
            Tank t = new Tank((int)((double)tanksDestroyed/10.0));
            entities.add(t);
            tanksSpawned++;
        }

        if (tanksSpawned % 10 == 0 && tanksSpawned > 0 && maxTimer > 16) {
            if (tanksSpawned == lastTimerDecreaseTankNum) {
                return;
            }

            lastTimerDecreaseTankNum = tanksSpawned;

            maxTimer --;
        }

        if (!lost) {
            canPurchaseShields = money - shieldCost >= 0;
        } else{
            canPurchaseShields = false;
        }



//        ellipse(mouseX, mouseY, 60, 60);  // draw circle at mouse loc
//        ellipse(mouseX - 80, mouseY, 60, 60);  // draw circle at mouse loc
//        ellipse(mouseX + 80, mouseY, 60, 60);  // draw circle at mouse loc
    }


    public static void increaseTanksDestroyed(){
        tanksDestroyed++;

        if (tanksDestroyed % 10 == 0){
            tankReward += 2;
        }

        if (tanksDestroyed % 50 == 0){
            tankReward += 10 * (tanksDestroyed/50.0);
        }

        money += tankReward;
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
}