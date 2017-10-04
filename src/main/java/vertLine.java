package main.java;

        import javax.swing.*;
        import java.awt.*;

public class vertLine extends JPanel {

    public vertLine(){
        setPreferredSize(new Dimension(26, 26));
        setLocation(0,0);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(13, 0, 13, 26);
    }
}
