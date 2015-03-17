/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer;

import com.inntalbahn.renderer.level.Level;

/**
 *
 * @author Usr
 */
public class GameLogic {
    private int playercount = 3, teams=2, level, roundtime = 45, mode,playersperteam, activeWorm=2, time = 0;
    public Worm[] aWorm = new Worm[playercount];     //array für worm assoziation muss public
    private Physics aPhysics;
    private Level aLevel;
    boolean fall = false;                  //flag worm falling
    
    
    public GameLogic(int mode, int teams, int level, int roundtime, int playercount){
        this.mode=mode;
        this.teams=teams;
        this.level=level;
        //this.roundtime=roundtime;
        this.playercount=playercount;
        aLevel = new Level(level);                  //neues level objekt (parameter = level id)
        aPhysics = new Physics(aLevel);             //neues physik objekt (paramenter assotiation zu level)
        spawnWorms();
    }
    
    
    public void spawnWorms(){
        //lese spawn koordinaten aus aLevel.spawns array und erstelle ein worm objekt
        /*
        playersperteam=playercount/teams;
        playersperteam=1;
        int ppt2=playersperteam;
        while(playercount>0){
            aWorm[playercount] = new Worm(aLevel.spawns[playercount], aLevel.spawns[playercount+aLevel.spawns.length], teams);
            playercount--;
            playersperteam--;
            if(playersperteam==0){
                teams--;
                playersperteam=ppt2;
            }
            
            
        }
        */
        
        //manuelles erstellen und zuweisen der assotiantionen
        //*
        //aWorm[0] = new Worm(10, 10, 1,0);
        aWorm[0] = new Worm(400, 100, 1,0);
        //aWorm[2] = new Worm(200, 20, 2,2);
        //aWorm[3] = new Worm(80, 40, 2,1);

        aWorm[1] = new Worm(10, 100, 1,0);
        aWorm[2] = new Worm(260, 100, 1,0);
        //aWorm[6] = new Worm(130, 20, 2,0);
        //aWorm[7] = new Worm(300, 40, 2,0);
        //*/
                
    }
    
    public void moveLeft(){
        //blocked function
        int override = aPhysics.collisionUpdate(aWorm[activeWorm]), gradient;
        if(override < -1)gradient = 1;
        if(override > 1) gradient = 2;
        else gradient = 0;
        aWorm[activeWorm].wormleft(true, override, gradient);
        //aWorm[activeWorm].setPosY(aWorm[activeWorm].returnY()-aPhysics.collisionUpdate(aWorm[activeWorm]));
    }
    
    public void moveRight(){
        int override = aPhysics.collisionUpdate(aWorm[activeWorm]), gradient;
        if(override < -1)gradient = 1;
        if(override > 1) gradient = 2;
        else gradient = 0;
        aWorm[activeWorm].wormright(true, override, gradient);
    }
    
    public void moveUp(){
        activeWorm++;
        if(activeWorm>(aWorm.length-1))activeWorm=(aWorm.length-1);

    }
        
    
    public void moveDown(){
        activeWorm--;
        if(activeWorm<0)activeWorm=0;
    }
    
    public int returnActive(){
        return activeWorm;
    }
    
    public void update(){
        time++;
        if(time >= roundtime*60){
            time=0;
            endMove();
        }
        fall();
        
    }
    
    public void fall(){
        if(aPhysics.gravityUpdate(aWorm[activeWorm]) == false){
            aWorm[activeWorm].wormfall();
            fall = true;
        }
        else{
            if( fall == true){
                aWorm[activeWorm].wormfallImpact();
                fall = false;
            }
        }
    }
    
    public void endMove(){
        
    }

}
