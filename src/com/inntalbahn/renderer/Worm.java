/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer;


/**
 *
 * @author Usr
 */
public class Worm {
    private int posx, posy, health=100, team, ti=0, animationT = 1, animationID = 0, scale=1, acc=-2;
    private boolean dir=false;
    //private int ammo[];
    
    
    public Worm(int posx, int posy, int team, int ani){
        this.posx = posx;
        this.posy = posy;
        this.team = team;
        this.animationID=ani;
        
    }

    public void wormleft(boolean unblocked, int yOverride, int gradient){
        ti++;
            if(ti>=(60/25) && dir == false){
                animationID=gradient;
               animationT++;
               dir=false;
                ti=0;
                posy = posy - yOverride;
                if(unblocked == false)posx++;
                if(animationT==14){
                    animationT=1;
                    if(unblocked)posx = posx - (10 / scale);
                    else posx = posx - (13 / scale);
                    //playSound("/com/inntalbahn/renderer/res/sounds/walk3.wav");
                }
            }
            if(ti>=(60/25) && dir){
                dir = false;
                posx = posx + 8 / scale;                    //position offset nach Richtungsänderung / kleienerer Wenderadius
                animationT=1;
            }
    }
    
    public void wormright(boolean unblocked, int yOverride, int gradient){
        ti++;
            if(ti>=(60/25) && dir){
                animationID=gradient;
                animationT++;
                dir=true;
                ti=0;
                posy = posy - yOverride;
                if(unblocked == false)posx--;
                if(animationT==14){
                    animationT=1;
                    if(unblocked)posx = posx + (10 / scale);
                    else posx = posx + (13 / scale);
                    //playSound("/com/inntalbahn/renderer/res/sounds/walk3.wav");
                }
            }
            if(ti>=(60/25) && dir == false){
                dir = true;
                posx = posx - 8 / scale;                    	//position offset nach Richtungsänderung / kleienerer Wenderadius
                animationT=1;
            }
    }
    
    public void wormjump(){
        
    }
    
    public void wormfall(){
        posy++;
        acc++;
        posy = posy+acc;
    }
    
    public void wormfallImpact(){
        System.out.println("impact" + acc);
        acc=-2;
        
    }
    
    public void wormdeath(){
        
    }
    
    public int returnY(){
        return posy;
    }
    
    public int returnX(){
        return posx;
    }
    
    public int returnAnimationState(){
        return animationT;
    }
    
    public int reAniID(){
        return animationID;
    }
    
    public int returnHealth(){
        return health;
    }
    
    public boolean returnDir(){
        return dir;
    }
    
    public void wormHealth(){
        
        health=health+20;
    }
    
    public void wormgetHealth(){
        health=health+20;
    }
    
    public void setScale(int scale){
        this.scale=scale;
    }
    
    public void setPosY(int y){
        posy=y;
    }
}
