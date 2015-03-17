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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import sun.applet.Main;

/**
 *
 * @author Usr
 */
public class Game extends Canvas implements Runnable{
    
    
    public static int width = 600;                      //resolution
    public static int height = width / 16*9;            //aspectratio
    public static int scale = 2;                        //Window scale
    public static String title = "Worms";               //window title
    
    private Thread thread;                              //initiate thread
    private JFrame frame;                               //initiate frame
    private Keyboard key;                               //initiate key handler
    private GameLogic aGameLogic;                       // assotiation gamelogic

    private boolean running = false;                    //running flag
    
    private int ti = 0;
    
    private int x=0, y=0;                               //Camera position
    private Screen screen;                              //Initiate Screen
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    ////////////////Constructor/////////////////////
    
    public Game(){
        Dimension size = new Dimension(width * scale, height *scale); //upscaling
        setPreferredSize(size);
        
        /////////// Erstellen der wichtigsten Objekte/////////////
        aGameLogic = new GameLogic(0,2,0,0,8);           //neues objekt GameLogic (parameter: level spielerzahl etc)
        screen = new Screen(width, height, aGameLogic); //neues Objekt screen mit assoziation zu GameLogic
        frame = new JFrame();                           //frame
        key = new Keyboard();                           //keyhandler
        frame.addKeyListener(key);                      //keyhandler für frame 
        
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
    
    /////////////Bereite nächste Spiel-Frame vor//////////////
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
        BufferStrategy bs = getBufferStrategy();
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

    //funktion zum abspielen von wav dateien
/*
public static synchronized void playSound(final String url) {
  new Thread(new Runnable() {
    public void run() {
      try {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
          Main.class.getResourceAsStream(url));
        clip.open(inputStream);
        clip.start(); 
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }).start();
}
*/
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
