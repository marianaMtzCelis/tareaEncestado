/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {

    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private Player player_cookie;          // to use a player_cookie
    private KeyManager keyManager;  // to manage the keyboard
    private MouseManager mouseManager; // to manage the mouse
    private Target target_monster;         // to use the player_cookie target_monster
    private int score;              // to keep track of score
    private int vidas;              // to keep track of player's lives
    private int falladas;           // to keep track of consecutive failed attempts

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * To get the keymanager from game
     *
     * @return keymanager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * To get the Mouse Manager from game
     *
     * @return mouseManager
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     * To get the player_cookie of the game
     *
     * @return player_cookie
     */
    public Player getPlayer() {
        return player_cookie;
    }

    /**
     * To get the target_monster of the game
     *
     * @return target_monster
     */
    public Target getTarget() {
        return target_monster;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        // declaring display
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        // initializing player and target
        target_monster = new Target(getWidth() - 250, getHeight() / 2 - 50, 200, 200, this);
        player_cookie = new Player(10, getHeight() / 2, 1, 50, 50, this);
        //adding elements to display
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        // setting initial values
        this.score = 0;
        this.vidas = 5;
        this.falladas = 0;
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        // check if player is still alive
        if (vidas > 0) {
            // tick all elements
            keyManager.tick();
            player_cookie.tick();
            target_monster.tick();
            // if scored
            if (player_cookie.colision(target_monster)) {
                score += 10;
                if (score % 50 == 0) {
                    vidas++;
                }
                Assets.bite.play();
                // reset cookie
                player_cookie.setX(10);
                player_cookie.setY(getHeight()/2);
                player_cookie.setIsThrown(false);
                mouseManager.setIzquierdo(true);
                player_cookie.setTime(new Date());
            } 
            // if missed
            if (player_cookie.isIsThrown() && (player_cookie.getX() > getWidth() || player_cookie.getY() <= 0 || player_cookie.getY() >= this.getHeight())) {
                // update variables
                if (falladas < 2) {
                    falladas++;
                } else if (falladas == 2) {
                    vidas--;
                    falladas = 0;
                }
                // reset cookie
                Assets.missed.play();
                player_cookie.setX(10);
                player_cookie.setY(height/2);
                player_cookie.setIsThrown(false);
                mouseManager.setIzquierdo(true);
                player_cookie.setTime(new Date());
            }

        }
    }

    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            g.drawImage(Assets.rectangle,0,0,175,height,null);
            g.drawImage(Assets.logo, 250, 10, 445,168, null);
            g.drawImage(Assets.scoreBoard,40,80,100,77,null);
            target_monster.render(g);

            // displays vidas and score
            g.setColor(Color.black);
            g.drawString("Falladas: " + falladas, 60, 100);
            g.drawString("Vidas:" + vidas, 60, 120);
            g.drawString("Score: " + score, 60, 140);
            
            player_cookie.render(g);

            // Displays game over if vidas reaches to 0
            if (vidas <= 0) {
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
            }

            bs.show();
            g.dispose();
        }

    }

    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
