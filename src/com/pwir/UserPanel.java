package com.pwir;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel{

    //Zmienna obiektowa pod kontroler animacji
    AnimationController animationController;

    // Slidery
    private UserPanelSlider barbSlider;
    private UserPanelSlider meatSlider;
    private UserPanelSlider priestsSlider;

    //labele i panele pod komponenty
    private JPanel slidersPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JLabel authorsLabel = new JLabel("  Made by: D. Sikorski & P. Piwowarski");

    // Buttony
    public JButton startButton = new JButton("Start");

    //zmienne pod parametry programu; dodana zmienna pod misjonarzy
    private int numberOfBarbarians;
    private int numberOfMeat;
    private int numberOfPriests;

    //setery i getery
    private void setNumberOfBarbarians(int value) {this.numberOfBarbarians=value;}
    private void setNumberOfMeat(int value) {this.numberOfMeat=value;}
    private void setNumberOfPriests(int value) {this.numberOfPriests=value;}

    public int getNumberOfBarbarians() {return this.numberOfBarbarians;}
    public int getNumberOfMeat() {return this.numberOfMeat;}
    public int getNumberOfPriests() {return this.numberOfPriests;}

    //zmienna pod rozmiar panelu użytkownika
    public static final int userPanelHeight = 145;

    public UserPanel() {

        //uzależniłem rozmiary i położenie panelu od zmiennych/stałych - łatwiej będzie w razie czego przeskalować
        setBounds(new Rectangle(0, MainFrame.SCREEN_SIZE_Y-userPanelHeight, MainFrame.SCREEN_SIZE_X, userPanelHeight));
        setBackground(Color.WHITE);
        setOpaque(true);
        setMaximumSize(new Dimension(MainFrame.SCREEN_SIZE_X, userPanelHeight));

        //labele opisujące slidery
        JLabel barbariansLabel1 = new JLabel("Liczba kanibali:  ");
        JLabel meatLabel1 = new JLabel("Ile porcji przypada na misjonarza:     ");
        JLabel priestsLabel1 = new JLabel("Liczba misjonarzy w klatce:  ");

        //inicjalizacja suwaków
        barbSlider = new UserPanelSlider("Barbarians Slider", 4, 12, new BarbSliderHandling());
        meatSlider = new UserPanelSlider("Meat Slider", 3, 8, new MeatSliderHandling());
        priestsSlider = new UserPanelSlider("Priests Slider", 3, 10, new PriestSliderHandling());

        //inicjalizacja zmiennych początkowymi stanami suwaków
        setNumberOfBarbarians(barbSlider.getValue());
        setNumberOfMeat(meatSlider.getValue());
        setNumberOfPriests(priestsSlider.getValue());

        //dodawanie komponentów do głównego panelu
        add(authorsLabel);
        add(slidersPanel);
        add(buttonsPanel);

        authorsLabel.setFont(new Font("Verdana", 1, 12));

        //dodawnie komponentów do panelu suwaków
        slidersPanel.setLayout(new GridLayout(2, 3));
        slidersPanel.add(barbariansLabel1);
        slidersPanel.add(meatLabel1);
        slidersPanel.add(priestsLabel1);

        slidersPanel.add(barbSlider);
        slidersPanel.add(meatSlider);
        slidersPanel.add(priestsSlider);


        //dodawanie buttonów + ich obsługa
        startButton.setSize(20, 15);
        //startButton.setPreferredSize(new Dimension(20,20));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        buttonsPanel.add(startButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 15)));




        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Uruchomienie animacji
                animationController = new AnimationController(numberOfMeat, numberOfBarbarians, numberOfPriests);
                animationController.start();
                startButton.setEnabled(false);
                barbSlider.setEnabled(false);
                meatSlider.setEnabled(false);
                priestsSlider.setEnabled(false);
            }
        });

    }

    //obsługa suwaków
    private class BarbSliderHandling implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            setNumberOfBarbarians(barbSlider.getValue());
            System.out.println("Barb: " + getNumberOfBarbarians());
        }
    }

    private class MeatSliderHandling implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            setNumberOfMeat(meatSlider.getValue());
            System.out.println("Meat: " + getNumberOfMeat());
        }
    }

    private class PriestSliderHandling implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            setNumberOfPriests(priestsSlider.getValue());
            System.out.println("Priest: " + getNumberOfPriests());
        }
    }

}
