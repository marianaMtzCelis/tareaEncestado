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
    private Game game;
    private Animation cookieAnimation; // stores the animation of player going up
    private int vo, yo, xo;
    private boolean isThrown;
    private double t;
    private static Date time = new Date();


    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;
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

    public void setX(int x){
        if (x <= 150 && x >= 0 && !isThrown){
            this.x = x;
        } else if (isThrown) {
            this.x = x;
        }
    }
    
    public void setY(int y){
        if (y >= 0 && (y+80) <= game.getHeight() && !isThrown){
            this.y = y;
        } else if (isThrown) {
            this.y = y;
        }
    }

    public void setIsThrown(boolean isThrown) {
        this.isThrown = isThrown;
    }

    public boolean isIsThrown() {
        return isThrown;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getT() {
        return t;
    }

    public static Date getTime() {
        return time;
    }

    public static void setTime(Date time) {
        Player.time = time;
    }
    
    
    
    
    @Override
    public void tick() {
        if (!isThrown) {
            setX(game.getMouseManager().getX());
            setY(game.getMouseManager().getY());
            if (!game.getMouseManager().isIzquierdo()) {
                isThrown = true;
                vo = this.getX() + 50;
                xo = this.getX();
                yo = this.getY();
                //System.out.println(vo);
            } 
        } else {
              setX(xo + (int)(vo*0.5253*t));
              setY(yo - ((int)((vo*0.851*t) - (4.91*t*t))));
              t = (new Date().getTime() - time.getTime()) / 1000.0 ;
              System.out.println(t);
              this.cookieAnimation.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        
        g.drawImage(cookieAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        
        //g.drawImage(Assets.normalCookie, getX(), getY(), getWidth(), getHeight(), null);
          
     
    }
}
