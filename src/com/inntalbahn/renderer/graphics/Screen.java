/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer.graphics;

import com.inntalbahn.renderer.GameLogic;
import com.inntalbahn.renderer.Worm;
import static com.inntalbahn.renderer.graphics.SpriteSheet.wormASprite;
import com.inntalbahn.renderer.level.Level;


/**
 *
 * @author Usr
 */
public class Screen {
    private int width, height;
    public int[] pixels;
    public GameLogic aGameLogic;
    public static SpriteSheet level;
    public Worm[] aWorm;
    public SpriteSheet[] animationSprite;
    private int scale=1;
   
    
    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        
        aGameLogic = new GameLogic(0,2,0,0,8);                                    //define level etc
        level = Level.level;
        aWorm = new Worm[aGameLogic.aWorm.length];
        System.arraycopy(aGameLogic.aWorm, 0, aWorm, 0, aGameLogic.aWorm.length);
        
        wormASprite[0] = new SpriteSheet("/com/inntalbahn/renderer/res/textures/wwalk.png", 64, 900, 60, -8355648); //x size, y size, tilseize y, alpha color
        wormASprite[1] = new SpriteSheet("/com/inntalbahn/renderer/res/textures/wwalkd.png", 64, 900, 60, -8355648);
        wormASprite[2] = new SpriteSheet("/com/inntalbahn/renderer/res/textures/wwalku.png", 64, 900, 60, -8355648);
        
        animationSprite = new SpriteSheet[wormASprite.length];

        System.arraycopy(SpriteSheet.wormASprite, 0, animationSprite, 0, wormASprite.length);
        
        
        
        
        

        
    }
    
    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    
    public void render(int xOffset, int yOffset){
        ///////////////Draw Level//////////////////
        for (int y = 0; y < height; y++){
            int yp = y + yOffset;
            if (yp < 0 || yp >= height) continue;
            for (int x = 0; x < width; x++){
                int xp = x + xOffset;
                
                if (xp < 0 || xp >= width) continue;
                
                //pixels[xp + yp*width] = Sprite.bg.pixels[(x & (Sprite.bg.SIZE-1))+(y & (Sprite.bg.SIZE-1)) * Sprite.bg.SIZE];
                pixels[xp + yp*width] = level.pixels[x + y * 600];
            }
            }

        ///////////////Draw Worms//////////////////
        int pos = 0;
        for(int p=0; p < aWorm.length; p ++){
            aWorm[p].setScale(scale);
            for (int y = 0; y < 64; y++){
                    if (y < 0 || y >= height) continue;
                        for (int x = 0; x < 64; x++){
                            if (x < 0 || x >= width) continue;
                            if(aWorm[p].returnDir() == true){
                                pos = (animationSprite[aWorm[p].reAniID()].SIZEX - x)+(y * animationSprite[aWorm[p].reAniID()].SIZEX ) + animationSprite[aWorm[p].reAniID()].SIZEX * animationSprite[aWorm[p].reAniID()].TILESIZE * aWorm[p].returnAnimationState();
                            }
                            else{
                                pos = x+(y * animationSprite[aWorm[p].reAniID()].SIZEX ) + animationSprite[aWorm[p].reAniID()].SIZEX * animationSprite[aWorm[p].reAniID()].TILESIZE * aWorm[p].returnAnimationState();
                            }
                            if(animationSprite[aWorm[p].reAniID()].pixels[pos] != animationSprite[aWorm[p].reAniID()].returnAlpha()){
                            pixels[(x /scale + aWorm[p].returnX())+ ((y /scale )+ aWorm[p].returnY())*width] = animationSprite[aWorm[p].reAniID()].pixels[pos];
                        }
                    }
                }
            }

        for(int x=0; x <= width; x++){
            for(int y=0; y <= height; y++){
                if(((x+y*width) <= (width*height)) && aGameLogic.path[x+y*width])pixels[x+y*width]=0;
            }
        }


        }

}
