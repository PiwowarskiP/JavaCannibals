package com.pwir;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

// kasa obsługująca suwaki odpowiedzialne za podawanie parametrów programu; powstała po to, żeby nie powtarzać 3 razy tego samego kodu w klasie UserPanel

public class UserPanelSlider extends JSlider
{
    private String name;

    public UserPanelSlider(String name, int min, int max, ChangeListener sliderHandling)
    {
        super(0, min, max, min + (int)((max - min)/2));
        this.name = name;
        setBackground(Color.WHITE);
        setMajorTickSpacing(1);
        setPaintTicks(true);
        setPaintLabels(true);

        addChangeListener(sliderHandling);
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}