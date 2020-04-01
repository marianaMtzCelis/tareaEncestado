/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Tarea Animaciones
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item {

    private int direction;
    private Game game;
    private Animation cookieAnimation; // stores the animation of player going up


    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;

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
        if (x <= 100){
            this.x = x;
        }
    }
    
    @Override
    public void tick() {
        
        
//        if (game.getMouseManager().isIzquierdo()) {
            setX(game.getMouseManager().getX());
            setY(game.getMouseManager().getY());
//        }
         
        if (game.getKeyManager().up) {
            // updating animation
            this.cookieAnimation.tick();
            direction = 1;
            setY(getY() - 1);
        }
        
         if (game.getKeyManager().down) {
            // updating animation
            this.cookieAnimation.tick();
            direction = 3;
            setY(getY() + 1);
        }

        if (game.getKeyManager().left) {
            // updating animation
            this.cookieAnimation.tick();
            direction = 2;
            setX(getX() - 1);
        }
        
         if (game.getKeyManager().right) {
            // updating animation
            this.cookieAnimation.tick();
            direction = 4;
            setX(getX() + 1);
        }

  
    }

    @Override
    public void render(Graphics g) {
        
        g.drawImage(cookieAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        
        //g.drawImage(Assets.normalCookie, getX(), getY(), getWidth(), getHeight(), null);
          
     
    }
}
