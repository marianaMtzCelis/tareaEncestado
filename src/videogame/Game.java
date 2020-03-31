/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 * RUBRICA 20 FALTA Documentacion n shit 10 YA Sonidos 10 IDK Creatividad 10 YA
 * Aniamciones 40 FALTA Tiro Parabolico 5 YA Score 5 YA Vidas
 */
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
    private Player cookie;          // to use a cookie
    private KeyManager keyManager;  // to manage the keyboard
    private MouseManager mouseManager; // to manage the mouse
    private Target monster;         // to use the cookie monster
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
     * To get the cookie of the game
     *
     * @return cookie
     */
    public Player getPlayer() {
        return cookie;
    }

    public Target getTarget() {
        return monster;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        monster = new Target(getWidth() - 250, getHeight() / 2 - 100, 200, 200, this);
        cookie = new Player(10, getHeight() / 2, 1, 100, 100, this);
        display.getJframe().addKeyListener(keyManager);

        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

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

        if (vidas > 0) {
            keyManager.tick();
            cookie.tick();
            monster.tick();

            if (cookie.colision(monster)) {
                score += 10;
                if (score % 50 == 0) {
                    vidas++;
                }
                Assets.bite.play();
                cookie.setX(10);
            } else if (cookie.getX() > getWidth() - 250) {
                if (falladas < 2) {
                    falladas++;
                } else if (falladas == 2) {
                    vidas--;
                    falladas = 0;
                }
                Assets.missed.play();
                cookie.setX(10);
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
            monster.render(g);
            cookie.render(g);

            // displays vidas and score
            g.setColor(Color.white);
            g.drawString("Falladas: " + falladas, 80, 80);
            g.drawString("Vidas:" + vidas, 80, 100);
            g.drawString("Score: " + score, 80, 120);

            // Displays game over if vidas reaches to 0
            if (vidas <= 0) {
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
            }

            bs.show();
            g.dispose();
        }

    }

    /**
     * setting the thead for the game
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
