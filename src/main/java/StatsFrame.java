package main.java;

import javax.swing.*;
import java.awt.*;

public class StatsFrame extends JInternalFrame {

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JLabel flitsCreatedNum;

    public StatsFrame()
    {
        super("Statistics",
                true, //resizable
                true, //closable
                true, //maximizable
                false);//iconifiable

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setSize(500, 500);
        setLayout(new GridLayout(5, 0));

        initTextWindow();

        pack();
        setVisible(true);
    }

    private void initTextWindow(){
        JPanel flitsCreated = new JPanel(new GridLayout(0, 2));
        JLabel flitsCreatedLabel = new JLabel("Flits created:");
        flitsCreatedNum =  new JLabel();
        flitsCreated.add(flitsCreatedLabel);
        flitsCreated.add(flitsCreatedNum);
        getContentPane().add(flitsCreated);
    }

//    public void addText(String inputText){
//        try {
//            textArea.append(inputText + "\n");
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }
}