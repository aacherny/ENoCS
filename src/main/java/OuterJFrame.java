package main.java;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Class for the outer window that holds other windows inside it
 */
public class OuterJFrame
{
    public OuterJFrame()
    {
        JFrame outerFrame = new JFrame();   // Outer window
        JMenuBar menuBar = new JMenuBar();  // Menu bar within the window

        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setTitle("ENoCS Simulator");
        outerFrame.setSize(500, 350);
        outerFrame.setLocationRelativeTo(null);


        //Build the File menu
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        //TODO add icons to the JMenuItems
        //'New' button under 'File'
        JMenuItem menuItemNew = new JMenuItem("New");
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItemNew.getAccessibleContext().setAccessibleDescription("Creates a new window");
        menu.add(menuItemNew);

        //'Open' button under 'File'
        JMenuItem menuItemOpen = new JMenuItem("Open");
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItemOpen.getAccessibleContext().setAccessibleDescription("Creates a new window");
        menu.add(menuItemOpen);

        menu.addSeparator();




        outerFrame.setJMenuBar(menuBar);


        outerFrame.setVisible(true);
    }
}
