package com.pwir;

import com.pwir.sprites.ChefBarbarian;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackgroundLayer extends JPanel {

    private Image backgroundGrass;
    private Image pot;
    private Image cage;
    private Image barbImage;


    public static final int POT_X = (MainFrame.SCREEN_SIZE_X + 120)/2 - 120/2;
    public static final int POT_Y = (MainFrame.SCREEN_SIZE_Y - 120)/2 - 161/2;

    public static final int CAGE_X = 90;
    public static final int CAGE_Y = 90;

    public BackgroundLayer() {
        setBounds(0,0, MainFrame.SCREEN_SIZE_X, MainFrame.SCREEN_SIZE_Y - UserPanel.userPanelHeight);
        setOpaque(false);

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2 = prepareSurfaceGraphics2D(g);
    }

    public Graphics2D prepareSurfaceGraphics2D(Graphics g){
        super.paintComponent(g);
        try {
            backgroundGrass = ImageIO.read(this.getClass().getResource("/background.png"));
            pot = ImageIO.read(this.getClass().getResource("/pot.png"));
            cage = ImageIO.read(this.getClass().getResource("/cage.png"));
            barbImage = ImageIO.read(this.getClass().getResource("/Barbarian.png"));
        } catch (IOException ex) {
            System.err.println("Not loaded img.");
        }

        g.drawImage(backgroundGrass, 0, 0, null);

        g.drawImage(pot, POT_X, POT_Y, null );

        g.drawImage(cage,CAGE_X, CAGE_Y, null );
        Graphics2D graphics2 = (Graphics2D) g;
        return graphics2;
    }

}

