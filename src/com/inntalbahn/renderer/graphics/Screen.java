/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer.graphics;
import com.inntalbahn.renderer.GameLogic;
import static com.inntalbahn.renderer.graphics.SpriteSheet.wormASprite;
import com.inntalbahn.renderer.level.Level;


/**
 *
 * @author Usr
 */
public class Screen {
    private int width, height;
    public int[] pixels;                    //zu darstellender bereich / muss public
    private GameLogic aGameLogic;
    private static SpriteSheet aSpriteSheet;
    //private SpriteSheet[] aSpriteSheet.wormASprite;
    private int scale=1;                    //skalierung der sprites
   
    
    public Screen(int width, int height, GameLogic aGameLogic){
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        this.aGameLogic = aGameLogic;
        
        aSpriteSheet = Level.level;

        wormASprite[0] = new SpriteSheet("/com/inntalbahn/renderer/res/textures/wwalk.png", 64, 900, 60, -8355648); //x size, y size, tilseize y, alpha color
        wormASprite[1] = new SpriteSheet("/com/inntalbahn/renderer/res/textures/wwalkd.png", 64, 900, 60, -8355648);
        wormASprite[2] = new SpriteSheet("/com/inntalbahn/renderer/res/textures/wwalku.png", 64, 900, 60, -8355648);
        
        //aSpriteSheet.wormASprite = new SpriteSheet[wormASprite.length];

        //System.arraycopy(SpriteSheet.wormASprite, 0, aSpriteSheet.wormASprite, 0, wormASprite.length);
        
        
        
        

        
    }
    
    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    
    public void render(int xOffset, int yOffset){
        ///////////////Draw aSpriteSheet//////////////////
        for (int y = 0; y < height; y++){
            int yp = y + yOffset;
            if (yp < 0 || yp >= height) continue;
            for (int x = 0; x < width; x++){
                int xp = x + xOffset;
                
                if (xp < 0 || xp >= width) continue;
                
                //pixels[xp + yp*width] = Sprite.bg.pixels[(x & (Sprite.bg.SIZE-1))+(y & (Sprite.bg.SIZE-1)) * Sprite.bg.SIZE];
                pixels[xp + yp*width] = aSpriteSheet.pixels[x + y * 600];
            }
            }

        ///////////////Draw Worms//////////////////
        int pos = 0;
        for(int p=0; p < aGameLogic.aWorm.length; p ++){
            aGameLogic.aWorm[p].setScale(scale);
            for (int y = 0; y < 64; y++){
                    if (y < 0 || y >= height) continue;
                        for (int x = 0; x < 64; x++){
                            if (x < 0 || x >= width) continue;
                            if(aGameLogic.aWorm[p].returnDir() == true){
                                pos = (aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].SIZEX - x)+(y * aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].SIZEX ) + aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].SIZEX * aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].TILESIZE * aGameLogic.aWorm[p].returnAnimationState();
                            }
                            else{
                                pos = x+(y * aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].SIZEX ) + aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].SIZEX * aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].TILESIZE * aGameLogic.aWorm[p].returnAnimationState();
                            }
                            if(aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].pixels[pos] != aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].returnAlpha()){
                            pixels[(x /scale + aGameLogic.aWorm[p].returnX())+ ((y /scale )+ aGameLogic.aWorm[p].returnY())*width] = aSpriteSheet.wormASprite[aGameLogic.aWorm[p].reAniID()].pixels[pos];
                        }
                    }
                }
            }

        }

}
