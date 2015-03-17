/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer.level;

import com.inntalbahn.renderer.graphics.SpriteSheet;

/**
 *
 * @author Usr
 */
public class Level {
    public static SpriteSheet level;
    public static SpriteSheet levelPhys;
    public int[] spawns = new int[10*2];
    
    
    
    public Level(int pLevelid){
        
        switch(pLevelid){
            case(0): level = new SpriteSheet("/com/inntalbahn/renderer/res/textures/5.png", 600, 333 , 0, 0);
                     levelPhys = new SpriteSheet("/com/inntalbahn/renderer/res/textures/5a.png", 600, 333 , 0, 0);
                     spawns[1]=10;
                     spawns[2]=30;
                     spawns[11]=50;
                     spawns[12]=70;
                     break;
            case(1): level = new SpriteSheet("/com/inntalbahn/renderer/res/textures/3.png", 600, 333, 0, 0);
                     spawns[1]=300;
                     spawns[2]=400;
                     spawns[11]=150;
                     spawns[12]=70;
                     break;
            default: level = new SpriteSheet("/com/inntalbahn/renderer/res/textures/2.png", 600, 333, 0, 0);
                     break;
        }
        
    }
    

}
