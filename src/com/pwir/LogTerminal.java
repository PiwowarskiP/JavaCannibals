package com.pwir;

import javax.swing.*;
import java.awt.*;

public class LogTerminal extends JPanel
{
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JLabel title;
    JScrollBar vertical;

    public LogTerminal(int x, int y)
    {
        title = new JLabel("Event Log");
        textArea = new JTextArea(2, 10);
        scrollPane = new JScrollPane(textArea);

        textArea.setEnabled(false);
        textArea.setFont(new Font("Verdana", 1, 12));
        textArea.setForeground(Color.black);
        textArea.setDisabledTextColor(Color.BLACK);

        setBounds(x, y, 300, 100);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        vertical = scrollPane.getVerticalScrollBar();
    }

    public void addLog(String napis)
    {
        this.textArea.append(napis + "\n");
        vertical.setValue(vertical.getMaximum());
    }
}