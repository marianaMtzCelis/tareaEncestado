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
public class Target extends Item {

    private Game game;
    private Animation targetAnimation; // stores the animation of player going up


    public Target(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;

        // creates the animations
        this.targetAnimation = new Animation(Assets.animatedMonster, 100);
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

    @Override
    public void tick() {
       
        this.targetAnimation.tick();
  
    }

    @Override
    public void render(Graphics g) {
        
        g.drawImage(targetAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        
        //g.drawImage(Assets.normalCookie, getX(), getY(), getWidth(), getHeight(), null);
          
     
    }
}
