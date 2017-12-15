package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Colored rectangle object
 * <P>
 * Colored rectangle to put on the pipeline of a router
 *
 * @author Alex Cherny
 */
public class ColoredRectangle extends JPanel {

    private Color color;
    private int RECT_X = 0;
    private int RECT_Y = 0;
    private int RECT_WIDTH = 8;
    private int RECT_HEIGHT = 19;

    private Flit focusFlit;

    /**
     * Creates a colored rectangle at a certain location
     * @param inputFlit     The flit used to make the rectangle
     * @param inputRectX    The X location of the rectangle, based on which stage of the pipeline it's in
     * @param inputRectY    The Y location of the rectangle
     */
    public ColoredRectangle(Flit inputFlit, int inputRectX, int inputRectY){
        focusFlit = inputFlit;
        color = inputFlit.getColor();

        if(0 <= inputRectX && inputRectX <= 15) {
            RECT_Y = 53 + inputRectY * 50;
            RECT_X = 23 + inputRectX * 11;
        } else if (inputRectX == 16) {
            RECT_Y = 41;
            RECT_X = 252;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 17) {
            RECT_Y = 40;
            RECT_X = 326;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 18) {
            RECT_Y = 40;
            RECT_X = 400;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 19) {
            RECT_Y = 40;
            RECT_X = 474;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 20) {
            RECT_Y = 52 + inputRectY * 50;
            RECT_X = 561;
        }

        setLocation(RECT_X, RECT_Y);
        setSize(RECT_WIDTH, RECT_HEIGHT);
        setBackground(color);

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                try {
                    createFlitPanel();
                    System.out.println("CLICK CLICK CLICK");
                } catch(Exception a) {
                    System.out.println(a);
                }

            }
        });
    }

    /**
     * Creates a colored rectangle if a color is provided instead of a flit
     * @param inputColor    The color that the rectangle will be
     * @param inputRectX    The X location of the rectangle, based on which stage of the pipeline it's in
     * @param inputRectY    The Y location of the rectangle
     */
    public ColoredRectangle(Color inputColor, int inputRectX, int inputRectY){
        color = inputColor;

        if(0 <= inputRectX && inputRectX <= 15) {
            RECT_Y = 53 + inputRectY * 50;
            RECT_X = 23 + inputRectX * 11;
        } else if (inputRectX == 16) {
            RECT_Y = 41;
            RECT_X = 252;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 17) {
            RECT_Y = 40;
            RECT_X = 326;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 18) {
            RECT_Y = 40;
            RECT_X = 400;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 19) {
            RECT_Y = 40;
            RECT_X = 474;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 20) {
            RECT_Y = 52 + inputRectY * 50;
            RECT_X = 561;
        }

        setLocation(RECT_X, RECT_Y);
        setSize(RECT_WIDTH, RECT_HEIGHT);
        setBackground(color);
    }

    /**
     * Creates a window to display flit information
     */
    public void createFlitPanel(){
        Color clearColor = new Color(1,1,1,0);

        JFrame flitWindow = new JFrame();
        flitWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        flitWindow.setTitle("Flit Details");
        flitWindow.setSize(200, 150);
        flitWindow.setResizable(false);
        flitWindow.setLocationRelativeTo(null);
        flitWindow.setLayout(new GridLayout(4, 0));
        flitWindow.getRootPane().setBorder(BorderFactory.createLineBorder(clearColor, 5));


        JPanel colorContainer = new JPanel(new GridLayout(0, 2));
        JLabel colorLabel = new JLabel("Color: ");
        JPanel colorNum =  new JPanel();
        colorNum.setBackground(focusFlit.getColor());
        colorContainer.add(colorLabel);
        colorContainer.add(colorNum);
        flitWindow.add(colorContainer);

        JPanel cycleCreatedContainer = new JPanel(new GridLayout(0, 2));
        JLabel cycleCreatedLabel = new JLabel("Cycle created: ");
        JLabel cycleCreatedNum =  new JLabel(focusFlit.getCycleCreated()+"");
        cycleCreatedContainer.add(cycleCreatedLabel);
        cycleCreatedContainer.add(cycleCreatedNum);
        flitWindow.add(cycleCreatedContainer);

        JPanel packetIndexContainer = new JPanel(new GridLayout(0, 2));
        JLabel packetIndexLabel = new JLabel("Packet index: ");
        JLabel packetIndexNum =  new JLabel(focusFlit.getPacketIndex()+"");
        packetIndexContainer.add(packetIndexLabel);
        packetIndexContainer.add(packetIndexNum);
        flitWindow.add(packetIndexContainer);

        JPanel destinationContainer = new JPanel(new GridLayout(0, 2));
        JLabel destinationLabel = new JLabel("Destination: ");
        JLabel destinationNum =  new JLabel(("(" + focusFlit.getDestinationX() + "," + focusFlit.getDestinationY()) + ")");
        destinationContainer.add(destinationLabel);
        destinationContainer.add(destinationNum);
        flitWindow.add(destinationContainer);

        flitWindow.setVisible(true);
    }
}