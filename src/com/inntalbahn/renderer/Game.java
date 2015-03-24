/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inntalbahn.renderer;
import com.inntalbahn.renderer.graphics.Screen;
import com.inntalbahn.renderer.graphics.Sprite;
import com.inntalbahn.renderer.input.Keyboard;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

/**
 *
 * @author Usr
 */
public class Game extends Canvas implements Runnable{
    
    
    public static int width = 600;                      //resolution
    public static int height = width / 16*9;            //aspectratio
    public static int scale = 2;                        //Window scale
    public static String title = "Game-Renderer";       //window title
    
    private Thread thread;                              //initiate thread
    private JFrame frame;                               //initiate frame
    private Keyboard key;                               //initiate key handler
    private GameLogic aGameLogic;

    private boolean running = false;                    //
    
    private int ti = 0;
    
    private int x=0, y=0;                               //Camera position
    private Screen screen;                              //Initiate Screen
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    ////////////////Constructor/////////////////////
    
    public Game(){
        Dimension size = new Dimension(width * scale, height *scale);
        setPreferredSize(size);
        screen = new Screen(width, height);
        frame = new JFrame();
        key = new Keyboard();
        frame.addKeyListener(key);
        aGameLogic = screen.aGameLogic;
        
    }

    
    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    
    public synchronized void stop(){
        try{
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /////////////Hauptschleife//////////////
    public void run(){
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double diff = 0;
        int frames = 0, updates = 0;
        //requestFocus();
        while(running){
            long now = System.nanoTime();
            diff += (now-lastTime) / ns;
            lastTime = now;
            while (diff >= 1){
                update();
                updates++;
                diff--;
            }
            render();
            frames++;

            //////////Statistiken/////////////
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title+"  |  " + updates + " Tickrate  " + frames + " FPS");
                updates = 0;
                frames = 0;
            }
        }
    }
    
    /////////////Bereite nächste SPiel-Frame vor//////////////
    public void update(){
        key.update();
        aGameLogic.update();
        if(key.up)aGameLogic.moveUp();
        if(key.down)aGameLogic.moveDown();
        if(key.left)aGameLogic.moveLeft();
        if(key.right)aGameLogic.moveRight();
    }
    
    ////////////Zeichne Frame/////////////
    
    public void render(){
        BufferStrategy bs =getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        screen.render(x,y);
        
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
    

    
    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle(game.title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        
        game.start();
    }
}

//test
