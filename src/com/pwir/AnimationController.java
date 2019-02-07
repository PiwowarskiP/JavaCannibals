package com.pwir;

import com.pwir.BarbariansFireplacePanel;
import com.pwir.sprites.ChefBarbarian;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AnimationController {

    private JLayeredPane _layers;
    public ChefBarbarian chefBarbarianLayer;
    public BarbariansFireplacePanel barbariansFireplacePanel;

    public static final ReentrantLock lock = new ReentrantLock (true);
    public static final Condition potEmpty = lock.newCondition();
    public static final Condition potFull = lock.newCondition();


    private Thread chefBarbarianThread;
    private Thread barbariansFireplaceThread;


    public volatile static int numberOfBarbariansFireplace;
    private volatile static int numberOfGuysInCage;
    public static volatile int potSize = 0;
    private int numberOfMeat;


    public AnimationController(int numberOfMeat, int numberOfBarbariansFireplace, int numberOfGuysInCage){
        _layers = MainFrame.getLayers();
        this.numberOfMeat = numberOfMeat;
        this.numberOfBarbariansFireplace = numberOfBarbariansFireplace;
        this.numberOfGuysInCage = numberOfGuysInCage;
    }

    public static int getNumberOfGuysInCage() {
        return numberOfGuysInCage;
    }
    public static void decrementNumberOfGuysInCage() {
        numberOfGuysInCage--;
    }

    public void start(){
        setAnimationPanels();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                chefBarbarianThread =  new Thread(chefBarbarianLayer);
                chefBarbarianLayer.setRunningTrue();
                chefBarbarianThread.start();

                barbariansFireplaceThread = new Thread(barbariansFireplacePanel);
                barbariansFireplacePanel.setRunningTrue();
                barbariansFireplaceThread.start();
            }
        });
    }

    private void setAnimationPanels(){
        chefBarbarianLayer = new ChefBarbarian(numberOfMeat);
        setPanelLayer(chefBarbarianLayer, 3);

        barbariansFireplacePanel = new BarbariansFireplacePanel(numberOfBarbariansFireplace, numberOfMeat);
        setPanelLayer(barbariansFireplacePanel, 4);

    }

    private void setPanelLayer(JPanel panel, int layerNumber ){
        _layers.setLayer(panel, layerNumber);
        _layers.add(panel);
    }

}
