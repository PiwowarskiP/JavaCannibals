package com.pwir;

import com.pwir.sprites.BarbarianFireplace;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class BarbariansFireplacePanel extends JPanel implements Runnable{

    private Image barbImage;

    private Random rnd = new Random(System.currentTimeMillis());

    private int numberOfBarbarians;
    private int numberOfMeat;

    public static int barbCenterX, barbCenterY;
    private int r = 200;                        //promień okręgu, po jakim będziemy się poruszać; w pikselach
    private double radiany = 0;
    private double offset;

    private volatile Boolean running = true;
    private List<BarbarianFireplace> barbsList = new ArrayList<>();


    public BarbariansFireplacePanel(int numberOfBarbarians, int numberOfMeat) {
        setBounds(0,0, MainFrame.SCREEN_SIZE_X, MainFrame.SCREEN_SIZE_Y - UserPanel.userPanelHeight);
        setOpaque(false);

        this.numberOfBarbarians = numberOfBarbarians;
        this.numberOfMeat = numberOfMeat;
        loadBarbImage();
        offset = 2*Math.PI/numberOfBarbarians;
        for(int i = 0; i< numberOfBarbarians; i++){
            int posX = (int) (barbCenterX + Math.cos(radiany + i*offset) * r);
            int posY = (int) (barbCenterY + Math.sin(radiany + i*offset) * r);
            barbsList.add(new BarbarianFireplace(posX, posY));
        }
    }

    public void setRunningTrue(){
        running = true;
    }


    private void loadBarbImage(){
        try {
            barbImage = ImageIO.read(this.getClass().getResource("/Barbarian.png"));
        } catch (IOException ex) {
            System.err.println("Not loaded img.");
        }
        barbCenterX = (MainFrame.SCREEN_SIZE_X + 120)/2 - barbImage.getWidth(null)/2;
        barbCenterY = (MainFrame.SCREEN_SIZE_Y - 120)/2 - barbImage.getHeight(null)/2;
    }

    @Override
    public void run(){
        /*
         Algorytm oparty o problem Producent - Konsument
        */
        while(true) {
            AnimationController.lock.lock();
            try {
                if(AnimationController.potSize == 0) {
                    AnimationController.potEmpty.await();
                }
                startMovingInCircle();
            } catch (Exception ex) {
            } finally {
                try {
                    AnimationController.lock.unlock();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void startMovingInCircle(){
            for(int i = 0; i<barbsList.size(); i++){
                barbsList.get(i).setPosition(
                        (int)(barbCenterX + Math.cos(radiany + i*offset) * r),
                        (int)(barbCenterY + Math.sin(radiany + i*offset) * r)
                );
            }
            sleepThisThreadForMilis(40);
            if((rnd.nextInt(100 - 1) +1) == 1) {
                randomBarbarianStartsEating();
                repaint();
            } else {
                repaint();
            }

            if(AnimationController.potSize == 0 ){
                AnimationController.potFull.signalAll();
            }
        radiany = radiany + 0.01;
        if (radiany >= Math.PI*2) {radiany = 0;}

    }

    private void randomBarbarianStartsEating(){

        AnimationController.potSize --;
        sleepThisThreadForMilis(40);
        int indexBarb = rnd.nextInt(numberOfBarbarians - 1) + 1;
        MainFrame.terminal.addLog("Kanibal nr " + indexBarb + " je.");
        if(AnimationController.potSize == 0){
            MainFrame.terminal.addLog("Kociol jest pusty !");
        }
    }


    public void paintComponent(Graphics g) {
        for(int i = 0; i<barbsList.size(); i++){
            g.drawImage(barbImage, barbsList.get(i).getX(), barbsList.get(i).getY(), null );
        }
    }

    private void sleepThisThreadForMilis(int milis){
        try{
            Thread.sleep(milis);
        }catch(Exception ex){}
    }
}

