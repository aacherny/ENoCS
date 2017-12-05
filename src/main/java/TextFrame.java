package main.java;

import javax.swing.*;
import java.awt.*;

public class TextFrame extends JInternalFrame {

    private JTextArea textArea;
    private JScrollPane scrollPane;

    public TextFrame()
    {
        super("Simulation Events",
                true, //resizable
                true, //closable
                true, //maximizable
                false);//iconifiable

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setSize(500, 350);
        setLayout(new FlowLayout());

        initTextWindow();

        pack();
        setVisible(true);
    }

    private void initTextWindow(){
        textArea = new JTextArea("", 5, 50);
        textArea.setLineWrap(true);
        textArea.setEditable(true);

        scrollPane =  new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().add(scrollPane);
    }

    public void addText(String inputText){
        textArea.append(inputText + "\n");
    }
}