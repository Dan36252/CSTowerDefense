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

    public void keyReleased(){
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

                out2.println("Money: " + money);
                out2.println("Tower Cost: " + towerCost);
                out2.println("Tank Reward: " + tankReward);
                out2.println("Shields: " + shields);
                out2.println("ShieldCost: " + shieldCost);
                out2.println("ShieldTax: " + shieldTax);
                out2.println("ShieldsBoughtCounter: " + shieldsBoughtCounter);
                out2.println("Tanks Spawned: " + tanksSpawned);
                out2.println("Tanks Destroyed: " + tanksDestroyed);
                out2.println("Tower Count: " + towerCount);
                out2.println("xPos: " + xPos);
                out2.println("LastTankDestroyed: " + lastTanksDestroyed);
                out2.println("LastTimerDecreaseTankNum: " + lastTimerDecreaseTankNum);
                out2.println("Timer: " + timer);
                out2.println("Tank Frequency: " + maxTimer);
                out2.println("Index: " + index);
                out2.println("Lost?: " + lost);



                out2.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            paused = true;
        }

        if (key == 'l') {
            System.out.println("l pressed");

            try {
                paused = true;

                BufferedReader in = new BufferedReader(new FileReader("saveGame.txt"));
                BufferedReader in2 = new BufferedReader(new FileReader("gameStats.txt"));
                Game.entities.clear();

                int i = 0;
                String line;
                while ((line = in.readLine()) != null) {
                    String[] vals = line.split (", ");
//                    for (int j = 0; j < vals.length; j++) {
//                        System.out.println(j + ": " + vals[j]);
//                    }

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


                    //System.out.println(i + " " + Game.entities);
                    //i++;

                }

                in.close();

                System.out.println();

                String line2;
                ArrayList<String> statList = new ArrayList<>();

                while ((line2 = in2.readLine()) != null) {
                    String[] stats = line2.split (": ");
                    statList.add(stats[1]);
                }

                in2.close();

//                for (int j = 0; j < statList.size(); j++) {
//                    System.out.println(statList.get(j));
//
//                }
                int loc = 0;
                money = Integer.parseInt(statList.get(loc));
                loc++;
                towerCost = Integer.parseInt(statList.get(loc));
                loc++;
                tankReward = Integer.parseInt(statList.get(loc));
                loc++;
                shields = Integer.parseInt(statList.get(loc));
                loc++;
                shieldCost = Integer.parseInt(statList.get(loc));
                loc++;
                shieldTax = Integer.parseInt(statList.get(loc));
                loc++;
                shieldsBoughtCounter = Integer.parseInt(statList.get(loc));
                loc++;
                tanksSpawned = Integer.parseInt(statList.get(loc));
                loc++;
                tanksDestroyed = Integer.parseInt(statList.get(loc));
                loc++;
                towerCount = Integer.parseInt(statList.get(loc));
                loc++;
                xPos = Integer.parseInt(statList.get(loc));
                loc++;
                lastTanksDestroyed = Integer.parseInt(statList.get(loc));
                loc++;
                lastTimerDecreaseTankNum = Integer.parseInt(statList.get(loc));
                loc++;
                timer = Integer.parseInt(statList.get(loc));
                loc++;
                maxTimer = Integer.parseInt(statList.get(loc));
                loc++;
                index = Integer.parseInt(statList.get(loc));
                loc++;
                lost = Boolean.parseBoolean(statList.get(loc));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(255);    // paint screen white
        fill(0, 255, 0);          // load green paint color
        rect(0, 300, 800, 200);
        PImage img = new PImage();

        textSize(13);
        fill(0);
        int x = 20;
        int y = 20;
        text("Tanks Destroyed: " + tanksDestroyed, x, y);
        y += 20;
        text("Money: $" + money, x, y);
        y += 20;
        text("Tank Reward: $" + tankReward, x, y);
        y += 20;
        text("Tower Cost: $" + towerCost, x, y);
        y += 20;
        text("Shields: " + shields, x, y);
        y += 20;
        text("Can Purchase Shield?: " + canPurchaseShields, x, y);
        y += 20;

//        y += 20;
//        text("Paused: " + paused, x, y);
        y += 20;
        text("Entity List: " + entities.size(), x, y);
        y += 20;
//        text("Timer: " + timer, x, y);
//        y += 20;
        text("Tank Frequency: " + maxTimer, x, y);
        y += 20;
        text("Tank Spawned: " + tanksSpawned, x, y);
        y += 20;
//        text("Last Timer Decrease: " + lastTimerDecreaseTankNum, x, y);


        String pauseState;
        if (paused){
            pauseState = "play";
        } else{
            pauseState = "pause";
        }

        if (!lost) {
            int x2 = 600;
            int y2 = 20;
            text("'r' to reset", x2, y2);
            y2 += 20;
            text("'p' to " + pauseState, x2, y2);
            y2 += 20;
            text("'i' to by a shield for $" + shieldCost, x2, y2);
            y2 += 20;
            text("'s' to save game", x2, y2);
            y2 += 20;
            text("'l' to load saved data", x2, y2);
            y2 += 20;

            y2 += 20;
            text("'d' to add $10 to money", x2, y2);
            y2 += 20;
            text("'c' to clear money", x2, y2);
            y2 += 20;
            text("'k' to loose a shield", x2, y2);
            y2 += 20;
            text("'j' to return to 5 shields", x2, y2);
            y2 += 20;
            text("'b' to make maxTimer = 20", x2, y2);
            y2 += 20;
            text("'a' to slow down tank frequency", x2, y2);

        }

        // GAME OVER MESSAGE
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

        // REMOVES DEAD TANKS
        for (int i = 0; i < entities.size(); i++) {
            if (!entities.get(i).getIsAlive()) {
                entities.remove(i);
                i--;
            }
        }

        //System.out.println("Drawing " + entities.size() + " entities");

        // DRAWS ENTITIES
        for (Entity e : entities) {
            index++;
            if (e.getType().equals("tank")) {
                e.drawTexture(this, tankTexture);
            } else {
                e.drawTexture(this);
            }

            if (!paused) {
                e.act(entities);
            }
        }

        // TANK SPAWNER
        if (!paused) {
            timer--;
            if (timer <= 0) {
                timer = maxTimer;
                Tank t = new Tank();
                entities.add(t);
                tanksSpawned++;
            }
        }

        // TOWER SPAWNER
        if(money - towerCost >= 0) {
            if (tanksDestroyed % 10 == 0 && tanksDestroyed > 0) {
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

                money -= towerCost;
                towerCost += 5;
            }
        }

        // SHIELD PURCHASING
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