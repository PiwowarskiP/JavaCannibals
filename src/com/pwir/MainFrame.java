package com.pwir;

import com.pwir.sprites.ChefBarbarian;

import javax.swing.*;
import java.awt.EventQueue;                         // SwingUtilities.invokeLater() tak w sumie tylko wywołuje EventQueue.invokeLater

public class MainFrame extends JFrame {

    public static final int SCREEN_SIZE_X = 1024;
    public static final int SCREEN_SIZE_Y = 768;
    private static JLayeredPane layers;
    public static LogTerminal terminal;


    public MainFrame(String name){
        setName(name);
        setTitle(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(SCREEN_SIZE_X, SCREEN_SIZE_Y);
        setResizable(false);

        this.getContentPane().setLayout(null);

        layers = getLayeredPane();
        prepareLayersBeforeStart();
        setVisible(true);
    }

    // funkcja przygotuje tło i panel użytkownika
    private void prepareLayersBeforeStart(){
        BackgroundLayer bgLayer = new BackgroundLayer();
        layers.setLayer(bgLayer, 1);
        layers.add(bgLayer);

        UserPanel userPanel = new UserPanel();
        layers.setLayer(userPanel, 2);
        layers.add(userPanel);

        //przygotowanie terminala
        terminal = new LogTerminal(10, 500);
        layers.setLayer(terminal, 3);
        layers.add(terminal);
    }

    public static JLayeredPane getLayers(){
        return layers;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame("Projekt Kanibale");
            }
        });
    }

}