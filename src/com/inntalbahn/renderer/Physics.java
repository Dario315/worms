/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer;

import com.inntalbahn.renderer.graphics.SpriteSheet;
import com.inntalbahn.renderer.level.Level;

/**
 *
 * @author Usr
 */
public class Physics {
    private Level aLevel;
    private SpriteSheet aLevelPhys;
    private boolean ground = false;
    
    public Physics(Level pLevel){
        aLevel = pLevel;
        aLevelPhys = aLevel.levelPhys;
    }
    
    
    public boolean gravityUpdate(Worm pWorm){
        for(int i = 21; i < 40; i++){
            ground = aLevelPhys.pixels[pWorm.returnX() + i - pWorm.returnAnimationState() + (pWorm.returnY() + 40) * aLevelPhys.SIZEX] != -1;
        }
        return ground;
    }
    
    public int collisionUpdate(Worm pWorm){
        int re=0;
        if(pWorm.returnDir() == false){
            for(int i = 16; i < 40; i++){
                if(aLevelPhys.pixels[pWorm.returnX() + 23 - (pWorm.returnAnimationState()) + (pWorm.returnY() + i) * aLevelPhys.SIZEX] != -1)re++; //pWorm.returnAnimationState()
            }
        }
        else{
            for(int i = 16; i < 40; i++){
                if(aLevelPhys.pixels[pWorm.returnX() + 40 + (pWorm.returnAnimationState())+ (pWorm.returnY() + i) * aLevelPhys.SIZEX] != -1)re++; //pWorm.returnAnimationState()
            }
        }
        return re;
    }
}
