package com.pwir.sprites;

import com.pwir.AnimationController;
import com.pwir.BackgroundLayer;
import com.pwir.MainFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChefBarbarian extends JPanel implements Runnable{

    private Image chef;

    public static int X_CUSTOM = BackgroundLayer.CAGE_X;
    public static int Y_CUSTOM = 300;
    private int x;
    private int y;

    private int potMaxNumberOfMeat;
    private volatile Boolean running = true;

    public ChefBarbarian(int potMaxNumberOfMeat) {
        this.potMaxNumberOfMeat = potMaxNumberOfMeat;
        setBounds(0,0, MainFrame.SCREEN_SIZE_X, MainFrame.SCREEN_SIZE_Y - 120);
        x = X_CUSTOM;
        y = Y_CUSTOM;
        setOpaque(false);
        setDoubleBuffered(true);
        loadChefToChefImage();
    }

    private void stopExecution(){
        running = false;
        Thread.currentThread().interrupt();
    }
    public void setRunningTrue(){
        running = true;
    }


    private void loadChefToChefImage(){
        try {
            chef = ImageIO.read(this.getClass().getResource("/ChiefBarbarian.png"));
        } catch (IOException ex) {
            System.err.println("Not loaded img.");
        }
    }

    private void loadChefWithhPriestToImage(){
        try {
            chef = ImageIO.read(this.getClass().getResource("/ChefPriest.png"));
        } catch (IOException ex) {
            System.err.println("Not loaded img.");
        }
    }

    @Override
    public void run() {
        while(AnimationController.getNumberOfGuysInCage() > 0) {
            AnimationController.decrementNumberOfGuysInCage();
            AnimationController.lock.lock();
            try {
                if (AnimationController.potSize != 0) {
                    AnimationController.potFull.await();
                }
                doChefMoves();
            } catch (Exception e) {
            } finally {
                try {
                    AnimationController.lock.unlock();
                } catch (Exception ex) {
                }
            }
        }
        stopExecution();
    }

    private void doChefMoves() {
        /*
           MESSAGE o pojsciu po misjonarza
        */
        MainFrame.terminal.addLog("Kucharz idzie po misjonarza...");
        while (y >= BackgroundLayer.CAGE_Y + 30 ){
            if(running == false){
                x = X_CUSTOM;
                y = Y_CUSTOM;
                return;
            }
            sleepThisThreadForMilis(40);
            y = y - 5;
            repaint();
        }
        /* Symulacja wyciagania z klatki */

        MainFrame.terminal.addLog("Kucharz bierze misjonarza (zostalo: "
                + AnimationController.getNumberOfGuysInCage() + ")");
        sleepThisThreadForMilis(1000);
        System.out.println(y);
        loadChefWithhPriestToImage();
        moveFromCageToPot();
    }

    private void  moveFromCageToPot(){
        while(true){
            // Na sztywno POT_Y
                if(y == 244){
                    x = x + 4;
                    sleepThisThreadForMilis(40);
                    // Na sztywno POT_X
                    if(x >= 512 - 44 ) {
                        /*
                        MESSAGE o uzupelnianiu kotla
                        */
                        MainFrame.terminal.addLog("Kucharz gotuje...");
                        sleepThisThreadForMilis(2000);
                        MainFrame.terminal.addLog("Kociol pelny - " + potMaxNumberOfMeat + " porcje.");
                       break;
                    }
                } else {
                    x = x + 5;
                    y = y + 3;
                    sleepThisThreadForMilis(40);
                }
                repaint();
            }
        moveFromPotToCustom();
    }

    private void moveFromPotToCustom() {
        loadChefToChefImage();
        while (true) {
            x = x - 5;
            y = y + 1;
            sleepThisThreadForMilis(40);
            repaint();
            if (x <= 90) {
                y= Y_CUSTOM;
                x= X_CUSTOM;
                break;
            }
        }

        MainFrame.terminal.addLog("Kucharz wraca na swoje miejsce ");
        AnimationController.potSize = potMaxNumberOfMeat;
        AnimationController.potEmpty.signalAll();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(chef, x, y, null);
    }

    private void sleepThisThreadForMilis(int milis){
        try{
            Thread.sleep(milis);
        }catch(Exception ex){}
    }
}

