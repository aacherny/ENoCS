package main.java;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Class for the outer window that holds other windows inside it
 */
public class OuterJFrame
{
    private JFrame outerFrame = new JFrame();   // Outer window

    public OuterJFrame()
    {
        // General things like window title and size
        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setTitle("ENoCS Simulator");
        outerFrame.setSize(500, 350);
        outerFrame.setLocationRelativeTo(null);

        // Creates the menu bar and adds it to the window
        JMenuBar menuBar = createMenuBar();
        outerFrame.setJMenuBar(menuBar);

        // Makes the window visible
        outerFrame.setVisible(true);
    }

    /**
     * Creates the menu bar in the outer window, adding dropdown menus like
     * 'File' and 'Edit'
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();  // Menu bar within the window

        //Build the File menu
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        //TODO add icons to the JMenuItems
        //'New' button under 'File'
        JMenuItem menuItemNew = new JMenuItem("New");
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menu.add(menuItemNew);

        //'Open' button under 'File'
        JMenuItem menuItemOpen = new JMenuItem("Open");
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menu.add(menuItemOpen);


        menu.addSeparator();

        //'Save' button under 'File'
        JMenuItem menuItemSave = new JMenuItem("Save");
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menu.add(menuItemSave);

        //'Save As' button under 'File'
        JMenuItem menuItemSaveAs = new JMenuItem("Save As");
        menu.add(menuItemSaveAs);

        menu.addSeparator();

        //'Exit' button under 'File'
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menu.add(menuItemExit);


        outerFrame.setJMenuBar(menuBar);

        return menuBar;
    }
}
