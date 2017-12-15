package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * A window that displays important events that happen during the simulation
 */
public class TextFrame extends JInternalFrame {

    private JTextArea textArea;
    private JScrollPane scrollPane;

    /**
     * Object constructor
     */
    public TextFrame()
    {
        super("Simulation Events",
                true, //resizable
                true, //closable
                true, //maximizable
                false);//iconifiable

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setSize(500, 500);
        setLayout(new FlowLayout());

        initTextWindow();

        pack();
        setVisible(true);
    }

    /**
     * Initializes the text window
     */
    private void initTextWindow(){
        textArea = new JTextArea("", 10, 65);
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        scrollPane =  new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().add(scrollPane);
    }

    /**
     * Adds text to the window
     * @param inputText The text to be added
     */
    public void addText(String inputText){
        try {
            textArea.append(inputText + "\n");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}