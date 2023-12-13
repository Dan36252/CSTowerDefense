import processing.core.PApplet;

import java.util.ArrayList;

public class Bullet extends Entity {
    private static double BULLET_SPEED = 8;
    private static int BULLET_RADIUS = 10;
    private static int shootOffsetX = 30;
    private double speedX = 0;
    private double speedY = 0;
    private Tower origin;
    private Tank target;
    private boolean canHit = true;
    public Bullet(int x, int y, Tower origin, Tank target) {
        super("bullet", x, y, BULLET_SPEED, BULLET_RADIUS);
        this.origin = origin;
        this.target = target;
//        double[] speeds = calculateSpeeds();
//        this.speedX = speeds[0];
//        this.speedY = speeds[1];
    }

    public void drawTexture(PApplet pApplet) {
        if(this.isAlive == false) {return;}
        pApplet.fill(255, 0, 0);
        pApplet.ellipse(x, y, BULLET_RADIUS, BULLET_RADIUS);
    }
    private double[] calculateSpeeds(){
        double dX = target.x+shootOffsetX-origin.x;
        double dY = target.y-origin.y;
        double dist = Math.sqrt(dX*dX+dY*dY);
        double sX = dX*speed/dist;
        double sY = dY*speed/dist;
        double[] result = {sX, sY};
        return result;
    }
    private void updateSpeeds() {
        double[] speeds = calculateSpeeds();
        this.speedX = speeds[0];
        this.speedY = speeds[1];
    }
    private boolean isInBounds(){
        boolean inBounds = true;
        if(x < 0 || x > 800 || y < 0 || y > 800) inBounds = false;
        return inBounds;
    }

    private boolean hitTarget() {
        double dX = Math.abs(x-target.x);
        double dY = Math.abs(y-target.y);
        double disp = Math.sqrt(dX*dX+dY*dY);
        return (disp <= (this.radius+Tank.TANK_RADIUS)/2);
    }

    public void act() {
        if(this.isAlive == false) {
            return;
        }
        if(speedX == 0 && speedY == 0) updateSpeeds();

        x += speedX;
        y += speedY;

        if(!isInBounds()) setAlive(false);
        if(hitTarget() && canHit && target.isAlive){
            canHit = false;
            target.tankHit();
            //target.setAlive(false);
            setAlive(false);
        }
    }
}
