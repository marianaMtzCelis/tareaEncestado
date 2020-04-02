/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Tarea Animaciones
 */
package videogame;

import java.awt.Graphics;
import java.util.Date;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item {

    private int direction;          
    private Game game;              // this game
    private Animation cookieAnimation; // stores the animation of player going up
    private int vo, yo, xo;         // initial velocity and positions
    private boolean isThrown;       // checks whether cookie has been throen
    private double t;               // checks the time for the formula
    private static Date time = new Date();  // current time


    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;
        // initial velocity
        vo = 0;
        t = new Date().getTime() - time.getTime() / 1000.0;
        isThrown = false;

        // creates the animations
        this.cookieAnimation = new Animation(Assets.rotatingCookie, 100);
    }

    /**
     * gets the direction of the player
     *
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * gets the width of the player
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * gets the height of the player
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * sets the direction of the player
     *
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * sets the width of the player
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * sets the height of the player
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    // updates x depending on conditions
    public void setX(int x){
        // if on throwing area
        if (x <= 150 && x >= 0 && !isThrown){
            this.x = x;
        } else if (isThrown) {
            // if it's in the air
            this.x = x;
        }
    }
    
    // updates x depending on conditions
    public void setY(int y){
        // if on throwing area
        if (y >= 0 && (y+80) <= game.getHeight() && !isThrown){
            this.y = y;
        } else if (isThrown) {
            // if it's in the air
            this.y = y;
        }
    }

    /**
     * sets the isThrown state of the player
     *
     * @param isThrown
     */
    public void setIsThrown(boolean isThrown) {
        this.isThrown = isThrown;
    }

    /**
     * gets the isThrown attribute of the player
     *
     * @return isThrown
     */
    public boolean isIsThrown() {
        return isThrown;
    }

    /**
     * sets the t of the player
     *
     * @param t
     */
    public void setT(double t) {
        this.t = t;
    }

    /**
     * gets the t of the player
     *
     * @return t
     */
    public double getT() {
        return t;
    }

    /**
     * gets the time of the player
     *
     * @return time
     */
    public static Date getTime() {
        return time;
    }
    
    /**
     * sets the time of the player
     *
     * @param time
     */
    public static void setTime(Date time) {
        Player.time = time;
    }

    @Override
    public void tick() {
        // if it has not been thrown
        if (!isThrown) {
            // move according to mouse
            setX(game.getMouseManager().getX());
            setY(game.getMouseManager().getY());
            //  update xo and yo according to the position of the player until izquierdo is released
            if (!game.getMouseManager().isIzquierdo()) {
                isThrown = true;
                vo = (185 - this.getX());
                xo = this.getX();
                yo = this.getY();
            } 
        } else {
            // update position of cookie according to the formula
            for (int i = 0; i <= 1000; ++i){
                setX(xo + (int)(vo*0.5253*t));
                setY(yo - ((int)((vo*0.851*t) - (4.91*t*t))));
                t = (new Date().getTime() - time.getTime()) / 1000.0;
                // tick cookie animation
                this.cookieAnimation.tick();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(cookieAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
}
