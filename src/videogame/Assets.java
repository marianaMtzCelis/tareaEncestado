/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Tarea Animaciones
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {

    public static BufferedImage background; // to store background image
    public static BufferedImage sprites; // to store the players sprites
    public static BufferedImage normalCookie; // for normal cookie
    public static BufferedImage rotatingCookie[];   // for rotating cookie
    public static BufferedImage normalMonster; // for static monster
    public static BufferedImage animatedMonster[]; // to animate monster
    public static BufferedImage gameOver;  // to display if player reaches to 0 lives
    public static SoundClip bite;  // when the player scores
    public static SoundClip missed; // when the player misses the shot

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");

        // Getting the sprites from the picture
        sprites = ImageLoader.loadImage("/images/cookieMonster.png");
        SpriteSheet spriteSheet = new SpriteSheet(sprites);
        normalCookie = spriteSheet.crop(12, 419, 194, 178);
        normalMonster = spriteSheet.crop(315,10,300,285);
        gameOver = ImageLoader.loadImage("/images/gameover.jpg");
        bite = new SoundClip("/sounds/bite.wav");
        missed = new SoundClip("/sounds/missed.wav");
        
        // Create the arrays of Images
        rotatingCookie = new BufferedImage[5];
        animatedMonster = new BufferedImage[3];

        // Croping the pictures from the sheet to the array
        for (int i = 0; i < 5; i++) {
            rotatingCookie[i] = spriteSheet.crop(i * 208+10, 419, 208, 200);
        }
        for (int i = 0; i < 3; i++) {
            animatedMonster[i] = spriteSheet.crop(i*300+15, 10, 295, 285);
        }

    }

}
