/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer.graphics;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Usr
 */
public class SpriteSheet {
    
    private String path;
    public final int SIZEX;
    public final int SIZEY;
    public final int TILESIZE;
    public int[] pixels;
    int alpha;
    public static SpriteSheet[] wormASprite = new SpriteSheet[50]; 
    
    
    //public static SpriteSheet bgsheet = new SpriteSheet("/com/inntalbahn/renderer/res/textures/bg.png", 256, 256);
    //public static SpriteSheet bgsheet = new SpriteSheet("/com/inntalbahn/renderer/res/textures/3.png", 600, 333);
    
    public SpriteSheet(String path, int sizex, int sizey, int tilesize, int alpha){
        this.path = path;
        SIZEX = sizex;
        SIZEY = sizey;
        pixels = new int [SIZEX*SIZEY];
        TILESIZE=tilesize;
        this.alpha=alpha;
        load();
    }
    
    private void load(){
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);//Image to Array
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("spritesheetload Error");
        }

    }
    
    public int returnAlpha(){
        return alpha;
    }
    
}
